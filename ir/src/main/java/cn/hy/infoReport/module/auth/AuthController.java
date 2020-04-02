package cn.hy.infoReport.module.auth;


import cn.hy.infoReport.common.constant.ProjectConstant;
import cn.hy.infoReport.common.utils.HttpClientUtils;
import cn.hy.infoReport.common.utils.UserUtils;
import cn.hy.infoReport.common.vo.Result;
import cn.hy.pms.thrift.SchoolRoleVoThrift;

import cn.hy.pms.thrift.SysUserThrift;
import cn.hy.pms.thrift.utils.ThriftUtils;

import com.alibaba.fastjson.JSONObject;
import org.apache.thrift.TException;
import org.jasig.cas.client.property.SsoProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;


import java.util.List;
import java.util.Map;
import java.util.Set;

import static cn.hy.infoReport.common.constant.ProjectConstant.LOGIN_USER_SESSION_KEY;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 验证登陆状态
     * @return
     */
    @RequestMapping("/checkLoginStatus")
    public Result checkLoginStatus() {
        return Result.success(null);
    }

    /**
     * 登出接口
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public Result logout(HttpSession session) {
        session.invalidate();
        return Result.success(SsoProperties.casServerLogoutUrl);
    }

    /**
     * 获取登陆用户信息
     * @return
     */
    @RequestMapping("/loginUserInfo")
    public Result getLoginUser(HttpSession session) {
        SysUserThrift sysUser = (SysUserThrift) session.getAttribute(LOGIN_USER_SESSION_KEY);
        if (sysUser == null) {
            try {
                sysUser = ThriftUtils.findUserByUserId(UserUtils.getUserId());
            } catch (TException e) {
                logger.error("", e);
                return Result.error("系统异常！", null);
            }
            if (sysUser == null) {
                return Result.error("您的账号不存在或者被停用，请联系管理员！", null);
            }
            session.setAttribute(LOGIN_USER_SESSION_KEY, sysUser);
        }
        return Result.success(sysUser);
    }

    /**
     * 获取当前用户可以操作的学校列表
     * @return
     */
    @RequestMapping("/userSchoolList")
    public Result userSchoolList() throws TException {
        String userId = UserUtils.getUserId();
        List<SchoolRoleVoThrift> srvList = ThriftUtils.findSchoolByUserIdAndAppCode(userId, ProjectConstant.APP_CODE);
        return Result.success(srvList);

    }

    /**
     * 获取用户权限信息
     * @return
     */
    @RequestMapping("/userPermissionList")
    public Result userPermissionList(@RequestParam(value = "schoolToken", required = false) String schoolId, HttpSession session) throws TException {

        Set<String> permissionSet = UserUtils.getUserPermSet(schoolId, ProjectConstant.APP_CODE);

        return Result.success(permissionSet);
    }

    /**
     * 更新密码
     * @return
     */
    @RequestMapping("/updatePwd")
    public Result updatePassword(@RequestParam Map<String, String> params) {
        params.put("userId", UserUtils.getUserId());
        String result = HttpClientUtils.post(ProjectConstant.dtApi + "/header/bridge/updatePwd", params, "UTF-8");
        return JSONObject.parseObject(result, Result.class);
    }
}
