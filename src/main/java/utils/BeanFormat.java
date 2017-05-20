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

    public static String beanFormat(String format, String srcText){
        List<List<String>> src = toList(srcText);
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
}
