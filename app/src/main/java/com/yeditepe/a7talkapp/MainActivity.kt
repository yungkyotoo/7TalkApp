import androidx.activity.ComponentActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import com.yeditepe.seventalkapp.ui.theme.7TalkAppTheme // PROJE ADINIZA GÖRE TEMA ADINI KONTROL EDİN!

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Buradaki Tema fonksiyonunuzun adı farklı olabilir, kontrol edin.
            7TalkAppTheme {
                MyApp()
            }
        }
    }
}

// --- Aşağıdaki kodları MainActivity sınıfı dışına ekleyin ---

// Uygulamadaki tüm ekranların rotaları (hedefleri)
sealed class Screen(val route: String) {
    object CommunityList : Screen("community_list")
    object Notifications : Screen("notifications")
    object Favorites : Screen("favorites")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    // Navigasyonu yönetecek olan kontrolcü
    val navController = rememberNavController()
    // Kenar çubuğu durumunu (Açık/Kapalı) yönetecek olan state
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope() // Drawer'ı açmak/kapamak için Coroutine kapsamı

    // Kenar Çubuğu (Drawer) yapısı
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            // Sidebar Content (Menü içeriği)
            SidebarContent(
                navController = navController,
                onCloseDrawer = {
                    scope.launch { drawerState.close() } // Menüden bir şeye tıklayınca kapat
                }
            )
        }
    ) {
        // Ana iskele (Scaffold) yapısı: TopBar ve ekran içeriğini birleştirir
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("7TalkApp") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() } // Menü ikonuna tıklayınca aç
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Menü")
                        }
                    }
                )
            }
        ) { paddingValues ->
            // Ekranlar arası geçişi yöneten NavHost
            NavHost(
                navController = navController,
                startDestination = Screen.CommunityList.route, // Başlangıç ekranı
                modifier = Modifier.padding(paddingValues)
            ) {
                // Ekran Composable'ları
                composable(Screen.CommunityList.route) {
                    Text("Topluluk Listesi Ekranı (CommunityList)")
                }
                composable(Screen.Notifications.route) {
                    Text("Bildirim Ekranı (Notifications)")
                }
                composable(Screen.Favorites.route) {
                    Text("Favoriler Ekranı (Favorites)")
                }
            }
        }
    }
}

// Kenar Çubuğu (Sidebar) menü içeriği
@Composable
fun SidebarContent(navController: NavHostController, onCloseDrawer: () -> Unit) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Ana Menü", style = MaterialTheme.typography.headlineMedium)
        Divider()

        // Topluluklar Menüsü
        TextButton(onClick = {
            navController.navigate(Screen.CommunityList.route) { popUpTo(0) } // Ana ekrana dön
            onCloseDrawer()
        }) {
            Text("Topluluklar")
        }
        // Bildirimler Menüsü
        TextButton(onClick = {
            navController.navigate(Screen.Notifications.route)
            onCloseDrawer()
        }) {
            Text("Bildirimler")
        }
        // Favoriler Menüsü
        TextButton(onClick = {
            navController.navigate(Screen.Favorites.route)
            onCloseDrawer()
        }) {
            Text("Favoriler")
        }
    }
}