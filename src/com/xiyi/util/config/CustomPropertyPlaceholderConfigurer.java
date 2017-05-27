package com.xiyi.util.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;


public class CustomPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer
{
    
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, java.util.Properties props)
        throws BeansException
    {
        super.processProperties(beanFactory, props);
        
        // load properties to CommonsConstants.GLOBAL_CONFIG_PROPERTIES
        for (Object key : props.keySet())
        {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            WebAppConfig.GLOBAL_CONFIG_PROPERTIES.put(keyStr, value);
        }
    }
}
