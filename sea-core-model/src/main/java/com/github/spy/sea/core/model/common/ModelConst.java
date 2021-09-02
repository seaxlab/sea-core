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
    public static final String ID = "id";
    public static final String REF_ID = "refId";
    public static final String REF_CODE = "refCode";

    public static final String CODE = "code";
    public static final String GROUP_CODE = "groupCode";
    public static final String PARENT_CODE = "parentCode";

    public static final String NAME = "name";
    public static final String TYPE = "type";
    public static final String BIZ_TYPE = "bizType";
    public static final String BIZ_SUB_TYPE = "bizSubType";

    public static final String BIZ_TYPE_CODE = "bizTypeCode";
    public static final String BIZ_SUB_TYPE_CODE = "bizSubTypeCode";

    public static final String STATUS = "status";
    public static final String STATE = "state";
    public static final String VALUE = "value";

    public static final String SORT = "sort";
    public static final String ORDER = "order";
    public static final String PRIORITY = "priority";

    public static final String PREFIX = "prefix";
    public static final String INTRO = "intro";
    public static final String EXTRA = "extra";
    public static final String REMARK = "remark";
    public static final String DESCRIPTION = "description";

    public static final String IS_ENABLED = "isEnabled";
    public static final String IS_DELETED = "isDeleted";
    public static final String VERSION = "version";
    public static final String TENANT_ID = "tenantId";

    public static final String CREATOR = "creator";
    public static final String CREATE_USER = "createUser";
    public static final String CREATE_TIME = "createTime";
    public static final String GMT_CREATE = "gmtCreate";

    public static final String EDITOR = "editor";
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

    public static final String REQUEST_ID = "requestId";
    public static final String TRACE_ID = "traceId";
    public static final String SOURCE = "source";
    public static final String CHANNEL = "channel";


    // value
    public static final String REMARK_EMPTY = "-";
    public static final String ID_EMPTY = "0";
    public static final Integer ID_INT_EMPTY = 0;
    public static final Long ID_LONG_EMPTY = 0L;

    private ModelConst() {
    }
}
