package br.com.uol.cliente.service;

import br.com.uol.cliente.model.*;
import br.com.uol.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public interface ClienteService {

    public List<Cliente> getAll();

    public Optional<Cliente> getById(Long id);

    public void delete(Long id);

    public Cliente createOrUpdate(Cliente cliente);

}

@Service
class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Value("${api_geolocalização_ip}")
    private String ip;
    @Value("${api_clima_geolocalização}")
    private String clima;

    @Override
    public List<Cliente> getAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Cliente> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Cliente createOrUpdate(Cliente cliente) {

        if(cliente.getId() == null){
            cliente = criarCliente(cliente);
        }

        return repository.save(cliente);

    }

    private Cliente criarCliente(Cliente cliente) {

        RestTemplate restTemplate = new RestTemplate();

        IpLocal ipLocalizacao = restTemplate.getForObject(ip+"/json/"+cliente.getIp(), IpLocal.class);

        cliente.setLat(Double.valueOf(ipLocalizacao.getData().getLatitude()));
        cliente.setLongi((Double.valueOf(ipLocalizacao.getData().getLongitude())));

        Localidade[] localidades = restTemplate.getForObject(clima+"location/search/?lattlong="+cliente.getLat()+","+cliente.getLongi(), Localidade[].class);
        List<Localidade> lista = Arrays.asList(localidades).stream().
                filter(x->x.getWoeid()!=null && !"".equals(x.getWoeid())).
                sorted((l1,l2)->Long.compare(Long.parseLong(l1.getDistance()), Long.parseLong(l2.getDistance()))).
                collect(Collectors.toList());
        boolean achou =  false;
        int i = 0;
        while(!achou) {
            Weather weather = restTemplate.getForObject(clima+"location/"+lista.get(i).getWoeid(), Weather.class);
            Optional<WeatherData> wD = weather.getConsolidated_weather().stream().
                    filter(x->x.getMax_temp()!=null && x.getMin_temp()!=null).
                    findFirst();
            if(wD.isPresent()) {
                cliente.setTempMax(Float.parseFloat(wD.get().getMax_temp()));
                cliente.setTempMin(Float.parseFloat(wD.get().getMin_temp()));
                achou = true;
            }
        }
        return cliente;
    }
}
