package com.example.demo.service;

import com.example.demo.pojo.ProductMode;

import java.util.List;

/**
 * @author F
 */
public interface ExcelService {
    /**
     * 从excel导入添加商品
     *
     * @param data
     * @return
     */
    List<ProductMode> importExcel(List<Object> data);
}
