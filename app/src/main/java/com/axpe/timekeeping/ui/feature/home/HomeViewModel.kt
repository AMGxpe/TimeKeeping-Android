package com.axpe.timekeeping.ui.feature.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axpe.timekeeping.core.CalendarDataSource
import com.axpe.timekeeping.core.TimeKeepingRepository
import com.axpe.timekeeping.ui.shared.calendar.DayState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.ZoneOffset

class HomeViewModel : ViewModel() {
    private val calendarDataSource = CalendarDataSource()
    private val timeKeepingRepository = TimeKeepingRepository()
    private val _uiState =
        MutableStateFlow(HomeViewModelUiState(emptyList()))
    val uiState = _uiState.asStateFlow()

    init {
        refreshDays(YearMonth.now())
    }

    fun refreshDays(yearMonth: YearMonth) {
        _uiState.update {
            it.copy(
                yearMonth = yearMonth,
                days = calendarDataSource.getDates(yearMonth)
            )
        }
    }

    fun updateDate(dayState: DayState) {
        val selectedDays =
            _uiState.value.days.map {
                if (it.date == dayState.date)
                    DayState(
                        date = it.date,
                        isSelected = !it.isSelected,
                        isSelectable = it.isSelectable
                    ) else it
            }
        _uiState.update { it.copy(days = selectedDays) }
    }

    fun sendDates(userId: Long) {
        viewModelScope.launch {
            Log.d("HomeViewModel", "Sending dates: ${_uiState.value.days.filter { it.isSelected }}")
            _uiState.value.days.filter { dayState -> dayState.isSelected && dayState.date != null }
                .forEach { dayState ->
                    val now = dayState.date!!.atStartOfDay().toInstant(ZoneOffset.UTC)
                    timeKeepingRepository.sendTimeKeeping(userId, now)
                }
        }
    }
}

data class HomeViewModelUiState(
    val days: List<DayState>,
    val yearMonth: YearMonth = YearMonth.now()
)
