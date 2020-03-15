package com.springboot.app.zuul.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter {

    private static Logger log = LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);

    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("Entrando a post filter");

        Long tiempoInicio = (Long) request.getAttribute("tiempoInicio");
        Long tiempoTranscurrido = System.currentTimeMillis() - tiempoInicio;

        log.info(String.format("Tiempo transcurrido en segundos %s", tiempoTranscurrido. doubleValue()/ 1000));
        log.info(String.format("Tiempo transcurrido en milisegundos %s", tiempoTranscurrido. doubleValue()));

        return null;
    }
}
