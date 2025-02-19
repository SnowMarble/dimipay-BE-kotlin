package io.dimipay.server.common.dto

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ResponseDto<T>(
    /**
     * same as http status code
     */
    val status: Int,

    /**
     * "OK" for success, or unique error code for error
     */
    val code: String,

    /**
     * usually for an error message
     */
    val message: String? = null,

    /**
     * data for success
     */
    val data: T? = null,

    /**
     * detail error data
     */
    val error: Any? = null,

    val timestamp: LocalDateTime = LocalDateTime.now()
)