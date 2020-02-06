package Ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Assistant.Algorithm;
import Assistant.Position;

public class Maze extends JPanel implements MouseListener,Runnable {

	public int[][] maze;

	public int row; // ����
	public int col; // ����
	int flagPeople; // �����˵ĸ����Ƿ�Ψһ
	int flagEnd;   // ���ý�������Ψһ
	int flagAdd = 1;    //�����Ƿ���Լ�����ӵ�ͼ
	int enAbleAdd = 1; // �Ƿ���Լ������е�ͼ�ϵĶ������
	int enAbleFlash = 0; // �Ƿ�ִ��ˢ��
	public int x = 0; // �ı�ĵ�ͼ����xλ��
	public int y = 0; // �ı�ĵ�ͼ����yλ��
	JFrame j = null; // ����
	Set p = null; // �������
	Position start; // ��ʼ
	Position end; // ������
	Position[] path;			//��·�����б���
	Algorithm al;     //�㷨��
	Toolkit tool = getToolkit();		//��ͼ
	

	public Maze(JFrame j, Set p) // ���캯��
	{
		this.j = j;
		this.p = p;
		row = 12;
		col = 16;
		initMaze();
		addMouseListener(this);

	}

	public void initMaze() // ��ʼ���Թ�
	{
		start = null;
		end = null;
		flagPeople = 1;
		flagEnd = 1;
		flagAdd = 1;
		maze = new int[row + 2][col + 2];
		for (int i = 0; i < col + 2; i++) {
			maze[0][i] = -3;
			maze[row + 1][i] = -3;
		}
		for (int i = 0; i < row + 2; i++) {
			maze[i][col + 1] = -3;
			maze[i][0] = -3;
		}
		
	}

	public void setRC(int row, int col) // �����к���
	{

		enAbleAdd = 1;
		this.row = row;
		this.col = col;
		initMaze();
		j.repaint();
		j.setSize(col * 30 + 20, row * 30 + 135);
	}

	public void paint(Graphics g) // ��ͼ
	{
		Graphics2D g_2d = (Graphics2D) g;
		int width = col * 30;
		int height = row * 30;

		g_2d.setColor(Color.WHITE);
		g_2d.fillRect(0, 0, width, height);

		for (int i = 1; i < row + 1; i++) { // ������Ӧ����
			for (int h = 1; h < col + 1; h++) {
				if (maze[i][h] == 1) {
					g_2d.setColor(Color.RED);
					g_2d.fillRect((h - 1) * 30, (i - 1) * 30, 30, 30);
				} else if (maze[i][h] == -1) {
//					g_2d.setColor(Color.YELLOW);
//					g_2d.fillRect((h - 1) * 30, (i - 1) * 30, 30, 30);
					
					Image image = tool.getImage("img/chukou.hpg");
					g_2d.drawImage(image,(h-1)*30,(i-1)*30,30,30,j);
				} else if (maze[i][h] == -2) {
//					g_2d.setColor(Color.RED);
//					g_2d.fillRect((h - 1) * 30, (i - 1) * 30, 30, 30);
					
					Image image = tool.getImage("img/fire.gif");
					g_2d.drawImage(image,(h-1)*30,(i-1)*30,30,30,j);
				} else if (maze[i][h] == -3) {
//					g_2d.setColor(Color.GRAY);
//					g_2d.fillRect((h - 1) * 30, (i - 1) * 30, 30, 30);Toolkit tool = getToolkit();
				
					Image image = tool.getImage("img/wall.jpg");
					g_2d.drawImage(image,(h-1)*30,(i-1)*30,30,30,j);
				}  else if (maze[i][h] == -4) {
					g_2d.setColor(Color.PINK);
					g_2d.fillRect((h - 1) * 30, (i - 1) * 30, 30, 30);
				}
			}
		}
		g_2d.setColor(Color.BLACK);
		for (int i = 0; i <= col; i++) { // �����Թ�����
			g_2d.drawLine(i * 30, 0, i * 30, height);
		}
		for (int i = 0; i <= row; i++) {
			g_2d.drawLine(0, i * 30, width, i * 30);
		}
		
		if(start != null)
		{
			Toolkit tool = getToolkit();
			Image image = tool.getImage("img/people.jpg");
			g_2d.drawImage(image,(start.y-1)*30,(start.x-1)*30,30,30,j);
		}
		
		if(end != null)
		{
			Toolkit tool = getToolkit();
			Image image = tool.getImage("img/chukou.jpg");
			g_2d.drawImage(image,(end.y-1)*30,(end.x-1)*30,30,30,j);
		}
	}
	
	public void test()
	{
		al = new Algorithm(maze,start,end,path);
		path = al.findPath();
		if(path == null)
		{
			
			JOptionPane.showMessageDialog(this,"�ܲ���ȥ�ˣ����˱��ذɣ�С����","��ʾ",JOptionPane.WARNING_MESSAGE);
		}
		else
		{
			Thread a = new Thread(this);
			a.start();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) { // ������¼�

		y = e.getX() / 30 + 1;
		x = e.getY() / 30 + 1;
		if(x<row+1 && y<col+1)
		{
			if(flagAdd == 1)
			{
				if (maze[x][y] == 0) {
					if (p.people.isSelected()) {
						if (flagPeople == 1) {
							maze[x][y] = 1;
							flagPeople = 0;
							start = new Position(x, y);
							this.j.repaint();
							
						}
					} else if (p.wall.isSelected()) {
						maze[x][y] = -3;
						this.j.repaint();
						

					} else if (p.magma.isSelected()) {
						maze[x][y] = -2;
						this.j.repaint();
						

					} else if (p.end.isSelected()) {
						if (flagEnd == 1) {
							maze[x][y] = -1;
							flagEnd = 0;
							end = new Position(x, y);
							this.j.repaint();
							
						}
					}
				}
			}
		}
	

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
			for (int i = 1 ;i < path.length; i++)
			{
				maze[path[i].x][path[i].y]= 1;
				this.j.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(i == path.length-1)
					break;
				al.Update(maze);
				this.j.repaint();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
	
		
	}

}
