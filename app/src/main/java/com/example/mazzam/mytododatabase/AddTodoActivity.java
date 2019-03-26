package com.example.mazzam.mytododatabase;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.mazzam.mytododatabase.Base.BaseActivity;
import com.example.mazzam.mytododatabase.Database.Model.ToDo;
import com.example.mazzam.mytododatabase.Database.MyDataBase;

public class AddTodoActivity extends BaseActivity {
    EditText title_ed;
    EditText date_ed;
    EditText content_ed;
    Button   add_btn;
    String stitle,sdate,scontent;
    public static ToDo toDoItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        title_ed = findViewById(R.id.title_ET);
        date_ed = findViewById(R.id.date_ET);
        content_ed = findViewById(R.id.content_ET);
        add_btn = findViewById(R.id.add_btn);
        if (toDoItem!=null)
        {
           title_ed.setText(toDoItem.getTitle());
           date_ed.setText(toDoItem.getDate());
           content_ed.setText(toDoItem.getContent());
           add_btn.setText("update");
           add_btn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   stitle=title_ed.getText().toString();
                   sdate=date_ed.getText().toString();
                   scontent=content_ed.getText().toString();
                   toDoItem.setTitle(stitle);
                   toDoItem.setDate(sdate);
                   toDoItem.setContent(scontent);
                   MyDataBase.GetInstance(activity)
                           .todoDao()
                           .UpdateTodo(toDoItem);
                            toDoItem=null;
                            showConfirmationMessage(R.string.message, R.string.the_item_has_been_updated, R.string.ok, new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
               }
           });
        }
        else
        {
            add_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stitle=title_ed.getText().toString();
                    sdate=date_ed.getText().toString();
                    scontent=content_ed.getText().toString();
                    ToDo toDo=new ToDo(stitle,sdate,scontent);
                    MyDataBase.GetInstance(activity)
                            .todoDao()
                            .AddTodoItem(toDo);
                    toDoItem=null;
                    showConfirmationMessage(R.string.message, R.string.the_item_has_been_added, R.string.ok, new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                            finish();
                        }
                    });
                }
            });

        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        toDoItem=null;
    }
}
