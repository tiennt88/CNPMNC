//package com.scrape.interceptor;
//
//import com.scrape.common.HibernateUtil;
//import com.opensymphony.xwork2.ActionInvocation;
//import com.opensymphony.xwork2.interceptor.Interceptor;
//import com.scrape.HibernateUtil;
//import java.util.HashMap;
//import org.apache.log4j.Logger;
//
//public class HibernateConnectInterceptor
//  implements Interceptor
//{
//  private static final Logger log = Logger.getLogger(HibernateConnectInterceptor.class);
//
//  public String intercept(ActionInvocation ai)
//    throws Exception
//  {
//    HashMap sessionsToRollBack = new HashMap();
//    try
//    {
//      boolean errorWhenExcuteAction = false;
//      Exception exceptionObject = null;
//      try {
//        ai.invoke();
//      }
//      catch (Exception ex)
//      {
//      }
//      finally
//      {
//        String msg;
//        String msg;
//        if (errorWhenExcuteAction) {
//          sessionsToRollBack = HibernateUtil.getCurrentSessions();
//          String msg = String.format("Lỗi khi thực hiện action %s, rollback toàn bộ các transaction chưa được commit", new Object[] { ai.getAction().toString() });
//
//          log.error(msg, exceptionObject);
//        }
//        else
//        {
//          sessionsToRollBack = HibernateUtil.commitCurrentSessions();
//          if (sessionsToRollBack.size() > 0) {
//            String msg = "Lỗi khi thực hiện commit transaction, rollback các transaction chưa được commit";
//            log.error(msg);
//          }
//
//        }
//
//      }
//
//    }
//    catch (Exception ex)
//    {
//      HibernateUtil.rollBackSessions(sessionsToRollBack);
//
//      throw ex;
//    }
//    finally {
//      HibernateUtil.closeCurrentSessions();
//    }
//
//    return "success";
//  }
//
//  public void destroy()
//  {
//  }
//
//  public void init()
//  {
//  }
//}