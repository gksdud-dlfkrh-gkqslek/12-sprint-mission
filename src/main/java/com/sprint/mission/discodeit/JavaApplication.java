package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.file.FileChannelService;
import com.sprint.mission.discodeit.service.file.FileMessageService;
import com.sprint.mission.discodeit.service.file.FileUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class JavaApplication {

    static UserService userService = new FileUserService();
    static ChannelService channelService = new FileChannelService();
    static MessageService messageService = new FileMessageService();


//-------------------------- 로그인 ----------------------------------------------

    public static void login() {
        Scanner input = new Scanner(System.in);
        while(true) {
            System.out.println("\n\n=========================");
            System.out.println("1. 로그인");
            System.out.println("2. 회원 가입");
            System.out.println("3. 프로그램 종료");
            System.out.println("=========================");
            System.out.print("선택(1~3) : ");
            int sign = input.nextInt();
            System.out.println("=========================");


            switch (sign) {
                case 1:     // 로그인
                    System.out.println("\n => 로그인 <=");

                    System.out.print("이메일 : ");
                    String email = input.next();
                    System.out.print("비밀번호 : ");
                    String password = input.next();

                    if(userService.login(email, password)){
                        System.out.println("로그인 성공!");
                        return;
                    }
                    else System.out.println("로그인 실패");
                    break;

                case 2:     // 회원가입
                    System.out.println("\n => 회원가입 <=");

                    System.out.print("이메일 : ");
                    String newemail = input.next();
                    System.out.print("이름 : ");
                    String newname = input.next();
                    System.out.print("비밀번호 : ");
                    String newpassword = input.next();

                    userService.create(newemail, newname, newpassword);
                    break;

                case 3:     //종료
                    System.exit(0);

                default:        // 입력 오류
                    System.out.println("!~잘못된 입력 값입니다~!");
                    break;
            }
        }
    }

//------------------------------- 채널 선택 -----------------------------------

    public static String channel(UUID myid){
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("\n\n=============================================");
            System.out.println("============== 채널에 입장하였습니다 ==============");
            System.out.println("=============================================");
            System.out.println("|             채팅방 id              |채팅방 이름|방장|");

            if(channelService.findMyChannel(myid) == null){
                System.out.println("\n             참여 중인 채널이 없습니다\n");
            }
            else {
                System.out.println(channelService.findMyChannel(myid).toString()
                        .replace(", ", "\n")
                        .replace("[","\n")
                        .replace("]","\n"));
            }


            System.out.println("-----------------------------------------------");
            System.out.println("||     a. 채널 만들기      |     b. 로그아웃      ||");
            System.out.println("-----------------------------------------------");

            System.out.print("선택(ID) : ");

            return input.nextLine();
        }
    }

//----------------------------------- 채널 생성 ----------------------------------------

    public static void createChannel(String username, UUID myid){
        Scanner input = new Scanner(System.in);

        System.out.print("채널 이름 : ");
        String channelname = input.nextLine();

        if(channelService.findAll(channelname)){
            channelService.creat(channelname, username, myid);  // 채널 생성
            UUID id = channelService.getchannelid(channelname); // 채널 id 조회
            messageService.create(channelname, id);             // 메시지 생성
            messageService.findmessage(id.toString());                     // 메시지 조회
        }
        else {
            System.out.println("이미 존재하는 채널입니다.");
        }
    }
//--------------------------------- 채널 삭제 ----------------------------------------------
    public static void deleteChannel(String channelId ,String username){
        channelService.delete(channelId, username);
    }

// -------------------------------- 채널 입장 ----------------------------------------------

    public static String inChannel(String channelID){
        Scanner input = new Scanner(System.in);

        String channelname1 = channelService.findnamebyid(channelID);       // 채널 이름 조회
        System.out.println("\n\n\n\n---- \"" + channelname1 + "\" 채널에 입장하였습니다. ----\n");


        List<String> foundMessage = messageService.findmessage(channelID); // 메시지 조회

        if(foundMessage == null) System.out.println(" ");    // null이면 공백 출력
        else System.out.println(foundMessage);              // 메시지 출력




        System.out.println("-----------------------------------");
        System.out.println("1. 채널 퇴장 2. 초대 하기 3. 채널 삭제하기");
        System.out.println("4. 유저 조회 5. 유저 강퇴 ");
        System.out.println("-----------------------------------");


        System.out.print("메시지 보내기(또는 1 or 2) : ");
        return input.nextLine();
    }

