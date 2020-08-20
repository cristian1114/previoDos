import java.util.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;


/**
 *  HiddenDragon Class
 *  <p>
 *  A HiddenDragon is a specific piece of the game
 */
public class HiddenDragon extends Pawn{

	/**
     *  HiddenDragon Constructor 
     *
     *  @param xx x position
     *  @param yy y position
     *  @param rc Vector with row and column position
     *  @param pl_color player color
     */
    public HiddenDragon(int xx, int yy, Vector2D rc, int pl_color){
		super(xx,yy,rc, pl_color);
        valueIA = 6;
	}
	public HiddenDragon(int xx, int yy, int rr, int cc, int pl_color){
		super( xx, yy, rr, cc, pl_color);
        valueIA = 6;
	}

    /**
     *  Returns the legal moves for the current position
     *
     *  @return ArrayList_BoardPanel legal moves of the piece
     */
	@Override
	public ArrayList<BoardPanel> checkMoves(){
    	ArrayList<BoardPanel> posibles = new ArrayList<BoardPanel>();
    	ArrayList<BoardPanel> elements = MyWorld.getBP();
    	int columna_actual = this.getColumn();
    	int fila_actual = this.getRow();
    	int dif = 0;
        Vector2D stopleft= new Vector2D(fila_actual,-1);
        Vector2D stopright= new Vector2D(fila_actual,10);
        Vector2D stopup = new Vector2D(-1,columna_actual);
        Vector2D stopdown = new Vector2D(10,columna_actual);
        Pawn tt = null;

    	if (player_color==0){ dif = -1;}
    	else{dif = 1;}

    	switch(this.level){
    		case 0:{
    			//calcular posiciones disponibles en piso 0
    			for (BoardPanel e:elements){
                    int ss = e.getTierSize();
                    if (e.getRow() == fila_actual && e.getColumn()<columna_actual){
                        if (ss>0){
                            tt = (Pawn)e.getTier().get(ss - 1);
                            if (tt.player_color == this.player_color){
                                if (ss==3 || tt.getClass()==Commander.class){
                                    stopleft.setX(e.getRow());
                                    stopleft.setY(e.getColumn());
                                }
                                else{
                                    stopleft.setX(e.getRow());
                                    stopleft.setY(e.getColumn()-1);
                                }
                            }
                            else{
                                stopleft.setX(e.getRow());
                                stopleft.setY(e.getColumn()-1);
                            }
                        }
                    }
                    else if (e.getRow() == fila_actual && e.getColumn()>columna_actual){
                        if (ss>0 && stopright.getY()==10){
                            tt = (Pawn)e.getTier().get(ss - 1);
                            if (tt.player_color == this.player_color){
                                if (ss==3 || tt.getClass()==Commander.class){
                                    stopright.setX(e.getRow());
                                    stopright.setY(e.getColumn());
                                }
                                else{
                                    stopright.setX(e.getRow());
                                    stopright.setY(e.getColumn()+1);
                                }
                            }
                            else{
                                stopright.setX(e.getRow());
                                stopright.setY(e.getColumn()+1);
                            }
                        }
                    }
                    else if (e.getColumn() == columna_actual && e.getRow()<fila_actual){
                        if (ss>0){
                            tt = (Pawn)e.getTier().get(ss-1);
                            if (tt.player_color == this.player_color){
                                if(ss==3 || tt.getClass()==Commander.class){
                                    stopup.setX(e.getRow());
                                    stopup.setY(e.getColumn());
                                }
                                else{
                                    stopup.setX(e.getRow()-1);
                                    stopup.setY(e.getColumn());
                                }
                            }
                            else{
                                stopup.setX(e.getRow()-1);
                                stopup.setY(e.getColumn());
                            }
                        }
                    }
                    else if (e.getColumn() == columna_actual && e.getRow()>fila_actual){
                        if (ss>0 && stopdown.getX()==10){
                            tt = (Pawn)e.getTier().get(ss-1);
                            if (tt.player_color == this.player_color){
                                if(ss==3 || tt.getClass()==Commander.class){
                                    stopdown.setX(e.getRow());
                                    stopdown.setY(e.getColumn());
                                }
                                else{
                                    stopdown.setX(e.getRow()+1);
                                    stopdown.setY(e.getColumn());
                                }
                            }
                            else{
                                stopdown.setX(e.getRow()+1);
                                stopdown.setY(e.getColumn());
                            }
                        }
                    }
      			}
                for(BoardPanel e:elements){
                     if (e.getRow() ==fila_actual && e.getColumn()>stopleft.getY() && e.getColumn()<columna_actual){
                        posibles.add(e);
                     }
                     else if (e.getRow() ==fila_actual && e.getColumn()<stopright.getY() && e.getColumn()>columna_actual){
                        posibles.add(e);
                     }
                     else if (e.getColumn() == columna_actual && e.getRow()>stopup.getX() && e.getRow()<fila_actual){
                        posibles.add(e);
                     }else if (e.getColumn() == columna_actual && e.getRow()<stopdown.getX() && e.getRow()>fila_actual){
                        posibles.add(e);
                     }
                }
    			break;
    		}
    		case 1:
    		case 2:{
    			//calcular posiciones disponibles en piso 2
    			for (BoardPanel e:elements){
    				if (e.getRow() == fila_actual+1 && e.getColumn()==columna_actual+1){
                        int ss = e.getTierSize();
                        switch(ss){
                            case 0: posibles.add(e); break;
                            case 1: 
                            case 2: {
                                tt = (Pawn)e.getTier().get(ss-1);
                                if(tt.getClass()!=Commander.class){
                                    posibles.add(e);
                                }
                                else if(tt.player_color!=this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                            case 3:{
                                tt = (Pawn)e.getTier().get(2);
                                if(tt.player_color!= this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                        }
    				}
    				else if (e.getRow() == fila_actual+1 && e.getColumn()==columna_actual-1){
                        int ss = e.getTierSize();
                        switch(ss){
                            case 0: posibles.add(e); break;
                            case 1: 
                            case 2: {
                                tt = (Pawn)e.getTier().get(ss-1);
                                if(tt.getClass()!=Commander.class){
                                    posibles.add(e);
                                }
                                else if(tt.player_color!=this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                            case 3:{
                                tt = (Pawn)e.getTier().get(2);
                                if(tt.player_color!= this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                        }
    				}
    				else if (e.getRow() == fila_actual-1 && e.getColumn()==columna_actual+1){
    					int ss = e.getTierSize();
                        switch(ss){
                            case 0: posibles.add(e); break;
                            case 1: 
                            case 2: {
                                tt = (Pawn)e.getTier().get(ss-1);
                                if(tt.getClass()!=Commander.class){
                                    posibles.add(e);
                                }
                                else if(tt.player_color!=this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                            case 3:{
                                tt = (Pawn)e.getTier().get(2);
                                if(tt.player_color!= this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                        }
    				}
    				else if (e.getRow() == fila_actual-1 && e.getColumn()==columna_actual-1){
    					int ss = e.getTierSize();
                        switch(ss){
                            case 0: posibles.add(e); break;
                            case 1: 
                            case 2: {
                                tt = (Pawn)e.getTier().get(ss-1);
                                if(tt.getClass()!=Commander.class){
                                    posibles.add(e);
                                }
                                else if(tt.player_color!=this.player_color){
                                    posibles.add(e);
                                }
                                break;
                            }
                            case 3:{
                                tt = (Pawn)e.getTier().get(2);
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