package ru.shapov.cointrackerandroid.models;

public class Coin {
    private Long id;
    private String name;
    private String description;
    private int denomination;
    private String currency;
    private String country;
    private String mint;
    private int yearMinting;
    private String picturePath;

    public Coin() {
    }
    public Coin(Long id, int denomination, String currency, String country, String mint, int yearMinting, String picturePath) {
        this.id = id;
        this.denomination = denomination;
        this.currency = currency;
        this.country = country;
        this.mint = mint;
        this.yearMinting = yearMinting;
        this.picturePath = picturePath;
    }
    public Coin(int denomination, String currency, String country, String mint, int yearMinting, String picturePath) {
        this.denomination = denomination;
        this.currency = currency;
        this.country = country;
        this.mint = mint;
        this.yearMinting = yearMinting;
        this.picturePath = picturePath;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public int getDenomination() {
        return denomination;
    }
    public void setDenomination(int denomination) {
        this.denomination = denomination;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getCurrency() {
        return currency;
    }
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    public String getMint() {
        return mint;
    }
    public void setMint(String mint) {
        this.mint = mint;
    }

    public int getYearMinting() {
        return yearMinting;
    }
    public void setYearMinting(int yearMinting) {
        this.yearMinting = yearMinting;
    }
    public String getPicturePath() {
        return picturePath;
    }
    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }
}
