
package com.simple_apiX.tasks.api.v1.local.back_task.profile_config;

import org.springframework.context.annotation.*;

@Configuration
@PropertySource("classpath:dev.properties")
@Profile("dev")
public class PropertiesSourceDev {
    
}
