package com.jyl.frame.dao.impl;


import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.beans.factory.annotation.Autowired;

import com.jyl.frame.constants.BaseErrorCode;
import com.jyl.frame.dao.IBaseDAO;
import com.jyl.frame.excption.helper.ExcpHelper;
import com.jyl.frame.excption.helper.ExcpHelper.Condition;
import com.jyl.frame.excption.helper.ValiContainer;
import com.jyl.frame.excption.utils.ExcpResultUtil;
import com.jyl.frame.shqlutils.HqlFormatterUtil;
import com.jyl.frame.shqlutils.HqlFormatterUtil.Restriction;
import com.jyl.frame.shqlutils.ReflectionUtil;
import com.jyl.frame.vo.PropertyHelper;
import com.jyl.frame.vo.QueryInfoHelper;
import com.jyl.util.empty.EmptyCheckUtil;
/**
 * <p>功能描述</br>封装Hibernate原生的API,不适用hibernateTemplate,直接操纵 Hibernate 原生API,对hibernate的一些
 *    基本的CRUD操作进行了简单的封装，并提供了多种查询方式，同时支持自定义的HQL语句和SQL语句
 *  </p>
 * @className BaseHibernateDAO
 * @author jiangyu
 * @date 2016年3月17日 下午6:08:45
 * @param <T> DAO操作的对象类型，也就是对应的实体类型
 * @param <PK> 主键类型
 * @version v1.0
 * @since v1.0
 */
@SuppressWarnings("unchecked")
public abstract class BaseHibernateDAO<T, PK extends Serializable> implements IBaseDAO<T, PK>
{
    /** 升序 */
    private static final String ASC = "asc";

    /** 降序 */
    private static final String DESC = "desc";

    @Autowired
    public SessionFactory sessionFactory;

    protected Class<T> entityClass;

    /**
     * <p>无参构造器</p>
     */
    public BaseHibernateDAO()
    {
        this.entityClass = ReflectionUtil.getClassGenricType(getClass());
    }
    /**
     * <p>有参构造器</p>
     * @param sessionFactory session工厂
     * @param entityClass 实体类
     */
    public BaseHibernateDAO(final SessionFactory sessionFactory, final Class<T> entityClass)
    {
        super();
        this.sessionFactory = sessionFactory;
        this.entityClass = entityClass;
    }

