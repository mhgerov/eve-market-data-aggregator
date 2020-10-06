package site.seedmonkey.eve.marketdataaggregator.scheduling

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock

@Configuration
class TimeConfiguration {

    @Bean
    fun clock(): Clock = Clock.systemDefaultZone()

}


