package com.example.splashscreen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Screen2(navController: NavController, ganar: Boolean , intentos:Int , dificultad:String){

    var win:Boolean by remember { mutableStateOf( ganar ) }
    var continuar:Boolean by remember { mutableStateOf( false ) }


    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(15.dp)
    ){

        if (ganar){
            Text(text = "siii")

        } else {
            Text(text = "noooo")
        }

    }
}
