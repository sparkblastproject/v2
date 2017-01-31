package com.rbh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RbhOrganismo {
    public final static String getPattern(String line, String pattern) {
      
        String ret = "";
        // Create a Pattern object
        Pattern r = Pattern.compile(pattern);

        // Now create matcher object.
        Matcher m = r.matcher(line);
        if (m.find()) {
          ret = m.group(1);
        }
        return ret;
    }
  
  
   
    private static String [] le_organismos(String arquivo) throws FileNotFoundException, IOException {
        String [] ret = new String[11];
       
        // TO DO: Ler o aquivo e alimentar um vetor
        BufferedReader arq = new BufferedReader(new FileReader(arquivo));
        String line = arq.readLine();
        int i = 0;
        while(line != null){
            ret[i] = line.split(",")[0];
            i++;
            line = arq.readLine();
        }
        arq.close();
       
        return ret;
    }
   
  
    public static void main(String [] args){
       
       
        if (args.length < 2){
            System.err.println("Use: java RbhOrganismo <arquivo de entrada> <arquivo de organismos>");
            System.exit(-1);
        }

        String arquivoEntrada = args[0];
        String arquivoOrganismos = args[1];
       
                   
        try{
           
            String [] organismos = le_organismos(arquivoOrganismos);
           
           
            for(int i=0; i <= organismos.length-1; i++) {
                for (int j=0; j <= organismos.length-1; j++) {
                    int total = 0;
                    BufferedReader arq = new BufferedReader(new FileReader(arquivoEntrada));
                    String linha = arq.readLine();

                    while(linha != null){
                        String [] divisor = linha.split("\\?");
                        String query = divisor[0];
                        String subject = divisor[1];

                        String [] nomeorgquerytmp = getPattern(query, "\\[(.*)\\]").split(" ");
                        String nomeorgquery;
                        if (nomeorgquerytmp.length < 2){
                            nomeorgquery = nomeorgquerytmp[0];
                        } else {
                            nomeorgquery = nomeorgquerytmp[0] + " " + nomeorgquerytmp[1];
                        }


                        String [] nomeorgsubjecttmp = getPattern(subject, "\\[(.*)\\]").split(" ");
                        String nomeorgsubject;
                        if (nomeorgsubjecttmp.length < 2) {
                            nomeorgsubject = nomeorgsubjecttmp[0];
                        } else {
                            nomeorgsubject = nomeorgsubjecttmp[0] + " " + nomeorgsubjecttmp[1];
                        }


                        if (nomeorgquery.equals(organismos[i]) && nomeorgsubject.equals(organismos[j])) {
                            total += 1;
                        }
                       
                        linha = arq.readLine();
                    }
                    System.out.println(organismos[i] + "," + organismos[j] + ","+total);
                    arq.close();       
                }
            }
        }catch(IOException e){
            System.err.println(e.getMessage());
        }
    }
}
