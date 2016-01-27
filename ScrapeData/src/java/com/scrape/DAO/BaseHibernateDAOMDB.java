/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.DAO;

import com.scrape.BO.BasicBO;
import com.scrape.common.ArgChecker;
import com.opensymphony.xwork2.ActionSupport;
import com.scrape.config.database.ReadDBConfig;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.xml.sax.SAXException;

/**
 *
 * @author KeyLove
 */
public class BaseHibernateDAOMDB extends ActionSupport{

    public static final ResourceBundle rb = ResourceBundle.getBundle("config");
    
    public static HashMap<String, SessionFactory> sessionFactoryMap = new HashMap();
    public static final ThreadLocal sessionMapsThreadLocal = new ThreadLocal();

    public static Session getSession(String key) throws HibernateException {

        HashMap<String, Session> sessionMaps = (HashMap<String, Session>) sessionMapsThreadLocal.get();

        if (sessionMaps == null) {
            sessionMaps = new HashMap<String, Session>();
            sessionMapsThreadLocal.set(sessionMaps);
        }

        // Open a new Session, if this Thread has none yet
        Session s = (Session) sessionMaps.get(key);
        if (s == null) {
            s = ((SessionFactory) sessionFactoryMap.get(key)).openSession();
            sessionMaps.put(key, s);
        }

        return s;
    }

    public static Session getSession() throws HibernateException {
        return getSession("default session");
    }

    public static void closeSessions() throws HibernateException {
        HashMap<String, Session> sessionMaps = (HashMap<String, Session>) sessionMapsThreadLocal.get();
        sessionMapsThreadLocal.set(null);
        if (sessionMaps != null) {
            for (Session session : sessionMaps.values()) {
                if (session.isOpen()) {
                    session.close();
                }
            }
        }
    }

    public static void closeSession() {
        HashMap sessionMaps = (HashMap) sessionMapsThreadLocal.get();
        sessionMapsThreadLocal.set(null);
        if (sessionMaps != null) {
            Session session = (Session) sessionMaps.get("default session".toLowerCase());
            if ((session != null) && (session.isOpen())) {
                session.close();
            }
        }
    }

    public static void closeSession(String key) {
        HashMap sessionMaps = (HashMap) sessionMapsThreadLocal.get();
        sessionMapsThreadLocal.set(null);
        if (sessionMaps != null) {
            Session session = (Session) sessionMaps.get(key.toLowerCase());
            if ((session != null) && (session.isOpen())) {
                session.close();
            }
        }
    }

