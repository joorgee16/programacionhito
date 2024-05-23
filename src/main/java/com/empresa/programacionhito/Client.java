package com.empresa.programacionhito;

public class Client {
    private String id;
    private String name;
    private String billing;

    public Client(String id, String name, String billing) {
        this.id = id;
        this.name = name;
        this.billing = billing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBilling() {
        return billing;
    }

    public void setBilling(String billing) {
        this.billing = billing;
    }
}
