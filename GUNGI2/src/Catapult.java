import java.util.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *  Catapult Class
 *  <p>
 *  A Catapult is a specific piece of the game
 */
public class Catapult extends Pawn{

	/**
     *  Catapult Constructor 
     *
     *  @param xx x position
     *  @param yy y position
     *  @param rc Vector with row and column position
     *  @param pl_color player color
     */
	public Catapult(int xx, int yy, Vector2D rc, int pl_color){
		super(xx,yy,rc, pl_color);
		valueIA = 0;
	}
	public Catapult(int xx, int yy, int rr, int cc, int pl_color){
		super( xx, yy, rr, cc, pl_color);
		valueIA = 0;
	}

	/**
     *  Returns the legal moves for the current position
     *
     *  @return ArrayList_BoardPanel legal moves of the piece
     */
	@Override
	public ArrayList<BoardPanel> checkMoves(){
    	ArrayList<BoardPanel> posibles = new ArrayList<BoardPanel>();
    	return posibles;
    }
}