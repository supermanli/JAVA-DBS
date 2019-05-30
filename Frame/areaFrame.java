package Frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ConnectMysql.ConnectSql;

/**
* @author super_lc
* @version 创建时间：2019年3月20日 下午7:49:35
* @filename areaFrame.java
* @ClassName 类名称
* @Description 类描述
*/
public class areaFrame {

	private DefaultTableModel model;
	private JTable table;
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	private JButton exitbtn;
	JFrame frame = new JFrame();
	JScrollPane scrollPane = new JScrollPane(table);
	Container tablePanel = new JPanel();

	public areaFrame() {
		init();
		action();
	}

	private void init() {

		frame.setTitle("供应商地区信息表");
		frame.setSize(720, 500);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);// 居中显示

		frame.add(tablePanel, "Center");
		tablePanel.setLayout(new BorderLayout());
		table = new JTable();
		table.setToolTipText("");

		tablePanel.add(scrollPane, "Center");
		String[] columnNames = { "供应商号", "供应商名", "地址"};
		// 连接数据库
		con = ConnectSql.ConnectSql();
		try {
			ps = con.prepareStatement(
					"select sup_number,sup_name,sup_address "
							+ "from supplier ");
			rs = ps.executeQuery();
			rs.last();// 将光标置于最后一行
			int n = rs.getRow();// 获取行数
			// System.out.println(n);
			String[][] result = new String[n][7];
			rs.beforeFirst(); // 返回第一行
			// 将结果集转化为数组
			while (rs.next()) {
				for (int i = 0; i < n; i++) {
					result[i][0] = rs.getString("sup_number");
					result[i][1] = rs.getString("sup_name");
					result[i][2] = rs.getString("sup_address");
					
					rs.next(); // 读取下一行
				}
				// 将结果集装入表格
				model = new DefaultTableModel(result, columnNames);
				table = new JTable(model);
				// 表格载入滑动面板
				JScrollPane jsp = new JScrollPane(table);
				tablePanel.add(jsp);
				
				exitbtn = new JButton("退出");
				JPanel southPane = new JPanel();
				southPane.setLayout(new FlowLayout());// 使用流布局
				// FlowLayout f = new FlowLayout();//
				
				southPane.add(exitbtn);
				southPane.setBorder(new EmptyBorder(10, 20, 10, 20));// 设置边界
				tablePanel.add(southPane, BorderLayout.SOUTH);// 加入到面板底部
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 关闭数据库连接
			ConnectSql.closeDB(con, ps, rs);
		}
	}

	private void action() {
		
		exitbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
			}
		});
	}

}

