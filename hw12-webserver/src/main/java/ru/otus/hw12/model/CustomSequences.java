package ru.otus.hw12.model;

import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

public class CustomSequences {
    private static final long serialVersionUID = 2105061907470199595L;

    private String nameSeq;

    public String getNameSeq() {
        return nameSeq;
    }

    public void setNameSeq(String nameSeq) {
        this.nameSeq = nameSeq;
    }

    public int getValueSeq() {
        return valueSeq;
    }

    public void setValueSeq(int valueSeq) {
        this.valueSeq = valueSeq;
    }

    private int valueSeq;

    public DBObject toDBObject() {
        BasicDBObjectBuilder builder = BasicDBObjectBuilder
                .start("nameSeq", nameSeq)
                .append("valueSeq", valueSeq);
        return builder.get();
    }

}
