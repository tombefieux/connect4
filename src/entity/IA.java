/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity ;


import Connect4.Config;
import entity.Player;
import java.util.List;
/**
 *
 * @author c
 */
public class IA extends Player {
    private Pawn[][] grid;
    protected int width;					
    protected int height;
    protected Player Nemesis;
    
    public IA(String name, PawnName pawnName, int width, int height)
    {
        super(name, pawnName);
        this.width=width;
        this.height=height;
        
        this.grid = new Pawn[width][];
	for (int i = 0 ; i < width; i++) {
			this.grid[i] = new Pawn[height];
	}
    }

    public void setGrid(int i, int j, Pawn pawn)
    {
        grid[i][j]=pawn;
        
    }
    
    public void setNemesis(Player Nemesis)
    {
        this.Nemesis=Nemesis;    
    }
    
    public int jouer(int profondeur)
    {
        
     int max = -100000;
     int tmp;
     int i,j;
     int maxi=0;
     for(i=0;i<width;i++)
     {
        for(j=height-1;j>=0;j--) if(grid[i][j]==null) break;
        if(j==0 && grid[i][j]!=null) continue;
        if(j==-1) continue;
            grid[i][j] = new Pawn(this);
            /*System.out.println(grid[i][j].getOwner().getName());
            System.out.println("Position : x = " + i);
            System.out.println("Position : y = " + j);
            */
            tmp = Min(profondeur-1,i,j);
            //System.out.println("WARNING: " + tmp +" i = " + i);
            if(tmp > max)
            {
                //System.out.println("plop");
                max = tmp;
                maxi = i;
            }
            grid[i][j] = null;
     }
     return maxi;

    }

    public int Min(int profondeur,int x, int y)
    {
        if(profondeur == 0 || TheEND(x,y))
     {
         //System.out.println("FIN");
          return eval(x,y);
     }

     int min = 100000;
     int i,j,tmp;

     for(i=0;i<width;i++)
     {
        for(j=height-1;j>=0;j--) if(grid[i][j]==null) break;
        if(j==0 && grid[i][j]!=null) continue;
        if(j==-1) continue;
            grid[i][j] = new Pawn(Nemesis);
            /*System.out.println(grid[i][j].getOwner().getName());
            System.out.println("Position : x = " + i);
            System.out.println("Position : y = " + j);
            */
            tmp = Max(profondeur-1, i, j);
            if(tmp < min)
            {
                 min = tmp;
            }
            grid[i][j] = null;

     }
     return min;
    }
    
    
    public int Max(int profondeur,int x, int y)
    {
        if(profondeur == 0 || TheEND(x,y))
     {
         // System.out.println("FIN");
          return eval(x,y);
          
     }

     int max = -100000;
     int i,j,tmp;

     for(i=0;i<width;i++)
     {
          for(j=height-1;j>=0;j--) if(grid[i][j]==null) break;
          if(j==0 && grid[i][j]!=null) continue;
          if(j==-1) continue;
          grid[i][j] = new Pawn(this);
          /*
          System.out.println(grid[i][j].getOwner().getName());
          System.out.println("Position : x = " + i);
            System.out.println("Position : y = " + j); */
            tmp = Min(profondeur-1, i, j);
            
            if(tmp > max)
            {
                 max = tmp;
            }
            grid[i][j] = null;
          
     }

     return max;
    }


    public int eval(int x, int y)
    {
        int eval=0;
        int i,j;
        if(TheEND(x,y))
        {
            if(this.getName().equals(grid[x][y].getOwner().getName())) return 10000;
          else return -10000;
        }
        for(i=0; i<width;i++)
        {
            
            for(j=height-1;j>=0;j--) if(grid[i][j]==null) break;
            if(j==0 && grid[i][j]!=null) continue;
             
            eval+= this.evalLine(i, j);
           
            /*
            System.out.println("Evalutaion");
            System.out.println("Position : x = " + i);
            System.out.println("Position : y = " + j);
            */
            eval+= this.evalRow(i, j);
            //System.out.println(eval);

            //eval+= this.evalDiagonal(i, j);

           
            //eval+= this.evalReverseDiagonal(i, j);

        }
        
        return eval;
    }
    
