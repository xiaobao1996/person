package cn.hy.infoReport.module.header;

import cn.hy.infoReport.common.constant.ProjectConstant;
import cn.hy.infoReport.common.enums.ResultCode;
import cn.hy.infoReport.common.utils.HttpClientUtils;
import cn.hy.infoReport.common.utils.UserUtils;
import cn.hy.infoReport.common.vo.Result;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/header")
@RestController
public class HeaderBridgeController {

    /**
     * 获取用户未读消息数量
     * @return
     */
    @RequestMapping("/unreadMsgCount")
    public Result userUnreadMsgCount(@RequestParam String schoolId) {
        Map<String, String> paramsMap = new HashMap<>(4);
        paramsMap.put("schoolId", schoolId);
        paramsMap.put("userId", UserUtils.getUserId());
        String result = HttpClientUtils.post(ProjectConstant.dtApi + "/header/bridge/unreadMsgCount", paramsMap, "UTF-8");
        return JSONObject.parseObject(result, Result.class);
    }


    /**
     * 用户通知消息分页数据
     * @return
     */
    @RequestMapping("/userMsgList")
    public Result userMsgList(@RequestParam Map<String, String> params) {
        params.put("userId", UserUtils.getUserId());
        String result = HttpClientUtils.post(ProjectConstant.dtApi + "/header/bridge/userMsgList", params, "UTF-8");
        return JSONObject.parseObject(result, Result.class);
    }

    /**
     * 更新用户信息
     * @return
     */
    @RequestMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestParam Map<String, String> params, HttpSession session) {
        params.put("userId", UserUtils.getUserId());
        String resultStr = HttpClientUtils.post(ProjectConstant.dtApi + "/header/bridge/updateUserInfo", params, "UTF-8");
        Result result = JSONObject.parseObject(resultStr, Result.class);
        if (result.getCode() == ResultCode.SUCCESS.getCode()) {
            session.removeAttribute(ProjectConstant.LOGIN_USER_SESSION_KEY);
        }
        return result;
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

    /**
     * 更新密码
     * @return
     */
    @RequestMapping("/readUserMsg")
    public Result readUserMsg(@RequestParam Map<String, String> params) {
        params.put("userId", UserUtils.getUserId());
        String result = HttpClientUtils.post(ProjectConstant.dtApi + "/header/bridge/readUserMsg", params, "UTF-8");
        return JSONObject.parseObject(result, Result.class);
    }


}
