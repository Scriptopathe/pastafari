/*    */ package unit;
import game.Coordonnee;
import game.Land;
import struct.City;
import struct.Fort;
import struct.Hopital;
import struct.Pont;
import struct.Road;
import struct.Structure;
/*    */ 
/*    */ public class Ingenieur extends Unit
/*    */ {
/*    */   public Ingenieur(Land pos, int p)
/*    */   {
/* 16 */     this.strengh = 0;
/* 17 */     this.defense = 2;
/* 18 */     this.action = 2;
/* 19 */     this.actionMax = 4;
/* 20 */     this.life = 4;
/* 21 */     this.lifeMax = 4;
/* 22 */     this.position = pos;
/* 23 */     this.player = p;
/* 24 */     this.range = 0;
/* 25 */     this.type = unitType.INGE;
/*    */   }
/*    */   
/*    */   public int move(Coordonnee cible) {
/* 29 */     int distance = this.position.place.distance(cible);
/* 30 */     int status = 0;
/* 31 */     if (distance < this.action)
/*    */     {
/* 33 */       this.action -= distance;
/*    */     }
/*    */     else
/*    */     {
/* 37 */       status = -1;
/*    */     }
/* 39 */     return status;
/*    */   }
/*    */   
/*    */   public int build(Structure struct) {
/* 43 */     int res = 0;
/* 44 */     if (this.position.struct == null)
/*    */     {
/* 46 */       if (this.action >= 2)
/*    */       {
/* 48 */         switch (struct)
/*    */         {
/*    */         case ROUTE: 
/* 51 */           this.position.struct = new Fort(this.position.place);
/* 52 */           break;
/*    */         case FORT: 
/* 54 */           this.position.struct = new Hopital(this.position.place);
/* 55 */           break;
/*    */         case HOPITAL: 
/* 57 */           this.position.struct = new Pont(this.position.place);
/* 58 */           break;
/*    */         case PONT: 
/* 60 */           this.position.struct = new Road(this.position.place);
/* 61 */           break;
/*    */         case VILLE: 
/* 63 */           this.position.struct = new City(this.position.place);
/* 64 */           break;
/*    */         }
/*    */         
/*    */         
/*    */ 
/*    */ 
/* 70 */         this.action -= 2;
/*    */       }
/*    */       else
/*    */       {
/* 74 */         res = -1;
/*    */       }
/*    */       
/*    */     }
/*    */     else {
/* 79 */       res = -1;
/*    */     }
/* 81 */     return res;
/*    */   }
/*    */   
/*    */   public int remove()
/*    */   {
/* 86 */     if (this.action >= 2)
/*    */     {
/* 88 */       this.position.struct = null;
/* 89 */       this.action -= 2;
/* 90 */       return 0;
/*    */     }
/*    */     
/*    */ 
/* 94 */     return -1;
/*    */   }
/*    */   
/*    */   public static final int getCost()
/*    */   {
/* 99 */     return 50;
/*    */   }
/*    */ }


/* Location:              C:\Users\Proïd\git\DRIVE FINALISTES\Server.jar!\Unit\Ingenieur.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */