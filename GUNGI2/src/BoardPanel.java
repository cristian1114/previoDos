import java.util.*;
import java.awt.*;
import java.awt.Graphics;
import java.awt.Graphics2D;


/**
 *  Board Pannel Class 
 *  <p>
 *  A board pannel represents a single pannel in the Gungi board
 */
public class BoardPanel extends GameObject {
	private static int id = 0;
	private Vector2D pos;
	private Vector2D row_col = null;	//vector row , column
	private BoardPanelView view;
	public int imagen = 0;
	private ArrayList<GameObject> tier;
	public int active = 0;

	
	public BoardPanel(int xx, int yy, Vector2D rc){
		super(id++);
		pos = new Vector2D(xx,yy);
		view = new BoardPanelView(this);
		tier = new ArrayList<GameObject>();
		row_col = rc;
	}


	public Vector2D getPosition(){
		return pos;
	}


	public void setPosition(Vector2D v){}
	
	
	public int getRow(){
		if (row_col==null) {return -1;}
		return row_col.getX();
	}


	public int getColumn(){
		if (row_col==null) {return -1;}
		return row_col.getY();
	}

   
	public int getDepth(){
		return 0;
	}


	public void updateImg( int act){
		view.updateImg(act);
		this.imagen = act;
	}

	public void paintView(Graphics2D gra) {
        view.paint(gra);
    }

    public void addTier(GameObject e){
    	if (tier.size()<3){
    		tier.add(e);
    	}
    }

   
    public void removeTier(int e){
    	if (tier.size()>0){
    		tier.remove(e);
    	}
    }

   
    public int getTierSize(){
    	return tier.size();
    }

    
    public ArrayList<GameObject> getTier(){
    	return tier;
    }

    
    public void tier_clear(){
    	tier.clear();
    }
}