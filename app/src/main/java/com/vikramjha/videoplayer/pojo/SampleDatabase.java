
package com.vikramjha.videoplayer.pojo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;


/**
 * The Room database.
 */
@Database(entities = {VideoList.class}, version = 1)
public abstract class SampleDatabase extends RoomDatabase {

    public abstract VideoListDao videoListDao();

    private static SampleDatabase sInstance;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();
    public static final String DATABASE_NAME = "videoList";


    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }


}