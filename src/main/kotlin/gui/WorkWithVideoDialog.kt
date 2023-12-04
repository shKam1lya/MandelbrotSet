package gui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.graphics.withSave
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tools.VideoMaker
import java.awt.image.BufferedImage


@Composable
fun workWithVideoDialog(
    videoCreator: VideoMaker,
    imageList: SnapshotStateList<BufferedImage>,
    close:()->Unit,
) {
    var height by remember { mutableStateOf(600) }
    var width by remember { mutableStateOf(800) }
    var fps by remember { mutableStateOf(24) }
    var duration by remember { mutableStateOf(5) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .background(MaterialTheme.colors.surface, shape = MaterialTheme.shapes.medium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Текст в диалоговом окне
            Text(
                "Параметры Видео",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f),
            )
            //Закрытие
            IconButton(
                onClick = close,
                modifier = Modifier
                    .padding(horizontal = 8.dp),
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Закрыть",
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            intTextField(
                value = height,
                label = "Высота",
                onValueChange = {height=it},
                maxValue = 10000,
                modifier = Modifier.weight(1f)
            )
            intTextField(
                value = width,
                label = "Ширина",
                onValueChange = {width=it},
                maxValue = 10000,
                modifier = Modifier.weight(1f)
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            intTextField(
                value = fps,
                label = "FPS",
                onValueChange = {fps=it},
                minValue = 1,
                maxValue = 240,
                modifier = Modifier.weight(1f)
            )
            intTextField(
                value = duration,
                label = "Длительность, c",
                onValueChange = {duration=it},
                maxValue = 10*60*60,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {

                },
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .height(45.dp)
            ) {
                Text("Создать")
            }
            Button(
                onClick = { imageList.clear() },
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
                    .height(45.dp)
            ) {
                Text("Очистить")
            }
        }
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(imageList) {
                MyCard(it) { imageList.remove(it) }
            }
        }
    }
}

@Composable
fun MyCard(bufferedImage: BufferedImage,onDel:()->Unit) {
    val sample = bufferedImage.toComposeImageBitmap()
    Card(
        modifier = Modifier
            .background(color = Color.Magenta)
            .width(110.dp)
            .height(110.dp),
        backgroundColor = MaterialTheme.colors.primarySurface
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Canvas(
                modifier = Modifier
            ) {
                drawIntoCanvas { canvas ->
                    canvas.withSave {
                        canvas.drawImage(sample, Offset.Zero, Paint())
                        canvas.translate(sample.width.toFloat(), 0f)
                    }
                }
            }
            IconButton(
                onClick = { onDel() },
                modifier = Modifier
                    .size(24.dp)
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    Icons.Default.Close,
                    "Удалить"
                )
            }
        }
    }
}
