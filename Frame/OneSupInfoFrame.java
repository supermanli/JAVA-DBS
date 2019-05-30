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
 * @version ����ʱ�䣺2018��12��1�� ����8:14:50
 * @filename SupInfoFrame.java
 * @ClassName ������
 * @Description ������
 */
public class OneSupInfoFrame extends JFrame {
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
	Container tablePanel = new JPanel();
	JFrame frame = new JFrame();

	public OneSupInfoFrame() {
		init();
		action();
		// run();
	}

	private void init() {
		JScrollPane scrollPane = new JScrollPane(table);
		frame.setTitle("��Ӧ����Ϣ��");

		frame.setSize(720, 500);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);// ������ʾ
		frame.validate();
		frame.add(tablePanel, "Center");
		tablePanel.setLayout(new BorderLayout());
		table = new JTable();
		table.setToolTipText("");

		tablePanel.add(scrollPane, "Center");
		String[] columnNames = { "��Ӧ�̺�", "��Ӧ����", "��ַ", "�绰", "��Ӧ��Ʒ��", "��Ӧ��", "���" };
		// �������ݿ�
		con = ConnectSql.ConnectSql();
		try {
			ps = con.prepareStatement(
					"select a.sup_number,sup_name,sup_address,sup_tel,part_number,sup_provide ,sup_introduce "
							+ "from supplier as a,sup_provide as b where a.sup_number = b.sup_number and a.sup_number = "
							+ LoginFrame.id);
			rs = ps.executeQuery();
			rs.last();// ������������һ��
			int n = rs.getRow();// ��ȡ����
			// System.out.println(n);
			String[][] result = new String[n][7];
			rs.beforeFirst(); // ���ص�һ��
			// �������ת��Ϊ����
			while (rs.next()) {
				for (int i = 0; i < n; i++) {
					result[i][0] = rs.getString("sup_number");
					result[i][1] = rs.getString("sup_name");
					result[i][2] = rs.getString("sup_address");
					result[i][3] = rs.getString("sup_tel");
					result[i][4] = rs.getString("part_number");
					result[i][5] = rs.getString("sup_provide");
					result[i][6] = rs.getString("sup_introduce");
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
				frame.validate();
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
						// ��ȡ���Ĺ�Ӧ�̺š���Ӧ��������Ӧ�̵�ַ����Ӧ�̵绰����Ӧ����š���Ӧ�������
						int a1 = Integer.parseInt((String) table.getModel().getValueAt(n, 0));
						// String a2 = (String) table.getModel().getValueAt(n, 1);
						// String a3 = (String) table.getModel().getValueAt(n, 2);
						// int a4 = Integer.parseInt((String) table.getModel().getValueAt(n, 3));
						int a5 = Integer.parseInt((String) table.getModel().getValueAt(n, 4));
						int a6 = Integer.parseInt((String) table.getModel().getValueAt(n, 5));
//						String a7 = (String) table.getModel().getValueAt(n, 6);
						// System.out.println(a3);
						// System.out.println(a4);
						// ps = con.prepareStatement("insert into supplier value (" + a1 + ", '" + a2 +
						// "', '" + a3 + "', "
						// + a4 + ",'" + a7 + "');");
						// ps.executeUpdate();
						ps = con.prepareStatement(
								"insert into sup_provide values (" + a1 + ", " + a5 + ", " + a6 + ");");
						ps.executeUpdate();
						// frame.dispose();
						if (a1 != 0) {
							JOptionPane.showMessageDialog(null, "������ݳɹ�");
							// model.fireTableDataChanged();
							String[] columnNames = { "��Ӧ�̺�", "��Ӧ����", "��ַ", "�绰", "��Ӧ�����", "��Ӧ��", "���" };
							// �������ݿ�
							con = ConnectSql.ConnectSql();
							try {
								ps = con.prepareStatement(
										"select a.sup_number,sup_name,sup_address,sup_tel,part_number,sup_provide ,sup_introduce "
												+ "from supplier as a,sup_provide as b where a.sup_number=b.sup_number and a.sup_number=20181001");
								rs = ps.executeQuery();
								rs.last();// ������������һ��
								int n1 = rs.getRow();// ��ȡ����
								// System.out.println(n);
								String[][] result = new String[n1][7];
								rs.beforeFirst(); // ���ص�һ��
								// �������ת��Ϊ����
								while (rs.next()) {
									for (int i = 0; i < n1; i++) {
										result[i][0] = rs.getString("sup_number");
										result[i][1] = rs.getString("sup_name");
										result[i][2] = rs.getString("sup_address");
										result[i][3] = rs.getString("sup_tel");
										result[i][4] = rs.getString("part_number");
										result[i][5] = rs.getString("sup_provide");
										result[i][6] = rs.getString("sup_introduce");
										rs.next(); // ��ȡ��һ��
									}
									// �������װ����
									model = new DefaultTableModel(result, columnNames);
									table = new JTable(model);
									// ������뻬�����
									JScrollPane jsp = new JScrollPane(table);
									tablePanel.add(jsp);

								}
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						} else {
							JOptionPane.showMessageDialog(null, "�������ʧ��");
						}

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null, "�������ʧ�ܣ�");
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
						// ps = con.prepareStatement("delete from supplier where sup_number= " + a1);
						// ps.executeUpdate();
						ps = con.prepareStatement(
								"delete from sup_provide where sup_number= " + a1 + " and part_number = " + a2 + ";");
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
						// ��ȡ���Ĺ�Ӧ�̺š���Ӧ��������Ӧ�̵�ַ����Ӧ�̵绰����Ӧ����š���Ӧ�������
						int a1 = Integer.parseInt((String) table.getModel().getValueAt(n, 0));
						String a2 = (String) table.getModel().getValueAt(n, 1);
						String a3 = (String) table.getModel().getValueAt(n, 2);
						int a4 = Integer.parseInt((String) table.getModel().getValueAt(n, 3));
						int a5 = Integer.parseInt((String) table.getModel().getValueAt(n, 4));
						int a6 = Integer.parseInt((String) table.getModel().getValueAt(n, 5));
						String a7 = (String) table.getModel().getValueAt(n, 6);
						// System.out.println(a3);
						// System.out.println(a4);
						ps = con.prepareStatement("update supplier set sup_name= '" + a2 + "' ,sup_address= '" + a3
								+ "', sup_tel=" + a4 + ",sup_introduce='" + a7 + "' where sup_number=" + a1);
						int n1 = ps.executeUpdate();
						ps = con.prepareStatement("update sup_provide set sup_provide= " + a6 + " where sup_number= "
								+ a1 + " and part_number =" + a5);
						int n2 = ps.executeUpdate();
						// frame.dispose();
						if (n1 != 0 && n2 != 0) {
							JOptionPane.showMessageDialog(null, "�������ݳɹ���");
							table.repaint();
							table.updateUI();
						} else {
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
