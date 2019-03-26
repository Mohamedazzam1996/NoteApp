package com.example.mazzam.mytododatabase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mazzam.mytododatabase.Adapter.TodoRecyclerViewAdapter;
import com.example.mazzam.mytododatabase.Base.BaseActivity;
import com.example.mazzam.mytododatabase.Database.Model.ToDo;
import com.example.mazzam.mytododatabase.Database.MyDataBase;

import java.util.List;

public class MainActivity extends BaseActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    TodoRecyclerViewAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(activity);
        adapter = new TodoRecyclerViewAdapter(null);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout=findViewById(R.id.swipe_refresh_layout);
        adapter.setOnitemClickListner(new TodoRecyclerViewAdapter.OnitemClickListner() {
            @Override
            public void Onitemclick(int position, ToDo toDo) {
                AddTodoActivity.toDoItem=toDo;
                startActivity(new Intent(activity,AddTodoActivity.class));
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                List<ToDo> toDos=MyDataBase.GetInstance(activity)
                                           .todoDao()
                        .GetAllTodo();
                adapter.changeData(toDos);
                swipeRefreshLayout.setRefreshing(false);

            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivity(intent);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                final ToDo toDoItem=adapter.getTodoAt(viewHolder.getAdapterPosition());
                     showConfirmationMessage(R.string.message, R.string.Do_you_want_to_delete, R.string.ok, new MaterialDialog.SingleButtonCallback() {
                         @Override
                         public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                               MyDataBase.GetInstance(activity)
                                       .todoDao()
                                       .removeTodo(toDoItem);
                         }
                     });
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    protected void onStart() {
        super.onStart();
      List<ToDo> toDos  =MyDataBase.GetInstance(activity)
                .todoDao()
                .GetAllTodo();
      adapter.changeData(toDos);
    }
}
