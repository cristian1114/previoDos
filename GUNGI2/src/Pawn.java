import java.util.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *  Pawn Class
 *  <p>
 *  A Pawn is a specific piece of the game
 *  The Pawn is the father of every other piece in the game
 *  Other pieces override the checkMoves method
 */
public class Pawn extends GameObject{
	private static int id = 0;
	private Vector2D pos;
	private Vector2D row_col = null;	//vector row , column
	private PieceView view;
	public int player_color; //0 means blue! 1 means red!
    public int in_play = 0; // 1 si esta en juego, 0 si esta en la mano
    public int exp_effect = 0;  //si esta siendo afectado por el efecto de expansion de rango
    public int valueIA = 1;     //catapult/fortress=0;pawn =1;bow=2;spy=3;samurai=4;capitan=5;Dragon/prodigio=6; commander=7;
    public int creator = 0;     //mano en la que se origino
    public int changedHand = 0; //si se encuentra en una mano diferente a la con que se origino
    public BoardPanel own_bp = null; //panel en el que se encuentra la ficha

	
    /**
     *  Pawn Constructor 
     *
     *  @param xx x position
     *  @param yy y position
     *  @param rc Vector with row and column position
     *  @param pl_color player color
     */
    public Pawn(int xx, int yy, Vector2D rc, int pl_color){
		super(id++);
		pos = new Vector2D(xx,yy);
		row_col = rc;
		player_color = pl_color;
        creator = pl_color;
		view = new PieceView(this, pl_color);
	}

	public Pawn(int xx, int yy, int rr, int cc, int pl_color){
		super(id++);
		pos = new Vector2D(xx,yy);
		row_col = new Vector2D(rr,cc);
		player_color = pl_color;
        creator = pl_color;
		view = new PieceView(this, pl_color);
	}

    /**
    *  Returns the position of this piece
    *  @return position 
    */
	public Vector2D getPosition(){
		return pos;
	}

    /**
    *  Sets the position for this piece
    *  @param newpos Vector with the new position to set
    */
	public void setPosition(Vector2D newpos){
		pos = newpos;
	}

    /**
    *  Returns the row of this piece
    *  @return row
    */
	public int getRow(){
		if (row_col==null) {return -1;}
		return row_col.getX();
	}

    /**
    *  Returns the column of this piece
    *  @return row
    */
	public int getColumn(){
		if (row_col==null) {return -1;}
		return row_col.getY();
	}

    /**
    *  Sets the row for this piece
    *  @param rr the new row to set
    */
	public void setRow(int rr){
		row_col.setX(rr);
	}

    /**
    *  Sets the column for this piece
    *  @param cc the new column to set
    */
	public void setColumn(int cc){
		row_col.setY(cc);
	}

    /**
    *   Returns the depth of the piece
    *  
    *   @return depth
    */
	public int getDepth(){
		return depth;
	}

	public void paintView(Graphics2D gra) {
        view.paint(gra);
    }

    /**
    * Updates the graphics for this piece
    */
    public void updateView(){
        view.update(this,player_color);
    }

    /**
    * Method to take this piece out of the board
    */
    public void getOut(){
        if (this.in_play==0){
            if (player_color==0){
                MyWorld.hand1.add(this);
                int ss = MyWorld.hand1.size();
                int hor_index = (ss-1)%5;
                int ver_index = (ss-1)/5;
                this.setPosition(new Vector2D(MyWorldView.WIDTH-90-50*hor_index, 304+80*ver_index));
                this.depth = ss;
                this.player_color = 1;
            }
            else{
                MyWorld.hand0.add(this);
                int ss = MyWorld.hand0.size();
                int hor_index = (ss-1)%5;
                int ver_index = (ss-1)/5;
                this.setPosition(new Vector2D(20 + 50*hor_index, 50+80*ver_index));
                this.depth = ss;
                this.player_color = 0;
            }
            view.update(this, player_color);
            if(creator == player_color){
                changedHand = 0;
            }
            else{
                changedHand = 1;
            }
        }
    }

