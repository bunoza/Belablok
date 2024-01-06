package com.bunoza.belablok.ui.gamedetailsscreen

import android.content.ContentValues.TAG
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.applyCanvas
import com.bunoza.belablok.data.database.model.Game
import com.bunoza.belablok.data.database.model.SingleGame
import com.bunoza.belablok.data.database.model.toPointList
import com.bunoza.belablok.ui.EmptyGameScreen
import com.bunoza.belablok.ui.UIState
import com.bunoza.belablok.ui.errorscreen.ErrorScreen
import com.bunoza.belablok.ui.loadingscreen.LoadingScreen
import com.bunoza.belablok.ui.scorescreen.LabelHeader
import com.bunoza.belablok.ui.theme.BelaBlokTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

@Destination
@Composable
fun GameDetailsScreen(navigator: DestinationsNavigator, id: Int) {
    val gameDetailsViewModel = koinViewModel<GameDetailsViewModel>() { parametersOf(id) }
    val uiState by gameDetailsViewModel.uiState.collectAsState()
    val wePointsList by gameDetailsViewModel.wePointsList.collectAsState()
    val themPointsList by gameDetailsViewModel.themPointsList.collectAsState()
    val context = LocalContext.current
    val view = LocalView.current
    val showDeleteGameAlertDialog = remember{
        mutableStateOf(false)
    }

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
                    /*bmp.let {
                        File(context.filesDir, "screenshot.png")
                            .writeBitmap(bmp, Bitmap.CompressFormat.PNG, 85)
                    }*/

                    val sendIntent: Intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_STREAM, getImageUriFromBitmap(context, bmp))
                        type = "image/*"
                        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                    }
                    val shareIntent = Intent.createChooser(sendIntent, null)
                    context.startActivity(shareIntent)
                },
                onDeleteClick = {
                    showDeleteGameAlertDialog.value=true
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
                if(showDeleteGameAlertDialog.value){
                    DeleteGameAlertDialog(
                        onDismissClick = {
                            showDeleteGameAlertDialog.value=false
                        },
                        onConfirmClick = {
                            navigator.navigateUp()
                        }
                    )


                }
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
        // Chart(chart = lineChart(), model = entryModelOf(entriesOf(game.wePointsList.toPairList())), startAxis = rememberStartAxis(), bottomAxis = rememberBottomAxis())
        ChartDetailsComposable(wePointsData = wePointsList.toPointList(), themPointsData = themPointsList.toPointList())
        Log.d(TAG, "GameDetailsContent: $wePointsList, $themPointsList")
    }
}

@Composable
@Preview
private fun PreviewGameDetailsContentLightTheme() {
    BelaBlokTheme {
        val game = Game(singleGameList = listOf<SingleGame>(SingleGame(baseGamePointsWe = 1, baseGamePointsThem = 1, callTwentyWe = 1, callTwentyThem = 1, callFiftyWe = 0, callFiftyThem = 0, callHundredWe = 1, callBelotThem = 1, callHundredThem = 1, callBelotWe = 1, afterBasePointsThem = 20, afterBasePointsWe = 12, scoreWe = 12, scoreThem = 12, dealer = "Ja")))
        // GameDetailsContent(game, PaddingValues(16.dp))
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
private fun PreviewGameDetailsContentDarkTheme() {
    BelaBlokTheme {
        // GameDetailsContent()
    }
}

fun getBitmapFromView(bmp: Bitmap?, context: Context): Uri? {
    var bmpUri: Uri? = null

    try {
        val file = File(context.externalCacheDir, System.currentTimeMillis().toString() + ".jpg")

        val out = FileOutputStream(file)
        bmp?.compress(Bitmap.CompressFormat.JPEG, 90, out)
        out.close()
        bmpUri = Uri.fromFile(file)
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return bmpUri
}

fun getImageUriFromBitmap(context: Context, bitmap: Bitmap): Uri {
    val bytes = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap, "Title", null)
    return Uri.parse(path.toString())
}
private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
    outputStream().use { out ->
        bitmap.compress(format, quality, out)
        out.flush()
    }
}
private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
    val tempFile = File(inContext.filesDir, "screenshot.png")
    val bytes = ByteArrayOutputStream()
    inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes)
    val bitmapData = bytes.toByteArray()

    val fileOutPut = FileOutputStream(tempFile)
    fileOutPut.write(bitmapData)
    fileOutPut.flush()
    fileOutPut.close()
    return Uri.fromFile(tempFile)
}

fun getUriFromBitmap(context: Context, bitmap: Bitmap): String? {
    // Get the directory where the image will be saved
    val cw = ContextWrapper(context)
    val directory = cw.getDir("images", Context.MODE_PRIVATE)

    // Create a unique file name
    val filename = "image_${System.currentTimeMillis()}.jpg"

    val file = File(directory, filename)

    try {
        val fos: OutputStream = FileOutputStream(file)
        // Compress the bitmap to a JPEG with 100% quality
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.close()
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    }

    // Return the URI of the saved image
    return file.absolutePath
}
fun bitmapToUri(context: Context, bitmap: Bitmap): Uri? {
    var imageUri: Uri? = null

    try {
        // Create a file to save the Bitmap
        val file = File(context.cacheDir, "image.png")

        // Create an output stream to write the Bitmap to the file
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.close()

        // Obtain a content URI for the saved file
        imageUri = Uri.fromFile(file)
    } catch (e: IOException) {
        e.printStackTrace()
    }

    return imageUri
}

private fun saveBitmapToFile(context: Context, bitmap: Bitmap): File {
    val directory = context.filesDir
    val file = File(directory, "temp_bitmap.png")
    val stream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    stream.close()
    return file
}