    @Override
    public T save(T entity)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(entity, BaseErrorCode.ENTITY_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).validate();
        this.currentSession().saveOrUpdate(entity);
        return entity;
    }

    @Override
    public T saveOrUpdate(T entity)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(entity, BaseErrorCode.ENTITY_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).validate();
        currentSession().saveOrUpdate(entity);
        return entity;
    }
    
    @Override
    public void update(final T entity){
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(entity, BaseErrorCode.ENTITY_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).validate();
        currentSession().update(entity);
    }
    
    @Override
    public void batchSave(List<T> entities)
    {
        ValiContainer.getInstance().registerExcep(
            new ExcpHelper(entities, BaseErrorCode.ENTITY_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).validate();
        int i = 0;
        for (T entity : entities)
        {
            currentSession().saveOrUpdate(entity);
            i++;
            if (i%20==0)
            {
                flush();
                clear();
            }
        }
    }

    @Override
    public T find(PK id)
    {
        // 错误编码待定
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(id, BaseErrorCode.ID_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).validate();
        /** 防止出现对象找不到异常  **/
        return (T)currentSession().get(entityClass, id);
    }

    @Override
    public void delete(T entity)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(entity, BaseErrorCode.ENTITY_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).validate();
        currentSession().delete(entity);
    }

    @Override
    public void delete(PK id)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(id, BaseErrorCode.ID_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).validate();
        delete(find(id));
    }

    @Override
    public List<T> find(Collection<PK> idList)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(idList, BaseErrorCode.IDCOLLECCTION_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate();
        return find(Restrictions.in(getIdName(), idList));
    }

    @Override
    public List<T> findAll()
    {
        return find();
    }
    

    @Override
    public List<T> findAllIsAsc(String orderProperty, boolean isAsc)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(orderProperty, BaseErrorCode.PROPERTYNAME_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate();
        Criteria criteria = createCriteria();
        if (isAsc)
        {
            criteria.addOrder(Order.asc(orderProperty));
        }
        else
        {
            criteria.addOrder(Order.desc(orderProperty));
        }
        return criteria.list();
    }

    @Override
    public List<T> findOrderedRecord(String orderProperty, String orderWay)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(orderProperty, BaseErrorCode.PROPERTYNAME_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).registerExcep(
            new ExcpHelper(orderWay, BaseErrorCode.ORDER_WAY_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate();
        Criteria criteria = createCriteria();
        if (ASC.equalsIgnoreCase(orderWay))
        {
            criteria.addOrder(Order.asc(orderProperty));
        }
        else if (DESC.equalsIgnoreCase(orderWay))
        {
            criteria.addOrder(Order.desc(orderProperty));
        }
        else
        {
            // 排序方式不存在
            ValiContainer.getInstance().throwDaoExcp(BaseErrorCode.ORDER_WAY_NOT_EXIST_CODE);
        }
        return criteria.list();
    }

    @Override
    public List<T> findByProperty(String propertyName, Object value)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(propertyName, BaseErrorCode.PROPERTYNAME_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate();
        Criterion criterion = Restrictions.eq(propertyName, value);
        return find(criterion);
    }

    @Override
    public T findUniqueByProperty(String propertyName, Object value)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(propertyName, BaseErrorCode.PROPERTYNAME_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate();
        Criterion criterion = Restrictions.eq(propertyName, value);
        return (T)createCriteria(criterion).uniqueResult();
    }
    
    @Override
    public T findUniqueByProperties(Criterion... criterions)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(criterions, BaseErrorCode.PROPERTYNAME_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate();
        return (T)createCriteria(criterions).uniqueResult();
    }
    
    @Override
    public List<T> findByProperties(Map<String, Object> params)
    {
        if (EmptyCheckUtil.isEmpty(params))
        {
            return findAll();
        }else {
            Criterion[] criterions = new Criterion[params.size()];
            Set<Map.Entry<String, Object>> entrySet = params.entrySet();
            Iterator<Map.Entry<String, Object>> iter = entrySet.iterator();
            int i = 0;
            while (iter.hasNext())
            {
                Map.Entry<String, Object> entry = iter.next();
                criterions[i]=Restrictions.eq(entry.getKey(), entry.getValue());
                i++;
            }
            return find(criterions);
        }
    }
    
    @Override
    public T findUniqueRecordByProperties(Map<String, Object> params)
    {
        if (EmptyCheckUtil.isEmpty(params))
        {
            List<T> records = findAll();
            if (EmptyCheckUtil.isEmpty(records))
            {
                return null;
            }else {
                return records.get(0);
            }
        }else {
            Criterion[] criterions = new Criterion[params.size()];
            Set<Map.Entry<String, Object>> entrySet = params.entrySet();
            Iterator<Map.Entry<String, Object>> iter = entrySet.iterator();
            int i = 0;
            while (iter.hasNext())
            {
                Map.Entry<String, Object> entry = iter.next();
                criterions[i]=Restrictions.eq(entry.getKey(), entry.getValue());
                i++;
            }
            return findUniqueByProperties(criterions);
        }
    }

    @Override
    public List<T> findIsAscRecordByProperties(String orderField,boolean isAsc,Map<String, Object> params)
    {
        if (EmptyCheckUtil.isEmpty(params))
        {
            return findAllIsAsc(orderField, isAsc);
        }else {
            Criterion[] criterions = new Criterion[params.size()];
            Set<Map.Entry<String, Object>> entrySet = params.entrySet();
            Iterator<Map.Entry<String, Object>> iter = entrySet.iterator();
            int i = 0;
            while (iter.hasNext())
            {
                Map.Entry<String, Object> entry = iter.next();
                criterions[i]=Restrictions.eq(entry.getKey(), entry.getValue());
                i++;
            }
            Criteria criteria = createCriteria(criterions);
            if (isAsc)
            {
                criteria.addOrder(Order.asc(orderField));
            }
            else
            {
                criteria.addOrder(Order.desc(orderField));
            }
            return criteria.list();
        }
    }

    @Override
    public <X> List<X> find(String hql, Object... values)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(hql, BaseErrorCode.HQL_OR_SQL_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).registerExcep(
            new ExcpHelper(values, BaseErrorCode.PRE_PARAMS_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate();
        return createQuery(hql, values).list();
    }

    @Override
    public <X> List<X> find(String hql, Map<String, ?> values)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(hql, BaseErrorCode.HQL_OR_SQL_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).registerExcep(
            new ExcpHelper(values, BaseErrorCode.PRE_PARAMS_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate();
        return createQuery(hql, values).list();
    }
    

    @Override
    public <X> X findUnique(String hql, Object... values)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(hql, BaseErrorCode.HQL_OR_SQL_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).registerExcep(
            new ExcpHelper(values, BaseErrorCode.PRE_PARAMS_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate();
        return (X)createQuery(hql, values).uniqueResult();
    }

    @Override
    public int batchExecute(String hql, Object... values)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(hql, BaseErrorCode.HQL_OR_SQL_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).registerExcep(
            new ExcpHelper(values, BaseErrorCode.PRE_PARAMS_EMPTY_OR_NULL_CODE,
                Condition.EMPTY))
            .validate();
        return createQuery(hql, values).executeUpdate();
    }

    @Override
    public int batchExecute(String hql, Map<String, ?> values)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(hql, BaseErrorCode.HQL_OR_SQL_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).registerExcep(
            new ExcpHelper(values, BaseErrorCode.PRE_PARAMS_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate();
        return createQuery(hql, values).executeUpdate();
    }

    @Override
    public <X> X findUnique(String hql, Map<String, ?> values)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(hql, BaseErrorCode.HQL_OR_SQL_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).registerExcep(
            new ExcpHelper(values, BaseErrorCode.PRE_PARAMS_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate();
        return (X)createQuery(hql, values).uniqueResult();
    }

    @Override
    public Query createQuery(String hql, Object[] values)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(hql, BaseErrorCode.HQL_OR_SQL_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).registerExcep(
            new ExcpHelper(values, BaseErrorCode.PRE_PARAMS_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate();
        Query query = currentSession().createQuery(hql);
        if (values != null)
        {
            for (int i = 0; i < values.length; i++ )
            {
                query.setParameter(String.valueOf(i), values[i]);
            }
        }
        return query;
    }

    @Override
    public Query createQuery(String hql, Map<String, ?> values)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(hql, BaseErrorCode.HQL_OR_SQL_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).registerExcep(
            new ExcpHelper(values, BaseErrorCode.PRE_PARAMS_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate();
        Query query = currentSession().createQuery(hql);
        
        if (values != null&&values.size()>0)
        {
            
            query.setProperties(values);
        }
        return query;
    }

    @Override
    public Query createQuery(String hql)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(hql, BaseErrorCode.HQL_OR_SQL_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).validate();
        Query query = currentSession().createQuery(hql);
        return query;
    }

    @Override
    public List<T> find(Criterion... criterions)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(criterions, BaseErrorCode.CRITERIA_PARAMS_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate();
        return createCriteria(criterions).list();
    }

    @Override
    public Criteria createCriteria(Criterion... criterions)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(criterions, BaseErrorCode.CRITERIA_PARAMS_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate();
        Criteria criteria = currentSession().createCriteria(entityClass);
        for (Criterion criterion : criterions)
        {
            criteria.add(criterion);
        }
        return criteria;
    }

    @Override
    public SQLQuery createSQLQuery(String sql, Object... values)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(sql, BaseErrorCode.HQL_OR_SQL_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).registerExcep(
            new ExcpHelper(values, BaseErrorCode.PRE_PARAMS_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate();
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        if (values != null)
        {
            for (int i = 0; i < values.length; i++ )
            {
                sqlQuery.setParameter(String.valueOf(i), values[i]);
            }
        }
        return sqlQuery;
    }

    @Override
    public SQLQuery createSQLQuery(String sql, Map<String, ?> values)
    {
        ValiContainer.getInstance().clear().registerExcep(sql, BaseErrorCode.HQL_OR_SQL_EMPTY_OR_NULL_CODE, Condition.EMPTY,"")
                /*.registerExcep(
            new ExcpHelper(values, BaseErrorCode.PRE_PARAMS_EMPTY_OR_NULL_CODE,
                Condition.EMPTY))*/
                .validate();
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        if (values != null&&values.size()>0)
        {
            sqlQuery.setProperties(values);
        }
        return sqlQuery;
    }

    @Override
    public SQLQuery createSQLQuery(String sql)
    {
        ValiContainer.getInstance().clear().registerExcep(
            new ExcpHelper(sql, BaseErrorCode.HQL_OR_SQL_EMPTY_OR_NULL_CODE, 
                Condition.EMPTY)).validate();
        SQLQuery sqlQuery = currentSession().createSQLQuery(sql);
        return sqlQuery;
    }

    /**
     * <p> 功能描述：查询实体对象中的部分属性信息</p>
     * @author  jiangyu
     * @date  2016年3月17日 下午11:29:33
     * @param target 转换后的目标对象class
     * @param src 原始实体类对象class
     * @return List<X> 满足条件的集合
     * @since V1.0
     */
    protected <X> List<X> partInfoQuery(Class<X> target, Class<?> src)
    {

        StringBuffer hql = formatHqlQuery(target, src);

        List<X> list = createQuery(hql.toString()).list();

        return list;
    }
    
    /**
     * <p> 功能描述：查询实体对象中的部分属性信息，附带限制条件</p>
     * @author  jiangyu
     * @date  2016年3月17日 下午11:28:02
     * @param target  转换后的目标对象class
     * @param src 原始实体类对象class
     * @param properties 附带限制条件的信息
     * @return List<X> 满足条件的集合
     * @since V1.0
     */
    protected <X> List<X> partInfoRestrictQuery(Class<X> target, Class<?> src,
                                                Set<PropertyHelper> properties)
    {
        StringBuffer hql = formatHqlQuery(target, src);
        List<X> list = null;
        if (!EmptyCheckUtil.isEmpty(properties))
        {
            hql.append(" WHERE 1=1 ");
            QueryInfoHelper helper = constructHqlWithPropertyHelper(properties);
            hql.append(helper.getHsql());
            list = createQuery(hql.toString(), helper.getParams()).list();
        }
        else
        {
            list = partInfoQuery(target,src);
        }
        return list;
    }
    
    private QueryInfoHelper constructHqlWithPropertyHelper(Set<PropertyHelper> properties)
    {
        String hqlStr = "";
        String orderByStr = "";
        Map<String, Object> values = new ConcurrentHashMap<String, Object>();
        for (PropertyHelper property : properties)
        {
            /** 是否支持排序策略   added by jiangyu 20160227 **/
            Boolean autoAsc = property.getAutoOrderAsc();
            if (!EmptyCheckUtil.isEmpty(autoAsc))
            {
                if (autoAsc)
                {
                    /** 升序  **/
                    orderByStr += ","+property.getPropertyName()+" "+ASC;
                }else {
                    /** 降序  **/
                    orderByStr += ","+property.getPropertyName()+" "+DESC;
                }
                if (EmptyCheckUtil.isEmpty(property.getValue()))
                {
                    continue;
                }
            }
            
            ValiContainer.getInstance().clear()
            .registerExcep(new ExcpHelper(property.getValue(),BaseErrorCode.PROPERTYVALUE_EMPTY_OR_NULL_CODE,Condition.EMPTY))
            .validate();
            /** 组织hql语句  **/
            if (Restriction.EQ_OR.equals(property.getRes()))
            {
                Map<String, Object> valueMap = (Map<String, Object>)property.getValue();
                int i=0;
                hqlStr+= " AND (";
                for (Map.Entry<String, Object> entry : valueMap.entrySet())
                {
                    ++i;
                    values.put("va"+i, entry.getValue());
                    hqlStr +=property.getPropertyName()+"=:"+("va"+i)+" OR ";
                }
                hqlStr+=" 1=2 ) ";
                continue;
            }
            else {
                values.put(property.getPropertyName(), property.getValue());
                hqlStr += HqlFormatterUtil.getInstance().clear().addRestrict(property.getPropertyName(),property.getRes()).format();
            }
        }
        /** 追加排序的hql语句  **/
        if (orderByStr.length()>0)
        {
            hqlStr += " ORDER BY "+orderByStr.substring(1);
        }
        return new QueryInfoHelper(hqlStr,values);
    }
    
    

    /**
     * <p> 功能描述：部分查询对象属性基本的hql语句组织</p>
     * @author  jiangyu
     * @date  2016年3月17日 下午11:30:13
     * @param target 目标对象Class
     * @param src 源对象Class
     * @return StringBuffer 组织好的hql语句的StringBuffer对象
     * @since V1.0
     */
    private StringBuffer formatHqlQuery(Class<?> target, Class<?> src)
    {
        ValiContainer.getInstance().clear()
        /** 目标对象class为空校验 **/
        .registerExcep(
            new ExcpHelper(target, BaseErrorCode.PART_QUERY_TARGET_CLASSS_EMPTY_OR_NULL_CODE,
                Condition.EMPTY))
        /** 源对象class为空校验 **/
        .registerExcep(
            new ExcpHelper(src, BaseErrorCode.PART_QUERY_SRC_CLASSS_EMPTY_OR_NULL_CODE,
                Condition.EMPTY)).validate();

        StringBuffer hql = new StringBuffer();
        hql.append(" SELECT new ").append(target.getName()).append("(");

        Field[] fields = target.getDeclaredFields();
        if (fields != null && fields.length > 0)
        {
            String filedsStr = "";
            for (Field field : fields)
            {
                filedsStr += field.getName() + ",";
            }
            hql.append(filedsStr.substring(0, filedsStr.length() - 1));
        }

        hql.append(")");

        hql.append(" FROM ").append(src.getName());
        return hql;
    }

    
    @Override
    public int deleteRecord(String propertyName, Object propertyValue)
    {
        StringBuffer hql = new StringBuffer();
        ValiContainer.getInstance().clear()
        /** 查询属性{0}的值为空 **/
        .registerExcep(propertyValue, BaseErrorCode.METHOD_GET_WAY_PARAMETER_EMPTY,Condition.EMPTY);
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append(basicDeleteHql())
        .append(propertyName)
        .append(Restriction.EQ.getOperator())
        .append(":")
        .append(propertyName);
        params.put(propertyName, propertyValue);
        return batchExecute(hql.toString(), params);
    }

    @Override
    public int deleteRecordByProperties(Map<String, Object> values)
    {
        StringBuffer hql = new StringBuffer();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append(basicDeleteHql());
        if (!EmptyCheckUtil.isEmpty(values))
        {
            for (Map.Entry<String, Object> property : values.entrySet())
            {
                hql.append(property.getKey())
                .append(Restriction.EQ.getOperator())
                .append(":")
                .append(property.getKey());
                params.put(property.getKey(), property.getValue());
            }
        }
        return batchExecute(hql.toString(), params);
    }
    
    @Override
    public int deleteRecordWithOutProperty()
    {
        StringBuffer hql = basicDeleteHql();
        return batchExecute(hql.toString());
    }

    @Override
    public int deleteRecordWithCondition(Set<PropertyHelper> propertyHelpers)
    {
        /** 组织基本的删除语句  **/
        StringBuffer hql = basicDeleteHql();
        
        if (!EmptyCheckUtil.isEmpty(propertyHelpers))
        {
            /** 组织约束条件语句  **/
            QueryInfoHelper helper = constructHqlWithPropertyHelper(propertyHelpers);
            hql.append(helper.getHsql());
            return batchExecute(hql.toString(), helper.getParams());
        }else {
            /** 不支持这样的操作 **/
            ExcpResultUtil.failSys(BaseErrorCode.NOT_SUPPORT_DELETE_OPERATE);
        }
        
        return 0;
    }

    /**
     * <p> 功能描述：取得当前的session对象</p>
     * @author  jiangyu
     * @date  2016年3月17日 下午11:30:45
     * @return Session 返回值为当前的session对象
     * @since V1.0
     */
    protected Session currentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    protected Session openSession()
    {
        return sessionFactory.openSession();
    }

    /**
     * <p> 功能描述：获取对象主键的名称</p>
     * @author  jiangyu
     * @date  2016年3月17日 下午11:31:09
     * @return String 返回值为主键的名称
     * @since V1.0
     */
    protected String getIdName()
    {
        ClassMetadata meta = sessionFactory.getClassMetadata(entityClass);
        return meta.getIdentifierPropertyName();
    }

    /**
     * <p> 功能描述：flush当前的Sesssion</p>
     * @author  jiangyu
     * @date  2016年3月17日 下午11:31:32
     * @since V1.0
     */
    protected void flush()
    {
        currentSession().flush();
    }
    /**
     * <p> 功能描述：clear当前的Sesssion</p>
     * @author  jiangyu
     * @date  2016年3月17日 下午11:31:44
     * @since V1.0
     */
    protected void clear()
    {
        currentSession().clear();
    }
    /**
     * <p> 功能描述：基本的删除Hql语句</p>
     * @author  jiangyu
     * @date  2016年3月17日 下午11:31:57
     * @return StringBuffer 不带任何条件的删除Hql
     * @since V1.0
     */
    protected StringBuffer basicDeleteHql()
    {
        StringBuffer hql = new StringBuffer();
        hql.append(" DELETE FROM ")
        .append(entityClass.getName())
        .append(" WHERE 1=1 ");
        return hql;
    }
}
