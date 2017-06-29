package utils;

import dto.ExcelDto;
import dto.ParamDto;
import exception.UtilsException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static utils.CommonUtils.capitalize;
import static utils.ExcelMaker.makeExcel;

/**
 * Created by Phi on 2017/5/20.
 */
public class DocFormat {
    public static List<ExcelDto> queryExcelList(String interfacePath, String paramPath, String interfaceJar, String paramJar) throws UtilsException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date today = new Date();
        String updateString = sdf.format(today);

        ReadDir readDir = new ReadDir();
        ReadFile readFile = new ReadFile();
        String interfaceStr = "";
        List<ExcelDto> res = new ArrayList<>();
        List<String> paramPathList;
        String packageName = "";
        try {
            paramPathList = readDir.queryFileNameList(paramPath);
            interfaceStr = readFile.readFile(interfacePath);
        } catch (IOException e) {
            throw new UtilsException(e.toString());
        } catch (Exception e) {
            throw new UtilsException(e.toString());
        }
        while (interfaceStr.contains("/**")) {
            String rmStr = "/**" + interfaceStr.split("/[*][*]")[1].split("[*]/")[0] + "*/";
            rmStr = rmStr.replaceAll("[*]","[*]");
            interfaceStr = interfaceStr.replaceAll(rmStr,"");
        }
            String[] interfaceList = interfaceStr.split("@FunctionInfo");
        String interfaceTmp = "";

        for (String s : interfaceList) {
            s = s.trim();
            if (s.contains("package")) {
                packageName = s.split("package ")[1].split(";")[0];
            }
            if (s.contains("interface ")) {
                interfaceTmp = s.split("interface ")[1].split(" ")[0];
            } else {
                if (s.contains("functionNo")) {
                    if (s.contains(");")) {
                        ExcelDto excelDto = new ExcelDto();
                        excelDto.setFunctionCode(s.split("functionNo")[1].split("\"")[1].split("\"")[0]);
                        excelDto.setFunctionName(s.split("\n")[1].trim().split(" ")[1].split("\\(")[0]);
                        excelDto.setCnName(s.split("description")[1].split("\"")[1].split("\"")[0]);
                        excelDto.setBusinessDescribe(s.split("description")[1].split("\"")[1].split("\"")[0]);
                        excelDto.setVersion(s.split("version")[1].split("\"")[1].split("\"")[0]);
                        excelDto.setUpdateDate(updateString);
                        excelDto.setInterFaceJar(interfaceJar);
                        excelDto.setParamJar(paramJar);
                        excelDto.setInterfaceFunction(packageName + "." + interfaceTmp + "." + (s.split("\n")[1].trim().split(" ")[1] ) + " request)");
                        excelDto.setReqName(s.split("\n")[1].trim().split("\\(")[1].split(" ")[0]);
                        excelDto.setResName(s.split("\n")[1].trim().split(" ")[0]);
                        try {
                            excelDto.setReqList(queryParam(excelDto.getReqName(), paramPathList));
                            excelDto.setResList(queryParam(excelDto.getResName(), paramPathList));
                        } catch (IOException e) {
                            throw new UtilsException(e.toString());
                        }
                        res.add(excelDto);
                    }
                }
            }
        }

