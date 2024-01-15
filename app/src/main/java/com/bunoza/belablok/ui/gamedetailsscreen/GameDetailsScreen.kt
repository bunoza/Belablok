package com.bunoza.belablok.ui.gamedetailsscreen

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.graphics.applyCanvas
import com.bunoza.belablok.data.database.model.Game
import com.bunoza.belablok.data.database.model.toPointList
import com.bunoza.belablok.ui.EmptyGameScreen
import com.bunoza.belablok.ui.UIState
import com.bunoza.belablok.ui.errorscreen.ErrorScreen
import com.bunoza.belablok.ui.loadingscreen.LoadingScreen
import com.bunoza.belablok.ui.scorescreen.LabelHeader
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.io.ByteArrayOutputStream

@Destination
@Composable
fun GameDetailsScreen(navigator: DestinationsNavigator, id: Int) {
    val gameDetailsViewModel = koinViewModel<GameDetailsViewModel>() { parametersOf(id) }
    val uiState by gameDetailsViewModel.uiState.collectAsState()
    val wePointsList by gameDetailsViewModel.wePointsList.collectAsState()
    val themPointsList by gameDetailsViewModel.themPointsList.collectAsState()
    val context = LocalContext.current
    val view = LocalView.current


    Scaffold(
        topBar = {
            GameDetailsTopBar(
                onShareClick = {
                    val bmp = Bitmap.createBitmap(
                        view.width,
                        view.height,
                        Bitmap.Config.ARGB_8888
                    ).applyCanvas {
                        view.draw(this)
                    }

                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_STREAM, getImageUriFromBitmap(context, bmp))
                        type = "image/*"
                        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                }
            )
        }
    ) {
        when (uiState) {
            is UIState.Loading -> LoadingScreen()
            is UIState.Error -> ErrorScreen {
                gameDetailsViewModel.getGameById()
            }
            is UIState.EmptyListState -> EmptyGameScreen {
                gameDetailsViewModel.getGameById()
            }
            is UIState.Success<*> -> {
                GameDetailsContent(game = (uiState as UIState.Success<*>).data as Game, it, wePointsList, themPointsList)

            }
        }
    }
}

@Composable
fun GameDetailsContent(game: Game, paddingValues: PaddingValues, wePointsList: List<Int>, themPointsList: List<Int>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(start = 16.dp, end = 16.dp, top = paddingValues.calculateTopPadding()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LabelHeader(firstPlayerText = "MI", secondPlayerText = "VI")
        SinglePointStatLineComposable(description = "Ukupni bodovi", firstvalue = game.totalPointsWe, secondValue = game.totalPointsThem)
        SinglePointStatLineComposable(description = "Bodovi iz igre", firstvalue = game.totalBasePointsWe, secondValue = game.totalBasePointsThem)
        SinglePointStatLineComposable(description = "Bodovi iz zvanja", firstvalue = game.calledPointsWe, secondValue = game.calledPointsThem)
        ChartDetailsComposable(wePointsData = wePointsList.toPointList(), themPointsData = themPointsList.toPointList())
    }
}

fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
    return Uri.parse(path.toString())
}
