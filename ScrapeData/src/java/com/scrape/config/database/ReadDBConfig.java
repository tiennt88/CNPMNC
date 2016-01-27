/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.config.database;

import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author KeyLove
 */
public class ReadDBConfig extends DefaultHandler {

    
    private String temp;
    private DBConfigBO db;
    private List<DBConfigBO> list = new ArrayList<DBConfigBO>();


    public static void main(String[] args) {
        try {
            //Create a "parser factory" for creating SAX parsers
            SAXParserFactory spfac = SAXParserFactory.newInstance();

            //Now use the parser factory to create a SAXParser object
            SAXParser sp = spfac.newSAXParser();

            //Create an instance of this class; it defines all the handler methods
            ReadDBConfig handler = new ReadDBConfig();

            //Finally, tell the parser to parse the input and notify the handler
            sp.parse("src\\java\\com\\scrape\\config\\database\\DBConfig.xml", handler);

            handler.readList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    /*
     * When the parser encounters plain text (not XML elements),
     * it calls(this method, which accumulates them in a string buffer
     */
    public void characters(char[] buffer, int start, int length) {
        temp = new String(buffer, start, length);
    }


    /*
     * Every time the parser encounters the beginning of a new element,
     * it calls this method, which resets the string buffer
     */
    public void startElement(String uri, String localName,
            String qName, Attributes attributes) throws SAXException {
        temp = "";
        if (qName.equalsIgnoreCase("Session-factory")) {
            db = new DBConfigBO();
            db.setType(attributes.getValue("type"));

        }
    }

    /*
     * When the parser encounters the end of an element, it calls this method
     */
    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {

        if (qName.equalsIgnoreCase("Session-factory")) {
            // add it to the list
            list.add(db);
        } else if (qName.equalsIgnoreCase("name")) {
            db.setName(temp);
        }else if (qName.equalsIgnoreCase("url")) {
            db.setUrl(temp);
        }

    }

    public void readList() {
        for(int i=0; i<list.size(); i++){
            System.out.println(list.get(i).getName());
            System.out.println(list.get(i).getUrl());
        }
    }

    public List<DBConfigBO> getList() {
        return list;
    }

    public void setList(List<DBConfigBO> list) {
        this.list = list;
    }

    
}


