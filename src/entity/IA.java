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
    }

    public void setGrid(Pawn[][] grid)
    {
        this. grid=grid;
    }
    
    public void setNemesis(Player Nemesis)
    {
        this.Nemesis=Nemesis;    
    }
    
    public int jouer(int profondeur)
    {
        
     int max = -10000;
     int tmp,maxi,maxj;
     int i,j;

     for(i=0;i<width;i++)
     {
        for(j=0;j<height;j++) if(grid[i][j]==null) break;
            grid[i][j] = new Pawn(this);
            tmp = Min(profondeur-1,i,j);
            if(tmp > max)
            {
                max = tmp;
                maxi = i;
                maxj = j;
            }
            grid[i][j] = null;
     }
     return i;

    }

    public int Min(int profondeur,int x, int y)
    {
        if(profondeur == 0 || TheEND(x,y))
     {
          return eval(x,y);
     }

     int min = 10000;
     int i,j,tmp;

     for(i=0;i<width;i++)
     {
        for(j=0;j<height;j++) if(grid[i][j]==null) break;
            grid[i][j] = new Pawn(this);
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
          return eval(x,y);
     }

     int max = -10000;
     int i,j,tmp;

     for(i=0;i<width;i++)
     {
          for(j=0;j<height;j++) if(grid[i][j]==null) break;
          grid[i][j] = new Pawn(Nemesis);
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
        int comptLine=0;
        int comptRow=0;
        int comptDiagonal=0;
        int comptReverseDiagonal=0;
        int i,j;
        if(TheEND(x,y))
        {
            if(this.getName().equals(grid[x][y].getOwner().getName())) return 10000;
            else return -10000;
        }
        for(i=0; i<width;i++)
        {
            
            for(j=0;j<height;j++) if(grid[i][j]==null) break;
            if(comptLine==0)
            {
                 eval+= this.evalLine(i, j, comptLine);
                 comptLine--;
            }
            else comptLine--;
            
            if(comptRow == 0)
            {
                eval+= this.evalRow(i, j, comptRow);
                comptRow--;
            }
            else comptRow--;
            
            if(comptDiagonal == 0)
            {
                eval+= this.evalDiagonal(i, j, comptDiagonal);
                comptDiagonal--;
            }
            else comptDiagonal--;
            
            if(comptReverseDiagonal == 0)
            {
                eval+= this.evalReverseDiagonal(i, j, comptReverseDiagonal);
                comptReverseDiagonal--;
            }
            else comptReverseDiagonal--;
            
            
            
            
        }
        return eval;
    }
    
    public int evalLine(int i, int j, int compt)
    {
       Player joueur=grid[i][j].getOwner();
       int x=i;
       int y= j;
       int eval=0;
       boolean freeL=false;
       boolean freeR=false;
       
       int Left;
        if(i>2)Left=3;
        else Left=i;
       
       int Right;
        if(width-i>3)Right=3;
        else Right=width-i;
       
       
       for(int a=0;a<Left;a++)
       {
           if(grid[x][y].getOwner().getName().equals(joueur.getName()))
           {
               compt++;
           }
           if(grid[x][y].getOwner()==null)
           {
               freeL=true;
               break;
           }
           if(!grid[x][y].getOwner().getName().equals(joueur.getName()))
           {
               break;
           }
           x--;
       }
       x=i;
       y= j;
       for(int a=0;a<Right;a++)
       {
           if(grid[x][y].getOwner().getName().equals(joueur.getName()))
           {
               compt++;
           }
           if(grid[x][y].getOwner()==null)
           {
               freeR=true;
               break;
           }
           if(!grid[x][y].getOwner().getName().equals(joueur.getName()))
           {
               break;
           }
           x++;
           
       }
       if(compt>=4) eval=10000;
       else
       {
            int coeff = freeL ? 1 : 0;
            coeff += freeR ? 1 : 0;
            eval=compt*1000+1000*coeff;
            
       }
       if(joueur.getName().equals(Nemesis.getName())) return (-1*eval);
       else return eval;
    }
    
    public int evalRow(int i, int j, int compt)
    {
       Player joueur=grid[i][j].getOwner();
       int x=i;
       int y= j;
       int eval=0;
       boolean freeL=false;
       boolean freeR=false;
       
       int Down;
        if(j>2)Down=3;
        else Down=i;
       
       int Up;
        if(width-j>3)Up=3;
        else Up=width-i;
       
       
       for(int a=0;a<Down;a++)
       {
           if(grid[x][y].getOwner().getName().equals(joueur.getName()))
           {
               compt++;
           }
           if(grid[x][y].getOwner()==null)
           {
               freeL=true;
               break;
           }
           if(!grid[x][y].getOwner().getName().equals(joueur.getName()))
           {
               break;
           }
           y--;
       }
       x=i;
       y= j;
       for(int a=0;a<Up;a++)
       {
           if(grid[x][y].getOwner().getName().equals(joueur.getName()))
           {
               compt++;
           }
           if(grid[x][y].getOwner()==null)
           {
               freeR=true;
               break;
           }
           if(!grid[x][y].getOwner().getName().equals(joueur.getName()))
           {
               break;
           }
           y++;
           
       }
       if(compt>=4) eval=10000;
       else
       {
            int coeff = freeL ? 1 : 0;
            coeff += freeR ? 1 : 0;
            eval=compt*1000+1000*coeff;
            
       }
       if(joueur.getName().equals(Nemesis.getName())) return (-1*eval);
       else return eval;
    }
    
    public int evalDiagonal(int i, int j, int compt)
    {
        Player joueur=grid[i][j].getOwner();
       int x=i;
       int y= j;
       int eval=0;
       boolean freeL=false;
       boolean freeR=false;
       
       
       for(int a=0;a<3;a++)
       {
           if(x>=0 && y>=0)
           {
           if(grid[x][y].getOwner().getName().equals(joueur.getName()))
           {
               compt++;
           }
           if(grid[x][y].getOwner()==null)
           {
               freeL=true;
               break;
           }
           if(!grid[x][y].getOwner().getName().equals(joueur.getName()))
           {
               break;
           }
           x--;
           y--;
           }
           else break;
       }
       x=i;
       y= j;
       for(int a=0;a<3;a++)
       {
           if(x<width && y<height)
           {
           if(grid[x][y].getOwner().getName().equals(joueur.getName()))
           {
               compt++;
           }
           if(grid[x][y].getOwner()==null)
           {
               freeR=true;
               break;
           }
           if(!grid[x][y].getOwner().getName().equals(joueur.getName()))
           {
               break;
           }
           x++;
           y++;
           }
           else break;
           
           
       }
       if(compt>=4) eval=10000;
       else
       {
            int coeff = freeL ? 1 : 0;
            coeff += freeR ? 1 : 0;
            eval=compt*1000+1000*coeff;
            
       }
       if(joueur.getName().equals(Nemesis.getName())) return (-1*eval);
       else return eval;
    }
    
    public int evalReverseDiagonal(int i, int j, int compt)
    {
        Player joueur=grid[i][j].getOwner();
       int x=i;
       int y= j;
       int eval=0;
       boolean freeL=false;
       boolean freeR=false;
       
       
       for(int a=0;a<3;a++)
       {
           if(x<width && y>=0 )
           {
           if(grid[x][y].getOwner().getName().equals(joueur.getName()))
           {
               compt++;
           }
           if(grid[x][y].getOwner()==null)
           {
               freeL=true;
               break;
           }
           if(!grid[x][y].getOwner().getName().equals(joueur.getName()))
           {
               break;
           }
           x++;
           y--;
           }
           else break;
       }
       x=i;
       y= j;
       for(int a=0;a<3;a++)
       {
           if(x>=0 && y<height)
           {
           if(grid[x][y].getOwner().getName().equals(joueur.getName()))
           {
               compt++;
           }
           if(grid[x][y].getOwner()==null)
           {
               freeR=true;
               break;
           }
           if(!grid[x][y].getOwner().getName().equals(joueur.getName()))
           {
               break;
           }
           x--;
           y++;
           }
           else break;
           
       }
       if(compt>=4) eval=10000;
       else
       {
            int coeff = freeL ? 1 : 0;
            coeff += freeR ? 1 : 0;
            eval=compt*1000+1000*coeff;
            
       }
       if(joueur.getName().equals(Nemesis.getName())) return (-1*eval);
       else return eval;
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
		if(cpt >= 4)
			for (int i = 0; i < cpt; i++)
				this.grid[startingX + i][y].setHighlighted(true);
		
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
		if(cpt >= 4)
			for (int i = 0; i < cpt; i++)
				this.grid[x][startingY + i].setHighlighted(true);
		
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
		if(cpt >= 4)
			for (int i = 0; i < cpt; i++)
				this.grid[startingX + i][y - (x - startingX) + i].setHighlighted(true);
		
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
					if(startingY > y - 1)
						startingY = y- i;
					cpt++;
				}
				else
					stopTopRight = true;
			}
		}
		
		// if win highlight
		if(cpt >= 4)
			for (int i = 0; i < cpt; i++)
				this.grid[x + (y - startingY) - i][startingY + i].setHighlighted(true);
		
		return (cpt >= 4);
	}
    
    
}
