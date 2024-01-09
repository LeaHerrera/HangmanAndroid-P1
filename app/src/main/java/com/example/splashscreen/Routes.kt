sealed class Routes(val route: String) {
    object SplashScreen:Routes("splash_screen")
    object MenuScreen:Routes("menu_screen")
    object Pantalla1:Routes("pantalla1/{dificultad}"){
        fun createRoute(dificultad:String) = "pantalla1/$dificultad"
    }
}
