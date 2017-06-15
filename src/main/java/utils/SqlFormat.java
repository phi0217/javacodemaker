package utils;

import com.sun.org.apache.regexp.internal.RE;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Phi on 2017/4/10.
 */
public class SqlFormat {

/*    private static String javaBeanName = "javaBeanOut";//javabean文件名
    private static String mapperParamName = "FundSubjectOriginalTmpMapper";//mapperParam文件名
    private static String path = "D:\\";//目标文件生成路径
    private static String filePath = "D:\\sql.txt";//sql文件路径*/

    /*  sql文件路径
       test.txt文件内容如下
       粘贴powerdesigner中的sql,例如
                " parameter_id   VARCHAR2(20) not null,\n" +
                "  subject_code   VARCHAR2(20) not null,\n" +
                "  subject_name   VARCHAR2(120) not null,"
    */

/*    //执行工具类
    public static void main(String[] args) {
        SqlFormat sqlTransformUtils = new SqlFormat();
        String sql = readTxtFile(filePath);
        String javaBeanParam = "public class " + javaBeanName + "{" + "\n" + sqlTransformUtils.toJavaBean(sql) +"}\n\n"+toEtcParam(sql);//生成javabean字段
        String mapperParam = sqlTransformUtils.toMapper(sql)+"\n\n"+toCommonSql(sql);//生成mapper字段
        try {
            creatTxtFile(javaBeanName,".java");
            writeTxtFile(javaBeanParam);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            creatTxtFile(mapperParamName,".xml");
            writeTxtFile(mapperParam);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("javaBeanParam:"+"\n"+javaBeanParam);
        System.out.println("mapperParam:"+"\n"+mapperParam + "\n");
    }*/

    /*sql生成的原素材，一个装List的List*/
    public static List<List<String>> toList(String string){
        String before = string.replaceAll("\"","");
        String[] list = before.replaceAll("\n","").split(",");
        List<List<String>> listList = new ArrayList<>();
        for (String l : list) {
            if (l.contains(" ")){
                List<String> li = new ArrayList<>();
                String cl = l.trim();
                String[] arr = cl.split("\\s+");
                String name = arr[0];
                String type = arr[1];
                String[] tolist = name.split("_");
                boolean first = true;
                StringBuffer propertyName = new StringBuffer();
                for (String theName : tolist) {
                    if (first){
                        propertyName.append(theName);
                        first = false;
                    }else {
                        theName = theName.substring(0, 1).toUpperCase() + theName.substring(1);
                        propertyName.append(theName);
                    }
                }
                li.add(name);//0 sqlName:parameter_id
                li.add(type);//1 sqlType:VARCHAR(20)
                String camelName = propertyName.toString();
                li.add(camelName);//2 camelName:parameterId
                String normalName = camelName.substring(0, 1).toUpperCase() + camelName.substring(1);
                li.add(normalName);//3 normalName:ParameterId
                listList.add(li);
            }

        }
        return listList;
    }

    public static String toEtcParam(String string){
        List<List<String>> listList = toList(string);
        //bean的所有参数赋值方法
        StringBuffer setter = new StringBuffer();
        for (List<String> list: listList){
            setter.append("theObject.set").append(list.get(3)).append("();\n");
        }

        //bean的所有参数作为入参

        return setter.toString();
    }

    public static String mapperTransform(String string){
        if (string.contains("CHAR")){
            string = "CHAR";
        }
        if (string.contains("NUMBER")&&string.contains(".")){
            string = "DOUBLE";
        }
        if (string.contains("NUMBER")&&!string.contains(".")){
            string = "INT";
        }
        if (string.contains("DATE") || string.contains("TIMESTAMP") || string.contains("YEAR")
                || string.contains("MONTH") || string.contains("DAY") || string.contains("SECOND")){
            string = "DATE";
        }
        string = string.split("\\(")[0];
        String res = new String();
        switch(string)
        {
            case "CHAR": res = "string"; break;
            case "INT": res = "int"; break;
            case "INTEGER": res = "int"; break;
            case "DOUBLE": res = "double"; break;
            case "FLOAT": res = "float"; break;
            case "DATE": res = "java.util.Date"; break;
            default: res = string; break;
        }
        return res;
    }

