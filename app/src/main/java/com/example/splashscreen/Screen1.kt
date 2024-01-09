package com.example.splashscreen


import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController

@Composable
fun Screen1(navController: NavController, dificultad: String){

    val abc:Array<Array<Char>> = arrayOf(
        arrayOf('A', 'B', 'C', 'D', 'E', 'F'),
        arrayOf('G', 'H', 'I', 'J', 'K', 'L'),
        arrayOf('M', 'N', 'Ñ', 'O', 'P', 'Q'),
        arrayOf('R' , 'S', 'T', 'U', 'V', 'W'),
        arrayOf( 'X', 'Y', 'Z')
    )

    var palabraSegreta:String by remember { mutableStateOf(palabraSecreta(dificultad)) }
    var intentos:Int by remember { mutableIntStateOf( intentosInicio(dificultad) ) }
    var ganar:Boolean by remember { mutableStateOf( false ) }

    var palabraEncontrada:Array<String> =  Array( palabraSegreta.length  ) {"_"}
    var letrasBuenas:MutableList<Char> = mutableListOf()
    var letrasMalas:MutableList<Char> = mutableListOf()

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(15.dp)
    ){

        Column (Modifier.padding(40.dp)){
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground ) ,
                contentDescription = ""
            )

            Text(text = "Numero de intentos restantes = ${intentos}" , fontSize = 15.sp , fontWeight = FontWeight.Bold , color = Color.Gray)

            Row {
                repeat( palabraSegreta.length ){caracter ->
                    Text(text = palabraEncontrada[caracter] , fontSize = 60.sp , fontWeight = FontWeight.Bold , modifier = Modifier.padding(8.dp))
                }
            }
        }

        var colorBoton by remember { mutableStateOf( Color.Gray ) }

        for ( columna in 0 .. abc.lastIndex ){
            Row {
                for ( caracter in abc[columna]){

                    if (caracter in letrasBuenas){
                        colorBoton = Color.Green
                    } else if (caracter in letrasMalas){
                        colorBoton = Color.Red
                    } else {
                        colorBoton = Color.Gray
                    }
                    Button(
                        onClick = {
                            intentos--
                        },
                        Modifier
                            .size(55.dp)
                            .padding(3.dp)
                        , //margen
                        colors = ButtonDefaults.buttonColors(colorBoton),
                        shape = RectangleShape
                    ){
                        Text(text = caracter.toString() , fontSize = 20.sp , fontWeight = FontWeight.Bold , textAlign = TextAlign.Center)
                    }
                }
            }
        }

        if (intentos <= 0 || ganar) {
            navController.navigate(Routes.Pantalla2.createRoute(ganar))
        }

    }
}

fun listacaracter(caracter:Char , palabraSecreta:String, listbuena: MutableList<Char> , listmala: MutableList<Char>){

}

fun palabraSecreta ( dificultad: String ):String{

    val palabra:String
    val palabrasFacil:Array<String> = arrayOf("facil")
    val palabrasDificil:Array<String> = arrayOf("dificil")

    if ( dificultad == "Nivel Facil" ) {
        palabra = palabraAleatoria(palabrasFacil)
    } else {
        palabra = palabraAleatoria(palabrasDificil)
    }

    return palabra
}

fun intentosInicio(dificultad: String):Int{
    val intentos:Int

    if ( dificultad == "Nivel Facil" ) {
        intentos = 9
    } else {
        intentos = 5
    }

    return intentos
}

fun palabraAleatoria( cadena: Array<String>):String{

    var palabraAleatoria:String

    val indiceAleatorio = (cadena.indices).random() // Obtiene un índice aleatorio del array
    palabraAleatoria = cadena[indiceAleatorio] // Devuelve el elemento del array en el índice aleatorio

    return palabraAleatoria
}
