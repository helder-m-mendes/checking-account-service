package com.checkingaccount.domain

import org.joda.money.CurrencyUnit
import org.joda.money.Money as JodaMoney

data class Money
    internal constructor(internal val money: JodaMoney) : Comparable<Money> {
        constructor(value: Double, currency: String = "BRL"):
                this(JodaMoney.of(CurrencyUnit.of(currency), value))

    override fun compareTo(other: Money) = money.compareTo(other.money)
}
