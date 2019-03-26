package com.example.mazzam.mytododatabase.Database.DAos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.mazzam.mytododatabase.Database.Model.ToDo;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface TodoDao {

    @Insert
    public void AddTodoItem(ToDo toDoItem);

    @Delete
    public void removeTodo(ToDo toDoItem);

    @Update
    public void UpdateTodo(ToDo toDoItem);

    @Query("select *from todo")
    public List<ToDo> GetAllTodo();

}