    public int evalLine(int i, int j)
    {
       
       int x=i;
       int y= j;
       int eval=0;
       int compt1=0;
       int compt2=0;
       
       //boolean freeL=false;
      // boolean freeR=false;
       boolean FirstStep=true;
       
       String joueur=null;
       String joueur2=null;
       
       int Left;
        if(i>3)Left=4;
        else Left=i;
       
       int Right;
        if(i<4)Right=4;
        else Right=width-1-i;
       
       
       for(int a=1;a<Left+1;a++)
       {
           if(x-a<0 || y<0) break;
           if(grid[x-a][y]==null)
           {
               //freeL=true;
               // System.out.println("Break Left");
               break;
           }
           if(grid[x-a][y].getOwner()!=null && FirstStep)
           {
               joueur=grid[x-a][y].getOwner().getName();
               FirstStep=false;
           }
           
           if(grid[x-a][y].getOwner().getName().equals(joueur))
           {
               compt1++;
           }
           
           if(!grid[x-a][y].getOwner().getName().equals(joueur))
           {
               break;
           }
       }
       FirstStep=true;
       for(int a=1;a<Right;a++)
       {   
           if(x+a>=width || y<0) break;
           if(grid[x+a][y]==null)
           {
               //freeR=true;
               // System.out.println("Break Right");
               break;
           }
           if(!FirstStep && grid[x+a][y].getOwner().getName().equals(grid[x+a-1][y].getOwner().getName()))
           {
               compt2++;
           }
           if(grid[x+a][y].getOwner()!=null && FirstStep && !grid[x+a][y].getOwner().getName().equals(joueur))
           {
               joueur2=grid[x+a][y].getOwner().getName();
               compt2++;
               FirstStep=false;
           }  
           if(FirstStep && grid[x+a][y].getOwner().getName().equals(joueur))
           {
               compt2++;
               FirstStep=false;
           }
       }
       
       if(joueur!=null)
       {
           //System.out.println(joueur);
            int compt = compt1 + compt2;
            if(compt>=4) eval = 10000; 
            else{
                
                eval=(compt*compt)*100;
            }
            if(joueur.equals(Nemesis.getName())) return (-1*eval);
            else return eval;
       }
       if(joueur2!=null)
       {
           // System.out.println(joueur);
            int compt = compt1 + compt2;
            if(compt>=4) eval = 10000; 
            else{
                
                eval=(compt*compt)*100;
            }
            if(joueur2.equals(Nemesis.getName())) return (-1*eval);
            else return eval;
       }
       if(joueur!=null && joueur2!=null)
       {
           eval=(compt1*compt1*compt1)*100-(compt2*compt2*compt2)*100;
           if(compt1 >= 4)
           {
               if(this.getName().equals(joueur)) return 10000;
               else return -10000;
           }
           if(compt2 >= 4)
           {
               if(this.getName().equals(joueur2)) return 10000;
               else return -10000;
           }
           
       }
       else eval=0;
       return eval;
    }
    
