/*
 * Copyright (C) 2016 Guillem LLuch Moll guillem72@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.glluch.certisparser;

import com.glluch.findterms.termsAndRelated;
import com.glluch.utils.Out;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class JReader {
    
 
    
    public static String infoDirName = "resources/CertisJson/";

    /**
     * Read all files in a specific json format and build a list of Certification instances
     * representing information about certification courses.
     * @return An array of Certificition with the information in json files in the directory.
     * @throws IOException reading some file or diretory.
     * @throws ParseException the file json is not in the expected format.
     */
    public static  ArrayList<Certification> readDir() throws IOException, ParseException{
    ArrayList<Certification> certs=new ArrayList<>();
    SuffixFileFilter jsonsff=new SuffixFileFilter(".json");
   Iterator<File> cfiles= FileUtils.iterateFiles(new File(infoDirName), jsonsff,null);
    while (cfiles.hasNext()){
        //Out.p("Cfiles");
        File cf=(File) cfiles.next();
        String filename=cf.getCanonicalPath();
        certs.add(readFile(filename));
    }
    return certs;
    }
     
    /**
     * Create a Certification instance from a json file. Please, see in resources to know more
     * about the format of the file.
     * @param fileName The name of the json file with the information of the certification.
     * @return a Certification instance
     * @throws IOException reading the file
     * @throws ParseException json format error.
     */
    public static Certification readFile(String fileName) throws IOException, ParseException {
        String[] sFields = new String[4];
        sFields[0] = "description";
        sFields[1] = "target";
        sFields[2] = "requirements";
        sFields[3] = "subjects";
        Out.p("com.glluch.certisparser.JReader working on: "+fileName);
        
        Certification res = new Certification();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(fileName));
        JSONObject jsonObject = (JSONObject) obj;
        String title = (String) jsonObject.get("title");
        res.setTitle(title);
        //summary
        String presumm=obtainSummary(sFields,jsonObject);
        String summ=ToTerms.normalize(presumm);
        res.setSummary(summ);
        
        //Profiles
        String profiles=(String) jsonObject.get("ICT_profile");
        //Out.p(profiles);
        res.setIct_profiles(obtainProfiles(profiles));
        
        //terms
        termsAndRelated ta = new termsAndRelated();
        JSONArray comp = (JSONArray) jsonObject.get("e_cf");
        //Out.typeOf(comp);
          HashMap<String, Double> ieee = ta.find(summ);
          res.setTerms(ieee);
          
          //competences
        Iterator<JSONObject> iterator = comp.iterator();
        while (iterator.hasNext()) {
            JSONObject jo = iterator.next();
            EcfCert ec = obtainEcf(jo);
            res.adEcf(ec);
        }

        return res;
    }
    
    /**
     * Given a text, return a list of their parts. The char for separation is "-".
     * @param text A agregate by "-".
     * @return A list of their parts.
     */
    public static ArrayList<String> obtainProfiles(String text){
        ArrayList<String> profs=new ArrayList<>();
        String[] parts = StringUtils.split(text, "-");
        //Out.p(parts.toString());
        int i=0;
        while (i<parts.length){
            //Out.p(i+" = "+parts[i]);
            if (!StringUtils.isEmpty(parts[i].trim()))
            {
                profs.add(parts[i].trim().toLowerCase());
            }
                i++;
        }
        //Out.stringlist(profs);
        return profs;
    }

    /**
     * Given a Json object return a string showing all their parts. 
     * @param sFields The name of the idenfitiers to retrieve the fields to be concatenated.
     * @param jo an JsonObject with the entries idetified by sFields.
     * @return An string resulting of concatenated all the fields identified by sFields.
     */
    public static String obtainSummary(String[] sFields,JSONObject jo){
        String res="";
        for(String f:sFields){
            String value = (String) jo.get(f);
            res+=" "+value;
        }
        return res;
    }
    
    /**
     * Given a JSONObject return an ECfCert.
     * @param jo The JSONObject
     * @return a EcfCert
     */
    public static EcfCert obtainEcf(JSONObject jo) {
        EcfCert ec = new EcfCert();
        Set keys = jo.keySet();
        boolean found = false;
        for (Object key0 : keys) {
            String key = (String) key0;
            String value = (String) jo.get(key0);
            if (StringUtils.equals(key, "G/P/S")) {
                ec.setSupport(value);
                found = true;
            }
            if (StringUtils.equals(key, "e-CF competency")) {
                ec.setCompetence(value);
                found = true;
            }
            if (StringUtils.equals(key, "level")) {
                ec.setLevel(value);
                found = true;
            }
            if (!found) {
                ec.adProfile(key);
                found = false;
            }

        }
        return ec;
    }

    /**
     * A method for represent in a raw manner the certification
     * @param jo The JSONObject.
     * @return A simple representation made by the identifiers and their value.
     */
    public static String comp2string(JSONObject jo) {
        String res = "";
        Set keys = jo.keySet();
        for (Object key0 : keys) {
            String key = (String) key0;
            res += " " + key + " ---> " + jo.get(key0) + System.lineSeparator();
        }
        return res;
    }

}
