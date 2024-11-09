package cc.icooper.myjavatemplate.gatewayservice.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

@WebFilter(urlPatterns = "/*")
public class LoggingFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse) response);

        // 获取请求地址
        String path = httpRequest.getServletPath();

        // 输出请求来源
        logger.info("Request at {}: {} from {}", LocalDateTime.now(), path, httpRequest.getRemoteAddr());

        long startTime = System.currentTimeMillis();

        try {
            chain.doFilter(request, responseWrapper);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            byte[] responseArray = responseWrapper.getContentAsByteArray();
            if (responseArray.length > 0) {
                try {
                    String responseBody = new String(responseArray, responseWrapper.getCharacterEncoding());
                    logger.info("Response: {}", responseBody);
                } catch (UnsupportedEncodingException e) {
                    logger.error("Failed to parse response body", e);
                }
            }

            // 在请求完成后记录信息
            logger.info("Request completed at {} with status {}, cost {} ms",
                        path,
                        responseWrapper.getStatus(),
                        duration);

            responseWrapper.copyBodyToResponse();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
