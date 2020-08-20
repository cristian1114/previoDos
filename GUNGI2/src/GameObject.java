import javax.swing.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Dimension;


public abstract class GameObject  implements Comparable<GameObject>{
   private final int myId; 
   public int depth;
   public int level;

   protected GameObject( int id){
      myId = id;
   }
   protected int getId() {
      return myId;
   }
   public abstract Vector2D getPosition();
   public abstract void setPosition(Vector2D newpos);
   public abstract int getColumn();
   public abstract int getRow();
   public abstract int getDepth();
   public abstract void paintView(Graphics2D v);

   
   @Override
   public int compareTo(GameObject compareDepth) {
 
      int compareQuantity = ((GameObject) compareDepth).getDepth(); 
 
    
      return this.depth - compareQuantity;
 
      
   }  
}