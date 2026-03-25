package com.sprint.mission.discodeit.service.jcf;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JCFChannel implements ChannelService {
    private final List<Channel> channels = new ArrayList<>();

    // 채널 이름으로 채널 id조회
    @Override
    public UUID getchannelid(String channelname) {
        for(Channel c : channels){
            if(c.getChannelname().equals(channelname)){
                return c.getId();
            }
        }
        return null;
    }

    // 내 채팅방 조회
    @Override
    public List<String> findMyChannel(UUID myid) {
        List<String> list = new ArrayList<>();
        for(Channel channel : channels) {
            for(UUID enableuser : channel.getEnableuser()){
                if(enableuser.equals(myid)){
                    list.add(channel.getId() + " | " + channel.getChannelname() + " | " + channel.getUsername());
                }
            }
        }
        if(list.isEmpty()){
            return null;
        }
        return list;


    }

    // 이름으로 채널 있는지 확인
    @Override
    public Boolean findAll(String channelname) {
        for(Channel c : channels){
            if(c.getChannelname().equals(channelname)){
                return false;
            }
        }
        return true;
    }

    // 채널 id로 조회 채널 존재 유무
    @Override
    public Boolean findchannelbyid(String channelid) {
        for(Channel c : channels){
            if(c.getId().toString().equals(channelid)){
                return true;
            }
        }
        return false;
    }

    // id로 name조회
    @Override
    public String findnamebyid(String i) {
        for(Channel c : channels){
            UUID uuid = UUID.fromString(i);
            if(c.getId().equals(uuid)){
                return c.getChannelname();
            }
        }
        return "채널을 찾을 수 없습니다.";
    }

    // 채널 id로 조회하고 유저 id로 유저 초대하기
    @Override
    public void invite(UUID channelId ,UUID inviterID) {
        for(Channel c : channels){
            if(c.getId().equals(channelId)){
                c.update(inviterID);
                System.out.println("초대 성공!");
            }
        }

    }

    // 채팅방 허용된 유저 조회
    @Override
    public List<UUID> findEnableUser(String channelID) {

        UUID chId = UUID.fromString(channelID);
        System.out.println("----- > 해당 채널에 허용된 유저 <-----");
        for(Channel c : channels){
            if(c.getId().equals(chId)){
                return c.getEnableuser();
            }
        }
        return null;

    }

    @Override
    public void resignUser(UUID channelID, String id, String username) {
        UUID uuid = UUID.fromString(id);
        for(Channel c : channels) {
            if (c.getId().equals(channelID)) {
                for (UUID enableuser : c.getEnableuser()) {
                    if (enableuser.equals(uuid)) {
                        c.getEnableuser().remove(enableuser);
                        System.out.println("강퇴 완료!");
                    }
                }
            }
        }
    }

    // 채널 삭제
    @Override
    public void delete(String channelId, String username) {
        UUID chId = UUID.fromString(channelId);

        for(Channel c : channels){
            if(c.getId().equals(chId)){
                if(c.getUsername().equals(username)){
                    channels.remove(c);
                    System.out.println("채널이 삭제 되었습니다!");
                    return;
                }
                else {
                    System.out.println("방장만 채널을 사용할 수 있습니다.");
                    return;
                }
            }
        }
        System.out.println("채널이 없습니다.");
    }

    // 채널 생성
    @Override
    public void creat(String channelname, String username,  UUID myid) {
        Channel channel = new Channel(channelname, username, myid);
        channels.add(channel);
        System.out.println("---- \"" + channelname + "\" 채널에 입장하였습니다. ----");

    }

}
