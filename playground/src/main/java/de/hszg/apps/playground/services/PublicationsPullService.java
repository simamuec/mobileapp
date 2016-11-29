package de.hszg.apps.playground.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created on 27.11.2016.
 */

public class PublicationsPullService extends IntentService {

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public PublicationsPullService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
