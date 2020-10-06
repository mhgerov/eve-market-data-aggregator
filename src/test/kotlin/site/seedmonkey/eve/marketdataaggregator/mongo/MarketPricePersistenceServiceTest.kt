package site.seedmonkey.eve.marketdataaggregator.mongo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoTemplate
import site.seedmonkey.eve.marketdataaggregator.web.EsiMarketPrice
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

@DataMongoTest
@Import(MarketPricePersistenceService::class)
internal class MarketPricePersistenceServiceTest {

    @Autowired
    lateinit var marketPricePersistenceService: MarketPricePersistenceService

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @Test
    fun contextLoads() {
    }

    @Test
    fun findById() {
        val timestamp = ZonedDateTime.of(2016, 5, 18, 16, 0, 0, 23000000, ZoneId.of("UTC"))
        mongoTemplate.executeCommand(
                "{insert:\"marketPrice\",documents: [" +
                        "{\n" +
                        "        \"typeId\": 32772,\n" +
                        "        \"averagePrice\": 1262004.63,\n" +
                        "        \"adjustedPrice\": 1027182.34,\n" +
                        "        \"timeStamp\": ISODate(\"2016-05-18T16:00:00.23Z\")" +
                        "      }" +
                        "]}"
        )
        val findByTypeId = marketPricePersistenceService.findByTypeId(32772)
        assertThat(findByTypeId.size).isEqualTo(1)
        val expected = MarketPrice(findByTypeId[0].id, 32772, BigDecimal("1262004.63"), BigDecimal("1027182.34"), Instant.from(timestamp))
        assertThat(findByTypeId[0]).isEqualTo(expected)
    }

    @Test
    fun saveAll() {
        val timeNow = Instant.parse("2016-05-18T16:00:00.23Z")
        val input = setOf<EsiMarketPrice>(
                EsiMarketPrice(27319, BigDecimal("11.64"), BigDecimal("10.07")),
                EsiMarketPrice(54559, BigDecimal("19683846.15"), BigDecimal("0")),
                EsiMarketPrice(32853, BigDecimal("130486190.48"), BigDecimal("140751035.8"))
        )
        val expected = setOf<MarketPrice>(
                MarketPrice(null, 27319, BigDecimal("11.64"), BigDecimal("10.07"), timeNow),
                MarketPrice(null, 54559, BigDecimal("19683846.15"), BigDecimal("0"), timeNow),
                MarketPrice(null, 32853, BigDecimal("130486190.48"), BigDecimal("140751035.8"), timeNow)
        )
        mock(Instant::class.java)
        `when`(Instant.now()).thenReturn(timeNow)
        assertThat(Instant.now()).isEqualTo(timeNow)
    }
}
