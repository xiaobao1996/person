package cn.hy.infoReport.common.permission;

import cn.hy.infoReport.common.constant.ProjectConstant;
import cn.hy.infoReport.common.enums.ResultCode;
import cn.hy.infoReport.common.exception.AuthException;
import cn.hy.infoReport.common.exception.MessageException;
import cn.hy.infoReport.common.utils.UserUtils;
import org.apache.thrift.TException;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpSession;
import java.util.Set;

/**
 * 权限utils
 */
public class PermissionUtils {

    /**
     * 判断是否拥有权限
     * @return
     */
    public static boolean hasPermission(HttpSession session, String schoolId, String permStr) {
        if (session == null) {
            return false;
        }
        try {
            Set<String> permissionSet = UserUtils.getUserPermSet(schoolId, ProjectConstant.APP_CODE);
            if (CollectionUtils.isEmpty(permissionSet)) {
                return false;
            }

            return permissionSet.contains(permStr);
        } catch (TException e) {
            return false;
        }

    }

    /**
     * 判断是否拥有权限,没有权限抛权限异常
     * @return
     */
    public static void permissionValidate(HttpSession session, String schoolId, String permStr) {
        if (session == null) {
            throw new AuthException(ResultCode.LACK_PERMISSION, ResultCode.LACK_PERMISSION.getMessage());
        }

        try {
            Set<String> permissionSet = UserUtils.getUserPermSet(schoolId, ProjectConstant.APP_CODE);
            if (CollectionUtils.isEmpty(permissionSet) || !permissionSet.contains(permStr)) {
                throw new AuthException(ResultCode.LACK_PERMISSION, ResultCode.LACK_PERMISSION.getMessage());
            }

        } catch (TException e) {
            throw new MessageException("获取权限信息失败！");
        }
    }
}
