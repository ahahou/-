package Assistant;

import java.util.LinkedList;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class Algorithm {
		int [][] b;
		int [][] c;
		Position here;
		Position finish;
		Position old;
		Position []path;
		Position []offset;
		LinkedList<Position> q = new LinkedList<Position>();
		int flagSuccess = 0;
	
		
		public Algorithm(int[][] A , Position Start, Position End, Position []path)
		{
			b  = new int [A.length][A[0].length];
			for(int i = 0; i < A.length; i++)
			{
				for (int j= 0; j < A[0].length; j++)
				{
					b[i][j] = A[i][j];
				}
			}
			
			c  = new int [A.length][A[0].length];
			for(int i = 0; i < A.length; i++)
			{
				for (int j= 0; j < A[0].length; j++)
				{
					c[i][j] = A[i][j];
				}
			}
		
			here = new Position();
			old = new Position();
			here.x = Start.x;
			here.y = Start.y;
			old.x = here.x;
			old.y = here.y;
			
			this.finish = End;
			this.path  = path;
			
			offset = new Position[4];
			for(int i=0;i < 4;i++)
			{
				offset[i] = new Position();
			}
			offset[0].x = -1; offset[0].y = 0;   //up
			offset[1].x = 0;  offset[1].y = 1;   //right
			offset[2].x = 1;  offset[2].y = 0;   //down
			offset[3].x = 0 ; offset[3].y = -1;  //left
			
			flagSuccess = 0;
				
		}
		
		
		public Position[] findPath()
		{
			Position nbr = null;
			
			while(true)
			{
				for (int i = 0;i <4;i ++)					//四个方向的搜索
				{
					nbr = new Position(here.x + offset[i].x,here.y + offset[i].y);
					
					if (b[nbr.x][nbr.y] == 0 || b[nbr.x][nbr.y] == -1)
					{
						if(b[nbr.x][nbr.y] == -1)
						{
							b[nbr.x][nbr.y] = b[here.x][here.y]+1;
							c[nbr.x][nbr.y] = c[here.x][here.y]+1;
							flagSuccess = 1;
							break;
						}
						for(int j =0 ; j<4 ;j++)			//四个方向的搜索
						{
							Position panduan = new Position(nbr.x + offset[j].x,nbr.y + offset[j].y);
							if(b[panduan.x][panduan.y] == -2)
							{
								break;
							}
							else if (j==3)
							{
								
								b[nbr.x][nbr.y] = b[here.x][here.y] + 1;
								c[nbr.x][nbr.y] = c[here.x][here.y] + 1;
								q.add(nbr);
							}
						}
					}
				}
				if (flagSuccess == 1)			 //找到路径
				{
						break;
				}
				else if(q.size()!=0)				//继续搜索
				{
					
					here = q.removeFirst();
			
					if (c[here.x][here.y] != c[old.x][old.y])			//当节点的四个方向都判断玩才更新界面
					{
						Update(b);
						old.x = here.x;
						old.y = here.y;
						
					}
					else
					{
						old.x = here.x;
						old.y = here.y;
						
					}
				}
				else								//队列为空，无路径
					return null;
			}
			
			int pathLen = c[finish.x][finish.y];
			path = new Position [pathLen];
			
			here.x = finish.x;
			here.y = finish.y;
			
			for(int j = pathLen - 1; j >= 0; j--)
			{
				path[j] = here;
				for(int i = 0;i < 4;i++)
				{
					nbr = new Position(here.x + offset[i].x,here.y + offset[i].y);
	
					if (c[nbr.x][nbr.y]  == j)
					{
						break;
					}
				}
				here = nbr;
			}
			for(int i = 0; i< pathLen; i++)
			{
				System.out.println(path[i].toString());
			}
			return path;
		}
		
		public void Update(int [][]b)
		{
			Position n = new Position();
			Position h = new Position();
			LinkedList<Position>  queue = new LinkedList<Position>();
			for(int i = 0; i < b.length; i++)
			{
				for(int j = 0; j < b[0].length; j++)
				{
					if (b[i][j] == -2  || b[i][j] == -4)
						queue.add(new Position(i,j));
				}
			}
			while (queue.size()!=0)
			{
				h = queue.removeFirst();
				for(int i= 0;i <4;i++)
				{
					n.x = h.x+ offset[i].x;
					n.y = h.y + offset[i].y;
					if(b[n.x][n.y] != -1 && b[n.x][n.y] != -3 && b[n.x][n.y]!=-4)
					{
						if(b[n.x][n.y]>0)
						{
							b[n.x][n.y]=-4;
						}
						else
						b[n.x][n.y] = -2;
					}
						
				}
			}
		}
}
