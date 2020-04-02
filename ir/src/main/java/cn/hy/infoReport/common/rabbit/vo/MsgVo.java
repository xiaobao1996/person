package cn.hy.infoReport.common.rabbit.vo;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 消息vo
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class MsgVo {

    private String id;
    private String schoolId;


    public static List<MsgVo> from(String msg) {
        if (StringUtils.isBlank(msg)) {
            return null;
        }

        try {
            return JSONObject.parseArray(msg, MsgVo.class);
        } catch (Exception e) {log.error(e.getMessage());}

        return null;
    }
}
