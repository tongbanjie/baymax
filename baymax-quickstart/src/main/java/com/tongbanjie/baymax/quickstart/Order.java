package com.tongbanjie.baymax.quickstart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* Created by Tpa-Generator
* 
*/
public class Order implements Serializable {

private static final long serialVersionUID = 1L;

    // 
    private Integer id;
    // 
    private String productId;
    // 
    private String productName;
    // 
    private BigDecimal amount;
    // 
    private Integer userId;
    // 
    private Date createTime;
    // 
    private Date modifyTime;

}