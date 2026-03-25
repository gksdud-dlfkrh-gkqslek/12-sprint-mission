package com.sprint.mission.discodeit.entity;

import java.io.Serializable;
import java.util.UUID;

public class User implements Serializable {
    private final UUID id;
    private final Long createdAt;
    private Long updatedAt;

    private String username;    //사용자 별명
    private String email;       //사용자 이메일
    private String password;    //사용자 비밀번호
    private Boolean enabled = false;

    public User (String email, String username, String password) {
        this.createdAt = System.currentTimeMillis();
        this.email = email;
        this.id = UUID.randomUUID();
        this.password = password;
        this.username = username;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public String getEmail() {
        return email;
    }

    public UUID getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public String getUsername() {
        return username;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void update(String email, String password, Boolean enabled) {
        this.email = email;
        this.password = password;
        this.enabled = enabled;

    }

    @Override
    public String toString() {
        return "User{" +
                "createdAt=" + createdAt +
                ", id=" + id +
                ", updatedAt=" + updatedAt +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
