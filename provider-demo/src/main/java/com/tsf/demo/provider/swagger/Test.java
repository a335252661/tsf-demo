package com.tsf.demo.provider.swagger;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        String data = "";
        // 如果只有编码后的数据，这里的api.json中可以保存这些编码后的数据，然后把下面解码的那一行注释打开
        // 这里需要将文件拷贝到resources目录根路径下
        try(InputStream in = Test.class.getClassLoader().getResourceAsStream("api.json")) {
            byte[] d = new byte[in.available()];
            in.read(d, 0, in.available());
            data = new String(d);

        } catch (IOException e) {
            e.printStackTrace();
        }

        //try {
            //String deData = TsfGzipUtil.base64DecodeDecompress(data);
            Gson gson = new Gson();
            //HashMap mdata = gson.fromJson(deData, HashMap.class);
            HashMap mdata = gson.fromJson(data, HashMap.class);
            LinkedTreeMap paths = (LinkedTreeMap) mdata.get("paths");
            for (Object path : paths.entrySet()) {
                Object key = ((Map.Entry) path).getKey();
                Object obj = ((Map.Entry) path).getValue();
                Object post = ((LinkedTreeMap) obj).get("post");
                if (null == post) {
                    post = ((LinkedTreeMap) obj).get("get");
                }
                if (null == post) {
                    post = ((LinkedTreeMap) obj).get("delete");
                }
                if (null == post) {
                    post = ((LinkedTreeMap) obj).get("put");
                }

                try {
                    Object params = ((LinkedTreeMap) post).get("parameters");
                    if (null == params) {
                        continue;
                    }
                    if (((ArrayList) params).size() >= 5) {
                        System.out.println(key);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

//            File file = new File("d:\\api3.json");
//            if(!file.exists()) {
//                file.createNewFile();
//            }
//
//            try(FileOutputStream os = new FileOutputStream(file)) {
//                os.write(deData.getBytes());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        //System.out.print(data);
    }
}