    public static String mapperJavaBean(String string){
        if (string.contains("CHAR")){
            string = "CHAR";
        }
        if (string.contains("NUMBER")&&string.contains(".")){
            string = "DOUBLE";
        }
        if (string.contains("NUMBER")&&!string.contains(".")){
            string = "INT";
        }
        if (string.contains("DATE") || string.contains("TIMESTAMP") || string.contains("YEAR")
                || string.contains("MONTH") || string.contains("DAY") || string.contains("SECOND")){
            string = "DATE";
        }
        string = string.split("\\(")[0];
        String res = new String();
        switch(string)
        {
            case "CHAR": res = "String"; break;
            case "INT": res = "Integer"; break;
            case "INTEGER": res = "Integer"; break;
            case "FLOAT": res = "Float"; break;
            case "DOUBLE": res = "Double"; break;
            case "DATE": res = "Date"; break;
            default: res = string; break;
        }

        return res;
    }


    //生成javabean
    public static String toJavaBean(String string){
        List<List<String>> listList = toList(string);
        StringBuffer res = new StringBuffer();
        for (List<String> strings : listList) {
            StringBuffer buf = new StringBuffer();
            buf.append("\t").append("private ").append(mapperJavaBean(strings.get(1))).append(" ").append(strings.get(2)).append(";");
            res.append(buf).append("\n");
        }
        return res.toString();
    }

    //生成带注释的javabean
    public static String toJavaBeanHaveText(String string,String text){
        List<List<String>> listList = toList(string);
        List<List<String>> textList = toTextList(text);
        Map<String, String> textMap = new HashMap<>();
        for (List<String> s : textList){
            textMap.put(s.get(0), s.get(1));
        }
        StringBuffer res = new StringBuffer();
        for (List<String> strings : listList) {
            StringBuffer buf = new StringBuffer();
            buf.append("\t").append("private ").append(mapperJavaBean(strings.get(1))).append(" ").append(strings.get(2)).append(";");
            if (textMap.containsKey(strings.get(0))){
                buf.append("//").append(textMap.get(strings.get(0)));
            }
            res.append(buf).append("\n");
        }
        return res.toString();
    }

    //生成mapperParam
    public static String toMapper(String string){
        List<List<String>> listList = toList(string);
        StringBuffer res = new StringBuffer();
        res.append("<resultMap type=\"?\" id=\"baseResult\">\n");
        for (List<String> strings : listList) {
            StringBuffer buf = new StringBuffer();
            buf.append("\t").append("<result column=\"").append(strings.get(0))
                    .append("\" property=\"").append(strings.get(2))
                    .append("\" javaType=\"").append(mapperTransform(strings.get(1))).append("\" />");
            res.append(buf).append("\n");
        }
        res.append("</resultMap>");
        return res.toString();
    }

