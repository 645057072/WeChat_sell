package com.hlx.sell.handler;



import com.hlx.sell.VO.ResultVo;
import com.hlx.sell.config.ProjectUrlConfig;
import com.hlx.sell.eums.ResultEnum;
import com.hlx.sell.exception.SellException;
import com.hlx.sell.exception.SellerAuthorizeException;
import com.hlx.sell.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellerExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;


    @ExceptionHandler(value = SellerAuthorizeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    ModelAndView handlerAuthorizeException(){
        return new ModelAndView("redircet:"
        .concat(projectUrlConfig.getWxOpenAuthorsizeUrl())
                .concat("/sell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(projectUrlConfig.getSell()));
    }


//     捕获异常
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVo handlerSellException(SellException e){
        return ResultUtils.error(e.getCode(),e.getMessage());
    }
}
