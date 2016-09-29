package com.rbh;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.Iterator;
import java.util.Vector;

public class Rbh {
    
    static HashMap<String,String> map = new HashMap<>();
    static HashMap<String,String> rbhduplicado = new HashMap<>();
    
       
    public static void main(String [] args) throws FileNotFoundException, IOException {
        
        
        //Lê e prepara o arquivo para ser aberto
        BufferedReader b = new BufferedReader(new FileReader("besthit-thais.csv"));
        String linha;
        
        BufferedWriter arq = new BufferedWriter(new FileWriter("rbh.csv"));
        
        linha = b.readLine();
        
        while(linha != null){
            String [] divisor = linha.split(",");
            map.put((divisor[0]), (divisor[1]));
            linha = b.readLine();
        }
        
        
        for(Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String value2 = map.get(value);
            if (value2 != null) {
                if (value2.equals(key)) {
                    rbhduplicado.put(key, value);
                }
            }
        }
        
        Vector vet= new Vector(rbhduplicado.size()/2);
        vet.add("");
        for(Entry<String, String> entry2 : rbhduplicado.entrySet()){
            String key = entry2.getKey();
            String value = entry2.getValue();
                arq.append(key + "," + value);
                arq.newLine();
                vet.add(value);
        }
        
        b.close();
        arq.close();
    }
}
