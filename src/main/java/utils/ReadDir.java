package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 读取文件夹下（包括子目录）所有文件的文件名
 * http://www.jb51.net/article/68629.htm
 * Created by Phi on 2017/5/20.
 */
public class ReadDir {


    public List<String> getFileNameList(String dirName) throws Exception{
        String dir_name=dirName;  //文件夹地址
        Vector<String> ver=new Vector<String>();  //用做堆栈
        List<String> res = new ArrayList<>();
        ver.add(dir_name);
        while(ver.size()>0){
            File[] files = new File(ver.get(0).toString()).listFiles();  //获取该文件夹下所有的文件(夹)名
            ver.remove(0);
            int len=files.length;
            for(int i=0;i<len;i++){
                String tmp=files[i].getAbsolutePath();
                res.add(tmp);
                if(files[i].isDirectory())  //如果是目录，则加入队列。以便进行后续处理
                    ver.add(tmp);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        List<String> res = new ArrayList<>();
        ReadDir readDir = new ReadDir();
        String dir = "D:\\Developer\\WorkSpace\\IntelliJIDEA\\settlementcenter2\\com.xinfengtech.member.schema\\src\\main\\java\\com\\xinfengtech\\member\\schema\\member";
        try {
            res = readDir.getFileNameList(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
