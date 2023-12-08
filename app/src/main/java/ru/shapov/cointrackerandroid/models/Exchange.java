package ru.shapov.cointrackerandroid.models;

public class Exchange {
    private Long idUser;
    private int heHasForExchange = 0;
    private int weHaveForExchange = 0;

    public Exchange() {}
    public Exchange(Long idUser, int heHasForExchange, int weHaveForExchange) {
        this.idUser = idUser;
        this.heHasForExchange = heHasForExchange;
        this.weHaveForExchange = weHaveForExchange;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public int getHeHasForExchange() {
        return heHasForExchange;
    }

    public void setHeHasForExchange(int heHasForExchange) {
        this.heHasForExchange = heHasForExchange;
    }

    public int getWeHaveForExchange() {
        return weHaveForExchange;
    }

    public void setWeHaveForExchange(int weHaveForExchange) {
        this.weHaveForExchange = weHaveForExchange;
    }
}
