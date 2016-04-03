package com.jyl.frame.dao.impl;


import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;

import com.jyl.frame.constants.BaseErrorCode;
import com.jyl.frame.dao.PageFormatCallable;
import com.jyl.frame.excption.helper.ExcpHelper;
import com.jyl.frame.excption.helper.ExcpHelper.Condition;
import com.jyl.frame.excption.helper.ValiContainer;
import com.jyl.frame.shqlutils.ReflectionUtil;
import com.jyl.frame.vo.JYLBaseInfo;
import com.jyl.frame.vo.Page;

/**
 * <p>功能描述:</br> BaseHibernateDAO更上一层的封装，主要是为了适应分页的需求</p>
 * @className HibernateDAO
 * @author jiangyu
 * @date 2015年11月9日 上午10:36:22
 * @param <T>
 *            对应的实体
 * @param <PK>
 *            对应实体的主键
 * @version v1.0
 * @since v1.0
 */
@SuppressWarnings("unchecked")
public class HibernateDAO<T, PK extends Serializable> extends BaseHibernateDAO<T, PK>
{

    /**
     * 无参构造函数
     */
    public HibernateDAO()
    {
        super();
    }

    /**
     * <p> 功能描述：功能描述： 按分页的方式查询所有的记录</p>
     * @author  jiangyu
     * @date  2016年3月17日 下午10:40:06
     * @param page 分页辅助类,请参考{@link com.jifenn.framework.frame.vo.Page}
     * @return Page 分页辅助类,请参考{@link com.jifenn.framework.frame.vo.Page}
     * @since V1.0
     * @see com.jifenn.framework.frame.vo.Page
     */
    public Page<T> getAll(final Page<T> page)
    {
        ValiContainer.getInstance().clear()
        .registerExcep(new ExcpHelper(page, BaseErrorCode.PAGE_OBJ_EMPTY_OR_NULL_CODE,Condition.EMPTY))
        .validate();
        return this.findByPage(page);
    }

    /**
     * <p> 功能描述：通过query接口实现分页查询(参数为数组形式传入)</p>
     * @author  jiangyu
     * @date 2016年3月17日 下午10:40:39
     * @param page 分页辅助类 ,请参考{@link com.jifenn.framework.frame.vo.Page}
     * @param hql 自定义hql语句
     * @param values hql语句中的参数，参数为数组形式
     * @return 存放结果的分页对象 Page ,请参考{@link com.jifenn.framework.frame.vo.Page}
     * @since v1.0
     * @see com.jifenn.framework.frame.vo.Page
     */
    protected Page<T> findPage(final Page<T> page, final String hql, final Object... values)
    {
        ValiContainer.getInstance().clear()
        .registerExcep(new ExcpHelper(page, BaseErrorCode.PAGE_OBJ_EMPTY_OR_NULL_CODE,Condition.EMPTY))
        .validate();
        
        Query query = this.createQuery(hql, values);
        if (page.isAutoCount())
        {
            long totalCount = this.countHqlResult(hql, values);
            page.setTotalCount(totalCount);
        }
        query = this.setPageParameter2Query(query, page);
        List<T> result = query.list();
        page.setList(result);
        return page;

    }
    
