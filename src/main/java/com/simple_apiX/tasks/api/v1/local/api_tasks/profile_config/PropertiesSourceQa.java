
package com.simple_apiX.tasks.api.v1.local.api_tasks.profile_config;

import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:qa.properties")
@Profile("qa")
public class PropertiesSourceQa {
    
}
