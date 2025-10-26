package ru.mirea.kovalikaa.pocketdictionary.data.repository;



import model.WordDefinition;
import repository.WordRepository;
import ru.mirea.kovalikaa.pocketdictionary.data.network.NetworkApi;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.WordStorage;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.models.WordDataModel;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.room.WordDao;
import ru.mirea.kovalikaa.pocketdictionary.data.storage.room.WordEntity;


public class WordRepositoryImpl implements WordRepository {

    private final WordStorage sharedPrefStorage;
    private final WordDao wordDao;
    private final NetworkApi networkApi;

    public WordRepositoryImpl(WordStorage sharedPrefStorage, WordDao wordDao, NetworkApi networkApi) {
        this.sharedPrefStorage = sharedPrefStorage;
        this.wordDao = wordDao;
        this.networkApi = networkApi;
    }

    @Override
    public WordDefinition getDefinition(String word) {
        WordEntity roomEntity = wordDao.getDefinitionByWord(word);
        if (roomEntity != null) {
            System.out.println("Got '" + word + "' from ROOM cache.");
            WordDataModel dataModel = mapFromRoomEntity(roomEntity);
            return mapToDomainModel(dataModel);
        }

        System.out.println("'" + word + "' not in cache. Fetching from NETWORK.");
        WordDataModel dataFromNetwork = networkApi.getDefinitionFromNetwork(word);

        sharedPrefStorage.save(dataFromNetwork);
        wordDao.saveDefinition(mapToRoomEntity(dataFromNetwork));
        System.out.println("Saved '" + word + "' to Room and SharedPreferences.");

        return mapToDomainModel(dataFromNetwork);
    }

    @Override
    public boolean saveWordToFavorites(WordDefinition word) {
        WordDataModel dataModel = mapToDataModel(word);
        sharedPrefStorage.save(dataModel);
        wordDao.saveDefinition(mapToRoomEntity(dataModel));
        return true;
    }



    private WordDataModel mapToDataModel(WordDefinition domainModel) {
        return new WordDataModel(domainModel.getWord(), domainModel.getDefinition(), System.currentTimeMillis());
    }

    private WordDefinition mapToDomainModel(WordDataModel dataModel) {
        if (dataModel == null) return null;
        return new WordDefinition(dataModel.getWord(), dataModel.getDefinition());
    }

    private WordEntity mapToRoomEntity(WordDataModel dataModel) {
        WordEntity entity = new WordEntity();
        entity.word = dataModel.getWord();
        entity.definition = dataModel.getDefinition();
        entity.timestamp = dataModel.getSaveTimestamp();
        return entity;
    }

    private WordDataModel mapFromRoomEntity(WordEntity roomEntity) {
        if (roomEntity == null) return null;
        return new WordDataModel(roomEntity.word, roomEntity.definition, roomEntity.timestamp);
    }
}