package com.github.seaxlab.core.support.oss;

import com.github.seaxlab.core.support.oss.manager.impl.HuaWeiCloudOssManager;
import com.obs.services.ObsClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/12/13
 * @since 1.0
 */
@Slf4j
public class HuaWeiCloudOssManagerTest extends BaseOssManagerTest {

  @Before
  public void before() {
    ENDPOINT = "https://27.221.114.245:8443";
    ACCESS_KEY = "WZZJCCRGI08LKFVUW6CL";
    SECRET_KEY = "2mo3ntGirIaPbfZVDpqXbJmbKRzRWjwaJgC5qBxb";
    super.before();

    ObsClient client = new ObsClient(ACCESS_KEY, SECRET_KEY, ENDPOINT);
    ossManager = new HuaWeiCloudOssManager(client);

  }


}
