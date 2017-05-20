package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import utils.CommonConstants;
import utils.CommonUtils;
import utils.MethodFormat;
import utils.MkdirsAndFile;

import java.util.List;

/**
 * Created by Phi on 2017/4/17.
 */
@Controller
public class MethodFormatUtilsController {

    @RequestMapping(value = "/toMethod")
    public String toMapper(){
        return "methodFormat";
    }

    @RequestMapping(value = "/toStandard")
    public String toStandard(){
        return "methodStandardFormat";
    }

    @RequestMapping(value = "/method")
    public String method(String path, String coreName, String method, ModelMap model){
        if (path == null || coreName == null || method == null ){
            model.addAttribute("msg","缺少参数");
            return "methodFormat";
        }

        method = CommonUtils.textFormat(method);
        String res = "";
        try{
            res = maker(path, coreName, method);
        }catch (Exception e){
            model.addAttribute("msg",e.toString());
            return "methodFormat";
        }

        model.addAttribute("res",res);
        model.addAttribute("msg","生成成功");
        model.addAttribute("path",path);
        model.addAttribute("coreName",coreName);
        model.addAttribute("method",method);
        model.addAttribute("model",model);
        return "methodFormat";
    }

    @RequestMapping(value = "/standard")
    public String standard(String path, String coreName,  ModelMap model){
        String res = "";
        String method = MethodFormat.toTheMethodList(coreName);

        if (path == null || coreName == null){
            model.addAttribute("msg","缺少参数");
            return "methodStandardFormat";
        }

        method = CommonUtils.textFormat(method);

        try{
            res = maker(path, coreName, method);
        }catch (Exception e){
            model.addAttribute("msg",e.toString());
            return "methodStandardFormat";
        }
        model.addAttribute("res",res);
        model.addAttribute("msg","生成成功");
        model.addAttribute("path",path);
        model.addAttribute("coreName",coreName);
        model.addAttribute("method",method);
        model.addAttribute("model",model);
        return "methodStandardFormat";
    }

    /*生成方法*/
    private static String maker(String path, String coreName, String method){
        String res = "";

        List<String> methodList = MethodFormat.toCommonMethodList(method);

        String manager = MethodFormat.toCommonManager(coreName,methodList);
        String managerImpl = MethodFormat.toCommonManagerImpl(coreName,methodList);
        String service = MethodFormat.toCommonService(coreName,methodList);
        String serviceImpl = MethodFormat.toCommonServiceImpl(coreName,methodList);
        String controller = MethodFormat.toCommonController(methodList);
        List<List<String>> reqList = MethodFormat.toReq(methodList);
        List<List<String>> respList = MethodFormat.toResp(methodList);


        MkdirsAndFile.mkdirsForMethod(path,coreName);


        MkdirsAndFile.fileMaker(manager,coreName + "Manager",".java",path + CommonConstants.THE_BASE_PATH + CommonConstants.MANAGER_PATH + "/" + coreName);
        MkdirsAndFile.fileMaker(managerImpl,coreName + "ManagerImpl",".java",path + CommonConstants.THE_BASE_PATH + CommonConstants.MANAGER_PATH + "/" + coreName + "/impl");
        MkdirsAndFile.fileMaker(service,coreName + "Service",".java",path + CommonConstants.THE_BASE_PATH + CommonConstants.INTERFACES_PATH + "/" + coreName);
        MkdirsAndFile.fileMaker(serviceImpl,coreName + "ServiceImpl",".java",path + CommonConstants.THE_BASE_PATH + CommonConstants.SERVICE_PATH + "/" + coreName);

        for (List<String> s : reqList) {
            MkdirsAndFile.fileMaker(s.get(1),s.get(0),".java",path + CommonConstants.THE_BASE_PATH + CommonConstants.SCHEMA_PATH + "/" + coreName + "/req");
        }
        for (List<String> s : respList) {
            MkdirsAndFile.fileMaker(s.get(1),s.get(0),".java",path + CommonConstants.THE_BASE_PATH + CommonConstants.SCHEMA_PATH + "/" + coreName + "/resp");
        }

        res = manager +"\n\n"+ managerImpl +"\n\n"+ service +"\n\n"+ serviceImpl +"\n\n" + controller;
        return res;
    }
}
