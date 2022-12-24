package com.github.seaxlab.core.spring.util;

import org.junit.Test;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * test
 *
 * @author spy
 * @version 1.0
 */
public class PropertySourceUtilTest {

  @Test
  public void testGetSubProperties() {

    ConfigurableEnvironment environment = new AbstractEnvironment() {
    };

    MutablePropertySources propertySources = environment.getPropertySources();

    Map<String, Object> source = new HashMap<>();
    Map<String, Object> source2 = new HashMap<>();

    MapPropertySource propertySource = new MapPropertySource("propertySource", source);
    MapPropertySource propertySource2 = new MapPropertySource("propertySource2", source2);

    propertySources.addLast(propertySource);
    propertySources.addLast(propertySource2);

    Map<String, Object> result = PropertySourceUtil.getSubProperties(propertySources, "user");

    assertEquals(Collections.emptyMap(), result);

    source.put("age", "31");
    source.put("user.name", "Mercy");
    source.put("user.age", "${age}");

    source2.put("user.name", "mercyblitz");
    source2.put("user.age", "32");

    Map<String, Object> expected = new HashMap<String, Object>();
    expected.put("name", "Mercy");
    expected.put("age", "31");

    assertEquals(expected, PropertySourceUtil.getSubProperties((Iterable) propertySources, "user"));
    assertEquals(expected, PropertySourceUtil.getSubProperties(environment, "user"));
    assertEquals(expected, PropertySourceUtil.getSubProperties(propertySources, "user"));
    assertEquals(expected, PropertySourceUtil.getSubProperties(propertySources, environment, "user"));

    result = PropertySourceUtil.getSubProperties(propertySources, "");
    assertEquals(Collections.emptyMap(), result);

    result = PropertySourceUtil.getSubProperties(propertySources, "no-exists");
    assertEquals(Collections.emptyMap(), result);

  }

}
