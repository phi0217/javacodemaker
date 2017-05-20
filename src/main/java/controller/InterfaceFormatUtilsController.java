package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import utils.InterfaceFormat;

import java.util.List;

/**
 * Created by Phi on 2017/5/9.
 */
@Controller
public class InterfaceFormatUtilsController {

    @RequestMapping(value = "/toInterfaceFormat")
    public String toMapper(){
        return "interfaceFormat";
    }
    @RequestMapping(value = "/interfaceFormat")
    public String interfaceFormat(String interfaceText, ModelMap model){
        List<String> methodList = InterfaceFormat.toCommonMethodList(interfaceText);
        String res = "";
        for (String s : methodList) {
            res = res + s +"\n";
        }
        model.addAttribute("res",res);
        model.addAttribute("interfaceText",interfaceText);
        return "interfaceFormat";
    }
}
