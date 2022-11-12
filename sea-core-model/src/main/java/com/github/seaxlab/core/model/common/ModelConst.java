package com.github.seaxlab.core.model.common;

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

  public static final String REF_BIZ_KEY = "refBizKey";

  //外部订单号(商户订单号)
  public static final String OUT_TRADE_NO = "outTradeNo";
  // 外部请求单号
  public static final String OUT_REQUEST_NO = "outRequestNo";
  // 单号
  public static final String ORDER_NO = "orderNo";
  // 子单号
  public static final String SUB_ORDER_NO = "subOrderNo";
  // 流水号
  public static final String SERIAL_NUMBER = "serialNumber";
  // 交易号
  public static final String TRANS_NO = "transNo";
  // 行号
  public static final String ROW_NO = "rowNo";


  // 申请单-单号
  public static final String APPLY_NO = "applyNo";
  // 申请单-状态
  public static final String APPLY_STATUS = "applyStatus";

  public static final String INVOICE_NO = "invoiceNo";

  // 账户相关, customer(租户)-> user(用户) -> account(账户)
  public static final String CUSTOMER_NO = "customerNo";
  public static final String USER_NO = "userNo";
  public static final String ACCOUNT_NO = "accountNo";

  public static final String CUSTOMER_ID = "customerId";
  public static final String USER_ID = "userId";
  public static final String ACCOUNT_ID = "accountId";


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
  public static final String BIZ_TYPE_NAME = "bizTypeName";
  public static final String BIZ_SUB_TYPE = "bizSubType";
  public static final String BIZ_SUB_TYPE_NAME = "bizSubTypeName";

  public static final String BIZ_TYPE_CODE = "bizTypeCode";
  public static final String BIZ_SUB_TYPE_CODE = "bizSubTypeCode";

  // task
  public static final String TASK_CODE = "taskCode";
  public static final String TASK_NAME = "taskName";

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

    public static final String USE_FLAG = "useFlag";
    public static final String STOP_FLAG = "stopFlag";
    public static final String ACTIVE_FLAG = "activeFlag";
    public static final String SYNC_FLAG = "syncFlag";

    // 启停标识
    public static final String IS_ENABLED = "isEnabled";
    public static final String ENABLE_FLAG = "enableFlag";

    // 删除标识
    public static final String IS_DELETED = "isDeleted";
    public static final String DEL_FLAG = "delFlag";
    public static final String DELETED = "deleted";

    // 版本号
    public static final String VERSION = "version";
    public static final String MODEL_VERSION = "modelVersion"; /* db model version */
    //租户
    public static final String TENANT_ID = "tenantId";

    /*----------这里兼容了几种常用的字段-----------------*/
    // 创建人
    public static final String CREATOR = "creator";
    public static final String CREATE_USER_ID = "createUserId";
    public static final String CREATE_USER_NAME = "createUserName";
    public static final String CREATE_USER = "createUser";

    public static final String CREATE_TIME = "createTime";
    public static final String CREATED_BY = "createdBy"; // BIGINT
    public static final String CREATED_USER_NAME = "createdUserName";
    public static final String GMT_CREATE = "gmtCreate";

    // 修改人
    public static final String EDITOR = "editor";
    public static final String UPDATE_USER_ID = "updateUserId";
    public static final String UPDATE_USER_NAME = "updateUserName";
    public static final String UPDATE_USER = "updateUser";

    public static final String UPDATED_BY = "updatedBy"; // BIGINT
    public static final String UPDATED_USER_NAME = "updatedUserName";
    public static final String UPDATE_TIME = "updateTime";
    public static final String UPDATED_AT = "updatedAt";
    public static final String GMT_MODIFY = "gmtModify";

    public static final String TRADING_DAY = "tradingDay";
    public static final String BILL_DATE = "billDate";

    // src/dist, from/to
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
  public static final String ACTION_TYPE = "actionType";
  public static final String ACTION_NAME = "actionName";

  public static final String STRATEGY = "strategy";

  public static final String URL = "url";
  public static final String IP = "ip";
  public static final String USER_AGENT = "userAgent";
  // 设备号、设备标识
  public static final String DEVICE_NO = "deviceNo";
  public static final String REQUEST = "request";
  public static final String RESPONSE = "response";
  public static final String PARAMETER = "parameter";
  public static final String BODY = "body";
  public static final String PAYLOAD = "payload";
  public static final String RESULT = "result";
  public static final String SUCCESS = "success";

  public static final String COUNT = "count";
  public static final String SIZE = "size";

  public static final String TOKEN = "token";
  public static final String ACCESS_TOKEN = "accessToken";
  public static final String REFRESH_TOKEN = "refreshToken";

  public static final String MOBILE = "mobile";
  public static final String PHONE = "phone";

  // value
  public static final String REMARK_EMPTY = "-";
  public static final String ID_EMPTY = "0";
  public static final Integer ID_INT_EMPTY = 0;
  public static final Long ID_LONG_EMPTY = 0L;

  private ModelConst() {
  }
}
