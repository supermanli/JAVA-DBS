package Frame;

import java.awt.BorderLayout;
import java.awt.Container;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ConnectMysql.ConnectSql;

/**
 * @author super_lc
 * @version 创建时间：2018年12月3日 下午8:44:32
 * @filename PartInfoFrame.java
 * @ClassName 类名称
 * @Description 类描述
 */
public class PartInfoFrame {
	private DefaultTableModel model;
	private JTable table;
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	private JButton exitbtn;
	JFrame frame = new JFrame();
	public PartInfoFrame() {
		init();
		// action();
		action();
	}

	private void init() {
		
		frame.setTitle("商品信息表");
		frame.setSize(700, 500);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);// 居中显示

		Container tablePanel = new JPanel();
		frame.add(tablePanel, "Center");
		tablePanel.setLayout(new BorderLayout());
		table = new JTable();
		table.setToolTipText("");

		JScrollPane scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane, "Center");
		String[] columnNames = { "商品号", "商品名", "重量", "颜色", "简介"};
		// 连接数据库
		try {
			con = ConnectSql.ConnectSql();
			ps = con.prepareStatement(
					"select * from part;");
			rs = ps.executeQuery();
			rs.last();// 将光标置于最后一行
			int n = rs.getRow();// 获取行数
			// System.out.println(n);
			String[][] result = new String[n][6];
			rs.beforeFirst(); // 返回第一行
			// 将结果集转化为数组
			while (rs.next()) {
				for (int i = 0; i < n; i++) {
					result[i][0] = rs.getString("part_number");
					result[i][1] = rs.getString("part_name");
					result[i][2] = rs.getString("part_weight");
					result[i][3] = rs.getString("part_color");
					result[i][4] = rs.getString("part_introdruce");
					rs.next(); // 读取下一行
				}
				// 将结果集装入表格
				model = new DefaultTableModel(result, columnNames);
				table = new JTable(model);
				// 表格载入滑动面板
				JScrollPane jsp = new JScrollPane(table);
				tablePanel.add(jsp);
				JPanel southPane = new JPanel();
				exitbtn = new JButton("退出");
				southPane.add(exitbtn,BorderLayout.CENTER);
				tablePanel.add(southPane,BorderLayout.SOUTH);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "连接数据库失败！");
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
				//关闭窗体
				frame.dispose();
			}
		});
	}

}
