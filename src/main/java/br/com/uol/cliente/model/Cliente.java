package br.com.uol.cliente.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

public class Cliente {

    private Long id;
    private String nome;
    private String idade;
    private Double lat;
    private Double longi;
    private Float tempMax;
    private Float tempMin;
    private String ip;

    public Cliente() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotEmpty(message = "Nome do cliente não pode ser vazio.")
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @PositiveOrZero(message="Idade inválida, valor deve ser maior ou igual a zero.")
    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }


    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLongi() {
        return longi;
    }

    public void setLongi(Double longi) {
        this.longi = longi;
    }

    public Float getTempMax() {
        return tempMax;
    }

    public void setTempMax(Float tempMax) {
        this.tempMax = tempMax;
    }

    public Float getTempMin() {
        return tempMin;
    }

    public void setTempMin(Float tempMin) {
        this.tempMin = tempMin;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public String toString() {
        return "Cliente [id=" + id + ", nome=" + nome + ", idade=" + idade + ", latitude=" + lat + ", longitude=" + longi + "]";
    }
}
