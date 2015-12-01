/*    */ package karincca;
/*    */ 
/*    */ import javafx.scene.Node;
/*    */ import javafx.scene.Scene;
/*    */ import javafx.scene.layout.VBox;
/*    */ import javafx.scene.layout.VBoxBuilder;
/*    */ import javafx.scene.text.Font;
/*    */ import javafx.scene.text.Text;
/*    */ import javafx.scene.text.TextAlignment;
/*    */ import javafx.scene.text.TextBuilder;
/*    */ import javafx.stage.Stage;
/*    */ import javafx.stage.StageBuilder;
/*    */ 
/*    */ public class How_to
/*    */ {
/*    */   private static Stage stage_how_to;
/*    */   private static Text txt_how_to;
/*    */   private static Font my_font;
/*    */ 
/*    */   public static void show()
/*    */   {
/* 20 */     my_font = Font.font("Times New Roman", 18.0D);
/* 21 */     stage_how_to = prepare_stage_how_to();
/* 22 */     stage_how_to.show();
/*    */   }
/*    */ 
/*    */   private static Stage prepare_stage_how_to() {
/* 26 */     VBox group_fomuller = prepare_group_how_toler();
/* 27 */     return ((StageBuilder)((StageBuilder)StageBuilder.create().fullScreen(false).resizable(false).scene(new Scene(group_fomuller)).title("Formulas").width(600.0D)).height(400.0D)).build();
/*    */   }
/*    */ 
/*    */   private static VBox prepare_group_how_toler()
/*    */   {
/* 32 */     String aciklama = "Karınca Algoritması Simülasyonu karınca algoritmasının bir graf üzerinde en kısa yol problemine çözüm üretmesini simülasyon olarak göstermektedir.\nSimülasyonu kullanmak için öncelikle Graf Çiz butonuna tıklayın ve sol taraftaki grid üzerinde iki noktaya tıklayarak graf çizin. Daha sonra yeşil renkte gösterilen graf noktaları üzerine sağ tıklayarak yiyecek ve yuva noktası belirleyin.\nSimülasyonu başlatmadan önce karınca sayısını ayarlayın. Simülasyon sırasında karınca algoritmasında kullanılan formüllerin parametrelerini ve simülasyon hızını gerçek zamanlı değiştirebilirsiniz. Simülasyon sırasında veya durdurulduğunda bir yol üzerine sağ tıklayarak o yolu silebilirsiniz. Simülasyonu durdurup Graf Çiz butonu ile çizdiğiniz graf üzerine yeni node'lar ekleyebilirsiniz.\nSimülasyonda çizdiğiniz Graf yapısını Program menüsünden Graf Dosyaya Yaz seçeneği ile bir dosyaya kaydedebilirsiniz.\nProgram menüsündeki Graf Dosyadan Oku seçeneği ile kayıtlı bir graf yapısını dosyadan okuyup programa aktarabilirsiniz.";
/* 33 */     txt_how_to = TextBuilder.create().text(aciklama).font(my_font).textAlignment(TextAlignment.JUSTIFY).wrappingWidth(580.0D).build();
/* 34 */     return ((VBoxBuilder)((VBoxBuilder)((VBoxBuilder)VBoxBuilder.create().children(new Node[] { txt_how_to })).spacing(10.0D).layoutX(10.0D)).layoutY(10.0D)).build();
/*    */   }
/*    */ }

/* Location:           /home/rcls/Desktop/Ants/karincca.jar
 * Qualified Name:     karincca.How_to
 * JD-Core Version:    0.6.2
 */