package utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Phi on 2017/4/18.
 */
public class MethodFormat {
   /* *//*生成按照命名规则的Manager*//*
    public static List<String> toTheManagerList(String coreName,String coreAnnotation){
        String text = "public interface " + coreName + "Manager {\n" +
                "    *//**\n" +
                "     * 新增" + coreAnnotation + "\n" +
                "     *//*\n" +
                "    Input" + coreName + "NewResponse input" + coreName + "New(Input" + coreName + "NewRequest req) throws ClearingException;//新增" + coreAnnotation + "录入\n" +
                "\n" +
                "    Modify" + coreName + "NewResponse modify" + coreName + "New(Modify" + coreName + "NewRequest req) throws ClearingException;//新增" + coreAnnotation + "录入修改\n" +
                "\n" +
                "    Delete" + coreName + "NewResponse delete" + coreName + "New(Delete" + coreName + "NewRequest req) throws ClearingException;//新增" + coreAnnotation + "录入删除\n" +
                "\n" +
                "    Audit" + coreName + "NewResponse audit" + coreName + "New(Audit" + coreName + "NewRequest req) throws ClearingException;//新增" + coreAnnotation + "复核\n" +
                "\n" +
                "    *//**\n" +
                "     * 变更" + coreAnnotation + "\n" +
                "     *//*\n" +
                "    Input" + coreName + "UpdateResponse input" + coreName + "Update(Input" + coreName + "UpdateRequest req) throws ClearingException;//变更" + coreAnnotation + "录入\n" +
                "\n" +
                "    Modify" + coreName + "UpdateResponse modify" + coreName + "Update(Modify" + coreName + "UpdateRequest req) throws ClearingException;//变更" + coreAnnotation + "录入修改\n" +
                "\n" +
                "    Delete" + coreName + "UpdateResponse delete" + coreName + "Update(Delete" + coreName + "UpdateRequest req) throws ClearingException;//变更" + coreAnnotation + "录入删除\n" +
                "\n" +
                "    Audit" + coreName + "UpdateResponse audit" + coreName + "Update(Audit" + coreName + "UpdateRequest req) throws ClearingException;//变更" + coreAnnotation + "复核\n" +
                "\n" +
                "    *//**\n" +
                "     * 注销" + coreAnnotation + "\n" +
                "     *//*\n" +
                "    Input" + coreName + "DropResponse input" + coreName + "Drop(Input" + coreName + "DropRequest req) throws ClearingException;//注销" + coreAnnotation + "录入\n" +
                "\n" +
                "    Delete" + coreName + "DropResponse delete" + coreName + "Drop(Delete" + coreName + "DropRequest req) throws ClearingException;//注销" + coreAnnotation + "录入删除\n" +
                "\n" +
                "    Audit" + coreName + "DropResponse audit" + coreName + "Drop(Audit" + coreName + "DropRequest req) throws ClearingException;//注销" + coreAnnotation + "复核\n" +
                "}";
        String title = coreName + "Manager";
        return null;
    }*/

    /*生成按照命名规则的方法*/
    public static String toTheMethodList(String coreName){
        StringBuffer res = new StringBuffer();
        res.append("input" + coreName + "New\n");
        res.append("modify" + coreName + "New\n");
        res.append("delete" + coreName + "New\n");
        res.append("audit" + coreName + "New\n");
        res.append("input" + coreName + "Update\n");
        res.append("modify" + coreName + "Update\n");
        res.append("delete" + coreName + "Update\n");
        res.append("audit" + coreName + "Update\n");
        res.append("input" + coreName + "Drop\n");
        res.append("delete" + coreName + "Drop\n");
        res.append("audit" + coreName + "Drop\n");
        return res.toString();
    }
    /*生成通用的方法List*/
    public static List<String> toCommonMethodList(String method){
        String[] list = method.split("\n");
        List<String> resList = new ArrayList<>();
        for (String s : list) {
            s = s.trim();
            s = s.substring(0, 1).toUpperCase() + s.substring(1);
            resList.add(s);
        }
        return resList;
    }

    /*按照方法List生成Manager*/
    public static String toCommonManager(String coreName,List<String> list){
        StringBuffer text = new StringBuffer();
        text.append("public interface " + coreName + "Manager {\n");
        for (String s : list) {
            String lower = s.substring(0,1).toLowerCase() + s.substring(1);
            text.append("\t").append(s).append("Response ").append(lower).append("(").append(s).append("Request req) throws ClearingException;\n");
        }
        text.append("}");
        return text.toString();
    }

    /*按照方法List生成ManagerImpl*/
    public static String toCommonManagerImpl(String coreName,List<String> list){
        StringBuffer text = new StringBuffer();
        text.append("@Service\n" +
                "public class " + coreName + "ManagerImpl implements " + coreName + "Manager {");
        for (String s : list) {
            String lower = s.substring(0,1).toLowerCase() + s.substring(1);
            text.append("/**\n" +
                    "     * \n" +
                    "     * @param req\n" +
                    "     * @return\n" +
                    "     * @throws ClearingException\n" +
                    "     */\n" +
                    "    @Override\n" +
                    "    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)\n" +
                    "    public ").append(s).append("Response ").append(lower).append("(").append(s).append("Request req) throws ClearingException{\n " +
                    "        ").append(s).append("Response res = new ").append(s).append("Response();\n" +
                    "        res.setResultCode(FundFieldConstants.RESULT_FLAG_SUCCESS);\n" +
                    "        return res;\n" +
                    "    }");
        }
        text.append("}");
        return text.toString();
    }

