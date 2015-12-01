/*      */ package karincca;
/*      */ 
/*      */ import java.awt.event.ActionListener;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.Random;
/*      */ import javafx.animation.Animation.Status;
/*      */ import javafx.animation.TranslateTransition;
/*      */ import javafx.application.Application;
/*      */ import javafx.application.Platform;
/*      */ import javafx.beans.property.DoubleProperty;
/*      */ import javafx.beans.property.ReadOnlyObjectProperty;
/*      */ import javafx.beans.value.ChangeListener;
/*      */ import javafx.beans.value.ObservableValue;
/*      */ import javafx.collections.ObservableList;
/*      */ import javafx.concurrent.Task;
/*      */ import javafx.event.EventHandler;
/*      */ import javafx.geometry.Pos;
/*      */ import javafx.geometry.Rectangle2D;
/*      */ import javafx.scene.Group;
/*      */ import javafx.scene.GroupBuilder;
/*      */ import javafx.scene.Node;
/*      */ import javafx.scene.Scene;
/*      */ import javafx.scene.SceneBuilder;
/*      */ import javafx.scene.control.Button;
/*      */ import javafx.scene.control.ButtonBuilder;
/*      */ import javafx.scene.control.ContextMenu;
/*      */ import javafx.scene.control.ContextMenuBuilder;
/*      */ import javafx.scene.control.Menu;
/*      */ import javafx.scene.control.MenuBar;
/*      */ import javafx.scene.control.MenuBarBuilder;
/*      */ import javafx.scene.control.MenuBuilder;
/*      */ import javafx.scene.control.MenuItem;
/*      */ import javafx.scene.control.MenuItemBuilder;
/*      */ import javafx.scene.control.Slider;
/*      */ import javafx.scene.control.SliderBuilder;
/*      */ import javafx.scene.control.ToggleButton;
/*      */ import javafx.scene.control.ToggleButtonBuilder;
/*      */ import javafx.scene.image.Image;
/*      */ import javafx.scene.image.ImageView;
/*      */ import javafx.scene.input.KeyEvent;
/*      */ import javafx.scene.input.MouseButton;
/*      */ import javafx.scene.input.MouseEvent;
/*      */ import javafx.scene.layout.HBox;
/*      */ import javafx.scene.layout.HBoxBuilder;
/*      */ import javafx.scene.layout.VBox;
/*      */ import javafx.scene.layout.VBoxBuilder;
/*      */ import javafx.scene.shape.Circle;
/*      */ import javafx.scene.text.Text;
/*      */ import javafx.stage.Screen;
/*      */ import javafx.stage.Stage;
/*      */ import javax.swing.Timer;
/*      */ 
/*      */ public class Karincca extends Application
/*      */ {
/*      */   private Group group_grid;
/*      */   private Group group_root;
/*      */   private Group group_left;
/*      */   private VBox group_right;
/*      */   private VBox group_time;
/*      */   private VBox group_buharlasma;
/*      */   private VBox group_q;
/*      */   private VBox group_ant_count;
/*      */   private VBox group_alpha_beta;
/*      */   private VBox group_aciklama;
/*      */   private HBox group_graf_buttons;
/*      */   private HBox group_sim_buttons;
/*      */   private Slider slider_animation_timer;
/*      */   private Slider slider_feromon_ucuculuk;
/*      */   private Slider slider_q_katsayisi;
/*      */   private Slider slider_ant_count;
/*      */   private Slider slider_alpha;
/*      */   private Slider slider_beta;
/*      */   private Text txt_animation_timer;
/*      */   private Text txt_feromon_ucuculuk;
/*      */   private Text txt_q_katsayisi;
/*      */   private Text txt_ant_count;
/*      */   private Text txt_alpha;
/*      */   private Text txt_beta;
/*      */   private Text txt_food_ex;
/*      */   private Text txt_cave_ex;
/*      */   private Text txt_ant_ex;
/*      */   private Text txt_graf_ex;
/*      */   private Text txt_yol_bilgi;
/*      */   private ContextMenu contex_menu_circle;
/*      */   private ContextMenu contex_menu_edge;
/*      */   private MenuItem contex_menu_item_food;
/*      */   private MenuItem contex_menu_item_cave;
/*      */   private MenuItem contex_menu_item_delete_circle;
/*      */   private MenuItem contex_menu_item_delete_edge;
/*      */   private MenuItem menu_item_formuller;
/*      */   private MenuItem menu_item_how_to;
/*      */   private MenuItem menu_item_write_file;
/*      */   private MenuItem menu_item_read_file;
/*      */   private ToggleButton button_draw_graf;
/*      */   private Button button_clear_graf;
/*      */   private Button button_start_sim;
/*      */   private Button button_stop_sim;
/*      */   private Circle circle_last;
/*      */   private Circle circle_context;
/*      */   private Circle circle_food_ex;
/*      */   private Circle circle_cave_ex;
/*      */   private Circle circle_graf_ex;
/*      */   private TranslateTransition[] animations;
/*      */   private ImageView image_ant_ex;
/*      */   private GrafLine line_context;
/*      */   private Timer my_timer;
/*      */   private Image_karinca[] karincalar;
/*      */   private Circle[][] circle_grid;
/*      */   private int[][] komsuluklar;
/*      */   private ArrayList olasiliklar;
/*      */   private ArrayList karinca_komsular;
/*      */   private GrafLine[] edges;
/*      */   private Builders my_builder;
/*      */   private Scene my_scene;
/*      */   private Random my_random;
/*      */   private Menu my_menu;
/*      */   private Operations Ops;
/*      */   private MenuBar my_menu_bar;
/*      */   private Stage my_stage;
/*   79 */   private int offset_x = 10; private int offset_y = 10; private int graf_aralik = 45; private int q_katsayisi = 5000; private int karinca_sayisi = 100; private int right_width = 300;
/*      */   private int grid_width;
/*      */   private int grid_height;
/*      */   private int ekran_width;
/*      */   private int ekran_height;
/*   80 */   private double buharlasma = 12.0D;
/*      */   private double animation_time;
/*   80 */   private double alpha = 1.5D; private double beta = -0.2D;
/*   81 */   private int animation_time_coef = 20;
/*      */ 
/*      */   public void start(Stage primaryStage)
/*      */   {
/*   85 */     this.my_stage = primaryStage;
/*   86 */     this.animation_time = (1.0D / this.animation_time_coef);
/*   87 */     this.animations = new TranslateTransition[0];
/*   88 */     prepare_variables();
/*   89 */     this.group_root = ((GroupBuilder)((GroupBuilder)GroupBuilder.create().layoutX(this.offset_x)).layoutY(this.offset_y)).build();
/*   90 */     this.group_right = prepare_group_right();
/*   91 */     this.group_left = prepare_group_left();
/*   92 */     this.my_timer = new Timer(1000, new ActionListener()
/*      */     {
/*      */       public void actionPerformed(java.awt.event.ActionEvent e)
/*      */       {
/*   97 */         Platform.runLater(new Task()
/*      */         {
/*      */           protected Object call()
/*      */             throws Exception
/*      */           {
/*  102 */             Karincca.this.feromon_update();
/*  103 */             return Integer.valueOf(1);
/*      */           }
/*      */         });
/*      */       }
/*      */     });
/*  108 */     this.group_root.getChildren().addAll(new Node[] { this.group_left, this.group_right });
/*  109 */     this.my_scene = SceneBuilder.create().width(this.ekran_width - 50).height(this.ekran_height - 100).root(this.group_root).onKeyPressed(scene_key_pressed()).build();
/*  110 */     prepare_stage();
/*      */   }
/*      */ 
/*      */   public void stop() throws Exception
/*      */   {
/*  115 */     this.my_timer.stop();
/*      */   }
/*      */ 
/*      */   private void feromon_update() {
/*  119 */     for (int m = 0; m < this.edges.length; m++)
/*      */     {
/*  121 */       this.edges[m].update_feromon();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void feromon_update(Image_karinca image_karinca) {
/*  126 */     ArrayList gecici = image_karinca.get_paths();
/*  127 */     int path_length = 0;
/*  128 */     for (int m = 0; m < gecici.size(); m++)
/*      */     {
/*  130 */       for (int k = 0; k < this.edges.length; k++)
/*      */       {
/*  132 */         if (this.edges[k].getId().equals(gecici.get(m).toString()))
/*      */         {
/*  134 */           path_length = (int)(path_length + this.edges[k].get_uzunluk());
/*  135 */           break;
/*      */         }
/*      */       }
/*      */     }
/*  139 */     for (int m = 0; m < gecici.size(); m++)
/*      */     {
/*  141 */       for (int k = 0; k < this.edges.length; k++)
/*      */       {
/*  143 */         if (this.edges[k].getId().equals(gecici.get(m).toString()))
/*      */         {
/*  145 */           this.edges[k].update_feromon_2(this.q_katsayisi, path_length);
/*  146 */           break;
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void animasyon_olustur(String karinca_name) {
/*  153 */     int m = -1;
/*  154 */     for (int t = 0; t < this.karincalar.length; t++)
/*      */     {
/*  156 */       if (this.karincalar[t].get_name().equals(karinca_name))
/*      */       {
/*  158 */         m = t;
/*  159 */         break;
/*      */       }
/*      */     }
/*  162 */     int eski_id = Integer.parseInt(this.karincalar[m].getId());
/*  163 */     if (this.circle_grid[(eski_id / this.grid_width)][(eski_id % this.grid_width)].getFill() == Colors_and_shapes.color_food)
/*      */     {
/*  165 */       if (!this.karincalar[m].get_yemek_buldu())
/*      */       {
/*  167 */         feromon_update(this.karincalar[m]);
/*  168 */         this.karincalar[m].clear_paths();
/*      */       }
/*  170 */       this.karincalar[m].set_yemek_buldu(true);
/*      */     }
/*  172 */     else if (this.circle_grid[(eski_id / this.grid_width)][(eski_id % this.grid_width)].getFill() == Colors_and_shapes.color_cave)
/*      */     {
/*  174 */       if (this.karincalar[m].get_yemek_buldu())
/*      */       {
/*  176 */         feromon_update(this.karincalar[m]);
/*  177 */         this.karincalar[m].clear_paths();
/*      */       }
/*  179 */       this.karincalar[m].set_yemek_buldu(false);
/*      */     }
/*  181 */     this.karinca_komsular.clear();
/*  182 */     for (int j = 0; j < this.komsuluklar[eski_id].length; j++)
/*      */     {
/*  184 */       if (this.komsuluklar[eski_id][j] == 1)
/*      */       {
/*  186 */         this.karinca_komsular.add(Integer.valueOf(j));
/*      */       }
/*      */     }
/*  189 */     Collections.shuffle(this.karinca_komsular);
/*  190 */     this.olasiliklar.clear();
/*  191 */     double toplam_deger = 0.0D;
/*  192 */     for (int i = 0; i < this.karinca_komsular.size(); i++)
/*      */     {
/*  194 */       for (int k = 0; k < this.edges.length; k++)
/*      */       {
/*  196 */         if (this.edges[k].getId().equals(this.Ops.id_hesap(eski_id, Integer.parseInt(this.karinca_komsular.get(i).toString()))))
/*      */         {
/*  198 */           toplam_deger += Math.pow(this.edges[k].get_feromon(), this.alpha) * Math.pow(this.edges[k].get_uzunluk(), this.beta);
/*  199 */           this.olasiliklar.add(Double.valueOf(Math.pow(this.edges[k].get_feromon(), this.alpha) * Math.pow(this.edges[k].get_uzunluk(), this.beta)));
/*  200 */           break;
/*      */         }
/*      */       }
/*      */     }
/*  204 */     int rastgele_max = 0;
/*  205 */     for (int i = 0; i < this.olasiliklar.size(); i++)
/*      */     {
/*  207 */       double komsu_deger = Double.parseDouble(this.olasiliklar.get(i).toString());
/*  208 */       if (toplam_deger == 0.0D)
/*      */       {
/*  210 */         this.olasiliklar.set(i, Integer.valueOf(1));
/*  211 */         rastgele_max++;
/*      */       }
/*      */       else
/*      */       {
/*  215 */         int gecici = (int)(komsu_deger / toplam_deger * 1000.0D) < 1 ? 1 : (int)(komsu_deger / toplam_deger * 1000.0D);
/*  216 */         this.olasiliklar.set(i, Integer.valueOf(gecici));
/*  217 */         rastgele_max += gecici;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  220 */     int gidilen_yer_id = -1;
/*  221 */     boolean olasilik_var = this.olasiliklar.size() > 1;
/*  222 */     boolean uygun_yol_var = this.karincalar[m].uygun_yol_var_mi(this.karinca_komsular);
/*  223 */     if (!uygun_yol_var);
/*      */     do
/*      */     {
/*  231 */       int rastgele = this.my_random.nextInt(rastgele_max);
/*  232 */       for (int k = 0; k < this.olasiliklar.size(); k++)
/*      */       {
/*  234 */         rastgele = (int)(rastgele - Double.parseDouble(this.olasiliklar.get(k).toString()));
/*  235 */         if (rastgele < 0)
/*      */         {
/*  237 */           gidilen_yer_id = Integer.parseInt(this.karinca_komsular.get(k).toString());
/*  238 */           break;
/*      */         }
/*      */       }
/*      */     }
/*  242 */     while (((olasilik_var) && (gidilen_yer_id == Integer.parseInt(this.karincalar[m].get_last_id()))) || (
/*  244 */       (!this.karincalar[m].yol_uygun_mu(gidilen_yer_id)) && (uygun_yol_var) && (olasilik_var)));
/*  245 */     this.karincalar[m].setId(gidilen_yer_id + "");
/*  246 */     this.karincalar[m].set_last_id(eski_id + "");
/*  247 */     this.karincalar[m].add_path(this.Ops.id_hesap(eski_id, gidilen_yer_id));
/*  248 */     int new_pos_x = this.offset_x + this.graf_aralik * (gidilen_yer_id % this.grid_width) - 10;
/*  249 */     int new_pos_y = this.offset_y + this.graf_aralik * (gidilen_yer_id / this.grid_width) - 10;
/*  250 */     GrafLine temp_line = this.edges[0];
/*  251 */     for (int k = 0; k < this.edges.length; k++)
/*      */     {
/*  253 */       if (this.edges[k].getId().equals(this.Ops.id_hesap(eski_id, gidilen_yer_id)))
/*      */       {
/*  255 */         temp_line = this.edges[k];
/*  256 */         break;
/*      */       }
/*      */     }
/*  259 */     int anim = -1;
/*  260 */     for (int k = 0; k < this.animations.length; k++)
/*      */     {
/*  262 */       if (((Image_karinca)this.animations[k].getNode()).get_name().equals(this.karincalar[m].get_name()))
/*      */       {
/*  264 */         anim = k;
/*  265 */         break;
/*      */       }
/*      */     }
/*  268 */     this.animations[anim] = this.my_builder.build_translate_transition(this.karincalar[m], this.animation_time, new_pos_x, new_pos_y, temp_line);
/*  269 */     int eski_x = (int)this.karincalar[m].getTranslateX(); int eski_y = (int)this.karincalar[m].getTranslateY();
/*  270 */     this.karincalar[m].setRotate(this.Ops.calc_rotate(eski_x, eski_y, new_pos_x, new_pos_y));
/*  271 */     final String recall_name = this.karincalar[m].get_name();
/*  272 */     this.animations[anim].setOnFinished(new EventHandler()
/*      */     {
/*      */       public void handle(javafx.event.ActionEvent event)
/*      */       {
/*  277 */         Karincca.this.animasyon_olustur(recall_name);
/*      */       }
/*      */     });
/*  280 */     this.animations[anim].playFromStart();
/*      */   }
/*      */ 
/*      */   private void komsuluklar_sifirla() {
/*  284 */     this.komsuluklar = new int[this.grid_width * this.grid_height][this.grid_width * this.grid_height];
/*  285 */     for (int m = 0; m < this.komsuluklar.length; m++)
/*      */     {
/*  287 */       this.komsuluklar[m][m] = 0;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void prepare_grid() {
/*  292 */     this.grid_width = ((this.ekran_width - this.offset_x - this.right_width - this.graf_aralik) / this.graf_aralik);
/*  293 */     this.grid_height = ((this.ekran_height - 2 * this.graf_aralik) / this.graf_aralik);
/*  294 */     this.circle_grid = new Circle[this.grid_height][this.grid_width];
/*  295 */     for (int m = 0; m < this.grid_height; m++)
/*      */     {
/*  297 */       for (int k = 0; k < this.grid_width; k++)
/*      */       {
/*  299 */         Circle temp_circle = this.my_builder.build_circle_grid(k + this.grid_width * m, this.offset_x + this.graf_aralik * k, this.offset_y + this.graf_aralik * m);
/*  300 */         temp_circle.setOnMouseClicked(graf_mouse_clicked());
/*  301 */         this.group_grid.getChildren().add(temp_circle);
/*  302 */         this.circle_grid[m][k] = temp_circle;
/*      */       }
/*      */     }
/*  305 */     komsuluklar_sifirla();
/*      */   }
/*      */ 
/*      */   private void komsuluk_sil(int komsu_1, int komsu_2) {
/*  309 */     this.komsuluklar[komsu_1][komsu_2] = 0;
/*  310 */     this.komsuluklar[komsu_2][komsu_1] = 0;
/*      */   }
/*      */ 
/*      */   private void komsuluk_sil(int komsu) {
/*  314 */     for (int m = 0; m < this.edges.length; m++)
/*      */     {
/*  316 */       String[] id = this.edges[m].getId().split(",");
/*  317 */       if ((Integer.parseInt(id[0]) == komsu) || (Integer.parseInt(id[1]) == komsu))
/*      */       {
/*  319 */         this.edges[m].setVisible(false);
/*      */       }
/*      */     }
/*  322 */     for (int m = 0; m < this.komsuluklar.length; m++)
/*      */     {
/*  324 */       this.komsuluklar[komsu][m] = 0;
/*  325 */       this.komsuluklar[m][komsu] = 0;
/*      */     }
/*      */   }
/*      */ 
/*      */   private void prepare_variables() {
/*  330 */     this.edges = new GrafLine[0];
/*  331 */     this.my_random = new Random();
/*  332 */     this.Ops = new Operations();
/*  333 */     this.my_builder = new Builders();
/*  334 */     this.olasiliklar = new ArrayList();
/*  335 */     this.karinca_komsular = new ArrayList();
/*  336 */     this.ekran_width = ((int)Screen.getPrimary().getBounds().getWidth());
/*  337 */     this.ekran_height = ((int)Screen.getPrimary().getBounds().getHeight());
/*      */   }
/*      */ 
/*      */   private void prepare_stage() {
/*  341 */     this.my_stage.setTitle("Ant Algorithm Simulator");
/*  342 */     this.my_stage.setScene(this.my_scene);
/*  343 */     this.my_stage.setWidth(this.ekran_width - 50);
/*  344 */     this.my_stage.setHeight(this.ekran_height - 100);
/*  345 */     this.my_stage.setFullScreen(true);
/*  346 */     this.my_stage.getIcons().add(new Image(Karincca.class.getResourceAsStream("imgs/ant.png")));
/*  347 */     this.my_stage.show();
/*      */   }
/*      */ 
/*      */   private Group prepare_group_left() {
/*  351 */     this.group_grid = GroupBuilder.create().build();
/*  352 */     prepare_grid();
/*  353 */     this.group_grid.setLayoutX(0.0D);
/*  354 */     this.group_grid.setLayoutY(40.0D);
/*  355 */     this.my_menu_bar = prepare_menu();
/*  356 */     return GroupBuilder.create().children(new Node[] { this.my_menu_bar, this.group_grid }).build();
/*      */   }
/*      */ 
/*      */   private VBox prepare_group_time() {
/*  360 */     this.slider_animation_timer = SliderBuilder.create().blockIncrement(1.0D).showTickMarks(true).majorTickUnit(1.0D).value(this.animation_time * this.animation_time_coef).min(0.0D).max(100.0D).build();
/*  361 */     this.slider_animation_timer.valueProperty().addListener(animation_timer_changed());
/*  362 */     this.txt_animation_timer = this.my_builder.build_text("Animasyon Hızı = " + (int)this.slider_animation_timer.getValue());
/*  363 */     return ((VBoxBuilder)VBoxBuilder.create().children(new Node[] { this.txt_animation_timer, this.slider_animation_timer })).spacing(5.0D).build();
/*      */   }
/*      */ 
/*      */   private VBox prepare_group_buharlasma() {
/*  367 */     this.slider_feromon_ucuculuk = SliderBuilder.create().blockIncrement(1.0D).snapToTicks(true).showTickMarks(true).majorTickUnit(1.0D).value(this.buharlasma).min(1.0D).max(90.0D).build();
/*  368 */     this.txt_feromon_ucuculuk = this.my_builder.build_text("Feromon Uçuculuğu Yüzdesi (b) = " + (int)this.slider_feromon_ucuculuk.getValue());
/*  369 */     this.slider_feromon_ucuculuk.valueProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
/*      */       {
/*  374 */         Karincca.this.txt_feromon_ucuculuk.setText("Feromon Uçuculuğu Yüzdesi (b) = " + newValue.intValue());
/*  375 */         Karincca.this.buharlasma = newValue.doubleValue();
/*  376 */         for (int m = 0; m < Karincca.this.edges.length; m++)
/*      */         {
/*  378 */           Karincca.this.edges[m].set_buharlasma(Karincca.this.buharlasma);
/*      */         }
/*      */       }
/*      */     });
/*  382 */     return ((VBoxBuilder)VBoxBuilder.create().children(new Node[] { this.txt_feromon_ucuculuk, this.slider_feromon_ucuculuk })).spacing(5.0D).build();
/*      */   }
/*      */ 
/*      */   private VBox prepare_group_q() {
/*  386 */     this.slider_q_katsayisi = SliderBuilder.create().blockIncrement(1.0D).snapToTicks(true).showTickMarks(false).majorTickUnit(1.0D).value(this.q_katsayisi / 100).min(0.0D).max(400.0D).build();
/*  387 */     this.txt_q_katsayisi = this.my_builder.build_text("Q sabiti = " + (int)this.slider_q_katsayisi.getValue());
/*  388 */     this.slider_q_katsayisi.valueProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
/*      */       {
/*  393 */         Karincca.this.txt_q_katsayisi.setText("Q sabiti = " + newValue.intValue());
/*  394 */         Karincca.this.q_katsayisi = (100 * newValue.intValue());
/*      */       }
/*      */     });
/*  397 */     return ((VBoxBuilder)VBoxBuilder.create().children(new Node[] { this.txt_q_katsayisi, this.slider_q_katsayisi })).spacing(5.0D).build();
/*      */   }
/*      */ 
/*      */   private VBox prepare_group_ant_count() {
/*  401 */     this.slider_ant_count = SliderBuilder.create().blockIncrement(1.0D).snapToTicks(true).showTickMarks(false).majorTickUnit(1.0D).value(this.karinca_sayisi).min(1.0D).max(2000.0D).build();
/*  402 */     this.txt_ant_count = this.my_builder.build_text("Karınca sayısı = " + this.karinca_sayisi);
/*  403 */     this.slider_ant_count.valueProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
/*      */       {
/*  408 */         Karincca.this.txt_ant_count.setText("Karınca sayısı = " + newValue.intValue());
/*  409 */         Karincca.this.karinca_sayisi = newValue.intValue();
/*      */       }
/*      */     });
/*  412 */     return ((VBoxBuilder)VBoxBuilder.create().children(new Node[] { this.txt_ant_count, this.slider_ant_count })).spacing(5.0D).build();
/*      */   }
/*      */ 
/*      */   private VBox prepare_group_aciklama() {
/*  416 */     this.circle_food_ex = this.my_builder.build_circle_ex();
/*  417 */     this.circle_food_ex.setRadius(14.0D);
/*  418 */     this.circle_food_ex.setFill(Colors_and_shapes.color_food);
/*  419 */     this.txt_food_ex = this.my_builder.build_text("Yiyecek Noktası");
/*  420 */     this.image_ant_ex = this.my_builder.build_image();
/*  421 */     this.txt_ant_ex = this.my_builder.build_text("Karınca");
/*  422 */     this.circle_cave_ex = this.my_builder.build_circle_ex();
/*  423 */     this.circle_cave_ex.setRadius(14.0D);
/*  424 */     this.circle_cave_ex.setFill(Colors_and_shapes.color_cave);
/*  425 */     this.txt_cave_ex = this.my_builder.build_text("Yuva Noktası");
/*  426 */     this.circle_graf_ex = this.my_builder.build_circle_ex();
/*  427 */     this.circle_graf_ex.setRadius(7.0D);
/*  428 */     this.circle_graf_ex.setFill(Colors_and_shapes.color_graf);
/*  429 */     this.txt_graf_ex = this.my_builder.build_text("Graf Noktası");
/*  430 */     HBox hbox_1 = ((HBoxBuilder)HBoxBuilder.create().children(new Node[] { this.circle_food_ex, this.txt_food_ex, this.circle_cave_ex, this.txt_cave_ex })).spacing(10.0D).alignment(Pos.BASELINE_LEFT).build();
/*  431 */     HBox hbox_2 = ((HBoxBuilder)HBoxBuilder.create().children(new Node[] { this.circle_graf_ex, this.txt_graf_ex, this.image_ant_ex, this.txt_ant_ex })).spacing(10.0D).alignment(Pos.BASELINE_LEFT).build();
/*  432 */     return ((VBoxBuilder)VBoxBuilder.create().children(new Node[] { hbox_1, hbox_2 })).spacing(10.0D).build();
/*      */   }
/*      */ 
/*      */   private VBox prepare_group_alpha_beta() {
/*  436 */     this.slider_alpha = SliderBuilder.create().blockIncrement(0.01D).snapToTicks(false).showTickMarks(true).majorTickUnit(1.0D).value(this.alpha).min(0.0D).max(10.0D).build();
/*  437 */     this.txt_alpha = this.my_builder.build_text("Alpha = " + this.slider_alpha.getValue());
/*  438 */     this.slider_alpha.valueProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
/*      */       {
/*  443 */         Karincca.this.txt_alpha.setText("Alpha = " + new DecimalFormat("#0.00").format(newValue.doubleValue()));
/*  444 */         Karincca.this.alpha = newValue.doubleValue();
/*      */       }
/*      */     });
/*  447 */     this.slider_beta = SliderBuilder.create().blockIncrement(0.01D).snapToTicks(false).showTickMarks(true).majorTickUnit(1.0D).value(this.beta).min(-1.0D).max(0.0D).build();
/*  448 */     this.txt_beta = this.my_builder.build_text("Beta = " + this.slider_beta.getValue());
/*  449 */     this.slider_beta.valueProperty().addListener(new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
/*      */       {
/*  454 */         Karincca.this.txt_beta.setText("Beta = " + new DecimalFormat("#0.00").format(newValue.doubleValue()));
/*  455 */         Karincca.this.beta = newValue.doubleValue();
/*      */       }
/*      */     });
/*  458 */     return ((VBoxBuilder)VBoxBuilder.create().children(new Node[] { this.txt_alpha, this.slider_alpha, this.txt_beta, this.slider_beta })).spacing(5.0D).build();
/*      */   }
/*      */ 
/*      */   private VBox prepare_group_right() {
/*  462 */     this.group_time = prepare_group_time();
/*  463 */     this.group_buharlasma = prepare_group_buharlasma();
/*  464 */     this.group_q = prepare_group_q();
/*  465 */     this.group_ant_count = prepare_group_ant_count();
/*  466 */     this.group_alpha_beta = prepare_group_alpha_beta();
/*  467 */     this.group_graf_buttons = prepare_group_graf_buttons();
/*  468 */     this.group_sim_buttons = prepare_group_sim_buttons();
/*  469 */     this.contex_menu_circle = prepare_contex_menu_circle();
/*  470 */     this.contex_menu_edge = prepare_contex_menu_edge();
/*  471 */     this.group_aciklama = prepare_group_aciklama();
/*  472 */     this.txt_yol_bilgi = this.my_builder.build_text("");
/*  473 */     return ((VBoxBuilder)((VBoxBuilder)((VBoxBuilder)VBoxBuilder.create().layoutX(this.ekran_width - this.right_width - 40)).spacing(5.0D).prefWidth(this.right_width)).children(new Node[] { this.group_time, this.group_ant_count, this.group_buharlasma, this.group_q, this.group_alpha_beta, this.group_graf_buttons, this.group_sim_buttons, this.group_aciklama, this.txt_yol_bilgi })).build();
/*      */   }
/*      */ 
/*      */   private HBox prepare_group_graf_buttons() {
/*  477 */     this.button_draw_graf = ((ToggleButtonBuilder)((ToggleButtonBuilder)((ToggleButtonBuilder)ToggleButtonBuilder.create().prefWidth(140.0D)).prefHeight(30.0D)).text("Graf çiz")).build();
/*  478 */     this.button_draw_graf.setOnAction(new EventHandler()
/*      */     {
/*      */       public void handle(javafx.event.ActionEvent event)
/*      */       {
/*  483 */         if (((ToggleButton)event.getSource()).isSelected())
/*      */         {
/*  485 */           ((ToggleButton)event.getSource()).setText("Graf çiziliyor");
/*  486 */           Karincca.this.button_start_sim.setDisable(true);
/*  487 */           Karincca.this.button_clear_graf.setDisable(true);
/*      */         }
/*      */         else
/*      */         {
/*  491 */           ((ToggleButton)event.getSource()).setText("Graf çiz");
/*  492 */           Karincca.this.button_start_sim.setDisable(false);
/*  493 */           Karincca.this.button_clear_graf.setDisable(false);
/*      */         }
/*  495 */         Karincca.this.circle_last = null;
/*      */       }
/*      */     });
/*  498 */     this.button_clear_graf = ((ButtonBuilder)((ButtonBuilder)((ButtonBuilder)ButtonBuilder.create().prefWidth(140.0D)).prefHeight(30.0D)).text("Graf temizle")).build();
/*  499 */     this.button_clear_graf.setOnAction(clear_graf());
/*  500 */     return ((HBoxBuilder)HBoxBuilder.create().children(new Node[] { this.button_draw_graf, this.button_clear_graf })).spacing(5.0D).build();
/*      */   }
/*      */ 
/*      */   private HBox prepare_group_sim_buttons() {
/*  504 */     this.button_start_sim = ((ButtonBuilder)((ButtonBuilder)((ButtonBuilder)ButtonBuilder.create().prefWidth(140.0D)).prefHeight(60.0D)).text("Simulasyonu\nbaşlat")).build();
/*  505 */     this.button_start_sim.setOnAction(start_sim());
/*  506 */     this.button_stop_sim = ((ButtonBuilder)((ButtonBuilder)((ButtonBuilder)((ButtonBuilder)((ButtonBuilder)ButtonBuilder.create().prefWidth(140.0D)).disable(true)).prefHeight(60.0D)).text("Simulasyonu\ndurdur")).onAction(stop_sim())).build();
/*  507 */     return ((HBoxBuilder)HBoxBuilder.create().children(new Node[] { this.button_start_sim, this.button_stop_sim })).spacing(5.0D).build();
/*      */   }
/*      */ 
/*      */   private ContextMenu prepare_contex_menu_edge() {
/*  511 */     this.contex_menu_item_delete_edge = MenuItemBuilder.create().text("Yolu sil").onAction(new EventHandler()
/*      */     {
/*      */       public void handle(javafx.event.ActionEvent event)
/*      */       {
/*  516 */         Karincca.this.line_context.setVisible(false);
/*  517 */         Karincca.this.komsuluk_sil(Karincca.this.line_context.get_komsu_1(), Karincca.this.line_context.get_komsu_2());
/*      */       }
/*      */     }).build();
/*      */ 
/*  520 */     return ContextMenuBuilder.create().items(new MenuItem[] { this.contex_menu_item_delete_edge }).build();
/*      */   }
/*      */ 
/*      */   private ContextMenu prepare_contex_menu_circle() {
/*  524 */     this.contex_menu_item_food = MenuItemBuilder.create().text("Yiyecek noktası").onAction(new EventHandler()
/*      */     {
/*      */       public void handle(javafx.event.ActionEvent event)
/*      */       {
/*  529 */         for (int m = 0; m < Karincca.this.circle_grid.length; m++)
/*      */         {
/*  531 */           for (int k = 0; k < Karincca.this.circle_grid[m].length; k++)
/*      */           {
/*  533 */             if (Karincca.this.circle_grid[m][k].getFill() == Colors_and_shapes.color_food)
/*      */             {
/*  535 */               Karincca.this.circle_grid[m][k].setFill(Colors_and_shapes.color_graf);
/*  536 */               Karincca.this.circle_grid[m][k].setRadius(7.0D);
/*      */             }
/*      */           }
/*      */         }
/*  540 */         Karincca.this.circle_context.setFill(Colors_and_shapes.color_food);
/*  541 */         Karincca.this.circle_context.setRadius(14.0D);
/*      */       }
/*      */     }).build();
/*      */ 
/*  544 */     this.contex_menu_item_cave = MenuItemBuilder.create().text("Yuva noktası").onAction(new EventHandler()
/*      */     {
/*      */       public void handle(javafx.event.ActionEvent event)
/*      */       {
/*  549 */         for (int m = 0; m < Karincca.this.circle_grid.length; m++)
/*      */         {
/*  551 */           for (int k = 0; k < Karincca.this.circle_grid[m].length; k++)
/*      */           {
/*  553 */             if (Karincca.this.circle_grid[m][k].getFill() == Colors_and_shapes.color_cave)
/*      */             {
/*  555 */               Karincca.this.circle_grid[m][k].setFill(Colors_and_shapes.color_graf);
/*  556 */               Karincca.this.circle_grid[m][k].setRadius(7.0D);
/*      */             }
/*      */           }
/*      */         }
/*  560 */         Karincca.this.circle_context.setFill(Colors_and_shapes.color_cave);
/*  561 */         Karincca.this.circle_context.setRadius(14.0D);
/*      */       }
/*      */     }).build();
/*      */ 
/*  564 */     this.contex_menu_item_delete_circle = MenuItemBuilder.create().text("Noktayı sil").onAction(new EventHandler()
/*      */     {
/*      */       public void handle(javafx.event.ActionEvent event)
/*      */       {
/*  569 */         Karincca.this.circle_context.setFill(Colors_and_shapes.color_grid);
/*  570 */         Karincca.this.circle_context.setRadius(7.0D);
/*  571 */         Karincca.this.komsuluk_sil(Integer.parseInt(Karincca.this.circle_context.getId()));
/*      */       }
/*      */     }).build();
/*      */ 
/*  574 */     return ContextMenuBuilder.create().items(new MenuItem[] { this.contex_menu_item_delete_circle, this.contex_menu_item_food, this.contex_menu_item_cave }).build();
/*      */   }
/*      */ 
/*      */   private MenuBar prepare_menu() {
/*  578 */     this.menu_item_formuller = MenuItemBuilder.create().text("Formüller ve açıklamaları").onAction(new EventHandler()
/*      */     {
/*      */       public void handle(javafx.event.ActionEvent event)
/*      */       {
/*  583 */         Formuller.show();
/*      */       }
/*      */     }).build();
/*      */ 
/*  586 */     this.menu_item_how_to = MenuItemBuilder.create().text("Nasıl kullanılır").onAction(new EventHandler()
/*      */     {
/*      */       public void handle(javafx.event.ActionEvent event)
/*      */       {
/*  591 */         How_to.show();
/*      */       }
/*      */     }).build();
/*      */ 
/*  594 */     this.menu_item_write_file = MenuItemBuilder.create().text("Graf dosyaya yaz").onAction(write_file()).build();
/*  595 */     this.menu_item_read_file = MenuItemBuilder.create().text("Graf dosyadan oku").onAction(read_file()).build();
/*  596 */     this.my_menu = ((MenuBuilder)MenuBuilder.create().text("Program")).items(new MenuItem[] { this.menu_item_formuller, this.menu_item_how_to, this.menu_item_write_file, this.menu_item_read_file }).build();
/*  597 */     return MenuBarBuilder.create().menus(new Menu[] { this.my_menu }).useSystemMenuBar(false).build();
/*      */   }
/*      */ 
/*      */   private EventHandler<MouseEvent> graf_mouse_clicked() {
/*  601 */     return new EventHandler()
/*      */     {
/*      */       public void handle(MouseEvent event)
/*      */       {
/*  606 */         if (event.getButton() == MouseButton.SECONDARY)
/*      */         {
/*  608 */           if ((!Karincca.this.button_start_sim.isDisabled()) && (((Circle)event.getSource()).getFill() == Colors_and_shapes.color_graf))
/*      */           {
/*  610 */             Karincca.this.circle_context = ((Circle)event.getSource());
/*  611 */             Karincca.this.contex_menu_circle.show(Karincca.this.circle_context, Karincca.this.circle_context.getCenterX(), Karincca.this.circle_context.getCenterY());
/*  612 */             Karincca.this.circle_last = null;
/*      */           }
/*      */         }
/*  615 */         else if (Karincca.this.button_draw_graf.isSelected())
/*      */         {
/*  617 */           Circle circle_temp = (Circle)event.getSource();
/*  618 */           circle_temp.setFill(Colors_and_shapes.color_graf);
/*  619 */           circle_temp.setRadius(7.0D);
/*  620 */           if ((Karincca.this.circle_last != null) && (!Karincca.this.circle_last.equals(circle_temp)))
/*      */           {
/*  622 */             String id = Karincca.this.Ops.id_hesap(Integer.parseInt(Karincca.this.circle_last.getId()), Integer.parseInt(circle_temp.getId()));
/*  623 */             GrafLine new_line = Karincca.this.my_builder.build_line(id, (int)Karincca.this.circle_last.getCenterX(), (int)Karincca.this.circle_last.getCenterY(), (int)circle_temp.getCenterX(), (int)circle_temp.getCenterY(), Karincca.this.buharlasma, Integer.parseInt(Karincca.this.circle_last.getId()), Integer.parseInt(circle_temp.getId()));
/*  624 */             new_line.setOnMouseClicked(Karincca.this.line_mouse_clicked());
/*  625 */             Karincca.this.group_grid.getChildren().add(new_line);
/*  626 */             Karincca.this.group_grid.getChildren().add(new_line.text_weight());
/*  627 */             new_line.toBack();
/*  628 */             Karincca.this.edges = Karincca.this.Ops.add_edge(Karincca.this.edges, new_line);
/*  629 */             Karincca.this.komsuluklar[Integer.parseInt(circle_temp.getId())][Integer.parseInt(Karincca.this.circle_last.getId())] = 1;
/*  630 */             Karincca.this.komsuluklar[Integer.parseInt(Karincca.this.circle_last.getId())][Integer.parseInt(circle_temp.getId())] = 1;
/*  631 */             Karincca.this.circle_last.setFill(Colors_and_shapes.color_graf);
/*  632 */             Karincca.this.circle_last = null;
/*      */           }
/*      */           else
/*      */           {
/*  636 */             Karincca.this.circle_last = circle_temp;
/*  637 */             Karincca.this.circle_last.setFill(Colors_and_shapes.color_last);
/*      */           }
/*      */         }
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   private EventHandler<MouseEvent> line_mouse_clicked() {
/*  645 */     return new EventHandler()
/*      */     {
/*      */       public void handle(MouseEvent event)
/*      */       {
/*  650 */         if (event.getButton() == MouseButton.SECONDARY)
/*      */         {
/*  652 */           Karincca.this.line_context = ((GrafLine)event.getSource());
/*  653 */           Karincca.this.contex_menu_edge.show(Karincca.this.line_context, event.getScreenX(), event.getScreenY());
/*      */         }
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   private EventHandler<KeyEvent> scene_key_pressed() {
/*  660 */     return new EventHandler()
/*      */     {
/*      */       public void handle(KeyEvent t)
/*      */       {
/*  665 */         if (t.getText().toLowerCase().equals("a"))
/*      */         {
/*  667 */           Karincca.this.slider_animation_timer.setValue(Karincca.this.slider_animation_timer.getValue() - 1.0D);
/*      */         }
/*  669 */         if (t.getText().toLowerCase().equals("q"))
/*      */         {
/*  671 */           Karincca.this.slider_animation_timer.setValue(Karincca.this.slider_animation_timer.getValue() + 1.0D);
/*      */         }
/*  673 */         if (t.getText().toLowerCase().equals("s"))
/*      */         {
/*  675 */           Karincca.this.slider_feromon_ucuculuk.setValue(Karincca.this.slider_feromon_ucuculuk.getValue() - 1.0D);
/*      */         }
/*  677 */         if (t.getText().toLowerCase().equals("w"))
/*      */         {
/*  679 */           Karincca.this.slider_feromon_ucuculuk.setValue(Karincca.this.slider_feromon_ucuculuk.getValue() + 1.0D);
/*      */         }
/*  681 */         if (t.getText().toLowerCase().equals("d"))
/*      */         {
/*  683 */           Karincca.this.slider_q_katsayisi.setValue(Karincca.this.slider_q_katsayisi.getValue() - 1.0D);
/*      */         }
/*  685 */         if (t.getText().toLowerCase().equals("e"))
/*      */         {
/*  687 */           Karincca.this.slider_q_katsayisi.setValue(Karincca.this.slider_q_katsayisi.getValue() + 1.0D);
/*      */         }
/*  689 */         if (t.getText().toLowerCase().equals("f"))
/*      */         {
/*  691 */           Karincca.this.slider_alpha.setValue(Karincca.this.slider_alpha.getValue() - 0.01D);
/*      */         }
/*  693 */         if (t.getText().toLowerCase().equals("r"))
/*      */         {
/*  695 */           Karincca.this.slider_alpha.setValue(Karincca.this.slider_alpha.getValue() + 0.01D);
/*      */         }
/*  697 */         if (t.getText().toLowerCase().equals("g"))
/*      */         {
/*  699 */           Karincca.this.slider_beta.setValue(Karincca.this.slider_beta.getValue() + 0.01D);
/*      */         }
/*  701 */         if (t.getText().toLowerCase().equals("t"))
/*      */         {
/*  703 */           Karincca.this.slider_beta.setValue(Karincca.this.slider_beta.getValue() - 0.01D);
/*      */         }
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   private EventHandler<javafx.event.ActionEvent> read_file() {
/*  710 */     return new EventHandler()
/*      */     {
/*      */       public void handle(javafx.event.ActionEvent event)
/*      */       {
/*  715 */         String str = Karincca.this.Ops.read_file(Karincca.this.circle_grid);
/*  716 */         if (!"".equals(str))
/*      */         {
/*      */           try
/*      */           {
/*  720 */             Karincca.this.graf_ciz(str);
/*      */           }
/*      */           catch (Exception e)
/*      */           {
/*      */           }
/*  725 */           Karincca.this.ayarlarlar_getir(str);
/*      */         }
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   private EventHandler<javafx.event.ActionEvent> write_file() {
/*  732 */     return new EventHandler()
/*      */     {
/*      */       public void handle(javafx.event.ActionEvent event)
/*      */       {
/*  737 */         if (Karincca.this.edges.length > 0)
/*      */         {
/*  739 */           Karincca.this.Ops.write_file(Karincca.this.edges, Karincca.this.karinca_sayisi, Karincca.this.buharlasma, Karincca.this.q_katsayisi, Karincca.this.alpha, Karincca.this.beta, Karincca.this.grid_width);
/*  740 */           Message_box.show("Çizilen graf ve ayarlar başarıyla kaydedildi.", "Uyarı", Message_box.info_message);
/*      */         }
/*      */         else
/*      */         {
/*  744 */           Message_box.show("Önce graf çiziniz!", "Uyarı", Message_box.warning_message);
/*      */         }
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   private void ayarlarlar_getir(String str)
/*      */   {
/*      */     try {
/*  753 */       String[] options = str.split("&")[1].split("/");
/*  754 */       this.karinca_sayisi = Integer.parseInt(options[0]);
/*  755 */       this.slider_ant_count.setValue(this.karinca_sayisi);
/*  756 */       this.buharlasma = Double.parseDouble(options[1]);
/*  757 */       this.slider_feromon_ucuculuk.setValue(this.buharlasma);
/*  758 */       this.q_katsayisi = Integer.parseInt(options[2]);
/*  759 */       this.slider_q_katsayisi.setValue(this.q_katsayisi / 100);
/*  760 */       this.alpha = Double.parseDouble(options[3]);
/*  761 */       this.slider_alpha.setValue(this.alpha);
/*  762 */       this.beta = Double.parseDouble(options[4]);
/*  763 */       this.slider_beta.setValue(this.beta);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   private void graf_ciz(String str) {
/*  771 */     String[] edge_ids = str.split("&")[0].split("/");
/*      */ 
/*  774 */     int en_buyuk_x = -1; int en_buyuk_y = -1;
/*  775 */     for (int m = 0; m < edge_ids.length; m++)
/*      */     {
/*  777 */       String[] temp_id = edge_ids[m].split(",");
/*  778 */       int x1 = Integer.parseInt(temp_id[0].split(";")[0]);
/*  779 */       int y1 = Integer.parseInt(temp_id[0].split(";")[1]);
/*  780 */       int x2 = Integer.parseInt(temp_id[1].split(";")[0]);
/*  781 */       int y2 = Integer.parseInt(temp_id[1].split(";")[1]);
/*  782 */       if (x1 > en_buyuk_x)
/*      */       {
/*  784 */         en_buyuk_x = x1;
/*      */       }
/*  786 */       if (x2 > en_buyuk_x)
/*      */       {
/*  788 */         en_buyuk_x = x2;
/*      */       }
/*  790 */       if (y1 > en_buyuk_y)
/*      */       {
/*  792 */         en_buyuk_y = y1;
/*      */       }
/*  794 */       if (y2 > en_buyuk_y)
/*      */       {
/*  796 */         en_buyuk_y = y2;
/*      */       }
/*      */     }
/*  799 */     if ((en_buyuk_x > this.grid_width) || (en_buyuk_y > this.grid_height))
/*      */     {
/*  801 */       Message_box.show("Bu graf bu ekrana çizilemez", "Hata", Message_box.warning_message);
/*  802 */       return;
/*      */     }
/*  804 */     clear_graf().handle(null);
/*  805 */     for (int m = 0; m < edge_ids.length; m++)
/*      */     {
/*  807 */       String[] temp_id = edge_ids[m].split(",");
/*  808 */       int x1 = Integer.parseInt(temp_id[0].split(";")[0]);
/*  809 */       int y1 = Integer.parseInt(temp_id[0].split(";")[1]);
/*  810 */       int x2 = Integer.parseInt(temp_id[1].split(";")[0]);
/*  811 */       int y2 = Integer.parseInt(temp_id[1].split(";")[1]);
/*  812 */       int pos_x_1 = this.offset_x + this.graf_aralik * x1;
/*  813 */       int pos_y_1 = this.offset_y + this.graf_aralik * y1;
/*  814 */       int pos_x_2 = this.offset_x + this.graf_aralik * x2;
/*  815 */       int pos_y_2 = this.offset_y + this.graf_aralik * y2;
/*  816 */       GrafLine new_line = this.my_builder.build_line(y1 * this.grid_width + x1 + "," + (y2 * this.grid_width + x2), pos_x_1, pos_y_1, pos_x_2, pos_y_2, this.buharlasma, y1 * this.grid_width + x1, y2 * this.grid_width + x2);
/*  817 */       new_line.setOnMouseClicked(line_mouse_clicked());
/*  818 */       this.group_grid.getChildren().add(new_line);
/*  819 */       this.group_grid.getChildren().add(new_line.text_weight());
/*  820 */       new_line.toBack();
/*  821 */       this.edges = this.Ops.add_edge(this.edges, new_line);
/*  822 */       this.komsuluklar[(y1 * this.grid_width + x1)][(y2 * this.grid_width + x2)] = 1;
/*  823 */       this.komsuluklar[(y2 * this.grid_width + x2)][(y1 * this.grid_width + x1)] = 1;
/*  824 */       for (int k = 0; k < this.circle_grid.length; k++)
/*      */       {
/*  826 */         for (int t = 0; t < this.circle_grid[k].length; t++)
/*      */         {
/*  828 */           if ((t == x1) && (k == y1))
/*      */           {
/*  830 */             this.circle_grid[k][t].setFill(Colors_and_shapes.color_graf);
/*      */           }
/*  832 */           if ((t == x2) && (k == y2))
/*      */           {
/*  834 */             this.circle_grid[k][t].setFill(Colors_and_shapes.color_graf);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private EventHandler<javafx.event.ActionEvent> stop_sim() {
/*  842 */     return new EventHandler()
/*      */     {
/*      */       public void handle(javafx.event.ActionEvent event)
/*      */       {
/*  847 */         Karincca.this.my_timer.stop();
/*  848 */         for (int m = 0; m < Karincca.this.animations.length; m++)
/*      */         {
/*  850 */           Karincca.this.animations[m].stop();
/*      */         }
/*  852 */         ((Button)event.getSource()).setDisable(true);
/*  853 */         Karincca.this.button_start_sim.setDisable(false);
/*  854 */         for (int m = 0; m < Karincca.this.grid_height; m++)
/*      */         {
/*  856 */           for (int k = 0; k < Karincca.this.grid_width; k++)
/*      */           {
/*  858 */             Karincca.this.circle_grid[m][k].setVisible(true);
/*      */           }
/*      */         }
/*  861 */         for (int m = 0; m < Karincca.this.karincalar.length; m++)
/*      */         {
/*  863 */           Karincca.this.karincalar[m].setVisible(false);
/*  864 */           Karincca.this.karincalar[m] = null;
/*      */         }
/*  866 */         for (int m = 0; m < Karincca.this.edges.length; m++)
/*      */         {
/*  868 */           Karincca.this.edges[m].set_feromon_zero();
/*      */         }
/*  870 */         Karincca.this.karincalar = new Image_karinca[0];
/*  871 */         Karincca.this.button_clear_graf.setDisable(false);
/*  872 */         Karincca.this.button_draw_graf.setDisable(false);
/*  873 */         Karincca.this.slider_ant_count.setDisable(false);
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   private EventHandler<javafx.event.ActionEvent> start_sim() {
/*  879 */     return new EventHandler()
/*      */     {
/*      */       public void handle(javafx.event.ActionEvent event)
/*      */       {
/*  884 */         boolean cave_exists = false; boolean food_sxists = false; boolean graf_exists = false; boolean graf_complete = true;
/*  885 */         for (int m = 0; m < Karincca.this.grid_height; m++)
/*      */         {
/*  887 */           for (int k = 0; k < Karincca.this.grid_width; k++)
/*      */           {
/*  889 */             if (Karincca.this.circle_grid[m][k].getFill() == Colors_and_shapes.color_cave)
/*      */             {
/*  891 */               cave_exists = true;
/*      */             }
/*  893 */             if (Karincca.this.circle_grid[m][k].getFill() == Colors_and_shapes.color_food)
/*      */             {
/*  895 */               food_sxists = true;
/*      */             }
/*  897 */             if (Karincca.this.circle_grid[m][k].getFill() == Colors_and_shapes.color_graf)
/*      */             {
/*  899 */               graf_exists = true;
/*      */             }
/*  901 */             if (Karincca.this.circle_grid[m][k].getFill() == Colors_and_shapes.color_last)
/*      */             {
/*  903 */               graf_complete = false;
/*      */             }
/*      */           }
/*      */         }
/*  907 */         if (!graf_exists)
/*      */         {
/*  909 */           Message_box.show("Lütfen graf çiziniz!", "Uyarı", Message_box.warning_message);
/*  910 */           return;
/*      */         }
/*  912 */         if (!graf_complete)
/*      */         {
/*  914 */           Message_box.show("Lütfen graf çizimini tamamlayınız! Pembe renkli graf noktası kalmamalı.", "Uyarı", Message_box.warning_message);
/*  915 */           return;
/*      */         }
/*  917 */         if (!cave_exists)
/*      */         {
/*  919 */           Message_box.show("Yuva noktası ekleyiniz!\nGraf noktasına sağ tıklayarak bu işlemi yapabilirsiniz.", "Uyarı", Message_box.warning_message);
/*  920 */           return;
/*      */         }
/*  922 */         if (!food_sxists)
/*      */         {
/*  924 */           Message_box.show("Yiyecek noktası ekleyiniz!\nGraf noktasına sağ tıklayarak bu işlemi yapabilirsiniz.", "Uyarı", Message_box.warning_message);
/*  925 */           return;
/*      */         }
/*  927 */         Karincca.this.button_clear_graf.setDisable(true);
/*  928 */         Karincca.this.button_draw_graf.setDisable(true);
/*  929 */         Karincca.this.slider_ant_count.setDisable(true);
/*  930 */         Karincca.this.karincalar = new Image_karinca[0];
/*  931 */         ((Button)event.getSource()).setDisable(true);
/*  932 */         Karincca.this.button_stop_sim.setDisable(false);
/*  933 */         for (int m = 0; m < Karincca.this.edges.length; m++)
/*      */         {
/*  935 */           Karincca.this.edges[m].set_feromon_zero();
/*      */         }
/*      */ 
/*  938 */         for (int m = 0; m < Karincca.this.grid_height; m++)
/*      */         {
/*  940 */           for (int k = 0; k < Karincca.this.grid_width; k++)
/*      */           {
/*  942 */             if (Karincca.this.circle_grid[m][k].getFill().equals(Colors_and_shapes.color_grid))
/*      */             {
/*  944 */               Karincca.this.circle_grid[m][k].setVisible(false);
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/*  949 */         Karincca.this.animations = new TranslateTransition[Karincca.this.karinca_sayisi];
/*  950 */         boolean cave = false;
/*  951 */         Circle cave_circle = Karincca.this.circle_cave_ex;
/*  952 */         for (int m = 0; m < Karincca.this.grid_height; m++)
/*      */         {
/*  954 */           for (int k = 0; k < Karincca.this.grid_width; k++)
/*      */           {
/*  956 */             if (Karincca.this.circle_grid[m][k].getFill() == Colors_and_shapes.color_cave)
/*      */             {
/*  958 */               cave = true;
/*  959 */               cave_circle = Karincca.this.circle_grid[m][k];
/*  960 */               break;
/*      */             }
/*      */           }
/*  963 */           if (cave)
/*      */           {
/*      */             break;
/*      */           }
/*      */         }
/*      */ 
/*  969 */         for (int m = 0; m < Karincca.this.karinca_sayisi; m++)
/*      */         {
/*  971 */           Image_karinca karinca = Karincca.this.my_builder.build_image_karinca(Integer.parseInt(cave_circle.getId()), (int)cave_circle.getCenterX(), (int)cave_circle.getCenterY(), "k" + m);
/*  972 */           Karincca.this.group_grid.getChildren().add(karinca);
/*  973 */           Karincca.this.karincalar = Karincca.this.Ops.add_circle(Karincca.this.karincalar, karinca);
/*  974 */           Karincca.this.animations[m] = new TranslateTransition();
/*  975 */           Karincca.this.animations[m].setNode(karinca);
/*  976 */           Karincca.this.animasyon_olustur(Karincca.this.karincalar[m].get_name());
/*      */         }
/*  978 */         Karincca.this.my_timer.restart();
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   private EventHandler<javafx.event.ActionEvent> clear_graf() {
/*  984 */     return new EventHandler()
/*      */     {
/*      */       public void handle(javafx.event.ActionEvent event)
/*      */       {
/*  989 */         for (int m = 0; m < Karincca.this.grid_height; m++)
/*      */         {
/*  991 */           for (int k = 0; k < Karincca.this.grid_width; k++)
/*      */           {
/*  993 */             Karincca.this.circle_grid[m][k].setFill(Colors_and_shapes.color_grid);
/*  994 */             Karincca.this.circle_grid[m][k].setRadius(7.0D);
/*      */           }
/*      */         }
/*  997 */         Karincca.this.komsuluklar_sifirla();
/*  998 */         for (int m = 0; m < Karincca.this.edges.length; m++)
/*      */         {
/* 1000 */           Karincca.this.edges[m].setVisible(false);
/* 1001 */           Karincca.this.edges[m] = null;
/*      */         }
/* 1003 */         Karincca.this.edges = new GrafLine[0];
/* 1004 */         Karincca.this.circle_last = null;
/*      */       }
/*      */     };
/*      */   }
/*      */ 
/*      */   private ChangeListener<Number> animation_timer_changed() {
/* 1010 */     return new ChangeListener()
/*      */     {
/*      */       public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue)
/*      */       {
/* 1015 */         Karincca.this.txt_animation_timer.setText("Animasyon Hızı = " + newValue.intValue());
/* 1016 */         Karincca.this.animation_time = (newValue.intValue() / Karincca.this.animation_time_coef);
/* 1017 */         Karincca.this.my_timer.setDelay(Karincca.this.animation_time != 0.0D ? (int)(500.0D / Karincca.this.animation_time) : 2147483647);
/* 1018 */         Karincca.this.my_timer.restart();
/* 1019 */         if (newValue.intValue() == 0)
/*      */         {
/* 1021 */           for (int m = 0; m < Karincca.this.animations.length; m++)
/*      */           {
/* 1023 */             Karincca.this.animations[m].pause();
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1028 */           for (int m = 0; m < Karincca.this.animations.length; m++)
/*      */           {
/* 1030 */             if (Karincca.this.animations[m].statusProperty().get() == Animation.Status.PAUSED)
/*      */             {
/* 1032 */               Karincca.this.animations[m].play();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     };
/*      */   }
/*      */ }

/* Location:           /home/rcls/Desktop/Ants/karincca.jar
 * Qualified Name:     karincca.Karincca
 * JD-Core Version:    0.6.2
 */