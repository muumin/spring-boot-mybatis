package example;

import com.microsoft.applicationinsights.web.internal.WebRequestTrackingFilter;
import com.microsoft.applicationinsights.web.spring.RequestNameHandlerInterceptorAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public HandlerInterceptorAdapter requestNameHandlerInterceptor() {
        return new RequestNameHandlerInterceptorAdapter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.debug("addInterceptors");
        registry.addInterceptor(requestNameHandlerInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public FilterRegistrationBean webRequestTrackingFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebRequestTrackingFilter());
        bean.addUrlPatterns("/*");
        bean.setOrder(1);
        return bean;
    }
}
