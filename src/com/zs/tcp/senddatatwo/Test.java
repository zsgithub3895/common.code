package com.zs.tcp.senddatatwo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Map.Entry;

public class Test {
	public static void main(String[] args) throws SQLException {
		/*String url = "jdbc:mysql://10.223.138.141:3306/neon?characterEncoding=utf-8";
		String username = "chenyunjie";
		String password = "chenyunjie";
		Connection conn = null;
		PreparedStatement stmt = null;
		int count = 0;
		
		try {
			conn = DriverManager.getConnection(url, username, password);
			StringBuilder insertSql = new StringBuilder();
			insertSql.append("INSERT INTO hademo VALUES(?,?,?,?,?,?,?)");
			conn.setAutoCommit(false);
			stmt = conn.prepareStatement(insertSql.toString());
		} catch (Exception e) {
            System.out.println(e);
		}
		*/
		File f = new File("C:/Users/sai/Desktop/122.txt");
		try {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String line = "";
			while ((line = br.readLine()) != null) {
				String hasIds = line.split(",", -1)[1];
				String key = line.split(",", -1)[7];
				String value = line.split(",", -1)[9];
				if("23".equals(hasIds) && !"0".equals(value)){
					System.out.println(key);
				}
				//System.out.println(hasIds);
				/*String[] arr = hasIds.split("#", -1);
				count++;
				stmt.setString(1, arr[0]);
				stmt.setString(2, arr[1]);
				stmt.setString(3, arr[2]);
				stmt.setString(4, arr[3]);
				stmt.setString(5, arr[4]);
				stmt.setString(6, arr[5]);
				stmt.setString(7, arr[6]);
				stmt.addBatch();
				if (count % 100 == 0) {
					stmt.executeBatch();
					conn.commit();
					count = 0;
			    }
				if (count != 0) {
					stmt.executeBatch();
					conn.commit();
				}*/
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void toBizMysqlTemp(String[] arr) {

		
	}

}
