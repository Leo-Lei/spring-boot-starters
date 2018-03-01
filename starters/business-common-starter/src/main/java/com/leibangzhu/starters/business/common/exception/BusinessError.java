package com.leibangzhu.starters.business.common.exception;

import java.io.Serializable;

/**
 * Author: lili
 * Date: 2016/10/20
 * Time: 10:16
 */
public enum BusinessError implements Serializable {

    //二维码租车返回的码
    USER_NOT_GPS(301, "无法定位您当前位置，请打开GPS"),
    HAVE_UNFINISHED_TRIP(302, "您还有未完成行程"),
    BAIL_NOT_SATISFY(303, "您押金不足"),
    BIKE_NOT_BE_USED(304, "此车暂不能使用，请换用其它车辆"),
    NOT_SUPPORT_OPERATE(305, "不能进行此操作"),
    FIND_NO_BIKE(306, "无该车辆编号"),
    BIKE_OFF_LINE(307, "车辆离线"),
    BIKE_NO_REAL_NAME(311, "实名认证后才能租车"),

    INVITE_USER_NOT_EXIST(312, "不存在邀请信息"),
    LOCATION_EXCTION(1000, "没有定位信息"),



    // Core：range is from 40001 to 40100
    QIBEI_SCORE_NOT_ENOUGH(40001, "您的骑呗信用分过低,不可租车"),



    // Auth：range is 40101: to 40200
    VALIDE_CODE_NOT_EXIST(40101, "验证码已失效"),
    VALIDE_CODE_ERROR(40102, "验证码错误"),
    SMS_SEND_ERROR(40103, "短信发送异常"),
    SMS_TOO_FREQUENT_ERROR(40104, "重复发送短信时间间隔过小"),
    USER_NOT_REGISTER_ERROR(40105, "用户不存在"),
    MOBILE_OR_PASSWORD_ERROR(40106, "手机号或密码错误"),
    USERNAME_OR_PASSWORD_ERROR(40107, "用户名或密码错误"),
    IDENTITYNUM_OR_PASSWORD_ERROR(40108, "身份证或密码错误"),
    USERNAME_EXIST_ERROR(40109, "该用户名已被使用"),
    MOBILE_EXIST_ERRROR(40110, "该手机号已被使用"),
    SERVER_INTERNAL_ERROR(40200, "服务器内部异常"),



    // User: range is  40201: to 40300
    IDENTITYNUM_BOUND_ERROR(40201, "该证件号已被使用"),
    ILLEGAL_ID_ERROR(40202, "证件号不合法"),
    ILLEGAL_NAME_ERROR(40203, "证件名不合法"),
    ILLEGAL_ORIGIN_PASSWORD_ERROR(40204, "原密码输入有误"),
    ILLEGAL_VALIDE_CODE_ERROR(40205, "验证码错误"),
    MISS_REQUIRED_PARAMTER_ERROR(40206, "缺少必要的参数"),
    MOBILE_IS_USED_ERROR(40207, "该手机号已被使用"),
    USERNAME_IS_USED_ERROR(40208, "该用户名已被使用"),
    USER_NOT_EXIST_ERROR(40209, "用户不存在"),
    USER_SERVER_INTERNAL_ERROR(40210, "服务器内部异常"),
    IDENTITY_NUM_EXIST_ERROR(40211, "此身份证号已被认证"),



    // Finance: range is  40301: to 40400
    BAIL_EXCEPTION(40301, "保证金退还异常"),
    RECHARGE_LACK_PARAM_EXCEPTION(40302, "充值参数缺少"),
    RECHARGE_BEHAVIOR_EXCEPTION(40303, "充值方式错误"),
    EVOCATION_WHEAT_EXCEPTION(40304, "调起微信失败"),
    IMG_UPLOAD_ERROR(40301, "上传文件异常"),
    ZHIMA_COMMIT_ERROR(40302, "上传认证信息异常"),
    ZHIMA_CALLBACK_ERROR(40303, "芝麻回调异常"),
    COMMIT_STATUS_ERROR(40304, "信息已提交"),
    COMMIT_IDENTITY_ERROR(40305, "身份证错误"),
    BALANCE_EXCEPTION(40306, "用户授权状态异常"),
    BILLI_SEMPTY_EXCEPTION(40307, "账单不存在"),
    PAYMENT_LACK_PARAM_EXCEPTION(40308, "参数错误"),
    PAYMENT_MONEY_EXCEPTION(40309, "账单金额错误"),
    PAYMENT_BEHAVIOR_EXCETION(40310, "支付方式错误"),



