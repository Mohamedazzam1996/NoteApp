package com.example.mazzam.mytododatabase.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mazzam.mytododatabase.AddTodoActivity;
import com.example.mazzam.mytododatabase.Database.Model.ToDo;
import com.example.mazzam.mytododatabase.R;

import java.util.List;

public class TodoRecyclerViewAdapter extends RecyclerView.Adapter<TodoRecyclerViewAdapter.ViewHolder> {
 List<ToDo>toDos;
  OnitemClickListner onitemClickListner;
 View view;

    public void setOnitemClickListner(OnitemClickListner onitemClickListner) {
        this.onitemClickListner = onitemClickListner;
    }

    public TodoRecyclerViewAdapter(List<ToDo> toDos)
 {
     this.toDos=toDos;
 }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

       view= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.single_todo,viewGroup,false);

       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        final ToDo toDo =toDos.get(position);
        viewHolder.title.setText(toDo.getTitle());
        viewHolder.date.setText(toDo.getDate());
        viewHolder.content.setText(toDo.getContent());
        if (onitemClickListner!=null) {
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onitemClickListner.Onitemclick(position, toDo);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (toDos==null)
            return 0;
        return toDos.size();
    }
    public ToDo getTodoAt(int position )
    {
      return   toDos.get(position);
    }
    public void changeData(List<ToDo> toDos)
    {
      this.toDos=toDos;
      notifyDataSetChanged();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
    TextView title;
    TextView date;
    TextView  content;
    public ViewHolder(@NonNull View view) {
        super(view);
        title=view.findViewById(R.id.title_TV);
        date=view.findViewById(R.id.data_TV);
        content=view.findViewById(R.id.content_TV);

    }
}
    public interface OnitemClickListner{
     public void Onitemclick(int position, ToDo toDo);
    }
}
