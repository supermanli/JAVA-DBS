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
 * @version ����ʱ�䣺2018��11��29�� ����11:17:23
 * @filename SearchFrame.java
 * @ClassName ������
 * @Description ������
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
		frame.setTitle("�˿���Ϣ��");
		frame.setSize(700, 500);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);// ������ʾ
		Container tablePanel = new JPanel();
		frame.add(tablePanel, "Center");
		tablePanel.setLayout(new BorderLayout());
		table = new JTable();
		table.setToolTipText("");
		JScrollPane scrollPane = new JScrollPane(table);
		tablePanel.add(scrollPane, "Center");
		String[] columnNames = { "�˿ͺ�", "�˿���", "��ַ", "�绰", "������Ʒ��", "������" };
		// �������ݿ�
		try {
			con = ConnectSql.ConnectSql();
			ps = con.prepareStatement(
					"select a.cus_number,cus_name,cus_address,cus_tel,part_number,cus_need from customer as a,cus_demand as b where a.cus_number=b.cus_number");
			rs = ps.executeQuery();
			rs.last();// ������������һ��
			int n = rs.getRow();// ��ȡ����
			// System.out.println(n);
			String[][] result = new String[n][6];
			rs.beforeFirst(); // ���ص�һ��
			// �������ת��Ϊ����
			while (rs.next()) {
				for (int i = 0; i < n; i++) {
					result[i][0] = rs.getString("cus_number");
					result[i][1] = rs.getString("cus_name");
					result[i][2] = rs.getString("cus_address");
					result[i][3] = rs.getString("cus_tel");
					result[i][4] = rs.getString("part_number");
					result[i][5] = rs.getString("cus_need");
					rs.next(); // ��ȡ��һ��
				}
				// �������װ����
				model = new DefaultTableModel(result, columnNames);
				table = new JTable(model);
				// ������뻬�����
				JScrollPane jsp = new JScrollPane(table);
				tablePanel.add(jsp);
				jiabtn = new JButton(" + ");
				addbtn = new JButton("����");
				delbtn = new JButton("ɾ��");
				updbtn = new JButton("����");
				exitbtn = new JButton("�˳�");
				JPanel southPane = new JPanel();
				southPane.setLayout(new FlowLayout());// ʹ��������
				// FlowLayout f = new FlowLayout();//
				southPane.add(jiabtn);
				southPane.add(addbtn);
				southPane.add(delbtn);
				southPane.add(updbtn);
				southPane.add(exitbtn);
				southPane.setBorder(new EmptyBorder(10, 20, 10, 20));// ���ñ߽�
				tablePanel.add(southPane, BorderLayout.SOUTH);// ���뵽���ײ�
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// �ر����ݿ�����
			ConnectSql.closeDB(con, ps, rs);
		}

	}

	private void action() {
		jiabtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				Vector<String[]> dataVector = new Vector<String[]>();
				((DefaultTableModel) table.getModel()).addRow(dataVector);// ��ӿհ���
				int count = model.getRowCount();// ���������
				table.requestFocus();
				table.setRowSelectionInterval(count - 1, count - 1);// ���һ�л�ý���
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
						// ��ȡ���Ĺ˿ͺš���������š��˿�������
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
							JOptionPane.showMessageDialog(null, "������ݳɹ���");
						} else {
							JOptionPane.showMessageDialog(null, "�������ʧ�ܣ�");
						}

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(null, "�������ʧ�ܣ�");
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
						JOptionPane.showMessageDialog(null, "ɾ�����ݳɹ���");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "ɾ������ʧ�ܣ�");
					} finally {
						// �ر����ݿ�����
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
						// ��ȡ���Ĺ˿ͺš��˿������˿͵�ַ���˿͵绰����������š��˿�������
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
							JOptionPane.showMessageDialog(null, "�������ݳɹ���");
						}
						else {
							JOptionPane.showMessageDialog(null, "��������ʧ�ܣ�");
						}

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "��������ʧ�ܣ�");
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
