package com.example.serverjava.util;

public class SharedUtil {

    public static String addExtension(String fileName, String mode){
        if(mode.equals("brotli")){
            return fileName+".bz";
        }

        return fileName+".gz";
    }

   public static String removeExtension(String fileName){
        return fileName.substring(0,fileName.length() - 3);
   }
}