//-------------------------------- 초대 --------------------------------------------------------

    public static void invite(String messageid, String useremail) {
        Scanner input = new Scanner(System.in);

        System.out.print("초대할 이메일 입력 : ");
        String inviteremail = input.nextLine();

        UUID inviteuserID = userService.finduserId(inviteremail);
        if(inviteuserID == null || inviteremail.isEmpty()){
            System.out.println("사용자를 찾을 수 없습니다.");
        }
        else if(inviteremail.equals(useremail)){
            System.out.println("이 이메일은 현제 접속 유저입니다.");
        }
        else {
            channelService.invite(UUID.fromString(messageid),inviteuserID);
        }

    }

    // 허용된 유저 조회
    private static void finduser(String channelID, String username) {
        List<UUID> enableid = channelService.findEnableUser(channelID);
        List<String> enablename = userService.IdByName(enableid);

        List<String> enable = new ArrayList<>();
        for(int i =0; i<enablename.size();i++){
            enable.add(enablename.get(i) + "\t: " + enableid.get(i));
        }
        messageService.tomessage(
                UUID.fromString(channelID),
                "\n< 허용된 유저 리스트 >" + enable.toString()
                        .replace(", ", "\n")
                        .replace("[","\n")
                        .replace("]","\n"),
                username);
    }

    // 유저 강퇴
    private static void resign(UUID channelID, String username) {
        Scanner input = new Scanner(System.in);

        System.out.println("=> 강퇴할 유저 id : ");
        String id = input.nextLine();
        channelService.resignUser(channelID, id, username);

    }

//----------------------------- main ---------------------------------------------------------

    public static void main(String[] args) {
        try{
            Scanner input = new Scanner(System.in);
            User info=null;
            String username;
            UUID myid;

            login();    // 로그인하기

            outer: while(true){

                // 사용자 정보 가져오기
                String useremail = userService.enableuser();    // -> 성공시 현제 유저 이메일 가져옴
                if(userService.enableuser() == null){      // 로그인된 유저가 없으면 다시 로그인
                    login();
                    useremail = userService.enableuser();
                }
                info = userService.find(useremail);    // 사용자 정보
                username = info.getUsername();       // 사용자 이름
                myid = userService.finduserId(useremail);  // 사용자 아이디
                //---------------------------------------------------------------

                String channelID = "";

                if (info.isEnabled() == true) {     // 채널 선택
                    channelID = channel(myid);
                }
                else break outer;
                switch (channelID){
                    case "a":        // 채널 생성
                        createChannel(username, myid);
                        break;

                    case "b":        // 로그아웃
                        System.out.println("로그아웃 합니다");
                        userService.logout(myid);
                        continue  outer;


                    default:         // 채널 id 입력 받았을때
                        inner: while (true) {
                            if(channelService.findchannelbyid(channelID)){
                                String message = inChannel(channelID);

                                switch (message){
                                    case "1":        // 채널나가기
                                        break inner;
                                    case "2":        // 초대하기
                                        invite(channelID, useremail);
                                        break;
                                    case "3":       // 채널 삭제
                                        deleteChannel(channelID, username);
                                        break;
                                    case "4":       // 허용된 유저 조회
                                        finduser(channelID,username);
                                        break;
                                    case "5":       // 유저 강퇴
                                        resign(UUID.fromString(channelID), username);
                                        break;
                                    default:
                                        System.out.println(message);
                                        messageService.tomessage(UUID.fromString(channelID),message,username);
                                        break;
                                }

                            }
                            else {
                                System.out.println("잘못된 입력값입니다.");
                                break inner;
                            }
                        }
                }
            }

        }catch(Exception e){
            System.out.println("==================");
            System.out.println("에러: " + e.getMessage());
            e.printStackTrace();
            System.out.println("==================");
        }

    }
}
