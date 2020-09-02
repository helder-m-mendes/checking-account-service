package com.checkingaccount.application.config

import com.checkingaccount.application.web.deserializers.LocalDateTimeDeserializer
import com.checkingaccount.application.web.serializers.LocalDateTimeSerializer
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.databind.util.StdDateFormat
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.time.LocalDateTime

object ObjectMapper {
    val mapper = jacksonObjectMapper().apply {
        propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false)
        dateFormat = StdDateFormat()
        registerModule(
            SimpleModule()
                .addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer)
                .addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer)
        )
    }
}