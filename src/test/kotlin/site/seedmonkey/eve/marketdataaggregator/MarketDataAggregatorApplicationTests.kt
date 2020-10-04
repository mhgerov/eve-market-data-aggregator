package site.seedmonkey.eve.marketdataaggregator

import org.junit.jupiter.api.Test
import org.quartz.Job
import org.quartz.JobBuilder.newJob
import org.quartz.JobExecutionContext
import org.quartz.Scheduler
import org.quartz.TriggerBuilder.newTrigger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class MarketDataAggregatorApplicationTests {

	@Test
	fun contextLoads() {
	}

}
