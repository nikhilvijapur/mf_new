package com.mf.mf_new.repo.database;

import android.content.Context;

import com.mf.mf_new.repo.MfDetailItem;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = MfDetailItem.class, version = 1)
public abstract class MfDetailDataBase extends RoomDatabase {

    private static MfDetailDataBase sInstance;

    public abstract MfDetailDOA MfDoa();

    public static synchronized MfDetailDataBase getsInstance(Context context) {
        if(sInstance == null) {
            sInstance = Room.databaseBuilder(context.getApplicationContext(), MfDetailDataBase.class, "mf_details")
                    .fallbackToDestructiveMigration()
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(SupportSQLiteDatabase db) {
                            super.onCreate(db);
                        }

                        @Override
                        public void onOpen(SupportSQLiteDatabase db) {
                            super.onOpen(db);
                        }
                    }).build();
        }
        return sInstance;
    }

}
