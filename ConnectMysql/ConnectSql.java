package ConnectMysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.ConnectionImpl;

/**
 * @author super_lc
 * @version ����ʱ�䣺2018��12��1�� ����10:18:03
 * @filename ConnectSql.java
 * @ClassName ������
 * @Description ������
 */
public class ConnectSql {
	public static Connection ConnectSql() {
		try {
			// ������������
			Class.forName("com.mysql.cj.jdbc.Driver");
			// getConnection()����������MySQL���ݿ⣡��
			return DriverManager.getConnection(
					"jdbc:mysql://127.0.0.1:3306/cus_sup?allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8",
					"root", "root");
		} catch (ClassNotFoundException ex1) {
			// ���ݿ��������쳣����
			ex1.printStackTrace();
		} catch (SQLException ex2) {
			// ���ݿ�����ʧ���쳣����
			ex2.printStackTrace();
		} catch (Exception ex3) {

			ex3.printStackTrace();
		}
		return null;
		
	}

	public static void closeDB(Connection con, PreparedStatement ps, ResultSet rs) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (rs != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
