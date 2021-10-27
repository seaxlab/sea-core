package com.github.spy.sea.core.script.groovy;

import com.github.spy.sea.core.script.AbstractScriptTest;
import groovy.text.SimpleTemplateEngine;
import groovy.text.Template;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2021/10/27
 * @since 1.0
 */
@Slf4j
public class SimpleTemplateEngineTest extends AbstractScriptTest {

    // 添加模版类缓存
    private final static ConcurrentHashMap<String, Template> templateCaches = new ConcurrentHashMap<>();
    private final static ArrayList strTemplates = new ArrayList();

    static {
        strTemplates.add("${user.name}");
        strTemplates.add("${user.code}");
        strTemplates.add("${user.company}");
        strTemplates.add("${user.address}");
        strTemplates.add("${user.message}");
    }

    public static Template getTemplate(String placeHolder) throws IOException, ClassNotFoundException {
        templateCaches.computeIfAbsent(placeHolder, key -> {
            Template template = null;
            try {
                template = new SimpleTemplateEngine().createTemplate(placeHolder);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return template;
        });

        return templateCaches.get(placeHolder);

//        Template template = templateCaches.get(placeHolder);
//        if (template != null) return template;
//        template = new SimpleTemplateEngine().createTemplate(placeHolder);
//        templateCaches.put(placeHolder, template);
//        return template;
    }

    public static String generateMessage(Map map, String placeHolder) {
        String msg = null;
        try {
            msg = getTemplate(placeHolder).make(map).toString();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return msg;
    }

    @Test
    public void test61() throws Exception {
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (; ; ) {
                    Map<String, Object> map = new HashMap<>();
                    int nameSuffix = new Random().nextInt(900) + 100;
                    int index = new Random().nextInt(strTemplates.size());
                    Person userDo = new Person();
                    userDo.setName("TestGroovy" + nameSuffix);
                    userDo.setCode(666);
                    map.put("user", userDo);
                    String placeHolder = (String) strTemplates.get(index);
                    String userName = generateMessage(map, placeHolder);
                    log.info(placeHolder + ": " + userName + Thread.currentThread().getName());
                }

            }).start();
        }
        sleepMinute(2);
    }


}
