package com.leibangzhu.starters.mq.ons;

import com.leibangzhu.starters.mq.IMsgHandler;
import com.leibangzhu.starters.mq.exception.MQException;

/**
 * Author: lili
 * Date: 2017/07/25
 * Time: 11:39
 */
public interface IOnsConsumer {

    /**
     * 启动服务
     */
    public void start() throws MQException;


    /**
     * 关闭服务
     */
    public void shutdown();


    /**
     * 订阅消息
     *
     * @param topic         订阅TOPIC
     * @param subExpression 订阅过滤表达式字符串，ONS服务器依据此表达式进行过滤。只支持或运算<br>
     *                      eg: "tag1 || tag2 || tag3"<br>
     *                      如果subExpression等于null或者*，则表示全部订阅
     * @param handler       消息回调处理器
     */
    public void subscribe(final String topic, final String subExpression, final IMsgHandler handler) throws MQException;

    /**
     * 订阅消息
     *
     * @param handler       消息回调处理器
     */
    public void subscribe(String topic, final IMsgHandler handler) throws MQException;


    /**
     * 取消某个topic订阅
     *
     * @param topic
     */
    public void unsubscribe(final String topic);
}
