package com.glluch.certisparser;

import com.glluch.findterms.termsAndRelated;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * A class for read a file with the descriptions of ICT certifications
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class DReader {

    public static String descriptionsFilename = "resources/CertisDesc.txt";
    public static String sep = "--->";
    
    /**
     * Internal function that reads a file and return a map "name of certification" -&gt;
     * "text". The format  of the file will be one certification per line and "title sep text". 
     * A line could be (for a ---> as separator)<br/>
     * Advanced Level Linux Certification (LPIC-2) ---> LPIC-2 is aimed at advanced Linux professionals. Candidates should be able to:  Administer a small to...
     * @return A map where the key are the title of the certification and the text, its value.
     * @throws IOException if file can't be read.
     */
    private static HashMap<String, String> readFile() throws IOException {
        File descriptionsFile = new File(descriptionsFilename);
        List<String> descs = FileUtils.readLines(descriptionsFile);
        HashMap<String, String> res = new HashMap<>();
        for (String desc:descs){
            String[] parts=StringUtils.splitByWholeSeparator(desc, sep);
            if (parts.length>1){
                res.put(parts[0].trim(), parts[1].trim());
            }
        }
        return res;
    }
    
   

    /**
     * Reads a file where there are the description of the certifications and return 
     * a list of such certifications
     * @return An arraylist of certifications 
     * @throws IOException if the file can't not be found.
     */
    public static ArrayList<Certification> read2cert() throws IOException{
        HashMap<String, String> certis = read();
        ArrayList<Certification> certs = new ArrayList<>();

        Set keys = certis.keySet();

       
        for (Object key0 : keys) {
            termsAndRelated ta = new termsAndRelated();
            String key = (String) key0;
            Certification c = new Certification();
            c.setTitle(key);
            HashMap<String, Double> ieee = ta.find(certis.get(key));
            c.setTerms(ieee);
            certs.add(c);

        }
        return certs;
    }
    
    /**
     * Reads and normalize the file containing the descriptions of the certifications
     * @return a hashmap where the key are the title of the certification and its value is 
     * the description
     * @throws IOException if the file can't be read.
     */
    public static HashMap <String, String> read() throws IOException {
       HashMap<String, String> res = readFile();
       
       res=ToTerms.normalize(res);
       return res;
    }
}
