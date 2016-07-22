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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

/**
 * Write Certification to a file in several formats.
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class Writer {

    /**
     * Write a file for every certification provided in <em>certs</em> as xml, prepared
     * for an specific Solr implementation.
     * @param path The path to the directory where the files will be written.
     * @param certs The list of Certifications to be written.
     * @throws IOException Not possible to write.
     */
    public void cert2SolrXML(String path, ArrayList<Certification> certs) throws IOException {

        for (Certification c : certs) {
            String xml = "<add><doc>";
            xml += "<field name=\"id\"";
            xml += ">";
            xml += c.getTitle();
            xml += "</field>";
            xml += "<field name=\"type\">Certification</field>";
            xml += terms2xml("term", c.getTerms());
            xml+=ecf2xml(c.getEcfs());
            xml+=profile2xml(c.getIct_profiles());
            xml += "</doc></add>";
            //competencesXMLsolr
            String fileTitle = c.getTitle() + ".xml";
            FileUtils.writeStringToFile(new File(path + fileTitle), xml, "utf8");
        }

    }
    
    /**
     * Transform a list of e-compentece framework mapping in a xml chunk for solr.
     * @param ecfs The list with the EcfCerts.
     * @return A string with the compenteces in an xml format.
     */
    public String ecf2xml(ArrayList <EcfCert> ecfs){
        String res="";
        for (EcfCert ec:ecfs){
            String support=ec.getSupport();
            res+="<field name=";
             if (StringUtils.equalsIgnoreCase(support, "")){
                res+= "\"competence_\">";
            }
             if (StringUtils.equalsIgnoreCase(support, "s")){
                 res+= "\"competence_s\">";
            }
            if (StringUtils.equalsIgnoreCase(support, "p")){
                res+= "\"competence_p\">";
            }
             if (StringUtils.equalsIgnoreCase(support, "g")){
                 res+= "\"competence_g\">";
            }
             res+=ec.getCompetence();
            
             res+="</field>"+System.lineSeparator();
            
        }
        
         
         return res;
    }
    
    
    private String profile2xml(ArrayList<String> ictProfiles){
        String res="";
        for (String p:ictProfiles){
            res+="<field name=\"ict_profile\">"+p+"</field>";
            
        }
        return res;
    }
    
    /**
     * For each term in a map build the correspondend xml chunk.
     * @param field_name the name of the field in solr.
     * @param terms a HashMap string -&gt; value so it has to be previously boosted.
     * @return A xml node represent each string in terms as a field for solr.
     */
    protected String terms2xml(String field_name,HashMap <String,Double> terms){
        String text="";
      Set pterms=terms.keySet();
       for (Object t0: pterms){
           String t=(String) t0;
           text+="<field name=\""+field_name+"\" "
                   + " boost=\""+terms.get(t)+"\""
                   + ">"
                   +t+"</field>";
       }
        return text;
    }
}
