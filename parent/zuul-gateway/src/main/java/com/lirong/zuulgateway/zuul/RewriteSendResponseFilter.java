package com.lirong.zuulgateway.zuul;

import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.post.SendResponseFilter;
import org.springframework.util.StreamUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;


/**
 * Title: RewriteSendResponseFilter <br>
 * Description: RewriteSendResponseFilter <br>
 * Date: 2019年04月16日
 *
 * @author LiRong
 * @version 1.0.0
 * @since jdk8
 */
public class RewriteSendResponseFilter extends SendResponseFilter {

    public RewriteSendResponseFilter(ZuulProperties zuulProperties) {
        super(zuulProperties);
    }

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 800;
    }

    @Override
    public boolean shouldFilter() {
       return true;
    }

    @Override
    public Object run() {
        try {
            writeResponse();
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private void writeResponse() throws Exception{
        RequestContext context = RequestContext.getCurrentContext();
        // there is no body to send
        if (context.getResponseBody() == null
                && context.getResponseDataStream() == null) {
            return;
        }

        String requestURI = context.getRequest().getRequestURI();
        System.out.println("requestURI"+requestURI);
        if(requestURI.contains("service-hi/hi")){
            InputStream stream = context.getResponseDataStream();
            String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
            context.setResponseBody("new body: "+body);
        }


    }

    private void addResponseHeaders(){

    }

    private void writeResponse(InputStream zin, OutputStream out) throws Exception {
        byte[] bytes = new byte[1024];
        int bytesRead = -1;
        while ((bytesRead = zin.read(bytes)) != -1) {
            out.write(bytes, 0, bytesRead);
        }
    }

}
