package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * java 读取文件到String
 * http://outofmemory.cn/code-snippet/1807/java-duqu-file-content-come-string
 * Created by Phi on 2017/5/20.
 */
public class ReadFile {
    /**
     * 将文本文件中的内容读入到buffer中
     * @param buffer buffer
     * @param filePath 文件路径
     * @throws IOException 异常
     * @author cn.outofmemory
     * @date 2013-1-7
     */
    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
//        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        InputStreamReader isr = new InputStreamReader(new FileInputStream(filePath), "UTF-8");
        BufferedReader reader = new BufferedReader(isr);
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        isr.close();
    }

    /**
     * 读取文本文件内容
     * @param filePath 文件所在路径
     * @return 文本内容
     * @throws IOException 异常
     * @author cn.outofmemory
     * @date 2013-1-7
     */
    public static String readFile(String filePath) throws IOException {
        StringBuffer sb = new StringBuffer();
        readToBuffer(sb, filePath);
        return sb.toString();
    }

    public static void main(String[] args) throws IOException {
        String path = "D:\\Developer\\WorkSpace\\IntelliJIDEA\\settlementcenter2\\com.xinfengtech.member.schema\\src\\main\\java\\com\\xinfengtech\\member\\schema\\member\\req\\AuditMemberActivateRequest.java";
        String file = readFile(path);
        System.out.println();
    }
}
