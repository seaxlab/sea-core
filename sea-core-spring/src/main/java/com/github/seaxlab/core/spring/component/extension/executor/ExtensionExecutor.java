package com.github.seaxlab.core.spring.component.extension.executor;

import com.github.seaxlab.core.exception.BaseAppException;
import com.github.seaxlab.core.exception.ErrorMessageEnum;
import com.github.seaxlab.core.spring.component.extension.model.BizScenario;
import com.github.seaxlab.core.spring.component.extension.model.ExtensionCoordinate;
import com.github.seaxlab.core.spring.component.extension.register.ExtensionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ExtensionExecutor
 *
 * @author fulan.zjf 2017-11-05
 */
@Slf4j
public class ExtensionExecutor extends AbstractComponentExecutor {

  @Autowired
  private ExtensionRepository extensionRepository;

  /**
   * 查找扩展点组件
   * <pre>
   * 查找说明：
   * 如果用户业务场景身份（bizScenarioUniqueIdentity）是"ali.tmall.supermarket"
   * 业务执行查找路径逻辑如下：
   * 1，首先尝试通过"ali.tmall.supermarket"获得扩展名，如果获得则返回。
   * 2，循环尝试通过"ali.tmall"获取扩展名，如果获取则返回。
   * 3，循环尝试通过"ali"获取扩展名，如果获取则返回。
   * 4，如果找不到，尝试默认扩展名。
   *</pre>
   * @param targetClz   扩展点目标类
   * @param bizScenario 业务场景
   * @return 扩展点组件
   */
  @Override
  public <C> C locateComponent(Class<C> targetClz, BizScenario bizScenario) {
    C extension = locateExtension(targetClz, bizScenario);
    if (log.isDebugEnabled()) {
      log.debug("[Located Extension]: {}", extension.getClass().getSimpleName());
    }
    return extension;
  }

  /**
   * if the bizScenarioUniqueIdentity is "ali.tmall.88vip"
   * <pre>
   * the search path is as below:
   * 1、first try to get extension by "ali.tmall.88vip", if get, return it.
   * 2、second try to get extension by "ali.tmall.#defaultScenario#", if get, return it.
   * 3、third try to get extension by "ali.#defaultUseCase#.#defaultScenario#", if get, return it.
   * 4、if not found, try the default extension
   * </pre>
   * @param targetClz
   */
  protected <Ext> Ext locateExtension(Class<Ext> targetClz, BizScenario bizScenario) {
    checkNull(bizScenario);

    Ext extension;
    if (log.isDebugEnabled()) {
      log.debug("BizScenario in locateExtension is : {}", bizScenario.getUniqueIdentity());
    }
    // first try with full namespace
    extension = firstTry(targetClz, bizScenario);
    if (extension != null) {
      return extension;
    }

    // second try with default scenario
    extension = secondTry(targetClz, bizScenario);
    if (extension != null) {
      return extension;
    }

    // third try with default use case + default scenario
    extension = defaultUseCaseTry(targetClz, bizScenario);
    if (extension != null) {
      return extension;
    }

    //throw new BaseAppException(ErrorMessageEnum.EXTENSION_NOT_EXIST, "Can not find extension with ExtensionPoint: " + targetClz + " BizScenario:" + bizScenario.getUniqueIdentity());
    throw new BaseAppException(ErrorMessageEnum.EXTENSION_NOT_EXIST, targetClz, bizScenario.getUniqueIdentity());
  }

  /**
   * first try with full namespace
   * <p>
   * example:  biz1.useCase1.scenario1
   */
  private <Ext> Ext firstTry(Class<Ext> targetClz, BizScenario bizScenario) {
    if (log.isDebugEnabled()) {
      log.debug("First trying with {}", bizScenario.getUniqueIdentity());
    }
    return locate(targetClz.getName(), bizScenario.getUniqueIdentity());
  }

  /**
   * second try with default scenario
   * <p>
   * example:  biz1.useCase1.#defaultScenario#
   */
  private <Ext> Ext secondTry(Class<Ext> targetClz, BizScenario bizScenario) {
    if (log.isDebugEnabled()) {
      log.debug("Second trying with {}", bizScenario.getIdentityWithDefaultScenario());
    }
    return locate(targetClz.getName(), bizScenario.getIdentityWithDefaultScenario());
  }

  /**
   * third try with default use case + default scenario
   * <p>
   * example:  biz1.#defaultUseCase#.#defaultScenario#
   */
  private <Ext> Ext defaultUseCaseTry(Class<Ext> targetClz, BizScenario bizScenario) {
    if (log.isDebugEnabled()) {
      log.debug("Third trying with " + bizScenario.getIdentityWithDefaultUseCase());
    }
    return locate(targetClz.getName(), bizScenario.getIdentityWithDefaultUseCase());
  }

  private <Ext> Ext locate(String name, String uniqueIdentity) {
    final Ext ext = (Ext) extensionRepository.getExtensionRepo().get(new ExtensionCoordinate(name, uniqueIdentity));
    if (ext != null) {
      log.info("active extension uniqueIdentity={}", uniqueIdentity);
    }
    return ext;
  }

  private void checkNull(BizScenario bizScenario) {
    if (bizScenario == null) {
      throw new BaseAppException(ErrorMessageEnum.EXTENSION_BIZ_SCENARIO_NOT_EXIST);
    }
  }

}
