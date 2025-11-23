package ru.mirea.kovalikaa.pocketdictionary.data.storage.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import java.util.List;
@Dao
public interface WordDao {
    @Query("SELECT * FROM definitions WHERE word = :word LIMIT 1")
    WordEntity getDefinitionByWord(String word);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveDefinition(WordEntity entity);

    @Query("SELECT * FROM definitions ORDER BY word ASC")
    List<WordEntity> getAllFavorites();

    @Delete
    void deleteFavorite(WordEntity word);
}