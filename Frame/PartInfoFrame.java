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
 * @version ����ʱ�䣺2018��12��3�� ����8:44:32
 * @filename PartInfoFrame.java
 * @ClassName ������
 * @Description ������
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
		
		frame.setTitle("��Ʒ��Ϣ��");
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
		String[] columnNames = { "��Ʒ��", "��Ʒ��", "����", "��ɫ", "���"};
		// �������ݿ�
		try {
			con = ConnectSql.ConnectSql();
			ps = con.prepareStatement(
					"select * from part;");
			rs = ps.executeQuery();
			rs.last();// ������������һ��
			int n = rs.getRow();// ��ȡ����
			// System.out.println(n);
			String[][] result = new String[n][6];
			rs.beforeFirst(); // ���ص�һ��
			// �������ת��Ϊ����
			while (rs.next()) {
				for (int i = 0; i < n; i++) {
					result[i][0] = rs.getString("part_number");
					result[i][1] = rs.getString("part_name");
					result[i][2] = rs.getString("part_weight");
					result[i][3] = rs.getString("part_color");
					result[i][4] = rs.getString("part_introdruce");
					rs.next(); // ��ȡ��һ��
				}
				// �������װ����
				model = new DefaultTableModel(result, columnNames);
				table = new JTable(model);
				// ������뻬�����
				JScrollPane jsp = new JScrollPane(table);
				tablePanel.add(jsp);
				JPanel southPane = new JPanel();
				exitbtn = new JButton("�˳�");
				southPane.add(exitbtn,BorderLayout.CENTER);
				tablePanel.add(southPane,BorderLayout.SOUTH);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "�������ݿ�ʧ�ܣ�");
		} finally {
			// �ر����ݿ�����
			ConnectSql.closeDB(con, ps, rs);
		}

	}
	private void action() {
		exitbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//�رմ���
				frame.dispose();
			}
		});
	}

}
