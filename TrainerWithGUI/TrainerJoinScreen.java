package TrainerWithGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;


public class TrainerJoinScreen extends JFrame {
	TrainerMenuJDBC tmdb = new TrainerMenuJDBC(); // JDBC ���� ��ü ����
	public TrainerJoinScreen(String userType) {
		
		setTitle("ȸ������");
		
		// 1. ������Ʈ���� ����� ����.
		JLabel title = new JLabel(userType + " ȸ������", JLabel.CENTER);
		
		title.setForeground(new Color(5, 0, 153));
		title.setFont(new Font("���� ���", Font.BOLD, 20));
		
		JButton join = new JButton("ȸ������");
		JButton cancel = new JButton("���");
		
		JTextField id = new JTextField(10); 
		JPasswordField pwd = new JPasswordField(10);
		JTextField name = new JTextField(10);
		JTextField gym = new JTextField(6);
		JTextField address1 = new JTextField(10); address1.setText("ex) ����");
		JTextField address2 = new JTextField(10); address2.setText("ex) ���빮��");
		JTextField address3 = new JTextField(10); address3.setText("ex) ��ȭ����� 52");
		
		//������ 1~500���� ������ �������� ó��
		NumberFormatter ���� = new NumberFormatter();
		����.setValueClass(Integer.class);
		����.setMinimum(new Integer(1));
		����.setMaximum(new Integer(500));
		JFormattedTextField price1 = new JFormattedTextField(����);
		JFormattedTextField price10 = new JFormattedTextField(����);
		JFormattedTextField price20 = new JFormattedTextField(����);
		price1.setColumns(10);
		price10.setColumns(10);
		price20.setColumns(10);
	
	
		// form panel
		JPanel formPanel = new JPanel();
		formPanel.setLayout(new GridLayout(9, 1)); //������ �ִ� 9�� ������ 9,1�� ��
		
		//����1. ��ȭ��ȣ ���ڸ� 4�ڸ�
		JPanel idPanel = new JPanel();
		idPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		idPanel.add(new JLabel("��ȸ��ȣ ��4�ڸ� : "));
		idPanel.add(id);
		
		formPanel.add(idPanel);
		
		//����2. �н�����
		JPanel pwdPanel = new JPanel();
		pwdPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pwdPanel.add(new JLabel("��й�ȣ : "));
		pwdPanel.add(pwd);
		
		formPanel.add(pwdPanel);
		
		//ȸ���̸�, �̸� , ���� �߰�
		if(userType.equals("ȸ��")) {
			//�̸�
			JPanel namePanel = new JPanel();
			namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			namePanel.add(new JLabel("��    �� : "));
			namePanel.add(name);
			
			formPanel.add(namePanel);
			
			//����
			JPanel address2Panel = new JPanel();
			address2Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			address2Panel.add(new JLabel("��    �� : "));
			address2Panel.add(address2);
			
			formPanel.add(address2Panel);
		}
		
		//Ʈ���̳ʸ�, �̸� �߰�
		if(userType.equals("Ʈ���̳�")) {
			//�̸�
			JPanel namePanel = new JPanel();
			namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			namePanel.add(new JLabel("��    �� : "));
			namePanel.add(name);
			
			formPanel.add(namePanel);
			// �Ҽ� �ｺ��
			JPanel gymPanel = new JPanel();
			gymPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			gymPanel.add(new JLabel("�Ҽ� �ｺ�� : "));
			gymPanel.add(gym);
			
			formPanel.add(gymPanel);
		}
		
		//�����̸�, �ｺ���̸�, ����, ����, ���θ��ּ�, 1ȸ����, 10ȸ����, 20ȸ ���� �߰�.
		if(userType.equals("����")) {
			//�ｺ���̸�
			JPanel namePanel = new JPanel();
			namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			namePanel.add(new JLabel("�ｺ�� �̸� : "));
			namePanel.add(name);
			
			formPanel.add(namePanel);
			
			//����, ����, ���θ� �ּ�
			JPanel address1Panel = new JPanel();
			address1Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			address1Panel.add(new JLabel("��    �� : "));
			address1Panel.add(address1);
			formPanel.add(address1Panel);
			
			JPanel address2Panel = new JPanel();
			address2Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			address2Panel.add(new JLabel("��    �� : "));
			address2Panel.add(address2);
			formPanel.add(address2Panel);
			
			JPanel address3Panel = new JPanel();
			address3Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			address3Panel.add(new JLabel("���θ� �ּ� : "));
			address3Panel.add(address3);
			formPanel.add(address3Panel);
			
			JPanel price1Panel = new JPanel();
			price1Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			price1Panel.add(new JLabel("PT 1ȸ ���� : "));
			price1Panel.add(price1);
			formPanel.add(price1Panel);
			
			JPanel price10Panel = new JPanel();
			price10Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			price10Panel.add(new JLabel("PT 10ȸ ���� : "));
			price10Panel.add(price10);
			formPanel.add(price10Panel);
			
			JPanel price20Panel = new JPanel();
			price20Panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			price20Panel.add(new JLabel("PT 20ȸ ���� : "));
			price20Panel.add(price20);
			formPanel.add(price20Panel);
		}
		
		// radio + form panel
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.add(formPanel);
		
		// button panel
		JPanel panel = new JPanel();
		panel.add(join);
		panel.add(cancel);
		
		add(title, BorderLayout.NORTH);
		add(contentPanel, BorderLayout.CENTER);
		add(panel, BorderLayout.SOUTH);
		
		setBounds(200, 200, 250, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		// ȸ������ ��ư ������ �� �̺�Ʈó��
		join.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				
				String myId = id.getText();
				String myPwd = new String(pwd.getPassword());
				String myName = name.getText();
				String myGym = gym.getText();
				String myAddress1 = address1.getText();
				String myAddress2 = address2.getText();
				String myAddress3 = address3.getText();
				
				/* price1.getText()���� ������
				try {
					int price1 = Integer.parseInt(price1.getText());
				}
				catch(NumberFormatException n) {
					JOptionPane.showMessageDialog(null, "���ݿ� 1~500������ ������ �Է����ּ���.");
				}
				*/
				
				if(userType.equals("ȸ��")) JOptionPane.showMessageDialog(null, "��ȭ��ȣ ��4�ڸ� : "+myId+", ��й�ȣ : "+myPwd+", �� �� : "+myName + ", �� �� : "+address2);
				else if(userType.equals("Ʈ���̳�")) JOptionPane.showMessageDialog(null, "��ȸ��ȣ ��4�ڸ� : "+myId+", ��й�ȣ : "+myPwd+", �� �� : "+myName+ "�Ҽ� �ｺ�� : " +myGym);
				else if(userType.equals("����")) JOptionPane.showMessageDialog(null, "��ȭ��ȣ ��4�ڸ� : "+myId+", ��й�ȣ : "+myPwd+", �ｺ�� �̸� : "+myName 
						+ "\n�ּ� : "+ myAddress1 +" " + myAddress2 +" "+ myAddress3);
				
				if (userType.equals("Ʈ���̳�")) {
					int idNo = tmdb.checkPhoneNum(myId);
					String newpk = "T" + myId + Integer.toString(idNo);
					System.out.println(newpk);
					int joinSuccess = tmdb.Join(myGym, newpk, myName, myPwd);
					if (joinSuccess == 1) {
						int success = tmdb.checkLogin(newpk, myPwd);
						if (success == 1) {
							new TrainerMenuJTable(newpk);
						}
					}
					
				}
				
			}
		});
		
		
		
		// ��� ��ư�� Ŭ������ �� �̺�Ʈ ó��
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				
				new TrainerJoinScreen(userType);
				dispose();
				
			}
		});
	}
}