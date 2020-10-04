package site.seedmonkey.eve.marketdataaggregator.mongo

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.context.annotation.Import
import org.springframework.data.mongodb.core.MongoTemplate

@DataMongoTest
@Import(MarketPricePersistenceService::class)
internal class MarketPricePersistenceServiceTest {

    @Autowired
    lateinit var marketPricePersistenceService: MarketPricePersistenceService

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @Test
    fun contextLoads() {}

    @Test
    fun findById() {
        val document = mongoTemplate.executeCommand(
                "{insert:\"marketPrice\",documents: [" +
                        "{\n" +
                        "        \"typeId\": 32772,\n" +
                        "        \"averagePrice\": 1262004.63,\n" +
                        "        \"adjustedPrice\": 1027182.34,\n" +
                        "        \"timeStamp\": new\n" +
                        "        Date(2020,\n" +
                        "        9,\n" +
                        "        15,\n" +
                        "        14,\n" +
                        "        55,\n" +
                        "        56,\n" +
                        "        500)\n" +
                        "      }" +
                        "]}"
        )
        val findByTypeId = marketPricePersistenceService.findByTypeId(32772)
        assertThat(findByTypeId).isNotNull
    }

}
