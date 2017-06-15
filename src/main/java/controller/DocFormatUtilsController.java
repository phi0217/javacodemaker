package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import static utils.DocFormat.docmaker;

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
        docmaker(interfacePath,paramPath,interfaceJar,paramJar,path);
        modelMap.addAttribute("interfacePath",interfacePath);
        modelMap.addAttribute("paramPath",paramPath);
        modelMap.addAttribute("interfaceJar",interfaceJar);
        modelMap.addAttribute("paramJar",paramJar);
        modelMap.addAttribute("path",path);
        modelMap.addAttribute("msg","生成成功");
        return "docFormat";
    }
}
