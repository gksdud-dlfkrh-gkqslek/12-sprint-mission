package com.sprint.mission.discodeit;

import com.sprint.mission.discodeit.entity.User;
import com.sprint.mission.discodeit.service.ChannelService;
import com.sprint.mission.discodeit.service.MessageService;
import com.sprint.mission.discodeit.service.UserService;
import com.sprint.mission.discodeit.service.jcf.JCFChannel;
import com.sprint.mission.discodeit.service.jcf.JCFMessage;
import com.sprint.mission.discodeit.service.jcf.JCFUserService;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class JavaApplication {

    static UserService userService = new JCFUserService();
    static ChannelService channelService = new JCFChannel();
    static MessageService messageService = new JCFMessage();


    // 로그인
    public static String login() {
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
                        return email;
                    };
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

    // 로그인 성공 후 채널 선택
    public static String channel(UUID myid){
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.println("\n\n=======================================");
            System.out.println("=========== 채널에 입장하였습니다 ===========");
            System.out.println("=======================================");

            channelService.findMyChannel(myid);

            System.out.println("---------------------------------------");
            System.out.println("||   a. 채널 만들기    |    b. 로그아웃   ||");
            System.out.println("---------------------------------------");

            System.out.print("선택(ID) : ");
            String i = input.nextLine();

            return i;
        }
    }

    // 채널 생성
    public static void createChannel(String username, UUID myid){
        Scanner input = new Scanner(System.in);

        System.out.print("채널 이름 : ");
        String channelname = input.nextLine();

        if(channelService.findAll(channelname)){
            channelService.creat(channelname, username, myid);  // 채널 생성
            UUID id = channelService.getchannelid(channelname); // 채널 id 조회
            messageService.create(channelname, id);             // 메시지 생성
            messageService.findmessage(id);                     // 메시지 조회
        }
        else {
            System.out.println("이미 존재하는 채널입니다.");
        }
    }

    public static String inChannel(String channelID){
        Scanner input = new Scanner(System.in);

        String channelname1 = channelService.findnamebyid(channelID);       // 채널 이름 조회
        System.out.println("\n\n\n\n---- \"" + channelname1 + "\" 채널에 입장하였습니다. ----\n");

        List<String> foundMessage = messageService.findmessage(UUID.fromString(channelID)); // 메시지 조회

        if(foundMessage == null) System.out.println("");    // null이면 공백 출력
        else System.out.println(foundMessage);              // 메시지 출력


        System.out.println("\n-----------------------------------");
        System.out.println("1. 채널 나가기 2. 초대하기");
        System.out.println("-----------------------------------");


        System.out.print("메시지 보내기(또는 1 or 2) : ");
        return input.nextLine();
    }

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

    public static void main(String[] args) {
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

            if (info != null && info.isEnabled() == true) {     // 채널 선택
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


    }
}
