package com.dosto.models;

import java.util.Base64;

public class Coder {
    public static String decode(String text){
        return new String(Base64.getDecoder().decode(text));
    }
    public static String encode(String text){
        return new String(Base64.getEncoder().withoutPadding().encode(text.getBytes()));
    }
}
