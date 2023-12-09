package com.github.seaxlab.core.dal.sql;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2023/12/1
 * @since 1.0
 */
@Slf4j
public class SqlUtil {

  private static final String STR_DATETIME = "Fri Dec 01 14:26:45 CST 2023";
  //
  private static final String SPLIT_INSERT = ", \\[";
  private static final String SPLIT_UPDATE = " \\[";
  private static final String SPLIT_SELECT = "ARGS: \\[";

  //
  private static final String PREFIX_INSERT = "INSERT_SQL: ";
  private static final String PREFIX_UPDATE = "UPDATE_SQL : ";
  private static final String PREFIX_SELECT = "QUERY_SQL";


  public static String parse(String content) {
    if (StringUtils.isBlank(content)) {
      return "";
    }

    if (content.toUpperCase().contains("INSERT ")) {
      return parseInsert(content);
    }
    if (content.toUpperCase().contains("UPDATE ")) {
      return parseUpdate(content);
    }
    if (content.toUpperCase().contains("SELECT ")) {
      return parseQuery(content);
    }

    return "";
  }


  public static String parseQuery(String content) {
    if (StringUtils.isBlank(content)) {
      return "";
    }

    int index = content.indexOf(", COST");
    if (index != -1) {
      content = content.substring(0, index);
    }

    String[] sqls = content.split(SPLIT_SELECT);
    String sql = StringUtils.trim(sqls[0]);
    String args = StringUtils.trim(sqls[1]);
    args = args.substring(0, args.length() - 1);

    String[] values = args.split(",");

    for (String value : values) {
      sql = StringUtils.replaceOnce(sql, "?", "'" + parseValue(value) + "'");
    }
    return sql;
  }

  public static String parseInsert(String content) {
    //"INSERT INTO";
    if (StringUtils.isBlank(content)) {
      return "";
    }
    if (content.startsWith(PREFIX_INSERT)) {
      content = content.replace(PREFIX_INSERT, "");
    }

    String[] sqls = content.split(SPLIT_INSERT);
    String sql = StringUtils.trim(sqls[0]);
    String args = StringUtils.trim(sqls[1]);
    args = args.substring(0, args.length() - 1);

    String[] values = args.split(",");

    for (String value : values) {
      sql = StringUtils.replaceOnce(sql, "?", "'" + parseValue(value) + "'");
    }
    return sql;
  }

  public static String parseUpdate(String content) {
    if (StringUtils.isBlank(content)) {
      return "";
    }
    if (content.startsWith(PREFIX_UPDATE)) {
      content = content.replace(PREFIX_UPDATE, "");
    }

    String[] sqls = content.split(SPLIT_UPDATE);
    String sql = StringUtils.trim(sqls[0]);
    String args = StringUtils.trim(sqls[1]);
    args = args.substring(0, args.length() - 1);

    String[] values = args.split(",");

    for (String value : values) {
      sql = StringUtils.replaceOnce(sql, "?", "'" + parseValue(value) + "'");
    }
    return sql;
  }

  //-----------------------private--------------------------------

  private static String parseValue(String value) {
    value = StringUtils.trim(value);

    // check date
    if (value.length() == STR_DATETIME.length() && value.indexOf(" CST ") > 0 && value.indexOf(":") > 0) {
      try {
        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
        Date datetime = sdf.parse(value);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        value = sdf2.format(datetime);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    return value;
  }

}
