package ru.mirea.kovalikaa.fragmentapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoViewHolder> {

    private final LayoutInflater inflater;
    private final List<Todo> todos;
    private final ApiService apiService;

    public TodoAdapter(Context context, List<Todo> todos, ApiService apiService) {
        this.inflater = LayoutInflater.from(context);
        this.todos = todos;
        this.apiService = apiService;
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        final TextView textViewTitle;
        final CheckBox checkBoxCompleted;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            checkBoxCompleted = itemView.findViewById(R.id.checkBoxCompleted);
        }
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_todo, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Todo todo = todos.get(position);
        holder.textViewTitle.setText(todo.getTitle());
        holder.checkBoxCompleted.setChecked(todo.getCompleted());

        holder.checkBoxCompleted.setOnClickListener(v -> {
            boolean isChecked = holder.checkBoxCompleted.isChecked();
            todo.setCompleted(isChecked);

            apiService.updateTodo(todo.getId(), todo).enqueue(new Callback<Todo>() {
                @Override
                public void onResponse(@NonNull Call<Todo> call, @NonNull Response<Todo> response) {
                    if (response.isSuccessful()) {
                        Log.d("TodoAdapter", "Todo " + todo.getId() + " updated successfully!");
                    } else {
                        Log.e("TodoAdapter", "Update failed with code: " + response.code());
                    }
                }

                @Override
                public void onFailure(@NonNull Call<Todo> call, @NonNull Throwable t) {
                    Log.e("TodoAdapter", "Update failed: " + t.getMessage());
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return todos.size();
    }
}