package ru.mirea.kovalikaa.pocketdictionary.data.storage.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "definitions")
public class WordEntity {
    @PrimaryKey
    @NonNull
    public String word;
    public String definition;
    public String imageUrl;
    public long timestamp;
    public boolean isFavorite;
}