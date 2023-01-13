package com.example.serverjava.util;

public class SharedUtil {

    public static String addExtension(String fileName, String mode){
        if(mode.equals("brotli")){
            return fileName+".br";
        }

        return fileName+".gz";
    }

   public static String removeExtension(String fileName){
        return fileName.substring(0,fileName.length() - 3);
   }

    public static String sanitizeFileName(String fileName){

        return fileName.strip().replaceAll("[^A-Za-z0-9]","");
    }
}
