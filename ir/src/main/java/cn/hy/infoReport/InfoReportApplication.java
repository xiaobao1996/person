package cn.hy.infoReport;


import cn.hy.infoReport.common.constant.ProjectConstant;
import cn.hy.infoReport.common.task.ClassSyncTask;
import cn.hy.infoReport.common.task.StaffSyncTask;
import cn.hy.infoReport.common.task.StudentSyncTask;
import cn.hy.pms.thrift.utils.ThriftUtils;
import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.property.SsoProperties;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.Cas30ProxyReceivingTicketValidationFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootApplication
@EnableScheduling
@MapperScan("cn.hy.infoReport.common.mapper")
public class InfoReportApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        ConfigurableApplicationContext applicationContext = SpringApplication.run(InfoReportApplication.class, args);
        ClassSyncTask classSyncTask = applicationContext.getBean(ClassSyncTask.class);
        classSyncTask.sync();

        StaffSyncTask staffSyncTask = applicationContext.getBean(StaffSyncTask.class);
        staffSyncTask.sync();

        StudentSyncTask studentSyncTask = applicationContext.getBean(StudentSyncTask.class);
        studentSyncTask.sync();

    }
}

@Configuration
class WebConfig implements WebMvcConfigurer {

    @Autowired
    private Environment environment;


    @PostConstruct
    public void initProperties() {
        String ssoServerPath = environment.getProperty("sso.properties.sso-server-path");
        String serviceName = environment.getProperty("sso.properties.service-name");
        SsoProperties.init(serviceName, ssoServerPath);

        String thriftHost = environment.getProperty("core.config.thrift-host");
        Integer thriftPort = Integer.valueOf(environment.getProperty("core.config.thrift-port", "10299"));
        ThriftUtils.init(thriftHost, thriftPort);
        String dtApi = environment.getProperty("core.config.dt-api");
        ProjectConstant.setDtApi(dtApi);
    }

    @Bean
    public SessionTrackingConfigListener sessionTrackingConfigListener() {
        SessionTrackingConfigListener listener = new SessionTrackingConfigListener();
        return listener;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(ProjectConstant.fileVisitPrefix + "/**").addResourceLocations("file:" + ProjectConstant.fileUploadLocation + "/");
    }

    @Bean
    public Converter<String, Date> addNewConvert() {
        return new Converter<String, Date>() {
            @Override
            public Date convert(String source) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = null;
                try {
                    date = sdf.parse(source);
                } catch (Exception e) {
                }
                return date;
            }
        };
    }


    /**
     * 开发方便，先关闭跨域安全
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }


    /*********************       单点登陆配置      ********************/

    /**
     * 单点登出过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean<SingleSignOutFilter> singleSignOutFilter() {
        FilterRegistrationBean<SingleSignOutFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new SingleSignOutFilter());
        registrationBean.setUrlPatterns(Collections.singleton("/*"));
        registrationBean.setOrder(1);
        registrationBean.setInitParameters(Collections.singletonMap("casServerUrlPrefix", SsoProperties.casServerUrlPrefix));
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<Cas30ProxyReceivingTicketValidationFilter> cas30ProxyReceivingTicketValidationFilter() {
        FilterRegistrationBean<Cas30ProxyReceivingTicketValidationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new Cas30ProxyReceivingTicketValidationFilter());
        registrationBean.setUrlPatterns(Collections.singleton("/auth/checkLoginStatus"));
        Map<String, String> initParam = new HashMap<>(5);
        initParam.put("casServerUrlPrefix", SsoProperties.casServerUrlPrefix);
        initParam.put("serverName", SsoProperties.serviceName);
        initParam.put("redirectAfterValidation", "false");
        initParam.put("useSession", "true");
        initParam.put("authn_method", "mfa-duo");
        registrationBean.setInitParameters(initParam);
        registrationBean.setOrder(2);

        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> authenticationFilter() {
        FilterRegistrationBean<AuthenticationFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuthenticationFilter());
        registrationBean.setUrlPatterns(Collections.singleton("/*"));

        Map<String, String> initParams = new HashMap<>(4);
        initParams.put("casServerLoginUrl", SsoProperties.casServerLoginUrl);
        initParams.put("serverName", SsoProperties.serviceName);
        initParams.put("ignorePattern", "(/auth/logout|favicon.ico|/pub/*|/api/*|/sync/*|/upload/*|/busi/*|" + ProjectConstant.fileVisitPrefix + "/*)");
//        initParams.put("ignorePattern", "(/auth/logout|favicon.ico|/pub/*|/api/*|/sync/*|/upload/*|/busi/irTemperatureReport/*|" + ProjectConstant.fileVisitPrefix + "/*)");
        registrationBean.setInitParameters(initParams);
        registrationBean.setOrder(3);

        return registrationBean;
    }



    @Bean
    public ServletContextInitializer initializer() {
        return new ServletContextInitializer() {

            @Override
            public void onStartup(ServletContext servletContext) throws ServletException {
                servletContext.setInitParameter("casServerLogoutUrl", SsoProperties.casServerLogoutUrl);
            }
        };
    }

    @Bean
    public FilterRegistrationBean<HttpServletRequestWrapperFilter> httpServletRequestWrapperFilter() {
        FilterRegistrationBean<HttpServletRequestWrapperFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new HttpServletRequestWrapperFilter());
        registrationBean.setUrlPatterns(Collections.singleton("/*"));
        registrationBean.setOrder(4);
        return registrationBean;
    }

    @Bean
    public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
        ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener = new ServletListenerRegistrationBean<>();
        singleSignOutHttpSessionListener.setListener(new SingleSignOutHttpSessionListener());
        return singleSignOutHttpSessionListener;
    }
}
