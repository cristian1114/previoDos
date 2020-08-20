import java.util.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *  Player Menu
 *  <p>
 *  This class gives some information about the players
 *	currently playing the game
 */
public class PlayerMenu extends GameObject{
	private static int id = 0;
	public int n_jugador = 0;	//0 es jugador azul
	public int n_hand = 0;
	public int active = 0;		//1 si esta en su turno
	private PlayerMenuView view;
	private Vector2D pos;

	
	public PlayerMenu(int n, int act, Vector2D p){
		super(id++);
		n_jugador = n;
		active = act;
		pos = p;
		view = new PlayerMenuView(this);
	}

	public PlayerMenu(int n, int act, int px, int py){
		super(id++);
		n_jugador = n;
		active = act;
		pos = new Vector2D(px,py);
		view = new PlayerMenuView(this);
	}

	public Vector2D getPosition(){
		return pos;
	}
	public void setPosition(Vector2D v){}
	public int getRow(){
		return -1;
	}
	public int getColumn(){
		return -1;
	}
	public int getDepth(){
		return depth;
	}
	public void updateImg(){
		view.updateImg();
	}
	
	
	public void paintView(Graphics2D gra) {
        view.paint(gra);
    }

}