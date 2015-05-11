/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.ddi.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
/**
 *
 * @author erhan
 */
public class WordAggregator {
    
    public String URLAddress;
    public String Word;
    
    
    public WordAggregator(String _url){
        this.URLAddress=_url;
    }
    
    public List<Word> queryWord(String _word){
        this.Word = _word;
        List<Word> results=new ArrayList<>();
        try{
            Connection.Response res = Jsoup.connect(URLAddress)
                                      .data("kelime", Word)
                                      .method(Connection.Method.POST)
                                      .timeout(5000)
                                      .execute();
            
            Document doc = res.parse();
            
            String query;
            query=doc.select("div.main_body").text();
            
            if(query.contains("sözü bulunamadı")){
                System.err.print(Word+" kelimesi bulunamadı...\n");
            }else{
                
                Elements definitions = doc.select("table#hor-minimalist-a");
                Elements extras = doc.select("table#hor-minimalist-b");
                
               
                
                for(Element def : definitions){
                    Word w=new Word();
                    Elements heads=def.select("td");
                    w.word=def.select("thead").select("tr").text();
                    for(Element head : heads){
                        String headText;
                        headText=head.text(); 
                        w.Definitions.add(headText);
                    }
                    results.add(w);
                }
                
                int orderIdioms=0, orderCompounds=0;
                for(Element extra : extras){
                    
                    if(extra.text().contains("Atasözü, deyim ve birleşik fiiller")){
                        Elements idioms=extra.select("td");
                        Word w=results.get(orderIdioms);
                        for (Element idiom : idioms) w.Idioms.add(idiom.text());
                        results.set(orderIdioms, w);
                        orderIdioms++;
                    }else if(extra.text().contains("Birleşik Sözler")){
                        Elements compounds=extra.select("td");
                        Word w=results.get(orderCompounds);
                        for(Element compound : compounds) w.Compounds.add(compound.text());
                        results.set(orderCompounds, w);
                        orderCompounds++;
                    }
                    
                    
                }
               
                
            }
            
        }catch (IOException ex) {
             System.err.printf(ex.toString()+"\n");
        }
        
        return results;
        
    }
     
      
         
  
}
