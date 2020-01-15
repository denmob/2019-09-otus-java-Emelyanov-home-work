package ru.otus.hw12.dbmanager;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class DBManagerImpl implements DBManager{

    private static final String MONGO_DB_HOST = "localhost";
    private static final String DB = "hw12";
    private static MongoDatabase mongoDatabase;

    public DBManagerImpl() {
        initMongoDB();
    }

    private void initMongoDB() {
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoClient mongoClient = new MongoClient(MONGO_DB_HOST, MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());
        mongoDatabase = mongoClient.getDatabase(DB);

    }

    @Override
    public  MongoDatabase getMongoDatabase() {
        return mongoDatabase;
    }

}
