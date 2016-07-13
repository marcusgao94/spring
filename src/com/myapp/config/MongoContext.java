package com.myapp.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.ProtocolException;

/**
 * Created by gy on 16/7/13.
 */

@Configuration
@EnableMongoRepositories
@PropertySource("classpath:com/myapp/config/mongo.properties")
public class MongoContext extends AbstractMongoConfiguration {

    @Value("${mongo.host}")
    private String host;
    @Value("${mongo.port}")
    private int port;
    @Value("${mongo.connectionsPerHost}")
    private int connectionsPerHost;
    @Value("${mongo.connectTimeout}")
    private int connectTimeout;

    @Override
    public String getDatabaseName() {
        return "spring";
    }

    @Override
    public Mongo mongo() {
        MongoClientOptions.Builder optionsBuilder = new MongoClientOptions.Builder();
        optionsBuilder.connectionsPerHost(connectionsPerHost);
        optionsBuilder.connectTimeout(connectTimeout);

        return new MongoClient(new ServerAddress(host, port),
                optionsBuilder.build());
    }
}
