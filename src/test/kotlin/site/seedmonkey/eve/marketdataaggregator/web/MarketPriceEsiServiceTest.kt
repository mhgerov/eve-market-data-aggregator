package site.seedmonkey.eve.marketdataaggregator.web

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess
import java.math.BigDecimal

@RestClientTest(MarketPriceEsiService::class)
@EnableConfigurationProperties(EsiConfigurationProperties::class)
internal class MarketPriceEsiServiceTest {

    val successResponseJsonString = "[\n" +
            "    {\n" +
            "        \"adjusted_price\": 1027182.34,\n" +
            "        \"average_price\": 1262004.63,\n" +
            "        \"type_id\": 32772\n" +
            "    },\n" +
            "    {\n" +
            "        \"adjusted_price\": 48795.29,\n" +
            "        \"average_price\": 47333.0,\n" +
            "        \"type_id\": 32774\n" +
            "    },\n" +
            "    {\n" +
            "        \"adjusted_price\": 5970966.54,\n" +
            "        \"average_price\": 6103663.82,\n" +
            "        \"type_id\": 32780\n" +
            "    },\n" +
            "    {\n" +
            "        \"adjusted_price\": 8.39,\n" +
            "        \"average_price\": 7.31,\n" +
            "        \"type_id\": 32782\n" +
            "    },\n" +
            "    {\n" +
            "        \"adjusted_price\": 0.0,\n" +
            "        \"average_price\": 120000.0,\n" +
            "        \"type_id\": 32783\n" +
            "    }]"

    val successResponseObj = EsiMarketPrice(32772, BigDecimal("1262004.63"),BigDecimal("1027182.34"))

    @Autowired
    lateinit var marketPriceEsiService: MarketPriceEsiService

    @Test
    fun contextLoads() {}

    @Test
    fun objectMapper() {
        val mapper = jacksonObjectMapper()
        mapper.propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        val readValue = mapper.readValue(successResponseJsonString, Array<EsiMarketPrice>::class.java)
        val first = readValue[0]
    }

    @Test
    fun getMarketPrices() {
        val httpHeaders = HttpHeaders()
        httpHeaders.set(HttpHeaders.ETAG,"\"5ce82dd98572baf626dcf1a09405339724796d8d9e2dcaea938b1d64\"")
        httpHeaders.set(HttpHeaders.EXPIRES,"Wed, 30 Sep 2020 01:54:55 GMT")
        val mockRestServiceServer  = MockRestServiceServer.createServer(marketPriceEsiService.restTemplate)
        mockRestServiceServer
                .expect(requestTo("/v1/markets/prices"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(successResponseJsonString, MediaType.APPLICATION_JSON).headers(httpHeaders))
        val actualPrices = marketPriceEsiService.getMarketPrices()
        assertThat(actualPrices.contains(successResponseObj)).isTrue()
    }
}