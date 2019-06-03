package com.hlx.sell.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * @param <T>
 */
@Data
public class ResultVo<T> implements Serializable {

    private static final long serialVersionUID = -1378904466186205297L;
    private Integer code;
    private String msg;
    private T Data;

}
