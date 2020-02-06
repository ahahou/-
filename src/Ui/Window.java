package Ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Window extends JFrame implements ActionListener{
		public int x;
		public int y;
		public JButton start = new JButton("Start");
		public JMenuBar menubar = new JMenuBar();
		public JMenu menu = new JMenu("菜单");
		public JMenuItem item2 = new JMenuItem("再来一次");
		public JMenuItem item1 = new JMenuItem("设置");					
		
		public Set set=new Set();
		public Maze maze=new Maze(this,set);
		MyJDialog dialog = new MyJDialog(this,maze);
		
		public int flagStart;
		
		public Window()
		{
			
			init();
			setLocation(200, 300);
			setSize(500,500);
			
			setVisible(true);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
		
		public void init(){
			x = 12;
			y = 16;
			flagStart = 1;
			menu.add(item1);
			menu.add(item2);
			menubar.add(menu);
			setJMenuBar(menubar);
			
			this.setLayout(new BorderLayout());
			add(set,BorderLayout.NORTH);
			add(maze,BorderLayout.CENTER);
			add(start,BorderLayout.SOUTH);
			dialog.setModal(true);
			dialog.setSize(200, 150);
			
			item1.addActionListener(new ActionListener()    
			{

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					dialog.setVisible(true);
					
				}});
			
			item2.addActionListener(new ActionListener()
			{

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
						maze.setRC(x,y);
						flagStart = 1;
				}
				
			});
			
			start.addActionListener(this);
			
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {          //开始按钮监听
			// TODO Auto-generated method stub
			if(maze.start == null || maze.end == null)
			{
				JOptionPane.showMessageDialog(this, "未设置开始和出口", "提示", JOptionPane.WARNING_MESSAGE);
			}
			else
			{	
				if(flagStart==1)
				{
					maze.test();
					flagStart = 0;
					maze.flagAdd = 0;
				
				}
				
				
			}
			
		}
		
		 
		public class MyJDialog extends JDialog implements ActionListener{               //自定义对话框
			JTextField row;
			JTextField col;
			JButton yes = new JButton("确认");
			JFrame f;
			
			MyJDialog(JFrame f ,Maze maze){
				init();
				setLocationRelativeTo(null);
				setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
			}
			
			public void init()
			{
				
				this.setLayout(new FlowLayout());
				this.setTitle("设置相应参数");
				row = new JTextField(10);
				col = new JTextField(10);
				add(new JLabel("输入行"));
				add(row);
				add(new JLabel("输入列"));
				add(col);
				add(yes);
				yes.addActionListener(this);

				
				
			}
			

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int a = Integer.parseInt(row.getText().toString());
				int b = Integer.parseInt(col.getText().toString());
				maze.setRC(a,b);
				x=a; y=b;
				flagStart= 1;
				this.dispose();                       //关闭对话框
			}
				
		}	
		
}
