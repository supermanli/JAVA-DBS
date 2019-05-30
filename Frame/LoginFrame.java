package Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ConnectMysql.ConnectSql;

/**
 * @author super_lc
 * @version 创建时间：2018年11月30日 上午11:12:40
 * @filename Login.java
 * @ClassName LoginFrame
 * @Description 用于交易员登录系统
 */
public class LoginFrame extends JFrame {
	final JTextField usernameField = new JTextField();
	final JPasswordField passwordField = new JPasswordField();
	final JButton loginBtn = new JButton("登录");// 创建登录按钮
	JButton closeBtn = new JButton("关闭");// 创建关闭按钮
	JFrame frame = new JFrame();
	public static int id=0;
//	public int sup_no;
	public LoginFrame() {
		init();
		addtion();
	}

	/**
	 * 登录面板构造方法
	 */
	public void init() {
		frame.setTitle("商品交易管理软件");// 设置窗体标题
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 窗体关闭后停止程序
		frame.setSize(360, 210);// 窗体宽高
		frame.setVisible(true);// 窗体可见
		frame.setLocationRelativeTo(null); // 自动居中

		JPanel contentPane = new JPanel();// 创建主容器面板
		// 主容器面板使用宽度和间距都为5像素的空边框
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.add(contentPane);// 将主容器面板载入到主容器中
		contentPane.setLayout(new BorderLayout());// 主容器面板使用边界布局
		JPanel centerPanel = new JPanel(new GridLayout(2, 1));
		contentPane.add(centerPanel, BorderLayout.CENTER);
		FlowLayout centerLayout = new FlowLayout(FlowLayout.CENTER);
		centerLayout.setHgap(10);// 布局中组件间隔10像素
		JPanel aFloorPanel = new JPanel(centerLayout);
		centerPanel.add(aFloorPanel);// 第一行面板放入中部面板中
		JLabel usernameLabel = new JLabel("账号：");// 创建标签
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		aFloorPanel.add(usernameLabel);// 第一行面板添加标签
		usernameField.setColumns(20);
		aFloorPanel.add(usernameField);
		JPanel bFloorPanel = new JPanel(centerLayout);
		centerPanel.add(bFloorPanel);
		JLabel pwdLabel = new JLabel("密码：");
		// 标签对其方式为居中
		pwdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bFloorPanel.add(pwdLabel);
		passwordField.setColumns(20);
		bFloorPanel.add(passwordField);
		JPanel southPanel = new JPanel(centerLayout);
		contentPane.add(southPanel, BorderLayout.SOUTH);
		southPanel.add(loginBtn);
		southPanel.add(closeBtn);
		frame.validate();//更新界面
	}// LoginFrame()结束

	public void addtion() {
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		loginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Connection con = null;
				PreparedStatement ps = null;
				ResultSet rs = null;
				con = ConnectSql.ConnectSql();
				try {
					ps = con.prepareStatement("select * from dealer");
					rs = ps.executeQuery();
					rs.last();
					int n = rs.getRow();
					rs.beforeFirst();
					String[][] s = new String[n][3];
					while (rs.next()) {
						for (int i = 0; i < n; i++) {
							s[i][0] = rs.getString("dea_number");
							s[i][1] = rs.getString("dea_name");
							s[i][2] = rs.getString("dea_password");
							rs.next();
						}
					}
					String account = usernameField.getText().trim();// 获取账号输入框中的内容，去掉两边空格
					String password1 = new String(passwordField.getPassword());// 获取密码框中的内容
					int j;
					for (j = 0; j < n; j++) {
						if (account.equals(s[j][0]) && password1.equals(s[j][2])) {
							if (s[j][1].equals("神盾局")) {
								new MainFrame();
								frame.dispose();// 销毁本窗体
								break;
							} else if (s[j][1].equals("顾客")) {
								id=Integer.parseInt(account);
								new OneCusInfoFrame();
								frame.dispose();// 销毁本窗体
								break;
							} else if (s[j][1].equals("供应商")){
								id=Integer.parseInt(account);
								new OneSupInfoFrame();
								frame.dispose();// 销毁本窗体
								break;
							}
						} 
						else {
							if(j>n-1) {
								JOptionPane.showMessageDialog(null, "您输入的账号或者密码不正确");
							}
						}
					}
					if(j>n-1) 
						JOptionPane.showMessageDialog(null, "无此用户");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					ConnectSql.closeDB(con, ps, rs);
				}
			}
		});
		// 密码面板添加回车事件
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginBtn.doClick();
			}
		});

	}

	// }
}
