package com.myapp.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by gy on 16/7/14.
 */

@Configuration
@ComponentScan("com.myapp")
@Import(MongoContext.class)
public class ApplicationConfig {
}