    public int evalRow(int i, int j)
    {

       int x=i;
       int y= j;
       int eval=0;
       int compt1=0;
       int compt2=0;
       
       //boolean freeL=false;
       //boolean freeR=false;
       boolean FirstStep=true;
       
       String joueur=null;
       String joueur2=null;
       
       int Down;
        if(j<4)Down=4;
        else Down=height-1-j;
       
       int Up;
        if(j>3)Up=4;
        else Up=j;
       
       
       for(int a=1;a<Up;a++)
       {
           if(grid[x][y-a]==null)
           {
               //freeL=true;
               break;
           }
           
           if(grid[x][y-a].getOwner()!=null && FirstStep)
           {
               joueur=grid[x][y-a].getOwner().getName();
               FirstStep=false;
           }
           
           if(grid[x][y-a].getOwner().getName().equals(joueur))
           {
               compt1++;
           }
           
           if(!grid[x][y-a].getOwner().getName().equals(joueur))
           {
               break;
           }
           
       }
       
       FirstStep=true;
       for(int a=1;a<Down;a++)
       {
           if(grid[x][y+a]==null)
           {
               //freeR=true;
               break;
           }
           if(!FirstStep && grid[x][y+a].getOwner().getName().equals(grid[x][y+a-1].getOwner().getName()))
           {
               compt2++;
           }
           if(grid[x][y+a].getOwner()!=null && FirstStep && !grid[x][y+a].getOwner().getName().equals(joueur))
           {
               joueur2=grid[x][y+a].getOwner().getName();
               compt2++;
               FirstStep=false;
           }  
           if(FirstStep && grid[x][y+a].getOwner().getName().equals(joueur))
           {
               compt2++;
               FirstStep=false;
           }
           
       }
       
       
       if(joueur!=null)
       {
            int compt = compt1 + compt2;
            if(compt>=4) eval = 10000; 
            else{
                
                eval=(compt*compt)*1000;
            }
            if(joueur.equals(Nemesis.getName())) return (-1*eval);
            else return eval;
       }
       if(joueur2!=null)
       {
            int compt = compt1 + compt2;
            if(compt>=4) eval = 10000; 
            else{
                
                eval=(compt*compt)*1000;
            }
            if(joueur2.equals(Nemesis.getName())) return (-1*eval);
            else return eval;
       }
       if(joueur!=null && joueur2!=null)
       {
           
           eval=(compt1*compt1)*1000-(compt2*compt2)*1000;
           if(compt1 >= 4)
           {
               if(this.getName().equals(joueur)) return 10000;
               else return -10000;
           }
           if(compt2 >= 4)
           {
               if(this.getName().equals(joueur2)) return 10000;
               else return -10000;
           }
           
       }
       else eval=0;
       return eval;
    }
    
    public int evalDiagonal(int i, int j)
    {

       int x=i;
       int y= j;
       int eval=0;
       int compt1=0;
       int compt2=0;
       
       //boolean freeL=false;
       //boolean freeR=false;
       boolean FirstStep = true;
       
       String joueur=null;
       String joueur2=null;
       
       for(int a=1;a<4;a++)
       {
           if(x+a>=width || y-a<0) break;
           if(grid[x+a][y-a]==null)
           {
               //freeL=true;
               break;
           }
           if(grid[x+a][y-a].getOwner()!=null && FirstStep)
           {
               joueur=grid[x+a][y-a].getOwner().getName();
               FirstStep=false;
           }
          
           if(grid[x+a][y-a].getOwner().getName().equals(joueur))
           {
               compt1++;
           }
           
           if(!grid[x+a][y-a].getOwner().getName().equals(joueur))
           {
               break;
           }
           
       }
      ;
       FirstStep=true;
       for(int a=1;a<4;a++)
       {
           if(x-a<0 || y+a>=height) break;
           if(grid[x-a][y+a]==null)
           {
               //freeR=true;
               break;
           }
           if(!FirstStep && grid[x-a][y+a].getOwner().getName().equals(grid[x-a+1][y+a-1].getOwner().getName()))
           {
               compt2++;
           }
           if(grid[x-a][y+a].getOwner()!=null && FirstStep && !grid[x-a][y+a].getOwner().getName().equals(joueur))
           {
               joueur2=grid[x-a][y+a].getOwner().getName();
               compt2++;
               FirstStep=false;
           }  
           if(FirstStep && grid[x-a][y+a].getOwner().getName().equals(joueur))
           {
               compt2++;
               FirstStep=false;
           }
           
           
       }
       
       if(joueur!=null)
       {
            int compt = compt1 + compt2;
            if(compt>=4) eval = 10000; 
            else{
                
                eval=(compt*compt)*1000;
            }
            if(joueur.equals(Nemesis.getName())) return (-1*eval);
            else return eval;
       }
       if(joueur2!=null)
       {
            int compt = compt1 + compt2;
            if(compt>=4) eval = 10000; 
            else{
                
                eval=(compt*compt)*1000;
            }
            if(joueur2.equals(Nemesis.getName())) return (-1*eval);
            else return eval;
       }
       if(joueur!=null && joueur2!=null)
       {
           
           eval=(compt1*compt1)*1000-(compt2*compt2)*1000;
           if(compt1 >= 4)
           {
               if(this.getName().equals(joueur)) return 10000;
               else return -10000;
           }
           if(compt2 >= 4)
           {
               if(this.getName().equals(joueur2)) return 10000;
               else return -10000;
           }
           
       }
       else eval=0;
       return eval;
    }
    
