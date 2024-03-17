package xyz.moevm.ecology.ui.routes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController


@Composable
fun AboutScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(21.dp)
    ) {
        Text(
            text = "Справка",
            style = TextStyle(
                fontSize = 24.sp
            )
        )
        Spacer(modifier = Modifier.height(9.dp))
        Text(
            text = "Вкладка \"Карта\": на ней можно посмотреть карту и наложенные объекты.",
            style = TextStyle(
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = "Вкладка \"Карты\": на ней можно посмотреть загруженные карты.",
            style = TextStyle(
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = "Вкладка \"Объекты\": на ней можно посмотреть загруженные объекты или объедки - кому как нравится.",
            style = TextStyle(
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = "Вкладка \"Профиль\": на ней можно посмотреть свой профиль и уровень кармы.",
            style = TextStyle(
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = "В верхнем левом углу есть значок компаса - с его помощью Вы можете добавить новый объект.",
            style = TextStyle(
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = "В верхнем правом углу на данный момент есть кнопка с помощью которой можно поделиться последним созданным объектом",
            style = TextStyle(
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Авторы: Болкунов Владислав, Давыдов Михаил, Парамонов Вячеслав.",
            style = TextStyle(
                fontSize = 18.sp
            )
        )
        Spacer(modifier = Modifier.height(7.dp))
        Text(
            text = "Под руководстом: \"ЛЭТИ МОЭВМ\" Заславский Марк.",
            style = TextStyle(
                fontSize = 18.sp
            )
        )
    }
}

