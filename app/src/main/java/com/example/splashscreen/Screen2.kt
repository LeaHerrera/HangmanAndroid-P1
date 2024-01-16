package com.example.splashscreen


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Screen2(navController: NavController, ganar: Boolean , intentos:Int , dificultad:String){

    var win:Boolean by remember { mutableStateOf( ganar ) }
    var continuar:Boolean by remember { mutableStateOf( false ) }
    val context = LocalContext.current
    val colorBoton :Color = Color.Gray

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(15.dp),

    ){

        if (ganar){
            Text(text = "¡Felicidades!")
            Text(text = "Has conseguido salvarte pulsando tan solo $intentos letras.")

            Image(
                painter = painterResource(id = R.drawable.ganar ) ,
                contentDescription = ""
            )

        } else {
            Text(text = "¡oh! no...")
            Text(text = "La muerte te ha alcanzado..")

            Image(
                painter = painterResource(id = R.drawable.perder ) ,
                contentDescription = ""
            )
        }

        Row {

            Button(
                onClick = {
                    navController.navigate(Routes.Pantalla1.createRoute(dificultad))
                    Toast.makeText(context, "Inicio de partida en $dificultad", Toast.LENGTH_SHORT).show()
                },
                Modifier
                    .width(150.dp)
                    .padding(10.dp)
                    .background(colorBoton)
                , //margen
                colors = ButtonDefaults.buttonColors(colorBoton),
                shape = RectangleShape
            ){
                Text(text = "PLAY AGAIN" , fontSize = 20.sp , fontWeight = FontWeight.Bold , textAlign = TextAlign.Center)
            }

            Button(
                onClick = {
                    navController.navigate(Routes.Pantalla2.createRoute(ganar,intentos, dificultad))
                },
                Modifier
                    .width(150.dp)
                    .padding(10.dp)
                    .background(colorBoton)
                , //margen
                colors = ButtonDefaults.buttonColors(colorBoton),
                shape = RectangleShape
            ){
                Text(text = "MENU" , fontSize = 20.sp , fontWeight = FontWeight.Bold , textAlign = TextAlign.Center)
            }
        }





    }
}
