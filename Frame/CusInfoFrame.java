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
 * @version 创建时间：2018年11月29日 下午11:17:23
 * @filename SearchFrame.java
 * @ClassName 类名称
 * @Description 类描述
 */

public class CusInfoFrame extends JFrame {
	private DefaultTableModel model;
	private JTable table;
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	private JButton addbtn;
	private JButton delbtn;
	private JButton updbtn;
	private JButton exitbtn;
	private JButton jiabtn;
	JFrame frame = new JFrame();

	// private DefaultTableModel tableModel;
	public CusInfoFrame() {
		init();
		action();
	}

	private void init() {
		frame.setTitle("顾客信息表");
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
		String[] columnNames = { "顾客号", "顾客名", "地址", "电话", "需求商品号", "需求量" };
		// 连接数据库
		try {
			con = ConnectSql.ConnectSql();
			ps = con.prepareStatement(
					"select a.cus_number,cus_name,cus_address,cus_tel,part_number,cus_need from customer as a,cus_demand as b where a.cus_number=b.cus_number");
			rs = ps.executeQuery();
			rs.last();// 将光标置于最后一行
			int n = rs.getRow();// 获取行数
			// System.out.println(n);
			String[][] result = new String[n][6];
			rs.beforeFirst(); // 返回第一行
			// 将结果集转化为数组
			while (rs.next()) {
				for (int i = 0; i < n; i++) {
					result[i][0] = rs.getString("cus_number");
					result[i][1] = rs.getString("cus_name");
					result[i][2] = rs.getString("cus_address");
					result[i][3] = rs.getString("cus_tel");
					result[i][4] = rs.getString("part_number");
					result[i][5] = rs.getString("cus_need");
					rs.next(); // 读取下一行
				}
				// 将结果集装入表格
				model = new DefaultTableModel(result, columnNames);
				table = new JTable(model);
				// 表格载入滑动面板
				JScrollPane jsp = new JScrollPane(table);
				tablePanel.add(jsp);
				jiabtn = new JButton(" + ");
				addbtn = new JButton("增加");
				delbtn = new JButton("删除");
				updbtn = new JButton("更新");
				exitbtn = new JButton("退出");
				JPanel southPane = new JPanel();
				southPane.setLayout(new FlowLayout());// 使用流布局
				// FlowLayout f = new FlowLayout();//
				southPane.add(jiabtn);
				southPane.add(addbtn);
				southPane.add(delbtn);
				southPane.add(updbtn);
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
		jiabtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				Vector<String[]> dataVector = new Vector<String[]>();
				((DefaultTableModel) table.getModel()).addRow(dataVector);// 添加空白行
				int count = model.getRowCount();// 获得总行数
				table.requestFocus();
				table.setRowSelectionInterval(count - 1, count - 1);// 最后一行获得焦点
				table.editCellAt(count - 1, 0);

			}
		});
		addbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int n = table.getSelectedRow();
				// System.out.println(n);
				if (n > -1) {
					con = ConnectSql.ConnectSql();
					// int update;
					try {
						// 获取表格的顾客号、需求零件号、顾客需求量
						int a1 = Integer.parseInt((String) table.getModel().getValueAt(n, 0));
//						String a2 = (String) table.getModel().getValueAt(n, 1);
//						String a3 = (String) table.getModel().getValueAt(n, 2);
//						int a4 = Integer.parseInt((String) table.getModel().getValueAt(n, 3));
						int a5 = Integer.parseInt((String) table.getModel().getValueAt(n, 4));
						int a6 = Integer.parseInt((String) table.getModel().getValueAt(n, 5));
						// System.out.println(a3);
						// System.out.println(a4);
						 ps = con.prepareStatement("insert into cus_demand values("+
						 a1+","+a5+","+a6+");");
//						ps = con.prepareStatement(
//								"insert into customer value (" + a1 + ", '" + a2 + "', '" + a3 + "', " + a4 + ");");
						int n1 = ps.executeUpdate();
//						ps = con.prepareStatement("insert into cus_demand value (" + a1 + ", " + a5 + ", " + a6 + ");");
//						int n2 = ps.executeUpdate();
						// frame.dispose();
						if (n1 != 0) {
							JOptionPane.showMessageDialog(null, "添加数据成功！");
						} else {
							JOptionPane.showMessageDialog(null, "添加数据失败！");
						}

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "添加数据失败！");
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
					// doing nothing
				}

			}
		});
		delbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int n = table.getSelectedRow();
				if (n > -1) {
					con = ConnectSql.ConnectSql();
					int a1 = Integer.parseInt((String) table.getModel().getValueAt(n, 0));
					int a2 = Integer.parseInt((String) table.getModel().getValueAt(n, 4));
					model.removeRow(n);
					try {

						ps = con.prepareStatement(
								"delete from cus_demand where cus_number= " + a1 + " and part_number =" + a2);
						ps.executeUpdate();
						JOptionPane.showMessageDialog(null, "删除数据成功！");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "删除数据失败！");
					} finally {
						// 关闭数据库连接
						ConnectSql.closeDB(con, ps, rs);
					}
					//

				} else {
					// doing nothing
				}
			}
		});
		updbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int n = table.getSelectedRow();
				// System.out.println(n);
				if (n > -1) {
					con = ConnectSql.ConnectSql();
					// int update;
					try {
						// 获取表格的顾客号、顾客名、顾客地址、顾客电话、需求零件号、顾客需求量
						int a1 = Integer.parseInt((String) table.getModel().getValueAt(n, 0));
						String a2 = (String) table.getModel().getValueAt(n, 1);
						String a3 = (String) table.getModel().getValueAt(n, 2);
						int a4 = Integer.parseInt((String) table.getModel().getValueAt(n, 3));
						int a5 = Integer.parseInt((String) table.getModel().getValueAt(n, 4));
						int a6 = Integer.parseInt((String) table.getModel().getValueAt(n, 5));
						// System.out.println(a3);
						// System.out.println(a4);
						// ps = con.prepareStatement("update cus_demand set")
//						ps = con.prepareStatement("update customer set cus_name= '" + a2 + "' , cus_address= '" + a3
//								+ "', cus_tel=" + a4 + " where cus_number=" + a1);
//						int n1=ps.executeUpdate();
						ps = con.prepareStatement("update cus_demand set cus_need=" + a6 + " where cus_number=" + a1
								+ " and part_number=" + a5);
						int n2=ps.executeUpdate();
						// frame.dispose();
						if(n2!=0) {
							JOptionPane.showMessageDialog(null, "更新数据成功！");
						}
						else {
							JOptionPane.showMessageDialog(null, "更新数据失败！");
						}

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "更新数据失败！");
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
					// doing nothing
				}

			}
		});
		exitbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
			}
		});
	}

}
