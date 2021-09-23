package com.github.spy.sea.core.model.common;

import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/9/1
 * @since 1.0
 */
@Slf4j
public final class ModelConst {

    // field
    // ID
    public static final String ID = "id";
    // 关联id
    public static final String REF_ID = "refId";
    // 关联code
    public static final String REF_CODE = "refCode";
    // 关联key
    public static final String REF_KEY = "refKey";
    // 关联单号
    public static final String REF_NO = "refNo";

    // 单号
    public static final String ORDER_NO = "orderNo";
    // 子单号
    public static final String SUB_ORDER_NO = "subOrderNo";
    // 流水号
    public static final String SERIAL_NUMBER = "serialNumber";
    // 交易号
    public static final String TRANS_NO = "transNo";

    public static final String CODE = "code";
    public static final String INNER_CODE = "innerCode";
    public static final String STANDARD_CODE = "standardCode";
    public static final String UNIFIED_CODE = "unifiedCode";
    public static final String GROUP_CODE = "groupCode";
    public static final String PARENT_CODE = "parentCode";

    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String CATEGORY = "category";
    public static final String BIZ_TYPE = "bizType";
    public static final String BIZ_SUB_TYPE = "bizSubType";

    public static final String BIZ_TYPE_CODE = "bizTypeCode";
    public static final String BIZ_SUB_TYPE_CODE = "bizSubTypeCode";

    // 业务键
    public static final String BIZ_KEY = "bizKey";
    // 业务单号
    public static final String BIZ_NO = "bizNo";

    public static final String STATUS = "status";
    public static final String STATE = "state";
    //阶段
    public static final String STAGE = "stage";
    public static final String SCOPE = "scope";
    public static final String RANGE = "range";
    public static final String VALUE = "value";

    public static final String ENV = "env";

    public static final String SORT = "sort";
    public static final String ORDER = "order";
    public static final String PRIORITY = "priority";
    public static final String RANK = "rank";
    public static final String SCORE = "score";
    public static final String LEVEL = "level";
    public static final String GRADE = "grade";

    public static final String PREFIX = "prefix";
    public static final String SUFFIX = "suffix";
    public static final String INTRO = "intro";
    public static final String META = "meta";
    public static final String EXTRA = "extra";
    public static final String REMARK = "remark";
    public static final String DESCRIPTION = "description";

    public static final String IS_ENABLED = "isEnabled";
    public static final String IS_DELETED = "isDeleted";
    public static final String VERSION = "version";
    public static final String TENANT_ID = "tenantId";

    /*----------这里兼容了几种常用的字段-----------------*/
    public static final String CREATOR = "creator";
    public static final String CREATE_USER_ID = "create_user_id";
    public static final String CREATE_USER_NAME = "create_user_name";
    public static final String CREATE_USER = "createUser";
    public static final String CREATE_TIME = "createTime";
    public static final String GMT_CREATE = "gmtCreate";

    public static final String EDITOR = "editor";
    public static final String UPDATE_USER_ID = "update_user_id";
    public static final String UPDATE_USER_NAME = "update_user_name";
    public static final String UPDATE_USER = "updateUser";
    public static final String UPDATE_TIME = "updateTime";
    public static final String GMT_MODIFY = "gmtModify";

    public static final String TRADING_DAY = "tradingDay";
    public static final String BILL_DATE = "billDate";

    public static final String BEGIN_DATE = "beginDate";
    public static final String END_DATE = "endDate";
    public static final String BEGIN_TIME = "beginTime";
    public static final String END_TIME = "endTime";

    public static final String PERIOD_TYPE = "periodType";

    public static final String BATCH_NO = "batchNo";

    public static final String TAG = "tag";
    public static final String TAG1 = "tag1";
    public static final String TAG2 = "tag2";
    public static final String TAG3 = "tag3";
    public static final String TAG4 = "tag4";
    public static final String TAG5 = "tag5";
    public static final String TAG6 = "tag6";


    /*-------------共性字段-----------------*/
    public static final String REQUEST_ID = "requestId";
    public static final String TRACE_ID = "traceId";
    /* 来源 */
    public static final String SOURCE = "source";
    /* 用户操作时系统渠道 */
    public static final String CHANNEL = "channel";
    /*场景*/
    public static final String SCENE = "scene";
    public static final String SCENE_NAME = "sceneName";
    public static final String ACTION = "action";
    public static final String ACTION_NAME = "actionName";

    public static final String STRATEGY = "strategy";

    public static final String URL = "url";
    public static final String IP = "ip";
    public static final String USER_AGENT = "userAgent";
    public static final String REQUEST = "request";
    public static final String RESPONSE = "response";
    public static final String PARAMETER = "parameter";
    public static final String BODY = "body";
    public static final String PAYLOAD = "payload";
    public static final String RESULT = "result";

    public static final String COUNT = "count";
    public static final String SIZE = "size";


    // value
    public static final String REMARK_EMPTY = "-";
    public static final String ID_EMPTY = "0";
    public static final Integer ID_INT_EMPTY = 0;
    public static final Long ID_LONG_EMPTY = 0L;

    private ModelConst() {
    }
}
