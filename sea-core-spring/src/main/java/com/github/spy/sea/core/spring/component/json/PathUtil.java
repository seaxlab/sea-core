package com.github.spy.sea.core.spring.component.json;

import com.github.spy.sea.core.model.KeyValuePair;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/5/28
 * @since 1.0
 */
@Slf4j
public class PathUtil {

    private static final Map<String, List<FieldBlock>> pathCache = new ConcurrentHashMap<>();

    public static Map<String, String> expand(Object root, String prefix) {
        Map<String, String> result = new HashMap<>();

        Queue<KeyValuePair<String, Object>> queue = new LinkedList<>();
        queue.offer(new KeyValuePair<>(prefix, root));

        KeyValuePair<String, Object> pair;
        while ((pair = queue.poll()) != null) {
            String path = pair.getKey();
            Object obj = pair.getValue();
            if (obj instanceof Map) {
                for (Map.Entry<String, Object> entry : ((Map<String, Object>) obj).entrySet()) {
                    queue.offer(new KeyValuePair<>((path == null ? "" : path + ".") + entry.getKey(), entry.getValue()));
                }
            } else if (obj instanceof List) {
                List list = (List) obj;
                for (int i = 0; i < list.size(); i++) {
                    queue.offer(new KeyValuePair<>(path + "[" + i + "]", list.get(i)));
                }
            } else {
                result.put(path, String.valueOf(obj));
            }
        }


        return result;
    }

    /**
     * 查找数据
     *
     * @param root 对象(map/list)
     * @param path
     * @return
     */
    public static Object find(Object root, String path) {
        path = path.replace(" ", "");
        if (path.startsWith(".")) {
            path = path.substring(1);
        }

        Object o = root;
        if (!path.isEmpty()) {
            for (FieldBlock block : pathCache.computeIfAbsent(path, PathUtil::compile)) {
                o = block.get(o);
                if (o == null) {
                    return null;
                }
            }
        }
        return o;
    }


    private static List<FieldBlock> compile(String path) {
        List<FieldBlock> fieldBlocks = new ArrayList<>();
        for (String s : path.split("\\.")) {
            int l = s.indexOf("[");
            int r = s.indexOf("]");
            String field = s.trim();
            int arrayIndex = -1;
            if (StringUtils.isBlank(field)) {
                throw new RuntimeException("Path format is incorrect [" + path + "]");
            }
            if (0 <= l && l < r) {
                try {
                    arrayIndex = Integer.parseInt(s.substring(l, r + 1));
                    field = s.substring(0, l).trim();
                } catch (Exception e) {
                    log.error("exception", e);
                    throw new RuntimeException("cannot parse field " + s + ". Path:" + path, e);
                }
            }
            if (!field.matches("[a-zA-Z0-1_$]+")) {
                throw new RuntimeException("Field name format is incorrect " + field + ". Path" + path);
            }
            fieldBlocks.add(new FieldBlock(field, arrayIndex));
        }
        return fieldBlocks;
    }


    @AllArgsConstructor
    @Getter
    private static class FieldBlock {
        private final String field;
        private final int index;

        public Object get(Object obj) {
            if (!(obj instanceof Map)) {
                return null;
            }
            Map<String, Object> map = (Map<String, Object>) obj;
            Object o = field.isEmpty() ? field : map.get(field);
            if (index >= 0) {
                if (o instanceof List) {
                    List list = (List) o;
                    if (index < list.size()) {
                        return list.get(index);
                    }
                }
                return null;
            }
            return o;
        }
    }

}
