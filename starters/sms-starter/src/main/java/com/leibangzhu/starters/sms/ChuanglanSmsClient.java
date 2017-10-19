package com.leibangzhu.starters.sms;

import com.leibangzhu.starters.common.util.QibeiLogger;
import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: lili
 * Date: 2017/2/22
 * Time: 17:50
 */
public class ChuanglanSmsClient extends BaseSmsClient {

    private static final Logger logger = QibeiLogger.create(ChuanglanSmsClient.class);

    private final String server;
    private final String account;
    private final String password;

    public ChuanglanSmsClient(String server, String account, String password) {

        this.server = server;
        this.account = account;
        this.password = password;
    }

    @Override
    public List<String> sendMessage(String mobileNumber, String message) {

        Map<String, Object> params = prepareRequestParameters(mobileNumber, message);

        String responseText = httpBroker.post(server, params, null);

        List<String> successNumbers = new ArrayList<>();

        if (StringUtils.isNoneEmpty(responseText)) {

            String[] returnTokens = responseText.split(",");
            if (2 == returnTokens.length) {

                if (returnTokens[1].equals("0")) {

                    successNumbers.add(mobileNumber);
                } else {

                    logger.error(String.format("Fail to send message by ChuangLan, error code is '%s'", returnTokens[1]));
                }
            } else {

                logger.warn(String.format("Wrong number of return tokens by ChuangLan, expected '2' but '%s'", returnTokens.length));
            }
        } else {

            logger.warn("Empty response from ChuangLan.");
        }

        return successNumbers;
    }

    private Map<String, Object> prepareRequestParameters(String mobileNumber, String message) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("account", account);
        params.put("pswd", password);
        params.put("mobile", mobileNumber);
        params.put("msg", message);
        return params;
    }

    @Override
    protected void releaseResouces() {

        super.releaseResouces();
    }

    @Override
    public List<String> sendMessage(List<String> mobileNumbers, String templateKey, String... templateArgs) throws Exception {

        throw new NotImplementedException("No batch way supported.");
    }
}

    

/*      状态码      说明
        0          成功
        1          失败
        2          非法请求
        101        账号不存在
        102        密码错误
        103        提交请求太快
        104        系统忙
        105        含有敏感危险词
        106        消息为空
        107        消息超过536个字符
        108        手机号码格式错误
        109        发送余额不足
        110        余额不足
        111        提交时间错误
        112        产品错误
        113        签名错误
        114        客户端IP错误
        115        发送权限错误
        116        账号已过期
        117        模板变量格式错误
        118        模板ID不能为空
        119        模板内容不存在
        120        网关错误
        121        该国家尚未支持国际验证码
        122        模板错误，请去提交模板
        123        短信内容不能为空
        124        缺少参数
        900        网络请求失败
        901        初始化失败
        50100      非法appkey
        50101      非法包名
        50103      非法签名认证
        50104      解密数据错误
        50105      非法设备ID
        50106      非法手机号
        50107      模板ID错误或不存在
        50108      模板签名不存在或错误
        50109      保存验证信息失败
        50110      保存发送验证码信息失败
        50111      产品未激活，不存在平台账号
        50112      短信验证信息不存在
        50113      频率过快限制
        50115      验证验证码失败
        50118      账户余额不足
        50119      数据签名错误
        50120      IP访问频率过快限制
        50121      未上线应用每天限制最多发送1000条
        50122      单个IP每分钟访问量限制
        50123      单个IP每小时访问量限制
        50124      单个IP每天访问量限制
        50125      单个手机号每分钟访问量限制
        50126      单个手机号每小时访问量限制
        50127      单个手机号每天访问量限制
        50128      单个IMEI每分钟访问量限制
        50129      单个IMEI每小时访问量限制
        50130      单个IMEI每天访问量限制
        50131      非法攻击访问
        50132      发送短信时间戳过期
        50133      重复发送短信请求
*/
