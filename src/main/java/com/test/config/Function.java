package com.test.config;

import lombok.Data;

/**
 * @author wyj
 * @date 2018/10/20
 */
@Data
public class Function {
    /**
     * sql类型，设定在xml读取时有四种情况
     */
    private String sqlType;
    /**
     * 方法名
     */
    private String funcName;
    /**
     * 执行的sql语句
     */
    private String sql;
    /**
     * 返回值类型
     */
    private Object resultType;
    /**
     * 参数类型
     */
    private String parameterType;
}
