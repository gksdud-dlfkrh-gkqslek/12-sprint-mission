package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Message;
import com.sprint.mission.discodeit.service.MessageService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFMessage implements MessageService {
    private final List<Message> message = new ArrayList<>();

    // 메시지 창 생성
    @Override
    public void create(String channelname ,UUID id) {
        Message createmessage = new Message(channelname, id);
        message.add(createmessage);
    }

    // 메시지 조회
    @Override
    public List<String> findmessage(UUID id) {
        for(Message m : message){
            if(m.getChannelId().equals(id)){
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
        for(Message m : message){
            if(m.getChannelId().equals(id)){
                String save = username + "님 메시지\t : " + content;
                m.getContents().add(save);
            }
        }

    }
}
