package site.seedmonkey.eve.marketdataaggregator.web

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("market.data.aggregator.esi")
class EsiConfigurationProperties {
    var baseUrl: String = "https://esi.evetech.net"
}