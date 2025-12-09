package ru.mirea.kovalikaa.pocketdictionary.presentation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import model.WordDefinition;
import ru.mirea.kovalikaa.pocketdictionary.R;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder> {

    private List<WordDefinition> favoritesList = new ArrayList<>();

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewFavorite;
        public TextView textViewWord;
        public TextView textViewDefinition;
        public ImageButton buttonDelete;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewFavorite = itemView.findViewById(R.id.imageViewFavorite);
            textViewWord = itemView.findViewById(R.id.textViewFavoriteWord);
            textViewDefinition = itemView.findViewById(R.id.textViewFavoriteDefinition);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteFavorite);
        }
    }

    public void setFavorites(List<WordDefinition> favorites) {
        this.favoritesList = favorites;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {
        WordDefinition currentFavorite = favoritesList.get(position);

        holder.textViewWord.setText(currentFavorite.getWord());
        holder.textViewDefinition.setText(currentFavorite.getDefinition());
        if (currentFavorite.getImageUrl() != null && !currentFavorite.getImageUrl().isEmpty()) {
            Picasso.get()
                    .load(currentFavorite.getImageUrl())
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_error)
                    .into(holder.imageViewFavorite);
        } else {
            holder.imageViewFavorite.setImageResource(R.drawable.ic_error);
        }
        holder.buttonDelete.setOnClickListener(v -> {
            if (onDeleteClickListener != null) {
                onDeleteClickListener.onDeleteClick(currentFavorite);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoritesList == null ? 0 : favoritesList.size();
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(WordDefinition word);
    }

    private OnDeleteClickListener onDeleteClickListener;

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }
}