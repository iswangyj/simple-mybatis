package com.test.config;

import lombok.Data;

import java.util.List;

/**
 * @author wyj
 * @date 2018/10/20
 */
@Data
public class MapperBean {
    /**
     * 接口名
     */
    private String interfaceName;

    /**
     * 接口下方法列表
     */
    private List<Function> list;
}
