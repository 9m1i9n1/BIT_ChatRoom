package Chatting;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
 
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 
public class ClientFrame extends JFrame{
    //서버와의 통신을 위한 소켓
    private Socket socket;
    private BufferedWriter bw;
    private ChattingFrame chatFrame;
    
    public ClientFrame(ChattingFrame chatFrame) {
       this.chatFrame = chatFrame;
    }
    
    public void sendMsg(String sendMessage) {
        try {
            bw.write(sendMessage+"\n");
            bw.flush();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }//한문장이 끝났다는 것을 알리기 위해서 bufferedWriter에 "\n"을 붙인다
    }
    
    
    //소켓 설정을 위한 세터
    //이제 프레임도 소켓의 정보를 가지게 되었다
    public void setSocket(Socket socket) {
        this.socket = socket;
        try {
            OutputStream out = socket.getOutputStream();
            bw = new BufferedWriter(new OutputStreamWriter(out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //내부 클래스로 수신 스레드 작성
    class TcpClientReceiveThread implements Runnable {
        private Socket socket;
        public TcpClientReceiveThread(Socket socket) {
            this.socket = socket;
        }
        @Override
        public void run() {
            //서버로부터 오는 메세지를 읽어서
            //텍스트 에어리어에 추가하기
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    String msg = br.readLine();//메세지 한줄 읽어오기 
                    
                    chatFrame.addList(msg);
                }
            } catch (Exception e) {
                  chatFrame.addList("연결이 종료되었습니다.");
                //System.out.println("연결이 종료되었습니다.");
            }
            finally {                
                try {
                    if (socket!=null&&!socket.isClosed()) {
                    socket.close();//다 쓴 소켓 닫기            
                    }
                } catch (Exception e2) {
                    
                }
            }
        }
    }
}