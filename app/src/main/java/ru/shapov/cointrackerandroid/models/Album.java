package ru.shapov.cointrackerandroid.models;

public class Album {
    private Long id;
    private Long userId;
    private String name;
    private String dateCreate;

    public Album(){}

    public Album(Long userId, String name, String dateCreate) {
        this.userId = userId;
        this.name = name;
        this.dateCreate = dateCreate;
    }

    public Album(Long id, Long userId, String name, String dateCreate) {
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.dateCreate = dateCreate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(String dateCreate) {
        this.dateCreate = dateCreate;
    }
}
