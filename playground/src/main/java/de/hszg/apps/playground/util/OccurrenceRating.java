package de.hszg.apps.playground.util;

/**
 * Projectname: MobileApp
 * Created on 02.12.2016.
 */

public enum OccurrenceRating {

LOW(1), MIDDLE(2), HIGH(3);

    private int occurrence;

    OccurrenceRating(int occurrence) {
    this.occurrence = occurrence;
    }

    public int getValue() {
        return occurrence;
    }
}
