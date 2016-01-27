///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package com.scrape.common;
//
//import java.io.IOException;
//import java.net.URL;
//import java.net.URLDecoder;
//import java.util.HashMap;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.hibernate.cfg.AnnotationConfiguration;
//import org.hibernate.cfg.Configuration;
//import org.xml.sax.SAXException;
//
//public class HibernateUtil
//{
//  private static Logger log = Logger.getLogger(HibernateUtil.class);
//
//  private static HashMap<String, SessionFactory> sessionFactories = new HashMap();
//
//  private static String dbcConfigFile = "";
//
//  private static Config dbcConfig = new Config();
//  private static final String TRANS_EX_MSG_TRANSNOTSTARTED = "Transaction not successfully started";
//
//  private static void loadEncryptedDBConfig(Configuration config, String filePath)
//  {
//    log.info("Begin reading encrypted file: " + filePath);
//    URL file = Thread.currentThread().getContextClassLoader().getResource(filePath);
//    String decryptString = EncryptDecryptUtils.decryptFile(URLDecoder.decode(file.getPath()));
//    String[] properties = decryptString.split("\r\n");
//    log.info("Number of encrypted properties: " + properties.length);
//    for (String property : properties) {
//      String[] temp = property.split("=", 2);
//      if (temp.length == 2) {
//        config.setProperty(temp[0], temp[1]);
//        log.info(String.format("Set property '%s' to HibernateConfiguration", new Object[] { temp[0] }));
//      }
//    }
//    log.info("Done Reading encrypted file :" + filePath);
//  }
//
//  public static void startup()
//  {
//    log.info("Khởi tạo HibernateUtil");
//  }
//
//  public static void shutdown()
//  {
//    log.info("Destroy HibernateUtil");
//    for (String key : getSessionFactories().keySet())
//      ((SessionFactory)getSessionFactories().get(key)).close();
//  }
//
//  public static HashMap<String, SessionFactory> getSessionFactories()
//  {
//    return sessionFactories;
//  }
//
//  public static SessionFactory getSessionFactory(String sessionName)
//  {
//    return (SessionFactory)getSessionFactories().get(sessionName.toLowerCase());
//  }
//
//  public static SessionFactory getSessionFactory() {
//    return (SessionFactory)getSessionFactories().get("default session");
//  }
//
//  public static HashMap<String, Session> getCurrentSessions()
//  {
//    HashMap sessions = new HashMap();
//    for (String key : getSessionFactories().keySet()) {
//      sessions.put(key, ((SessionFactory)getSessionFactories().get(key)).getCurrentSession());
//    }
//    return sessions;
//  }
//
//  public static Session getSessionAndBeginTransaction()
//  {
//    return getSessionAndBeginTransaction("default session");
//  }
//
//  public static Session getSessionAndBeginTransaction(int transTimeout)
//  {
//    return getSessionAndBeginTransaction("default session", transTimeout);
//  }
//
//  public static Session getSessionAndBeginTransaction(String sessionName)
//  {
//    if (getSessionFactory(sessionName) == null) {
//      log.info(String.format("Không tồn tại hibernate session ứng với key +'%s'", new Object[] { sessionName }));
//      return null;
//    }
//    Session session = getSessionFactory(sessionName).getCurrentSession();
//    session.beginTransaction();
//    return session;
//  }
//
//  public static Session getSessionAndBeginTransaction(String sessionName, int transTimeout)
//  {
//    if (getSessionFactory(sessionName) == null) {
//      log.info(String.format("Không tồn tại hibernate session ứng với key +'%s'", new Object[] { sessionName }));
//      return null;
//    }
//    Session session = getSessionFactory(sessionName).getCurrentSession();
//    session.getTransaction().setTimeout(transTimeout);
//    session.getTransaction().begin();
//
//    return session;
//  }
//
//  public static HashMap<String, Session> commitCurrentSessions()
//    throws Exception
//  {
//    HashMap sessions = getCurrentSessions();
//
//    HashMap sessionsToRollBack = new HashMap();
//    boolean hasExceptionDuringCommit = false;
//
//    for (String sessionName : sessions.keySet()) {
//      Session session = (Session)sessions.get(sessionName);
//      if (session.isOpen()) {
//        Transaction t = session.getTransaction();
//
//        if ((t.isActive()) && (!hasExceptionDuringCommit)) {
//          try {
//            t.commit();
//          } catch (Throwable ex) {
//            hasExceptionDuringCommit = true;
//            sessionsToRollBack.put(sessionName, session);
//            log.error("Co loi xay ra khi commit transaction cua session " + sessionName, ex);
//          }
//        }
//        else if (hasExceptionDuringCommit) {
//          sessionsToRollBack.put(sessionName, session);
//        }
//      }
//    }
//    return sessionsToRollBack;
//  }
//
//  public static void rollBackSessions(HashMap<String, Session> sessionsToRollBack)
//  {
//    if (sessionsToRollBack != null)
//      for (String sessionName : sessionsToRollBack.keySet()) {
//        Session session = (Session)sessionsToRollBack.get(sessionName);
//        if (session.isOpen()) {
//          Transaction t = session.getTransaction();
//          try {
//            t.rollback();
//          } catch (Exception ex) {
//            if (ex.getMessage().equals("Transaction not successfully started"))
//            {
//              log.info("Session " + sessionName + " không rollback do chưa được khởi tạo");
//            }
//            else log.error("Có lỗi xảy ra khi rollback session " + sessionName, ex);
//          }
//          catch (Throwable ta)
//          {
//            log.error("Có lỗi xảy ra khi rollback session " + sessionName, ta);
//          }
//        }
//      }
//  }
//
//  public static void closeCurrentSessions()
//    throws Exception
//  {
//    HashMap sessionMaps = getCurrentSessions();
//    if (sessionMaps != null)
//      for (String key : sessionMaps.keySet()) {
//        Session session = (Session)sessionMaps.get(key);
//        try {
//          if (session.isOpen())
//            session.close();
//        }
//        catch (Exception ex) {
//          log.error("Lỗi khi đóng session \"" + key + "\"", ex);
//        }
//      }
//  }
//
//  static
//  {
//    try
//    {
//      dbcConfigFile = ResourceBundleUtil.getDBCConfigFileLocation();
//      dbcConfig.loadInstance(dbcConfigFile);
//      HashMap dbcConfigInfo = dbcConfig.getSessions();
//
//      if (dbcConfigInfo != null) {
//        for (String key : dbcConfigInfo.keySet()) {
//          String path = (String)dbcConfigInfo.get(key);
//          try {
//            log.info("Tạo SessionFactory cho file cấu hình: " + path);
//            AnnotationConfiguration dbConfig = new AnnotationConfiguration().configure(path);
//            String encryptedFile = dbcConfigFile.substring(0, dbcConfigFile.lastIndexOf("/") + 1) + key;
//            try {
//              loadEncryptedDBConfig(dbConfig, encryptedFile);
//            } catch (Throwable ex) {
//              log.info("Error while reading encrypted file: " + encryptedFile, ex);
//              log.info("Read HibernateConfigFile again, file to read: " + path);
//              dbConfig = new AnnotationConfiguration().configure(path);
//            }
//
//            SessionFactory sessionFactory = dbConfig.buildSessionFactory();
//            sessionFactories.put(key.toLowerCase(), sessionFactory);
//          } catch (Throwable ex) {
//            String msg = "Lỗi khi tạo SessionFactory của  cấu hình: %s \nSẽ không lấy đuợc HibernateSession có tên: \"%s\"";
//
//            msg = String.format(msg, new Object[] { path, key.toLowerCase() });
//            log.fatal(msg, ex);
//            log.fatal("Tiếp tục thực hiện tạo SessionFactory cho các file cấu hình tiếp theo (nếu còn)");
//          }
//        }
//      }
//
//      if (sessionFactories.isEmpty())
//        log.warn("Không tạo đuợc SessionFactory nào. Ứng dụng sẽ chạy mà không có khả năng truy cập CSDL");
//    }
//    catch (IOException ex)
//    {
//      log.fatal("Lỗi khi đọc file : " + dbcConfigFile, ex);
//      throw new ExceptionInInitializerError(ex);
//    } catch (SAXException ex) {
//      log.fatal("Lỗi khi parse nội dung file : " + dbcConfigFile, ex);
//      throw new ExceptionInInitializerError(ex);
//    }
//  }
//}