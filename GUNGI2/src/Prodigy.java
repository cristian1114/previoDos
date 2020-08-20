import java.util.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *  Prodigy Class
 *  <p>
 *  A Prodigy is a specific piece of the game
 */
public class Prodigy extends Pawn{

	/**
     *  Prodigy Constructor 
     *
     *  @param xx x position
     *  @param yy y position
     *  @param rc Vector with row and column position
     *  @param pl_color player color
     */
    public Prodigy(int xx, int yy, Vector2D rc, int pl_color){
		super(xx,yy,rc, pl_color);
        valueIA = 6;
	}
	public Prodigy(int xx, int yy, int rr, int cc, int pl_color){
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
        Vector2D stopupleft= new Vector2D(fila_actual-9,columna_actual-9);
        Vector2D stopupright= new Vector2D(fila_actual-9,columna_actual+9);
        Vector2D stopdownleft = new Vector2D(fila_actual+9,columna_actual-9);
        Vector2D stopdownright = new Vector2D(fila_actual+9,columna_actual+9);
        Pawn tt = null;

    	if (player_color==0){ dif = -1;}
    	else{dif = 1;}

    	switch(this.level){
    		case 0:{
    			//calcular posiciones disponibles en piso 0
    			for (BoardPanel e:elements){
                    int ss = e.getTierSize();
                    for(int i=0;i<9;i++){
                        if(e.getRow()==fila_actual-(i+1) && e.getColumn()==columna_actual-(i+1) ){
                            if (ss>0){
                                tt = (Pawn)e.getTier().get(ss - 1);
                                if (tt.player_color == this.player_color){
                                    if (ss==3 || tt.getClass()==Commander.class){
                                        stopupleft.setX(e.getRow());
                                        stopupleft.setY(e.getColumn());
                                    }
                                    else{
                                        stopupleft.setX(e.getRow()-1);
                                        stopupleft.setY(e.getColumn()-1);
                                    }
                                }
                                else{
                                    stopupleft.setX(e.getRow()-1);
                                    stopupleft.setY(e.getColumn()-1);
                                }
                            }
                        }
                        if(e.getRow()==fila_actual-(i+1) && e.getColumn()==columna_actual+(i+1) ){
                            if (ss>0){
                                tt = (Pawn)e.getTier().get(ss - 1);
                                if (tt.player_color == this.player_color){
                                    if (ss==3 || tt.getClass()==Commander.class){
                                        if (e.getRow()>stopupright.getX() && e.getColumn()<stopupright.getY()){
                                            stopupright.setX(e.getRow());
                                            stopupright.setY(e.getColumn());
                                        }
                                    }
                                    else{
                                        if (e.getRow()-1>stopupright.getX() && e.getColumn()+1<stopupright.getY()){
                                            stopupright.setX(e.getRow()-1);
                                            stopupright.setY(e.getColumn()+1);
                                        }
                                    }
                                }
                                else{
                                    if (e.getRow()-1>stopupright.getX() && e.getColumn()+1<stopupright.getY()){
                                        stopupright.setX(e.getRow()-1);
                                        stopupright.setY(e.getColumn()+1);
                                    }
                                }
                            }
                        }
                        if(e.getRow()==fila_actual+(i+1) && e.getColumn()==columna_actual-(i+1) ){
                            if (ss>0){
                                tt = (Pawn)e.getTier().get(ss - 1);
                                if (tt.player_color == this.player_color){
                                    if (ss==3 || tt.getClass()==Commander.class){
                                        if (e.getRow()<stopdownleft.getX() && e.getColumn()>stopdownleft.getY()){
                                            stopdownleft.setX(e.getRow());
                                            stopdownleft.setY(e.getColumn());
                                        }
                                    }
                                    else{
                                        if (e.getRow()+1<stopdownleft.getX() && e.getColumn()-1>stopdownleft.getY()){
                                            stopdownleft.setX(e.getRow()+1);
                                            stopdownleft.setY(e.getColumn()-1);
                                        }
                                    }
                                }
                                else{
                                    if (e.getRow()+1<stopdownleft.getX() && e.getColumn()-1>stopdownleft.getY()){
                                        stopdownleft.setX(e.getRow()+1);
                                        stopdownleft.setY(e.getColumn()-1);
                                    }
                                }
                            }
                        }
                        if(e.getRow()==fila_actual+(i+1) && e.getColumn()==columna_actual+(i+1) ){
                            if (ss>0){
                                tt = (Pawn)e.getTier().get(ss - 1);
                                if (tt.player_color == this.player_color){
                                    if (ss==3 || tt.getClass()==Commander.class){
                                        if (e.getRow()<stopdownright.getX() && e.getColumn()<stopdownright.getY()){
                                            stopdownright.setX(e.getRow());
                                            stopdownright.setY(e.getColumn());
                                        }
                                    }
                                    else{
                                        if (e.getRow()+1<stopdownright.getX() && e.getColumn()+1<stopdownright.getY()){
                                            stopdownright.setX(e.getRow()+1);
                                            stopdownright.setY(e.getColumn()+1);
                                        }
                                    }
                                }
                                else{
                                    if (e.getRow()+1<stopdownright.getX() && e.getColumn()+1<stopdownright.getY()){
                                        stopdownright.setX(e.getRow()+1);
                                        stopdownright.setY(e.getColumn()+1);
                                    }
                                }
                            }
                        }
                    }
                }
                for (BoardPanel e:elements){
                    for(int j=0;j<9;j++){
                        if(e.getRow()>stopupleft.getX() && e.getColumn()>stopupleft.getY() ){
                            if(e.getRow()==fila_actual-(j+1) && e.getColumn()==columna_actual-(j+1) ){
                                posibles.add(e);
                            }
                        }
                        if(e.getRow()>stopupright.getX() && e.getColumn()<stopupright.getY() ){
                            if(e.getRow()==fila_actual-(j+1) && e.getColumn()==columna_actual+(j+1) ){
                                posibles.add(e);
                            }
                        }
                        if(e.getRow()<stopdownleft.getX() && e.getColumn()>stopdownleft.getY() ){
                            if(e.getRow()==fila_actual+(j+1) && e.getColumn()==columna_actual-(j+1) ){
                                posibles.add(e);
                            }
                        }
                        if(e.getRow()<stopdownright.getX() && e.getColumn()<stopdownright.getY() ){
                            if(e.getRow()==fila_actual+(j+1) && e.getColumn()==columna_actual+(j+1) ){
                                posibles.add(e);
                            }
                        }
                        
                    }
                }
    			break;
    		}

    		case 1:
    		case 2:{
    			//calcular posiciones disponibles en piso 2
    			for (BoardPanel e:elements){
    				if ( (e.getRow() == fila_actual+1 || e.getRow() == fila_actual-1) && e.getColumn()==columna_actual){
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
    				else if (e.getRow() == fila_actual && (e.getColumn()==columna_actual+1 || e.getColumn()==columna_actual-1) ){
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