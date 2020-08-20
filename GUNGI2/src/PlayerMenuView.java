import java.util.*;
import java.awt.*;
import java.awt.Image;
import javax.swing.ImageIcon;



public class PlayerMenuView {
    private PlayerMenu menu;
    private Image img;
    public static int gp = 0;

    /**
    * Constructor for PlayerMenuView
    * @param m Player menu to draw
    */
    public PlayerMenuView (PlayerMenu m){
      menu=m;
      Vector2D pos = m.getPosition();

      if (menu.n_jugador == 0){
        ImageIcon ii = new ImageIcon("src/InfoBlue.png");
        img = ii.getImage();
      }
      else{
        ImageIcon ii = new ImageIcon("src/InfoRed.png");
        img = ii.getImage();
      }
    }

  
    public void updateImg(){
      if (MyWorld.player_color == menu.n_jugador){
        if (menu.n_jugador == 0){
          ImageIcon ii = new ImageIcon("src/InfoBlue.png");
          img = ii.getImage();
        }
        else{
          ImageIcon ii = new ImageIcon("src/InfoRed.png");
          img = ii.getImage();
        }
      }
      else{
        ImageIcon ii = new ImageIcon("src/OtherIsPlayingRed.png");
        img = ii.getImage();
      }
    }

    
    void paint(Graphics2D g) {
       Vector2D pos = menu.getPosition();
       g.drawImage(img, pos.getX(), pos.getY(), null);
       g.setColor(Color.WHITE);
       g.setFont(new Font("Sylfaen", Font.PLAIN, 24));
       if (MyWorld.modoJuego == 0) {
         
       }
       switch (MyWorld.modoJuego){
        case 0: {
          g.drawString("PLAYER "+menu.n_jugador,pos.getX()+84, pos.getY()+64);
          break;
        }
        case 1: {
          if (MyWorld.pvcIA==menu.n_jugador) {
            g.drawString("IA_"+menu.n_jugador,pos.getX()+84, pos.getY()+64);
          }
          else{
            g.drawString("PLAYER "+menu.n_jugador,pos.getX()+84, pos.getY()+64);
          }
          break;
        }
        case 2: {
          g.drawString("IA_"+menu.n_jugador,pos.getX()+84, pos.getY()+64);
          break;
        }
       }
       if (menu.n_jugador == 0){
          g.drawString("Mano: "+MyWorld.hand0.size(),pos.getX()+90, pos.getY()+160);
       }
       else{
          g.drawString("Mano "+MyWorld.hand1.size(),pos.getX()+90, pos.getY()+160);
       }
       g.setColor(Color.BLACK);
    }
 }