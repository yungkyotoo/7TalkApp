package com.yeditepe.seventalkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yeditepe.seventalkapp.screens.LoginScreen
import com.yeditepe.seventalkapp.screens.RegisterScreen
import com.yeditepe.seventalkapp.ui.theme._7TalkAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            _7TalkAppTheme {




            val navController = rememberNavController()


            NavHost(navController = navController, startDestination = "login") {


                composable("login") {
                    LoginScreen(
                        onRegisterClick = {
                            navController.navigate("register")
                        }
                    )
                }


                composable("register") {
                    RegisterScreen(
                        onLoginClick = {
                            navController.popBackStack()

                        }
                    )
                }
            }
            }
        }
    }
}