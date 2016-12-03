package de.hszg.apps.playground.util;

/**
 * Projectname: MobileApp
 * Created on 02.12.2016.
 */

public enum Significance {


    LOW(1), MIDDLE(2), HIGH(3);

    private int significance;

    Significance(int significance) {
        this.significance = significance;
    }

    public int getValue() {
        return significance;
    }


}
