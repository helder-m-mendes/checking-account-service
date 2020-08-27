package com.checkingaccount.domain

import com.checkingaccount.application.web.deserializers.MoneyDeserializer
import com.checkingaccount.application.web.serializers.MoneySerializer
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.joda.money.CurrencyUnit
import java.math.BigDecimal
import java.math.RoundingMode
import org.joda.money.Money as JodaMoney

@JsonDeserialize(using = MoneyDeserializer::class)
@JsonSerialize(using = MoneySerializer::class)
data class Money internal constructor(internal val money: JodaMoney) : Comparable<Money> {
    constructor(value: BigDecimal, currency: String = "BRL") :
            this(JodaMoney.of(CurrencyUnit.of(currency), value))

    override fun compareTo(other: Money) = money.compareTo(other.money)

    operator fun times(other: BigDecimal): Money = Money(money.multipliedBy(other, RoundingMode.HALF_EVEN))

    fun asBigDecimal(): BigDecimal = money.toBigMoney().amount
}
