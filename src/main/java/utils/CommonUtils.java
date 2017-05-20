package utils;

/**
 * Created by Phi on 2017/4/18.
 */
public class CommonUtils {
    public static String textFormat(String text){
        String res = text.replaceAll("\r","");
        return res;
    }

    public static String htmlFormat(String text){
        String res = text.replaceAll("\n","</p><p>");
        res = "<p>" + res + "</p>";
        return res;
    }

    public static void main(String[] args) {
        String res = textFormat("inputSubjectNew\r\n" +
                "modifySubjectNew");
        System.out.println();
    }


}
