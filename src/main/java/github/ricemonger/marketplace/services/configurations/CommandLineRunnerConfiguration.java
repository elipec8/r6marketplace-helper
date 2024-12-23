package github.ricemonger.marketplace.services.configurations;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommandLineRunnerConfiguration {

    //@Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {

        };
    }
}
