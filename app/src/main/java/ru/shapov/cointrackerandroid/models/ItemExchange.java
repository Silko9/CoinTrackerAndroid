package ru.shapov.cointrackerandroid.models;

public class ItemExchange {
    private Coin coin;
    private int amount;

    public ItemExchange() {}
    public ItemExchange(Coin coin, int amount) {
        this.coin = coin;
        this.amount = amount;
    }

    public Coin getCoin() {
        return coin;
    }

    public void setCoin(Coin coin) {
        this.coin = coin;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
