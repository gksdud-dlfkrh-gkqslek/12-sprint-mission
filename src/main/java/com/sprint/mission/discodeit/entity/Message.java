package com.sprint.mission.discodeit.entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Message {
    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;

    private UUID channelId;
    private String username;            //사용자 이름
    private String channelname;         //체널이름
    private List<String> contents = new ArrayList<>();

    public Message(String channelname, UUID id) {
        this.channelname = channelname;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = this.createdAt;
        this.id = UUID.randomUUID();
        this.channelId = id;
    }

    public String getChannelname() {
        return channelname;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public UUID getId() {
        return id;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public UUID getChannelId() {
        return channelId;
    }

    public List<String> getContents() {
        return contents;
    }
    @Override
    public String toString() {
        return "Message{" +
                "channelname='" + channelname + '\'' +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", username='" + username + '\'' +
                '}';
    }

}
