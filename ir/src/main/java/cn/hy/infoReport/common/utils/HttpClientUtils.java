package cn.hy.infoReport.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * http util
 */
public class HttpClientUtils {

    /**
     * get请求，带自定义的header
     * @param url
     * @param headerMap
     * @param charset
     * @return
     */
    public static String getWithHeaders(String url, Map<String, String> headerMap, String charset) {
        HttpGet httpGet = new HttpGet(url);
        if (!CollectionUtils.isEmpty(headerMap)) {
            Set<Map.Entry<String, String>> entries = headerMap.entrySet();
            entries.forEach((eTmp) -> {
                httpGet.setHeader(eTmp.getKey(), eTmp.getValue());
            });
        }
        return executeRequest(httpGet, charset);
    }

    /**
     * http的post请求，传递map格式参数
     */
    public static String post(String url, Map<String, String> dataMap, String charset) {
        HttpPost httpPost = new HttpPost(url);
        try {
            if (dataMap != null){
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, charset);
                formEntity.setContentEncoding(charset);
                httpPost.setEntity(formEntity);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return executeRequest(httpPost, charset);
    }

    /**
     * http的post请求，传递map格式参数
     */
    public static String postWithHeaders(String url, Map<String, String> dataMap, Map<String, String> headerMap, String charset) {
        HttpPost httpPost = new HttpPost(url);
        try {
            if (dataMap != null){
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
                UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nvps, charset);
                formEntity.setContentEncoding(charset);
                httpPost.setEntity(formEntity);
            }

            if (!CollectionUtils.isEmpty(headerMap)) {
                Set<Map.Entry<String, String>> entries = headerMap.entrySet();
                entries.forEach((eTmp) -> {
                    httpPost.setHeader(eTmp.getKey(), eTmp.getValue());
                });
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return executeRequest(httpPost, charset);
    }

    /**
     * 执行http请求，传递json数据参数
     * @param url
     * @param jsonParamStr
     * @param charset
     * @return
     */
    public static String post(String url, String jsonParamStr, String charset) {
        HttpPost httpPost = new HttpPost(url);
        HttpEntity httpEntity = new StringEntity(jsonParamStr, ContentType.APPLICATION_JSON);
        httpPost.setEntity(httpEntity);
        return executeRequest(httpPost, charset);
    }

    /**
     * 执行http get请求
     * @return
     */
    public static String get(String url, String charset) {
        HttpGet httpGet = new HttpGet(url);
        return executeRequest(httpGet, charset);
    }

    /**
     * 执行一个http请求，传递HttpGet或HttpPost参数
     */
    public static String executeRequest(HttpUriRequest httpRequest, String charset) {
        CloseableHttpClient httpclient;
        if ("https".equals(httpRequest.getURI().getScheme())){
            httpclient = createSSLInsecureClient();
        }else{
            httpclient = HttpClients.createDefault();
        }
        String result = "";
        try {
            try {
                CloseableHttpResponse response = httpclient.execute(httpRequest);
                HttpEntity entity = null;
                try {
                    entity = response.getEntity();
                    result = EntityUtils.toString(entity, charset);
                } finally {
                    EntityUtils.consume(entity);
                    response.close();
                }
            } finally {
                httpclient.close();
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
        return result;
    }

    /**
     * 创建 SSL连接
     */
    public static CloseableHttpClient createSSLInsecureClient() {
        try {
            SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return HttpClients.custom().setSSLSocketFactory(sslsf).build();
        } catch (GeneralSecurityException ex) {
            throw new RuntimeException(ex);
        }
    }

}
