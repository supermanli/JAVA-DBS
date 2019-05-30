package Frame;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
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

/**
 * @author super_lc
 * @version ����ʱ�䣺2018��12��1�� ����8:48:17
 * @filename UpdateInfoFrame.java
 * @ClassName UpdateInfoFrame
 * @Description ��
 */
public class SupplyAndDemand extends JFrame {
	private DefaultTableModel model;
	private JTable table;
	private JButton updbtn;
	private JButton cancelbtn;
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	JDialog frame = new JDialog();

	public SupplyAndDemand() {
		init();
		action();
	}

	// ��ʼ������
	public void init() {
		// ���ñ���
		frame.setTitle("�����ϵ��");
		frame.setSize(700, 500);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);// ������ʾ
		frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		Container tablePanel = new JPanel();

		frame.add(tablePanel, "Center");
		tablePanel.setLayout(new BorderLayout());// ����Ϊ�߿򲼾�
		table = new JTable();
		// table.setEnabled(false);
		table.setToolTipText("");
		JScrollPane scrollPane = new JScrollPane(table);// �������
		tablePanel.add(scrollPane, "Center");
		try {
			con = ConnectSql.ConnectSql();
			ps = con.prepareStatement(
					"select cus_number,a.part_number,cus_need,sup_number,sup_provide from cus_demand as a ,sup_provide as b where a.part_number = b.part_number;");
			rs = ps.executeQuery();
			rs.last();// ������������һ��
			int n = rs.getRow();// ��ȡ����
			// System.out.println(n);
			String[] columnNames = { "�����", "�˿ͺ�", "������", "��Ӧ�̺�", "��Ӧ��" };
			String[][] result = new String[n][5];
			rs.beforeFirst(); // ���ص�һ��
			// �������ת��Ϊ����
			while (rs.next()) {
				for (int i = 0; i < n; i++) {
					result[i][0] = rs.getString("part_number");
					result[i][1] = rs.getString("cus_number");
					result[i][2] = rs.getString("cus_need");
					result[i][3] = rs.getString("sup_number");
					result[i][4] = rs.getString("sup_provide");
					rs.next(); // ��ȡ��һ��
				}
				// �������װ����
				model = new DefaultTableModel(result, columnNames);
				table = new JTable(model);
				// ������뻬�����
				JScrollPane jsp = new JScrollPane(table);
				tablePanel.add(jsp);
//				updbtn = new JButton("����");
				cancelbtn = new JButton("�˳�");
				JPanel southPane = new JPanel();
				southPane.setLayout(new FlowLayout());// ʹ��������
				// FlowLayout f = new FlowLayout();//
//				southPane.add(updbtn);
				southPane.add(cancelbtn);
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
		cancelbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				// System.exit(0);
				frame.dispose();
			}
		});

	}

}
