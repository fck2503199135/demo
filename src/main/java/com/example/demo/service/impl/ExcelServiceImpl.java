package com.example.demo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.pojo.ProductMode;
import com.example.demo.service.ExcelService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author F
 */
@Service
public class ExcelServiceImpl implements ExcelService {
    /**
     * Excel导入
     *
     * @author fan
     */
    @Override
    public List<ProductMode> importExcel(List<Object> data) {
        List<ProductMode> list = new ArrayList<>();
        for (Object object : data) {
            String jsonString = JSONObject.toJSONString(object);
            JSONObject jsonObject = JSONObject.parseObject(jsonString);
            ProductMode productMode = JSONObject.toJavaObject(jsonObject, ProductMode.class);
            list.add(productMode);
        }
        return list;
    }
}
