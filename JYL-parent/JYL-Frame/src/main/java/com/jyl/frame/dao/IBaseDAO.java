package com.jyl.frame.dao;


import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;

import com.jyl.frame.vo.JYLBaseInfo;
import com.jyl.frame.vo.Page;
import com.jyl.frame.vo.PropertyHelper;


/**
 * <p>: dao层抽象的接口<br/> </p>
 * @className IBaseDAO
 * @author jiangyu
 * @date 2016年3月17日 下午10:52:20
 * @version v1.0
 * @since v1.0
 * @param <T>
 *            DAO操作的对象类型，也就是对应的实体类型
 * @param <PK>
 *            主键类型
 */
public interface IBaseDAO<T, PK extends Serializable>
{
    /**
     * <p> 功能描述：新增记录</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:19:51
     * @param entity 实体对象
     * @return T 返回值为主键
     * @version  v1.0
     * @since v1.0
     * @see T
     */
    public T save(final T entity);

    /**
     * <p> 功能描述：更新和新增记录</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:20:23
     * @param entity 要保存和更新的实体
     * @return T 实体记录
     * @version  v1.0
     * @since v1.0
     * @see T
     */
    public T saveOrUpdate(final T entity);
    
    /**
     * <p> 功能描述：更新记录</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:20:40
     * @param entity 要更新操作的实体
     * @version  v1.0
     */
    public void update(final T entity);
    
    /**
     * <p> 功能描述：批量新增记录</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:31:21
     * @param list 实体对象集合
     * @version  v1.0
     */
    public void batchSave(final List<T> list);

    /**
     * <p> 功能描述：通过主键查询记录</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:31:42
     * @param id 主键值 PK
     * @return T 返回对应的实体
     * @version v1.0
     * @since v1.0
     * @see T
     */
    public T find(final PK id);

    /**
     * <p> 功能描述：删除记录</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:32:05
     * @param entity 传入实体类型参数
     * @version  v1.0
     * @since v1.0
     */
    public void delete(final T entity);

    /**
     * <p> 功能描述：通过主键删除</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:32:27
     * @param id 主键值
     * @version  v1.0
     * @since v1.0
     */
    public void delete(final PK id);

    /**
     * <p> 功能描述：通过主键list查询记录</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:32:45
     * @param idList 主键集合
     * @return List<T> 返回值为满足条件的实体List集合
     * @version  v1.0
     * @since v1.0
     */
    public List<T> find(final Collection<PK> idList);

    /**
     * <p> 功能描述：查询全部</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:33:06
     * @return List<T> 返回值为所有的实体List集合
     * @version  v1.0
     * @since v1.0
     */
    public List<T> findAll();

    /**
     * <p> 功能描述：支持排序查询所有记录</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:33:24
     * @param orderProperty 排序的属性名称
     * @param isAsc  是否是升序 \n true:升序 \n false:降序
     * @return List<T> 返回值为所有的记录按照排序方式的List集合
     * @version  v1.0
     * @since v1.0
     */
    public List<T> findAllIsAsc(String orderProperty, boolean isAsc);

    /**
     * <p> 功能描述：支持排序查询所有记录</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:33:48
     * @param orderProperty 排序的属性名称
     * @param orderWay  asc 升序  desc 降序
     * @return List<T> 返回值为所有的记录按照排序方式的List集合
     * @version  v1.0
     * @since v1.0
     */
    public List<T> findOrderedRecord(String orderProperty, String orderWay);

    /**
     * <p> 功能描述：按属性查找，匹配方式为相等<br></p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:34:14
     * @param propertyName 属性名称
     * @param value 属性对应的值
     * @return List<T> 返回值为满足查询条件值的List集合
     * @version  v1.0
     * @since v1.0
     */
    public List<T> findByProperty(final String propertyName, final Object value);

    /**
     * <p> 功能描述：通过属性，查询唯一的对象，匹配方式为相等</p>
     * @author jiangyu
     * @date 2016年3月17日 下午10:58:13
     * @param propertyName 属性名称
     * @param value 属性值 \n 如果属性名称为别的关联实体，请在传入参数值的时候务必传入实体对象
     * @return T 返回值为满足属性值筛选的唯一记录
     * @version v1.0
     * @since v1.0
     */
    public T findUniqueByProperty(final String propertyName, final Object value);
    
