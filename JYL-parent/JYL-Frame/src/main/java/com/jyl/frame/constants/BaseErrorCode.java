package com.jyl.frame.constants;

/**
 * <p>功能描述:</br> 错误编码的常量 </p>
 * @className  BaseErrorCode
 * @author  jiangyu
 * @date  2016年4月3日 下午6:24:23
 * @version  v1.0
 */
public interface BaseErrorCode
{
    /** 主键id为空 */
    String ID_EMPTY_OR_NULL_CODE = "000001";

    /** 实体对象为空 */
    String ENTITY_EMPTY_OR_NULL_CODE = "000002";

    /** 主键集合为空 */
    String IDCOLLECCTION_EMPTY_OR_NULL_CODE = "000003";

    /** 属性名称为空 */
    String PROPERTYNAME_EMPTY_OR_NULL_CODE = "000004";

    /** 查询语句为空 */
    String HQL_OR_SQL_EMPTY_OR_NULL_CODE = "000005";

    /** 预编译参数值容器为空 */
    String PRE_PARAMS_EMPTY_OR_NULL_CODE = "000006";

    /** 查询参数criterions内容为空 */
    String CRITERIA_PARAMS_EMPTY_OR_NULL_CODE = "000007";

    /** 排序方式错误 */
    String ORDER_WAY_NOT_EXIST_CODE = "000008";

    /** 排序方式为空 */
    String ORDER_WAY_EMPTY_OR_NULL_CODE = "000009";

    /** 分页对象为空 */
    String PAGE_OBJ_EMPTY_OR_NULL_CODE = "000010";

    /** 分页的页码小于或等于0 */
    String PAGE_NO_LESS_THAN_ZERO_CODE = "000011";

    /** 预编译参数不匹配 */
    String PRE_PARAM_COUNT_NOT_MATCH_CODE = "000012";

    /** 查询接口Query为空 */
    String QUERY_INTERFACE_EMPTY_OR_NULL_CODE = "000013";

    /** 查询接口Criteria为空 */
    String CRITERIA_INTERFACE_EMPTY_OR_NULL_CODE = "000014";

    /** 属性名称对应的值为空 */
    String PROPERTYVALUE_EMPTY_OR_NULL_CODE = "000015";

    /** 查询条件的限制为空 **/
    String QUERY_RESTRICTION_EMPTY_OR_NULL_CODE = "000016";

    /** hql部分查询属性目标类class为空 **/
    String PART_QUERY_TARGET_CLASSS_EMPTY_OR_NULL_CODE = "000017";

    /** hql部分查询属性原始类class为空 **/
    String PART_QUERY_SRC_CLASSS_EMPTY_OR_NULL_CODE = "000018";

    /** 反射机制调用方法异常之【没有找到方法】 **/
    String REFLECTION_INVOKE_METHOD_NO_METHOD_ERROR = "000019";

    /** 反射机制调用方法异常之【没有权限访问方法】 **/
    String REFLECTION_INVOKE_METHOD_NO_AUTHORTY_ERROR = "000020";

    /** 反射机制调用方法异常之【调用private方法】 **/
    String REFLECTION_INVOKE_PRIVATE_METHOD_ERROR = "000021";

    /** 反射机制调用方法异常之【参数不合法或不正确】 **/
    String REFLECTION_INVOKE_METHOD_WITH_WRONG_PARAMS_ERROR = "000022";

    /** 反射机制调用方法异常之【调用目标方法异常】 **/
    String REFLECTION_INVOKE_METHOD_TARGET_ERROR = "000023";

    /** 请求参数{0}不存在 **/
    String METHOD_GET_WAY_PARAMETER_EMPTY = "000024";

    /** 查询属性{0}的值为空 **/
    String QUERY_PROPERTY_VALUE_IS_EMPTY = "000025";

    /** 不支持的删除操作 **/
    String NOT_SUPPORT_DELETE_OPERATE = "000026";

    /** {0}对象为空 **/
    String OBJ_IS_EMPTY = "000027";

    /** 非空约束:{0}不能为空 **/
    String VALIDATE_NULLABLE = "000028";

    /** 长度约束:{0}长度不能超过{1} **/
    String VALIDATE_MAX_LENGTH = "000029";

    /** 长度约束:{0}长度不能小于{1} **/
    String VALIDATE_MIN_LENGTH = "000030";

    /** 字符内容约束:{0}不能含有特殊字符 **/
    String CAN_NOT_CONTAINS_SPECIAL_CHARACTER = "000031";

    /** 字符内容约束:{0}不能含有中文字符 **/
    String CAN_NOT_CONTAINS_CHINESE_CHARACTER = "000032";

    /** 地址约束:{0}地址格式不正确 **/
    String ADDRESS_FORMAT_IS_INVALID = "000033";

    /** 数据约束:{0}不是有效的数字 **/
    String IS_NOT_VALID_NUMBER = "000034";

    /** 手机号码约束:{0}不是有效的手机号码 **/
    String IS_NOT_VALIDATE_PHONE_NUM = "000035";

    /** 格式约束:{0}格式不正确 **/
    String VALUE_FORMAT_IS_INVALID = "000036";

    /** 不支持此种类型的校验 **/
    String DO_NOT_SUPPORT_THIS_TYPE_VALIDATE = "000037";
}
