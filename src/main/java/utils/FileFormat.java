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

/**
 * Created by Phi on 2017/5/20.
 */
public class FileFormat {
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
        String[] interfaceList = interfaceStr.split("\n");
        String tmp = "";
        String interfaceTmp = "";

        for (String s : interfaceList) {
            s = s.trim();
            if (s.contains("import")){
                continue;
            }
            if (s.contains("package")) {
                packageName = s.split(" ")[1].split(";")[0];
            }else{
                if (s.contains("interface ")) {
                    interfaceTmp = s.split("interface ")[1].split(" ")[0];
                }else {
                    if (s.contains("@FunctionInfo")) {
                        tmp = s;
                    }else {
                        if (s.contains(");")) {
                            ExcelDto excelDto = new ExcelDto();
                            excelDto.setFunctionCode(tmp.split("@FunctionInfo\\(functionNo=\"")[1].split("\"")[0]);
                            excelDto.setFunctionName(capitalize(s.split(" ")[1].split("\\(")[0]));
                            excelDto.setCnName(tmp.split(" description=\"")[1].split("\"")[0]);
                            excelDto.setBusinessDescribe(tmp.split(" description=\"")[1].split("\"")[0]);
                            excelDto.setVersion(tmp.split("version=\"")[1].split("\"")[0]);
                            excelDto.setUpdateDate(updateString);
                            excelDto.setInterFaceJar(interfaceJar);
                            excelDto.setParamJar(paramJar);
                            excelDto.setInterfaceFunction(packageName + "." + interfaceTmp + "." + (s.split(" ")[1] + " " + s.split(" ")[2]).split(";")[0]);
                            excelDto.setReqName(s.split("\\(")[1].split(" ")[0]);
                            excelDto.setResName(s.split(" ")[0]);
                            try {
                                excelDto.setReqList(queryParam(excelDto.getReqName(),paramPathList));
                                excelDto.setResList(queryParam(excelDto.getResName(),paramPathList));
                            } catch (IOException e) {
                                throw new UtilsException(e.toString());
                            }
                            res.add(excelDto);
                        }
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
                boolean a = (!file.contains(" class "))||(!file.contains("/[*][*]"))||(!file.contains("private"))||(!file.contains(";"));
                String[] paramList = file.split(" class ")[1].split("/[*][*]");
                for (String s1 : paramList) {
                    n++;
                    if (s1.contains("private static final long")){
                        continue;
                    }
                    if (!s1.contains("private")||!s1.contains(";")){
                        continue;
                    }
                    String prefix = "";
                    String suffix = "";
                    ParamDto paramDto = new ParamDto();
                    paramDto.setName(s1.split("private ")[1].split(" ")[1].split(";")[0]);
                    paramDto.setType(s1.split("private ")[1].split(" ")[0]);
                    if (paramDto.getType().contains("List<")){
                        paramDto.setType(paramDto.getType().split("List<")[1].split(">")[0]);
                        suffix = "n]";
                    }else {
                        suffix = "1]";
                    }
                    if (s1.contains("@ParamElement(required=true)")){
                        prefix = "[1";
                    }else {
                        prefix = "[0";
                    }
                    paramDto.setAttribute(prefix + "…" + suffix);
                    if (s1.contains("[*]")){
                        paramDto.setRemark(s1.split("[*]")[1].split("\n")[0]);
                    }
                    res.add(paramDto);
                }
            }
        }
        return res;
    }

    public static void main(String[] args) {
        String interfacePath = "D:\\Developer\\WorkSpace\\IntelliJIDEA\\settlementcenter2\\com.xinfengtech.fund.interfaces\\src\\main\\java\\com\\xinfengtech\\fund\\interfaces\\service\\inneraccount\\InnerAccountService.java";
        String paramPath = "D:\\Developer\\WorkSpace\\IntelliJIDEA\\settlementcenter2\\com.xinfengtech.fund.schema\\src\\main\\java\\com\\xinfengtech\\fund\\schema\\inneraccount";
        String interfaceJar = "com.xinfengtech.fund.interfaces";
        String paramJar = "com.xinfengtech.control.schema";
        List<ExcelDto> res = queryExcelList(interfacePath,paramPath,interfaceJar,paramJar);
        System.out.println();
    }
}
