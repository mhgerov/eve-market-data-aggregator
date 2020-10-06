package site.seedmonkey.eve.marketdataaggregator.web

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.time.Instant

@Service
class MarketPriceEsiService(esiConfigurationProperties: EsiConfigurationProperties, restTemplateBuilder: RestTemplateBuilder) {

    val marketPricesPath: URI = URI.create("/v1/markets/prices")
    final val restTemplate: RestTemplate

    init {
        val mapper = jacksonObjectMapper()
        mapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        val converter = MappingJackson2HttpMessageConverter()
        converter.objectMapper = mapper
        val restTemplate = restTemplateBuilder
                .rootUri(esiConfigurationProperties.baseUrl)
                .build()
        val messageConverters = restTemplate.messageConverters
        for (cv in messageConverters) {
            if (cv is MappingJackson2HttpMessageConverter) {
                cv.objectMapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
            }
        }
        this.restTemplate = restTemplate
    }

    lateinit var etag: String
    lateinit var expires: Instant

    fun getMarketPrices(): Set<EsiMarketPrice> {
        val response = restTemplate.getForEntity(marketPricesPath, Array<EsiMarketPrice>::class.java)
        etag = response.headers.eTag!!
        expires = Instant.ofEpochMilli(response.headers.expires)
        return response.body!!.toSet()
    }
}