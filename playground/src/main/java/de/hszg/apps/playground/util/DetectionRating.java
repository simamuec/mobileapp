package de.hszg.apps.playground.util;

/**
 * Projectname: MobileApp
 * Created on 02.12.2016.
 */

public enum DetectionRating {

    LOW(1), MIDDLE(2), HIGH(3);

    private int detection;

    DetectionRating(int detection) {
        this.detection = detection;
    }

    public int getValue() {
        return detection;
    }
}
