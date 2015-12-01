/*    */ package karincca;
/*    */ 
/*    */ import javafx.collections.ObservableList;
/*    */ import javafx.event.ActionEvent;
/*    */ import javafx.event.EventHandler;
/*    */ import javafx.geometry.Pos;
/*    */ import javafx.geometry.VPos;
/*    */ import javafx.scene.Node;
/*    */ import javafx.scene.SceneBuilder;
/*    */ import javafx.scene.control.Button;
/*    */ import javafx.scene.control.ButtonBuilder;
/*    */ import javafx.scene.image.Image;
/*    */ import javafx.scene.layout.VBox;
/*    */ import javafx.scene.layout.VBoxBuilder;
/*    */ import javafx.scene.text.Font;
/*    */ import javafx.scene.text.Text;
/*    */ import javafx.scene.text.TextAlignment;
/*    */ import javafx.scene.text.TextBuilder;
/*    */ import javafx.stage.Stage;
/*    */ import javafx.stage.StageBuilder;
/*    */ 
/*    */ public class Message_box
/*    */ {
/* 22 */   public static int info_message = 0;
/* 23 */   public static int warning_message = 1;
/*    */   private static Stage new_stage;
/*    */   private static Text new_text;
/*    */   private static Button new_button;
/*    */   private static VBox new_group;
/*    */   private static Font my_font;
/*    */ 
/*    */   public static void show(String mesaj, String baslik, int message_type)
/*    */   {
/* 31 */     my_font = Font.font("Times New Roman", 18.0D);
/* 32 */     new_stage = ((StageBuilder)((StageBuilder)StageBuilder.create().title(baslik).resizable(false).width(300.0D)).height(170.0D)).build();
/* 33 */     new_text = TextBuilder.create().text(mesaj).wrappingWidth(260.0D).textOrigin(VPos.CENTER).textAlignment(TextAlignment.CENTER).font(my_font).build();
/* 34 */     new_button = ((ButtonBuilder)((ButtonBuilder)((ButtonBuilder)((ButtonBuilder)ButtonBuilder.create().text("Tamam")).prefWidth(100.0D)).prefHeight(25.0D)).onAction(new EventHandler()
/*    */     {
/*    */       public void handle(ActionEvent event)
/*    */       {
/* 39 */         Message_box.new_stage.close();
/*    */       }
/*    */     })).build();
/*    */ 
/* 42 */     new_group = ((VBoxBuilder)((VBoxBuilder)((VBoxBuilder)((VBoxBuilder)VBoxBuilder.create().layoutY(10.0D)).alignment(Pos.TOP_CENTER).spacing(30.0D).children(new Node[] { new_text, new_button })).prefWidth(300.0D)).prefHeight(200.0D)).build();
/* 43 */     new_stage.setScene(SceneBuilder.create().root(new_group).build());
/* 44 */     if (message_type == warning_message)
/*    */     {
/* 46 */       new_stage.getIcons().add(new Image(Karincca.class.getResourceAsStream("imgs/warning.png")));
/*    */     }
/* 48 */     else if (message_type == info_message)
/*    */     {
/* 50 */       new_stage.getIcons().add(new Image(Karincca.class.getResourceAsStream("imgs/info.png")));
/*    */     }
/* 52 */     new_stage.show();
/*    */   }
/*    */ }

/* Location:           /home/rcls/Desktop/Ants/karincca.jar
 * Qualified Name:     karincca.Message_box
 * JD-Core Version:    0.6.2
 */