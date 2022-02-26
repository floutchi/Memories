package org.helmo.memories;

import android.app.Application;


import org.helmo.memories.repository.MemoryDataBase;

public class MemoriesApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MemoryDataBase.initDatabase(getBaseContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        MemoryDataBase.disconnectDatabase();
    }
}
