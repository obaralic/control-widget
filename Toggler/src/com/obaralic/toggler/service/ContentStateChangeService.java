
/*
 * Copyright 2013 oBaralic, Inc. (owner Zivorad Baralic)
 *            ____                   ___
 *     ____  / __ )____ __________ _/ (_)____
 *    / __ \/ __  / __ `/ ___/ __ `/ / / ___/
 *   / /_/ / /_/ / /_/ / /  / /_/ / / / /__
 *   \____/_____/\__,_/_/   \__,_/_/_/\___/
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.obaralic.toggler.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.obaralic.toggler.service.commands.content.ContentStateChangeServiceInterface;
import com.obaralic.toggler.service.factories.ContentStateChangeServiceCommandFactory;
import com.obaralic.toggler.service.threads.ContentStateChangeServiceWorkerThread;
import com.obaralic.toggler.utilities.debug.LogUtil;
import com.obaralic.toggler.utilities.validation.IntentValidator;

/**
 * Service used for handling content state changing operations.
 */
public class ContentStateChangeService extends Service {

    private static final String TAG = LogUtil.getTag(ContentStateChangeService.class);

    private ContentStateChangeServiceWorkerThread mWorkerThread;

    @Override
    public void onCreate() {
        super.onCreate();
        mWorkerThread = new ContentStateChangeServiceWorkerThread(this);
        mWorkerThread.start();
    }

    @Override
    public void onDestroy() {
        mWorkerThread.interrupt();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d(TAG, "Called onStartCommand");

        IntentValidator validator = new IntentValidator();

        // If received intent is invalid intent or intent with unknown action
        if (!validator.isOkay(intent)) {
            return stopUponBadIntent();
        }

        String action = intent.getAction();
        LogUtil.d(TAG, "Action: " + action);

        // Extract a Service command from the Intent
        ContentStateChangeServiceInterface command = ContentStateChangeServiceCommandFactory.get(intent);
        if (command == null) {
            return stopUponBadIntent();
        }

        // Execute the command on the worker thread ***
        mWorkerThread.addJob(command);

        // We want the Service to stick around
        return Service.START_STICKY;
    }

    /**
     * Stops the service when a null intent is sent. That happens after the system decides to silently kill
     * our process.
     * 
     * @return The {@link Service#START_STICKY} integer value
     */
    private int stopUponBadIntent() {
        Log.w(TAG, "Started for a null or unknown Intent - stopping.");
        stopSelf();
        return Service.START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
