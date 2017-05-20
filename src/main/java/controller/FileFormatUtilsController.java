package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Phi on 2017/5/20.
 */
@Controller
public class FileFormatUtilsController {
    @RequestMapping(value = "/toFileFormat")
    public String toFile(){
        return "fileFormat";
    }
    @RequestMapping(value = "/fileFormat")
    public String fileFormat(String path, String interfacePath, String paramPath,String interfaceJar,String paramJar, ModelMap modelMap){

        return "fileFormat";
    }
}
