package com.example.splashscreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val botonHabilitado:Array<Array<Boolean>> by remember {
        mutableStateOf(
            arrayOf(
                Array( 6 ) {true},
                Array( 6 ) {true},
                Array( 6 ) {true},
                Array( 6 ) {true},
                Array( 3 ) {true}
            )
        )
    }

    var palabraSegreta:String by remember { mutableStateOf(palabraSecreta(dificultad)) }
    var palabraEncontrada:Array<String> by remember { mutableStateOf(Array( palabraSegreta.length  ) {"_"}) }
    var letrasBuenas:MutableList<Char> by remember { mutableStateOf( mutableListOf() ) }
    var letrasMalas:MutableList<Char> by remember { mutableStateOf( mutableListOf() ) }

    var fallos:Int by remember { mutableIntStateOf( intentosInicio(dificultad) ) }
    var intentos:Int by remember { mutableIntStateOf( 0 ) }
    var ganar:Boolean by remember { mutableStateOf( false ) }

    var colorBoton by remember { mutableStateOf( Color.Gray ) }

    ganar = ganar( arrayIntentos = palabraEncontrada , palabraSecreta = palabraSegreta , letrasBuenas = letrasBuenas )

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(15.dp)
    ){

        CabeceraScreen1 ( palabraEncontrada , palabraSegreta ,  fallos )

        for ( columna in 0 .. abc.lastIndex ){
            Row {
                for ( char in 0 until abc[columna].size){

                    val caracter:Char = abc[columna][char]

                    colorBoton = colorBoton( caracter = caracter , listbuena = letrasBuenas , listmala = letrasMalas)

                    Button(
                        onClick = {
                            listacaracter( caracter = caracter , palabraSecreta = palabraSegreta , listbuena = letrasBuenas , listmala = letrasMalas)
                            colorBoton = colorBoton( caracter = caracter , listbuena = letrasBuenas , listmala = letrasMalas)

                            botonHabilitado[columna][char] = false

                            if ( caracter !in palabraSegreta ){
                                fallos--
                            }
                            intentos++
                        },
                        Modifier
                            .size(55.dp)
                            .padding(3.dp)
                            .background(colorBoton)
                        , //margen
                        colors = ButtonDefaults.buttonColors(colorBoton),
                        enabled = botonHabilitado[columna][char],
                        shape = RectangleShape
                    ){
                        Text(text = caracter.toString() , fontSize = 20.sp , fontWeight = FontWeight.Bold , textAlign = TextAlign.Center)
                    }
                }
            }
        }

        Text(
            text = "Nº intentos : ${intentos}" ,
            fontSize = 20.sp , fontWeight = FontWeight.Bold ,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(25.dp)
        )

        if (fallos <= 0 || ganar) {
            navController.navigate(Routes.Pantalla2.createRoute(ganar))
        }
    }
}

@Composable
fun CabeceraScreen1 ( palabraEncontrada: Array<String> , palabraSegreta: String ,  fallos: Int ){

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(40.dp)
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground ) ,
            contentDescription = ""
        )

        Text(text = "Ahorcado dentro de ${fallos} fallos" , fontSize = 15.sp , fontWeight = FontWeight.Bold , color = Color.Gray)

        Row {
            repeat( palabraSegreta.length ){caracter ->
                Text(text = palabraEncontrada[caracter] , fontSize = 60.sp , fontWeight = FontWeight.Bold , modifier = Modifier.padding(8.dp))
            }
        }
    }

}

fun ganar ( arrayIntentos:Array<String> , palabraSecreta: String , letrasBuenas:MutableList<Char> ):Boolean{

    var resultado = false
    var respuestasBuenas = 0

    repeat(arrayIntentos.size){caracter ->

        if ( palabraSecreta[caracter] in letrasBuenas ){
            arrayIntentos[caracter] = palabraSecreta[caracter].toString()
            respuestasBuenas++
        }
    }

    if (respuestasBuenas == palabraSecreta.length){
        resultado = true
    }
    return resultado
}

fun listacaracter(caracter:Char , palabraSecreta:String, listbuena: MutableList<Char> , listmala: MutableList<Char>){

    if (caracter in palabraSecreta){
        listbuena.add(caracter)
    } else {
        listmala.add(caracter)
    }

}

fun colorBoton (caracter:Char , listbuena: MutableList<Char> , listmala: MutableList<Char>):Color {

    var color: Color

    when (caracter) {
        in listbuena -> { color = Color.Green }
        in listmala -> { color = Color.Red }
        else -> { color = Color.Gray }
    }

    return color

}

fun palabraSecreta ( dificultad: String ):String{

    val palabra:String
    val palabrasFacil:Array<String> = arrayOf("FACIL")
    val palabrasNormal:Array<String> = arrayOf("NORMAL")
    val palabrasDificil:Array<String> = arrayOf("DIFICIL")

    if ( dificultad == "Nivel Facil" ) {
        palabra = palabraAleatoria(palabrasFacil)
    } else if ( dificultad == "Nivel Normal") {
        palabra = palabraAleatoria(palabrasNormal)
    } else {
        palabra = palabraAleatoria(palabrasDificil)
    }

    return palabra
}

fun intentosInicio(dificultad: String):Int{
    val intentos:Int

    if ( dificultad == "Nivel Facil" ) {
        intentos = 10
    } else if ( dificultad == "Nivel Normal") {
        intentos = 7
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
