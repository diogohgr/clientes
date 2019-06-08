package br.com.uol.cliente.model;

public class IpLocal {

    private String status;
    private DataIp data;

    public IpLocal(){

    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataIp getData() {
        return data;
    }

    public void setData(DataIp data) {
        this.data = data;
    }
}
