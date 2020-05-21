package com.example.demo.controller;

import com.example.demo.common.RespBean;
import com.example.demo.pojo.ProductMode;
import com.example.demo.service.ExcelService;
import com.example.demo.utils.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fan
 */
@Api(tags = "Excel导入")
@Slf4j
@RestController
public class ExcelImportController {

    @Resource
    ExcelService excelService;

    /**
     * Excel导入
     *
     * @author fan
     */
    @ApiOperation("Excel导入")
    @PostMapping(value = "importExcel")
    @ResponseBody
    public RespBean importExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<Object> data = ExcelUtil.readExcel(file, new ProductMode());
            assert data != null;
            if (data.isEmpty()) {
                return RespBean.error("导入失败，Excel的数据不正确");
            }
            log.info("[readExcel]导入excel添加商品  size=>{}", data.size());
            List<ProductMode> productModeList = excelService.importExcel(data);
            return RespBean.ok("导入成功", productModeList);
        } catch (Exception e) {
            e.printStackTrace();
            return RespBean.error("导入失败,系统错误");
        }
    }
}
