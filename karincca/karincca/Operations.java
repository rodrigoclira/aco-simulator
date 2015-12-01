/*     */ package karincca;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import javafx.scene.shape.Circle;
/*     */ import javafx.stage.FileChooser;
/*     */ import javafx.stage.FileChooserBuilder;
/*     */ 
/*     */ public class Operations
/*     */ {
/*     */   public String id_hesap(int sayi_1, int sayi_2)
/*     */   {
/*  17 */     if (sayi_1 < sayi_2)
/*     */     {
/*  19 */       return new StringBuilder().append(sayi_1).append(",").append(sayi_2).toString();
/*     */     }
/*     */ 
/*  23 */     return new StringBuilder().append(sayi_2).append(",").append(sayi_1).toString();
/*     */   }
/*     */ 
/*     */   public GrafLine[] add_edge(GrafLine[] old_array, GrafLine new_line)
/*     */   {
/*  28 */     GrafLine[] new_array = new GrafLine[old_array.length + 1];
/*  29 */     for (int m = 0; m <= old_array.length; m++)
/*     */     {
/*  31 */       if (m == old_array.length)
/*     */       {
/*  33 */         new_array[m] = new_line;
/*     */       }
/*     */       else
/*     */       {
/*  37 */         new_array[m] = old_array[m];
/*     */       }
/*     */     }
/*  40 */     return new_array;
/*     */   }
/*     */ 
/*     */   public Image_karinca[] add_circle(Image_karinca[] old_array, Image_karinca new_circle) {
/*  44 */     Image_karinca[] new_array = new Image_karinca[old_array.length + 1];
/*  45 */     for (int m = 0; m <= old_array.length; m++)
/*     */     {
/*  47 */       if (m == old_array.length)
/*     */       {
/*  49 */         new_array[m] = new_circle;
/*     */       }
/*     */       else
/*     */       {
/*  53 */         new_array[m] = old_array[m];
/*     */       }
/*     */     }
/*  56 */     return new_array;
/*     */   }
/*     */ 
/*     */   public double calc_rotate(int eski_x, int eski_y, int new_pos_x, int new_pos_y)
/*     */   {
/*  61 */     int fark_x = eski_x - new_pos_x;
/*  62 */     int fark_y = eski_y - new_pos_y;
/*     */     double degree;
/*     */     double degree;
/*  63 */     if ((fark_x != 0) && (fark_y != 0))
/*     */     {
/*  65 */       degree = 180.0D - Math.toDegrees(Math.atan(fark_x / fark_y));
/*     */     }
/*     */     else
/*     */     {
/*  69 */       degree = Math.toDegrees(Math.atan(fark_x / fark_y));
/*     */     }
/*  71 */     return degree;
/*     */   }
/*     */ 
/*     */   public void write_file(GrafLine[] edges, int karinca_sayisi, double buharlasma, int q_katsayisi, double alpha, double beta, int grid_width) {
/*  75 */     FileChooser file_chooser = FileChooserBuilder.create().title("Graf Kaydet").build();
/*  76 */     File selected_file = file_chooser.showSaveDialog(null);
/*  77 */     if (selected_file == null)
/*     */     {
/*  79 */       return;
/*     */     }
/*  81 */     String saved_file = selected_file.getAbsolutePath();
/*  82 */     String str = "";
/*     */     try
/*     */     {
/*  86 */       BufferedWriter bw = new BufferedWriter(new FileWriter(new File(saved_file), false));
/*  87 */       for (int m = 0; m < edges.length; m++)
/*     */       {
/*  89 */         String temp = edges[m].getId();
/*  90 */         temp = new StringBuilder().append(Integer.parseInt(temp.split(",")[0]) % grid_width).append(";").append(Integer.parseInt(temp.split(",")[0]) / grid_width).append(",").append(Integer.parseInt(temp.split(",")[1]) % grid_width).append(";").append(Integer.parseInt(temp.split(",")[1]) / grid_width).toString();
/*  91 */         str = new StringBuilder().append(str).append(temp).append(m != edges.length - 1 ? "/" : "").toString();
/*     */       }
/*  93 */       bw.write(str);
/*  94 */       bw.newLine();
/*  95 */       bw.write(new StringBuilder().append(karinca_sayisi).append("/").append(buharlasma).append("/").append(q_katsayisi).append("/").append(alpha).append("/").append(beta).toString());
/*  96 */       bw.close();
/*     */     }
/*     */     catch (IOException ex)
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public String read_file(Circle[][] circle_grid) {
/* 104 */     FileChooser file_chooser = FileChooserBuilder.create().title("Graf Oku").build();
/* 105 */     File selected_file = file_chooser.showOpenDialog(null);
/* 106 */     if (selected_file == null)
/*     */     {
/* 108 */       return "";
/*     */     }
/* 110 */     String read_file = selected_file.getAbsolutePath();
/* 111 */     String str = "";
/*     */     try
/*     */     {
/* 115 */       BufferedReader bw = new BufferedReader(new FileReader(read_file));
/* 116 */       while (bw.ready())
/*     */       {
/* 118 */         str = new StringBuilder().append(str).append(bw.readLine()).append("&").toString();
/*     */       }
/* 120 */       bw.close();
/*     */     }
/*     */     catch (IOException ex)
/*     */     {
/*     */     }
/* 125 */     return str;
/*     */   }
/*     */ }

/* Location:           /home/rcls/Desktop/Ants/karincca.jar
 * Qualified Name:     karincca.Operations
 * JD-Core Version:    0.6.2
 */