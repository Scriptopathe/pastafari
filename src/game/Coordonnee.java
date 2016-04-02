/*    */ package game;
/*    */ 
/*    */ public class Coordonnee
/*    */ {
/*    */   int x;
/*    */   int y;
/*    */   
/*    */   public Coordonnee(int a, int b) {
/*  9 */     this.x = a;
/* 10 */     this.y = b;
/*    */   }
/*    */   
/*    */   public void moveTo(int a, int b)
/*    */   {
/* 15 */     this.x = a;
/* 16 */     this.y = b;
/*    */   }
/*    */   
/*    */   public int distance(Coordonnee a)
/*    */   {
/* 21 */     double res = Math.sqrt((int)(Math.pow(this.x - a.x, 2.0D) + Math.pow(this.y - a.y, 2.0D)));
/* 22 */     return (int)res;
/*    */   }
/*    */ }


/* Location:              C:\Users\Proïd\git\DRIVE FINALISTES\Server.jar!\Game\Coordonnee.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */