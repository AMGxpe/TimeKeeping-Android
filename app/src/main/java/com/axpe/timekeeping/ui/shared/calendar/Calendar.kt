package com.axpe.timekeeping.ui.shared.calendar

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

data class DayState(
    val date: LocalDate?,
    val isSelected: Boolean,
    val isSelectable: Boolean,
)

@Composable
fun Calendar(
    dates: List<DayState>,
    modifier: Modifier = Modifier,
    yearMonth: YearMonth = YearMonth.now(),
    updateYearMonth: (YearMonth) -> Unit,
    onClickDate: (DayState) -> Unit
) {
    val dayModifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .size(64.dp)
    val unSelectableDayModifier = Modifier
        .background(MaterialTheme.colorScheme.secondaryContainer)
        .size(64.dp)
    val emptyDayModifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .size(64.dp)
    Column(modifier = modifier) {
        CalendarHeader(yearMonth = yearMonth, updateYearMonth = updateYearMonth)
        LazyVerticalGrid(columns = GridCells.Fixed(7)) {
            items(daysOfWeek) {
                Box(
                    modifier = Modifier
                        .background(Color.Black.copy(alpha = 0.1F))
                        .fillMaxWidth()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = it)
                }
            }
            items(dates) {
                Box(
                    modifier = when {
                        it.date == null -> emptyDayModifier
                        !it.isSelectable -> Modifier.calculateBorder(it.date, dates)
                        it.isSelected -> dayModifier
                            .clip(
                                AbsoluteCutCornerShape(
                                    topLeft = 16.dp,
                                    topRight = 16.dp,
                                    bottomLeft = 16.dp,
                                    bottomRight = 16.dp
                                )
                            )
                            .background(Color.Green.copy(0.5F))

                        else -> dayModifier
                    }.clickable {
                        onClickDate(it)
                    },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (it.date != null) it.date.dayOfMonth.toString() else "",
                        modifier = Modifier.padding(8.dp),
                        style = if (it.isSelected && it.isSelectable) MaterialTheme.typography.bodyLarge.copy(
                            color = Color.Black,
                            fontWeight = FontWeight.Black
                        ) else MaterialTheme.typography.bodyLarge.copy(),
                    )
                }
            }
        }
    }
}

@Composable
fun Modifier.calculateBorder(currentDate: LocalDate, dates: List<DayState>): Modifier {
    Log.d("AMG", "currentDate: $currentDate")
    val nextDate = currentDate.plusDays(1)
    val nextDateState = dates.find { it.date == nextDate }
    val upperDate = currentDate.plusDays(7)
    val upperDateState = dates.find { it.date == upperDate }
    Log.d("AMG", "nextDate: $nextDate")
    val previousDate = currentDate.minusDays(1)
    val previousDateState = dates.find { it.date == previousDate }
    val lowerDate = currentDate.minusDays(7)
    val lowerDateState = dates.find { it.date == lowerDate }
    Log.d("AMG", "previousDate: $previousDate")
    return this
        .clip(
            AbsoluteCutCornerShape(
                topLeft = 0.dp,
                topRight = 0.dp,
                bottomLeft = 0.dp,
                bottomRight = 0.dp
            )
//            AbsoluteCutCornerShape(
//                topLeft = if (previousDateState?.isSelectable == false || upperDateState?.isSelectable == false) 0.dp else 16.dp,
//                topRight = if (nextDateState?.isSelectable == false || upperDateState?.isSelectable == false) 0.dp else 16.dp,
//                bottomLeft = if (previousDateState?.isSelectable == false || lowerDateState?.isSelectable == false) 0.dp else 16.dp,
//                bottomRight = if (nextDateState?.isSelectable == false || lowerDateState?.isSelectable == false) 0.dp else 16.dp
//            )
        )
        .background(MaterialTheme.colorScheme.secondaryContainer)
        .size(64.dp)
}

@Composable
fun CalendarHeader(yearMonth: YearMonth, updateYearMonth: (YearMonth) -> Unit) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = yearMonth.format(DateTimeFormatter.ofPattern("MMMM - yyyy")),
                style = MaterialTheme.typography.headlineMedium,
            )
            Row {
                IconButton(onClick = {
                    updateYearMonth(yearMonth.minusMonths(1))
                }) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null)

                }
                IconButton(onClick = {
                    updateYearMonth(yearMonth.plusMonths(1))
                }) {
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null)
                }
            }
        }
    }
}

val daysOfWeek: List<String>
    get() {
        val daysOfWeek = mutableListOf<String>()
        DayOfWeek.entries.forEach {
            val localizedDayName = it.getDisplayName(TextStyle.SHORT, Locale.getDefault())
            daysOfWeek.add(localizedDayName)
        }
        return daysOfWeek
    }

fun YearMonth.getDayOfMonthStartingFromMonday(): List<LocalDate> {
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    val firstMondayOfMonth = firstDayOfMonth.with(DayOfWeek.MONDAY)
    val firstDayOfNextMonth = firstDayOfMonth.plusMonths(1)

    return generateSequence(firstMondayOfMonth) { it.plusDays(1) }
        .takeWhile { it.isBefore(firstDayOfNextMonth) }
        .toList()
}