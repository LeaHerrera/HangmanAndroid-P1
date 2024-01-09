sealed class Routes(val route: String) {
    object SplashScreen:Routes("splash_screen")
    object MenuScreen:Routes("menu_screen")
    object Pantalla1:Routes("pantalla1/{dificultad}"){
        fun createRoute(dificultad:String) = "pantalla1/$dificultad"
    }
    object Pantalla2:Routes("pantalla2/{ganar}"){
        fun createRoute(ganar:Boolean) = "pantalla2/$ganar"
    }
}
