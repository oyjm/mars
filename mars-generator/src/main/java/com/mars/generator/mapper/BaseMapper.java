package com.mars.generator.mapper;

import java.util.List;
import java.util.Map;

/**
 * Mapper 基础接口
 *
 */
public interface BaseMapper {
    List<Map<String, Object>> queryList(Map<String, Object> map);

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);
}
