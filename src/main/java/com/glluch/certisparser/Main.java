package com.glluch.certisparser;

import com.glluch.utils.Out;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.parser.ParseException;


/**
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class Main {

    public static double term_boost = 2.0;
    public static double related_boost = 1.0;

    /**
     *
     * @param args
     * @throws IOException
     * @throws ParseException
     */
    public static void main(String[] args) throws IOException, ParseException {
        //doit();
        //jreader1();
        jreader();

    }
    
    /**
     *
     * @throws IOException
     * @throws ParseException
     */
    public static void jreader() throws IOException, ParseException{
        ArrayList<Certification> certs=JReader.readDir();
        Writer w=new Writer();
        w.cert2SolrXML("resources/certisSolrXML/", certs);
        /*
        for (Certification cert:certs){
            System.out.println(cert.brief2String());
        }
        */
    }

    /**
     *
     * @throws IOException
     * @throws ParseException
     */
    public static void jreader1() throws IOException, ParseException{
       
       Certification c=JReader.readFile("resources/CertisJson/Security+.json");
        Out.p(c);
       System.out.println(c.brief2String());
    }
    
    //Versió incomplerta, no agafa competencies perquè llegueix de text, no del json
        public static  ArrayList<Certification> doit() throws IOException {
        //DescriptionReader dr=new DescriptionReader
        //System.out.println(DReader.read().toString());
        //Out.stringstringmap(DReader.read());
        
        ArrayList<Certification> certs = DReader.read2cert();
        //certisSolrXML
        Writer w=new Writer();
        w.cert2SolrXML("resources/certisSolrXML/", certs);
        return certs;
    }
    

}
