package com.jyl.util.poi.entity;


import java.util.Comparator;


/**
 * <p>功能描述:</br> 按照升序排序</p>
 * 
 * @className ComparatorExcelField
 * @author jiangyu
 * @date 2016年4月3日 下午5:17:58
 * @version v1.0
 */
public class ComparatorExcelField implements Comparator<ExcelExportEntity>
{

    public int compare(ExcelExportEntity prev, ExcelExportEntity next)
    {
        return prev.getOrderNum() - next.getOrderNum();
    }

}
