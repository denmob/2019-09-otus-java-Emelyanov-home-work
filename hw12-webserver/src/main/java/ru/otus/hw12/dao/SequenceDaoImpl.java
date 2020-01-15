package ru.otus.hw12.dao;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import ru.otus.hw12.dbmanager.DBManager;
import ru.otus.hw12.model.CustomSequences;

public class SequenceDaoImpl implements SequenceDao{

    private final MongoCollection<CustomSequences> sequencesMongoCollection;

    public SequenceDaoImpl(DBManager customSequencesDBManager) {
        this.sequencesMongoCollection = customSequencesDBManager.getMongoDatabase().getCollection("customSequences",CustomSequences.class);
        if (!checkFoundCustomSequences()) createCustomSequences();
    }

    private void createCustomSequences(){
        CustomSequences customSequences = new CustomSequences();
        customSequences.setNameSeq("userSeq");
        customSequences.setValueSeq(0);
        sequencesMongoCollection.insertOne(customSequences);
    }

    private boolean checkFoundCustomSequences() {
       return sequencesMongoCollection.countDocuments()>0;
    }

    @Override
    public long getNextSequence() {
        CustomSequences customSequences = sequencesMongoCollection.find().first();
        long newSequence =customSequences.getValueSeq()+1;
        Bson filter = new Document("nameSeq", "userSeq");
        Bson newValue = new Document("valueSeq", newSequence);
        Bson updateOperationDocument = new Document("$set", newValue);
        sequencesMongoCollection.updateOne(filter,updateOperationDocument);
        return newSequence;
    }
}
