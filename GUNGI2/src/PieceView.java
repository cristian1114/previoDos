import java.util.*;
import java.awt.*;
import java.awt.Image;
import javax.swing.ImageIcon;


public class PieceView {
    private GameObject pp;
    private Image img;
    private String dir_img = "src/";

    
    public PieceView (GameObject p, int color){
      pp=p;
      String nombre = p.getClass().getName();
      Vector2D pos = p.getPosition();
      if (color==1){
        dir_img = "src/R"+nombre+".png";
      }
      else{
        dir_img = "src/B"+nombre+".png";
      }
      ImageIcon ii = new ImageIcon(dir_img);
      img = ii.getImage();
    }

   
    public void update(GameObject p, int color){
      pp=p;
      String nombre = p.getClass().getName();
      Vector2D pos = p.getPosition();
      if (color==1){
        dir_img = "src/R"+nombre+".png";
      }
      else{
        dir_img = "src/B"+nombre+".png";
      }
      ImageIcon ii = new ImageIcon(dir_img);
      img = ii.getImage();
    }

   
    void paint(Graphics2D g) {
       Vector2D pos = pp.getPosition();
       Pawn ppeon = (Pawn)pp;
       g.drawImage(img, pos.getX(), pos.getY(), null);
       Pawn peon = (Pawn)pp;
    }
 }