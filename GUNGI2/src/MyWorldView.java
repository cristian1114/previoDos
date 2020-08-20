import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.*;
import java.awt.Image;
import java.util.*;
import java.awt.event.*;


public class MyWorldView extends JPanel implements MouseListener, MouseMotionListener{
   private MyWorld world;
   private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
   public static int WIDTH =(int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getWidth();
   public static int HEIGHT =(int) GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().getHeight();
   private Image bgImage = Toolkit.getDefaultToolkit().createImage("src/bgImage.png");
   private Image borde = Toolkit.getDefaultToolkit().createImage("src/bordetablero.png");
   private Image winImage0 = Toolkit.getDefaultToolkit().createImage("src/win0.png");
   private Image winImage1 = Toolkit.getDefaultToolkit().createImage("src/win1.png");
   public static int x=0,y=0;
   public static int click_derecho = 0;
   public static int click_izquierdo = 0;
   public static GameObject ficha_move = null;
   public static int droping = 0;
   public static int win = -1;

   public MyWorldView(MyWorld w){
      world = w;
      this.addMouseListener(this);
      this.addMouseMotionListener(this);
   }

   
   @Override
   public void paintComponent(Graphics g){
      Graphics2D g2 = (Graphics2D)g;
      g.drawImage(bgImage, 0, 0, WIDTH, HEIGHT, null);
      g.drawImage(borde, (WIDTH-680)/2 , (HEIGHT-680)/2, null);

      ArrayList<GameObject> gameobjects = world.getGO();
      ArrayList<BoardPanel> elements = world.getBP();
      Collections.sort(gameobjects);
      for (BoardPanel e:elements){
         e.paintView(g2);
      }
      if(MyWorld.game_phase==0){
         g.setColor(Color.RED);
         g.drawLine((WIDTH-630)/2, (HEIGHT-630)/2 +70*3, (WIDTH-630)/2 +630, (HEIGHT-630)/2 +70*3);
         g.setColor(Color.BLUE);
         g.drawLine((WIDTH-630)/2, (HEIGHT-630)/2 +70*6, (WIDTH-630)/2 +630, (HEIGHT-630)/2 +70*6);
      }
      for (int i=0;i<gameobjects.size() ;i++ ) {
         GameObject go = gameobjects.get(i);
         go.paintView(g2);
      }
      world.info.paintView(g2);
      if (MyWorld.game_phase==3){
         if (win==0){
            g.drawImage(winImage0, WIDTH/2 - 160,HEIGHT/2 -160 ,null);
         }
         else if (win==1){
            g.drawImage(winImage1, WIDTH/2 - 160,HEIGHT/2 -160 ,null);
         }
      }
   }

   @Override
   public void mousePressed(MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON3 && ficha_move==null){
         click_derecho = 1;
         repaint();
      }
      if (e.getButton() == MouseEvent.BUTTON1){
         click_izquierdo = 1;
         repaint();
      }
   }

   @Override
   public void mouseReleased(MouseEvent e) {
      if (e.getButton() == MouseEvent.BUTTON3){
         click_derecho = 0;
      }
      if (e.getButton() == MouseEvent.BUTTON1){
         click_izquierdo = 0;
      }
   }

   @Override
   public void mouseEntered(MouseEvent e) {
   }

   @Override
   public void mouseExited(MouseEvent e) {
   }

   @Override
   public void mouseClicked(MouseEvent e) {
   }

   @Override
   public void mouseDragged(MouseEvent e){
      x = e.getX();
      y = e.getY();
      repaint();
   }

   @Override
   public void mouseMoved(MouseEvent e){
      x = e.getX();
      y = e.getY();
      repaint();
   }
}
