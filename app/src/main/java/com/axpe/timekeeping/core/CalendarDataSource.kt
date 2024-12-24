package com.axpe.timekeeping.core

import com.axpe.timekeeping.ui.shared.calendar.DayState
import com.axpe.timekeeping.ui.shared.calendar.getDayOfMonthStartingFromMonday
import java.time.LocalDate
import java.time.YearMonth

class CalendarDataSource {
    fun getDates(yearMonth: YearMonth): List<DayState> {
        return yearMonth.getDayOfMonthStartingFromMonday()
            .map { date ->
                if (date.monthValue == yearMonth.monthValue) {
                    DayState(
                        date = date,
                        isSelected = date.isNonWorkableDay && date.isBefore(
                            LocalDate.now().plusDays(1)
                        ),
                        isSelectable = date.isNonWorkableDay,
                    )
                } else {
                    DayState(
                        date = null,
                        isSelected = false,
                        isSelectable = false
                    )
                }
            }
    }
}

private data class HolidaysDay(
    val date: LocalDate,
    val name: String
)

private val LocalDate.isNonWorkableDay: Boolean
    get() {
        return when {
            this.dayOfWeek.value == 6 || this.dayOfWeek.value == 7 ||
                    spainNationalHolidays.map { it.date }.contains(this) -> false

            else -> true
        }
    }
private val spainNationalHolidays = listOf(
    HolidaysDay(LocalDate.of(2024, 12, 6), "Día de la Constitución Española"),
    HolidaysDay(LocalDate.of(2024, 12, 8), "Inmaculada Concepción"),
    HolidaysDay(LocalDate.of(2024, 12, 25), "Navidad"),
    HolidaysDay(LocalDate.of(2025, 1, 1), "Año Nuevo"),
    HolidaysDay(LocalDate.of(2025, 4, 18), "Viernes Santo"),
    HolidaysDay(LocalDate.of(2025, 5, 1), "Fiesta del Trabajo"),
    HolidaysDay(LocalDate.of(2025, 8, 15), "Asuncion de la Virgen"),
    HolidaysDay(LocalDate.of(2025, 11, 1), "Todos los Santos"),
    HolidaysDay(LocalDate.of(2025, 12, 6), "Día de la Constitución Española"),
    HolidaysDay(LocalDate.of(2025, 12, 8), "Inmaculada Concepción"),
    HolidaysDay(LocalDate.of(2025, 12, 25), "Navidad"),


    )
