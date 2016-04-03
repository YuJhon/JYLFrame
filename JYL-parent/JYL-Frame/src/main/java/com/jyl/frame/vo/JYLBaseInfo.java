package com.jyl.frame.vo;

/**
 * <p>功能描述</br> 分页查询的时候前台传入的参数</p>
 * @className JifennBaseInfo
 * @author jiangyu
 * @date 2016年3月17日 下午10:26:28
 * @version v1.0
 * @since v1.0
 */
public class JYLBaseInfo
{
    /**
     * 分页大小
     */
    private Integer pageSize = 10;

    /**
     * 当前页
     */
    private Integer pageNo = 1;

    public JYLBaseInfo()
    {}

    public Integer getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public Integer getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
}
