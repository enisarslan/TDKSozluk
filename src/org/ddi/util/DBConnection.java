package org.ddi.util;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author erhan
 */
public class DBConnection {
    
    MongoClient client;
    DB db;
    boolean isConnected;
    DBCollection collection;
    
    public DBConnection(String Server, int Port, String Database, String Collection ){
        client=new MongoClient(Server, Port);
        db= client.getDB(Database);
        isConnected=true;
        collection=db.getCollection(Collection);
    }
    
    
    public void addWord(Word w){
        
        BasicDBObject doc = new BasicDBObject("word", w.word);
        doc.append("name", w.name);
        BasicDBList definitions=new BasicDBList();
        for(String definition: w.Definitions) definitions.add(definition);
        BasicDBList idioms=new BasicDBList();
        for(String idiom: w.Idioms) idioms.add(idiom);
        BasicDBList compounds=new BasicDBList();
        for(String compound: w.Compounds) compounds.add(compound);
        doc.append("Definitions", definitions);
        doc.append("Idioms", idioms);
        doc.append("Compounds", compounds);
        collection.insert(doc);
    }
    
    public List<Word> getWord(String name){
        List<Word> words=new ArrayList<>();
        BasicDBObject query=new BasicDBObject();
        query.put("name", name);
        DBCursor cursor=collection.find(query);
        for(DBObject o:cursor){
            Word w=new Word();
            w.name=(String) o.get("name");
            w.word=(String) o.get("word");
            BasicDBList d,i,c;
            d=(BasicDBList)o.get("Definitions");
            for(Object def:d)w.Definitions.add((String)def);
            i=(BasicDBList)o.get("Idioms");
            for(Object id:i)w.Idioms.add((String)id);
            c=(BasicDBList)o.get("Compounds");
            for(Object com:c)w.Compounds.add((String)com); 
            words.add(w);
        }
         
        
        return words;
    }
    
}
