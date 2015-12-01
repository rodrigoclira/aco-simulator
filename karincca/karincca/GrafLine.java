/*     */ package karincca;
/*     */ 
/*     */ import javafx.beans.property.BooleanProperty;
/*     */ import javafx.beans.property.SimpleDoubleProperty;
/*     */ import javafx.beans.value.ChangeListener;
/*     */ import javafx.beans.value.ObservableValue;
/*     */ import javafx.scene.Cursor;
/*     */ import javafx.scene.paint.Color;
/*     */ import javafx.scene.shape.Line;
/*     */ import javafx.scene.text.Text;
/*     */ import javafx.scene.text.TextBuilder;
/*     */ 
/*     */ public class GrafLine extends Line
/*     */ {
/*     */   private double line_length;
/*     */   private double start_x;
/*     */   private double start_y;
/*     */   private double end_x;
/*     */   private double end_y;
/*  14 */   private double opacity_minimum = 0.1D; private double feromon_minimum = 1.0D;
/*     */   private double feromon_buharlasma;
/*     */   private SimpleDoubleProperty feromon_maddesi;
/*     */   private int komsu_1;
/*     */   private int komsu_2;
/*     */   private Text new_text;
/*     */ 
/*     */   public GrafLine(String id, double startX, double startY, double endX, double endY, double buharlasma, int komsu_1, int komsu_2)
/*     */   {
/*  20 */     super(startX, startY, endX, endY);
/*  21 */     setId(id);
/*  22 */     setStrokeWidth(7.0D);
/*  23 */     setCursor(Cursor.HAND);
/*  24 */     this.start_x = startX;
/*  25 */     this.start_y = startY;
/*  26 */     this.end_x = endX;
/*  27 */     this.end_y = endY;
/*  28 */     this.feromon_buharlasma = (1.0D - buharlasma / 100.0D);
/*  29 */     setStroke(Color.rgb(255, 0, 0, this.opacity_minimum));
/*  30 */     this.line_length = Math.sqrt(Math.pow(Math.abs(this.start_x - this.end_x), 2.0D) + Math.pow(Math.abs(this.start_y - this.end_y), 2.0D));
/*  31 */     this.feromon_maddesi = new SimpleDoubleProperty(this.feromon_minimum);
/*  32 */     this.feromon_maddesi.addListener(new ChangeListener()
/*     */     {
/*     */       public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
/*     */       {
/*  37 */         GrafLine.this.new_text.setText(Math.round(newValue.doubleValue()) + " - " + (int)GrafLine.this.line_length);
/*     */       }
/*     */     });
/*  40 */     this.komsu_1 = komsu_1;
/*  41 */     this.komsu_2 = komsu_2;
/*     */   }
/*     */ 
/*     */   public int get_komsu_1() {
/*  45 */     return this.komsu_1;
/*     */   }
/*     */ 
/*     */   public int get_komsu_2() {
/*  49 */     return this.komsu_2;
/*     */   }
/*     */ 
/*     */   public void update_feromon() {
/*  53 */     this.feromon_maddesi.set(this.feromon_minimum + this.feromon_maddesi.doubleValue() * this.feromon_buharlasma);
/*  54 */     Color line_color = (Color)getStroke();
/*  55 */     double opacity = opacity_hesabi();
/*  56 */     line_color = Color.rgb(255, (int)line_color.getGreen(), (int)line_color.getBlue(), opacity);
/*  57 */     setStroke(line_color);
/*     */   }
/*     */ 
/*     */   public void update_feromon_2(double q_katsayisi, int toplam_uzunluk) {
/*  61 */     this.feromon_maddesi.set(this.feromon_maddesi.doubleValue() + q_katsayisi / toplam_uzunluk);
/*  62 */     Color line_color = (Color)getStroke();
/*  63 */     double opacity = opacity_hesabi();
/*  64 */     line_color = Color.rgb(255, (int)line_color.getGreen(), (int)line_color.getBlue(), opacity);
/*  65 */     setStroke(line_color);
/*     */   }
/*     */ 
/*     */   public double get_feromon() {
/*  69 */     return this.feromon_maddesi.doubleValue();
/*     */   }
/*     */ 
/*     */   public void set_feromon_zero() {
/*  73 */     this.feromon_maddesi.set(this.feromon_minimum);
/*  74 */     setStroke(Color.rgb(255, 0, 0, this.opacity_minimum));
/*     */   }
/*     */ 
/*     */   public double get_uzunluk() {
/*  78 */     return this.line_length;
/*     */   }
/*     */ 
/*     */   private double opacity_hesabi() {
/*  82 */     double sonuc = this.feromon_maddesi.doubleValue() / 100.0D;
/*  83 */     if (sonuc < this.opacity_minimum)
/*     */     {
/*  85 */       sonuc = this.opacity_minimum;
/*     */     }
/*  87 */     if (sonuc > 1.0D)
/*     */     {
/*  89 */       sonuc = 1.0D;
/*     */     }
/*  91 */     return sonuc;
/*     */   }
/*     */ 
/*     */   public void set_buharlasma(double buharlasma) {
/*  95 */     this.feromon_buharlasma = (1.0D - buharlasma / 100.0D);
/*     */   }
/*     */ 
/*     */   public Text text_weight() {
/*  99 */     this.new_text = TextBuilder.create().build();
/* 100 */     this.new_text.setText(Math.round(this.feromon_maddesi.doubleValue()) + " - " + (int)this.line_length);
/* 101 */     if (getStartX() > getEndX())
/*     */     {
/* 103 */       this.new_text.setLayoutX(getStartX() - Math.abs(getStartX() - getEndX()) / 2.0D);
/*     */     }
/*     */     else
/*     */     {
/* 107 */       this.new_text.setLayoutX(getStartX() + Math.abs(getStartX() - getEndX()) / 2.0D);
/*     */     }
/* 109 */     if (getStartY() > getEndY())
/*     */     {
/* 111 */       this.new_text.setLayoutY(getStartY() - Math.abs(getStartY() - getEndY()) / 2.0D);
/*     */     }
/*     */     else
/*     */     {
/* 115 */       this.new_text.setLayoutY(getStartY() + Math.abs(getStartY() - getEndY()) / 2.0D);
/*     */     }
/* 117 */     this.new_text.visibleProperty().bind(visibleProperty());
/* 118 */     return this.new_text;
/*     */   }
/*     */ }

/* Location:           /home/rcls/Desktop/Ants/karincca.jar
 * Qualified Name:     karincca.GrafLine
 * JD-Core Version:    0.6.2
 */