    public int evalReverseDiagonal(int i, int j)
    {

       int x=i;
       int y= j;
       int eval=0;
       int compt1=0;
       int compt2=0;
       
       //boolean freeL=false;
       //boolean freeR=false;
       boolean FirstStep=true;
       
       String joueur=null;
       String joueur2=null;
       
       for(int a=1;a<4;a++)
       {
           if(x+a>=width || y+a>=height) break;
           if(grid[x+a][y+a]==null)
           {
               //freeL=true;
               break;
           }
           if(grid[x+a][y+a].getOwner()!=null && FirstStep)
           {
               joueur=grid[x+a][y+a].getOwner().getName();
               FirstStep=false;
           }
           
           if(grid[x+a][y+a].getOwner().getName().equals(joueur))
           {
               compt1++;
           }
           
           if(!grid[x+a][y+a].getOwner().getName().equals(joueur))
           {
               break;
           }
          
       }

       FirstStep=true;
       for(int a=1;a<4;a++)
       {
           if(x-a<0 || y-a<0) break;
           if(grid[x-a][y-a]==null)
           {
               //freeR=true;
               break;
           }
           if(!FirstStep && grid[x-a][y-a].getOwner().getName().equals(grid[x-a][y-a+1].getOwner().getName()))
           {
               compt2++;
           }
           if(grid[x-a][y-a].getOwner()!=null && FirstStep && !grid[x-a][y-a].getOwner().getName().equals(joueur))
           {
               joueur2=grid[x-a][y-a].getOwner().getName();
               compt2++;
               FirstStep=false;
           }  
           if(FirstStep && grid[x-a][y-a].getOwner().getName().equals(joueur))
           {
               compt2++;
               FirstStep=false;
           }
           
       }
       
      if(joueur!=null)
       {
            int compt = compt1 + compt2;
            if(compt>=4) eval = 10000; 
            else{
                
                eval=(compt*compt)*1000;
            }
            if(joueur.equals(Nemesis.getName())) return (-1*eval);
            else return eval;
       }
       if(joueur2!=null)
       {
            int compt = compt1 + compt2;
            if(compt>=4) eval = 10000; 
            else{
                
                eval=(compt*compt)*1000;
            }
            if(joueur2.equals(Nemesis.getName())) return (-1*eval);
            else return eval;
       }
       if(joueur!=null && joueur2!=null)
       {
           
           eval=(compt1*compt1)*1000-(compt2*compt2)*1000;
           if(compt1 >= 4)
           {
               if(this.getName().equals(joueur)) return 10000;
               else return -10000;
           }
           if(compt2 >= 4)
           {
               if(this.getName().equals(joueur2)) return 10000;
               else return -10000;
           }
           
       }
       else eval=0;
       return eval;
    }
    
    
    public boolean TheEND(int x, int y)
    {
        return (checkLine(x, y) || checkRow(x, y)  || checkDiagonal(x,y) || checkReversedDiagonal(x, y));
    }
    
    public boolean checkLine(int x, int y) {
		int cpt = 1;
		int startingX = x;
		boolean stopLeft = false, stopRight = false;
		Player owner = this.grid[x][y].getOwner();
		
		for (int i = 1; i <= 3 && (!stopLeft || !stopRight); i++) {
			
			// check on the left of the pawn
			if(x - i >= 0 && !stopLeft) {
				if(this.grid[x - i][y] != null && this.grid[x - i][y].getOwner().equals(owner)) {
					if(startingX > x - i)
						startingX = x - i;
					cpt++;
				}
					
				else
					stopLeft = true;
			}
			
			// check on the right of the pawn
			if(x + i < Config.GRID_WIDTH && !stopRight) {
				if(this.grid[x + i][y] != null && this.grid[x + i][y].getOwner().equals(owner))
					cpt++;
				else
					stopRight = true;
			}
		}
	
		// if win highlight
		//if(cpt >= 4)
			//for (int i = 0; i < cpt; i++)
				//this.grid[startingX + i][y].setHighlighted(true);
		
		return (cpt >= 4);
	}

