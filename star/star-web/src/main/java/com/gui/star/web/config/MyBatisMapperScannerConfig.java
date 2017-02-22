package com.gui.star.web.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author wuhoujian
 * @desc 配置spring应该扫描哪些包下面的*Mapper.java文件，从而决定为哪些dao生成动态代理
 * 
 */
@Configuration
public class MyBatisMapperScannerConfig {
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage("com.gui.star.dal.mapper");

		return mapperScannerConfigurer;
	}
}
