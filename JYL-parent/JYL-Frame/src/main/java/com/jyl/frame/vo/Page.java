package com.jyl.frame.vo;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>功能描述</br> 分页类 ，与具体ORM实现无关的分页参数及查询结果封装.</p>
 * @className Page
 * @author jiangyu
 * @date 2016年3月17日 下午10:26:46
 * @version v1.0
 * @since v1.0
 */
public class Page<T>
{
    /**
     * 升序
     */
    public static final String ASC = "asc";

    /**
     * 降序
     */
    public static final String DESC = "desc";

    /**
     * 当前页
     */
    protected Integer pageNo = 1;

    /**
     * 每页显示的条数
     */
    protected Integer pageSize = -1;

    /**
     * 排序字段
     */
    protected String orderBy = null;

    /**
     * 排序方式
     */
    protected String order = null;

    /**
     * 是否自动查询总数
     */
    protected boolean autoCount = true;

    /**
     * 查询的记录
     */
    protected List<T> list = new ArrayList<>();

    /**
     * 总的记录条数
     */
    protected Long totalCount = -1L;
    
    /**
     * db系统时间
     */
    protected Long sysTime;

    public Page()
    {}

    public Page(Integer pageSize)
    {
        this.pageSize = pageSize;
    }

    public Page(Integer pageNo, Integer pageSize, String orderBy, String order)
    {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.orderBy = orderBy;
        this.order = order;
    }

    public int getPageNo()
    {
        return pageNo;
    }

    public void setPageNo(final int pageNo)
    {
        this.pageNo = pageNo;
        if (pageNo < 1)
        {
            this.pageNo = 1;
        }
    }

    public Page<T> pageNo(final int pageNo)
    {
        setPageNo(pageNo);
        return this;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(final int pageSize)
    {
        this.pageSize = pageSize;
    }

    public Page<T> pageSize(final int pageSize)
    {
        setPageSize(pageSize);
        return this;
    }

    public int getFirst()
    {
        return ((pageNo - 1) * pageSize) + 1;
    }

    public String getOrderBy()
    {
        return orderBy;
    }

    public void setOrderBy(final String orderBy)
    {
        this.orderBy = orderBy;
    }

    public Page<T> orderBy(final String theOrderBy)
    {
        setOrderBy(theOrderBy);
        return this;
    }

    public String getOrder()
    {
        return order;
    }

    public void setOrder(final String order)
    {
        String lowerCaseOrder = StringUtils.lowerCase(order);
        // 检查order字符串的合法值
        String[] orders = StringUtils.split(lowerCaseOrder, ',');
        for (String orderStr : orders)
        {
            if (!StringUtils.equals(DESC, orderStr) && !StringUtils.equals(ASC, orderStr))
            {
                throw new IllegalArgumentException("order method " + orderStr + "is incorrect");
            }
        }
        this.order = lowerCaseOrder;
    }

    public Page<T> order(final String theOrder)
    {
        setOrder(theOrder);
        return this;
    }

    public boolean isOrderBySetted()
    {
        return (StringUtils.isNotBlank(orderBy) && StringUtils.isNotBlank(order));
    }

    public boolean isAutoCount()
    {
        return autoCount;
    }

    public void setAutoCount(final boolean autoCount)
    {
        this.autoCount = autoCount;
    }

    public List<T> getList()
    {
        return list;
    }

    public void setList(final List<T> list)
    {
        this.list = list;
    }

    public Long getTotalCount()
    {
        return totalCount;
    }

    public void setTotalCount(final long totalCount)
    {
        this.totalCount = totalCount;
    }

    /**
     * <p> 功能描述：计算总的页数</p>
     * @author jiangyu
     * @date 2016年3月17日 下午10:27:06
     * @return long 总的页数
     * @since v1.0
     */
    public long getTotalPages()
    {
        if (totalCount < 0)
        {
            return -1;
        }
        long count = totalCount / pageSize;
        if (totalCount % pageSize > 0)
        {
            count++ ;
        }
        return count;
    }

    /**
     * <p> 功能描述：是否有下一页</p>
     * @author jiangyu
     * @date 2016年3月17日 下午10:27:27
     * @return boolean 是否有下一页
     * @since v1.0
     */
    public boolean isHasNext()
    {
        return (pageNo + 1 <= getTotalPages());
    }

    /**
     * <p> 功能描述：是否有上一页</p>
     * @author jiangyu
     * @date 2016年 3月17日 下午10:27:41
     * @return boolean
     * @since v1.0
     */
    public boolean isHasPre()
    {
        return (pageNo - 1 >= 1);
    }

    /**
     * <p> 功能描述：获取前一页</p>
     * @author jiangyu
     * @date 2016年3月17日 下午10:27:55
     * @return int 前一页页码
     * @since v1.0
     */
    public int getPrePage()
    {
        if (isHasPre())
        {
            return pageNo - 1;
        }
        else
        {
            return pageNo;
        }
    }

    public Long getSysTime()
    {
        return sysTime;
    }

    public void setSysTime(Long sysTime)
    {
        this.sysTime = sysTime;
    }
}
