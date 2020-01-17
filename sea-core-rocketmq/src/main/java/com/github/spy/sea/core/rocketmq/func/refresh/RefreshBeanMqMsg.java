package com.github.spy.sea.core.rocketmq.func.refresh;

import lombok.*;

import java.io.Serializable;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2020/1/17
 * @since 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RefreshBeanMqMsg implements Serializable {

    private String beanName;
}
