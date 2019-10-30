package com.mf.mf_new.repo.database;

import com.mf.mf_new.repo.MfDetailItem;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MfDetailDOA {

    @Insert
    void insert(MfDetailItem item);

    @Update
    void update(MfDetailItem item);

    @Delete
    void delete(MfDetailItem item);

    @Query("DELETE FROM MfDetailItem")
    void deleteAll();

    @Query("SELECT * FROM MfDetailItem")
    LiveData<List<MfDetailItem>> getAll();

}
