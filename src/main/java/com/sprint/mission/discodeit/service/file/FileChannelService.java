package com.sprint.mission.discodeit.service.file;

import com.sprint.mission.discodeit.entity.Channel;
import com.sprint.mission.discodeit.service.ChannelService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileChannelService implements ChannelService {

    // 파일 읽어 오기
    private List<Channel> readChannelsFromFile(){
        File channelfile = new File("channels.ser");

        if(!channelfile.exists()) return new ArrayList<>();

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(channelfile))){
            return (List<Channel>) ois.readObject();
        }
        catch(Exception e){
            System.out.println("error");

            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // 파일에 저장하기
    private void saveChannelsToFile(List<Channel> channels){
        File channelfile = new File("channels.ser");

        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(channelfile))){
            oos.writeObject(channels);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("저장에 실패했습니다.");
        }
    }

    // 채널 이름으로 채널 id조회
    @Override
    public UUID getchannelid(String channelname) {
        List<Channel> channels = readChannelsFromFile();

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
        List<Channel> channels = readChannelsFromFile();
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
        List<Channel> channels = readChannelsFromFile();
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
        List<Channel> channels = readChannelsFromFile();
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
        List<Channel> channels = readChannelsFromFile();
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
        List<Channel> channels = readChannelsFromFile();
        for(Channel c : channels){
            if(c.getId().equals(channelId)){
                c.update(inviterID);
                saveChannelsToFile(channels);
                System.out.println("초대 성공!");
            }
        }

    }

    // 채널 삭제
    @Override
    public void delete(String channelId, String username) {
        List<Channel> channels = readChannelsFromFile();

        UUID chId = UUID.fromString(channelId);

        for(Channel c : channels){
            if(c.getId().equals(chId)){
                if(c.getUsername().equals(username)){
                    channels.remove(c);
                    saveChannelsToFile(channels);
                    System.out.println("채널이 삭제되었습니다.");
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

    // 채팅방 허용된 유저 조회
    @Override
    public List<UUID> findEnableUser(String channelID) {
        List<Channel> channels = readChannelsFromFile();

        UUID chId = UUID.fromString(channelID);
        for(Channel c : channels){
            if(c.getId().equals(chId)){
                return c.getEnableuser();
            }
        }
        return null;

    }

    // 유저 강퇴
    @Override
    public void resignUser(UUID channelId, String id, String username) {
        List<Channel> channels = readChannelsFromFile();

        UUID uuid = UUID.fromString(id);
        for(Channel c : channels){
            if(c.getId().equals(channelId)){
                for(UUID enableuser : c.getEnableuser()){

                    if(enableuser.equals(uuid)){
                        c.getEnableuser().remove(enableuser);
                        saveChannelsToFile(channels);
                        System.out.println("강퇴 완료!");
                    }
                }
            }

        }
    }

    // 채널 생성
    @Override
    public void creat(String channelname, String username,  UUID myid) {
        List<Channel> channels = readChannelsFromFile();
        Channel channel = new Channel(channelname, username, myid);
        channels.add(channel);
        saveChannelsToFile(channels);

    }
}
