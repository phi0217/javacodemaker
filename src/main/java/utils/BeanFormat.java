package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phi on 2017/4/19.
 */
public class BeanFormat {
    public static List<List<String>> toList(String resp) {
        String[] list = resp.trim().split("\n");
        List<List<String>> res = new ArrayList<>();
        for (String s : list) {
            if (s.indexOf(";") != -1) {
                List<String> l = new ArrayList<>();
                String[] detail = s.trim().split(" ");
                String tmp = detail[2].trim();
                String name = tmp.split(";")[0];
                l.add(detail[1]);//type
                l.add(name);//name
                l.add(name.substring(0, 1).toUpperCase() + name.substring(1));//首字母大写的name
                if (tmp.indexOf("//") != -1) {
                    l.add(tmp.split("//")[1].trim());//注释
                }
                res.add(l);
            }
        }
        return res;
    }

    public static List<List<String>> toList2(String resp) {
        String[] list = resp.trim().split("/[*][*]");
        List<List<String>> res = new ArrayList<>();
        for (String s : list) {
            if (s.contains(";")) {
                if (countStr(s, ";") > 1) {
                    if (s.contains("/**")) {
                        String first = s.split(";")[0];
                        String other = s.substring(s.indexOf(";"));
                        List<String> l = new ArrayList<>();
                        String text = first.split("[*]")[1].trim();
                        String type = first.split("private ")[1].split(" ")[0].trim();
                        String name = first.split("private ")[1].split(" ")[1].split(";")[0].trim();
                        String uppercase = name.substring(0, 1).toUpperCase() + name.substring(1);
                        l.add(type);
                        l.add(name);
                        l.add(uppercase);
                        l.add(text);
                        res.add(l);
                        List<List<String>> otherList = toList(other);
                        res.addAll(otherList);
                    } else {
                        List<List<String>> otherList = toList(s);
                        res.addAll(otherList);
                    }
                } else {
                    List<String> l = new ArrayList<>();
                    String text = s.split("[*]")[1].trim();
                    String type = s.split("private ")[1].split(" ")[0].trim();
                    String name = s.split("private ")[1].split(" ")[1].split(";")[0].trim();
                    String uppercase = name.substring(0, 1).toUpperCase() + name.substring(1);
                    l.add(type);
                    l.add(name);
                    l.add(uppercase);
                    l.add(text);
                    res.add(l);
                }
            }
        }
        return res;
    }

    public static String beanFormat(String format, String srcText) {
        List<List<String>> src;
        if (srcText.contains("/**")) {
            src = toList2(srcText);
        } else {
            src = toList(srcText);
        }
        String res = "";
        if (format.contains("#if#")) {
            String condition = format.trim().replace("#if#", "").split("#do#")[0];
            String format1 = format.split("#do#")[1].split("#else#")[0];
            String format2 = format.split("#else#")[1];
            for (List<String> l : src) {
                String string;
                if (l.get(0).equals(condition)) {
                    string = formatMaker(format1, l);
                }else {
                    string = formatMaker(format2, l);
                }
                res = res + string + "\n";
            }
        } else {
            for (List<String> l : src) {
//                String string = format.replaceAll("#type#", l.get(0))
//                        .replaceAll("#name#", l.get(1)).replaceAll("#uppercase#", l.get(2)).replaceAll("#enter#", "\n");
//                if (l.size() >= 4) {
//                    string = string.replaceAll("#text#", l.get(3));
//                } else {
//                    string = string.replaceAll("#text#", "");
//                }
                String string = formatMaker(format, l);
                res = res + string + "\n";
            }
        }

        return res;
    }

    public static String formatMaker(String format, List<String> l) {
        String string = format.replaceAll("#type#", l.get(0))
                .replaceAll("#name#", l.get(1)).replaceAll("#uppercase#", l.get(2)).replaceAll("#enter#", "\n");
        if (l.size() >= 4) {
            string = string.replaceAll("#text#", l.get(3));
        } else {
            string = string.replaceAll("#text#", "");
        }
        return string;
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

    public static int countStr(String str1, String str2) {
        int counter = 0;
        if (str1.indexOf(str2) == -1) {
            return 0;
        }
        while (str1.indexOf(str2) != -1) {
            counter++;
            str1 = str1.substring(str1.indexOf(str2) + str2.length());
        }
        return counter;
    }
}
