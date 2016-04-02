/*    */ package unit;
import game.Coordonnee;
import game.Land;
import struct.Batiment;
import struct.Structure;
/*    */ 
/*    */ public abstract class Unit
/*    */ {
/*    */   public int strengh;
/*    */   public int defense;
/*    */   public int action;
/*    */   public int actionMax;
/*    */   public int life;
/*    */   public int lifeMax;
/*    */   public Land position;
/*    */   public int player;
/*    */   public int range;
/*    */   public unitType type;
/*    */   
/*    */   public int move(Coordonnee cible, int cost)
/*    */   {
/* 23 */     int status = 0;
/* 24 */     if (cost <= this.action)
/*    */     {
/* 26 */       this.action -= cost;
/*    */     }
/*    */     else
/*    */     {
/* 30 */       status = -1;
/*    */     }
/* 32 */     return status;
/*    */   }
/*    */   
/*    */   public boolean alive() {
/* 36 */     return this.life > 0;
/*    */   }
/*    */   
/*    */   public void hurt(Unit adversaire)
/*    */   {
/* 41 */     int defence = adversaire.defense;
/* 42 */     double roll = Math.random() * 6.0D + 1.0D;
/* 43 */     if ((adversaire.position.struct != null) && (adversaire.position.struct.getType() == Structure.FORT))
/*    */     {
/* 45 */       defence += 2;
/*    */     }
/* 47 */     int score = defence - this.strengh + 3;
/* 48 */     if (score > 6)
/*    */     {
/* 50 */       score = 6;
/*    */     }
/* 52 */     if (roll >= score)
/*    */     {
/* 54 */       adversaire.life -= 1;
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean inrange(Unit adversaire)
/*    */   {
/* 60 */     return this.position.place.distance(adversaire.position.place) <= this.range;
/*    */   }
/*    */   
/*    */   public void fight(Unit adversaire)
/*    */   {
/* 65 */     for (int i = 0; (i < 3) && (adversaire.alive()) && (alive()); i++)
/*    */     {
/* 67 */       hurt(adversaire);
/* 68 */       if ((adversaire.alive()) && (adversaire.inrange(this)))
/*    */       {
/* 70 */         adversaire.hurt(this);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public void restAction()
/*    */   {
/* 78 */     if (this.life != 0)
/*    */     {
/* 80 */       this.action = this.actionMax;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\Proïd\git\DRIVE FINALISTES\Server.jar!\Unit\Unit.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */