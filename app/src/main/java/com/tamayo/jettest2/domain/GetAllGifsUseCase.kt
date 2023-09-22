package com.tamayo.jettest2.domain

import com.tamayo.jettest2.data.model.Data
import com.tamayo.jettest2.data.rest.GifsRepository
import com.tamayo.jettest2.utils.UIState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllGifsUseCase @Inject constructor(private val gifsRepository: GifsRepository) {

    operator fun invoke(tag: String): Flow<UIState<List<Data>>> = flow{
        gifsRepository.getGifsFromApi(tag).collect{
            emit(it)
        }
    }
}