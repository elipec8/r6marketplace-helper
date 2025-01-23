package github.ricemonger.fetching_acounts_manager;

import github.ricemonger.utils.SchedulingUtilsConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@ComponentScan(basePackages = {"github.ricemonger.fetching_acounts_manager", "github.ricemonger.utilspostgresschema"},
        basePackageClasses = github.ricemonger.utils.PublicMethodLogger.class)
@EntityScan(basePackages = "github.ricemonger.utilspostgresschema")
@Import({
        SchedulingUtilsConfiguration.class
})
public class FetchingAcountsManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FetchingAcountsManagerApplication.class, args);
    }

}
