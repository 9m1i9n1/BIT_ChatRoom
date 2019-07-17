package Chatting;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JList;

//Action 추가
class MenuActionListener implements ActionListener{
   @Override
   public void actionPerformed(ActionEvent e) {
      // TODO Auto-generated method stub
      
      String cmd = e.getActionCommand();
      
      switch(cmd) {
         case "연결":
            break;
         case "연결종료":
            break;
         case "채팅종료":
            System.exit(0);
      }
   }
}

public class ChattingFrame extends JFrame {

   private JPanel contentPane;
   private JTextField textField;
   private final JList list;
   private JButton btnSend;
   private Vector<String> vector;
   private JScrollPane scrollPane;
   private ClientFrame client;
   private String nickname;

   /**
    * Create the frame.
    */
   public ChattingFrame(String nickname) {
      //메뉴 이름 저장소
      String[] itemTitle = {"연결", "연결종료", "채팅종료"};
      
      this.nickname = nickname;
      
      vector = new Vector<String>();
      list = new JList();
      
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 450, 600);
      setTitle("2조 Chatting 프로그램");
      
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      textField = new JTextField();
      textField.setBounds(0, 447, 329, 85);
      contentPane.add(textField);
      textField.setColumns(10);
      
      btnSend = new JButton("\uC804\uC1A1");
      btnSend.setBounds(328, 448, 100, 50);
      contentPane.add(btnSend);
      
      //JScrollPane 객체 생성
      scrollPane = new JScrollPane();
      scrollPane.setBounds(0, 0, 430, 445);
      
      list.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
      list.setBounds(0, 0, 430, 445);

      //Panel에 ScrollPane 추가
      contentPane.add(scrollPane);

      //ScrollPane에 List 추가
      scrollPane.setViewportView(list);
      
      JMenuBar menuBar = new JMenuBar();
      JMenu settingMenu = new JMenu("설정");
      JMenuItem[] menuItem = new JMenuItem[3];
      
      //메뉴 액션 설정.
      MenuActionListener listener = new MenuActionListener();
      
      int menuCount = menuItem.length;
      for (int i = 0; i < menuCount; i++) {
         menuItem[i] = new JMenuItem(itemTitle[i]);
         menuItem[i].addActionListener(listener);
         settingMenu.add(menuItem[i]);
      }
      
      menuBar.setBounds(0, 0, 70, 40);
      
      menuBar.add(settingMenu);
      setJMenuBar(menuBar);
   }
   
   public void btnEvent(JButton btnSend, 
         JTextField textField, Vector<String> vector) {
      btnSend.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            vector.add("[" + GetNickname() + "]" + " : " + textField.getText());
            list.setListData(vector);
            
            client.sendMsg("[" + GetNickname() + "]" + " : " + textField.getText());
            
            textField.setText("");
         }
      });
   }
   
   public void textEvent(JTextField textField, Vector<String> vector) {
      textField.addActionListener(new ActionListener() {
         
         @Override
         public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            vector.add("[" + GetNickname() + "]" + " : " + textField.getText());
            list.setListData(vector);
            
            System.out.println(textField.getText());
            client.sendMsg("[" + GetNickname() + "]" + " : " + textField.getText());
            
            textField.setText("");
         }
      });
   }
   
   public void addList(String msg) {
      Vector<String> getVector = getVector();
      
      getVector.add(msg);
      list.setListData(getVector);
   }
   
   public JButton getButton() {
      return btnSend;
   }
   
   public JTextField getTextField() {
      return textField;
   }
   
   public Vector<String> getVector(){
      return vector;
   }
   
   public void setClient(ClientFrame client) {
      this.client = client;
   }
   
   public String GetNickname() {
      return nickname;
   }
}