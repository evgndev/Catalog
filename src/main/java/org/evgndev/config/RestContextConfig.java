package org.evgndev.config;

import com.google.common.collect.ImmutableMap;
import cz.jirutka.spring.exhandler.RestHandlerExceptionResolver;
import cz.jirutka.spring.exhandler.handlers.RestExceptionHandler;
import cz.jirutka.spring.exhandler.support.HttpMessageConverterUtils;
import org.evgndev.exception.RestException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * https://github.com/jirutka/spring-rest-exception-handler
 */

@Configuration
public class RestContextConfig extends WebMvcConfigurerAdapter implements RestExceptionHandler<RestException, Object> {

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add( exceptionHandlerExceptionResolver() ); // resolves @ExceptionHandler
        resolvers.add( restExceptionResolver() );
    }

    @Bean
    public RestHandlerExceptionResolver restExceptionResolver() {
        return RestHandlerExceptionResolver.builder()
                .defaultContentType(MediaType.APPLICATION_JSON)
                .addErrorMessageHandler(EmptyResultDataAccessException.class, HttpStatus.NOT_FOUND)
                .addHandler(RestException.class, new RestContextConfig())
                .build();
    }

    @Bean
    public ExceptionHandlerExceptionResolver exceptionHandlerExceptionResolver() {
        ExceptionHandlerExceptionResolver resolver = new ExceptionHandlerExceptionResolver();
        resolver.setMessageConverters(HttpMessageConverterUtils.getDefaultHttpMessageConverters());
        return resolver;
    }

    @Override
    public ResponseEntity<Object> handleException(RestException e, HttpServletRequest httpServletRequest) {
        Map<String, Object> map = ImmutableMap.of(
                "message", e.getMessage(),
                "status", HttpStatus.INTERNAL_SERVER_ERROR.value()
                );
        return new ResponseEntity(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
