package Frame;
/**
* @author super_lc
* @version 创建时间：2018年12月1日 下午8:48:35
* @filename DeaFrame.java
* @ClassName 类名称
* @Description 类描述
*/

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import ConnectMysql.ConnectSql;

public class DeaFrame extends JFrame {
	private DefaultTableModel model;
	private JTable table;
	private JButton deabtn;
	private JButton cancelbtn;
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	int update;
	JDialog frame = new JDialog();

	public DeaFrame() {
		init();
		action();
	}

	public void init() {

		frame.setTitle("交易信息表");
		frame.setSize(700, 500);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);// 居中显示
		Container tablePanel = new JPanel();
		frame.add(tablePanel, "Center");
		tablePanel.setLayout(new BorderLayout());
		table = new JTable();
		table.setEnabled(false);
		table.setToolTipText("");
		JScrollPane scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane, "Center");
		try {
			con = ConnectSql.ConnectSql();
			ps = con.prepareStatement(
					"select cus_number,a.part_number,cus_need,sup_number,sup_provide from cus_demand as a ,sup_provide as b where a.part_number = b.part_number");
			rs = ps.executeQuery();
			rs.last();// 将光标置于最后一行
			int n = rs.getRow();// 获取行数
			// System.out.println(n);
			String[] columnNames = { "商品号", "顾客号", "需求量", "供应商号", "供应量" };
			String[][] result = new String[n][5];
			rs.beforeFirst(); // 返回第一行
			// 将结果集转化为数组
			while (rs.next()) {
				for (int i = 0; i < n; i++) {
					result[i][0] = rs.getString("part_number");
					result[i][1] = rs.getString("cus_number");
					result[i][2] = rs.getString("cus_need");
					result[i][3] = rs.getString("sup_number");
					result[i][4] = rs.getString("sup_provide");
					rs.next(); // 读取下一行
				}
				// 将结果集装入表格
				model = new DefaultTableModel(result, columnNames);
				table = new JTable(model);
				// 表格载入滑动面板
				// table.isCellEditable(1, 1);
				JScrollPane jsp = new JScrollPane(table);
				// System.out.println(jsp.getRowHeader());
				tablePanel.add(jsp);
				deabtn = new JButton("交易");
				cancelbtn = new JButton("退出");
				// tablePanel.setBounds(20, 20, 20, 20);
				JPanel southPane = new JPanel();
				southPane.setLayout(new FlowLayout());
				FlowLayout f = new FlowLayout();
				// f.setHgap(100);
				southPane.add(deabtn);
				southPane.add(cancelbtn);
				southPane.setBorder(new EmptyBorder(10, 20, 10, 20));
				tablePanel.add(southPane, BorderLayout.SOUTH);
				// tablePanel.add(deabtn,BorderLayout.SOUTH);
				// tablePanel.add(cancelbtn,BorderLayout.SOUTH);
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
		deabtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// mouseClicked();
				int n = table.getSelectedRow();
				// 如果选中行
				if (n > -1) {
					Object s1 = table.getModel().getValueAt(n, 2);
					Object s2 = table.getModel().getValueAt(n, 4);
					String s3 = String.valueOf(s1);
					String s4 = String.valueOf(s2);
					// System.out.println(s3.hashCode()<=s4.hashCode());
					if (s3.hashCode() <= s4.hashCode()) {
						int a1 = Integer.parseInt(s3);
						int a2 = Integer.parseInt(s4);
						a2 = a2 - a1;
						table.setValueAt(a2, n, 4);
						con = ConnectSql.ConnectSql();
						try {
							int a3 = Integer.parseInt((String) table.getModel().getValueAt(n, 0));
							int a4 = Integer.parseInt((String) table.getModel().getValueAt(n, 3));
							// System.out.println(a3);
							// System.out.println(a4);
							ps = con.prepareStatement("update sup_provide set sup_provide= " + a2
									+ " where sup_number = " + a4 + "  and part_number = " + a3 + ";");
							update = ps.executeUpdate();
							frame.dispose();
							JOptionPane.showMessageDialog(null, "交易成功");

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} finally {
							try {
								con.close();
								ps.close();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

						}

					} else {
						JOptionPane.showMessageDialog(null, "供求量不满足需求量不能交易");
					}
				} else {
					// doing nothing
				}
			}

		});
		cancelbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				frame.dispose();
			}
		});

	}

}
