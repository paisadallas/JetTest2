package com.tamayo.jettest2.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tamayo.jettest2.data.model.Data
import com.tamayo.jettest2.domain.GetAllGifsUseCase
import com.tamayo.jettest2.utils.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GifsViewModel @Inject constructor(
    private val gifsUseCase: GetAllGifsUseCase
): ViewModel() {

    private val _gifs: MutableStateFlow<UIState<List<Data>>> = MutableStateFlow(UIState.LOADING)
    val gifs: StateFlow<UIState<List<Data>>> get() = _gifs

    private val _query = MutableStateFlow("")
    val query get() = _query.asStateFlow()


    fun setQuerySearch(query: String){
        _query.value = query
    }


    fun getAllGifs(tag: String) = viewModelScope.launch {
        gifsUseCase.invoke(tag).collect{
            _gifs.value = it
        }

    }


}