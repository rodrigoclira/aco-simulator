/*    */ package karincca;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import javafx.scene.image.Image;
/*    */ import javafx.scene.image.ImageView;
/*    */ 
/*    */ public class Image_karinca extends ImageView
/*    */ {
/*  9 */   private boolean yemek_buldu = false;
/*    */   private String last_point;
/*    */   private String name;
/*    */   private ArrayList gidilen_yollar;
/*    */   private Operations Ops;
/*    */ 
/*    */   public Image_karinca(int id, int pos_x, int pos_y, String name)
/*    */   {
/* 15 */     setId(id + "");
/* 16 */     set_name(name);
/* 17 */     setTranslateX(pos_x - 10);
/* 18 */     setTranslateY(pos_y - 10);
/* 19 */     this.last_point = (id + "");
/* 20 */     setImage(new Image(Karincca.class.getResourceAsStream("imgs/ant.png")));
/* 21 */     setFitWidth(20.0D);
/* 22 */     setFitHeight(20.0D);
/* 23 */     setSmooth(true);
/* 24 */     this.gidilen_yollar = new ArrayList();
/* 25 */     this.Ops = new Operations();
/*    */   }
/*    */ 
/*    */   public String get_name() {
/* 29 */     return this.name;
/*    */   }
/*    */ 
/*    */   public void set_name(String name) {
/* 33 */     this.name = name;
/*    */   }
/*    */ 
/*    */   public String get_last_id() {
/* 37 */     return this.last_point;
/*    */   }
/*    */ 
/*    */   public void set_last_id(String last_edge) {
/* 41 */     this.last_point = last_edge;
/*    */   }
/*    */ 
/*    */   public void set_yemek_buldu(boolean deger) {
/* 45 */     this.yemek_buldu = deger;
/*    */   }
/*    */ 
/*    */   public void clear_paths() {
/* 49 */     this.gidilen_yollar.clear();
/* 50 */     set_last_id("-1");
/*    */   }
/*    */ 
/*    */   public ArrayList get_paths() {
/* 54 */     return this.gidilen_yollar;
/*    */   }
/*    */ 
/*    */   public boolean get_yemek_buldu() {
/* 58 */     return this.yemek_buldu;
/*    */   }
/*    */ 
/*    */   public boolean yol_uygun_mu(int gidilen_id) {
/* 62 */     boolean uygun = true;
/* 63 */     if (this.gidilen_yollar.contains(this.Ops.id_hesap(gidilen_id, Integer.parseInt(getId()))))
/*    */     {
/* 65 */       uygun = false;
/*    */     }
/* 67 */     return uygun;
/*    */   }
/*    */ 
/*    */   public void add_path(String id_hesap) {
/* 71 */     if (!this.gidilen_yollar.contains(id_hesap))
/*    */     {
/* 73 */       this.gidilen_yollar.add(id_hesap);
/*    */     }
/*    */   }
/*    */ 
/*    */   public boolean uygun_yol_var_mi(ArrayList karinca_komsular) {
/* 78 */     boolean yol_var = false;
/* 79 */     for (int m = 0; m < karinca_komsular.size(); m++)
/*    */     {
/* 81 */       yol_var = !this.gidilen_yollar.contains(this.Ops.id_hesap(Integer.parseInt(getId()), Integer.parseInt(karinca_komsular.get(m).toString())));
/* 82 */       if (yol_var)
/*    */       {
/*    */         break;
/*    */       }
/*    */     }
/* 87 */     return (yol_var) || (this.gidilen_yollar.isEmpty());
/*    */   }
/*    */ }

/* Location:           /home/rcls/Desktop/Ants/karincca.jar
 * Qualified Name:     karincca.Image_karinca
 * JD-Core Version:    0.6.2
 */