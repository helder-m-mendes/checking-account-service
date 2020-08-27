package com.checkingaccount.application.web.deserializers

import com.checkingaccount.domain.Money
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.math.BigDecimal

class MoneyDeserializer: StdDeserializer<Money>(Money::class.java) {
    override fun deserialize(p: JsonParser, ctx: DeserializationContext) =
        Money(p.readValueAs(BigDecimal::class.java))
}