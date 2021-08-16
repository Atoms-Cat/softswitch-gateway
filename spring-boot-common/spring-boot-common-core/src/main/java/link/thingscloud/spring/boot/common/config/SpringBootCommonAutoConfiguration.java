package link.thingscloud.spring.boot.common.config;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(
        basePackages = {"link.thingscloud.spring.boot.common"}
)
public class SpringBootCommonAutoConfiguration {
    public SpringBootCommonAutoConfiguration() {
    }
}
