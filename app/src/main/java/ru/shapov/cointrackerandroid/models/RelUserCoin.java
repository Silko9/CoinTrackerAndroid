package ru.shapov.cointrackerandroid.models;

public class RelUserCoin {
    private Long userId;
    private Long coinId;
    private int stockAmount;
    private int exchangeAmount;
    private int needAmount;

    public RelUserCoin() {}

    public RelUserCoin(Long userId, Long coinId, int stockAmount, int exchangeAmount, int needAmount) {
        this.userId = userId;
        this.coinId = coinId;
        this.stockAmount = stockAmount;
        this.exchangeAmount = exchangeAmount;
        this.needAmount = needAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCoinId() {
        return coinId;
    }

    public void setCoinId(Long coinId) {
        this.coinId = coinId;
    }

    public int getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(int stockAmount) {
        this.stockAmount = stockAmount;
    }

    public int getExchangeAmount() {
        return exchangeAmount;
    }

    public void setExchangeAmount(int exchangeAmount) {
        this.exchangeAmount = exchangeAmount;
    }

    public int getNeedAmount() {
        return needAmount;
    }

    public void setNeedAmount(int needAmount) {
        this.needAmount = needAmount;
    }
}
