/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.DAO;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import java.security.MessageDigest;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author KeyLove
 */
public class test {
//extends BaseHibernateDAOMDB

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
//            String password = "admin";
//            System.out.println(DigestUtils.md5Hex(password));
//            System.out.println(password);
//
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(password.getBytes());
//            byte byteData[] = md.digest();
//            //convert the byte to hex format method 1
//            StringBuffer sb = new StringBuffer();
//            for (int i = 0; i < byteData.length; i++) {
//                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
//            }
//
//            System.out.println("Digest(in hex format):: " + sb.toString());
//
//            //convert the byte to hex format method 2
//            StringBuffer hexString = new StringBuffer();
//            for (int i = 0; i < byteData.length; i++) {
//                String hex = Integer.toHexString(0xff & byteData[i]);
//                if (hex.length() == 1) {
//                    hexString.append('0');
//                }
//                hexString.append(hex);
//            }
//            System.out.println("Digest(in hex format):: " + hexString.toString());
//
//            System.out.println(MD5(password));

        } catch (Exception ex) {
            Logger.getLogger(test.class.getName()).log(Level.SEVERE, null, ex);
        } 

    }

    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public static String MD51(String md5) {
        try {
            StringBuilder hexString = new StringBuilder();
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte byteData[] = md.digest();
            for (int i = 0; i < byteData.length; i++) {
                String hex = Integer.toHexString(0xff & byteData[i]);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            System.out.println("Digest(in hex format):: " + hexString.toString());
            return hexString.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public void checkEmail() {
        String email = "tiennt2@fpt.com.vn";
        String password = "test";
        try {
            String endpoint = "http://login.ho.fpt.vn/fpter";
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new java.net.URL(endpoint));
            //	NamespaceUri và Service Name
            call.setOperationName(new QName("http://login.ho.fpt.vn", "authentication"));
            //	Truy?n Email và Password vào
            Boolean check = (Boolean) call.invoke(new Object[]{email, password});
            System.out.println(check);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//
//    public void test() {
//        try {
//
//            List<NoteBookCrawl> l = getAll("MatchView");
//            for (int i = 0; i < l.size(); i++) {
//                System.out.println(l.get(i).getLink());
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public String report() {
//        try {
//
//            String sql = "select nb.ITEM_CODE itemCode, nb.SERIAL , nb.PARTNO , nb.FEATURE , nb.RAM , nb.HDD ,"
//                    + " nb.SCREEN , nb.VGA , nb.OS , nbc.WEB , nbc.ITEM_NAME itemName, nbc.BRAND , nbc.PRICE ,"
//                    + " nbc.PROMOTION , nbc.LINK , nbc.INFORMATION , convert(nvarchar,nbc.CREATE_DATE,103) createDate "
//                    + " from NOTE_BOOK nb, MAPPING m, NOTE_BOOK_CRAWL nbc "
//                    + " where nb.ITEM_CODE = m.ITEM_CODE and nbc.LINK = m.LINK "
//                    + " and CONVERT(nvarchar,nbc.create_date,103) = ? "
//                    + " ORDER BY WEB ";
//
//            Query queryObjectTest = getSession().createSQLQuery(sql)
//                    .addScalar("itemCode", Hibernate.STRING)
//                    .addScalar("serial", Hibernate.STRING)
//                    .addScalar("partNo", Hibernate.STRING)
//                    .addScalar("feature", Hibernate.STRING)
//                    .addScalar("ram", Hibernate.STRING)
//                    .addScalar("hdd", Hibernate.STRING)
//                    .addScalar("screen", Hibernate.STRING)
//                    .addScalar("vga", Hibernate.STRING)
//                    .addScalar("os", Hibernate.STRING)
//                    .addScalar("web", Hibernate.STRING)
//                    .addScalar("brand", Hibernate.STRING)
//                    .addScalar("price", Hibernate.STRING)
//                    .addScalar("promotion", Hibernate.STRING)
//                    .addScalar("link", Hibernate.STRING)
//                    .addScalar("information", Hibernate.STRING)
//                    .addScalar("createDate", Hibernate.STRING)
//                    .setResultTransformer(Transformers.aliasToBean(ReportLaptop.class));
//
//                    queryObjectTest.setParameter(0, "17/03/2014");
//                    List<ReportLaptop> listTest = queryObjectTest.list();
//                    System.out.println(listTest.size());
////            getRequest().setAttribute("attachFile", downFile);
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return "success";
//    }
}
