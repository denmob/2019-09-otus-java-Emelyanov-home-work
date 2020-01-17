package ru.otus.hw12.dao;

import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.bson.conversions.Bson;
import ru.otus.hw12.dbmanager.DBManager;
import ru.otus.hw12.model.CustomSequence;

public class SequenceDaoImpl implements SequenceDao{

    private final MongoCollection<CustomSequence> sequencesMongoCollection;

    public SequenceDaoImpl(DBManager customSequencesDBManager) {
        this.sequencesMongoCollection = customSequencesDBManager.getMongoDatabase().getCollection("customSequence", CustomSequence.class);
        if (!checkFoundCustomSequences()) createCustomSequences();
    }

    private void createCustomSequences(){
        CustomSequence customSequences = new CustomSequence();
        customSequences.setNameSeq("userSeq");
        customSequences.setValueSeq(0);
        sequencesMongoCollection.insertOne(customSequences);
    }

    private boolean checkFoundCustomSequences() {
       return sequencesMongoCollection.countDocuments()>0;
    }

    @Override
    public long getNextSequence() {
        CustomSequence customSequences = sequencesMongoCollection.find().first();
        long newSequence =customSequences.getValueSeq()+1;
        Bson filter = new Document("nameSeq", "userSeq");
        Bson newValue = new Document("valueSeq", newSequence);
        Bson updateOperationDocument = new Document("$set", newValue);
        sequencesMongoCollection.updateOne(filter,updateOperationDocument);
        return newSequence;
    }
}
