package com.github.seaxlab.core.component.ognl.member;

import lombok.extern.slf4j.Slf4j;
import ognl.MemberAccess;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.util.Map;

/**
 * 类中属性访问控制
 * <p>
 * 重要：这里必须实现一个，因为OGNL包中没有默认实现，奇葩的设计
 *
 * @author spy
 * @version 1.0 2019-07-14
 * @since 1.0
 */
@Slf4j
public class DefaultMemberAccess implements MemberAccess {

    public boolean allowPrivateAccess = false;
    public boolean allowProtectedAccess = false;
    public boolean allowPackageProtectedAccess = false;

    public DefaultMemberAccess(boolean allowAllAccess) {
        this(allowAllAccess, allowAllAccess, allowAllAccess);
    }

    public DefaultMemberAccess(boolean allowPrivateAccess, boolean allowProtectedAccess, boolean allowPackageProtectedAccess) {
        super();
        this.allowPrivateAccess = allowPrivateAccess;
        this.allowProtectedAccess = allowProtectedAccess;
        this.allowPackageProtectedAccess = allowPackageProtectedAccess;
    }

    /*===================================================================
        Public methods
      ===================================================================*/
    public boolean getAllowPrivateAccess() {
        return allowPrivateAccess;
    }

    public void setAllowPrivateAccess(boolean value) {
        allowPrivateAccess = value;
    }

    public boolean getAllowProtectedAccess() {
        return allowProtectedAccess;
    }

    public void setAllowProtectedAccess(boolean value) {
        allowProtectedAccess = value;
    }

    public boolean getAllowPackageProtectedAccess() {
        return allowPackageProtectedAccess;
    }

    public void setAllowPackageProtectedAccess(boolean value) {
        allowPackageProtectedAccess = value;
    }

    /*===================================================================
        MemberAccess interface
      ===================================================================*/
    @Override
    public Object setup(Map context, Object target, Member member, String propertyName) {
        Object result = null;

        if (isAccessible(context, target, member, propertyName)) {
            AccessibleObject accessible = (AccessibleObject) member;

            if (!accessible.isAccessible()) {
                result = Boolean.FALSE;
                accessible.setAccessible(true);
            }
        }
        return result;
    }

    public void restore(Map context, Object target, Member member, String propertyName, Object state) {
        if (state != null) {
            final AccessibleObject accessible = (AccessibleObject) member;
            final boolean stateboolean = ((Boolean) state).booleanValue();  // Using twice (avoid unboxing)
            if (!stateboolean) {
                accessible.setAccessible(stateboolean);
            } else {
                throw new IllegalArgumentException("Improper restore state [" + stateboolean + "] for target [" + target +
                        "], member [" + member + "], propertyName [" + propertyName + "]");
            }
        }
    }

    /**
     * Returns true if the given member is accessible or can be made accessible
     * by this object.
     */
    public boolean isAccessible(Map context, Object target, Member member, String propertyName) {
        int modifiers = member.getModifiers();
        boolean result = Modifier.isPublic(modifiers);

        if (!result) {
            if (Modifier.isPrivate(modifiers)) {
                result = getAllowPrivateAccess();
            } else {
                if (Modifier.isProtected(modifiers)) {
                    result = getAllowProtectedAccess();
                } else {
                    result = getAllowPackageProtectedAccess();
                }
            }
        }
        return result;
    }

}
