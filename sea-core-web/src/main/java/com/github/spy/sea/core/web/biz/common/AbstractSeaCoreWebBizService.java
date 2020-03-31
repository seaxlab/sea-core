package com.github.spy.sea.core.web.biz.common;

import lombok.extern.slf4j.Slf4j;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/3/31
 * @since 1.0
 */
@Slf4j
public abstract class AbstractSeaCoreWebBizService {

    /**
     * get operator
     *
     * @return
     */
    public abstract String getOperator();

    /**
     * check DB if <=0  throw exception.
     *
     * @param rowCount
     * @return
     */
    public abstract String checkDB(int rowCount);

}
