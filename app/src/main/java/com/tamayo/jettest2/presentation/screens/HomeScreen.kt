package com.tamayo.jettest2.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import coil.size.Size
import com.tamayo.jettest2.data.model.Data
import com.tamayo.jettest2.presentation.viewmodel.GifsViewModel
import com.tamayo.jettest2.utils.UIState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(vm: GifsViewModel) {

    Scaffold(modifier = Modifier.fillMaxSize()) {
        BodyScreen(vm = vm, modifier = Modifier.padding(it))

    }
}

@Composable
fun BodyScreen(vm: GifsViewModel, modifier: Modifier) {
    Column(modifier = modifier.fillMaxSize()) {

        MySearch(vm = vm)

        when (val gifs = vm.gifs.collectAsState().value) {
            is UIState.ERROR -> {}
            is UIState.LOADING -> {}
            is UIState.SUCCESS -> {
                ListGifs(items = gifs.data)
            }
        }

    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListGifs(items: List<Data>) {

    LazyVerticalStaggeredGrid(modifier = Modifier.padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        columns = StaggeredGridCells.Fixed(3),
        content = {
            items(items) {
                GifsCard(data = it)
            }
        })
}

@Composable
fun GifsCard(data: Data) {

    Card(modifier = Modifier.fillMaxSize(), elevation = CardDefaults.cardElevation()) {

        val imageLoader = ImageLoader.Builder(LocalContext.current)
            .components { add(ImageDecoderDecoder.Factory()) }.build()

        Image(modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current).data(data.images?.original?.url ?: "").apply(
                    block = fun ImageRequest.Builder.(){
                        size(Size.ORIGINAL)
                    }
                ).build(), imageLoader = imageLoader
            ),

            contentDescription = "Gif"
        )

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MySearch(vm: GifsViewModel) {

    val context = LocalContext.current

    var error by remember {
        mutableStateOf(false)
    }

    val hiddeKeyBoard = LocalSoftwareKeyboardController.current

    val query = vm.query.collectAsState()

    OutlinedTextField(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        isError = error,
        value = query.value,
        onValueChange = { vm.setQuerySearch(it) },
        label = { Text(text = "Search...") },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(
            onDone = {
                if (query.value.isEmpty()) {
                    error = true
                } else {
                    error = false
                    hiddeKeyBoard?.hide()
                    vm.getAllGifs(query.value)
                    Toast.makeText(context, "Loading gifts wait a moment please", Toast.LENGTH_LONG).show()

                }

            }
        ))

}














