package com.github.seaxlab.core.spring.util;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.PropertyValues;
import org.springframework.mock.env.MockEnvironment;

/**
 * x
 *
 * @author spy
 * @version 1.0
 */
public class PropertyValueUtilTest {

  @Test
  public void testGetSubPropertyValues() {

    MockEnvironment environment = new MockEnvironment();

    PropertyValues propertyValues = PropertyValueUtil.getSubPropertyValues(environment, "user");

    Assert.assertNotNull(propertyValues);

    Assert.assertFalse(propertyValues.contains("name"));
    Assert.assertFalse(propertyValues.contains("age"));

    environment.setProperty("user.name", "Mercy");
    environment.setProperty("user.age", "30");

    propertyValues = PropertyValueUtil.getSubPropertyValues(environment, "user");

    Assert.assertEquals("Mercy", propertyValues.getPropertyValue("name").getValue());
    Assert.assertEquals("30", propertyValues.getPropertyValue("age").getValue());

  }

}