    //生成常用sql
    public static String toCommonSql(String string){
        List<List<String>> listList = toList(string);
        StringBuffer baseSql = new StringBuffer();
        baseSql.append("<sql id=\"baseSql\">\n");
        StringBuffer select = new StringBuffer();
        select.append("<select id=\"\" resultType=\"?\">\n" +
                "\tSELECT\n" +
                "\t<include refid=\"baseSql\" />\n" +
                "\tfrom ?\n" +
                "\twhere ?\n" +
                "\tORDER by ? DESC\n" +
                "</select>");
        StringBuffer selectCommon = new StringBuffer();
        selectCommon.append("<select id=\"?\" resultType=\"?\">\n" +
                "\tSELECT\n" +
                "\t<include refid=\"baseSql\" />\n" +
                "\tfrom ?\n" +
                "\twhere\n" +
                "\t<trim prefixOverrides=\"AND\">\n");
        StringBuffer insertBefore = new StringBuffer();
        insertBefore.append("<insert id=\"\" >\n" +
                "\tINSERT\n" +
                "\tINTO ?\n" +
                "\t(");
        StringBuffer insertAfter = new StringBuffer();
        insertAfter.append("\tVALUES\n" +
                "\t(");
        StringBuffer insertCommonBefore = new StringBuffer();
        insertCommonBefore.append("<insert id=\"?\">\n" +
                "\tINSERT\n" +
                "\tINTO ?\n" +
                "\t(\n\t<trim suffixOverrides=\",\">\n");
        StringBuffer insertCommonAfter = new StringBuffer();
        insertCommonAfter.append("\tVALUES\n" +
                "\t(\n\t<trim suffixOverrides=\",\">\n");
        StringBuffer update = new StringBuffer();
        update.append("<update id=\"\" parameterType=\"?\">\n" +
                "\tUPDATE ?\n" +
                "\tSET\n");
        StringBuffer updateCommon = new StringBuffer();
        updateCommon.append("<update id=\"\">\n" +
                "\tUPDATE ?\n" +
                "\t<set>\n");
        StringBuffer delete = new StringBuffer();
        delete.append("<delete id=\"\">\n" +
                "\tdelete from ? where ?\n" +
                "</delete>");
        for (int i = 0;i <= listList.size()-1;i++) {
            if (i != listList.size()-1){
                baseSql.append("\t").append(listList.get(i).get(0)).append(",\n");
                selectCommon.append("\t<if test=\"").append(listList.get(i).get(2)).append(" != null\">").append("AND ")
                        .append(listList.get(i).get(0)).append("=#{").append(listList.get(i).get(2)).append("}</if>\n");
                insertBefore.append("\t").append(listList.get(i).get(0)).append(",\n");
                insertAfter.append("\t#{").append(listList.get(i).get(2)).append("},\n");
                insertCommonBefore.append("\t\t<if test=\"").append(listList.get(i).get(2)).append(" != null\">")
                        .append(listList.get(i).get(0)).append(",</if>\n");
                insertCommonAfter.append("\t\t<if test=\"").append(listList.get(i).get(2)).append(" != null\">")
                        .append("#{").append(listList.get(i).get(2)).append("},</if>\n");
                update.append("\t").append(listList.get(i).get(0)).append("=#{").append(listList.get(i).get(2)).append("},\n");
                updateCommon.append("\t\t<if test=\"").append(listList.get(i).get(2)).append(" != null\">")
                        .append(listList.get(i).get(0)).append("=#{").append(listList.get(i).get(2)).append("},</if>\n");
            }else {
                baseSql.append("\t").append(listList.get(i).get(0)).append("\n</sql>");
                selectCommon.append("\t<if test=\"").append(listList.get(i).get(2)).append(" != null\">").append("AND ")
                        .append(listList.get(i).get(0)).append("=#{").append(listList.get(i).get(2)).append("}</if>\n</trim>\n</select>");
                insertBefore.append("\t").append(listList.get(i).get(0)).append(")\n");
                insertAfter.append("\t#{").append(listList.get(i).get(2)).append("})\n" +
                        "</insert>");
                insertCommonBefore.append("\t\t<if test=\"").append(listList.get(i).get(2)).append(" != null\">")
                        .append(listList.get(i).get(0)).append("</if>\n\t</trim>\n\t)\n");
                insertCommonAfter.append("\t\t<if test=\"").append(listList.get(i).get(2)).append(" != null\">")
                        .append("#{").append(listList.get(i).get(2)).append("}</if>\n\t</trim>\n\t)\n" +
                        "</insert>");
                update.append("\t").append(listList.get(i).get(0)).append("=#{").append(listList.get(i).get(2)).append("}\n\tWHERE ?\n" +
                        "</update>");
                updateCommon.append("\t\t<if test=\"").append(listList.get(i).get(2)).append(" != null\">")
                        .append(listList.get(i).get(0)).append("=#{").append(listList.get(i).get(2)).append("}</if>\n" +
                        "\t</set>\n" +
                        "\tWHERE ?\n</update>");
            }
        }
        StringBuffer insert = new StringBuffer();
        StringBuffer insertCommon = new StringBuffer();
        insert.append(insertBefore).append(insertAfter);
        insertCommon.append(insertCommonBefore).append(insertCommonAfter);
        String res = baseSql.append("\n\n").append(select).append("\n\n").append(selectCommon).append("\n\n").append(insert)
                .append("\n\n").append(insertCommon).append("\n\n").append(update).append("\n\n").append(updateCommon).append("\n\n").append(delete).toString();
        return res;
    }

