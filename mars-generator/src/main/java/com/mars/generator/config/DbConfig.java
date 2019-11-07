package com.mars.generator.config;

import com.mars.generator.mapper.BaseMapper;
import com.mars.generator.mapper.MySQLMapper;
import com.mars.generator.mapper.OracleMapper;
import com.mars.generator.mapper.SQLServerMapper;
import com.mars.generator.utils.MarsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 数据库配置
 *
 */
@Configuration
public class DbConfig {
    @Value("${mars.database: mysql}")
    private String database;
    @Autowired
    private MySQLMapper mySQLMapper;
    @Autowired
    private OracleMapper oracleMapper;
    @Autowired
    private SQLServerMapper sqlServerMapper;

    @Bean
    @Primary
    public BaseMapper getMapper(){
        if("mysql".equalsIgnoreCase(database)){
            return mySQLMapper;
        }else if("oracle".equalsIgnoreCase(database)){
            return oracleMapper;
        }else if("sqlserver".equalsIgnoreCase(database)){
            return sqlServerMapper;
        }else {
            throw new MarsException("不支持当前数据库：" + database);
        }
    }
}
