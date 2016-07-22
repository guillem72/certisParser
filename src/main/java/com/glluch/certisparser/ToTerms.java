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

import com.glluch.findterms.AcronymsExpansion;
import java.util.HashMap;
import java.util.Set;

/**
 *
 * @author Guillem LLuch Moll guillem72@gmail.com
 */
public class ToTerms {
     /**
     * Funcion normalize constist in expand the acronyms found and lower the result.
     * The acronyms expansion is done in such a way that "IT" will be transformed in 
     * "information tecnology (it)"
     * @param raw The map unprocessed .
     * @return a hasmap with the second string normalized, that is with acronyms expanded 
     * and in lower case.
     */
    public static HashMap <String, String> normalize(HashMap <String, String> raw){
      HashMap<String, String> res = new HashMap<>();
      
      //acronyms
      AcronymsExpansion ae=new AcronymsExpansion();
         
      Set keys=raw.keySet();
      for (Object key0:keys){
       
           String key=(String) key0;
              System.out.println(key);
              String text=raw.get(key);
          text=ae.add(text).toLowerCase().trim();
          res.put(key, text);
          
      }
       return res;
    }
    
    /**
     * Method that transform an string expanded its acronyms 
     * @param text The text to be transformed
     * @return the transformed text
     */
    public static String normalize (String text){
    String res;
    AcronymsExpansion ae=new AcronymsExpansion();
     res=ae.add(text).toLowerCase().trim();
    return res;
    }
}
