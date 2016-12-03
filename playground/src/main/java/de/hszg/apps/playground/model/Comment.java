package de.hszg.apps.playground.model;

import java.util.Date;
import java.util.List;

/**
 * Projectname: MobileApp
 * Created on 27.11.2016.
 */

public class Comment {

    private int id;
    private String author;
    private String text;
    private Date timestamp;
    private List<Comment> listOfAnswers;

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<Comment> getListOfAnswers() {
        return listOfAnswers;
    }

    public void setListOfAnswers(List<Comment> listOfAnswers) {
        this.listOfAnswers = listOfAnswers;
    }
}
