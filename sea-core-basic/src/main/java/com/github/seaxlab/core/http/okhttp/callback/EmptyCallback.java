package com.github.seaxlab.core.http.okhttp.callback;

import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2019-08-11
 * @since 1.0
 */
@Slf4j
public class EmptyCallback implements Callback {
  @Override
  public void onFailure(Call call, IOException e) {
    log.error("empty callback fail", e);
  }

  @Override
  public void onResponse(Call call, Response response) throws IOException {
    log.info("empty callback success");

    if (response != null) {
      log.info("response body={}", response.body());
    }

  }
}
