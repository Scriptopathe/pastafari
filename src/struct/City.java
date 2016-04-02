/*    */ package struct;
import game.Coordonnee;
/*    */ 
/*    */ 
/*    */ public class City
/*    */   extends Batiment
/*    */ {
/*    */   public City(Coordonnee pos)
/*    */   {
/* 11 */     this.PVMax = 10;
/* 12 */     this.PV = 10;
/* 13 */     this.place = pos;
/* 14 */     this.type = Structure.VILLE;
/*    */   }
/*    */   
/*    */   public Structure getType()
/*    */   {
/* 19 */     return Structure.VILLE;
/*    */   }
/*    */ }


/* Location:              C:\Users\Proïd\git\DRIVE FINALISTES\Server.jar!\Struct\City.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */