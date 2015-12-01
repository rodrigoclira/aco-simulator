/*    */ package karincca;
/*    */ 
/*    */ import javafx.animation.TranslateTransition;
/*    */ import javafx.animation.TranslateTransitionBuilder;
/*    */ import javafx.scene.Cursor;
/*    */ import javafx.scene.image.Image;
/*    */ import javafx.scene.image.ImageView;
/*    */ import javafx.scene.image.ImageViewBuilder;
/*    */ import javafx.scene.shape.Circle;
/*    */ import javafx.scene.shape.CircleBuilder;
/*    */ import javafx.scene.text.Text;
/*    */ import javafx.scene.text.TextBuilder;
/*    */ import javafx.util.Duration;
/*    */ 
/*    */ public class Builders
/*    */ {
/*    */   public TranslateTransition build_translate_transition(Image_karinca apply_to, double animation_speed, int new_pos_x, int new_pos_y, GrafLine line)
/*    */   {
/* 19 */     return ((TranslateTransitionBuilder)((TranslateTransitionBuilder)((TranslateTransitionBuilder)((TranslateTransitionBuilder)((TranslateTransitionBuilder)TranslateTransitionBuilder.create().duration(Duration.millis(line.get_uzunluk() / animation_speed)).autoReverse(false)).cycleCount(1)).node(apply_to).targetFramerate(30.0D)).fromX(apply_to.getTranslateX()).fromY(apply_to.getTranslateY()).toX(new_pos_x).toY(new_pos_y).autoReverse(false)).cycleCount(1)).build();
/*    */   }
/*    */ 
/*    */   public Image_karinca build_image_karinca(int id, int pos_x, int pos_y, String name) {
/* 23 */     Image_karinca yeni_karinca = new Image_karinca(id, pos_x, pos_y, name);
/* 24 */     return yeni_karinca;
/*    */   }
/*    */ 
/*    */   public Text build_text(String text) {
/* 28 */     return TextBuilder.create().text(text).build();
/*    */   }
/*    */ 
/*    */   public Circle build_circle_ex() {
/* 32 */     return CircleBuilder.create().build();
/*    */   }
/*    */ 
/*    */   public Circle build_circle_grid(int id, int pos_x, int pos_y) {
/* 36 */     return ((CircleBuilder)((CircleBuilder)((CircleBuilder)CircleBuilder.create().id(id + "")).cursor(Cursor.HAND)).centerX(pos_x).centerY(pos_y).radius(7.0D).fill(Colors_and_shapes.color_grid)).build();
/*    */   }
/*    */ 
/*    */   public GrafLine build_line(String id, double start_x, double start_y, double end_x, double end_y, double b, int komsu_1, int komsu_2) {
/* 40 */     GrafLine new_line = new GrafLine(id, start_x, start_y, end_x, end_y, b, komsu_1, komsu_2);
/* 41 */     return new_line;
/*    */   }
/*    */ 
/*    */   public ImageView build_image() {
/* 45 */     return ImageViewBuilder.create().fitWidth(20.0D).fitHeight(20.0D).image(new Image(Karincca.class.getResourceAsStream("imgs/ant.png"))).build();
/*    */   }
/*    */ 
/*    */   private double total_length(double translateX, double translateY, int new_pos_x, int new_pos_y) {
/* 49 */     return Math.sqrt(Math.pow(Math.abs(translateX - translateY), 2.0D) + Math.pow(Math.abs(new_pos_x - new_pos_y), 2.0D));
/*    */   }
/*    */ }

/* Location:           /home/rcls/Desktop/Ants/karincca.jar
 * Qualified Name:     karincca.Builders
 * JD-Core Version:    0.6.2
 */