package com.checkingaccount.application.web.deserializers

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object LocalDateTimeDeserializer: StdDeserializer<LocalDateTime>(LocalDateTime::class.java) {
    override fun deserialize(p: JsonParser, ctx: DeserializationContext): LocalDateTime =
        p.readValueAs(String::class.java).toLocalDateTime()
}

private fun String.toLocalDateTime() =
    LocalDateTime.parse(this, DateTimeFormatter.ISO_LOCAL_DATE_TIME)