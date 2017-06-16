package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phi on 2017/5/9.
 */
public class InterfaceFormat {
    /*生成通用的方法List*/
    public static List<String> toCommonMethodList(String method){
        String[] list = method.split("\n");
        List<String> resList = new ArrayList<>();
        for (String s : list) {
            if (s.contains(";")){
                s = s.trim();
                if (s.length()>0){
                    s = s.split(" ")[1].split("\\(")[0];
                    resList.add(s);
                }
            }
        }
        return resList;
    }



    public static void main(String[] args) {
        String a = "(";
        System.out.println();

    }
}
