/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scrape.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDecryptUtils
{
  private static byte[] key = { -95, -29, -62, 25, 25, -83, -18, -85 };

  private static String algorithm = "DES";

  private static SecretKeySpec keySpec = new SecretKeySpec(key, algorithm);
  private static final int BUFFER_SIZE = 8120;

  public static byte[] encrypt(byte[] arrByte)
    throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
  {
    Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(1, keySpec);
    byte[] data = cipher.doFinal(arrByte);

    return data;
  }

  public static byte[] decrypt(byte[] arrByte)
    throws Exception
  {
    Cipher cipher = Cipher.getInstance(algorithm);
    cipher.init(2, keySpec);
    return cipher.doFinal(arrByte);
  }

  public static String decryptToString()
    throws Exception
  {
    String input = "";
    String[] arrTemp = input.split("#");
    byte[] c = new byte[arrTemp.length];
    int i = 0;
    for (String a : arrTemp) {
      byte b = new Byte(a).byteValue();
      System.out.println(b);
      c[i] = b;
      ++i;
    }

    return new String(decrypt(c));
  }

  public static void encryptFile(String originalFilePath, String encryptedFilePath)
  {
    try
    {
      FileInputStream stream = new FileInputStream(originalFilePath);
      OutputStream out = new FileOutputStream(encryptedFilePath);
      int bytesRead = 0;
      byte[] buffer = new byte[8120];
      while ((bytesRead = stream.read(buffer, 0, 8120)) != -1)
      {
        byte[] cloneBuffer = new byte[bytesRead];
        if (bytesRead < buffer.length) {
          for (int i = 0; i < bytesRead; ++i) {
            cloneBuffer[i] = buffer[i];
          }
        }
        out.write(encrypt(cloneBuffer));
      }

      stream.close();
      out.close();
    } catch (FileNotFoundException fex) {
      fex.printStackTrace();
    }
    catch (IOException iex) {
      iex.printStackTrace();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void decryptFile(String encryptedFilePath, String decryptedFilePath)
  {
    try
    {
      FileInputStream stream = new FileInputStream(encryptedFilePath);
      OutputStream out = new FileOutputStream(decryptedFilePath);
      int bytesRead = 0;
      byte[] buffer = new byte[8120];
      while ((bytesRead = stream.read(buffer, 0, 8120)) != -1) {
        System.out.println(bytesRead);
        byte[] cloneBuffer = new byte[bytesRead];
        if (bytesRead < buffer.length) {
          for (int i = 0; i < bytesRead; ++i) {
            cloneBuffer[i] = buffer[i];
          }
        }
        out.write(decrypt(cloneBuffer));
      }

      stream.close();
      out.close();
    } catch (FileNotFoundException fex) {
      fex.printStackTrace();
    }
    catch (IOException iex) {
      iex.printStackTrace();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static String decryptFile(String encryptedFilePath)
  {
    String returnValue = "";
    try {
      FileInputStream stream = new FileInputStream(encryptedFilePath);
      int bytesRead = 0;
      byte[] buffer = new byte[8120];
      while ((bytesRead = stream.read(buffer, 0, 8120)) != -1)
      {
        byte[] cloneBuffer = new byte[bytesRead];
        if (bytesRead < buffer.length) {
          for (int i = 0; i < bytesRead; ++i) {
            cloneBuffer[i] = buffer[i];
          }
        }
        returnValue = returnValue + new String(decrypt(cloneBuffer));
      }

      stream.close();
    }
    catch (FileNotFoundException fex) {
      fex.printStackTrace();
    }
    catch (IOException iex) {
      iex.printStackTrace();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return returnValue;
  }

  public static String decryptFile(InputStream stream)
  {
    String returnValue = "";
    try {
      int bytesRead = 0;
      byte[] buffer = new byte[8192];

      while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
        byte[] cloneBuffer = new byte[bytesRead];
        if (bytesRead < buffer.length) {
          for (int i = 0; i < bytesRead; ++i) {
            cloneBuffer[i] = buffer[i];
          }
        }
        returnValue = returnValue + new String(decrypt(cloneBuffer));
      }

      stream.close();
    }
    catch (FileNotFoundException fex) {
      fex.printStackTrace();
    }
    catch (IOException iex) {
      iex.printStackTrace();
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return returnValue;
  }
}