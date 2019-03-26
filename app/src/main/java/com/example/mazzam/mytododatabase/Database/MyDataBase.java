package com.example.mazzam.mytododatabase.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.mazzam.mytododatabase.Database.DAos.TodoDao;
import com.example.mazzam.mytododatabase.Database.Model.ToDo;

@Database(entities = {ToDo.class},version = 1,exportSchema = false)
public abstract class MyDataBase extends RoomDatabase {
    private static MyDataBase myDataBase;
    public abstract TodoDao todoDao();
    public static MyDataBase  GetInstance(Context context)
    {
        if(myDataBase==null) {
            myDataBase = Room.databaseBuilder(context.getApplicationContext(), MyDataBase.class, "user-database")
                    .allowMainThreadQueries()
                    .build();
        }
        return myDataBase;
    }


}
