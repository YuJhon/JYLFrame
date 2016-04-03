package com.jyl.frame.vo;


import java.util.Map;

/**
 * <p>功能描述</br> 查询条件的帮助类</p>
 * @className QueryInfoHelper
 * @author jiangyu
 * @date 2016年3月17日 下午10:29:42
 * @version v1.0
 * @since v1.0
 */
public class QueryInfoHelper
{
    /**
     * sql或者hql
     */
    private String hsql = "";
    /**
     * 对应的属性的查询参数
     */
    private Map<String, Object> params;

    public QueryInfoHelper()
    {}

    public QueryInfoHelper(String hsql, Map<String, Object> params)
    {
        this.hsql = hsql;
        this.params = params;
    }

    public String getHsql()
    {
        return hsql;
    }

    public void setHsql(String hsql)
    {
        this.hsql = hsql;
    }

    public Map<String, Object> getParams()
    {
        return params;
    }

    public void setParams(Map<String, Object> params)
    {
        this.params = params;
    }

}