    public static List<List<String>> toTextList(String text){
        List<List<String>> res = new ArrayList<>();
        String[] lineList = text.split(";");
        for (String s : lineList) {
            List<String> tmpList = new ArrayList<>();
            if (s.indexOf("column") > 0){
                String[] sList = s.split("\"");
                tmpList.add(sList[3]);//属性名
                String[] sList1 = s.split("'");
                tmpList.add(sList1[1]);//注释
                res.add(tmpList);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String req ="comment on table \"t_subject_original\" is\n" +
                "'会计科目表';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"parameter_id\" is\n" +
                "'参数编号';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"subject_code\" is\n" +
                "'会计科目代码';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"subject_name\" is\n" +
                "'会计科目名称';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"superior_subject_code\" is\n" +
                "'上级会计科目代码';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"superior_subject_name\" is\n" +
                "'上级会计科目名称';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"subject_level\" is\n" +
                "'会计科目级别';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"subject_type\" is\n" +
                "'会计科目类型（表内、表外）';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"has_sub_subject_flag\" is\n" +
                "'是否有下级会计科目（0：无，1：有）';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"subject_balance_direction\" is\n" +
                "'会计科目余额方向';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"remark\" is\n" +
                "'备注';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"subject_authority_type\" is\n" +
                "'科目权限类型（私有、全局）';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"subject_ownership\" is\n" +
                "'科目所有者（私有的情况下，记录其所有者）';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"input_operator_code\" is\n" +
                "'录入人代码';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"input_operator_name\" is\n" +
                "'录入人姓名';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"input_datetime\" is\n" +
                "'录入时间';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"audit_operator_code\" is\n" +
                "'核准人代码';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"audit_operator_name\" is\n" +
                "'核准人姓名';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"audit_datetime\" is\n" +
                "'核准时间';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"effect_status\" is\n" +
                "'生效状态';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"effect_datetime\" is\n" +
                "'生效时间';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"update_sequence_no\" is\n" +
                "'最近更新序号';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"update_datetime\" is\n" +
                "'最近更新时间';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"reserved_field1\" is\n" +
                "'预留字段1';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"reserved_field2\" is\n" +
                "'预留字段2';\n" +
                "\n" +
                "comment on column \"t_subject_original\".\"reserved_field3\" is\n" +
                "'预留字段3';\n";
        String sql = " \"parameter_id\"       VARCHAR(20)          not null,\n" +
                "   \"subject_code\"       VARCHAR(20)          not null,\n" +
                "   \"subject_name\"       VARCHAR(120)         not null,\n" +
                "   \"superior_subject_code\" VARCHAR(20),\n" +
                "   \"superior_subject_name\" VARCHAR(120),\n" +
                "   \"subject_level\"      NUMBER(2)            not null,\n" +
                "   \"subject_type\"       NUMBER(2)            not null,\n" +
                "   \"has_sub_subject_flag\" NUMBER(2)            not null,\n" +
                "   \"subject_balance_direction\" NUMBER(2)            not null,\n" +
                "   \"remark\"             VARCHAR(1000),\n" +
                "   \"subject_authority_type\" VARCHAR(20)          not null,\n" +
                "   \"subject_ownership\"  VARCHAR(20),\n" +
                "   \"input_operator_code\" VARCHAR(20),\n" +
                "   \"input_operator_name\" VARCHAR(200),\n" +
                "   \"input_datetime\"     DATE,\n" +
                "   \"audit_operator_code\" VARCHAR(20),\n" +
                "   \"audit_operator_name\" VARCHAR(200),\n" +
                "   \"audit_datetime\"     DATE,\n" +
                "   \"effect_status\"      NUMBER(2)            not null,\n" +
                "   \"effect_datetime\"    DATE,\n" +
                "   \"update_sequence_no\" VARCHAR(20),\n" +
                "   \"update_datetime\"    DATE,\n" +
                "   \"reserved_field1\"    VARCHAR(200),\n" +
                "   \"reserved_field2\"    VARCHAR(200),\n" +
                "   \"reserved_field3\"    VARCHAR(200),";
        List<List<String>> res = toTextList(req);
        String rres = toJavaBeanHaveText(sql,req);
        System.out.println();
    }

}
