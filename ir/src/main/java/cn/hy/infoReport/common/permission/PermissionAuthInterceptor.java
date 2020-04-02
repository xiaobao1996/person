package cn.hy.infoReport.common.permission;



import cn.hy.infoReport.common.constant.ProjectConstant;
import cn.hy.infoReport.common.enums.ResultCode;
import cn.hy.infoReport.common.utils.UserUtils;
import cn.hy.infoReport.common.vo.Result;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.property.SsoProperties;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Set;

import static org.jasig.cas.client.util.AbstractCasFilter.CONST_CAS_ASSERTION;

/**
 * controller 权限注解拦截器
 */
public class PermissionAuthInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (! (handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        SupperAdminAccess saaAnno = ((HandlerMethod) handler).getMethodAnnotation(SupperAdminAccess.class);
        if (saaAnno != null) {
            buildResponse(request, response);
            response.getWriter().write(JSONObject.toJSONString(new Result(ResultCode.LACK_PERMISSION.getCode(), ResultCode.LACK_PERMISSION.getMessage(), null)));
            return false;
        }

        RequirePermissions rpAnno = handlerMethod.getMethodAnnotation(RequirePermissions.class);
        //没有配置权限注解
        if (rpAnno == null) {
            return true;
        }

        //权限注解是否配置了权限字符串
        String[] requirePerms = rpAnno.value();
        boolean hasPerm = false;
        if (requirePerms.length > 0) {
            for (String permTmp : requirePerms) {
                if (StringUtils.isNotBlank(permTmp)) {
                    hasPerm = true;
                    break;
                }
            }
        }
        if (!hasPerm) {
            return true;
        }

        HttpSession session = request.getSession(false);
        String platform = request.getParameter("platform");
        //登陆信息过期
        if (session == null) {
            buildResponse(request, response);
            response.getWriter().write(JSONObject.toJSONString(new Result(ResultCode.AUTHENTICATION_EXPIRED.getCode(), "认证信息已过期，请重新登陆！",
                    SsoProperties.casServerLoginUrl)));
            return false;
        }
        Assertion assertion = (Assertion) session.getAttribute(CONST_CAS_ASSERTION);
        if (assertion == null || assertion.getPrincipal() == null || assertion.getPrincipal().getAttributes() == null || !assertion.getPrincipal().getAttributes().containsKey("userId")) {
            buildResponse(request, response);
            response.getWriter().write(JSONObject.toJSONString(new Result(ResultCode.AUTHENTICATION_EXPIRED.getCode(), "认证信息已过期，请重新登陆！",
                    SsoProperties.casServerLoginUrl)));
            return false;
        }

        String schoolId = request.getParameter("schoolToken");
        //进行权限验证
        Set<String> permissionSet =  UserUtils.getUserPermSet(schoolId, ProjectConstant.APP_CODE);

        Logical logical = rpAnno.logical();
        //判断权限是否通过
        boolean permAccess = false;
        for (String rpTmp : requirePerms) {
            boolean matched = false;
            for (String pTmp : permissionSet) {
                if (pTmp.equals(rpTmp)) {
                    matched = true;
                    break;
                }
            }
            if (logical.equals(Logical.OR)) {
                if (matched) {
                    permAccess = true;
                    break;
                }
            } else {
                if (!matched) {
                    permAccess = false;
                    break;
                } else {
                    permAccess = true;
                }
            }
        }
        //权限未通过
        if (!permAccess) {
            buildResponse(request, response);
            response.getWriter().write(JSONObject.toJSONString(new Result(ResultCode.LACK_PERMISSION.getCode(), ResultCode.LACK_PERMISSION.getMessage(), null)));
            return false;
        }


        return true;
    }


    /**
     * 封装response，用于直接返回
     * @param request
     * @param response
     */
    private void buildResponse(HttpServletRequest request, HttpServletResponse response) {
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers","Origin,Content-Type,Accept,token,X-Requested-With");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Content-type", "application/json;charset=UTF-8");
    }
}
