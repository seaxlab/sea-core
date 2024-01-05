package com.github.seaxlab.core.util.model.person;

import java.util.Date;
import lombok.Data;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/11/13
 * @since 1.0
 */
@Data
public class Birthday {

  private int year;
  private int month;
  private int day;
  private Date date;
  private String dateStr;
}
