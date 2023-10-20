package com.teenager.content.model.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Xue
 * @create 2023-03-26-9:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhysiologyData {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer heartRate;//每分钟次数

    private BigDecimal bloodHighPressure;//mmHg

    private BigDecimal bloodLowPressure;//mmHg

    private Integer bloodOxygen;//xx%

    private BigDecimal temperature;//摄氏度

    private BigDecimal sleepTime;//h

    private Integer steps;

    private Integer exerciseTime;

    private  BigDecimal distance;

    private Integer heat;

    private Integer climb;

    private Long userId;

    private Date createTime;
}
