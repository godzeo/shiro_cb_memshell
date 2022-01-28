package com.govuln.shiroattack.asm;

import com.govuln.shiroattack.memshell.Client_memshell_tomcat;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;


import java.net.URI;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Resolver {
    public static void resolve(String name){
        try {
            URI uri = Objects.requireNonNull(Client_memshell_tomcat.class.getResource(name)).toURI();
            Path path = null;
            try{
                Map<String, String> env = new HashMap<>();
                env.put("create", "true");
                FileSystem zipfs = FileSystems.newFileSystem(uri, env);
                path = Paths.get(uri);
            }catch (Exception e){
                e.printStackTrace();
                //@see https://stackoverflow.com/questions/25032716/getting-filesystemnotfoundexception-from-zipfilesystemprovider-when-creating-a-p        //@see  http://docs.oracle.com/javase/7/docs/technotes/guides/io/fsp/zipfilesystemprovider.html        FileSystem zipfs = FileSystems.newFileSystem(uri, env);        path = Paths.get(uri);    }
            }
            byte[] bytes = Files.readAllBytes(path);
            ClassReader cr = new ClassReader(bytes);
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            int api = Opcodes.ASM9;
            ClassVisitor cv = new ShortClassVisitor(api, cw);
            int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;
            cr.accept(cv, parsingOptions);
            byte[] out = cw.toByteArray();
            Files.write(Paths.get(uri), out);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
