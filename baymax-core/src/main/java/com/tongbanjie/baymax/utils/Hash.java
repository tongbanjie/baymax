package com.tongbanjie.baymax.utils;

/**
 * Created by sidawei on 16/4/2.
 *
 * from com.google.common.hash.Murmur3_32HashFunction
 */
public class Hash {
    public static int hashUnencodedChars(CharSequence input) {
        int h1 = 0;

        int k1;
        for(k1 = 1; k1 < input.length(); k1 += 2) {
            int k11 = input.charAt(k1 - 1) | input.charAt(k1) << 16;
            k11 = mixK1(k11);
            h1 = mixH1(h1, k11);
        }

        if((input.length() & 1) == 1) {
            char k12 = input.charAt(input.length() - 1);
            k1 = mixK1(k12);
            h1 ^= k1;
        }

        return fmix(h1, 2 * input.length());
    }

    private static int mixK1(int k1) {
        k1 *= -862048943;
        k1 = Integer.rotateLeft(k1, 15);
        k1 *= 461845907;
        return k1;
    }

    private static int mixH1(int h1, int k1) {
        h1 ^= k1;
        h1 = Integer.rotateLeft(h1, 13);
        h1 = h1 * 5 + -430675100;
        return h1;
    }

    private static int fmix(int h1, int length) {
        h1 ^= length;
        h1 ^= h1 >>> 16;
        h1 *= -2048144789;
        h1 ^= h1 >>> 13;
        h1 *= -1028477387;
        h1 ^= h1 >>> 16;
        return h1;
    }
}
