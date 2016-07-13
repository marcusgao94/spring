package com.myapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by gy on 16/7/13.
 */

@Configuration
@ComponentScan(basePackages = "com.myapp")
@Import(MongoContext.class)
public class ApplicationContext {

}
