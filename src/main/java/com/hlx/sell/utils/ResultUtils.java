package com.hlx.sell.utils;

import com.hlx.sell.VO.ResultVo;

public class ResultUtils {

    public static ResultVo success(){
        return success();
    }
    public static ResultVo success(Object object){
        ResultVo resultVo=new ResultVo();
        resultVo.setCode(0);
        resultVo.setMsg("获取参数成功");
        resultVo.setData(object);
        return resultVo;
    }

    public static ResultVo error(Integer code, String msg){
        ResultVo resultVo=new ResultVo();
        resultVo.setCode(code);
        resultVo.setMsg(msg);
        return resultVo;
    }
}