    /**
     * <p> 功能描述：通过Criterion查询唯一的对象</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:35:04
     * @param criterions 查询条件
     * @return T 满足查询条件的唯一记录
     * @version  v1.0
     * @since v1.0
     */
    public T findUniqueByProperties(Criterion... criterions);

    /**
     * <p> 功能描述：自己写hql查询语句 灵活处理查询，参数类型为数组</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:35:23
     * @param hql 自定义hql
     * @param values hql中对应的预编译参数对应的值
     * @return List<X> 返回值为满足查询条件的List集合,返回值不局限于当前的实体，可以是Object,其中X是自定义类型
     * @version  v1.0
     * @since v1.0
     */
    public <X> List<X> find(final String hql, final Object... values);

    /**
     * <p> 功能描述：自定义hql语句，参数封装在Map中</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:36:32
     * @param hql 自定义hql
     * @param values hql中对应的占位的预编译参数对应的值
     * @return List<X> 返回值为满足查询条件的List集合,返回值不局限于当前的实体，可以是Object,其中X是自定义类型
     * @version  v1.0
     * @see v1.0
     */
    public <X> List<X> find(final String hql, final Map<String, ?> values);

    /**
     * <p> 功能描述：根据Hql语句查询唯一结果,参数为数组</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:36:55
     * @param hql 自定义hql
     * @param values hql中对应的预编译参数对应的值
     * @return X 返回值为满足查询条件的唯一对象 ,返回值不局限于当前的实体，可以是Object
     * @version  v1.0
     * @since v1.0
     */
    public <X> X findUnique(final String hql, final Object... values);

    /**
     * <p> 功能描述：执行HQL语句进行删除和更新操作,参数为数组</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:37:21
     * @param hql 自定义hql
     * @param values hql中对应的预编译参数对应的值
     * @return 返回值为DML操纵所影响的记录数
     * @version  v1.0
     * @since v1.0
     */
    public int batchExecute(final String hql, final Object... values);

    /**
     * <p> 功能描述：执行hql语句进行删除和更新操作，参数为Map</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:37:46
     * @param hql 自定义hql
     * @param values hql中对应的预编译参数对应的值
     * @return int 返回值为DML操纵所影响的记录数
     * @version  v1.0
     * @since v1.0
     */
    public int batchExecute(final String hql, final Map<String, ?> values);

    /**
     * <p> 功能描述：根据Hql语句查询唯一结果,参数为Map</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:38:48
     * @param hql 自定义hql
     * @param values hql中对应的预编译参数对应的值
     * @return X 返回值为满足查询条件的唯一对象 ,返回值不局限于当前的实体，可以是Object
     * @version  v1.0
     * @since v1.0
     */
    public <X> X findUnique(final String hql, final Map<String, ?> values);

    /**
     * <p> 功能描述：执行hql返回query接口</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:39:41
     * @param hql 自定义hql
     * @param values hql中对应的预编译参数对应的值
     * @return Query 返回值为组装好参数的Query接口
     * @version  v1.0
     * @since v1.0
     */
    public Query createQuery(String hql, Object[] values);

    /**
     * <p> 功能描述：使用map封装查询的参数</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:40:09
     * @param hql 自定义hql
     * @param values 返回值为组装好参数的Query接口
     * @return Query 返回值为组装好参数的Query接口
     * @version  v1.0
     * @since v1.0
     */
    public Query createQuery(final String hql, final Map<String, ?> values);

    /**
     * <p> 功能描述：不需要参数的查询</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:40:37
     * @param hql 自定义hql
     * @return Query 返回值为Query接口
     * @version  v1.0
     * @since v1.0
     */
    public Query createQuery(final String hql);

    /**
     * <p> 功能描述：按Criteria查询对象List列表.</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:40:59
     * @param criterions 数量可变的Criterion.
     * @return List<T> 返回值为满足条件的List结果结合
     * @version  v1.0
     * @since v1.0
     */
    public List<T> find(final Criterion... criterions);

    /**
     * <p> 功能描述：组装Criteria接口，封装查询条件到Criteria接口中</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:42:03
     * @param criterions 查询条件的数组
     * @return Criteria 封装好查询条件的Criteria接口
     * @version  v1.0
     * @since v1.0
     */
    public Criteria createCriteria(final Criterion... criterions);

    // ==================================================================
    // = 提供sql查询接口执行的方式，以满足hql语句不能灵活执行执行 =
    // ==================================================================

