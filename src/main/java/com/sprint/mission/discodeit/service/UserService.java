package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.User;

import java.util.UUID;

public interface UserService {

    // 로그인
    Boolean login(String email, String password);

    // 회원가입
    void create(String newemail, String newname, String newpassword);

    // 이메일로 유저 조회
    User find(String email);

    // 이메일로 유저 id 조회
    UUID finduserId(String email);

    // 로그아웃
    void logout(UUID id);

    // 현제 접속 중인지 유저 이메일 조회
    String  enableuser();
}
