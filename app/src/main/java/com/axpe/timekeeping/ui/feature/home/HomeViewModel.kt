package com.axpe.timekeeping.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.axpe.timekeeping.core.CalendarDataSource
import com.axpe.timekeeping.core.PreferencesDataSource
import com.axpe.timekeeping.core.TimeKeepingRepository
import com.axpe.timekeeping.core.model.UserData
import com.axpe.timekeeping.ui.shared.calendar.DayState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.YearMonth
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val calendarDataSource: CalendarDataSource,
    private val timeKeepingRepository: TimeKeepingRepository,
    private val preferencesDataSource: PreferencesDataSource
) : ViewModel() {
    private val _uiState =
        MutableStateFlow(HomeViewModelUiState(days = emptyList()))
    val uiState = _uiState.asStateFlow()

    init {
        updateUserInfo()
        refreshDays(YearMonth.now())
    }

    private fun updateUserInfo() {
        viewModelScope.launch {
            preferencesDataSource.getDataStoreUser().collect { user ->
                _uiState.update { it.copy(user = user) }
            }
        }
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

    fun sendDates() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            _uiState.value.days.filter { dayState -> dayState.isSelected && dayState.date != null }
                .forEach { dayState ->
                    val now = dayState.date!!.atStartOfDay().toInstant(ZoneOffset.UTC)
                    timeKeepingRepository.sendTimeKeeping(now)
                }
            _uiState.update { it.copy(isLoading = false) }
        }
    }
}

data class HomeViewModelUiState(
    val days: List<DayState>,
    val user: UserData? = null,
    val isLoading: Boolean = false,
    val yearMonth: YearMonth = YearMonth.now()
)