    /*按照方法List生成Service*/
    public static String toCommonService(String coreName,List<String> list){
        StringBuffer text = new StringBuffer();
        text.append("public interface " + coreName + "Service {\n");
        for (String s : list) {
            String lower = s.substring(0,1).toLowerCase() + s.substring(1);
            text.append("@FunctionInfo(functionNo=\"?\", description=\"?\", version=\"1.0.0.0\")\n");
            text.append("\t").append(s).append("Response ").append(lower).append("(").append(s).append("Request req);\n\n");
        }
        text.append("}");
        return text.toString();
    }

    /*按照方法List生成ServiceImpl*/
    public static String toCommonServiceImpl(String coreName,List<String> list){
        StringBuffer text = new StringBuffer();
        String lowerCoreName = coreName.substring(0,1).toLowerCase() + coreName.substring(1);
        text.append("@Service(\""+ coreName +"Service\")\n" +
                "public class "+ coreName +"ServiceImpl implements "+ coreName +"Service {\n" +
                "    private final Logger logger = LoggerFactory.getLogger(this.getClass()); \n" +
                "    @Autowired\n" +
                "    ").append(coreName).append("Manager ").append(lowerCoreName).append("Manager;\n");
        for (String s : list) {
            String lower = s.substring(0,1).toLowerCase() + s.substring(1);
            text.append("    @Override\n" +
                    "    public ").append(s).append("Response ").append(lower).append("(").append(s).append("Request req) {\n" +
                    "        ").append(s).append("Response resp = new ").append(s).append("Response();\n" +
                    "\tString description = \"?功能名?\";\n"+
                    "        try {\n" +
                            "\t\t\tlogger.info(\n" +
                            "\t\t\t\t\t\"请求编号：【\"+req.getRequestNo()+\"】\"\n" +
                            "\t\t\t\t\t+ \"操作员：【\"+req.getOperCode()+\"】\"\n" +
                            "\t\t\t\t\t+ \"【BEGIN】\"+description+\"...\"\n" +
                            "\t\t\t\t\t+ \"?主键?：【?】\"\n" +
                            "\t\t\t\t\t);"+
                    "            try {\n" +
                    "                resp= ").append(lowerCoreName).append("Manager.").append(lower).append("(req);\n" +
                    "\t\t\tlogger.info(\n" +
                    "\t\t\t\t\t\"【END-SUCCESS】\"+description+\"...\"\n" +
                    "\t\t\t\t\t+\"?主键?：【?】\"\n" +
                    "\t\t\t\t\t);" +
                    "            }catch (ClearingException e){\n" +
                    "                logger.error(\"【END-ERROR】\"+description+\"...errorCode=\" + e.getErrorCode(), e);\n" +
                    "                resp.setResultCode(e.getErrorCode());\n" +
                    "                resp.setResultMessage(e.getErrorMessage());\n" +
                    "            }\n" +
                    "        }catch (Exception e){\n" +
                    "            logger.error(\"【END-ERROR】\"+description+\"...\", e);\n" +
                    "            resp.setResultCode(ExceptionConstants.SYS_ERR);\n" +
                    "            resp.setResultMessage(e.getMessage().toString());\n" +
                    "        }\n finally {\n" +
                    "\t\t\tlogger.info(\n" +
                    "\t\t\t\t\t\"请求编号：【\"+req.getRequestNo()+\"】\"\n" +
                    "\t\t\t\t\t+ \"【END】\"+description+\"...\"\n" +
                    "\t\t\t\t\t);\n" +
                    "\t\t}" +
                    "        return resp;\n" +
                    "    }\n");
        }
        text.append("}");
        return text.toString();
    }

    public static String toCommonController(List<String> list){
        StringBuffer text = new StringBuffer();
        for (String s : list) {
            String lower = s.substring(0,1).toLowerCase() + s.substring(1);
            text.append("@RequestMapping(value = \"/").append(lower).append("\")\n" +
                    "    public String ").append(lower).append("(){\n" +
                    "        return null;\n" +
                    "    }\n");
        }
        return text.toString();
    }

    /*按照方法List生成Resp*/
    public static List<List<String>> toResp(List<String> list){
        List<List<String>> res = new ArrayList<>();
        for (String s : list) {
            List<String > l = new ArrayList<>();
            StringBuffer text = new StringBuffer();
            text.append("import com.xinfengtech.common.base.resp.BaseResponse;\n" +
                    "\n" +
                    "/**\n" +
                    " * Created by \n" +
                    " */").append("public class ").append(s).append("Response extends BaseResponse{\n" +
                    "\n" +
                    "}");
            l.add(s+"Response");//文件名
            l.add(text.toString());//文件内容
            res.add(l);
        }
        return res;
    }
    /*按照方法List生成Req*/
    public static List<List<String>> toReq(List<String> list){
        List<List<String>> res = new ArrayList<>();
        for (String s : list) {
            List<String > l = new ArrayList<>();
            StringBuffer text = new StringBuffer();
            text.append("import com.xinfengtech.common.base.req.BaseRequest;\n" +
                    "\n" +
                    "/**\n" +
                    " * Created by .\n" +
                    " */\n" +
                    "public class ").append(s).append("Request extends BaseRequest{\n" +
                    "\n" +
                    "}");
            l.add(s+"Request");//文件名
            l.add(text.toString());//文件内容
            res.add(l);
        }
        return res;
    }

}
