package com.gzw.daomain;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gujian on 2017/10/11.
 */
@Data
public class SecKill {

    private Integer id;

    private String comName;

    private BigDecimal comPrice;

    private Integer storageCount;

    private String createTime;

    private String startTime;

    private String endTime;

}
