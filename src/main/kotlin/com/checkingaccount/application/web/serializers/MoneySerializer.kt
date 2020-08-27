package com.checkingaccount.application.web.serializers

import com.checkingaccount.domain.Money
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer

class MoneySerializer: StdSerializer<Money>(Money::class.java) {
    override fun serialize(value: Money, gen: JsonGenerator, provider: SerializerProvider) {
        gen.writeNumber(value.asBigDecimal())
    }
}