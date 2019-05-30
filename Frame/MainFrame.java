package Frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * @author super_lc
 * @version ����ʱ�䣺2018��11��27�� ����10:52:54
 * @filename MainFrame.java
 * @ClassName MainFrame
 * @Description ����������
 */
public class MainFrame extends JFrame {

	private JButton dea;
	private JButton update;
	private JButton search;
	private JButton exit;

	public MainFrame() {
		init();
		addtion();
	}

	private void init() {
		JFrame frame = new JFrame();
		JPanel pane = new JPanel();
		frame.setVisible(true); // ����ɼ�
		frame.setResizable(false); // ��С���ɱ�
		frame.setTitle("��Ʒ���׹������");
		frame.setSize(500, 600); // Frame�ĳ��Ϳ�
		frame.setLocationRelativeTo(null); // �Զ�����
		// frame.setBackground(new Color(100, 50, 25));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // �رշ�ʽ
		update = new JButton("�����ϵ");
		search = new JButton("��ѯ����");
		dea = new JButton("  ����    ");
		exit = new JButton("  �˳�    ");
		frame.setLayout(new BorderLayout()); // �����������Ǳ߿򲼾�
		frame.add(pane, BorderLayout.CENTER); // �������ӵ�frame������
		pane.setLayout(new GridLayout(2, 1));// ����pane��2*1�����񲼾� ��ť��ˮƽ�ʹ�ֱ���붼��50
		JPanel northPane = new JPanel();
		northPane.setLayout(new FlowLayout());
		northPane.setBorder(new EmptyBorder(200, 20, 10, 20));
		JPanel southPane = new JPanel();
		pane.add(northPane);
		pane.add(southPane);
		northPane.add(search);
		northPane.add(dea);
		southPane.add(update);
		southPane.add(exit);

	}

	private void addtion() {
		dea.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new DeaFrame();
			}
		});
		update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new SupplyAndDemand();
			}
		});

		search.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new SearchFrame();
			}
		});
		exit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}

}
