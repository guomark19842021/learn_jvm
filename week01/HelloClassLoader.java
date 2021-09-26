package com.jvm;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloClassLoader extends ClassLoader {
    public static void main(String[] args) {
        try {
            Class<?> helloClass = new HelloClassLoader().findClass("Hello");
            Method helloMethod = helloClass.getMethod("hello");
            helloMethod.invoke(helloClass.newInstance());
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException |InvocationTargetException| InstantiationException e){
            e.printStackTrace();
        }

    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //获取Hello.xlass文件path
        Path path = Paths.get(System.getProperty("user.dir") + "/src/com/jvm/Hello.xlass");
        byte[] helloBase64 = new byte[0];
        try {
            helloBase64 = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < helloBase64.length; i++) {
            helloBase64[i] = (byte)(255 - helloBase64[i]);
        }
        return defineClass(name, helloBase64,0,helloBase64.length);
    }
}