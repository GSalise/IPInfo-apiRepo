package com.georgesalise.apiRepo.api.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckIP {
    public static boolean isIPValid(String ipAddress){
        Pattern pattern = Pattern.compile("^([0-9]{1,3}\\.){3}[0-9]{1,3}$");
        Matcher matcher = pattern.matcher(ipAddress);


        if(matcher.matches()){
            String[] parts = ipAddress.split("\\.");
            boolean valid = true;
            for(String part : parts){
                int n = Integer.parseInt(part);
                if(n < 0 || n > 255){
                    valid = false;
                    break;
                }
            }
            if(valid){
                return true;
            } else {
                // IP is invalid, out of range
                return false;
            }
        } else {
            // IP is invalid, wrong format
            return false;
        }
    }
}
