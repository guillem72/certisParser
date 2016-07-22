package com.glluch.certisparser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * A class to represent a Certification
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class Certification {
    private String title;
    private String summary;
    private ArrayList <EcfCert> ecfs = new ArrayList<>();
    private ArrayList <String> ict_profiles;
    //private ArrayList<ECFMap> ecfs=new ArrayList<>();
    protected HashMap <String,Double> pterms=new HashMap <>();
    protected HashMap <String, Double> rterms=new HashMap <>();
    protected HashMap <String, Double> terms=new HashMap <>();

    public ArrayList<String> getIct_profiles() {
        return ict_profiles;
    }

    public void setIct_profiles(ArrayList<String> ict_profiles) {
        this.ict_profiles = ict_profiles;
    }

    /**
     * Return a simple representation of the Certification
     * @return an string form by the title and the summary.
     */
    public String brief2String(){
        return title+" "+summary;
    }
    
    public void adEcf(EcfCert ec){
        ecfs.add(ec);
    }
    
    public ArrayList<EcfCert> getEcfs() {
        return ecfs;
    }

    public void setEcfs(ArrayList<EcfCert> ecfs) {
        this.ecfs = ecfs;
    }

    
    
    
    public HashMap<String, Double> getTerms() {
        return terms;
    }

    public void setTerms(HashMap<String, Double> terms) {
        this.terms = terms;
    }

    /**
     * Given a  HashMap &lt; String, Integer &gt; , save it as a convinient pterms
     * @param pterms HashMap with a list of terms and their correpondend ocurrences.
     */
    public void setPtermsI2D(HashMap<String, Integer> pterms) {
        Set keys=pterms.keySet();
        for(Object key0:keys){
            String key=(String)key0;
            this.pterms.put(key, NumberUtils.createDouble(pterms.get(key).toString()));
           
        }
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public HashMap<String, Double> getPterms() {
        return pterms;
    }

    public void setPterms(HashMap<String, Double> pterms) {
        this.pterms = pterms;
    }

    public HashMap<String, Double> getRterms() {
        return rterms;
    }

    public void setRterms(HashMap<String, Double> rterms) {
        this.rterms = rterms;
    }
    
    
    
}