    /**
     * <p> 功能描述：分页查询记录(参数为Map形式传入)</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:13:30
     * @param page 分页辅助类
     * @param hql 自定义hql语句
     * @param values hql语句中的参数，参数为数组形式
     * @return Page 分页对象 page ,请参考{@link com.jifenn.framework.frame.vo.Page}
     * @since v1.0
     * @see com.jifenn.framework.frame.vo.Page
     */
    protected Page<T> findPage(final Page<T> page, final String hql, final Map<String, ?> values) {
        ValiContainer.getInstance().clear()
        .registerExcep(new ExcpHelper(page, BaseErrorCode.PAGE_OBJ_EMPTY_OR_NULL_CODE,Condition.EMPTY))
        .validate();

        Query q = this.createQuery(hql, values);

        if (page.isAutoCount()) {
            long totalCount = this.countHqlResult(hql, values);
            page.setTotalCount(totalCount);
        }

        this.setPageParameter2Query(q, page);

        List<T> result = q.list();
        page.setList(result);
        return page;
    }
    
    
    /**
     * <p> 功能描述：查询记录总数,参数为数组</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:14:57
     * @param hql 自定义hql语句
     * @param values hql预编译参数
     * @return long 记录总数
     * @since v1.0
     */
    public long countHqlResult(String hql, final Object... values)
    {
        ValiContainer.getInstance().clear()
        .registerExcep(new ExcpHelper(hql, BaseErrorCode.HQL_OR_SQL_EMPTY_OR_NULL_CODE,Condition.EMPTY))
        .registerExcep(new ExcpHelper(values, BaseErrorCode.PRE_PARAMS_EMPTY_OR_NULL_CODE,Condition.EMPTY))
        .validate();
        String countHql = this.prepareCountHql(hql);
        Long count = this.findUnique(countHql, values);
        return count;
    }

    /**
     * <p> 功能描述：查询记录总数,参数为Map</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:15:19
     * @param hql 自定义的hql语句
     * @param values hql语句中对应的参数
     * @return long 记录总数 
     * @since v1.0
     */
    public long countHqlResult(String hql, final Map<String, ?> values)
    {
        ValiContainer.getInstance().clear()
        .registerExcep(new ExcpHelper(hql, BaseErrorCode.HQL_OR_SQL_EMPTY_OR_NULL_CODE,Condition.EMPTY))
        .registerExcep(new ExcpHelper(values, BaseErrorCode.PRE_PARAMS_EMPTY_OR_NULL_CODE,Condition.EMPTY))
        .validate();
        String countHql = this.prepareCountHql(hql);
        Long count = this.findUnique(countHql, values);
        return count;
    }

    /**
     * <p> 功能描述：设置分页参数到query接口中</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:15:50
     * @param query query接口
     * @param page 分页辅助类 ,请参考{@link com.jifenn.framework.frame.vo.Page}
     * @return 分页接口
     * @see org.hibernat.Query
     */
    private Query setPageParameter2Query(Query query, Page<T> page)
    {
        ValiContainer.getInstance().clear()
        .registerExcep(new ExcpHelper(query, BaseErrorCode.QUERY_INTERFACE_EMPTY_OR_NULL_CODE,Condition.EMPTY))
        .registerExcep(new ExcpHelper(page, BaseErrorCode.PAGE_OBJ_EMPTY_OR_NULL_CODE,Condition.EMPTY))
        .validate();
        if (page.getFirst() > 0 && page.getPageSize() != -1)
        {
            query.setFirstResult(page.getFirst() - 1);
            query.setMaxResults(page.getPageSize());
        }
        return query;
    }

    /**
     * <p> 功能描述：准备查询总记录数的hql语句[注意：此方法实现得不是很好，后期进一步完善]</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:16:27
     * @param hql 自定义的hql语句
     * @return String 返回组织好的hql语句
     * @since v1.0
     */
    private String prepareCountHql(String hql)
    {
        ValiContainer.getInstance().clear()
        .registerExcep(new ExcpHelper(hql, BaseErrorCode.HQL_OR_SQL_EMPTY_OR_NULL_CODE,Condition.EMPTY))
        .validate();
        
        String fromHql = hql;
        String entityName = StringUtils.substringAfter(fromHql, "from");
        if ("".equals(entityName) || null == entityName)
        {
            entityName = StringUtils.substringAfter(fromHql, "FROM");
        }
        fromHql = "from " + entityName;
        fromHql = StringUtils.substringBefore(fromHql, "order by");
        String countHqlStr = "select count(*) " + fromHql;
        return countHqlStr;
    }

