package cn.edu.cqu.ngtl.tools.utils;

import java.io.*;

/**
 * Created by tangjing on 16-11-29.
 */
public class FileUtils {

    //1.创建文件夹 递归方式
    public static void createFolder(String folderName) {

        File myFolderPath = new File(folderName);
        if(myFolderPath.exists())
            return ;
        try {
            if (myFolderPath.getParentFile().exists()) {
                myFolderPath.mkdir();
            }
            else {
                createFolder(myFolderPath.getParent());
                myFolderPath.mkdir();
            }
        }
        catch (Exception e) {
            System.out.println("新建目录操作出错");
            e.printStackTrace();
        }
    }

    //2.写入文件 byte[]
    public static void saveFile(String fileName, byte[] blob) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        try {
            fos.write(blob);
            System.out.println("保存文件成功");
        }
        finally {
            fos.close();
        }
    }

    //3.写入文件 FileInputStream
    public static void saveFile(String fileName, InputStream is) throws IOException {
        FileOutputStream fos = new FileOutputStream(fileName);
        try {
            byte[] blob = new byte[1024];
            while(is.read(blob) > 0)
                fos.write(blob);
            System.out.println("保存文件成功");
        }
        finally {
            fos.close();
            is.close();
        }
    }
}
