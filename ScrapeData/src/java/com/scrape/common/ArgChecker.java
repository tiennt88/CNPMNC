/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.common;
import java.util.Collection;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 *
 * @author KeyLove
 */
public class ArgChecker {
    private static Log log = LogFactory.getLog(ArgChecker.class);

  public static void denyNull(Object arg0)
    throws IllegalArgumentException
  {
    denyNull(arg0, new Object(), new Object());
  }

  public static void denyNull(Object arg0, Object arg1)
    throws IllegalArgumentException
  {
    denyNull(arg0, arg1, new Object());
  }

  public static void denyNull(Object arg0, Object arg1, Object arg2)
    throws IllegalArgumentException
  {
    if ((arg0 != null) && (arg1 != null) && (arg2 != null))
      return;
    deny("Null value is denied");
  }

  public static void denyBlank(String arg0)
    throws IllegalArgumentException
  {
    denyBlank(arg0, "A", "A");
  }

  public static void denyBlank(String arg0, String arg1)
    throws IllegalArgumentException
  {
    denyBlank(arg0, arg1, "A");
  }

  public static void denyBlank(String arg0, String arg1, String arg2)
    throws IllegalArgumentException
  {
    if ((!StringUtils.isBlank(arg0)) && (!StringUtils.isBlank(arg1)) && (!StringUtils.isBlank(arg2)))
      return;
    deny("Blank value is denied");
  }

  public static void denyEmpty(String arg0)
    throws IllegalArgumentException
  {
    denyEmpty(arg0, "A", "A");
  }

  public static void denyEmpty(String arg0, String arg1)
    throws IllegalArgumentException
  {
    denyEmpty(arg0, arg1, "A");
  }

  public static void denyEmpty(String arg0, String arg1, String arg2)
    throws IllegalArgumentException
  {
    if ((!StringUtils.isEmpty(arg0)) && (!StringUtils.isEmpty(arg1)) && (!StringUtils.isEmpty(arg2)))
      return;
    deny("Empty value is denied");
  }

  public static void denyEmpty(Collection arg0)
    throws IllegalArgumentException
  {
    denyNull(arg0);
    if (arg0.size() != 0)
      return;
    deny("Empty value is denied");
  }

  public static void denyEmpty(Object[] arg0)
    throws IllegalArgumentException
  {
    denyNull(arg0);
    if (arg0.length != 0)
      return;
    deny("Empty value is denied");
  }

  public static void denyEmpty(short[] arg0)
    throws IllegalArgumentException
  {
    denyNull(arg0);
    if (arg0.length != 0)
      return;
    deny("Empty value is denied");
  }

  public static void denyEmpty(int[] arg0)
    throws IllegalArgumentException
  {
    denyNull(arg0);
    if (arg0.length != 0)
      return;
    deny("Empty value is denied");
  }

  public static void denyEmpty(long[] arg0)
    throws IllegalArgumentException
  {
    denyNull(arg0);
    if (arg0.length != 0)
      return;
    deny("Empty value is denied");
  }

  public static void denyEmpty(float[] arg0)
    throws IllegalArgumentException
  {
    denyNull(arg0);
    if (arg0.length != 0)
      return;
    deny("Empty value is denied");
  }

  public static void denyEmpty(double[] arg0)
    throws IllegalArgumentException
  {
    denyNull(arg0);
    if (arg0.length != 0)
      return;
    deny("Empty value is denied");
  }

  public static void denyNotEquals(Object arg0, Object arg1)
    throws IllegalArgumentException
  {
    if ((arg0 == null) && (arg1 == null))
    {
      return;
    }
    if ((arg0 != null) && (arg1 != null) && (arg1.equals(arg0)))
      return;
    deny("Different values are denied");
  }

  public static void denyNotEquals(boolean arg0, boolean arg1)
    throws IllegalArgumentException
  {
    if (arg0 == arg1)
      return;
    deny("Different values are denied");
  }

  public static void denyNotEquals(short arg0, short arg1)
    throws IllegalArgumentException
  {
    if (arg0 == arg1)
      return;
    deny("Different values are denied");
  }

  public static void denyNotEquals(int arg0, int arg1)
    throws IllegalArgumentException
  {
    if (arg0 == arg1)
      return;
    deny("Different values are denied");
  }

  public static void denyNotEquals(long arg0, long arg1)
    throws IllegalArgumentException
  {
    if (arg0 == arg1)
      return;
    deny("Different values are denied");
  }

  public static void denyNotAssignableFrom(Class arg0, Class arg1)
    throws IllegalArgumentException
  {
    if ((arg0 != null) && (arg1 != null) && (arg0.isAssignableFrom(arg1)))
      return;
    deny("Not assignable from");
  }

  private static void deny(String denyMsg)
    throws IllegalArgumentException
  {
    log.error("Illegal Argument : " + denyMsg);
    throw new IllegalArgumentException("Illegal Argument : " + denyMsg);
  }
}
