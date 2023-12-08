package ru.shapov.cointrackerandroid.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Message {
    private String messageText;
    private String timeSent;
    private Long userId;

    public Message(String messageText, Long userId) {
        this.messageText = messageText;
        this.timeSent = getCurrentTime();
        this.userId = userId;
    }

    public Message(String messageText, String timeSent, Long userId) {
        this.messageText = messageText;
        this.timeSent = timeSent;
        this.userId = userId;
    }

    public String getMessage() {
        return messageText;
    }

    public String getTime() {
        return timeSent;
    }

    public Long getUserId() {return userId;}

    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault());
        return sdf.format(new Date());
    }
}
