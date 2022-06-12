package com.github.seaxlab.core.component.transform;

import java.util.List;

/**
 * module name
 *
 * @author spy
 * @version 1.0 2022/2/24
 * @since 1.0
 */
public interface Convert {

    void transform(Object obj, List<FieldRule> rules);
}
