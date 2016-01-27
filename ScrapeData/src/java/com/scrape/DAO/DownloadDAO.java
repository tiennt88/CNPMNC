/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.DAO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author KeyLove
 */
public class DownloadDAO extends BaseHibernateDAOMDB {

    private InputStream inputStream;
    private String filename;

    public String download() throws Exception {
        File file = null;
        if (getRequest().getParameter("filePath") != null) {
            file = new File(getRequest().getParameter("filePath").toString());
        }

        inputStream = new FileInputStream(file);
        filename = file.getName();
        HttpServletResponse response = getResponse();
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Content-Disposition", "attachment;filename =\"" + filename + "\"");
        response.setHeader("Pragma", "public");
        response.setHeader("Expires", "0");
        response.setHeader("Content-Transfer-Encoding", "binary");
        ServletOutputStream sos = response.getOutputStream();
        rewrite(inputStream, sos);
        sos.flush();
        return "success";
    }

    protected void rewrite(InputStream input, OutputStream output) throws IOException {
        int numRead;
        if ((input == null) || (output == null)) {
            return;
        }
        byte[] buf = new byte[4096];
        while (!((numRead = input.read(buf)) < 0)) {
            output.write(buf, 0, numRead);
        }
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getFileName() {
        return filename;
    }

    public void setFileName(String fileName) {
        this.filename = fileName;
    }
}
