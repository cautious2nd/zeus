package com.scaffold.file.io;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class FileIOUtil {
    private static final String CLASSPATH_ALL_URL_PREFIX = "classpath*:";
    private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";
    private static final int DEFAULT_BUFFER_SIZE = 8192;



    public static void getJarSteam(String jarPath, String filePath) {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(jarPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Enumeration<JarEntry> ee = jarFile.entries();

        List<JarEntry> jarEntryList = new ArrayList<JarEntry>();
        while (ee.hasMoreElements()) {
            JarEntry entry = ee.nextElement();
            // 过滤我们出满足我们需求的东西，这里的fileName是指向一个具体的文件的对象的完整包路径，比如com/mypackage/test.txt
            if (entry.getName().startsWith(filePath)) {
                jarEntryList.add(entry);
            }
        }
        try {
            InputStream in = jarFile.getInputStream(jarEntryList.get(0));
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String s = "";

            while ((s = br.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //jar:file:/E:/ideaProject/poseidon-print/target/poseidon-print-1.0-SNAPSHOT.jar!/key/apiclient_key.pem
    public static String readFileFromJarPath(String jarPath,String fileName) {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(jarPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Enumeration<JarEntry> ee = jarFile.entries();

        while (ee.hasMoreElements()) {
            JarEntry entry = ee.nextElement();
            // 过滤我们出满足我们需求的东西，这里的fileName是指向一个具体的文件的对象的完整包路径，比如com/mypackage/test.txt
            if (entry.getName().startsWith(fileName)) {
                try(InputStream in = jarFile.getInputStream(entry)) {
                    return new String(toByteArray(in), StandardCharsets.UTF_8);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        }

        return null;


    }

    public static String readFileFromSteam(InputStream inputStream){
        try(InputStream in = inputStream) {
            return new String(toByteArray(in), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }



    public static byte[] toByteArray(InputStream inputStream) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        int nRead;
        byte[] data = new byte[DEFAULT_BUFFER_SIZE];
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }

        return buffer.toByteArray();
    }

}