    /**
     * <p> 功能描述：根据Sql执行特定的查询，参数封装在数组中</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:42:56
     * @param sql 自定义SQL
     * @param values sql中的预编译参数对应的值
     * @return SQLQuery 返回值为组装好查询条件参数的SQLQuery接口
     * @version  v1.0
     * @since v1.0
     */
    public SQLQuery createSQLQuery(final String sql, final Object... values);

    /**
     * <p> 功能描述：根据sql执行特定的查询，参数封装在map中</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:43:26
     * @param sql 自定义SQL
     * @param values sql中的预编译参数对应的值
     * @return SQLQuery 返回值为组装好查询条件参数的SQLQuery接口
     * @version  v1.0
     * @since v1.0
     */
    public SQLQuery createSQLQuery(final String sql, final Map<String, ?> values);

    /**
     * <p> 功能描述：根据sql执行特定的查询</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:44:11
     * @param sql 自定义SQL
     * @return SQLQuery 返回值为SQLQuery接口
     * @version  v1.0
     * @since v1.0
     */
    public SQLQuery createSQLQuery(final String sql);
    
    /**
     * <p> 功能描述：sql分页查询数据</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:44:42
     * @param info 分页参数信息 ，子类中一般都需要继承JifennBaseInfo类
     * @param sql 自定义sql语句，可以带预编译的参数
     * @param params 对应sql语句预编译参数的值
     * @param formatter 回调接口，对查询出来的分页结果进行转换成X类型的数据模型，如果不需要转换可以传入null,需要转换的就需要实现接口中的format方法
     * 接口参考：{@link com.jifenn.framework.frame.dao.PageFormatCallable}
     * @return Page 封装好的分页结果  {@link com.jifenn.framework.frame.vo.Page}
     * @version  v1.0
     * @since v1.0
     */
    public <X> Page<X> pageSqlQuery(JYLBaseInfo info,String sql,Map<String, Object> params,PageFormatCallable<X> formatter);
    
    /**
     * <p> 功能描述：多个属性查询</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:45:42
     * @param params 条件参数
     * @return List<T> 满足条件的结果集合
     * @version  v1.0
     * @since v1.0
     */
    public List<T> findByProperties(Map<String,Object> params);
    
    /**
     * <p> 功能描述：多个属性查询[目前只支持等于的条件]</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:46:05
     * @param params 条件参数
     * @return T 满足查询条件的结果
     * @version  v1.0
     * @since v1.0
     * @see T
     */
    public T findUniqueRecordByProperties(Map<String,Object> params);
    
    /**
     * <p> 功能描述：多个属性查询+是否排序   目前只支持当个字段的排序</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:46:33
     * @param orderField 排序的字段
     * @param isAsc 是否是升序
     * @param params 条件参数
     * @return List<T> 满足查询条件的结果集合
     * @version  v1.0
     * @since v1.0
     */
    public List<T> findIsAscRecordByProperties(String orderField,boolean isAsc,Map<String,Object> params);
    
    /**
     * <p> 功能描述：删除记录【根据单一属性匹配：条件为相等】</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:47:03
     * @param propertyName 属性名称
     * @param propertyValue 属性对应的值
     * @return int 返回所受影响的结果的记录数
     * @version  v1.0
     * @since v1.0
     */
    public int deleteRecord(String propertyName,Object propertyValue);
    
    /**
     * <p> 功能描述：删除记录【多个属性相等的匹配方式】</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:47:31
     * @param values 对应的属性集合
     * @return int 返回所受影响的记录
     * @version  v1.0
     * @since v1.0
     */
    public int deleteRecordByProperties(Map<String, Object> values);
    
    /**
     * <p> 功能描述：不带属性约束的删除</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:47:52
     * @return int 返回所受影响的记录
     * @version  v1.0
     * @since v1.0
     */
    public int deleteRecordWithOutProperty();
    
    /**
     * <p> 功能描述：删除记录[包含复合的约束条件]</p>
     * @author jiangyu
     * @date 2016年3月17日 下午6:48:09
     * @param propertyHelpers 属性约束条件{@link com.jifenn.framework.frame.vo.PropertyHelper }
     * @return int 返回所受影响的记录
     * @version  v1.0
     * @since v1.0
     */
    public int deleteRecordWithCondition(Set<PropertyHelper> propertyHelpers);
    
}