    /**
     * <p> 功能描述：分页查询数据，使用criterion方式</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:17:11
     * @param page 分页辅助类
     * @param criterions 查询条件
     * @return Page 分页对象  ,请参考{@link com.jifenn.framework.frame.vo.Page}
     * @since v1.0
     * @see com.jifenn.framework.frame.vo.Page
     */
    private Page<T> findByPage(Page<T> page, final Criterion... criterions)
    {
        ValiContainer.getInstance().clear()
        .registerExcep(new ExcpHelper(page, BaseErrorCode.PAGE_OBJ_EMPTY_OR_NULL_CODE,Condition.EMPTY))
        .validate();
        
        Criteria criteria = this.createCriteria(criterions);
        if (page.isAutoCount())
        {
            long totalCount = this.countCriteriaResult(criteria);
            page.setTotalCount(totalCount);
        }
        criteria = this.setPageParameter2Criteria(criteria, page);
        List<T> result = criteria.list();
        page.setList(result);
        return page;
    }

    /**
     * <p> 功能描述：设置分页参数到Criteria对象，辅助函数</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:17:35
     * @param criteria 查询接口
     * @param page 分页辅助对象   ,请参考{@link com.jifenn.framework.frame.vo.Page}
     * @return  Criteria
     * @since v1.0
     */
    private Criteria setPageParameter2Criteria(Criteria criteria, Page<T> page)
    {
        ValiContainer.getInstance().clear()
            .registerExcep(new ExcpHelper(criteria, BaseErrorCode.CRITERIA_INTERFACE_EMPTY_OR_NULL_CODE,Condition.EMPTY))
            .registerExcep(new ExcpHelper(page.getPageSize(),BaseErrorCode.PAGE_NO_LESS_THAN_ZERO_CODE ,Condition.LE_0))
            .validate();
        
        criteria.setFirstResult(page.getFirst() - 1);
        criteria.setMaxResults(page.getPageSize());

        if (page.isOrderBySetted())
        {
            String[] orderByArray = StringUtils.split(page.getOrderBy(), ',');
            String[] orderArray = StringUtils.split(page.getOrder(), ',');
            
            ValiContainer.getInstance()
            .registerExcep(new ExcpHelper(orderByArray.length == orderArray.length, BaseErrorCode.PRE_PARAM_COUNT_NOT_MATCH_CODE,Condition.NOT_MATCH))
            .validate();
            
            for (int i = 0; i < orderArray.length; i++ )
            {
                if (Page.ASC.equals(orderArray[i]))
                {
                    criteria.addOrder(Order.asc(orderByArray[i]));
                }
                else
                {
                    criteria.addOrder(Order.desc(orderByArray[i]));
                }
            }
        }
        return criteria;
    }

