package com.hlx.sell.exception;

import com.hlx.sell.eums.ResultEnum;
import lombok.Getter;

import javax.persistence.criteria.CriteriaBuilder;
import javax.ws.rs.GET;

@Getter
public class SellException extends RuntimeException{

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }
    public SellException(Integer code,String message){
        super(message);
        this.code=code;
    }
}
