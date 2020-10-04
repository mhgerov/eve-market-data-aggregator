package site.seedmonkey.eve.marketdataaggregator.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Service

@Service
class MarketPricePersistenceService (private val marketPriceRepository: MarketPriceRepository){

    fun findById(id: String) = marketPriceRepository.findById(id)
    fun findByTypeId(typeId: Int) = marketPriceRepository.findByTypeIdOrderByTimeStampDesc(typeId)

}

interface MarketPriceRepository : MongoRepository<MarketPrice,String> {
    fun findByTypeIdOrderByTimeStampDesc(typeId: Int): List<MarketPrice>
}