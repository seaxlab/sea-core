package com.github.seaxlab.core.common;

import com.github.seaxlab.core.util.ClassUtil;
import com.github.seaxlab.core.util.JvmUtil;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * sea core const
 *
 * @author spy
 * @version 1.0 2019-05-14
 * @since 1.0
 */
public class CoreConst {

  private static final Logger log = LoggerFactory.getLogger(CoreConst.class);

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
   * console session
   */
  public static final String SYS_SESSION_CONSOLE = "SYS_SESSION_CONSOLE";

  /**
   * app session.
   */
  public static final String SYS_SESSION_APP = "SYS_SESSION_APP";

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
   * true str
   */
  public static final String TRUE_STR = "true";

  /**
   * false str
   */
  public static final String FALSE_STR = "false";

  /**
   * status=1
   */
  public static final Integer STATUS_YES = 1;

  /**
   * status=0
   */
  public static final Integer STATUS_NO = 0;

  /**
   * 请求Id
   */
  public static final String REQUEST_ID = "requestId";

  /**
   * 链路id
   */
  public static final String TRACE_ID = "traceId";

  /**
   * not applicable
   */
  public static final String NOT_APPLICABLE = "N/A";

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

  public static final int PAGE_SIZE_200 = 200;
  public static final int PAGE_SIZE_500 = 500;
  public static final int PAGE_SIZE_1000 = 1000;
  public static final int PAGE_SIZE_2000 = 2000;

  /**
   * 当前程序运行模式, 不要使用sea.dev.mode,太长了
   */
  public static final String SEA_ENV = "sea.env";


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
   * you should config [com.github.seaxlab.core.web.servlet.WebApplicationListener] in web application.
   */
  public static String WEB_ROOT = ""; // NOSONAR

  /**
   * 1KB
   */
  public static final long ONE_KB = 1024;

  /**
   * 1MB
   */
  public static final long ONE_MB = ONE_KB * 1024;

  /**
   * 1GB
   */
  public static final long ONE_GB = ONE_MB * 1024;

  /**
   * 默认 mock key
   */
  public static final String DEFAULT_MOCK_KEY = "sea_mock";

  /**
   * 判断是否有sofa tracer
   */
  public static final boolean HAS_SOFA_TRACER = ClassUtil.has("com.alipay.common.tracer.core.SofaTracer", false);

  /**
   * 最大日期跨度30天
   */
  public static final Integer MAX_DATE_RANGE_30 = 30;

  /**
   * 最大日期跨度60天
   */
  public static final Integer MAX_DATE_RANGE_60 = 60;

  /**
   * 最大日期跨度90天
   */
  public static final Integer MAX_DATE_RANGE_90 = 90;

  /**
   * 最大日期跨度180天
   */
  public static final Integer MAX_DATE_RANGE_180 = 180;

  /**
   * 最大日期跨度365天
   */
  public static final Integer MAX_DATE_RANGE_ONE_YEAR = 365;

  /**
   * <p>总共尝试3次</p>
   * loop=1开始，++loop<= DEFAULT_RETRY_COUNT
   */
  public static final int DEFAULT_RETRY_COUNT = 3;

  public static final int DEFAULT_RETRY_COUNT_1 = 1;
  public static final int DEFAULT_RETRY_COUNT_2 = 2;
  public static final int DEFAULT_RETRY_COUNT_3 = 3;
  public static final int DEFAULT_RETRY_COUNT_4 = 4;
  public static final int DEFAULT_RETRY_COUNT_5 = 5;
  public static final int DEFAULT_RETRY_COUNT_6 = 6;

  // number
  public static final int NUMBER_0 = 0;
  public static final int NUMBER_1 = 1;
  public static final int NUMBER_2 = 2;
  public static final int NUMBER_3 = 3;
  public static final int NUMBER_4 = 4;
  public static final int NUMBER_5 = 5;
  public static final int NUMBER_6 = 6;
  public static final int NUMBER_7 = 7;
  public static final int NUMBER_8 = 8;
  public static final int NUMBER_9 = 9;
  public static final int NUMBER_10 = 10;
  public static final int NUMBER_20 = 20;
  public static final int NUMBER_30 = 30;
  public static final int NUMBER_50 = 50;
  public static final int NUMBER_60 = 60;
  public static final int NUMBER_90 = 90;
  public static final int NUMBER_180 = 180;
  public static final int NUMBER_365 = 365;
  //
  public static final int NUMBER_100 = 100;
  public static final int NUMBER_200 = 200;
  public static final int NUMBER_500 = 500;
  public static final int NUMBER_1000 = 1000;
  public static final int NUMBER_2000 = 2000;
  public static final int NUMBER_5000 = 5000;
  public static final int NUMBER_10000 = 10000;

  // lock time
  public static final long LOCK_TIME_30_SECOND = 30 * 1000L;
  public static final long LOCK_TIME_1_MINUTE = 60 * 1000L;
  public static final long LOCK_TIME_2_MINUTE = 2 * LOCK_TIME_1_MINUTE;
  public static final long LOCK_TIME_5_MINUTE = 5 * LOCK_TIME_1_MINUTE;
  public static final long LOCK_TIME_10_MINUTE = 10 * LOCK_TIME_1_MINUTE;
  public static final long LOCK_TIME_30_MINUTE = 30 * LOCK_TIME_1_MINUTE;
  public static final long LOCK_TIME_1_HOUR = 60 * LOCK_TIME_1_MINUTE;

  /**
   * 业务单元使用线程池的默认最大线程池
   */
  public static final int DEFAULT_BIZ_THREAD_POOL_SIZE = 4;

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
    System.out.println(logo); // NOSONAR
  }
}
