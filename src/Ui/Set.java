package Ui;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Set extends JPanel {
	public JRadioButton people = new JRadioButton("��");
	public JRadioButton end = new JRadioButton("����");
	public JRadioButton wall = new JRadioButton("ǽ");
	public JRadioButton magma = new JRadioButton("�ҽ�");
	  
	public  ButtonGroup group = new ButtonGroup();
	
	public Set()
	{
		this.setLayout(new FlowLayout());
		group.add(people);
		group.add(end);
		group.add(wall);
		group.add(magma);
		this.add(people);
		this.add(end);
		this.add(wall);
		this.add(magma);
	}
	

}
