import java.util.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;


/**
 *  Captain Class
 *  <p>
 *  A Captain is a specific piece of the game
 */
public class Captain extends Pawn{

	/**
     *  Captain Constructor 
     *
     *  @param xx x position
     *  @param yy y position
     *  @param rc Vector with row and column position
     *  @param pl_color player color
     */
    public Captain(int xx, int yy, Vector2D rc, int pl_color){
		super(xx,yy,rc, pl_color);
        valueIA = 5;
	}
	public Captain(int xx, int yy, int rr, int cc, int pl_color){
		super( xx, yy, rr, cc, pl_color);
        valueIA = 5;
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
    	if (player_color==0){ dif = -1;}
    	else{dif = 1;}

    	switch(this.level){
    		case 0:{
    			//calcular posiciones disponibles en piso 0
    			for (BoardPanel e:elements){
    				if (e.getRow() == fila_actual+dif && e.getColumn()>=columna_actual-1 && e.getColumn()<=columna_actual+1){
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
    				else if (e.getRow() == fila_actual-dif && e.getColumn()==columna_actual+1){
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
    				else if (e.getRow() == fila_actual-dif && e.getColumn()==columna_actual-1){
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
                //calcular posiciones disponibles en piso 1
                for (BoardPanel e:elements){
                    if (e.getRow() == fila_actual+1 && e.getColumn()>=columna_actual-1 && e.getColumn()<=columna_actual+1){
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
                    else if (e.getRow() == fila_actual-1 && e.getColumn()>=columna_actual-1 && e.getColumn()<=columna_actual+1){
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
    				if ( (e.getRow() == fila_actual+1 || e.getRow() == fila_actual-1)  && (e.getColumn()==columna_actual+1 || e.getColumn()==columna_actual-1) ){
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
                    else if (e.getRow() == fila_actual && (e.getColumn()==columna_actual-2 || e.getColumn()==columna_actual+2)){
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
                    else if (e.getRow() == fila_actual+2*dif && (e.getColumn()==columna_actual-2 || e.getColumn()==columna_actual+2)){
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