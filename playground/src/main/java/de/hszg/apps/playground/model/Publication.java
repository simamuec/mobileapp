package de.hszg.apps.playground.model;

import java.util.Date;

/**
 * Projectname: MobileApp
 * Created on 27.11.2016.
 */

public class Publication {

    private String id;
    private Date entryDate;
    private Date revisionDate;
    private String title;
    private String status;
    private int numberOfReports;
    private int numberOfComments;

    public Publication() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNumberOfReports() {
        return numberOfReports;
    }

    public void setNumberOfReports(int numberOfReports) {
        this.numberOfReports = numberOfReports;
    }

    public int getNumberOfComments() {
        return numberOfComments;
    }

    public void setNumberOfComments(int numberOfComments) {
        this.numberOfComments = numberOfComments;
    }

    @Override
    public String toString() {
        return "Publication{" +
                "id='" + id + '\'' +
                ", entryDate=" + entryDate +
                ", revisionDate=" + revisionDate +
                ", title='" + title + '\'' +
                ", status='" + status + '\'' +
                ", numberOfReports=" + numberOfReports +
                ", numberOfComments=" + numberOfComments +
                '}';
    }

}
