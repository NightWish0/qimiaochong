package com.qimiaochong.common.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.HashMap;
import java.util.Map;

public class Md5Util {

    /**
     * Md5+盐 加密
     * 算列算法 md5(md5(password+loginName+salt))
     * @param loginName
     * @param password
     * @return
     */
    public static Map<String,String> encodeMd5Salt(String loginName, String password){
        Map<String,String> map=new HashMap();
        String salt=new SecureRandomNumberGenerator().nextBytes().toHex();
        map.put("salt",salt);
        SimpleHash hash=new SimpleHash("md5",password,loginName+salt,2);
        String encodePwd=hash.toBase64();
        map.put("password",encodePwd);
        return map;
    }

    public static String encodeMd5Salt(String loginName, String password,String salt){
        SimpleHash hash=new SimpleHash("md5",password,loginName+salt,2);
        String encodePwd=hash.toBase64();
        return encodePwd;
    }

    public static void main(String[] args){
        Map<String,String> map=encodeMd5Salt("admin","admin");
        System.out.println(map.get("salt"));
        System.out.println(map.get("password"));

//        Jedi jedis=new Jedis("192.168.123.180",6379);
//        System.out.println(jedis.ping());
//        jedis.set("dd","eee");
//        System.out.println(jedis.get("dd"));
    }
}
