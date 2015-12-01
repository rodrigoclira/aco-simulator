/*    */ package karincca;
/*    */ 
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.scene.Node;
/*    */ import javafx.scene.Scene;
/*    */ import javafx.scene.image.Image;
/*    */ import javafx.scene.image.ImageView;
/*    */ import javafx.scene.image.ImageViewBuilder;
/*    */ import javafx.scene.layout.VBox;
/*    */ import javafx.scene.layout.VBoxBuilder;
/*    */ import javafx.scene.text.Font;
/*    */ import javafx.scene.text.Text;
/*    */ import javafx.scene.text.TextBuilder;
/*    */ import javafx.stage.Stage;
/*    */ import javafx.stage.StageBuilder;
/*    */ 
/*    */ public class Formuller
/*    */ {
/*    */   private static Stage stage_formul;
/*    */   private static ImageView img_formul_feromon;
/*    */   private static ImageView img_formul_secim;
/*    */   private static Text txt_formul_feromon;
/*    */   private static Text txt_formul_secim;
/*    */   private static Font my_font;
/*    */ 
/*    */   public static void show()
/*    */   {
/* 23 */     my_font = Font.font("Times New Roman", 18.0D);
/* 24 */     stage_formul = prepare_stage_formul();
/* 25 */     stage_formul.getIcons().add(new Image(Karincca.class.getResourceAsStream("imgs/info.png")));
/* 26 */     stage_formul.show();
/*    */   }
/*    */ 
/*    */   private static Stage prepare_stage_formul() {
/* 30 */     VBox group_fomuller = prepare_group_formuller();
/* 31 */     return ((StageBuilder)((StageBuilder)StageBuilder.create().fullScreen(false).resizable(false).scene(new Scene(group_fomuller)).title("Formulas").width(330.0D)).height(280.0D)).build();
/*    */   }
/*    */ 
/*    */   private static VBox prepare_group_formuller() {
/* 35 */     txt_formul_feromon = TextBuilder.create().text("Feromon maddesi hesaplama formülü").font(my_font).build();
/* 36 */     img_formul_feromon = ImageViewBuilder.create().image(new Image(Karincca.class.getResourceAsStream("imgs/feromon_formul.jpg"))).fitWidth(300.0D).fitHeight(80.0D).build();
/* 37 */     txt_formul_secim = TextBuilder.create().text("Nokta seçimi hesaplama formülü").font(my_font).build();
/* 38 */     img_formul_secim = ImageViewBuilder.create().image(new Image(Karincca.class.getResourceAsStream("imgs/yol_secim_formul.jpg"))).fitWidth(250.0D).fitHeight(90.0D).build();
/* 39 */     return ((VBoxBuilder)((VBoxBuilder)((VBoxBuilder)VBoxBuilder.create().children(new Node[] { txt_formul_feromon, img_formul_feromon, txt_formul_secim, img_formul_secim })).spacing(10.0D).layoutX(10.0D)).layoutY(10.0D)).build();
/*    */   }
/*    */ }

/* Location:           /home/rcls/Desktop/Ants/karincca.jar
 * Qualified Name:     karincca.Formuller
 * JD-Core Version:    0.6.2
 */