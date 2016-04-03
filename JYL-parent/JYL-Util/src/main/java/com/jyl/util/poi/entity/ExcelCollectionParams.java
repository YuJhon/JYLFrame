package com.jyl.util.poi.entity;


import java.util.Map;


/**
 * <p>功能描述:</br> Excel 对于的 Collection</p>
 * 
 * @className ExcelCollectionParams
 * @author jiangyu
 * @date 2016年4月3日 下午5:18:22
 * @version v1.0
 */
public class ExcelCollectionParams
{

    /**
     * 集合对应的名称
     */
    private String name;

    /**
     * 实体对象
     */
    private Class<?> type;

    /**
     * 这个list下面的参数集合实体对象
     */
    private Map<String, ExcelImportEntity> excelParams;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Class<?> getType()
    {
        return type;
    }

    public void setType(Class<?> type)
    {
        this.type = type;
    }

    public Map<String, ExcelImportEntity> getExcelParams()
    {
        return excelParams;
    }

    public void setExcelParams(Map<String, ExcelImportEntity> excelParams)
    {
        this.excelParams = excelParams;
    }
}
