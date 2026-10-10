
package com.pedro.servicemonitor;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "monitor.enabled=false",
        "spring.datasource.url=jdbc:h2:mem:service-monitor-test"
})
class ServiceMonitorApplicationTests {

    @Test
    void contextLoads() {
    }
}
