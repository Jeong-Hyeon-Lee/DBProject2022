package TrainerWithGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class TrainerLoginScreen extends JFrame {
	TrainerMenuJDBC tmdb = new TrainerMenuJDBC(); // JDBC ���� ��ü ����
	
	public TrainerLoginScreen(String userType) {

		setTitle("�α���");

		JPanel title = new JPanel();
		TrainerMenuJDBC tmdb = new TrainerMenuJDBC();
		JLabel login = new JLabel(userType + " �α���"); 
		login.setForeground(new Color(5, 0, 153));
		login.setFont(new Font("���� ���", Font.BOLD, 20));
		title.add(login);

		// ���̵� text field
		JPanel jp1 = new JPanel();
		jp1.setLayout(new GridLayout(2, 2));

		JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel jlb1 = new JLabel("���̵� : ", JLabel.CENTER);

		idPanel.add(jlb1);

		JPanel idPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JTextField jtf1 = new JTextField(10);

		idPanel2.add(jtf1);

		jp1.add(idPanel);
		jp1.add(idPanel2);

		// ��й�ȣ text field
		JPanel pwdPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel jlb2 = new JLabel("��й�ȣ : ", JLabel.CENTER);

		JPanel pwdPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JPasswordField jtf2 = new JPasswordField(10);

		pwdPanel.add(jlb2);
		pwdPanel2.add(jtf2);
		jp1.add(pwdPanel);
		jp1.add(pwdPanel2);

		// ��ư
		JPanel jp2 = new JPanel();
		jp2.setLayout(new FlowLayout());
		JPanel loginPanel = new JPanel(new FlowLayout());
		JButton jLogin = new JButton("�α���");

		JPanel joinPanel = new JPanel(new FlowLayout());
		JButton join = new JButton("ȸ������");

		JPanel backPanel = new JPanel(new FlowLayout());
		JButton back = new JButton("����ȭ��");

		loginPanel.add(jLogin);
		joinPanel.add(join);
		backPanel.add(back);
		jp2.add(loginPanel);
		jp2.add(joinPanel);
		jp2.add(backPanel);

		// jp3�� ��ġ
		JPanel jp3 = new JPanel();
		jp3.setLayout(new FlowLayout());
		jp3.add(jp1);
		jp3.add(jp2);

		setLayout(new BorderLayout());

		add(title, BorderLayout.NORTH);
		add(jp1, BorderLayout.CENTER);
		add(jp2, BorderLayout.SOUTH);

		setBounds(200, 200, 400, 250);

		setResizable(false); // ȭ�� ũ�� �����ϴ� �۾�

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);

		// �̺�Ʈ ó��
		//�α��� ��ư Ŭ����
		jLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				//�Էµ� �� �޾ƿ���
				String myId = jtf1.getText();
				String myPwd = new String(jtf2.getPassword());

				//�˾� â ��� Ȯ�� �����ֱ�
				JOptionPane.showMessageDialog(null, "���̵� : " + myId + ", ��й�ȣ : " + myPwd);
			
				if(userType.equals("ȸ��")) {
					
				}
				else if(userType.equals("Ʈ���̳�")) {
					int success = tmdb.checkLogin(myId, myPwd);
					if (success == 1) {
						new TrainerMenuJTable(myId);
					}
					else {
						jtf1.setText("");
						jtf2.setText("");
					}
				}
				
			}
		});
		
		//ȸ������ ��ư Ŭ����
		join.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				new TrainerJoinScreen(userType);
				dispose(); // ������ frame�� �����Ű�� �޼���.

			}
		});

		//����ȭ�� ��ư Ŭ����
		back.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {

				new ServiceStartScreen();
				dispose();

			}
		});
	}
	
}
