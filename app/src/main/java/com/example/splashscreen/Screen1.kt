package com.example.splashscreen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
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
        modifier = Modifier
            .paint( //fondo
                painterResource(id = R.drawable.fondo),
                contentScale = ContentScale.FillBounds
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
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
            navController.navigate(Routes.Pantalla2.createRoute(ganar,intentos, dificultad, palabraSegreta))
        }
    }
}

@Composable
fun CabeceraScreen1 ( palabraEncontrada: Array<String> , palabraSegreta: String ,  fallos: Int ){

    var imagen = imagenesPerder.size - fallos - 1

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(40.dp)
    ){
        Image(
            painter = painterResource(id = imagenesPerder[imagen] ) ,
            contentDescription = ""
        )

        Text(text = "Ahorcado dentro de ${fallos} fallos" , fontSize = 15.sp , fontWeight = FontWeight.Bold , color = Color.Gray)

        Row (
            modifier= Modifier.fillMaxWidth(),
            Arrangement.Center
        ){
            repeat( palabraSegreta.length ){caracter ->
                Text(text = palabraEncontrada[caracter] , fontSize = 25.sp , fontWeight = FontWeight.Bold , modifier = Modifier.padding(3.dp))
            }
        }
    }

}

val imagenesPerder:Array<Int> = arrayOf(
    R.drawable.intento1,
    R.drawable.intento2,
    R.drawable.intento3,
    R.drawable.intento4,
    R.drawable.intento5,
    R.drawable.intento10,
    R.drawable.intento11,
    R.drawable.intento6,
    R.drawable.intento7,
    R.drawable.intento8,
    R.drawable.intento9
)

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
    //listas generadas por chat gpt
    val palabrasFacil:Array<String> = arrayOf(
        "AMOR", "CASA", "DIA", "FLOR", "GATO",
        "HORA", "IDEA", "JUEGO", "LUZ", "MESA",
        "NINO", "OLA", "PIEDRA", "QUERIDO", "RIO",
        "SOL", "TIERRA", "VIENTO", "ZAPATO",
        "BUENO", "CALIDO", "DULCE", "FRESCO", "GRANDE",
        "HERMOSO", "JOVEN", "LENTO", "MAGICO",
        "NUEVO", "OSCURO", "PEQUENO", "RAPIDO", "SENCILLO",
        "TRISTE", "UNICO", "VERDE", "BLANCO", "ROJO",
        "AMARILLO", "AZUL", "FELIZ", "DIVERTIDO", "SABROSO",
        "AMIGO", "FAMILIA", "FELIZ", "LIBRO", "CIELO",
        "MONTANA", "CAMINO", "FIESTA", "BOSQUE", "MAR",
        "CANCION", "RISA", "ABRAZO", "CAMINAR", "CORRER",
        "SALTAR", "NADAR", "VIAJAR", "SONAR", "COMER",
        "BEBER", "APRENDER", "ESCUCHAR", "MIRAR", "HABLAR",
        "JUGAR", "AYUDAR", "BAILAR", "CANTAR", "SONREIR",
        "DESCANSAR", "VIAJE", "AVENTURA", "DESCUBRIR", "DESPERTAR",
        "DESCANSO", "REIR", "FELICIDAD", "SUEÑO", "ENERGIA",
        "PAZ", "ESPERANZA", "LIBERTAD", "EXITO", "SALUD",
        "CREATIVIDAD", "AMABILIDAD", "AMISTAD",
        "CONOCIMIENTO", "PACIENCIA", "RESPETO" , "BUENO" ,
        "MALO" , "YO" , "TU" , "AMIGOS" , "ELLOS" , "ELLAS" ,
        "LEON" , "LEER" , "GATOS"
    )
    val palabrasNormal:Array<String> = arrayOf(
        "AEROPUERTO", "OCEANICO", "AUTOMOVIL", "AVENTURERO", "BIOLOGIA",
        "CALEIDOSCOPIO", "OCULTACION", "DESARROLLADOR", "EDUCACION", "ELECTRONICO",
        "FANTASTICO", "GALAXIA", "HELICOPTERO", "HIPNOTIZAR", "INNOVACION",
        "JEROGLIFICO", "JARDINERIA", "KILOMETRO", "LABORATORIO", "MELODIA",
        "NAVEGACION", "OASIS", "PARALELEPIPIDO", "QUIMICA", "RAZONAMIENTO",
        "SENSACIONAL", "SINFONIA", "TELESCOPIO", "UBICACION", "UTOPIA",
        "VIBRACION", "VIAJERO", "XENOFOBIA", "XILOFONO", "YOGA",
        "ZOLOGICO", "ALUCINANTE", "BICICLETA", "CATACLISMO", "DIAMANTE",
        "ECOLOGIA", "FOTOGRAFIA", "GEOGRAFIA", "HARMONIA", "ILUSORIO",
        "JUBILACION", "KIOSCO", "LUMINOSO", "MAGNIFICO", "NAUTICO",
        "ORBITA", "PATRIOTICO", "QUINTETO", "RADIANTE", "SURREALISTA",
        "TECNICO", "UNIVERSIDAD", "VORAZ", "WINDSURF", "XEROGRAGIA",
        "YOGUR", "ZUMBIDO", "AMPLIFICACION", "BIBLIOTECA", "CONCENTRACION",
        "DECISION", "ELOCUENCIA", "FASCINANTE", "GENERACION", "HIPOPOTAMO",
        "IMPONENTE", "JUVENTUD", "LIBERACION", "MAJESTUOSO",
        "NEUROCIENCIA", "OCUPACION", "POLARIZACION", "RECONOCIMIENTO",
        "SINCRONIZACION", "TRANSFORMACION", "UBICACION", "VANGUARDIA", "ZARZAMORA"
    )
    val palabrasDificil:Array<String> = arrayOf(
        "ABSTRUSO", "CAUTERIO", "DIAFANO", "EBANO", "FERETRO",
        "GELIDO", "HABIL", "IGNEO", "JACENA", "KAISER",
        "LOBREGO", "MIMICO", "NANDU", "OSCURO", "PETREO",
        "QUIMERA", "RUSTICO", "SERPICO", "TACITO", "URSIDO",
        "VASTAGO", "WESTICO", "XEROX", "YAMBICO", "ZEFIRO",
        "ALGIDO", "BUHO", "CIRCULO", "DOCIL", "EPICO",
        "FARRAGO", "GONDOLA", "HELICE", "INDIGO", "JUBILO",
        "KARATE", "LAPIZ", "MUSICO", "NONO", "OVULO",
        "PUDICO", "QUASAR", "RAFAGA", "SOLIDO", "TECNICO",
        "ULTIMO", "VERTIGO", "WESTERN", "XENON", "YOQUEY",
        "ZEJEL", "ACRATA", "BOVEDA", "CALIDO", "DOLAR",
        "ELFICO", "FULGIDO", "GELIDO", "HABITO", "IMPETU",
        "JUBILO", "KETCHUP", "LOBREGO", "MISTICO", "ÑOÑO",
        "OSCURO", "PETREO", "QUIMERA", "RUSTICO", "SERPICO",
        "TACITO", "URSIDO", "VASTAGO", "XILOFONO", "XEROX",
        "YAMBICO", "ZEFIRO", "ALGIDO", "BUHO", "CIRCULO",
        "DOCIL", "EPICO", "FARRAGO", "GONDOLA", "HELICE",
        "INDIGO", "JUBILO", "KARATE", "LAPIZ", "MUSICO",
        "ÑAME", "OVULO", "PUDICO", "CAZAR", "RAFAGA",
        "SOLIDO", "TECNICO", "ULTIMO", "VERTIGO", "KILOMETRO",
        "XENON", "YOQUEY", "ZEJEL", "ACRATA", "BOVEDA",
        "CALIDO", "DOLAR", "ELFICO", "FULGIDO", "GELIDO",
        "HABITO", "IMPETU", "JUBILO", "KETCHUP", "LOBREGO",
        "MISTICO", "ÑU", "OVULO"
    )

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
        intentos = 8
    } else {
        intentos = 7
    }

    return intentos
}

fun palabraAleatoria( cadena: Array<String>):String{

    var palabraAleatoria:String

    val indiceAleatorio = (cadena.indices).random() // Obtiene un índice aleatorio del array
    palabraAleatoria = cadena[indiceAleatorio] // Devuelve el elemento del array en el índice aleatorio

    return palabraAleatoria
}
