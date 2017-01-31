package com.rbh;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPMessage;
import sun.misc.IOUtils;


public class Rbhsoap {
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
    };
    
    public final static String ncbiSOAP(String server){
        String ret = "";
        try {

            URL u = new URL(server);
            URLConnection uc = u.openConnection();
            HttpURLConnection connection = (HttpURLConnection) uc;

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            OutputStream out = connection.getOutputStream();


            InputStream in = connection.getInputStream();

            StringBuilder sb=new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String read;

            while((read=br.readLine()) != null) {
                sb.append(read);   
            }

            br.close();
            ret = sb.toString();
            in.close();
        } catch (IOException e) {
        System.err.println(e); 
        }
        
        return ret;
    }
    
    
   
    public static void main(String[] args) {
        try {
            BufferedReader b = new BufferedReader(new FileReader("rbh.csv"));
            BufferedWriter arquivo = new BufferedWriter(new FileWriter("rbhorganisms.csv"));
            String linha;
            
            linha = b.readLine();
        
            while(linha != null){
                String [] divisor = linha.split(",");
                String query = divisor[0];
                String subject = divisor[1];

                String GI_query = getPattern(query, "gi\\|(\\d+)\\|.*");
                String GI_subject = getPattern(subject, "gi\\|(\\d+)\\|.*");

                // Query
                String Server = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=protein&id=" + GI_query + "&rettype=docsum&retmode=xml";
                String ncbi = ncbiSOAP(Server);
                String organism_query = getPattern(ncbi, "<Item Name=\"Title\" Type=\"String\">(.*)<\\/Item>.*<Item Name=\"Extra\".*");

                // Subject
                Server = "https://eutils.ncbi.nlm.nih.gov/entrez/eutils/efetch.fcgi?db=protein&id=" + GI_subject + "&rettype=docsum&retmode=xml";
                ncbi = ncbiSOAP(Server);
                String organism_subject = getPattern(ncbi, "<Item Name=\"Title\" Type=\"String\">(.*)<\\/Item>.*<Item Name=\"Extra\".*");

                arquivo.append(query + "(" + organism_query + ")?" + subject + "(" + organism_subject + ")");
                arquivo.newLine();


                linha = b.readLine();
            }
            
            b.close();
            arquivo.close();
            
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
