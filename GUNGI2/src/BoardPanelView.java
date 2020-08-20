import java.util.*;
import java.awt.*;
import java.awt.Image;
import javax.swing.ImageIcon;

public class BoardPanelView {
    private BoardPanel board;
    private Image img;

    
    public BoardPanelView (BoardPanel b){
      board=b;
      Vector2D pos = b.getPosition();

      ImageIcon ii = new ImageIcon("src/BoardPanelImage.png");
      img = ii.getImage();
    }
    

   
    public void updateImg( int act){
      switch(act){
        case 0:{
          ImageIcon ii = new ImageIcon("src/BoardPanelImage.png");
          img = ii.getImage();
          break;
        }
        case 1:{
          ImageIcon ii = new ImageIcon("src/SelectImage.png");
          img = ii.getImage();
          break;
        }
        case 2:{
          ImageIcon ii = new ImageIcon("src/BoardPanelMoves.png");
          img = ii.getImage();
          break;
        }
        case 3:{
          ImageIcon ii = new ImageIcon("src/BoardPanelBack.png");
          img = ii.getImage();
          break;
        }
      }
    }

   
    void paint(Graphics2D g) {
       Vector2D pos = board.getPosition();
       g.drawImage(img, pos.getX(), pos.getY(), null);
       //g.setColor(Color.BLUE);
       //g.drawString(""+board.getTierSize(),pos.getX(), pos.getY());
       //g.drawString(""+MyWorld.fichasIA_0.size(),pos.getX(), pos.getY());
    }
 }