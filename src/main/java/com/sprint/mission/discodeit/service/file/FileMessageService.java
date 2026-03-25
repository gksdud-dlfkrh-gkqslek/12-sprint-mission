package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileMessageService implements MessageService {

    // 파일 읽어 오기
    private List<Message> readMessageFromFile(){
        File messagefile = new File("message.ser");

        if(!messagefile.exists()){
            return new ArrayList<>();
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(messagefile))){
            return (List<Message>) ois.readObject();
        }
        catch(Exception e){
            System.out.println("error");
            return new ArrayList<>();
        }
    }

    // 파일에 저장하기
    private void saveMessageToFile(List<Message> messages){
        File messagefile = new File("message.ser");

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(messagefile))){
            oos.writeObject(messages);
        }
        catch(Exception e){
            System.out.println("저장에 실패했습니다.");
        }
    }

    // 메시지 창 생성
    @Override
    public void create(String channelname , UUID id) {
        List<Message> message = readMessageFromFile();
        Message createmessage = new Message(channelname, id);
        message.add(createmessage);
        saveMessageToFile(message);
    }

    // 메시지 조회
    @Override
    public List<String> findmessage(String id) {
        List<Message> message = readMessageFromFile();
        UUID uuid = UUID.fromString(id);
        for(Message m : message){
            if(m.getChannelId().equals(uuid)){
                for(String me :  m.getContents()){
                    System.out.println(me);
                }
                break;
            }
        }
        return null;
    }

    // 입력 받은 메시지 저장
    @Override
    public void tomessage(UUID id, String content, String username) {
        List<Message> message = readMessageFromFile();
        for(Message m : message){
            if(m.getChannelId().equals(id)){
                String save = username + "님 메시지\t : " + content;
                m.getContents().add(save);
                saveMessageToFile(message);
            }
        }

    }
}
