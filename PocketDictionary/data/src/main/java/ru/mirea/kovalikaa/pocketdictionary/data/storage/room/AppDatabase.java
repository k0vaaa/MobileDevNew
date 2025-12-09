package ru.mirea.kovalikaa.pocketdictionary.data.storage.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WordEntity.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WordDao wordDao();
}