package com.tamayo.jettest2.data.rest

import com.tamayo.jettest2.data.model.Data
import com.tamayo.jettest2.utils.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

interface GifsRepository {
    fun getGifsFromApi(tag: String): Flow<UIState<List<Data>>>
}

class GifsRepositoryImpl @Inject constructor(
    private val serviceApi: ServiceApi, private val ioDispatcher: CoroutineDispatcher
) : GifsRepository {
    override fun getGifsFromApi(tag: String): Flow<UIState<List<Data>>> = flow {
        emit(UIState.LOADING)
        try {

            val response = serviceApi.getGifs(q = tag)

            if (response.isSuccessful) {
                response.body()?.let {

                    emit(UIState.SUCCESS(it.data ?: emptyList()))

                } ?: throw Exception(response.errorBody()?.string())
            } else {
                throw Exception("Failed response")
            }
        } catch (e: Exception) {
            emit(UIState.ERROR(e))
        }


    }.flowOn(ioDispatcher)

}