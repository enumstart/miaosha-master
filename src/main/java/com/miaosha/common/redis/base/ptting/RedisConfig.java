package com.miaosha.common.redis.base.ptting;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by pc on 2018/3/5.
 */
@Component
@ConfigurationProperties(prefix = "redis")
public class RedisConfig {
   private String   host;
   private String   password;
   private int      port;
   private int      timeout;
   private int      poolMaxTotal;
   private int      poolMaxIdle;
   private int      poolMaxWait;

    public String getHost() {
        return host;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public int getPoolMaxTotal() {
        return poolMaxTotal;
    }

    public void setPoolMaxTotal(int poolMaxTotal) {
        this.poolMaxTotal = poolMaxTotal;
    }

    public int getPoolMaxIdle() {
        return poolMaxIdle;
    }

    public void setPoolMaxIdle(int poolMaxIdle) {
        this.poolMaxIdle = poolMaxIdle;
    }

    public int getPoolMaxWait() {
        return poolMaxWait;
    }

    public void setPoolMaxWait(int poolMaxWait) {
        this.poolMaxWait = poolMaxWait;
    }
}
