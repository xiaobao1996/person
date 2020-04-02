package cn.hy.infoReport.common.utils;


import cn.hy.infoReport.common.constant.ProjectConstant;
import cn.hy.infoReport.common.entity.SysUser;
import cn.hy.infoReport.common.permission.PermissionConstant;
import cn.hy.pms.thrift.SysMenuPermissionThrift;
import cn.hy.pms.thrift.utils.ThriftUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.jasig.cas.client.util.AbstractCasFilter.CONST_CAS_ASSERTION;

/**
 * 用户相关utils
 */
public class UserUtils {



    /**
     * 获取登录用户信息
     * @return
     */
    public static SysUser getSessionUser() {
        Object sysUser = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getAttribute(ProjectConstant.sessionKey);
        return sysUser == null ? null : ((SysUser) sysUser);
    }


    /**
     * 获取assertion
     * @return
     */
    private static Assertion getAssertion() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }

        return (Assertion) session.getAttribute(CONST_CAS_ASSERTION);
    }


    /**
     * 获取用户id
     * @return
     */
    public static String getUserId() {
//        return "002185CE-3FF9-4E6E-830E-42D6E78A040E";
        Assertion assertion = getAssertion();
        if (assertion == null || assertion.getPrincipal() == null || assertion.getPrincipal().getAttributes() == null || !assertion.getPrincipal().getAttributes().containsKey("userId")) {
            return null;
        }
        return (String) assertion.getPrincipal().getAttributes().get("userId");
    }

    /**
     * 获取用户登录名
     * @return
     */
    public static String getUsername() {
        Assertion assertion = getAssertion();
        if (assertion == null || assertion.getPrincipal() == null || assertion.getPrincipal().getAttributes() == null || !assertion.getPrincipal().getAttributes().containsKey("username")) {
            return null;
        }
        return (String) assertion.getPrincipal().getAttributes().get("username");
    }

    /**
     * 获取用户的权限列表
     * @param schoolId
     * @param appCode
     * @return
     */
    public static Set<String> getUserPermSet(String schoolId, String appCode) throws TException {
        String userId = getUserId();
        List<SysMenuPermissionThrift> smpList = ThriftUtils.findMenuPermByUserIdAndSchoolIdAndAppCode(userId, schoolId, appCode);
        Set<String> permissionSet = null;
        if (CollectionUtils.isEmpty(smpList)) {
            permissionSet = Collections.singleton(PermissionConstant.NONE_PERMISSION);
        } else {
            permissionSet = smpList.stream().filter((smp) -> StringUtils.isNotBlank(smp.getPermission()))
                    .map(SysMenuPermissionThrift::getPermission).collect(Collectors.toSet());
        }
        return permissionSet;
    }

}
