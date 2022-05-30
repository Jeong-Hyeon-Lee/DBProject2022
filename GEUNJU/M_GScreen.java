package DB2022TEAM03.GEUNJU;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class M_GScreen extends JFrame {
	public M_GScreen() {
		setTitle("�ｺ�� PT ���� �ý���");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //������ �����츦 ������ ���α׷� ����
		
		//��� - ȸ�� MENU
		JPanel M_main = new JPanel();
		JLabel subtitle = new JLabel("ȸ��MENU-�ｺ��");
		subtitle.setForeground(new Color(5,0,153));
		subtitle.setFont(new Font("���� ���", Font.BOLD, 25));
		M_main.add(subtitle);
		
		//�޴���
		JPanel btnGroup = new JPanel();
		btnGroup.setLayout(new GridLayout(2,1));
		
		//�� �޴�
		JPanel jp1 = new JPanel();
		jp1.setLayout(new FlowLayout());
		JPanel Menu1 = new JPanel();
		JButton recommendGYMBtn = new JButton("�ｺ�� ��õ�ޱ�");
		Menu1.add(recommendGYMBtn);
		jp1.add(Menu1);
		
		//JPanel jp2 = new JPanel();
		//jp2.setLayout(new FlowLayout());
		JPanel Menu2 = new JPanel();
		JButton searchGYMBtn = new JButton("�ｺ�� �˻��ϱ�");
		Menu2.add(searchGYMBtn);
		jp1.add(Menu2);
		
		//JPanel jp3 = new JPanel();
		//jp3.setLayout(new FlowLayout());
		JPanel Menu3 = new JPanel();
		JButton enrollGYMBtn = new JButton("�ｺ�� ����ϱ�");
		Menu3.add(enrollGYMBtn);
		jp1.add(Menu3);
		
		JPanel jp0 = new JPanel();
		jp0.setLayout(new FlowLayout());
		JPanel Menu9 = new JPanel();
		JButton undo = new JButton("�ڷΰ���");
		Menu9.add(undo);
		jp0.add(Menu9);

		//�޴��ǿ� �޴����̱�
		btnGroup.add(jp1);
		//btnGroup.add(jp2);
		//btnGroup.add(jp3);
		btnGroup.add(jp0);
		
		setLayout(new BorderLayout());
		
		add(M_main,BorderLayout.NORTH);
		add(btnGroup,BorderLayout.CENTER);
		
		setBounds(200,200,600,200);
		
		setResizable(false); // ȭ�� ũ�� �����ϴ� �۾�

		setVisible(true);
		
		//Btn click �̺�Ʈ
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				new M_MainScreen();
				dispose(); // ������ frame�� �����Ű�� �޼���.

			}
		});
	
	}	
}