	/**
	 * This function checks if there's a connect4 with the pawn at (x, y) in row.
	 * This function also highlights the winning pawns.
	 * @param x
	 * @param y
	 * @return if there's a connect 4
	 */
	public boolean checkRow(int x, int y) {
		int cpt = 1;
		int startingY = y;
		boolean stopTop = false, stopBottom = false;
		Player owner = this.grid[x][y].getOwner();
		
		for (int i = 1; i <= 3 && (!stopTop || !stopBottom); i++) {
			
			// check on the top of the pawn
			if(y - i >= 0 && !stopTop) {
				if(this.grid[x][y - i] != null && this.grid[x][y - i].getOwner().equals(owner)) {
					if(startingY > y - i)
						startingY = y - i;
					cpt++;
				}
				else
					stopTop = true;
			}
			
			// check on the bottom of the pawn
			if(y + i < Config.GRID_HEIGHT && !stopBottom) {
				if(this.grid[x][y + i] != null && this.grid[x][y + i].getOwner().equals(owner))
					cpt++;
				else
					stopBottom = true;
			}
		}
		
		// if win highlight
		//if(cpt >= 4)
		//	for (int i = 0; i < cpt; i++)
		//		this.grid[x][startingY + i].setHighlighted(true);
		
		return (cpt >= 4);
	}
	
	/**
	 * This function checks if there's a connect4 with the pawn at (x, y) in diagonal.
	 * This function also highlights the winning pawns.
	 * @param x
	 * @param y
	 * @return if there's a connect 4
	 */
	public boolean checkDiagonal(int x, int y) {
		int cpt = 1;
		int startingX = x;
		boolean stopTopLeft = false, stopBottomRight = false;
		Player owner = this.grid[x][y].getOwner();
		
		for (int i = 1; i <= 3 && (!stopTopLeft || !stopBottomRight); i++) {
			
			// check on the top left of the pawn
			if(x - i >= 0 && y - i >= 0 && !stopTopLeft) {
				if(this.grid[x - i][y - i] != null && this.grid[x - i][y - i].getOwner().equals(owner)) {
					if(startingX > x - i)
						startingX = x - i;
					cpt++;
				}
				else
					stopTopLeft = true;
			}
			
			// check on the bottom right of the pawn
			if(x + i < Config.GRID_WIDTH && y + i < Config.GRID_HEIGHT && !stopBottomRight) {
				if(this.grid[x + i][y + i] != null && this.grid[x + i][y + i].getOwner().equals(owner))
					cpt++;
				else
					stopBottomRight = true;
			}
		}
		
		// if win highlight
		//if(cpt >= 4)
		//	for (int i = 0; i < cpt; i++)
		//		this.grid[startingX + i][y - (x - startingX) + i].setHighlighted(true);
		
		return (cpt >= 4);
	}
	
	/**
	 * This function checks if there's a connect4 with the pawn at (x, y) in the reversed diagonal.
	 * This function also highlights the winning pawns.
	 * @param x
	 * @param y
	 * @return if there's a connect 4
	 */
	public boolean checkReversedDiagonal(int x, int y) {
		int cpt = 1;
		int startingY = y;
		boolean stopBottomLeft = false, stopTopRight = false;
		Player owner = this.grid[x][y].getOwner();
		
		for (int i = 1; i <= 3 && (!stopBottomLeft || !stopTopRight); i++) {
			
			// check on the bottom left of the pawn
			if(x - i >= 0 && y + i < Config.GRID_HEIGHT && !stopBottomLeft) {
				if(this.grid[x - i][y + i] != null && this.grid[x - i][y + i].getOwner().equals(owner))
					cpt++;
				else
					stopBottomLeft = true;
			}
			
			// check on the top right of the pawn
			if(x + i < Config.GRID_WIDTH && y - i >= 0 && !stopTopRight) {
				if(this.grid[x + i][y - i] != null && this.grid[x + i][y - i].getOwner().equals(owner)) {
					if(startingY > y - i)
						startingY = y- i;
					cpt++;
				}
				else
					stopTopRight = true;
			}
		}
		
		// if win highlight
		//if(cpt >= 4)
		//	for (int i = 0; i < cpt; i++)
		//		this.grid[x + (y - startingY) - i][startingY + i].setHighlighted(true);
		
		return (cpt >= 4);
	}
    
    
}
