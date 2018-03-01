package com.leibangzhu.starters.cache.redis;

import com.leibangzhu.starters.common.date.DateTime;
import com.leibangzhu.starters.common.date.TimeUnits;
import com.leibangzhu.starters.common.util.LeibangzhuLogger;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CacheManager implements ICacheManager {

    private static final Logger logger = LeibangzhuLogger.create(CacheManager.class);

    private Map<String, ValueWrapper<String>> localCache = new ConcurrentHashMap<>();
    private String cacheTopic;

    public CacheManager(String cacheTopic){

        this.cacheTopic = cacheTopic;
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public String get(String key) {

        ValueWrapper<String> value = localCache.get(key);

        DateTime currentDateTime = DateTime.now();
        if(null == value || value.getLastUpdatedTime().getPeroidTo(currentDateTime, TimeUnits.SECONDS) > 60){

            String remoteValue = redisTemplate.opsForValue().get(key);

            if(null == value){

                value = new ValueWrapper<>(remoteValue);
                localCache.put(key, value);
            }else{

                value.refresh(remoteValue);
            }
        }

        return value.getValue();
    }



    @Override
    public synchronized String put(String key, String value) {

        redisTemplate.opsForValue().set(key, value);
        ValueWrapper<String> oldValue = localCache.put(key, new ValueWrapper<String>(value));

        if(logger.isDebugEnabled()) {

            if(null != oldValue){

                logger.debug(String.format("Cache is refreshed for[%s], from [%s] to [%s].", key, oldValue.getValue(), value));
            }else{

                logger.debug(String.format("Cache is created for[%s] -- [%s].", key, value));
            }
        }

        NotificationMessage notification = new NotificationMessage(String.valueOf(this.hashCode()), key);
        redisTemplate.convertAndSend(cacheTopic, notification.toMessage());

        if(logger.isDebugEnabled()) {

            logger.debug(String.format("Value update notification for [%s] -- [%s] is sent.", key, value));
        }

        return null != oldValue ? oldValue.getValue() : null;
    }

    public void receiveMessage(String message) {

        if(logger.isDebugEnabled()) {

            logger.debug(String.format("Received <%s>.", message));
        }

        NotificationMessage notificationMessage = NotificationMessage.fromMessage(message);
        if(null != notificationMessage && !notificationMessage.getId().equals(String.valueOf(this.hashCode()))){

            ValueWrapper<String> oldValue = localCache.remove(notificationMessage.getMessage());

            if(logger.isDebugEnabled()) {

                logger.debug(String.format("Cache is removed, [%s].", notificationMessage.getMessage()));
            }
        }
    }
}
