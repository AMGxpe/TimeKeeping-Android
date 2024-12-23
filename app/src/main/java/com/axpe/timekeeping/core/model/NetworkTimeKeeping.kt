package com.axpe.timekeeping.core.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant
import java.time.LocalTime
import kotlin.random.Random

@Serializable
data class NetworkTimeKeeping(
    @SerialName("comentarios")
    val comments: String,
    @SerialName("dia")
    val day: String,
    @SerialName("entrada1")
    val start1: String,
    @SerialName("entrada2")
    val start2: String,
    @SerialName("salida1")
    val end1: String,
    @SerialName("salida2")
    val end2: String,
    @SerialName("notrabajado")
    val noWorked: Boolean = false,
    @SerialName("entrada3")
    val start3: String = "::",
    @SerialName("entrada4")
    val start4: String = "::",
    @SerialName("entrada5")
    val start5: String = "::",
    @SerialName("salida3")
    val end3: String = "::",
    @SerialName("salida4")
    val end4: String = "::",
    @SerialName("salida5")
    val end5: String = "::"
) {
    companion object {
        fun default(day: Instant): NetworkTimeKeeping {
            val startToday = LocalTime.parse("08:45:00").toSecondOfDay()
            val endToday = LocalTime.parse("09:00:00").toSecondOfDay()
            val randomSeconds = Random.nextInt(startToday, endToday)
            val start1 = LocalTime.ofSecondOfDay(randomSeconds.toLong())
            val randomEatLapse = Random.nextInt(5, 7)
            val randomEatMinsDelay = Random.nextInt(-30, 30)
            val end1 =
                start1.plusHours(randomEatLapse.toLong()).plusMinutes(randomEatMinsDelay.toLong())
            val start2 = end1.plusHours(1)
            val end2 = start1.plusHours(9)
            return NetworkTimeKeeping(
                comments = "",
                day = day.toString(),
                start1 = "${start1.hour}:${start1.minute}:00",
                start2 = "${start2.hour}:${start2.minute}:00",
                end1 = "${end1.hour}:${end1.minute}:00",
                end2 = "${end2.hour}:${end2.minute}:00"
            )
        }
    }
}