    /**
     *  Returns the legal moves for the current position
     *
     *  @return ArrayList_BoardPanel legal moves of the piece
     */
    public ArrayList<BoardPanel> checkMoves(){
    	ArrayList<BoardPanel> posibles = new ArrayList<BoardPanel>();
    	ArrayList<BoardPanel> elements = MyWorld.getBP();
    	int columna_actual = this.getColumn();
    	int fila_actual = this.getRow();
    	int dif = 0;
    	if (player_color==0){ dif = -1;}
    	else{dif = 1;}

    	switch(this.level){
    		case 0:{
    			//calcular posiciones disponibles en piso 0
    			for (BoardPanel e:elements){
    				if (e.getRow() == fila_actual+dif && e.getColumn()==columna_actual){
                        int ss = e.getTierSize();
                        switch(ss){
                            case 0: posibles.add(e); break;
                            case 1: 
                            case 2: {
                                Pawn tt = (Pawn)e.getTier().get(ss-1);
                                if(tt.getClass()!=Commander.class){
                                    posibles.add(e);
                                }
                                else if(tt.player_color!=this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                            case 3:{
                                Pawn tt = (Pawn)e.getTier().get(2);
                                if(tt.player_color!= this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                        }
    				}
      			}
    			break;
    		}
    		case 1:{
    			//calcular posiciones disponibles en psio 1
    			for (BoardPanel e:elements){
    				if (e.getRow() == fila_actual+dif && e.getColumn()==columna_actual){
    					int ss = e.getTierSize();
                        switch(ss){
                            case 0: posibles.add(e); break;
                            case 1: 
                            case 2: {
                                Pawn tt = (Pawn)e.getTier().get(ss-1);
                                if(tt.getClass()!=Commander.class){
                                    posibles.add(e);
                                }
                                else if(tt.player_color!=this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                            case 3:{
                                Pawn tt = (Pawn)e.getTier().get(2);
                                if(tt.player_color!= this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                        }
    				}
    				else if (e.getRow() == fila_actual && e.getColumn()==columna_actual+2){
    					int ss = e.getTierSize();
                        switch(ss){
                            case 0: posibles.add(e); break;
                            case 1: 
                            case 2: {
                                Pawn tt = (Pawn)e.getTier().get(ss-1);
                                if(tt.getClass()!=Commander.class){
                                    posibles.add(e);
                                }
                                else if(tt.player_color!=this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                            case 3:{
                                Pawn tt = (Pawn)e.getTier().get(2);
                                if(tt.player_color!= this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                        }
    				}
    				else if (e.getRow() == fila_actual && e.getColumn()==columna_actual-2){
    					int ss = e.getTierSize();
                        switch(ss){
                            case 0: posibles.add(e); break;
                            case 1: 
                            case 2: {
                                Pawn tt = (Pawn)e.getTier().get(ss-1);
                                if(tt.getClass()!=Commander.class){
                                    posibles.add(e);
                                }
                                else if(tt.player_color!=this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                            case 3:{
                                Pawn tt = (Pawn)e.getTier().get(2);
                                if(tt.player_color!= this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                        }
    				}
      			}
    			break;
    		}
    		case 2:{
    			//calcular posiciones disponibles en piso 2
    			for (BoardPanel e:elements){
    				if (e.getRow() == fila_actual+dif && e.getColumn()==columna_actual+1){
    					int ss = e.getTierSize();
                        switch(ss){
                            case 0: posibles.add(e); break;
                            case 1: 
                            case 2: {
                                Pawn tt = (Pawn)e.getTier().get(ss-1);
                                if(tt.getClass()!=Commander.class){
                                    posibles.add(e);
                                }
                                else if(tt.player_color!=this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                            case 3:{
                                Pawn tt = (Pawn)e.getTier().get(2);
                                if(tt.player_color!= this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                        }
    				}
    				else if (e.getRow() == fila_actual+dif && e.getColumn()==columna_actual-1){
    					int ss = e.getTierSize();
                        switch(ss){
                            case 0: posibles.add(e); break;
                            case 1: 
                            case 2: {
                                Pawn tt = (Pawn)e.getTier().get(ss-1);
                                if(tt.getClass()!=Commander.class){
                                    posibles.add(e);
                                }
                                else if(tt.player_color!=this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                            case 3:{
                                Pawn tt = (Pawn)e.getTier().get(2);
                                if(tt.player_color!= this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                        }
    				}
    				else if (e.getRow() == fila_actual && e.getColumn()==columna_actual+2){
    					int ss = e.getTierSize();
                        switch(ss){
                            case 0: posibles.add(e); break;
                            case 1: 
                            case 2: {
                                Pawn tt = (Pawn)e.getTier().get(ss-1);
                                if(tt.getClass()!=Commander.class){
                                    posibles.add(e);
                                }
                                else if(tt.player_color!=this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                            case 3:{
                                Pawn tt = (Pawn)e.getTier().get(2);
                                if(tt.player_color!= this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                        }
    				}
    				else if (e.getRow() == fila_actual && e.getColumn()==columna_actual-2){
    					int ss = e.getTierSize();
                        switch(ss){
                            case 0: posibles.add(e); break;
                            case 1: 
                            case 2: {
                                Pawn tt = (Pawn)e.getTier().get(ss-1);
                                if(tt.getClass()!=Commander.class){
                                    posibles.add(e);
                                }
                                else if(tt.player_color!=this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                            case 3:{
                                Pawn tt = (Pawn)e.getTier().get(2);
                                if(tt.player_color!= this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                        }
    				}
      			}
    			break;
    		}
    	}
    	return posibles;
    }
}