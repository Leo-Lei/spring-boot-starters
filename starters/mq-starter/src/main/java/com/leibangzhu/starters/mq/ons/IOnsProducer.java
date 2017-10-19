package com.leibangzhu.starters.mq.ons;


import com.leibangzhu.starters.mq.Message;
import com.leibangzhu.starters.mq.MsgSendResult;
import com.leibangzhu.starters.mq.exception.MQException;

/**
 * Author: lili
 * Date: 2017/07/25
 * Time: 11:10
 */
public interface IOnsProducer {

    /**
     * 启动服务
     */
    void start() throws MQException;


    /**
     * 关闭服务
     */
    void shutdown();


    /**
     * 同步发送消息，只要不抛异常就表示成功
     *
     * @param message
     * @return 发送结果，含消息Id
     */
    MsgSendResult send(final Message message) throws MQException;


    /**
     * 发送消息，Oneway形式，服务器不应答，无法保证消息是否成功到达服务器
     *
     * @param message
     */
    void sendOneway(final Message message) throws MQException;
}
