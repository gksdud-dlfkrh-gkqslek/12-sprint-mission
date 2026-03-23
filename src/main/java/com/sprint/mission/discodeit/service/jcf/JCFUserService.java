package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFUserService implements UserService {
    private final List<User> users = new ArrayList<>();



    // 로그인
    @Override
    public Boolean login(String email, String password) {
        if(password.isEmpty() || email.isEmpty()){
            System.out.println(" => 이메일, 비밀번호를 다시 입력해주세요");
        }
        for(User user : users){
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                user.setEnabled(true);
                System.out.println(" => 로그인 성공!");
                System.out.println(" => 채널로 이동합니다");
                return true;
            }
        }
        System.out.println(" => 로그인 실패 ");
        return false;
    }

    // 회원가입
    @Override
    public void create(String newemail, String newname, String newpassword) {
        for(User user : users){
            if(user.getEmail().equals(newemail)){
                System.out.println("이미 가입된 이메일입니다.");
                return;
            }
        }
        User user = new User(newemail, newname, newpassword);
        users.add(user);
        System.out.println("회원 가입 성공!");
    }

    // 이메일로 유저 조회
    @Override
    public User find(String email) {
        for(User user : users){
            if(user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

    // 이메일로 유저 id 조회
    @Override
    public UUID finduserId(String email) {
        for(User user : users){
            if(user.getEmail().equals(email)){
                return user.getId();
            }
        }
        return null;
    }

    // 로그아웃
    @Override
    public void logout(UUID id) {
        for(User user : users){
            if(user.getId().equals(id)){
                user.setEnabled(false);
            }
        }
    }

    // 현제 접속 중인지 확인
    @Override
    public String enableuser() {
        for(User user : users){
            if(user.isEnabled()){
                return user.getEmail();
            }
        }
        return null;
    }
}
