package com.leibangzhu.starters.cache.redis;


import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
@ConfigurationProperties(prefix = "cache")
public class CacheAutoConfiguration {

    private String notifyTopic;

    @Bean(name = "cacheManager")
    @Qualifier("cacheManager")
    public ICacheManager cacheManager(){

        return new CacheManager(evaluateCacheTopic());
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   MessageListenerAdapter listenerAdapter) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic(evaluateCacheTopic()));

        return container;
    }

    @Bean
    public MessageListenerAdapter listenerAdapter(@Qualifier("cacheManager") ICacheManager cacheManager) {
        return new MessageListenerAdapter(cacheManager, "receiveMessage");
    }

    @Bean
    public StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    public String getNotifyTopic() {
        return notifyTopic;
    }

    public void setNotifyTopic(String notifyTopic) {
        this.notifyTopic = notifyTopic;
    }

    private String evaluateCacheTopic() {

        return StringUtils.isEmpty(notifyTopic) ? "cache" : notifyTopic;
    }
}
