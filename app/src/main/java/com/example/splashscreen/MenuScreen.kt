package com.example.splashscreen


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(navController: NavController) {

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground ) ,
            contentDescription = ""
        )

        //DROPMENU
        val dificultades = arrayOf( "Nivel Facil" , "Nivel Dificil" )

        //variables para el DROPMENU (opcion multiple)
        var valorDificultad by remember { mutableStateOf("Selecciona Dificultad") }
        var mostrar by remember { mutableStateOf(false) }

        Box(){
            //TextField para el DROPMENU
            OutlinedTextField(
                //donde guardaremos los datos
                value = valorDificultad,
                onValueChange = { valorDificultad = it },
                //no se puede introducir texto
                enabled = false,
                readOnly = true,
                //para que aparescan las opciones
                modifier = Modifier
                    .clickable { mostrar = true }
                    .width(220.dp)
                    .padding(top = 30.dp)
            )

            //opciones (DROPMENU)
            DropdownMenu(
                //variable booleano, cuando se mostraran las opciones
                expanded = mostrar,
                onDismissRequest = { mostrar = false },
                modifier = Modifier.width(220.dp)
            ) {
                //recorremos los valores del array
                dificultades.forEach { dificultad -> //cada valor
                    //cada opcion se crea
                    DropdownMenuItem(
                        text = { Text(text = dificultad) },
                        onClick = {
                            //deja de mostrar el menu
                            mostrar = false
                            //guardamos el valor
                            valorDificultad = dificultad
                        }
                    )
                }
            }
        }

        val context = LocalContext.current
        Button(
            onClick = {
                if (valorDificultad == "Selecciona Dificultad"){
                    //TOATS
                    Toast.makeText(context, "Debes Introducir una dificultad", Toast.LENGTH_SHORT).show()
                } else {
                    navController.navigate(Routes.Pantalla1.createRoute(valorDificultad))
                }

            },
            Modifier
                .width(300.dp)
                .padding(10.dp), //margen
            colors = ButtonDefaults.buttonColors(Color.Gray)
        ){
            Text(text = "PLAY" , fontSize = 20.sp , fontWeight = FontWeight.Bold )
        }

        var dialogShow by remember { mutableStateOf(false) }
        Instrucciones (show = dialogShow) { dialogShow = false }
        Button(
            onClick = {
                dialogShow = true
            },
            Modifier
                .width(300.dp)
                .padding(10.dp)
            , //margen
            colors = ButtonDefaults.buttonColors(Color.Gray)
        ){
            Text(text = "HELP" , fontSize = 20.sp , fontWeight = FontWeight.Bold )
        }
    }

}

@Composable
fun Instrucciones (show: Boolean, onDismiss: () -> Unit){
    if(show){
        Dialog(onDismissRequest = { onDismiss() }) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(Color.Gray)
                    .padding(24.dp)
                    .fillMaxWidth()
            ){
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground ) ,
                    contentDescription = ""
                )
                Text( text = "OBJETIVO :" , fontSize = 20.sp , fontWeight = FontWeight.Bold )
                Text( text = "Adivinar una palabra en el menor número de intentos posibles." )
                Text( text = "" )
                Text( text = "El juego consiste en adivinar una palabra, proponiendo 1 letras por turno. Por cada letra acertada, se escribe en el lugar correspondiente. Por cada letra fallida, aparecera una parte del cuerpo de un muñeco que será ahorcado si no se logra resolver el enigma.")
                Text( text = "")

            }
        }
    }
}


