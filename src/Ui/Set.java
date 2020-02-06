package Ui;

import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class Set extends JPanel {
	public JRadioButton people = new JRadioButton("ÈË");
	public JRadioButton end = new JRadioButton("³ö¿Ú");
	public JRadioButton wall = new JRadioButton("Ç½");
	public JRadioButton magma = new JRadioButton("ÑÒ½¬");
	  
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
