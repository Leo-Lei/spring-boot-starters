package com.leibangzhu.starters.http;

import com.leibangzhu.starters.common.util.LeibangzhuLogger;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.util.Args;
import org.slf4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: lili
 * Date: 2017/2/21
 * Time: 17:22
 */
public final class HttpBrokerConfiguration implements Cloneable {

    private static final Logger logger = LeibangzhuLogger.create(HttpBrokerConfiguration.class);

    public static final HttpBrokerConfiguration DEFAULT = new Builder().build();

    private int maxConnections;
    private int defaultMaxConnectionsForEachRoute;
    private Map<HttpRoute,Integer> maxConnectionsPerRoute = new ConcurrentHashMap<>();
    private int socketTimeout;
    private int connectTimeout;
    private int connectionRequestTimeout;
    private String cookieSpec;

    public HttpBrokerConfiguration(
            final int maxConnections,
            final int defaultMaxConnectionsForEachRoute,
            final Map<HttpRoute,Integer> maxConnectionsPerRoute,
            final int socketTimeout,
            final int connectTimeout,
            final int connectionRequestTimeout,
            final String cookieSpec){

        this.maxConnections = maxConnections;
        this.defaultMaxConnectionsForEachRoute = defaultMaxConnectionsForEachRoute;
        this.maxConnectionsPerRoute = maxConnectionsPerRoute;
        this.socketTimeout = socketTimeout;
        this.connectTimeout = connectTimeout;
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.cookieSpec = cookieSpec;
    }

    public int getMaxConnections() {

        return maxConnections;
    }

    public int getDefaultMaxConnectionsForEachRoute() {

        return defaultMaxConnectionsForEachRoute;
    }

    public Map<HttpRoute, Integer> getMaxConnectionsPerRoute() {
        
        return maxConnectionsPerRoute;
    }

    public int getSocketTimeout() {

        return socketTimeout;
    }

    public int getConnectTimeout() {

        return connectTimeout;
    }

    public int getConnectionRequestTimeout() {

        return connectionRequestTimeout;
    }

    public String getCookieSpec() {

        return cookieSpec;
    }@Override
    protected ConnectionConfig clone() throws CloneNotSupportedException {
        return (ConnectionConfig) super.clone();
    }

    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder();
        builder.append("[maxConnections=").append(this.defaultMaxConnectionsForEachRoute)
                .append(", cookieSpec=").append(this.cookieSpec)
                .append(", socketTimeout=").append(this.socketTimeout)
                .append(", connectTimeout=").append(this.connectTimeout)
                .append(", connectionRequestTimeout=").append(this.connectionRequestTimeout)
                .append("]");
        return builder.toString();
    }

    public static Builder custom() {
        return new Builder();
    }

    public static Builder copy(final HttpBrokerConfiguration config) {
        Args.notNull(config, "HttpBroker config");
        return new Builder()
                .setMaxConnections(config.getMaxConnections())
                .setDefaultMaxConnectionsForEachRoute(config.getDefaultMaxConnectionsForEachRoute())
                .setMaxConnectionsPerRoute(config.getMaxConnectionsPerRoute())
                .setSocketTimeout(config.getSocketTimeout())
                .setConnectTimeout(config.getConnectTimeout())
                .setConnectionRequestTimeout(config.getConnectionRequestTimeout())
                .setCookieSpec(config.getCookieSpec());
    }

    public static class Builder {

        private int maxConnections = 100;
        private int defaultMaxConnectionsForEachRoute = 20;
        private Map<HttpRoute,Integer> maxConnectionsPerRoute = new ConcurrentHashMap<>();
        private int socketTimeout = 2000;
        private int connectTimeout = 3000;
        private int connectionRequestTimeout = 200;
        private String cookieSpec = CookieSpecs.IGNORE_COOKIES;

        public Builder setMaxConnections(int maxConnections) {

            this.maxConnections = maxConnections;
            return this;
        }

        public Builder setDefaultMaxConnectionsForEachRoute(int defaultMaxConnectionsForEachRoute) {

            this.defaultMaxConnectionsForEachRoute = defaultMaxConnectionsForEachRoute;
            return this;
        }

        public Builder setMaxConnectionsPerRoute(Map<HttpRoute,Integer> maxConnectionsPerRoute){

            this.maxConnectionsPerRoute = maxConnectionsPerRoute;
            return this;
        }

        public Builder appendMaxConnectionsPerSpecificRoute(HttpRoute route, int maxLimit){

            maxConnectionsPerRoute.put(route, maxLimit);
            return this;
        }

        public Builder setSocketTimeout(int socketTimeout) {

            this.socketTimeout = socketTimeout;
            return this;
        }

        public Builder setConnectTimeout(int connectTimeout) {

            this.connectTimeout = connectTimeout;
            return this;
        }

        public Builder setConnectionRequestTimeout(int connectionRequestTimeout) {

            this.connectionRequestTimeout = connectionRequestTimeout;
            return this;
        }

        public Builder setCookieSpec(String cookieSpec) {

            this.cookieSpec = cookieSpec;
            return this;
        }

        public HttpBrokerConfiguration build() {

            return new HttpBrokerConfiguration(
                    maxConnections,
                    defaultMaxConnectionsForEachRoute,
                    maxConnectionsPerRoute,
                    socketTimeout,
                    connectTimeout,
                    connectionRequestTimeout,
                    cookieSpec);
        }

    }
}
