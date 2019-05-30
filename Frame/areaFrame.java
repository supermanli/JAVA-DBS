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
* @version ����ʱ�䣺2019��3��20�� ����7:49:35
* @filename areaFrame.java
* @ClassName ������
* @Description ������
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

		frame.setTitle("��Ӧ�̵�����Ϣ��");
		frame.setSize(720, 500);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);// ������ʾ

		frame.add(tablePanel, "Center");
		tablePanel.setLayout(new BorderLayout());
		table = new JTable();
		table.setToolTipText("");

		tablePanel.add(scrollPane, "Center");
		String[] columnNames = { "��Ӧ�̺�", "��Ӧ����", "��ַ"};
		// �������ݿ�
		con = ConnectSql.ConnectSql();
		try {
			ps = con.prepareStatement(
					"select sup_number,sup_name,sup_address "
							+ "from supplier ");
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
					
					rs.next(); // ��ȡ��һ��
				}
				// �������װ����
				model = new DefaultTableModel(result, columnNames);
				table = new JTable(model);
				// ������뻬�����
				JScrollPane jsp = new JScrollPane(table);
				tablePanel.add(jsp);
				
				exitbtn = new JButton("�˳�");
				JPanel southPane = new JPanel();
				southPane.setLayout(new FlowLayout());// ʹ��������
				// FlowLayout f = new FlowLayout();//
				
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
		
		exitbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				frame.dispose();
			}
		});
	}

}

