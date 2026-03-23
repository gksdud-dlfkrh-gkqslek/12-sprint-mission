package com.sprint.mission.discodeit.service;

import com.sprint.mission.discodeit.entity.Message;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    // 메시지 창 생성
    void create(String channelname, UUID id);

    // 메시지 조회
    List<String> findmessage(UUID id);

    // 입력 받은 메시지 저장
    void tomessage(UUID id, String message, String username);
}
