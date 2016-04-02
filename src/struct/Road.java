/*    */ package struct;
import game.Coordonnee;
/*    */ 
/*    */ public class Road
/*    */   extends Batiment
/*    */ {
/*    */   public Road(Coordonnee pos)
/*    */   {
/* 10 */     this.PVMax = 1;
/* 11 */     this.PV = 1;
/* 12 */     this.place = pos;
/* 13 */     this.type = Structure.ROUTE;
/*    */   }
/*    */   
/*    */   public Structure getType()
/*    */   {
/* 18 */     return Structure.ROUTE;
/*    */   }
/*    */   
/*    */   public static final int getCoast()
/*    */   {
/* 23 */     return 50;
/*    */   }
/*    */ }


/* Location:              C:\Users\Proïd\git\DRIVE FINALISTES\Server.jar!\Struct\Road.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */