/*     */ package com.javafx.main;
/*     */ 
/*     */ import java.applet.Applet;
/*     */ import java.applet.AppletContext;
/*     */ import java.awt.BorderLayout;
/*     */ import java.awt.Container;
/*     */ import java.awt.Desktop;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URL;
/*     */ import javax.swing.JApplet;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JTextPane;
/*     */ 
/*     */ public class NoJavaFXFallback extends JApplet
/*     */   implements ActionListener
/*     */ {
/*  19 */   boolean isInBrowser = false;
/*  20 */   boolean oldJRE = true;
/*  21 */   String requiredJavaFXVersion = null;
/*  22 */   boolean oldJavaFX = false;
/*  23 */   boolean doNotUseJNLPAPI = false;
/*     */ 
/*     */   public NoJavaFXFallback()
/*     */   {
/*     */   }
/*     */ 
/*     */   public NoJavaFXFallback(boolean needJREUpgrade, boolean needFXUpgrade, String requiredJavaFX) {
/*  30 */     this.isInBrowser = false;
/*  31 */     this.oldJavaFX = needFXUpgrade;
/*  32 */     this.requiredJavaFXVersion = requiredJavaFX;
/*  33 */     this.oldJRE = needJREUpgrade;
/*  34 */     this.doNotUseJNLPAPI = true;
/*     */ 
/*  36 */     populate();
/*     */   }
/*     */ 
/*     */   private static float getJavaVersionAsFloat() {
/*  40 */     String versionString = System.getProperty("java.version", "1.5.0");
/*     */ 
/*  42 */     StringBuffer sb = new StringBuffer();
/*     */ 
/*  44 */     int firstDot = versionString.indexOf(".");
/*  45 */     sb.append(versionString.substring(0, firstDot));
/*     */ 
/*  47 */     int secondDot = versionString.indexOf(".", firstDot + 1);
/*  48 */     sb.append(versionString.substring(firstDot + 1, secondDot));
/*     */ 
/*  50 */     int underscore = versionString.indexOf("_", secondDot + 1);
/*  51 */     if (underscore >= 0) {
/*  52 */       int dash = versionString.indexOf("-", underscore + 1);
/*  53 */       if (dash < 0) {
/*  54 */         dash = versionString.length();
/*     */       }
/*  56 */       sb.append(versionString.substring(secondDot + 1, underscore)).append(".").append(versionString.substring(underscore + 1, dash));
/*     */     }
/*     */     else
/*     */     {
/*  60 */       int dash = versionString.indexOf("-", secondDot + 1);
/*  61 */       if (dash < 0) {
/*  62 */         dash = versionString.length();
/*     */       }
/*  64 */       sb.append(versionString.substring(secondDot + 1, dash));
/*     */     }
/*     */ 
/*  67 */     float version = 150.0F;
/*     */     try {
/*  69 */       version = Float.parseFloat(sb.toString());
/*     */     } catch (NumberFormatException e) {
/*     */     }
/*  72 */     return version;
/*     */   }
/*     */ 
/*     */   private void test() {
/*  76 */     this.oldJRE = (getJavaVersionAsFloat() < 160.17999F);
/*     */     try
/*     */     {
/*  81 */       Class jclass = Class.forName("netscape.javascript.JSObject");
/*  82 */       Method m = jclass.getMethod("getWindow", new Class[] { Applet.class });
/*  83 */       this.isInBrowser = (m.invoke(null, new Object[] { this }) != null); } catch (Exception e) {
/*     */     }
/*     */   }
/*     */ 
/*     */   String getText() {
/*  88 */     String text = "This application requires a newer version of the Java runtime. Please download and install the latest Java runtime from java.com.";
/*     */ 
/*  91 */     if (this.isInBrowser)
/*  92 */       text = text + " Then restart the browser.";
/*     */     else {
/*  94 */       text = text + " Then restart the application.";
/*     */     }
/*  96 */     return text;
/*     */   }
/*     */ 
/*     */   public void init()
/*     */   {
/* 101 */     this.requiredJavaFXVersion = getParameter("requiredFXVersion");
/* 102 */     test();
/* 103 */     populate();
/*     */   }
/*     */ 
/*     */   private void populate() {
/* 107 */     Container pane = getContentPane();
/* 108 */     pane.setLayout(new BorderLayout());
/* 109 */     JTextPane l = new JTextPane();
/* 110 */     l.setText(getText());
/* 111 */     l.setEditable(false);
/*     */ 
/* 113 */     pane.add(l, "Center");
/*     */ 
/* 115 */     if ((getJavaVersionAsFloat() > 160.0F) || ((getJavaVersionAsFloat() > 150.0F) && (!this.doNotUseJNLPAPI)))
/*     */     {
/* 117 */       JButton installButton = new JButton("Install Now");
/* 118 */       installButton.addActionListener(this);
/* 119 */       pane.add(installButton, "South");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void actionPerformed(ActionEvent ae) {
/*     */     try {
/* 125 */       URL u = new URL("http://java.com/");
/* 126 */       if (this.isInBrowser) {
/* 127 */         getAppletContext().showDocument(u);
/*     */       }
/* 129 */       else if (!this.doNotUseJNLPAPI) {
/* 130 */         Class sm = Class.forName("javax.jnlp.ServiceManager");
/* 131 */         Class bs = Class.forName("javax.jnlp.BasicService");
/* 132 */         Method lookup = sm.getMethod("lookup", new Class[] { String.class });
/*     */ 
/* 134 */         Method showDoc = bs.getMethod("showDocument", new Class[] { URL.class });
/*     */ 
/* 136 */         Object s = lookup.invoke(null, new Object[] { "javax.jnlp.BasicService" });
/*     */ 
/* 138 */         showDoc.invoke(s, new Object[] { u });
/*     */       }
/*     */       else {
/* 141 */         Desktop d = Desktop.getDesktop();
/* 142 */         if (d != null)
/* 143 */           d.browse(u.toURI());
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 148 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }

/* Location:           /home/rcls/Desktop/Ants/karincca.jar
 * Qualified Name:     com.javafx.main.NoJavaFXFallback
 * JD-Core Version:    0.6.2
 */