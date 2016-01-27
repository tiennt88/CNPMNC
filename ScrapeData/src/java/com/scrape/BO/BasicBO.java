/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.BO;
import java.io.Serializable;
/**
 *
 * @author KeyLove
 */
public class BasicBO implements Serializable, Cloneable{
    private static final long serialVersionUID = 1L;

  public Object clone()
  {
    try
    {
      return super.clone();
    }
    catch (CloneNotSupportedException e)
    {
      throw new InternalError(e.toString());
    }
  }
}
