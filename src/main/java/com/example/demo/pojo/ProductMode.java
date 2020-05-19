package com.example.demo.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.*;


/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductMode extends BaseRowModel {
    
    @ExcelProperty(index = 0)
    private String stationCode;

    @ExcelProperty(index = 1)
    private String posCode;

    @ExcelProperty(index = 2)
    private String firstProdProp;

    @ExcelProperty(index = 3)
    private String secondProdProp;

    @ExcelProperty(index = 4)
    private String thirdProdProp;

    @ExcelProperty(index = 5)
    private String fourthProdProp;
}
