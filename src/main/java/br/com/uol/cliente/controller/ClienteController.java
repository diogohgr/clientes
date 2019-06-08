package br.com.uol.cliente.controller;


import br.com.uol.cliente.model.Cliente;
import br.com.uol.cliente.response.Response;
import br.com.uol.cliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
public class ClienteController{

    @Autowired
    private ClienteService service;

    @GetMapping(value = "/api/v1/cliente/clientes", produces = "application/json")
    public ResponseEntity<List<Cliente>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @GetMapping(value = "/api/v1/cliente/clientes/{id}", produces = "application/json")
    public ResponseEntity<Cliente> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getById(id).get());
    }

    @DeleteMapping("/api/v1/cliente/clientes/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void delete (@PathVariable Long id){
        service.delete(id);
    }

    @PostMapping(path="/api/v1/cliente/clientes", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Response<Cliente>> createOrUpdate(@Valid @RequestBody Cliente cliente, BindingResult result, HttpServletRequest request) {
        Response<Cliente> response = new Response<Cliente>();

        if(result.hasErrors()) {
            result.getAllErrors().forEach(error->response.getErros().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }

        if(result.hasErrors()) {
            result.getAllErrors().forEach(error->response.getErros().add(error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(response);
        }
        cliente.setIp(getIpRequest(request));

        response.setData(service.createOrUpdate(cliente));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private String getIpRequest(HttpServletRequest request) {
        String remoteAddr = "";

        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
}