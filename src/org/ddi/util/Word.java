package org.ddi.util;

import java.util.*;

/**
 *
 * @author erhan
 */
public class Word {
    public String name;
    public String word;
    public String type;
    public String suffix;
    public List<String> Definitions; //= new ArrayList<ArrayList<String>>();
    public List<String> Compounds;
    public  List<String> Idioms;
    
    public Word(){
        Definitions=new ArrayList<>();
        Compounds=new ArrayList<>();
        Idioms=new ArrayList<>();    
    }
    
    
    public String getDefinitions(){
          String defText=""; 
          for(String def : Definitions) defText+=def+"\n"; 
          return defText;
    }
    
    public String getCompounds(){
        String comText="";
        for(String com:Compounds) comText+=com+"\n";
        return comText;
    }
    
    public String getIdioms(){
        String idText="";
        for(String id:Idioms) idText+=id+"\n";
        return idText;
    }
    
}
