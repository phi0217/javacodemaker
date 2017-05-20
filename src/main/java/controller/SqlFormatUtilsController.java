package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import utils.CommonConstants;
import utils.CommonUtils;
import utils.MkdirsAndFile;
import utils.SqlFormat;


/**
 * Created by Phi on 2017/4/17.
 */
@Controller
public class SqlFormatUtilsController {
    @RequestMapping(value = "/toSql")
    public String toMapper() {
        return "sqlFormat";
    }

    @RequestMapping(value = "/sqlFormat")
    public String sqlFormat(String path, String mapperName, String dtoName,String sql,String anno, ModelMap model) {
        if (path == null || mapperName == null || dtoName == null || sql == null){
            model.addAttribute("msg","缺少参数");
            return "sqlFormat";
        }

        sql = CommonUtils.textFormat(sql);

        MkdirsAndFile.mkdirsForSql(path);

        String javaBeanParam = "";
        if (anno != null && !anno.trim().equals("")){
            //注释不为空
            javaBeanParam = "public class " + dtoName + "{" + "\n" + SqlFormat.toJavaBeanHaveText(sql,anno) +"}\n";//生成javabean字段
        }else{
            javaBeanParam = "public class " + dtoName + "{" + "\n" + SqlFormat.toJavaBean(sql) +"}\n";//生成javabean字段
        }

        String mapperParam = SqlFormat.toMapper(sql)+"\n\n"+SqlFormat.toCommonSql(sql);//生成mapper字段
        String otherParam = SqlFormat.toEtcParam(sql);

        MkdirsAndFile.fileMaker(javaBeanParam,dtoName,".java",path + CommonConstants.THE_BASE_PATH  + CommonConstants.DTO_PATH);
        MkdirsAndFile.fileMaker(mapperParam,mapperName,".xml",path + CommonConstants.THE_BASE_PATH  + CommonConstants.SQLMAP_PATH);
        MkdirsAndFile.fileMaker(otherParam,"other",".txt",path + CommonConstants.THE_BASE_PATH  + CommonConstants.OTHER_PATH);

        String res = javaBeanParam + mapperParam;
        model.addAttribute("res",res);
        model.addAttribute("path",path);
        model.addAttribute("mapperName",mapperName);
        model.addAttribute("dtoName",dtoName);
        model.addAttribute("sql",sql);
        model.addAttribute("anno",anno);
        model.addAttribute("msg","生成成功");
        return "sqlFormat";
    }
}
