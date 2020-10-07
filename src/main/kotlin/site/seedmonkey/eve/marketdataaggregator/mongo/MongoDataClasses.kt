package site.seedmonkey.eve.marketdataaggregator.mongo

import org.bson.types.ObjectId
import java.math.BigDecimal
import java.time.Instant

data class MarketPrice (
        val id: ObjectId?,
        val typeId: Int,
        val averagePrice: BigDecimal,
        val adjustedPrice: BigDecimal,
        val timeStamp: Instant
)