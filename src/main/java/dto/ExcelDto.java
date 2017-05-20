package dto;

import java.util.List;

/**
 * Created by Phi on 2017/5/20.
 */
public class ExcelDto {
    private String functionCode;//功能号
    private String functionName;//功能名
    private String cnName;//功能中文名
    private String version;//版本号
    private String updateDate;//更新日期
    private String interFaceJar;//接口jar
    private String paramJar;//参数jar
    private String businessDescribe;//业务描述
    private String interfaceFunction;//接口方法
    private String reqName;
    private String resName;

    private List<ParamDto> reqList;//入参列表
    private List<ParamDto> resList;//出参列表

    public String getInterfaceFunction() {
        return interfaceFunction;
    }

    public void setInterfaceFunction(String interfaceFunction) {
        this.interfaceFunction = interfaceFunction;
    }

    public String getReqName() {
        return reqName;
    }

    public void setReqName(String reqName) {
        this.reqName = reqName;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public List<ParamDto> getReqList() {
        return reqList;
    }

    public void setReqList(List<ParamDto> reqList) {
        this.reqList = reqList;
    }

    public List<ParamDto> getResList() {
        return resList;
    }

    public void setResList(List<ParamDto> resList) {
        this.resList = resList;
    }

    public String getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(String functionCode) {
        this.functionCode = functionCode;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getCnName() {
        return cnName;
    }

    public void setCnName(String cnName) {
        this.cnName = cnName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getInterFaceJar() {
        return interFaceJar;
    }

    public void setInterFaceJar(String interFaceJar) {
        this.interFaceJar = interFaceJar;
    }

    public String getParamJar() {
        return paramJar;
    }

    public void setParamJar(String paramJar) {
        this.paramJar = paramJar;
    }

    public String getBusinessDescribe() {
        return businessDescribe;
    }

    public void setBusinessDescribe(String businessDescribe) {
        this.businessDescribe = businessDescribe;
    }
}
