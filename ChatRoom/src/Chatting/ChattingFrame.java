package Chatting;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
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
import javax.swing.BoxLayout;

//Action 추가
class MenuActionListener implements ActionListener {
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		String cmd = e.getActionCommand();

		switch (cmd) {
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
	private JPanel bottomPane;
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
		// 메뉴 이름 저장소
		String[] itemTitle = { "연결", "연결종료", "채팅종료" };

		this.nickname = nickname;

		vector = new Vector<String>();
		list = new JList();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("2조 Chatting 프로그램");
		setSize(480, 600);
		setResizable(false);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

		// JScrollPane 객체 생성
		scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		scrollPane.getViewport().setBorder(null);
		scrollPane.setViewportBorder(null);
		scrollPane.setBorder(null);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {  
	        public void adjustmentValueChanged(AdjustmentEvent e) {  
	            e.getAdjustable().setValue(e.getAdjustable().getMaximum());  
	        }
	    }); 

		list.setBorder(null);

		// ScrollPane에 List 추가
		scrollPane.setViewportView(list);

		// ===========================

		bottomPane = new JPanel();
		bottomPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		textField = new JTextField();
		textField.setColumns(35);

		btnSend = new JButton("전송");
		// ===========================

		// Panel에 ScrollPane 추가
		contentPane.add(scrollPane);

		bottomPane.add(textField);
		bottomPane.add(btnSend);

		getContentPane().add(contentPane, "Center");
		getContentPane().add(bottomPane, "South");

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBorderPainted(false);
		JMenu settingMenu = new JMenu("설정");
		JMenuItem[] menuItem = new JMenuItem[3];

		// 메뉴 액션 설정.
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

	public void btnEvent(JButton btnSend, JTextField textField, Vector<String> vector) {
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

	public Vector<String> getVector() {
		return vector;
	}

	public void setClient(ClientFrame client) {
		this.client = client;
	}

	public String GetNickname() {
		return nickname;
	}
}