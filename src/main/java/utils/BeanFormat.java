package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phi on 2017/4/19.
 */
public class BeanFormat {
    public static List<List<String>> toList(String resp){
        String[] list = resp.trim().split("\n");
        List<List<String>> res = new ArrayList<>();
        for (String s : list) {
            if(s.indexOf(";")!=-1){
                List<String> l = new ArrayList<>();
                String[] detail = s.trim().split(" ");
                String tmp = detail[2].trim();
                String name = tmp.split(";")[0];
                l.add(detail[1]);//type
                l.add(name);//name
                l.add(name.substring(0,1).toUpperCase() + name.substring(1));//首字母大写的name
                if(tmp.indexOf("//")!=-1){
                    l.add(tmp.split("//")[1].trim());//注释
                }
                res.add(l);
            }
        }
       return res;
    }

    public static List<List<String>> toList2(String resp){
        String[] list = resp.trim().split("/[*][*]");
        List<List<String>> res = new ArrayList<>();
        for (String s : list) {
            if (s.contains(";")){
                List<String> l = new ArrayList<>();
                String text = s.split("[*]")[1].trim();
                String type = s.split("private ")[1].split(" ")[0].trim();
                String name = s.split(type)[1].split(";")[0].trim();
                String uppercase = name.substring(0,1).toUpperCase() + name.substring(1);
                l.add(type);
                l.add(name);
                l.add(uppercase);
                l.add(text);
                res.add(l);
            }
        }
        return res;
    }

    public static String beanFormat(String format, String srcText){
        List<List<String>> src;
        if (srcText.contains("/**")){
            src = toList2(srcText);
        }else{
            src = toList(srcText);
        }
        String res = "";
        for (List<String> l : src){
            String string = format.replaceAll("#type#", l.get(0))
                    .replaceAll("#name#", l.get(1)).replaceAll("#uppercase#", l.get(2));
                if (l.size() >= 4){
                    string = string.replaceAll("#text#", l.get(3));
                }else{
                    string = string.replaceAll("#text#", "");
                }
            res = res + string + "\n";
        }
        return res;
    }

    public static void main(String[] args) {
        String src = " /**\n" +
                "     * 操作序列号\n" +
                "     */\n" +
                "    private String opSequenceNo;\n" +
                "    /**\n" +
                "     * 参数编号\n" +
                "     */\n" +
                "    private String parameterId;\n" +
                "    /**\n" +
                "     * 外部账户帐号\n" +
                "     */\n" +
                "    private String account;\n" +
                "    /**\n" +
                "     * 代理查询人持有人账号\n" +
                "     */\n" +
                "    private String holderAccount;\n" +
                "    /**\n" +
                "     * 持有人账号简称\n" +
                "     */\n" +
                "    private String holderShortName;\n" +
                "    /**\n" +
                "     * 操作类型\n" +
                "     */\n" +
                "    private Integer operType;\n" +
                "    /**\n" +
                "     * 操作执行状态\n" +
                "     */\n" +
                "    private Integer operExecuteStatus;";
        List<List<String>> res = toList2(src);
        System.out.println();
    }
}
