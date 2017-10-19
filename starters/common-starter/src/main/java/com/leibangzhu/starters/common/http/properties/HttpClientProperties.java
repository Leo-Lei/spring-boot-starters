package com.leibangzhu.starters.common.http.properties;

public class HttpClientProperties {
    private final Pool pool = new Pool();
    private int socketTimeout = 3000;
    private int connectTimeout = 3000;
    private int connectRequestTimeout = 3000;

    public Pool getPool() {
        return pool;
    }

    public int getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(int socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getConnectRequestTimeout() {
        return connectRequestTimeout;
    }

    public void setConnectRequestTimeout(int connectRequestTimeout) {
        this.connectRequestTimeout = connectRequestTimeout;
    }


    public static class Pool{
        private int maxTotal = 50;             //整个连接池最大的连接数
        private int maxPerRoute = 30;          //给每个连接的http主机的最大连接数

        public int getMaxTotal() {
            return maxTotal;
        }

        public void setMaxTotal(int maxTotal) {
            this.maxTotal = maxTotal;
        }

        public int getMaxPerRoute() {
            return maxPerRoute;
        }

        public void setMaxPerRoute(int maxPerRoute) {
            this.maxPerRoute = maxPerRoute;
        }
    }
}
