package site.seedmonkey.eve.marketdataaggregator.web

import java.math.BigDecimal

data class EsiMarketPrice (
        val typeId: Int,
        val averagePrice: BigDecimal,
        val adjustedPrice: BigDecimal
)