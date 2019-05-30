package Frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
* @author super_lc
* @version 创建时间：2018年12月1日 下午7:50:37
* @filename Search.java
* @ClassName 类名称
* @Description 类描述
*/
public class SearchFrame extends JFrame{
	private JButton cusbtn;
	private JButton supbtn;
	private JButton partbtn;
	private JButton areabtn;
	public SearchFrame() {
		init();
		action();
	}
	private void init() {
		JFrame frame = new JFrame();
		frame.setTitle("查询顾客——供应商");
		frame.setSize(800, 530);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);// 居中显示
		
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(200, 50, 200, 50));
		pane.setLayout(new BorderLayout());
		frame.add(pane);
		JPanel pane1 = new JPanel();
		pane.add(pane1,BorderLayout.CENTER);
		pane1.setLayout(new GridLayout(1, 2,50,50));
		cusbtn = new JButton("顾客表");
		supbtn = new JButton("供应商供应表");
		partbtn = new JButton("商品信息表");
		areabtn = new JButton("供应商地区表");
		pane1.add(cusbtn);
		pane1.add(supbtn);
		pane1.add(partbtn);
		pane1.add(areabtn);
		}
	private void action() {
		cusbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new CusInfoFrame();
			}
		});
		supbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new SupInfoFrame();
			}
		});
		partbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new PartInfoFrame();
			}
		});
		areabtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new areaFrame();
			}
		});
	}
}