    /**
     * <p> 功能描述：查询满足条件的记录总数</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:18:09
     * @param criteria 查询条件
     * @return long 查询的结果的数量
     * @since v1.0
     */
    @SuppressWarnings("rawtypes")
    protected long countCriteriaResult(final Criteria criteria)
    {
        CriteriaImpl impl = (CriteriaImpl)criteria;
        Projection projection = impl.getProjection();
        ResultTransformer transformer = impl.getResultTransformer();
        List<CriteriaImpl.OrderEntry> orderEntries = null;
        
        orderEntries = (List)ReflectionUtil.getFieldValue(impl, "orderEntries");
        ReflectionUtil.setFieldValue(impl, "orderEntries", new ArrayList());
        
        // 执行count语句
        Long totalCountObject = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult();
        long totalCount = (totalCountObject != null) ? totalCountObject : 0;
        criteria.setProjection(projection);
        if (projection == null)
        {
            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        if (transformer != null)
        {
            criteria.setResultTransformer(transformer);
        }
        ReflectionUtil.setFieldValue(impl, "orderEntries", orderEntries);
        return totalCount;
    }
    
    
    @Override
    public <X> Page<X> pageSqlQuery(JYLBaseInfo info,String sql,Map<String, Object> params,PageFormatCallable<X> formatter)
    {
        /*
        Page page = new Page();
        Query query = super.createSQLQuery(sql, params);
        query.setFirstResult((info.getPageNo()-1)*info.getPageSize()).setMaxResults(info.getPageSize());

        String countField = countFiled(sql, " as ", " AS ");
        if (CollectionUtil.isEmpty(countField))
        {
            countField = "*";
        }
        
        String conditionSql = StringUtils.substringAfter(sql, "from");
        if ("".equals(conditionSql) || null == conditionSql)
        {
            conditionSql = StringUtils.substringAfter(sql, "FROM");
        }
        *//** don't need order **//*
        conditionSql = StringUtils.substringBefore(StringUtils.substringBefore(conditionSql, "order by"), "ORDER BY");
        *//** don't need group **//*
        conditionSql = StringUtils.substringBefore(StringUtils.substringBefore(conditionSql, "group by"), "GROUP BY");
        
        String countHqlStr = "SELECT COUNT("+countField+") FROM " + conditionSql;
        
        Query countQuery = createSQLQuery(countHqlStr, params);
        BigInteger recordCount = (BigInteger)countQuery.uniqueResult();
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Object> list = query.list();
        Long totalCount = recordCount==null?0L:recordCount.longValue();
        page.setTotalCount(totalCount);
        page.setPageNo(info.getPageNo());
        page.setPageSize(info.getPageSize());
        if (formatter!=null)
        {
            page.setList(formatter.format(list));
        }else {
            page.setList(list);
        }
        return page;
        */
        
        return pageComplexSqlQuery(info,sql,params,formatter);
    }
    
    @SuppressWarnings("unused")
    private static String countFiled(String sql, String... str)
    {
        String backSql = sql;
        backSql = backSql.toLowerCase().trim();
        if (sql.indexOf(",") > -1)
        {
            int tx = sql.indexOf(",");
            sql = sql.substring(6, tx);
        }
        else
        {
            int fx = backSql.indexOf(" from ");
            sql = sql.substring(6, fx);
        }
        if (str != null && str.length > 0)
        {
            for (String replaceVal : str)
            {
                sql = StringUtils.substringBefore(sql, replaceVal);
            }
        }
        return sql.trim();
    }

    @SuppressWarnings("rawtypes")
    private <X> Page<X> pageComplexSqlQuery(JYLBaseInfo info, String sql,
                                           Map<String, Object> params,
                                           PageFormatCallable<X> formatter)
    {
        Page page = new Page();
        Query query = super.createSQLQuery(sql, params);
        query.setFirstResult((info.getPageNo()-1)*info.getPageSize()).setMaxResults(info.getPageSize());

        String countHqlStr = "SELECT COUNT(1) FROM (" + sql+") roundtable";
        
        Query countQuery = createSQLQuery(countHqlStr, params);
        BigInteger recordCount = (BigInteger)countQuery.uniqueResult();
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Object> list = query.list();
        Long totalCount = recordCount==null?0L:recordCount.longValue();
        page.setTotalCount(totalCount);
        page.setPageNo(info.getPageNo());
        page.setPageSize(info.getPageSize());
        if (formatter!=null)
        {
            page.setList(formatter.format(list));
        }else {
            page.setList(list);
        }
        /** 查询DB的时间  **/
        page.setSysTime(getSysTime().getTime());
        
        return page;
    }
    
    /**
     * <p> 功能描述：查询DB服务器的时间</p>
     * @author  jiangyu
     * @date  2016年3月17日 下午10:51:27
     * @return Date 数据库中当前的时间
     * @since V1.0
     */
    protected Date getSysTime() {
        String sql = "select now()";
        return (Date) currentSession().createSQLQuery(sql).uniqueResult();
    }
}
