package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Channel;

import java.util.UUID;

public interface ChannelService {

    // 내 채팅방 조회
    void findMyChannel(UUID myid);

    // 채널 생성
    void creat(String channelname, String username,  UUID myid);

    // 채널 이름으로 채널 id조회
    UUID getchannelid(String channelname);

    // 이름으로 채널 있는지 확인
    Boolean findAll(String channelname);

    // 채널 id로 조회 채널 존재 유무
    Boolean findchannelbyid(String channelid);

    // id로 name조회
    String findnamebyid(String i);

    // 채널 id로 조회하고 유저 id로 유저 초대하기
    void invite(UUID channelId, UUID inviteruserId);
}