    public static void buildSessionFactories(HashMap<String, String> configs) {
        try {
            for (String key : configs.keySet()) {
                URL url = BaseHibernateDAOMDB.class.getResource((String) configs.get(key));
                SessionFactory sessionFactory = new Configuration() {
                }.configure(url).buildSessionFactory();

                sessionFactoryMap.put(key, sessionFactory);
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.out);

            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void buildSessionFactory(String key, String path) {
        try {
            SessionFactory sessionFactory = new AnnotationConfiguration().configure(path).buildSessionFactory();
            sessionFactoryMap.put(key, sessionFactory);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static void buildSessionFactory(String path) {
        try {
            SessionFactory sessionFactory = new AnnotationConfiguration().configure(path).buildSessionFactory();
            sessionFactoryMap.put("default session", sessionFactory);
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }


    public void save(BasicBO objectToSave) throws Exception {
        Session session = getSession("default session");
        ArgChecker.denyNull(objectToSave);
        session.save(objectToSave);
    }

    public void update(BasicBO objectToUpdate) throws Exception {
        Session session = getSession("default session");
        ArgChecker.denyNull(objectToUpdate);
        session.update(objectToUpdate);
    }

    public void saveOrUpdate(BasicBO objectToSaveOrUpdate) throws Exception {
        Session session = getSession("default session");
        ArgChecker.denyNull(objectToSaveOrUpdate);
        session.saveOrUpdate(objectToSaveOrUpdate);
    }

    public void delete(BasicBO objectToDelete) throws Exception {
        Session session = getSession("default session");
        ArgChecker.denyNull(objectToDelete);
        session.delete(objectToDelete);
    }

    public void refresh(BasicBO objectToRefresh) throws Exception {
        Session session = getSession("default session");
        session.refresh(objectToRefresh);
    }

    public BasicBO get(Object id, String strClassHandle) throws Exception {
        Session session = getSession("default session");
        BasicBO instance = (BasicBO) session.get(strClassHandle, (Serializable) id);
        session.refresh(instance);
        return instance;
    }

    public List getAll(String strClassHandle) {
        Session session = getSession("default session");
        String queryString = "from " + strClassHandle;
        Query queryObject = session.createQuery(queryString);
        return queryObject.list();
    }

    public List findByProperty(String strClassHandle, String propertyName, Object value) {
        List lstReturn = new ArrayList();
        String queryString = "from " + strClassHandle + " as model where model." + propertyName + "= ?";
        Query queryObject = getSession("default session").createQuery(queryString);
        queryObject.setParameter(0, value);
        lstReturn = queryObject.list();
        return lstReturn;
    }

    public List findByProperty(Class T, HashMap<String, Object> map) {
        List lstReturn = new ArrayList();
        Criteria cri = getSession("default session").createCriteria(T);
        if(map.size()> 0){
            for (Entry<String, Object> e : map.entrySet()){
                System.out.println("Value for " + e.getKey() + " : " + e.getValue());
                cri.add(Restrictions.eq(e.getKey(), e.getValue()));
             }
            lstReturn = cri.list();
            if (lstReturn!= null){
                System.out.println(lstReturn.size());
            }
            
        }

        return lstReturn;
    }

    public static long getSequence(String sequenceName) throws Exception {
        String strQuery = "SELECT " + sequenceName + " .NextVal FROM Dual";
        Query queryObject = getSession("default session").createSQLQuery(strQuery);
        BigDecimal bigDecimal = (BigDecimal) queryObject.uniqueResult();
        return bigDecimal.longValue();
    }

    public static Date getSysDate() throws Exception {
        String strQuery = "SELECT getdate()";
        Query queryObject = getSession("default session").createSQLQuery(strQuery);
        Date date =  (Date) queryObject.list().get(0);
        return date;
    }

    public void save(BasicBO objectToSave, String sessionName) throws Exception {
        Session session = getSession(sessionName);
        ArgChecker.denyNull(objectToSave);
        session.save(objectToSave);
    }

    public void update(BasicBO objectToUpdate, String sessionName) throws Exception {
        Session session = getSession(sessionName);
        ArgChecker.denyNull(objectToUpdate);
        session.update(objectToUpdate);
    }

    public void saveOrUpdate(BasicBO objectToSaveOrUpdate, String sessionName) throws Exception {
        Session session = getSession(sessionName);
        ArgChecker.denyNull(objectToSaveOrUpdate);
        session.saveOrUpdate(objectToSaveOrUpdate);
    }

    public void delete(BasicBO objectToDelete, String sessionName) throws Exception {
        Session session = getSession(sessionName);
        ArgChecker.denyNull(objectToDelete);
        session.delete(objectToDelete);
    }

    public void refresh(BasicBO objectToRefresh, String sessionName) throws Exception {
        Session session = getSession(sessionName);
        session.refresh(objectToRefresh);
    }

    public BasicBO get(Object id, String strClassHandle, String sessionName) throws Exception {
        Session session = getSession(sessionName);
        BasicBO instance = (BasicBO) session.get(strClassHandle, (Serializable) id);
        session.refresh(instance);
        return instance;
    }

    public List getAll(String strClassHandle, String sessionName) {
        Session session = getSession(sessionName);
        String queryString = "from " + strClassHandle;
        Query queryObject = session.createQuery(queryString);
        return queryObject.list();
    }

    public List findByProperty(String strClassHandle, String propertyName, Object value, String sessionName) {
        List lstReturn = new ArrayList();
        String queryString = "from " + strClassHandle + " as model where model." + propertyName + "= ?";
        Query queryObject = getSession(sessionName).createQuery(queryString);
        queryObject.setParameter(0, value);
        lstReturn = queryObject.list();
        return lstReturn;
    }

    public static long getSequence(String sequenceName, String sessionName) throws Exception {
        String strQuery = "SELECT " + sequenceName + " .NextVal FROM Dual";
        Query queryObject = getSession(sessionName).createSQLQuery(strQuery);
        BigDecimal bigDecimal = (BigDecimal) queryObject.uniqueResult();
        return bigDecimal.longValue();
    }

    public List query(String query, Object[] params) {
        List result = new ArrayList();
        Set key = sessionFactoryMap.keySet();
        Iterator iter = key.iterator();
        while (iter.hasNext()) {
            Session s = getSession((String) iter.next());
            Query q = s.createQuery(query);
            for (int i = 0; i < params.length; ++i) {
                q.setParameter(i, params[i]);
            }
            result.addAll(q.list());
        }
        return result;
    }

    public  HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();
    }

    public  HttpServletResponse getResponse() {
        return ServletActionContext.getResponse();
    }


    public static void closeObject(Connection obj) {
        try {
            if ((obj != null)
                    && (!obj.isClosed())) {
                if (!obj.getAutoCommit()) {
                    obj.rollback();
                }
                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public static void closeObject(CallableStatement obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeObject(Statement obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeObject(ResultSet obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeObject(PreparedStatement obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    static {
        try{
            buildSessionFactory("com/scrape/config/database/hibernate.cfg.xml");
            System.out.println("Khởi tạo thành công Hibernate cho 1 session!");

//            SAXParserFactory spfac = SAXParserFactory.newInstance();
//            //Now use the parser factory to create a SAXParser object
//            SAXParser sp = spfac.newSAXParser();
//
//            //Create an instance of this class; it defines all the handler methods
//            ReadDBConfig handler = new ReadDBConfig();
//
//            //Finally, tell the parser to parse the input and notify the handler
////            String url = ServletActionContext.getServletContext().getRealPath("DBConfig.xml");
////            System.out.println(url);
//            sp.parse("src/java/com/scrape/config/database/DBConfig.xml", handler);
////            sp.parse(url, handler);
//
//            for(int i=0; i<handler.getList().size(); i++){
//                buildSessionFactory(handler.getList().get(i).getUrl());
//                System.out.println("Khởi tạo thành công Hibernate cho session: " + handler.getList().get(i).getName());
//            }

        }catch(Exception ex){
            System.out.println("Lỗi khởi tạo session Hibernate");
            ex.printStackTrace();
        }
    }

    private static String convertToFileURL(String filename) {
        String path = new File(filename).getAbsolutePath();
        if (File.separatorChar != '/') {
            path = path.replace(File.separatorChar, '/');
        }

        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        return "file:" + path;
    }

    
 public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
//    Configuration configuration = new Configuration().configure("com/scrape/config/database/hibernate.cfg.xml");
//    SessionFactory factory = configuration.buildSessionFactory();
//    Session session = factory.openSession();
     //Create a "parser factory" for creating SAX parsers

     System.out.println(convertToFileURL("DBConfig.xml"));

//    try{
//        SAXParserFactory spfac = SAXParserFactory.newInstance();
//
//        //Now use the parser factory to create a SAXParser object
//        SAXParser sp = spfac.newSAXParser();
//
//        //Create an instance of this class; it defines all the handler methods
//        ReadDBConfig handler = new ReadDBConfig();
//
//        //Finally, tell the parser to parse the input and notify the handler
//        sp.parse("src\\java\\com\\scrape\\config\\database\\DBConfig.xml", handler);
//
//        for(int i=0; i<handler.getList().size(); i++){
//            buildSessionFactory(handler.getList().get(i).getName(),handler.getList().get(i).getUrl());
//            System.out.println("Khởi tạo thành công Hibernate cho session: " + handler.getList().get(i).getName());
//        }
//        Session session = getSession();
//        Session session2 = getSession("db2");
//        System.out.println("?");
//    } catch(Exception ex){
//        ex.printStackTrace();
//    }
    
 }


 
}
