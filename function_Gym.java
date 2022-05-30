package DB2022Team03;


import java.sql.SQLException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.*;

public class function_Gym extends JFrame {

	/**
	 * @param args
	 */
	
	static String gymID;
	
	public static void main(String[] args) throws SQLException, IOException {
		// TODO Auto-generated method stub

		// 헬스장 로그인
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("헬스장 번호 입력: ");
		gymID = br.readLine();

		br.close();

		new G_selectMenu(gymID);
	}

}
