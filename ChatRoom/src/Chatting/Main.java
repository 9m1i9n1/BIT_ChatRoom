package Chatting;

import java.awt.EventQueue;
import java.net.Socket;

import javax.swing.JOptionPane;

import Chatting.ClientFrame.TcpClientReceiveThread;

public class Main {

   public static void main(String args[]) {
      
      String nickname = JOptionPane.showInputDialog("닉네임을 입력하세요!");

        if (nickname.length() > 0) {

         ChattingFrame chatFrame = new ChattingFrame(nickname);
         ClientFrame cf = new ClientFrame(chatFrame);
         
         new MenuActionListener();
         
         try {
               //서버 아이피 , 포트번호 -> 소켓 생성 -> 연결 요청
               Socket socket = new Socket("192.168.0.9", 5000);
               //소켓 객체 생성
               cf.setSocket(socket);//메인에서 프레임 생성
               TcpClientReceiveThread th1 = cf.new TcpClientReceiveThread(socket);
               //TcpClientReceiveThread가 내부 클래스로 선언되어 있기 때문에
               //cf로 접근해서 socket을 전달한다
               new Thread(th1).start();
               
           } catch (Exception e) {
               e.printStackTrace();
           }
         
         chatFrame.setClient(cf);
         
         EventQueue.invokeLater(new Runnable() {
            public void run() {
               try {
                  chatFrame.setVisible(true);
                  
                  chatFrame.btnEvent(chatFrame.getButton(), chatFrame.getTextField(), chatFrame.getVector());
                  chatFrame.textEvent(chatFrame.getTextField(), chatFrame.getVector());
               } catch (Exception e) {
                  e.printStackTrace();
               }
            }
         });
        }
   }
}