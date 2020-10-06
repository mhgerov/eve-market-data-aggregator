package site.seedmonkey.eve.marketdataaggregator.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Service
import site.seedmonkey.eve.marketdataaggregator.web.EsiMarketPrice
import java.time.Instant
import java.util.function.Consumer

@Service
class MarketPricePersistenceService (private val marketPriceRepository: MarketPriceRepository){

    fun findByTypeId(typeId: Int) = marketPriceRepository.findByTypeIdOrderByTimeStampDesc(typeId)

    fun saveAll(prices: Set<EsiMarketPrice>) {
        val timestamp = Instant.now()
        val transformed = HashSet<MarketPrice>()
        prices.forEach(Consumer { price ->
            transformed.add(MarketPrice(null,price.typeId,price.averagePrice,price.adjustedPrice,timestamp))
        })
        marketPriceRepository.saveAll(transformed)
    }

}

interface MarketPriceRepository : MongoRepository<MarketPrice,String> {
    fun findByTypeIdOrderByTimeStampDesc(typeId: Int): List<MarketPrice>
}