    // Inviter: range is  40501: to 40600
    INVITE_CODE_IS_SET(40501, "用户已使用邀请码"),
    USER_IS_NOT_EXIST(40502, "在邀请服务的用户名不存在"),
    INVITE_CODE_IS_SELF(40503, "不能使用自己的邀请码"),
    UNKNOWN_INVITE_CODE(40504, "无效的邀请码"),
    USER_NOT_USEBENEFIT(40505, "此用户无法使用邀请服务"),



    // Feedback: range is  40601: to 40700
    ALREADY_COMMENT_EXCEPTION(40601,"此订单已评价"),



    // Version: range is  40701: to 40800



    // Device: range is  40801: to 040900
    SIGN_VALID_ERROR(40801,"服务器繁忙，请稍候再试"),
    PARSE_MESSAGE_ERROR(40802,"服务器繁忙，请稍候再试"),

    UNKNOWN_DEVICE_ERROR(40803, "服务器繁忙，请稍候再试"),
    DEVICE_INFO_NOT_FOUND(40804, "这二维码我不认识哦"),
    CANNOT_BE_LOCK_IF_BIKE_IS_AVAILALBE(40805, "设备处于非租用状态无法关锁"),
    CANNOT_BE_UNLOCK_IF_BIKE_IS_NOT_AVAILABLE(40806, "租车失败，扫码重试，或换辆车试试"),
    LOW_BATTERY_FOR_DEVICE(40807, "租车失败，换辆车试试"),
    NO_GPS_FOR_DEVICE(40808, "租车失败，扫码重试，或换辆车试试"),
    DEVICE_IS_OFFLINE(40809, "租车失败，换辆车试试"),
    DEVICE_IS_UNDER_WARNING(40810, "设备处于警告状态"),
    DEVICE_IS_NOT_AUTHORIZED(40811, "租车失败，换辆车试试"),
    COMMAND_OF_LOCK_IS_FAIL_TO_SENT(40813, "发送关锁指令失败"),
    COMMAND_OF_UNLOCK_IS_FAIL_TO_SENT(40814, "租车失败，换辆车试试"),
    COMMAND_OF_FINDING_LOCK_IS_FAIL_TO_SENT(40815, "发送寻锁指令失败"),
    FAIL_TO_INVOKE_API_OF_FINDING_DEVICE(40816, "服务器繁忙，请稍候再试"),
    FAIL_TO_INVOKE_API_OF_REGISTERING_DEVICE(40817, "服务器繁忙，请稍候再试"),
    DEVICE_IS_ALREADY_REGISTERED(40818, "服务器繁忙，请稍候再试"),
    FAIL_TO_KEEP_HEARTBEAT_OF_ALI(40819, "服务器繁忙，请稍候再试"),
    FAIL_TO_KEEP_HEARTBEAT_OF_NOKE(40820, "服务器繁忙，请稍候再试"),
    FAIL_TO_INVOKE_CALLBACK_OF_NOKE_COMMAND(40821, "服务器繁忙，请稍候再试"),
    FAIL_TO_GET_NEARBY_DEVICE_INFO(40822, "服务器繁忙，请稍候再试"),
    FAIL_TO_CANCEL_LOCK_COMMAND(40823, "服务器繁忙，请稍候再试"),
    FAIL_TO_REGISTER_CALLBACK_OF_ALI(40824, "服务器繁忙，请稍候再试"),
    DEVICE_IS_NOT_IN_VALID_SCOPE(40825, "您不在此车辆的可租范围内"),


    // DataX: range is  40901: to 41000
    INVALID_DATA_EXTRACT_TASK_NAME(40901,"未定义的数据抓取任务名"),
    FAIL_TO_NOTIFY_OF_DATA_EXTRACTED(40902,"通知失败(数据抓取任务完成)"),
    UNKNOWN_DATA_EXTRACT_ERROR(40903,"未知异常（数据抓取服务)");



    private int code;
    private String message;

    BusinessError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessge() {
        return message;
    }
}