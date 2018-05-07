package cn.nizuge.util;

import java.util.Base64;

public class MyCryption {
    /**
     * 基于base64的加密解密
     * @param s
     * @return
     */
    public static String encrypt(String s){
        byte[] encrypt = s.getBytes();
        for(int i = 0;i<encrypt.length;i++){
            if(encrypt[i]%2==0){
                encrypt[i] = (byte) (encrypt[i]+1);
            }else {
                encrypt[i] = (byte) (encrypt[i]-1);
            }
        }
        return Base64.getEncoder().encodeToString(encrypt);
    }
    public static String decrypt(String s){
        String ds = new String(Base64.getDecoder().decode(s.getBytes()));
        byte[] decrypt = ds.getBytes();
        for(int i = 0;i<decrypt.length;i++){
            if(decrypt[i]%2==0){
                decrypt[i] = (byte) (decrypt[i]+1);
            }else {
                decrypt[i] = (byte) (decrypt[i]-1);
            }
        }
        return new String(decrypt);
    }
}
