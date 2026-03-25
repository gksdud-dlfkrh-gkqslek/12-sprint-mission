package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Channel implements Serializable {
    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;

    private String username;        // 채널을 만든 사용자이름
    private String channelname;     //채널 이름
    private List<UUID> enableuser =  new ArrayList<>();

    public Channel(String channelname,String username,  UUID myid) {
        this.channelname = channelname;
        this.createdAt = System.currentTimeMillis();
        this.id = UUID.randomUUID();
        this.username = username;
        this.enableuser.add(myid);
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

    public List<UUID> getEnableuser() {
        return enableuser;
    }

    public void update(UUID enableuser) {
        this.enableuser.add(enableuser);

    }

    @Override
    public String toString() {
        return "Channel{" +
                "channelname='" + channelname + '\'' +
                ", id=" + id +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", username='" + username + '\'' +
                '}';
    }
}
