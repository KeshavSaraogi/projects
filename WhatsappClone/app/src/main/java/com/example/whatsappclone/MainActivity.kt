package com.example.whatsappclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.whatsappclone.ui.ChatListScreen
import com.example.whatsappclone.ui.ProfileScreen
import com.example.whatsappclone.ui.SignupScreen
import com.example.whatsappclone.ui.SingleChatScreen
import com.example.whatsappclone.ui.SingleStatusScreen
import com.example.whatsappclone.ui.StatusListScreen
import com.example.whatsappclone.ui.theme.WhatsappCloneTheme
import dagger.hilt.android.AndroidEntryPoint

sealed class DestinationScreen(val route: String) {
    object Signup: DestinationScreen("signup")
    object Login: DestinationScreen("login")
    object Profile: DestinationScreen("profile")
    object ChatList: DestinationScreen("chatList")
    object SingleChat: DestinationScreen("singleChat/{chatID}") {
        fun createRoute(id: String) = "singleChat/$id"
    }
    object StatusList: DestinationScreen("statusList")
    object SingleStatus: DestinationScreen("singleStatus/{statusID}") {
        fun createRoute(id: String) = "singleStatus/$id"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhatsappCloneTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatAppNavigation()
                }
            }
        }
    }
}

@Composable
fun ChatAppNavigation() {
    val navController = rememberNavController()
    val viewModel = hiltViewModel<AppViewModel>()

    NotificationMessage(viewModel = viewModel)

    NavHost(navController = navController, startDestination = DestinationScreen.Signup.route) {
        composable(DestinationScreen.Signup.route) {
            SignupScreen(navController, viewModel)
        }
        composable(DestinationScreen.Login.route) {
            LoginScreen()
        }
        composable(DestinationScreen.Profile.route) {
            ProfileScreen(navController = navController)
        }
        composable(DestinationScreen.StatusList.route) {
            StatusListScreen(navController = navController)
        }
        composable(DestinationScreen.Signup.route) {
            SignupScreen(navController, viewModel)
        }
        composable(DestinationScreen.SingleStatus.route) {
            SingleStatusScreen(statusID = "123")
        }
        composable(DestinationScreen.ChatList.route) {
            ChatListScreen(navController = navController)
        }
        composable(DestinationScreen.SingleChat.route) {
            SingleChatScreen(chatID = "123")
        }
    }
}