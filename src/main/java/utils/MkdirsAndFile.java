package utils;

import exception.UtilsException;

import java.io.*;

/**
 * Created by Phi on 2017/4/17.
 */
public class MkdirsAndFile {

    private static String filenameTemp;


    /*文件生成通用工具类*/
    public static void fileMaker(String file, String fileName,String suffix,String path){
        try {
            creatTxtFile(fileName,suffix,path);
            writeTxtFile(file);
        } catch (IOException e) {
            throw new UtilsException("文件生成失败:"+e.toString());
        }
    }
    public static void mkdirsForSql(String basePath){
        File file1 = new File(basePath + CommonConstants.THE_BASE_PATH  + CommonConstants.DTO_PATH);
        file1.mkdirs();
        File file2 = new File(basePath + CommonConstants.THE_BASE_PATH  + CommonConstants.SQLMAP_PATH);
        file2.mkdirs();
        File file3 = new File(basePath + CommonConstants.THE_BASE_PATH  + CommonConstants.OTHER_PATH);
        file3.mkdirs();
    }
    public static void mkdirsForMethod(String basePath,String coreName){
//        coreName = coreName.substring(0,1).toLowerCase() + coreName.substring(1);
        coreName = coreName.toLowerCase();//包名全换成小写
        File file1 = new File(basePath + CommonConstants.THE_BASE_PATH + CommonConstants.MANAGER_PATH + "/" + coreName + "/impl");
        file1.mkdirs();
        File file2 = new File(basePath + CommonConstants.THE_BASE_PATH + CommonConstants.SERVICE_PATH + "/" + coreName);
        file2.mkdirs();
        File file3 = new File(basePath + CommonConstants.THE_BASE_PATH + CommonConstants.INTERFACES_PATH + "/" +coreName);
        file3.mkdirs();
        File file4 = new File(basePath + CommonConstants.THE_BASE_PATH + CommonConstants.SCHEMA_PATH + "/" +coreName + "/req");
        file4.mkdirs();
        File file5 = new File(basePath + CommonConstants.THE_BASE_PATH + CommonConstants.SCHEMA_PATH + "/" +coreName + "/resp");
        file5.mkdirs();
    }

    /**
     * 创建文件
     *
     * @throws IOException
     */
    public static boolean creatTxtFile(String name,String suffix,String path) throws IOException {
        boolean flag = false;
        filenameTemp = path + "/" + name + suffix;
        File filename = new File(filenameTemp);
        if (!filename.exists()) {
            filename.createNewFile();
            flag = true;
        }
        return flag;
    }

    /**
     * 写文件
     *
     * @param newStr
     *            新内容
     * @throws IOException
     */
    public static boolean writeTxtFile(String newStr) throws IOException {
        // 先读取原有文件内容，然后进行写入操作
        boolean flag = false;
        String filein = newStr + "\r\n";
        String temp = "";

        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            // 文件路径
            File file = new File(filenameTemp);
            // 将文件读入输入流
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            StringBuffer buf = new StringBuffer();

            // 保存该文件原有的内容
            for (int j = 1; (temp = br.readLine()) != null; j++) {
                buf = buf.append(temp);
                // System.getProperty("line.separator")
                // 行与行之间的分隔符 相当于“\n”
                buf = buf.append(System.getProperty("line.separator"));
            }
            buf.append(filein);

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buf.toString().toCharArray());
            pw.flush();
            flag = true;
        } catch (IOException e1) {
            // TODO 自动生成 catch 块
            throw e1;
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
        return flag;
    }

    //读文件
    public static String readTxtFile(String filePath) {
        String res = "";
        try {
            String encoding = "UTF-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    res = res + lineTxt + "\n";
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        if (res.equals("")){
            return "文件内容为空";
        }
        return res;
    }

    public static void main(String[] args) {
        mkdirsForSql(CommonConstants.THE_BASE_PATH);
//        mkdirsForMethod(CommonConstants.THE_BASE_PATH);
    }
}
