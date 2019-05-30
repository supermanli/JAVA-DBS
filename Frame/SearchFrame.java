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
* @version ����ʱ�䣺2018��12��1�� ����7:50:37
* @filename Search.java
* @ClassName ������
* @Description ������
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
		frame.setTitle("��ѯ�˿͡�����Ӧ��");
		frame.setSize(800, 530);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);// ������ʾ
		
		JPanel pane = new JPanel();
		pane.setBorder(new EmptyBorder(200, 50, 200, 50));
		pane.setLayout(new BorderLayout());
		frame.add(pane);
		JPanel pane1 = new JPanel();
		pane.add(pane1,BorderLayout.CENTER);
		pane1.setLayout(new GridLayout(1, 2,50,50));
		cusbtn = new JButton("�˿ͱ�");
		supbtn = new JButton("��Ӧ�̹�Ӧ��");
		partbtn = new JButton("��Ʒ��Ϣ��");
		areabtn = new JButton("��Ӧ�̵�����");
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

