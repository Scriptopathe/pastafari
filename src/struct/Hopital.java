/*    */ package struct;
import game.Coordonnee;
/*    */ 
/*    */ public class Hopital extends Batiment
/*    */ {
/*    */   public Hopital(Coordonnee pos)
/*    */   {
/*  9 */     this.PVMax = 2;
/* 10 */     this.PV = 2;
/* 11 */     this.place = pos;
/* 12 */     this.type = Structure.HOPITAL;
/*    */   }
/*    */   
/*    */   public Structure getType()
/*    */   {
/* 17 */     return Structure.HOPITAL;
/*    */   }
/*    */   
/*    */   public static final int getCoast()
/*    */   {
/* 22 */     return 100;
/*    */   }
/*    */ }


/* Location:              C:\Users\Proïd\git\DRIVE FINALISTES\Server.jar!\Struct\Hopital.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */