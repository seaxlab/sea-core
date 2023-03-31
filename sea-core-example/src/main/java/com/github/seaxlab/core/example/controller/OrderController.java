package com.github.seaxlab.core.example.controller;

import com.github.seaxlab.core.example.controller.dto.order.BaseOrderDTO;
import com.github.seaxlab.core.example.controller.dto.order.MerchantOrderDTO;
import com.github.seaxlab.core.example.controller.dto.order.UserOrderDTO;
import com.github.seaxlab.core.model.Result;
import com.github.seaxlab.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * order controller
 *
 * @author spy
 * @version 1.0 2023/03/30
 * @since 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {

  @GetMapping("/get")
  public Result<? extends BaseOrderDTO> get() {
    if (RandomUtil.nextBoolean()) {
      UserOrderDTO order = new UserOrderDTO();
      order.setOrderNo(RandomUtil.nonceStr());
      order.setUserName("userOrder");
      return Result.success(order);
    } else {
      MerchantOrderDTO order = new MerchantOrderDTO();
      order.setOrderNo(RandomUtil.nonceStr());
      order.setMerchantName("merchantOrder");
      return Result.success(order);
    }

  }


}
