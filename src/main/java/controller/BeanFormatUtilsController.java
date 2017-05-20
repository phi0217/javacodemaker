package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import utils.BeanFormat;

/**
 * Created by Phi on 2017/4/19.
 */
@Controller
public class BeanFormatUtilsController {
    @RequestMapping(value = "/toBean")
    public String toBean(){
        System.out.println();
        return "beanFormat";
    }

    @RequestMapping(value = "/bean")
    public String bean(String targetFormat, String srcTest, ModelMap model){
        if (targetFormat == null || srcTest == null){
            model.addAttribute("msg","缺少参数");
            return "beanFormat";
        }
        String res = BeanFormat.beanFormat(targetFormat.trim(),srcTest.trim());

        model.addAttribute("res",res);
        model.addAttribute("targetFormat",targetFormat);
        model.addAttribute("srcTest",srcTest);
        model.addAttribute("msg","生成成功");
        return "beanFormat";
    }

}
