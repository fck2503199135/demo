package com.example.demo.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 */
public class ExcelListener extends AnalysisEventListener {

    /**
     * 可以通过实例获取该值
     * 自定义用于暂时存储data
     */
    private List<Object> datas = new ArrayList<>();

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }

    /**
     * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
     */
    @Override
    public void invoke(Object object, AnalysisContext analysisContext) {
        //数据存储到list，供批量处理，或后续自己业务逻辑处理。
        datas.add(object);
        //根据业务自行 do something
        doSomething();
        //如数据过大,可以进行定量分批处理
/*        if (datas.size() <= 100) {
            datas.add(object);
        } else {
            doSomething();
            datas = new ArrayList<Object>();
        }*/
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    /*
            datas.clear();
            解析结束销毁不用的资源
         */

    }

    /**
     * 根据业务自行实现该方法
     */
    private void doSomething() {
    }


}
