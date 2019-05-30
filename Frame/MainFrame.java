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
 * @version 创建时间：2018年11月27日 下午10:52:54
 * @filename MainFrame.java
 * @ClassName MainFrame
 * @Description 窗体主界面
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
		frame.setVisible(true); // 窗体可见
		frame.setResizable(false); // 大小不可变
		frame.setTitle("商品交易管理软件");
		frame.setSize(500, 600); // Frame的长和宽
		frame.setLocationRelativeTo(null); // 自动居中
		// frame.setBackground(new Color(100, 50, 25));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭方式
		update = new JButton("供求关系");
		search = new JButton("查询数据");
		dea = new JButton("  交易    ");
		exit = new JButton("  退出    ");
		frame.setLayout(new BorderLayout()); // 设置主界面是边框布局
		frame.add(pane, BorderLayout.CENTER); // 将面板添加到frame的中心
		pane.setLayout(new GridLayout(2, 1));// 设置pane是2*1的网格布局 按钮的水平和垂直距离都是50
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
