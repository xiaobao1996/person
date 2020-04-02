package cn.hy.infoReport.common.utils;

import java.util.UUID;

/**
 * Created by lyw on 2017/12/7.
 */
public class IdUtils {

    public static String newId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}
