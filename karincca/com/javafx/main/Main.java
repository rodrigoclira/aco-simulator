/*     */ package com.javafx.main;
/*     */ 
/*     */ import java.awt.Container;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.net.URLClassLoader;
/*     */ import java.net.URLDecoder;
/*     */ import java.security.CodeSource;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.jar.Attributes;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.jar.Manifest;
/*     */ import javax.swing.JApplet;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.SwingUtilities;
/*     */ import sun.misc.BASE64Decoder;
/*     */ 
/*     */ public class Main
/*     */ {
/*  68 */   private static boolean verbose = false;
/*     */   private static final String fxApplicationClassName = "javafx.application.Application";
/*     */   private static final String fxLaunchClassName = "com.sun.javafx.application.LauncherImpl";
/*     */   private static final String manifestAppClass = "JavaFX-Application-Class";
/*     */   private static final String manifestPreloaderClass = "JavaFX-Preloader-Class";
/*     */   private static final String manifestFallbackClass = "JavaFX-Fallback-Class";
/*     */   private static final String manifestClassPath = "JavaFX-Class-Path";
/*     */   private static final String manifestUpdateHook = "X-JavaFX-Update-Hook";
/*     */   private static final String JAVAFX_FAMILY_VERSION = "2.";
/*     */   private static final String JAVAFX_REQUIRED_VERSION = "2.1.0";
/*     */   private static final String ZERO_VERSION = "0.0.0";
/*  94 */   private static Attributes attrs = null;
/*     */ 
/*     */   private static URL fileToURL(File file) throws IOException {
/*  97 */     return file.getCanonicalFile().toURI().toURL();
/*     */   }
/*     */ 
/*     */   private static Method findLaunchMethod(File jfxRtPath, String fxClassPath) {
/* 101 */     Class[] argTypes = { Class.class, Class.class, new String[0].getClass() };
/*     */     try
/*     */     {
/* 105 */       ArrayList urlList = new ArrayList();
/*     */ 
/* 108 */       String cp = System.getProperty("java.class.path");
/* 109 */       if (cp != null) {
/* 110 */         while (cp.length() > 0) {
/* 111 */           int pathSepIdx = cp.indexOf(File.pathSeparatorChar);
/* 112 */           if (pathSepIdx < 0) {
/* 113 */             String pathElem = cp;
/* 114 */             urlList.add(fileToURL(new File(pathElem)));
/* 115 */             break;
/* 116 */           }if (pathSepIdx > 0) {
/* 117 */             String pathElem = cp.substring(0, pathSepIdx);
/* 118 */             urlList.add(fileToURL(new File(pathElem)));
/*     */           }
/* 120 */           cp = cp.substring(pathSepIdx + 1);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 125 */       cp = fxClassPath;
/* 126 */       if (cp != null)
/*     */       {
/* 130 */         File baseDir = null;
/*     */         try {
/* 132 */           String path = Main.class.getProtectionDomain().getCodeSource().getLocation().getPath();
/*     */ 
/* 134 */           String decodedPath = URLDecoder.decode(path, "UTF-8");
/* 135 */           baseDir = new File(decodedPath).getParentFile();
/* 136 */           if (!baseDir.exists())
/* 137 */             baseDir = null;
/*     */         } catch (Exception e) {
/*     */         }
/* 140 */         while (cp.length() > 0) {
/* 141 */           int pathSepIdx = cp.indexOf(" ");
/* 142 */           if (pathSepIdx < 0) {
/* 143 */             String pathElem = cp;
/* 144 */             File f = baseDir == null ? new File(pathElem) : new File(baseDir, pathElem);
/*     */ 
/* 146 */             urlList.add(fileToURL(f));
/* 147 */             break;
/* 148 */           }if (pathSepIdx > 0) {
/* 149 */             String pathElem = cp.substring(0, pathSepIdx);
/* 150 */             File f = baseDir == null ? new File(pathElem) : new File(baseDir, pathElem);
/*     */ 
/* 152 */             urlList.add(fileToURL(f));
/*     */           }
/* 154 */           cp = cp.substring(pathSepIdx + 1);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 159 */       if (jfxRtPath != null) {
/* 160 */         File jfxRtLibPath = new File(jfxRtPath, "lib");
/* 161 */         urlList.add(fileToURL(new File(jfxRtLibPath, "jfxrt.jar")));
/* 162 */         urlList.add(fileToURL(new File(jfxRtLibPath, "deploy.jar")));
/* 163 */         urlList.add(fileToURL(new File(jfxRtLibPath, "plugin.jar")));
/* 164 */         urlList.add(fileToURL(new File(jfxRtLibPath, "javaws.jar")));
/*     */       }
/*     */ 
/* 167 */       URL[] urls = (URL[])urlList.toArray(new URL[0]);
/* 168 */       if (verbose) {
/* 169 */         System.err.println("===== URL list");
/* 170 */         for (int i = 0; i < urls.length; i++) {
/* 171 */           System.err.println("" + urls[i]);
/*     */         }
/* 173 */         System.err.println("=====");
/*     */       }
/*     */ 
/* 176 */       ClassLoader urlClassLoader = new URLClassLoader(urls, null);
/* 177 */       Class launchClass = Class.forName("com.sun.javafx.application.LauncherImpl", true, urlClassLoader);
/*     */ 
/* 179 */       Method m = launchClass.getMethod("launchApplication", argTypes);
/* 180 */       if (m != null) {
/* 181 */         Thread.currentThread().setContextClassLoader(urlClassLoader);
/* 182 */         return m;
/*     */       }
/*     */     } catch (Exception ex) {
/* 185 */       if (jfxRtPath != null) {
/* 186 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */ 
/* 190 */     return null;
/*     */   }
/*     */ 
/*     */   private static Method findLaunchMethodInClasspath(String fxClassPath) {
/* 194 */     return findLaunchMethod(null, fxClassPath);
/*     */   }
/*     */ 
/*     */   private static Method findLaunchMethodInJar(String jfxRtPathName, String fxClassPath) {
/* 198 */     File jfxRtPath = new File(jfxRtPathName);
/*     */ 
/* 201 */     File jfxRtLibPath = new File(jfxRtPath, "lib");
/* 202 */     File jfxRtJar = new File(jfxRtLibPath, "jfxrt.jar");
/* 203 */     if (!jfxRtJar.canRead()) {
/* 204 */       if (verbose) {
/* 205 */         System.err.println("Unable to read " + jfxRtJar.toString());
/*     */       }
/* 207 */       return null;
/*     */     }
/*     */ 
/* 210 */     return findLaunchMethod(jfxRtPath, fxClassPath);
/*     */   }
/*     */ 
/*     */   private static int[] convertVersionStringtoArray(String version)
/*     */   {
/* 217 */     int[] v = new int[3];
/* 218 */     if (version == null) return null;
/*     */ 
/* 220 */     String[] s = version.split("\\.");
/* 221 */     if (s.length == 3) {
/* 222 */       v[0] = Integer.parseInt(s[0]);
/* 223 */       v[1] = Integer.parseInt(s[1]);
/* 224 */       v[2] = Integer.parseInt(s[2]);
/* 225 */       return v;
/*     */     }
/*     */ 
/* 228 */     return null;
/*     */   }
/*     */ 
/*     */   private static int compareVersionArray(int[] a1, int[] a2)
/*     */   {
/* 237 */     boolean isValid1 = (a1 != null) && (a1.length == 3);
/* 238 */     boolean isValid2 = (a2 != null) && (a2.length == 3);
/*     */ 
/* 241 */     if ((!isValid1) && (!isValid2)) {
/* 242 */       return 0;
/*     */     }
/*     */ 
/* 246 */     if (!isValid2) {
/* 247 */       return -1;
/*     */     }
/*     */ 
/* 251 */     if (!isValid1) {
/* 252 */       return 1;
/*     */     }
/*     */ 
/* 255 */     for (int i = 0; i < a1.length; i++) {
/* 256 */       if (a2[i] > a1[i]) return 1;
/* 257 */       if (a2[i] < a1[i]) return -1;
/*     */     }
/*     */ 
/* 260 */     return 0;
/*     */   }
/*     */ 
/*     */   private static String lookupRegistry()
/*     */   {
/* 270 */     if (!System.getProperty("os.name").startsWith("Win")) {
/* 271 */       return null;
/*     */     }
/*     */ 
/* 274 */     String javaHome = System.getProperty("java.home");
/* 275 */     if (verbose) {
/* 276 */       System.err.println("java.home = " + javaHome);
/*     */     }
/* 278 */     if ((javaHome == null) || (javaHome.equals(""))) {
/* 279 */       return null;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 287 */       File jreLibPath = new File(javaHome, "lib");
/* 288 */       File deployJar = new File(jreLibPath, "deploy.jar");
/*     */ 
/* 290 */       URL[] urls = { fileToURL(deployJar) };
/* 291 */       if (verbose) {
/* 292 */         System.err.println(">>>> URL to deploy.jar = " + urls[0]);
/*     */       }
/*     */ 
/* 295 */       ClassLoader deployClassLoader = new URLClassLoader(urls, null);
/*     */       try
/*     */       {
/* 299 */         String configClassName = "com.sun.deploy.config.Config";
/* 300 */         Class configClass = Class.forName(configClassName, true, deployClassLoader);
/*     */ 
/* 302 */         Method m = configClass.getMethod("getInstance", null);
/* 303 */         Object config = m.invoke(null, null);
/* 304 */         m = configClass.getMethod("loadDeployNativeLib", null);
/* 305 */         m.invoke(config, null);
/*     */       }
/*     */       catch (Exception ex)
/*     */       {
/*     */       }
/* 310 */       String winRegistryWrapperClassName = "com.sun.deploy.association.utility.WinRegistryWrapper";
/*     */ 
/* 313 */       Class winRegistryWrapperClass = Class.forName(winRegistryWrapperClassName, true, deployClassLoader);
/*     */ 
/* 316 */       Method mGetSubKeys = winRegistryWrapperClass.getMethod("WinRegGetSubKeys", new Class[] { Integer.TYPE, String.class, Integer.TYPE });
/*     */ 
/* 323 */       Field HKEY_LOCAL_MACHINE_Field2 = winRegistryWrapperClass.getField("HKEY_LOCAL_MACHINE");
/*     */ 
/* 325 */       int HKEY_LOCAL_MACHINE2 = HKEY_LOCAL_MACHINE_Field2.getInt(null);
/* 326 */       String registryKey = "Software\\Oracle\\JavaFX\\";
/*     */ 
/* 330 */       String[] fxVersions = (String[])mGetSubKeys.invoke(null, new Object[] { new Integer(HKEY_LOCAL_MACHINE2), "Software\\Oracle\\JavaFX\\", new Integer(255) });
/*     */ 
/* 336 */       if (fxVersions == null)
/*     */       {
/* 338 */         return null;
/*     */       }
/* 340 */       String version = "0.0.0";
/*     */ 
/* 342 */       for (int i = 0; i < fxVersions.length; i++)
/*     */       {
/* 345 */         if ((fxVersions[i].startsWith("2.")) && (fxVersions[i].compareTo("2.1.0") >= 0))
/*     */         {
/* 347 */           int[] v1Array = convertVersionStringtoArray(version);
/* 348 */           int[] v2Array = convertVersionStringtoArray(fxVersions[i]);
/* 349 */           if (compareVersionArray(v1Array, v2Array) > 0) {
/* 350 */             version = fxVersions[i];
/*     */           }
/*     */         }
/* 353 */         else if (verbose) {
/* 354 */           System.err.println("  Skip version " + fxVersions[i] + " (required=" + "2.1.0" + ")");
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 360 */       if (version.equals("0.0.0"))
/*     */       {
/* 362 */         return null;
/*     */       }
/*     */ 
/* 366 */       String winRegistryClassName = "com.sun.deploy.util.WinRegistry";
/* 367 */       Class winRegistryClass = Class.forName(winRegistryClassName, true, deployClassLoader);
/*     */ 
/* 369 */       Method mGet = winRegistryClass.getMethod("getString", new Class[] { Integer.TYPE, String.class, String.class });
/*     */ 
/* 374 */       Field HKEY_LOCAL_MACHINE_Field = winRegistryClass.getField("HKEY_LOCAL_MACHINE");
/* 375 */       int HKEY_LOCAL_MACHINE = HKEY_LOCAL_MACHINE_Field.getInt(null);
/* 376 */       String path = (String)mGet.invoke(null, new Object[] { new Integer(HKEY_LOCAL_MACHINE), "Software\\Oracle\\JavaFX\\" + version, "Path" });
/*     */ 
/* 381 */       if (verbose) {
/* 382 */         System.err.println("FOUND KEY: Software\\Oracle\\JavaFX\\" + version + " = " + path);
/*     */       }
/* 384 */       return path;
/*     */     } catch (Exception ex) {
/* 386 */       ex.printStackTrace();
/*     */     }
/*     */ 
/* 389 */     return null;
/*     */   }
/*     */ 
/*     */   private static Attributes getJarAttributes() throws Exception {
/* 393 */     String theClassFile = "Main.class";
/* 394 */     Class theClass = Main.class;
/* 395 */     String classUrlString = theClass.getResource(theClassFile).toString();
/* 396 */     if ((!classUrlString.startsWith("jar:file:")) || (classUrlString.indexOf("!") == -1)) {
/* 397 */       return null; } 
/*     */ String urlString = classUrlString.substring(4, classUrlString.lastIndexOf("!"));
/* 401 */     File jarFile = new File(new URI(urlString).getPath());
/* 402 */     String jarName = jarFile.getCanonicalPath();
/*     */ 
/* 405 */     JarFile jf = null;
/*     */     Attributes attr;
/*     */     try { jf = new JarFile(jarName);
/* 408 */       Manifest mf = jf.getManifest();
/* 409 */       attr = mf.getMainAttributes();
/*     */     } finally {
/* 411 */       if (jf != null)
/*     */         try {
/* 413 */           jf.close();
/*     */         }
/*     */         catch (Exception ex)
/*     */         {
/*     */         }
/*     */     }
/* 419 */     return attr;
/*     */   }
/*     */ 
/*     */   private static String decodeBase64(String inp) throws IOException {
/* 423 */     BASE64Decoder decoder = new BASE64Decoder();
/* 424 */     byte[] decodedBytes = decoder.decodeBuffer(inp);
/* 425 */     return new String(decodedBytes);
/*     */   }
/*     */ 
/*     */   private static String[] getAppArguments(Attributes attrs) {
/* 429 */     List args = new LinkedList();
/*     */     try
/*     */     {
/* 432 */       int idx = 1;
/* 433 */       String argNamePrefix = "JavaFX-Argument-";
/* 434 */       while (attrs.getValue(argNamePrefix + idx) != null) {
/* 435 */         args.add(decodeBase64(attrs.getValue(argNamePrefix + idx)));
/* 436 */         idx++;
/*     */       }
/*     */ 
/* 439 */       String paramNamePrefix = "JavaFX-Parameter-Name-";
/* 440 */       String paramValuePrefix = "JavaFX-Parameter-Value-";
/* 441 */       idx = 1;
/* 442 */       while (attrs.getValue(paramNamePrefix + idx) != null) {
/* 443 */         String k = decodeBase64(attrs.getValue(paramNamePrefix + idx));
/* 444 */         String v = null;
/* 445 */         if (attrs.getValue(paramValuePrefix + idx) != null) {
/* 446 */           v = decodeBase64(attrs.getValue(paramValuePrefix + idx));
/*     */         }
/* 448 */         args.add("--" + k + "=" + (v != null ? v : ""));
/* 449 */         idx++;
/*     */       }
/*     */     } catch (IOException ioe) {
/* 452 */       System.err.println("Failed to extract application parameters");
/* 453 */       ioe.printStackTrace();
/*     */     }
/*     */ 
/* 457 */     return (String[])args.toArray(new String[0]);
/*     */   }
/*     */ 
/*     */   private static String getAppName(Attributes attrs, boolean preloader)
/*     */   {
/* 463 */     String propName = preloader ? "javafx.preloader.class" : "javafx.application.class";
/*     */ 
/* 467 */     String className = System.getProperty(propName);
/* 468 */     if ((className != null) && (className.length() != 0)) {
/* 469 */       return className;
/*     */     }
/*     */ 
/* 475 */     if (attrs == null) {
/* 476 */       return "TEST";
/*     */     }
/*     */ 
/* 479 */     if (preloader) {
/* 480 */       String appName = attrs.getValue("JavaFX-Preloader-Class");
/* 481 */       if ((appName == null) || (appName.length() == 0)) {
/* 482 */         if (verbose) {
/* 483 */           System.err.println("Unable to find preloader class name");
/*     */         }
/* 485 */         return null;
/*     */       }
/* 487 */       return appName;
/*     */     }
/* 489 */     String appName = attrs.getValue("JavaFX-Application-Class");
/* 490 */     if ((appName == null) || (appName.length() == 0)) {
/* 491 */       System.err.println("Unable to find application class name");
/* 492 */       return null;
/*     */     }
/* 494 */     return appName;
/*     */   }
/*     */ 
/*     */   private static Class getAppClass(String appName)
/*     */   {
/*     */     try
/*     */     {
/* 501 */       if (verbose) {
/* 502 */         System.err.println("Try calling Class.forName(" + appName + ") using classLoader = " + Thread.currentThread().getContextClassLoader());
/*     */       }
/*     */ 
/* 506 */       Class appClass = Class.forName(appName, false, Thread.currentThread().getContextClassLoader());
/*     */ 
/* 508 */       if (verbose) {
/* 509 */         System.err.println("found class: " + appClass);
/*     */       }
/* 511 */       return appClass;
/*     */     } catch (NoClassDefFoundError ncdfe) {
/* 513 */       ncdfe.printStackTrace();
/* 514 */       errorExit("Unable to find class: " + appName);
/*     */     } catch (ClassNotFoundException cnfe) {
/* 516 */       cnfe.printStackTrace();
/* 517 */       errorExit("Unable to find class: " + appName);
/*     */     }
/*     */ 
/* 520 */     return null;
/*     */   }
/*     */ 
/*     */   private static void tryToSetProxy()
/*     */   {
/*     */     try {
/* 526 */       if ((System.getProperty("http.proxyHost") != null) || (System.getProperty("https.proxyHost") != null) || (System.getProperty("ftp.proxyHost") != null))
/*     */       {
/* 529 */         if (verbose) {
/* 530 */           System.out.println("Explicit proxy settings detected. Skip autoconfig.");
/* 531 */           System.out.println("  http.proxyHost=" + System.getProperty("http.proxyHost"));
/* 532 */           System.out.println("  https.proxyHost=" + System.getProperty("https.proxyHost"));
/* 533 */           System.out.println("  ftp.proxyHost=" + System.getProperty("ftp.proxyHost"));
/*     */         }
/* 535 */         return;
/*     */       }
/* 537 */       if (System.getProperty("javafx.autoproxy.disable") != null) {
/* 538 */         if (verbose) {
/* 539 */           System.out.println("Disable autoproxy on request.");
/*     */         }
/* 541 */         return;
/*     */       }
/*     */ 
/* 544 */       Class sm = Class.forName("com.sun.deploy.services.ServiceManager", true, Thread.currentThread().getContextClassLoader());
/*     */ 
/* 547 */       Class[] params = { Integer.TYPE };
/* 548 */       Method setservice = sm.getDeclaredMethod("setService", params);
/* 549 */       String osname = System.getProperty("os.name");
/*     */ 
/* 551 */       String servicename = null;
/* 552 */       if (osname.startsWith("Win")) {
/* 553 */         servicename = "STANDALONE_TIGER_WIN32";
/*     */       }
/* 555 */       else if (osname.contains("Mac"))
/* 556 */         servicename = "STANDALONE_TIGER_MACOSX";
/*     */       else {
/* 558 */         servicename = "STANDALONE_TIGER_UNIX";
/*     */       }
/* 560 */       Object[] values = new Object[1];
/* 561 */       Class pt = Class.forName("com.sun.deploy.services.PlatformType", true, Thread.currentThread().getContextClassLoader());
/*     */ 
/* 564 */       values[0] = pt.getField(servicename).get(null);
/* 565 */       setservice.invoke(null, values);
/*     */ 
/* 567 */       Class dps = Class.forName("com.sun.deploy.net.proxy.DeployProxySelector", true, Thread.currentThread().getContextClassLoader());
/*     */ 
/* 571 */       Method m = dps.getDeclaredMethod("reset", new Class[0]);
/* 572 */       m.invoke(null, new Object[0]);
/* 573 */       if (verbose)
/* 574 */         System.out.println("Autoconfig of proxy is completed.");
/*     */     }
/*     */     catch (Exception e) {
/* 577 */       if (verbose)
/* 578 */         System.out.println("Failed to autoconfig proxy due to " + e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void processUpdateHook(String updateHookName)
/*     */   {
/* 584 */     if (updateHookName == null) return;
/*     */ 
/*     */     try
/*     */     {
/* 588 */       if (verbose) {
/* 589 */         System.err.println("Try calling Class.forName(" + updateHookName + ") using classLoader = " + Thread.currentThread().getContextClassLoader());
/*     */       }
/*     */ 
/* 593 */       Class hookClass = Class.forName(updateHookName, false, Thread.currentThread().getContextClassLoader());
/*     */ 
/* 595 */       if (verbose) {
/* 596 */         System.err.println("found class: " + hookClass.getCanonicalName());
/*     */       }
/*     */ 
/* 599 */       Method mainMethod = hookClass.getMethod("main", new Class[] { new String[0].getClass() });
/*     */ 
/* 601 */       String[] args = null;
/* 602 */       mainMethod.invoke(null, new Object[] { args });
/*     */     }
/*     */     catch (Exception ex) {
/* 605 */       if (verbose) {
/* 606 */         System.err.println("Failed to run update hook: " + ex.getMessage());
/* 607 */         ex.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void launchApp(Method launchMethod, String appName, String preloaderName, String updateHookName, String[] args)
/*     */   {
/* 618 */     Class preloaderClass = null;
/* 619 */     if (preloaderName != null) {
/* 620 */       preloaderClass = getAppClass(preloaderName);
/*     */     }
/* 622 */     Class appClass = getAppClass(appName);
/* 623 */     Class fxApplicationClass = null;
/*     */     try {
/* 625 */       fxApplicationClass = Class.forName("javafx.application.Application", true, Thread.currentThread().getContextClassLoader());
/*     */     }
/*     */     catch (NoClassDefFoundError ex) {
/* 628 */       errorExit("Cannot find javafx.application.Application");
/*     */     } catch (ClassNotFoundException ex) {
/* 630 */       errorExit("Cannot find javafx.application.Application");
/*     */     }
/*     */ 
/* 633 */     if (fxApplicationClass.isAssignableFrom(appClass))
/*     */       try {
/* 635 */         if (verbose) {
/* 636 */           System.err.println("launchApp: Try calling " + launchMethod.getDeclaringClass().getName() + "." + launchMethod.getName());
/*     */         }
/*     */ 
/* 640 */         tryToSetProxy();
/* 641 */         processUpdateHook(updateHookName);
/* 642 */         launchMethod.invoke(null, new Object[] { appClass, preloaderClass, args });
/*     */       } catch (InvocationTargetException ex) {
/* 644 */         ex.printStackTrace();
/* 645 */         errorExit("Exception while running Application");
/*     */       } catch (Exception ex) {
/* 647 */         ex.printStackTrace();
/* 648 */         errorExit("Unable to invoke launch method");
/*     */       }
/*     */     else
/*     */       try {
/* 652 */         if (verbose) {
/* 653 */           System.err.println("Try calling " + appClass.getName() + ".main(String[])");
/*     */         }
/*     */ 
/* 656 */         Method mainMethod = appClass.getMethod("main", new Class[] { new String[0].getClass() });
/*     */ 
/* 658 */         mainMethod.invoke(null, new Object[] { args });
/*     */       } catch (Exception ex) {
/* 660 */         ex.printStackTrace();
/* 661 */         errorExit("Unable to invoke main method");
/*     */       }
/*     */   }
/*     */ 
/*     */   private static void checkJre()
/*     */   {
/* 668 */     String javaVersion = System.getProperty("java.version");
/* 669 */     if (verbose) {
/* 670 */       System.err.println("java.version = " + javaVersion);
/* 671 */       System.err.println("java.runtime.version = " + System.getProperty("java.runtime.version"));
/*     */     }
/*     */ 
/* 676 */     if ((!javaVersion.startsWith("1.6")) && (!javaVersion.startsWith("1.7")) && (!javaVersion.startsWith("1.8")) && (!javaVersion.startsWith("1.9")))
/*     */     {
/* 680 */       showFallback(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static Method findLaunchMethod(String fxClassPath) {
/* 685 */     Method launchMethod = null;
/*     */ 
/* 688 */     if (verbose) {
/* 689 */       System.err.println("1) Try existing classpath...");
/*     */     }
/* 691 */     launchMethod = findLaunchMethodInClasspath(fxClassPath);
/* 692 */     if (launchMethod != null) return launchMethod;
/*     */ 
/* 696 */     if (verbose) {
/* 697 */       System.err.println("2) Try javafx.runtime.path property...");
/*     */     }
/* 699 */     String javafxRuntimePath = System.getProperty("javafx.runtime.path");
/* 700 */     if (javafxRuntimePath != null) {
/* 701 */       if (verbose) {
/* 702 */         System.err.println("    javafx.runtime.path = " + javafxRuntimePath);
/*     */       }
/* 704 */       launchMethod = findLaunchMethodInJar(javafxRuntimePath, fxClassPath);
/*     */     }
/* 706 */     if (launchMethod != null) return launchMethod;
/*     */ 
/* 708 */     if (verbose) {
/* 709 */       System.err.println("3) Look for cobundled JavaFX ... [java.home=" + System.getProperty("java.home"));
/*     */     }
/*     */ 
/* 712 */     launchMethod = findLaunchMethodInJar(System.getProperty("java.home"), fxClassPath);
/*     */ 
/* 714 */     if (launchMethod != null) return launchMethod;
/*     */ 
/* 717 */     if (verbose) {
/* 718 */       System.err.println("4) Look in the OS platform registry...");
/*     */     }
/* 720 */     javafxRuntimePath = lookupRegistry();
/* 721 */     if (javafxRuntimePath != null) {
/* 722 */       if (verbose) {
/* 723 */         System.err.println("    Installed JavaFX runtime found in: " + javafxRuntimePath);
/*     */       }
/*     */ 
/* 726 */       launchMethod = findLaunchMethodInJar(javafxRuntimePath, fxClassPath);
/* 727 */       if (launchMethod != null) return launchMethod;
/*     */ 
/*     */     }
/*     */ 
/* 735 */     if (verbose) {
/* 736 */       System.err.println("5) Look in hardcoded paths");
/*     */     }
/* 738 */     String[] hardCodedPaths = { "../rt", "../../../../rt", "../../sdk/rt", "../../../artifacts/sdk/rt" };
/*     */ 
/* 745 */     for (int i = 0; i < hardCodedPaths.length; i++) {
/* 746 */       javafxRuntimePath = hardCodedPaths[i];
/* 747 */       launchMethod = findLaunchMethodInJar(javafxRuntimePath, fxClassPath);
/* 748 */       if (launchMethod != null) return launchMethod;
/*     */     }
/*     */ 
/* 751 */     return launchMethod;
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */   {
/* 756 */     verbose = Boolean.getBoolean("javafx.verbose");
/*     */ 
/* 759 */     checkJre();
/*     */     try
/*     */     {
/* 763 */       attrs = getJarAttributes();
/*     */     } catch (Exception ex) {
/* 765 */       ex.printStackTrace();
/* 766 */       errorExit("Unable to load jar manifest");
/*     */     }
/*     */ 
/* 770 */     String appName = getAppName(attrs, false);
/* 771 */     if (verbose) {
/* 772 */       System.err.println("appName = " + appName);
/*     */     }
/* 774 */     if (appName == null) {
/* 775 */       errorExit("Unable to find application class name");
/*     */     }
/*     */ 
/* 779 */     String preloaderName = getAppName(attrs, true);
/* 780 */     if (verbose) {
/* 781 */       System.err.println("preloaderName = " + preloaderName);
/*     */     }
/*     */ 
/* 784 */     String[] embeddedArgs = getAppArguments(attrs);
/* 785 */     if (verbose) {
/* 786 */       System.err.println("embeddedArgs = " + Arrays.toString(embeddedArgs));
/* 787 */       System.err.println("commandLineArgs = " + Arrays.toString(args));
/*     */     }
/*     */ 
/* 790 */     String updateHook = attrs.getValue("X-JavaFX-Update-Hook");
/* 791 */     if ((verbose) && (updateHook != null))
/* 792 */       System.err.println("updateHook = " + updateHook);
/*     */     String fxClassPath;
/*     */     String fxClassPath;
/* 797 */     if (attrs != null)
/* 798 */       fxClassPath = attrs.getValue("JavaFX-Class-Path");
/*     */     else {
/* 800 */       fxClassPath = "";
/*     */     }
/*     */ 
/* 803 */     Method launchMethod = findLaunchMethod(fxClassPath);
/* 804 */     if (launchMethod != null) {
/* 805 */       launchApp(launchMethod, appName, preloaderName, updateHook, args.length > 0 ? args : embeddedArgs);
/*     */ 
/* 807 */       return;
/*     */     }
/*     */ 
/* 810 */     showFallback(false);
/*     */   }
/*     */ 
/*     */   private static void showFallback(boolean jreError) {
/* 814 */     SwingUtilities.invokeLater(new Runnable() { private final boolean val$jreError;
/*     */ 
/* 816 */       public void run() { JFrame f = new JFrame("JavaFX Launcher");
/* 817 */         f.setDefaultCloseOperation(3);
/* 818 */         JApplet japp = null;
/*     */ 
/* 821 */         if (Main.attrs.getValue("JavaFX-Fallback-Class") != null) {
/* 822 */           Class customFallback = null;
/*     */           try {
/* 824 */             customFallback = Class.forName(Main.attrs.getValue("JavaFX-Fallback-Class"), false, Thread.currentThread().getContextClassLoader());
/*     */           }
/*     */           catch (ClassNotFoundException ce)
/*     */           {
/* 828 */             System.err.println("Custom fallback class is not found: " + Main.attrs.getValue("JavaFX-Fallback-Class"));
/*     */           }
/*     */ 
/* 834 */           if ((customFallback != null) && (!NoJavaFXFallback.class.getName().equals(customFallback))) {
/*     */             try
/*     */             {
/* 837 */               japp = (JApplet)customFallback.newInstance();
/*     */             } catch (Exception e) {
/* 839 */               System.err.println("Failed to instantiate custom fallback " + customFallback.getName() + " due to " + e);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 846 */         if (japp == null)
/*     */         {
/* 849 */           japp = new NoJavaFXFallback(this.val$jreError, !this.val$jreError, "2.1.0");
/*     */ 
/* 851 */           f.getContentPane().add(japp);
/* 852 */           f.pack();
/* 853 */           f.setVisible(true);
/*     */         } else {
/* 855 */           japp.init();
/* 856 */           f.getContentPane().add(japp);
/* 857 */           japp.start();
/* 858 */           f.pack();
/* 859 */           f.setVisible(true);
/*     */         } }
/*     */     });
/*     */   }
/*     */ 
/*     */   private static void errorExit(String string)
/*     */   {
/*     */     try
/*     */     {
/* 868 */       Runnable runnable = new Runnable() {
/*     */         private final String val$string;
/*     */ 
/*     */         public void run() { try { Class componentClass = Class.forName("java.awt.Component");
/* 872 */             Class jOptionPaneClass = Class.forName("javax.swing.JOptionPane");
/* 873 */             Field ERROR_MESSAGE_Field = jOptionPaneClass.getField("ERROR_MESSAGE");
/* 874 */             int ERROR_MESSAGE = ERROR_MESSAGE_Field.getInt(null);
/* 875 */             Method showMessageDialogMethod = jOptionPaneClass.getMethod("showMessageDialog", new Class[] { componentClass, Object.class, String.class, Integer.TYPE });
/*     */ 
/* 881 */             showMessageDialogMethod.invoke(null, new Object[] { null, this.val$string, "JavaFX Launcher Error", new Integer(ERROR_MESSAGE) });
/*     */           }
/*     */           catch (Exception ex)
/*     */           {
/* 885 */             ex.printStackTrace();
/*     */           }
/*     */         }
/*     */       };
/* 890 */       Class swingUtilsClass = Class.forName("javax.swing.SwingUtilities");
/* 891 */       Method invokeAndWaitMethod = swingUtilsClass.getMethod("invokeAndWait", new Class[] { Runnable.class });
/*     */ 
/* 893 */       invokeAndWaitMethod.invoke(null, new Object[] { runnable });
/* 894 */       if (verbose)
/* 895 */         System.err.println("Done with invoke and wait");
/*     */     }
/*     */     catch (Exception ex) {
/* 898 */       ex.printStackTrace();
/*     */     }
/*     */ 
/* 901 */     System.exit(1);
/*     */   }
/*     */ }

/* Location:           /home/rcls/Desktop/Ants/karincca.jar
 * Qualified Name:     com.javafx.main.Main
 * JD-Core Version:    0.6.2
 */