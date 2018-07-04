package com.code.util;

import com.jfinal.kit.StrKit;

public class GenerateCodeUtils {
	private static final char SEPARATOR = '_';
	 
    public static String toUnderlineName(String s) {
        if (s == null) {
            return null;
        }
 
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
 
            boolean nextUpperCase = true;
 
            if (i < (s.length() - 1)) {
                nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
            }
 
            if ((i >= 0) && Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0) sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }
 
            sb.append(Character.toLowerCase(c));
        }
 
        return sb.toString();
    }
 
    public static String toCamelCase(String s) {
        if (s == null) {
            return null;
        }
 
        s = s.toLowerCase();
 
        StringBuilder sb = new StringBuilder(s.length());
        boolean upperCase = false;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
 
            if (c == SEPARATOR) {
                upperCase = true;
            } else if (upperCase) {
                sb.append(Character.toUpperCase(c));
                upperCase = false;
            } else {
                sb.append(c);
            }
        }
 
        return sb.toString();
    }
 
    public static String toCapitalizeCamelCase(String s) {
        if (s == null) {
            return null;
        }
        s = toCamelCase(s);
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
    /**
     * 首字母大写
     * @return
     */
    public static String toFirstUpperCase(String s) {
    	if (StrKit.isBlank(s)) {
            return null;
        }
    	return StrKit.firstCharToUpperCase(s);
    }
    public static String getToStr(Object obj) {
    	if(obj==null) {
    		return null;
    	}
    	return String.valueOf(obj);
    }
    public static void main(String[] args) {
        System.out.println(GenerateCodeUtils.toUnderlineName("ISOCertifiedStaff"));
        System.out.println(GenerateCodeUtils.toUnderlineName("CertifiedStaff"));
        System.out.println(GenerateCodeUtils.toUnderlineName("UserID"));
        System.out.println(GenerateCodeUtils.toCamelCase("iso_certified_staff"));
        System.out.println(GenerateCodeUtils.toCamelCase("certified_staff"));
        System.out.println(GenerateCodeUtils.toCamelCase("user_potential_batch"));
        System.out.println(Boolean.valueOf("YES"));
        System.out.println(Boolean.valueOf("NO"));
    }
}
