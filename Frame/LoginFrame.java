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
 * @version ����ʱ�䣺2018��11��30�� ����11:12:40
 * @filename Login.java
 * @ClassName LoginFrame
 * @Description ���ڽ���Ա��¼ϵͳ
 */
public class LoginFrame extends JFrame {
	final JTextField usernameField = new JTextField();
	final JPasswordField passwordField = new JPasswordField();
	final JButton loginBtn = new JButton("��¼");// ������¼��ť
	JButton closeBtn = new JButton("�ر�");// �����رհ�ť
	JFrame frame = new JFrame();
	public static int id=0;
//	public int sup_no;
	public LoginFrame() {
		init();
		addtion();
	}

	/**
	 * ��¼��幹�췽��
	 */
	public void init() {
		frame.setTitle("��Ʒ���׹������");// ���ô������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// ����رպ�ֹͣ����
		frame.setSize(360, 210);// ������
		frame.setVisible(true);// ����ɼ�
		frame.setLocationRelativeTo(null); // �Զ�����

		JPanel contentPane = new JPanel();// �������������
		// ���������ʹ�ÿ�Ⱥͼ�඼Ϊ5���صĿձ߿�
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		frame.add(contentPane);// ��������������뵽��������
		contentPane.setLayout(new BorderLayout());// ���������ʹ�ñ߽粼��
		JPanel centerPanel = new JPanel(new GridLayout(2, 1));
		contentPane.add(centerPanel, BorderLayout.CENTER);
		FlowLayout centerLayout = new FlowLayout(FlowLayout.CENTER);
		centerLayout.setHgap(10);// ������������10����
		JPanel aFloorPanel = new JPanel(centerLayout);
		centerPanel.add(aFloorPanel);// ��һ���������в������
		JLabel usernameLabel = new JLabel("�˺ţ�");// ������ǩ
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		aFloorPanel.add(usernameLabel);// ��һ�������ӱ�ǩ
		usernameField.setColumns(20);
		aFloorPanel.add(usernameField);
		JPanel bFloorPanel = new JPanel(centerLayout);
		centerPanel.add(bFloorPanel);
		JLabel pwdLabel = new JLabel("���룺");
		// ��ǩ���䷽ʽΪ����
		pwdLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bFloorPanel.add(pwdLabel);
		passwordField.setColumns(20);
		bFloorPanel.add(passwordField);
		JPanel southPanel = new JPanel(centerLayout);
		contentPane.add(southPanel, BorderLayout.SOUTH);
		southPanel.add(loginBtn);
		southPanel.add(closeBtn);
		frame.validate();//���½���
	}// LoginFrame()����

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
					String account = usernameField.getText().trim();// ��ȡ�˺�������е����ݣ�ȥ�����߿ո�
					String password1 = new String(passwordField.getPassword());// ��ȡ������е�����
					int j;
					for (j = 0; j < n; j++) {
						if (account.equals(s[j][0]) && password1.equals(s[j][2])) {
							if (s[j][1].equals("��ܾ�")) {
								new MainFrame();
								frame.dispose();// ���ٱ�����
								break;
							} else if (s[j][1].equals("�˿�")) {
								id=Integer.parseInt(account);
								new OneCusInfoFrame();
								frame.dispose();// ���ٱ�����
								break;
							} else if (s[j][1].equals("��Ӧ��")){
								id=Integer.parseInt(account);
								new OneSupInfoFrame();
								frame.dispose();// ���ٱ�����
								break;
							}
						} 
						else {
							if(j>n-1) {
								JOptionPane.showMessageDialog(null, "��������˺Ż������벻��ȷ");
							}
						}
					}
					if(j>n-1) 
						JOptionPane.showMessageDialog(null, "�޴��û�");

				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} finally {
					ConnectSql.closeDB(con, ps, rs);
				}
			}
		});
		// ���������ӻس��¼�
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loginBtn.doClick();
			}
		});

	}

	// }
}
