package de.hszg.apps.playground.parser;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.StringReader;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import de.hszg.apps.playground.model.Publication;


/**
 * Created on 27.11.2016.
 */
public class Parser {

    public List<Publication> parse (String content) throws XmlPullParserException, IOException{
        List<Publication> publications =  new ArrayList<>();
        XmlPullParser xmlPullParser = Xml.newPullParser();
        xmlPullParser.setInput(new StringReader(content));
        while(xmlPullParser.next()!= XmlPullParser.END_TAG){
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = xmlPullParser.getName();
            // Starts by looking for the entry tag
            if (name.equals("entry")) {
                publications.add(readEntry(xmlPullParser));
            } else {
                skip(xmlPullParser);
            }
        }
        return publications;
    }


    private Publication readEntry(XmlPullParser xmlPullParser) throws XmlPullParserException,IOException {
        xmlPullParser.require(XmlPullParser.START_TAG, null, "entry");
        Publication publication = new Publication();

        while (xmlPullParser.next() != XmlPullParser.END_TAG) {
            if (xmlPullParser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }

            String name = xmlPullParser.getName();
            if (name.equals("id")) {
                publication.setId(readTitle(xmlPullParser,"id"));
            } else if (name.equals("entryDate")) {
                publication.setEntryDate(Date.valueOf(readTitle(xmlPullParser,"entryDate")));
            } else if (name.equals("revisionDate")) {
                publication.setRevisionDate(Date.valueOf(readTitle(xmlPullParser,"revisionDate")));
            } else if (name.equals("title")) {
                publication.setTitle(readTitle(xmlPullParser,"title"));
            } else if (name.equals("status")) {
                publication.setStatus(readTitle(xmlPullParser,"status"));//enum
            } else if (name.equals("numberOfReports")) {
                publication.setNumberOfReports(Integer.valueOf(readTitle(xmlPullParser,"numberOfReports")));
            } else if (name.equals("numberOfComments")) {
                publication.setNumberOfComments(Integer.valueOf(readTitle(xmlPullParser,"numberOfComments")));
            }else {
                skip(xmlPullParser);
            }
        }
        return new Publication();
    }


    private String readTitle(XmlPullParser parser, String name) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, null, name);
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, null, name);
        return title;
    }


    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }


    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }

}
