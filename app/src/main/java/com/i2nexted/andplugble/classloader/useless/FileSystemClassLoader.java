package com.i2nexted.andplugble.classloader.useless;

import com.i2nexted.andplugble.utils.FileUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/12/4.
 * 标准java虚拟机中动态加载class文件的方法
 */

public class FileSystemClassLoader extends ClassLoader{
    private String className;

    public FileSystemClassLoader(String className) {
        super();
        this.className = className;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] classData = getClassData(name);
        if (classData == null){
            throw new ClassNotFoundException("没有找到对应类文件");
        }else {
            return defineClass(name,classData, 0,classData.length);
        }
    }


    /**
     *获取class文件的字节数据
     */
    private byte[] getClassData(String className){
        String classFilePath = classNameToPath(className);
        try {
            InputStream inputStream = new FileInputStream(new File(classFilePath));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bufferSize = 2048;
            byte[] buffer = new byte[bufferSize];
            int readed;
            while ((readed= inputStream.read(buffer))!= -1){
                baos.write(buffer, 0, readed);
            }
            return baos.toByteArray();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String classNameToPath(String className){
        String[] segs = className.split("\\.");
        return FileUtil.getDir(FileUtil.CLASS_DIR) + segs[segs.length -1] + ".class";
    }
}
