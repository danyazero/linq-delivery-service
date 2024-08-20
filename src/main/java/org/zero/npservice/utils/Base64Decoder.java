package org.zero.npservice.utils;

import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class Base64Decoder {
    public static String apply(String originalString) {
        return new String(Base64.getDecoder().decode(originalString.getBytes()));
    }
}
