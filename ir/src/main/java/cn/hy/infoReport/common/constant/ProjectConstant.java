package cn.hy.infoReport.common.constant;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 项目常量
 */
@Component
@ConfigurationProperties(prefix = "core.config")
public class ProjectConstant {
    /**
     * 当前应用编码
     */
    public static final String APP_CODE = "irapp";
    public static final String PROJECT_NAME = "IR";

    /**
     * 管理员权限
     */
    public static final String MANAGER_PERMISSION = "ir:manager";

    /**
     * 用户信息sessionKey
     */
    public static final String LOGIN_USER_SESSION_KEY = PROJECT_NAME + "_LOGIN_USER_KEY";

    /**
     * 文件上传路径
     */
    public static String fileUploadLocation = "/opt/upload/file";
    /**
     * 文件文件访问前缀
     */
    public static String fileVisitPrefix = "/file";

    /**
     * 用户sessionKey
     */
    public static String sessionKey = "LOGIN_USER";

    public static Set<Byte> staffGroupTypeSet;

    /**
     * 需要同步的pms学校id集合
     */
    public static Set<String> pmsSchoolIdSet;

    /**
     * 云桌面api地址
     */
    public static String dtApi;

    public static String getDtApi() {
        return dtApi;
    }

    public static void setDtApi(String dtApi) {
        ProjectConstant.dtApi = dtApi;
    }

    public static String hostName = "";

    public static String tmpDir = "/opt/tmp";

    /**
     * 模板消息commonId
     */
    public static String templateMsgCommonId = "";
    /**
     * 消息发送路径
     */
    public static String templateMsgHost = "";

    public void setStaffGroupType(String staffGroupType) {
        String[] groupTypeArray = staffGroupType.split(",");
        staffGroupTypeSet = Arrays.stream(groupTypeArray).map(Byte::parseByte).collect(Collectors.toSet());
    }

    public void setPmsSchoolIds(String pmsSchoolIds) {
        String[] pmsSchoolIdArray = pmsSchoolIds.split(",");
        pmsSchoolIdSet = Arrays.stream(pmsSchoolIdArray).collect(Collectors.toSet());
    }

    public void setTemplateMsgCommonId(String templateMsgCommonId) {
        ProjectConstant.templateMsgCommonId = templateMsgCommonId;
    }

    public void setTemplateMsgHost(String templateMsgHost) {
        ProjectConstant.templateMsgHost = templateMsgHost;
    }
}
