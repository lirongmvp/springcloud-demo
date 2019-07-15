package com.lirong.servicehi.error;

import com.lirong.servicehi.filter.BodyReaderHttpServletRequestWrapper;
import com.lirong.servicehi.resp.HttpErrorResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Title: HttpErrorRecord <br>
 * Description: HttpErrorRecord <br>
 * Date: 2019年07月08日
 *
 * @author lirong
 * @version 1.0.0
 * @since jdk8
 */
@Component
@Slf4j
public class HttpErrorRecord {
    @Autowired
    private HttpErrorResp httpErrorResp;

    public void record(Exception e) {
        StringWriter sw = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            log.info("再次获取请求参数,id:{}",request.getParameter("id"));
            String fullUrl = fullUrl(request);
            String method = request.getMethod();
            String body = null;
            if (request instanceof BodyReaderHttpServletRequestWrapper) {
                byte[] s = ((BodyReaderHttpServletRequestWrapper) request).getBody();
                body = new String(s);
            }
            sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw, true));

            System.out.println("打印异常：" + sw);
            HttpError httpError = new HttpError(fullUrl, method, body, sw.toString());
            httpErrorResp.save(httpError);
        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }

    public String fullUrl(HttpServletRequest request) {

        Map<String, String[]> parameterMap = request.getParameterMap();
        List<String> list = new ArrayList<>();
        for (String s : parameterMap.keySet()) {
            String tem = s + "=" + StringUtils.collectionToDelimitedString(Arrays.asList(parameterMap.get(s)), ",");
            list.add(tem);
        }
        String s1 = StringUtils.collectionToDelimitedString(list, "&");
        return request.getRequestURI() + "?" + s1;
    }
}
