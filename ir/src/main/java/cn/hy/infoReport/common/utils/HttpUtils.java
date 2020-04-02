package cn.hy.infoReport.common.utils;

import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * http协议相关utils
 */
public class HttpUtils {

    /**
     * 获取浏览器类型
     * @param request
     * @return
     */
    public static String getBrowser(HttpServletRequest request) {
        String useragent = request.getHeader("USER-AGENT").toLowerCase();
        if (useragent.contains("msie") || useragent.contains("trident") || useragent.contains("edge"))
            return "MICRO";
        if (useragent.contains("firefox"))
            return "FF";
        if (useragent.contains("safari"))
            return "SF";
        return null;
    }

    /**
     * 获取正确编码的文件名
     * @return
     */
    public static String getEncodeFileName(HttpServletRequest request, String fileName) {
        String browser = getBrowser(request);
        if ("MICRO".equals(browser)) {
            try {
                return URLEncoder.encode(fileName, "UTF-8");
            } catch (Exception e) {
                return "";
            }
        } else {
            return new String(fileName.getBytes(), StandardCharsets.ISO_8859_1);
        }
    }
}