        return res;
    }

    public static List<ParamDto> queryParam(String paramName, List<String> paramPathList) throws IOException {
        ReadFile readFile = new ReadFile();
        List<ParamDto> res = new ArrayList<>();
        int n = 0;
        for (String s : paramPathList) {
            if (s.contains(paramName)) {
                String file = readFile.readFile(s);
                boolean a = (!file.contains(" class ")) || (!file.contains("/**")) || (!file.contains("private")) || (!file.contains(";"));
                String[] paramList = file.split(" class ")[1].split("/[*][*]");
                for (String s1 : paramList) {
                    n++;
                    if (s1.contains("private static final long")) {
                        continue;
                    }
                    if (!s1.contains("private") || !s1.contains(";")) {
                        continue;
                    }
                    String prefix = "";
                    String suffix = "";
                    ParamDto paramDto = new ParamDto();
                    paramDto.setName(s1.split("private ")[1].split(" ")[1].split(";")[0]);
                    paramDto.setType(s1.split("private ")[1].split(" ")[0]);
                    if (paramDto.getType().contains("List<")) {
                        paramDto.setType(paramDto.getType().split("List<")[1].split(">")[0]);
                        suffix = "n]";
                    } else {
                        suffix = "1]";
                    }
                    if (s1.contains("@ParamElement(required=true)")) {
                        prefix = "[1";
                    } else {
                        prefix = "[0";
                    }
                    paramDto.setAttribute(prefix + "…" + suffix);
                    if (s1.contains("*")) {
                        paramDto.setRemark(s1.split("[*]")[1].split("\n")[0].trim());
                    }
                    res.add(paramDto);
                }
            }
        }
        return res;
    }

    /**
     * 生成非空判断
     * @param excelDtos
     * @return
     */
    public static String validateNotNullUtil(List<ExcelDto> excelDtos){
        StringBuffer validateBuffer = new StringBuffer();
        StringBuffer constantBuffer = new StringBuffer();
        for (ExcelDto excelDto : excelDtos) {
            StringBuffer validateBuf = new StringBuffer();
            validateBuf.append("    /**\n" +
                    "     * ").append(excelDto.getCnName()).append("校验非空字段\n" +
                    "     *\n" +
                    "     * @param req\n" +
                    "     */\n" +
                    "    private void ").append("validateNotNull").append(excelDto.getFunctionName())
                    .append("(").append(excelDto.getReqName()).append(" req) {\n" +
                    "        ");
            StringBuffer constantBuf = new StringBuffer();
            constantBuf.append("//*************").append(excelDto.getFunctionName()).append("操作常量************//\n");

            List<ParamDto> reqList = excelDto.getReqList();
//            int counter = 0;
//            int counter2 = 101;
            for (ParamDto paramDto : reqList) {
                if (paramDto.getAttribute().contains("[1")){

//                    String counterStr = counter+"";
//                    if (1 == counterStr.length()){
//                        counterStr = "0" + counterStr;
//                    }

                    validateBuf.append("if (BaseBeanUtils.isNull(req.get").append(capitalize(paramDto.getName())).append("())){\n" +
                            "            throw new ClearingException(ExceptionConstants.ERR_").append(excelDto.getFunctionName().toUpperCase())
                    .append("_").append(paramDto.getName().toUpperCase()).append("_").append("ISNULL").append(");\n" +
                            "        }\n" +
                            "        ");
                    constantBuf.append("\t/** ").append("参数").append(paramDto.getRemark()).append("不能为空").append(" */\n" +
                            "\tpublic static final String ERR_").append(excelDto.getFunctionName().toUpperCase())
                            .append("_").append(paramDto.getName().toUpperCase()).append("_").append("ISNULL").append(" = \"")
                            .append("参数").append(paramDto.getRemark()).append("不能为空").append("\";\n" +
                            "\t");

                validateBuf.append("\n" +
                        "    }");
                validateBuffer.append("\n").append(validateBuf);
                constantBuffer.append("\n").append(constantBuf);
                }
            }
        }
        return validateBuffer.append("\n").append(constantBuffer).toString();
    }

    public static String controllerMaker(List<ExcelDto> req){
        StringBuffer res = new StringBuffer();
        for (ExcelDto excelDto : req) {
            int tmp = 0;
            res.append("@RequestMapping(value = \"/").append(excelDto.getFunctionName().toLowerCase()).append("\")\n" +
                    "    public String ").append(excelDto.getFunctionName()).append("(");
            for (ParamDto paramDto : excelDto.getReqList()) {
                if ("Date".equals(paramDto.getType())){
                    tmp = 1;
                    res.append("@RequestParam(value = \"").append(paramDto.getName()).append("\", required = false) ").append("String").append(" ").append(paramDto.getName()).append(",\n");
                }else {
                    res.append("@RequestParam(value = \"").append(paramDto.getName()).append("\", required = false) ").append(paramDto.getType()).append(" ").append(paramDto.getName()).append(",\n");
                }
            }
            if (tmp == 1){
                res.append("ModelMap modelMap) {\n" +
                        "        ").append("SimpleDateFormat sdf = new SimpleDateFormat(\"yyyy-MM-dd HH:mm\");\n" +
                        "        ").append(excelDto.getReqName()).append(" req = new ").append(excelDto.getReqName()).append("();\n" +
                        "        ");
            }
            res.append("ModelMap modelMap) {\n" +
                    "        ").append(excelDto.getReqName()).append(" req = new ").append(excelDto.getReqName()).append("();\n" +
                    "        ");
            for (ParamDto paramDto : excelDto.getReqList()) {
                if ("Date".equals(paramDto.getType())){
                    res.append("try {\n" +
                            "                req.set").append(capitalize(paramDto.getName())).append("(").append("sdf.parse(").append(paramDto.getName()).append("));\n" +
                            "            } catch (ParseException e) {\n" +
                            "            }");
                }if ("BigDecimal".equals(paramDto.getType())){
                    res.append("if (").append(paramDto.getName()).append(" == null){\n" +
                            "            req.set").append(capitalize(paramDto.getName())).append("(new BigDecimal(0));\n" +
                            "        }else {\n" +
                            "            req.set").append(capitalize(paramDto.getName())).append("(").append(paramDto.getName()).append(");\n" +
                            "        }\n" +
                            "        ");
                }else {
                    res.append("req.set").append(capitalize(paramDto.getName())).append("(").append(paramDto.getName()).append(");\n" +
                            "        ");
                }
            }
            res.append(excelDto.getResName()).append(" res = ").append("?Service.").append(excelDto.getFunctionName()).append("(req);\n" +
                    "        getResultMessageByResultCode(res);\n" +
                    "        return null;\n" +
                    "    }\n\n");

        }
        return res.toString();
    }

    public static String nameListMaker(List<ExcelDto> excelDtos){
        StringBuffer res = new StringBuffer();
        for (ExcelDto excelDto : excelDtos) {
            res.append(excelDto.getFunctionName()).append(" ").append(excelDto.getCnName()).append("\n");
        }
        return res.toString();
    }

    public static void docmaker(String interfacePath, String paramPath, String interfaceJar, String paramJar, String path) {
        List<ExcelDto> excelDtos = queryExcelList(interfacePath, paramPath, interfaceJar, paramJar);
        makeExcel(excelDtos, path + "workbook.xls");
    }

    public static void main(String[] args) {
        String interfacePath = "D:\\Developer\\WorkSpace\\IntelliJIDEA\\settlementcenter2\\com.xinfengtech.fund.interfaces\\src\\main\\java\\com\\xinfengtech\\fund\\interfaces\\service\\externalaccount\\ExternalAccountService.java";
        String paramPath = "D:\\Developer\\WorkSpace\\IntelliJIDEA\\settlementcenter2\\com.xinfengtech.fund.schema\\src\\main\\java\\com\\xinfengtech\\fund\\schema\\externalaccount";
        String interfaceJar = "com.xinfengtech.fund.interfaces";
        String paramJar = "com.xinfengtech.control.schema";
        List<ExcelDto> res = queryExcelList(interfacePath, paramPath, interfaceJar, paramJar);
        String path = "d:\\workbook.xls";
        String validateNotNull = validateNotNullUtil(res);
        String nameList = nameListMaker(res);
        String controllerList = controllerMaker(res);
        System.out.println();

        makeExcel(res, path);

        System.out.println();
    }
}
