package site.seedmonkey.eve.marketdataaggregator

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MarketDataAggregatorApplication

fun main(args: Array<String>) {
	runApplication<MarketDataAggregatorApplication>(*args)
}
