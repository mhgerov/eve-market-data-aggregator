package site.seedmonkey.eve.marketdataaggregator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import site.seedmonkey.eve.marketdataaggregator.web.EsiConfigurationProperties

@SpringBootApplication
@ConfigurationPropertiesScan(basePackageClasses = [EsiConfigurationProperties::class])
class MarketDataAggregatorApplication

fun main(args: Array<String>) {
	runApplication<MarketDataAggregatorApplication>(*args)
}
