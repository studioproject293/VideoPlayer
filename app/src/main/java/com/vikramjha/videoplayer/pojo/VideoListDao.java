package com.vikramjha.videoplayer.pojo;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface VideoListDao {

    @Query("SELECT COUNT(*) FROM " + VideoList.TABLE_NAME)
    int count();

    @Query("SELECT COUNT(*) FROM " + VideoList.TABLE_NAME + " where " + VideoList.COLUMN_URL + " =:url")
    int countByUrl(String url);

    @Query("SELECT " + VideoList.COLUMN_POSITION + " FROM " + VideoList.TABLE_NAME + " where " + VideoList.COLUMN_URL + " =:url")
    long getPosition(String url);

    @Insert
    long insert(VideoList videoList);

    @Update
    int update(VideoList videoList);


}
