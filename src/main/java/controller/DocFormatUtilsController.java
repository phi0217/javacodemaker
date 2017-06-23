package controller;

import dto.ExcelDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static utils.DocFormat.*;
import static utils.ExcelMaker.makeExcel;

/**
 * Created by Phi on 2017/6/15.
 */
@Controller
public class DocFormatUtilsController {
    @RequestMapping(value = "/toDoc")
    public String toDoc(){
        return "docFormat";
    }

    @RequestMapping(value = "/doc")
    public String doc(String interfacePath, String paramPath, String interfaceJar, String paramJar, String path, ModelMap modelMap){

        List<ExcelDto> excelDtos = queryExcelList(interfacePath, paramPath, interfaceJar, paramJar);


        makeExcel(excelDtos, path + "workbook.xls");
        String validateNotNull = validateNotNullUtil(excelDtos);
        String nameList = nameListMaker(excelDtos);

        String res = "【方法名语句,以method为原料生成使用】\n\n" + nameList + "\n" + "【Manager入参非空判断语句】\n" + validateNotNull;

        modelMap.addAttribute("interfacePath",interfacePath);
        modelMap.addAttribute("res",res);
        modelMap.addAttribute("paramPath",paramPath);
        modelMap.addAttribute("interfaceJar",interfaceJar);
        modelMap.addAttribute("paramJar",paramJar);
        modelMap.addAttribute("path",path);
        modelMap.addAttribute("msg","生成成功");
        return "docFormat";
    }
}
