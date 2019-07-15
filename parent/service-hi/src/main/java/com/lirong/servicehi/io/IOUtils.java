package com.lirong.servicehi.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.function.Consumer;


public class IOUtils {

    private static final Logger LOG = LoggerFactory.getLogger(IOUtils.class);

    /**
     *
     * 按行读取
     *
     * @param readFile 读文件
     * @param consumer 消费者
     */
    public static void readLine(final File readFile, Consumer<String> consumer) {
        if (readFile != null && !readFile.exists()) {
            LOG.warn("文件不存在，readFile:[{}]",readFile);
            return;
        }
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(readFile));
            String s ;
            while ((s = reader.readLine()) != null) {
                consumer.accept(s);
            }
        } catch (Exception e) {
            LOG.error("读取文件异常：{}",e);
        }finally {
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
