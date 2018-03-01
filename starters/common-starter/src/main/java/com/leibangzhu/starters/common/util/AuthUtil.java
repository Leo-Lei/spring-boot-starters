package com.leibangzhu.starters.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class AuthUtil {

    private static final Logger logger = LoggerFactory.getLogger(AuthUtil.class);

    private static final String SIGN = "sign";
    private static final String APP_NAME = "appName";
    private static final String TIMESTAMP = "timestamp";

    /**
     * 判断是否含有必要的参数
     */
    public static Boolean requestIsValide(HttpServletRequest request) {
        if (StringUtils.isBlank(request.getParameter(TIMESTAMP))
                || StringUtils.isBlank(request.getParameter(APP_NAME))
                || StringUtils.isBlank(request.getParameter(SIGN))) {
            return false;
        }
        return true;
    }

    /**
     * 验证请求是否逾期
     */
    public static Boolean requestIsExpiry(HttpServletRequest request) {
        try {
            String timestamp = request.getParameter(TIMESTAMP);
            if (StringUtils.isBlank(timestamp)) {
                logger.info("request parame timestamp is null");
                return false;
            }
            Long curTime = new Date().getTime();
            Long requestTime = Long.parseLong(timestamp);
            if (Math.abs(curTime - requestTime) < 10000) {
                return true;
            }
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return false;
    }

    /**
     * @param request   http请求
     * @param appSecret 分配给应用的appSecret
     * @return
     */
    public static Boolean verifySign(HttpServletRequest request, String appSecret) {
        //1.获取所有的参数名字
        List<String> nameList = getRequestParameName(request);
        //2.生成sign
        String sign = createSign(nameList, request, appSecret);
        //3.获取app传递的sign值
        String accessSign = request.getParameter(SIGN);
        logger.info("App端方法签名: {}, 服务器端方法签名:{}", accessSign, sign);
        Boolean success = StringUtils.equalsIgnoreCase(accessSign, sign);
        if (Boolean.FALSE == success) {
            logger.info("sign is error, appsign:{}, sign:{}", accessSign, sign);
        }
        return success;
    }

    // 在请求的参数中添加必要的信息，比如签名，时间戳等
    public static Map<String,String> addAuthInfo2Params(Map<String,String> params,String appSecret){

        // 添加timestamp
        Long curTime = new Date().getTime();
        params.put(TIMESTAMP,curTime.toString());

        Set<String> keys = params.keySet();
        String[] keyArray = {};
        List<String> keyList = Arrays.asList(keys.toArray(keyArray));
        Collections.sort(keyList);

        // 添加签名
        StringBuilder sb = new StringBuilder();
        for (String name : keyList) {

            sb.append(name);
            String parameterValues = params.get(name);
            sb.append(parameterValues);
        }

        sb.append(appSecret);
        logger.info("加密前sign:[{}]", sb.toString());
        String sign = MD5Util.MD5(sb.toString());
        logger.info("加密后sign:[{}]", sign);

        params.put(SIGN,sign);
        return params;
    }

    private static List<String> getRequestParameName(HttpServletRequest request) {
        List<String> nameList = new ArrayList<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();

            if (paramName.equals(SIGN)) {
                continue;
            }

            nameList.add(paramName);
        }

        logger.info("排序前的消息头名和请求参数名列表: {}", nameList);
        Collections.sort(nameList);
        logger.info("排序后的消息头名和请求参数名列表: {}", nameList);

        return nameList;
    }

    // 根据参数和appSecret生成签名
    private static String createSign(List<String> nameList, HttpServletRequest request, String appSecret) {
        StringBuilder sb = new StringBuilder();
        for (String name : nameList) {

            sb.append(name);
            String[] parameterValues = request.getParameterValues(name);
            for (String paramValue : parameterValues) {
                sb.append(paramValue);
            }
        }

        sb.append(appSecret);
        logger.info("加密前sign:[{}]", sb.toString());
        String sign = MD5Util.MD5(sb.toString());
        logger.info("加密后sign:[{}]", sign);
        return sign;
    }
}
