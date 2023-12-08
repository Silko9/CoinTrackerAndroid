package ru.shapov.cointrackerandroid.models;

public class Dialogue {
    private Long userId;
    private int countMessages;
    private String lastDateMessage;

    public Dialogue() {}

    public Dialogue(Long userId, int countMessages, String lastDateMessage) {
        this.userId = userId;
        this.countMessages = countMessages;
        this.lastDateMessage = lastDateMessage;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getCountMessages() {
        return countMessages;
    }

    public void setCountMessages(int countMessages) {
        this.countMessages = countMessages;
    }

    public String getLastDateMessage() {
        return lastDateMessage;
    }

    public void setLastDateMessage(String lastDateMessage) {
        this.lastDateMessage = lastDateMessage;
    }
}
