package com.gzTeleader.screencap.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import org.springframework.web.util.WebAppRootListener;

@Configuration
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter (){
        return new ServerEndpointExporter();
    }
    @Configuration
    @ComponentScan
    @EnableAutoConfiguration
    public class MyServletContextInitializer implements ServletContextInitializer{
        
        //配置websocket传输大小，50M
        @Override
        public void onStartup(ServletContext servletContext) throws ServletException {
            servletContext.addListener(WebAppRootListener.class);
            servletContext.setInitParameter("org.apache.tomcat.websocket.textBufferSize","52428800");
            servletContext.setInitParameter("org.apache.tomcat.websocket.binaryBufferSize","52428800");
        }
    }
}
