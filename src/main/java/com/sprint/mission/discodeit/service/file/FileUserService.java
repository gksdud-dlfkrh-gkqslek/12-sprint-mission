package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.UserService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUserService implements UserService {

    // 파일 읽어 오기
    private List<User> readUserFromFile() {
        File userfile = new File("users.ser");  // .ser 직렬화된 파일, Serialized 객체 파일 // .txt도 가능. 단, 메모장으로 열람시 깨짐

        if(!userfile.exists()) {
            return new ArrayList<>();
        }
        // ObjectInputStream: 바이트를 자바 객체로 변환하는 스트림
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userfile))) {
            return (List<User>) ois.readObject();
        }
        catch(Exception e) {
            return new ArrayList<>();
        }
    }

    // 파일에 저장하기
    private void saveUserToFile(List<User> users) {
        File userfile = new File("users.ser");
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(userfile))) {
            oos.writeObject(users);
        }
        catch(Exception e) {
            System.out.println("저장에 실패했습니다.");
        }
    }

    // 로그인
    @Override
    public Boolean login(String email, String password) {
        if(password.isEmpty() || email.isEmpty()){
            System.out.println(" => 이메일, 비밀번호를 다시 입력해주세요");
        }
        List<User> users = readUserFromFile();
        for(User user : users){
            if(user.getEmail().equals(email) && user.getPassword().equals(password)){
                user.setEnabled(true);
                saveUserToFile(users);
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
        List<User> users = readUserFromFile();

        for(User user : users){
            if(user.getEmail().equals(newemail)){
                System.out.println("이미 가입된 이메일입니다.");
                return;
            }
        }
        User user = new User(newemail, newname, newpassword);
        users.add(user);
        saveUserToFile(users);
        System.out.println("회원 가입 성공!");
    }

    // 이메일로 유저 조회
    @Override
    public User find(String email) {
        List<User> users = readUserFromFile();
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
        List<User> users = readUserFromFile();
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
        List<User> users = readUserFromFile();
        for(User user : users){
            if(user.getId().equals(id)){
                user.setEnabled(false);
                saveUserToFile(users);
            }
        }
    }

    // 현제 접속 중인지 확인
    @Override
    public String enableuser() {
        List<User> users = readUserFromFile();
        for(User user : users){
            if(user.isEnabled()){
                return user.getEmail();
            }
        }
        return null;
    }

    @Override
    public List<String> IdByName(List<UUID>  enableid) {
        List<User> users = readUserFromFile();
        List<String> enablename = new ArrayList<>();

        for(User user : users){
            for(UUID id : enableid){
                if(user.getId().equals(id)){
                    enablename.add(user.getUsername());

                }
            }

        }
        return enablename;
    }

}
