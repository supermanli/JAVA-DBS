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
 * @version 创建时间：2018年12月1日 下午8:48:17
 * @filename UpdateInfoFrame.java
 * @ClassName UpdateInfoFrame
 * @Description 用
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

	// 初始化界面
	public void init() {
		// 设置标题
		frame.setTitle("供求关系表");
		frame.setSize(700, 500);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);// 居中显示
		frame.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		Container tablePanel = new JPanel();

		frame.add(tablePanel, "Center");
		tablePanel.setLayout(new BorderLayout());// 设置为边框布局
		table = new JTable();
		// table.setEnabled(false);
		table.setToolTipText("");
		JScrollPane scrollPane = new JScrollPane(table);// 滚动面板
		tablePanel.add(scrollPane, "Center");
		try {
			con = ConnectSql.ConnectSql();
			ps = con.prepareStatement(
					"select cus_number,a.part_number,cus_need,sup_number,sup_provide from cus_demand as a ,sup_provide as b where a.part_number = b.part_number;");
			rs = ps.executeQuery();
			rs.last();// 将光标置于最后一行
			int n = rs.getRow();// 获取行数
			// System.out.println(n);
			String[] columnNames = { "零件号", "顾客号", "需求量", "供应商号", "供应量" };
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
				JScrollPane jsp = new JScrollPane(table);
				tablePanel.add(jsp);
//				updbtn = new JButton("更新");
				cancelbtn = new JButton("退出");
				JPanel southPane = new JPanel();
				southPane.setLayout(new FlowLayout());// 使用流布局
				// FlowLayout f = new FlowLayout();//
//				southPane.add(updbtn);
				southPane.add(cancelbtn);
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
