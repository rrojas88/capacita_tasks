
package com.simple_apiX.tasks.api.v1.local.Utils;

import java.util.Arrays;


public class UtilsFile {
    
    public static String getExtension( String name ){
        String[] parts = name.split("\\.");
        int ArraySize = Arrays.asList(parts).size();
        return parts[ ArraySize - 1 ];
    }
    
    public static boolean validateExtension( String name, String[] extensions ){
        String ext = UtilsFile.getExtension( name );
        return Arrays.asList(extensions).contains( ext );
    }
    
}
