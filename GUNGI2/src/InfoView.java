import java.util.*;
import java.awt.*;
import java.awt.Image;
import javax.swing.ImageIcon;


public class InfoView extends GameObject{
  private static int id = 0;
  private int nivel = 0;
  private int active = 0;
  public int depth = 20000;
  private GameObject target = null;
  private Image img;
  private Image mvs = null;
  private Image mvs2 = null;
  private Image mvs3 = null;
  private Vector2D pos = new Vector2D(0,0);

  /**
  * InfoView Constructor
  *@param n selected image
  */
  public InfoView(int n){
    super(id++);
    nivel = n;
    ImageIcon ii = null;
    switch(nivel){
      case 0: ii = new ImageIcon("src/info_1.png"); break;
      case 1: ii = new ImageIcon("src/info_2.png"); break;
      case 2: ii = new ImageIcon("src/info_3.png"); break;
    }
    if (ii!=null){
      img = ii.getImage();
    }
  }


  /**
     *  Updates the image of the panel acording to these parameters
     *
     *  @param n tier
     *  @param act in the panel
     *  @param tg target
     */
  public void updateImg(int n, int act, GameObject tg){
    active = act;
    nivel = n;
    target = tg;
    ImageIcon ii = null;
    ImageIcon mm = null;
    switch(nivel){
      case -1:{
        ii = new ImageIcon("src/info_0.png");
        if (target!=null){
          String dir = "src/"+target.getClass().getName()+"3.png";
          mm = new ImageIcon(dir);
          mvs3 = mm.getImage();

          dir = "src/"+target.getClass().getName()+"2.png";
          mm = new ImageIcon(dir);
          mvs2 = mm.getImage();

          dir = "src/"+target.getClass().getName()+"1.png";
          mm = new ImageIcon(dir);
        }
        break;
      }
      case 0: {
        ii = new ImageIcon("src/info_1.png");
        if (target!=null){
          String dir = "src/"+target.getClass().getName()+"1.png";
          mm = new ImageIcon(dir);
        }
        break;
      }
      case 1: {
        ii = new ImageIcon("src/info_2.png");
        if (target!=null){
          String dir = "src/"+target.getClass().getName()+"2.png";
          mm = new ImageIcon(dir);
        }
        break;
      }
      case 2: {
        ii = new ImageIcon("src/info_3.png");
        if (target!=null){
          String dir = "src/"+target.getClass().getName()+"3.png";
          mm = new ImageIcon(dir);
        }
        break;
      }
    }
    if (ii!=null){
      img = ii.getImage();
    }
    if (target!=null && mm!=null){
      mvs = mm.getImage();
    }
  }

  public Vector2D getPosition(){
    return pos;
  }
  public void setPosition(Vector2D newpos){
    pos = newpos;
  }
  public int getColumn(){
    return -1;
  }
  public int getRow(){
    return -1;
  }
  public int getDepth(){
    return depth;
  }

  /**
     *  Method for the world to paint this element
     *
     *  @param g grafics 2d object
     */
  public void paintView(Graphics2D g) {
    if (active==1){
      g.drawImage(img, pos.getX(), pos.getY(), null);
      g.setColor(Color.WHITE);
      g.setFont(new Font("Sylfaen", Font.PLAIN, 20));
      if (target!=null && mvs!=null && nivel!=-1){
        g.drawString((nivel+1)+" "+target.getClass().getName(),pos.getX()+42, pos.getY()+22);
        g.drawImage(mvs, pos.getX()+100,pos.getY()+40,null);
        if (target.getClass()==Pawn.class || target.getClass()==Spy.class){
          g.setFont(new Font("Sylfaen", Font.PLAIN, 16));
          g.drawString("Forced Recovery" ,pos.getX()+116, pos.getY()+24);
        }
      }
      else if (nivel==-1){
        g.drawString(target.getClass().getName(),pos.getX()+8, pos.getY()+22);
        g.drawImage(mvs, pos.getX()+8,pos.getY()+40,null);
        g.drawImage(mvs2, pos.getX()+87,pos.getY()+40,null);
        g.drawImage(mvs3, pos.getX()+165,pos.getY()+40,null);
      }
    }
  }

}