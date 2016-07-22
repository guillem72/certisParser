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

import java.util.ArrayList;
import org.apache.commons.lang.math.NumberUtils;

/**
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class EcfCert {
    
    
    private ArrayList<String> ictProfiles=new ArrayList<>();
    //maybe it could be HashMap<String, ArrayList<Integer>>
    
    private String support;
    private String competence;
    private String competenceCode;
    private int level;

    public void adProfile(String p){
        ictProfiles.add(p.toLowerCase().trim());
    }
    
    public ArrayList<String> getIctProfiles() {
        return ictProfiles;
    }

    public void setIctProfiles(ArrayList<String> ictProfiles) {
        this.ictProfiles = ictProfiles;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support.trim();
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence.trim();
        String[] parts=this.competence.split(" ");
        this.competenceCode=parts[0];
    }

    public String getCompetenceCode() {
        return competenceCode;
    }

   

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
    
    public void setLevel(String level) {
        this.level = NumberUtils.createInteger(level);
    }
    
}
