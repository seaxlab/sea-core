package com.github.spy.sea.core.common;

import com.github.spy.sea.core.util.ClassUtil;
import com.github.spy.sea.core.util.JvmUtil;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * sea core const
 *
 * @author spy
 * @version 1.0 2019-05-14
 * @since 1.0
 */
@Slf4j
public class CoreConst {
    private CoreConst() {
    }

    static {
        log.info("sea app pid={}", JvmUtil.getPID());
        printLogo();
    }

    /**
     * number of cpu
     */
    public static final int NCPU = Runtime.getRuntime().availableProcessors();

    /**
     * sys operator
     */
    public static final String SYS_OPERATOR = "SYS_OPERATOR";

    /**
     * sys creator
     */
    public static final String SYS_CREATOR = "SYS_CREATOR";

    /**
     * sys editor
     */
    public static final String SYS_EDITOR = "SYS_EDITOR";

    /* global yes flag */
    public static final Boolean YES = Boolean.TRUE;
    /* global no flag */
    public static final Boolean NO = Boolean.FALSE;

    /**
     * 请求Id
     */
    public static final String REQUEST_ID = "requestId";

    /**
     * 链路id
     */
    public static final String TRACE_ID = "traceId";

    /**
     * MDC req.id
     */
    public static final String MDC_REQ_ID = "req.id";


    /**
     * default charset name
     */
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";

    /**
     * default charset is utf-8
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    /**
     * charset UTF_8
     */
    public static final Charset CHARSET_UTF8 = StandardCharsets.UTF_8;

    /**
     * http
     */
    public static final String SCHEME_HTTP = "http";

    /**
     * https
     */
    public static final String SCHEME_HTTPS = "https";

    /**
     * default page size
     */
    public static final int DEFAULT_PAGE_SIZE = 200;

    /**
     * 程序当前运行模式
     * 请使用KEY_SEA_ENV
     */
    @Deprecated
    public static final String KEY_SEA_DEV_MODE = "sea.dev.mode";

    /**
     * 当前程序运行模式
     */
    public static final String KEY_SEA_ENV = "sea.env";


    /**
     * 租户标识（上下文）
     */
    public static final String CTX_TENANT_ID = "sea-tenant-id";

    /**
     * 灰度标识（限内部使用）
     */
    public static final String CTX_TAG_GRAY = "sea-tag-gray";

    /**
     * 压力测试标识（限内部使用）
     */
    public static final String CTX_TAG_STRESS_TESTING = "sea-tag-stress-testing";

    /**
     * http 外部灰度标识，内部不可用
     */
    public static final String HTTP_HEADER_X_SERVICE_CHAIN = "X-Service-Chain";

    public static final String REQUEST_URL = "request.url";
    public static final String REQUEST_USER_AGENT = "request.user.agent";

    /**
     * you should config [com.github.spy.sea.core.web.servlet.WebApplicationListener] in web application.
     */
    public static String WEB_ROOT = "";

    /**
     * 1KB
     */
    public static long ONE_KB = 1024;

    /**
     * 1MB
     */
    public static long ONE_MB = ONE_KB * 1024;

    /**
     * 1GB
     */
    public static long ONE_GB = ONE_MB * 1024;

    /**
     * 默认 mock key
     */
    public static final String DEFAULT_MOCK_KEY = "sea_mock";

    /**
     * 判断是否有sofa tracer
     */
    public static final boolean HAS_SOFA_TRACER = ClassUtil.has("com.alipay.common.tracer.core.SofaTracer");


    /**
     * Java MiddleWare Log Path,
     * <code>
     * https://github.com/alibaba/logger.api/blob/master/logger.core/src/main/java/com/taobao/middleware/logger/support/LoggerHelper.java#L69
     * </code>
     */
    public static final String JM_LOG_PATH = "JM.LOG.PATH";

    //---private method

    private static void printLogo() {
        StringBuilder logo = new StringBuilder();
        logo.append("\n");
        logo.append("   _____               _____               \n");
        logo.append("  / ____|             / ____|              \n");
        logo.append(" | (___   ___  __ _  | |     ___  _ __ ___ \n");
        logo.append("  \\___ \\ / _ \\/ _` | | |    / _ \\| '__/ _ \\\n");
        logo.append("  ____) |  __/ (_| | | |___| (_) | | |  __/\n");
        logo.append(" |_____/ \\___|\\__,_|  \\_____\\___/|_|  \\___|   Power by SPY.\n");
        System.out.println(logo.toString());
    }
}
