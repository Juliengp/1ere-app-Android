package com.example.premiereappli

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.premiereappli.ui.theme.PremiereAppliTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState


@Serializable class DestinationProfil
@Serializable class DestinationFilms
@Serializable class DestinationSeries
@Serializable class DestinationActeurs



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PremiereAppliTheme {
                val navController = rememberNavController()
                val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
                val currentDestination = navController.currentBackStackEntryAsState().value?.destination
                val viewModel : MainViewModel = viewModel()

                Scaffold(
                    bottomBar = {
                        if(currentDestination?.hasRoute<DestinationProfil>()== false) {
                        NavigationBar {
                            NavigationBarItem(
                                icon = { Icon(
                                    painter = painterResource(id = R.drawable.baseline_movie_24),
                                    contentDescription = "films"
                                ) },
                                selected = currentDestination?.route == DestinationFilms().toString(),
                                onClick = { navController.navigate(DestinationFilms()) }
                            )
                            NavigationBarItem(
                                icon={Icon(
                                    painter = painterResource(id = R.drawable.baseline_tv_24),
                                    contentDescription = "series"
                                )},
                                selected = currentDestination?.route == DestinationSeries().toString(),
                                onClick = { navController.navigate(DestinationSeries()) }
                            )
                            NavigationBarItem(
                                icon={Icon(
                                    painter = painterResource(id = R.drawable.baseline_person_24),
                                    contentDescription = "acteurs"
                                )},
                                selected = currentDestination?.route == DestinationActeurs().toString(),
                                onClick = {
                                    viewModel.resetActors()
                                    navController.navigate(DestinationActeurs()) }
                            )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(navController, startDestination = DestinationProfil(),
                        Modifier.padding(innerPadding)) {
                        composable<DestinationProfil> { HomeScreen(windowSizeClass, navController) }
                        composable<DestinationFilms> { FilmsScreen(navController, viewModel) }
                        composable<DestinationSeries> { SeriesScreen(navController, viewModel) }
                        composable<DestinationActeurs> { ActeursScreen(navController, viewModel) }
                        composable("filmDetails/{filmId}") { backStackEntry ->
                            val filmId = backStackEntry.arguments?.getString("filmId")
                            filmId?.let { FilmsDetails(it, viewModel, navController) }
                        }
                        composable("serieDetails/{serieId}") { backStackEntry ->
                            val serieId = backStackEntry.arguments?.getString("serieId")
                            serieId?.let { SeriesDetails(it, viewModel, navController) }
                        }
                        composable("actorDetails/{actorId}") { backStackEntry ->
                            val actorId = backStackEntry.arguments?.getString("actorId")?.toInt()
                            actorId?.let { ActeursDetails(it, viewModel, navController) }
                        }


                    }
                }
            }
        }
    }
}


@Composable
fun HomeScreen(windowClass: WindowSizeClass, navController: NavController) {
    when (windowClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.racoon),
                    contentDescription = "Photo de profil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(128.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Black, CircleShape)
                )
                Text(
                    text = "Simon Rocket",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(32.dp))
                Text(text = "Agent des gardiens de la Galaxie")
                Text(
                    text = "Univers 387",
                    fontStyle = FontStyle.Italic
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.baseline_email_24),
                            contentDescription = "Mail",
                        )
                        Image(
                            painter = painterResource(id = R.drawable.linkedin),
                            contentDescription = "Linkedin",
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Column {
                        Text(text = "simon.rocket@gmail.com")
                        Text(text = "www.linkedin.com/in/simon-rocket")
                    }
                }
                Spacer(modifier = Modifier.height(80.dp))
                Button(onClick = {navController.navigate(DestinationFilms()) }) {
                    Text(text = "Démarrer")
                }
            }
        }
        else -> {
            Row(
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentSize(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.racoon),
                        contentDescription = "Photo de profil",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(128.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.Black, CircleShape)
                    )
                    Text(
                        text = "Simon Rocket",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(text = "Agent des gardiens de la Galaxie")
                    Text(
                        text = "Univers 387",
                        fontStyle = FontStyle.Italic
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentSize(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_email_24),
                                contentDescription = "Mail",
                            )
                            Image(
                                painter = painterResource(id = R.drawable.linkedin),
                                contentDescription = "Linkedin",
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Column {
                            Text(text = "simon.rocket@gmail.com")
                            Text(text = "www.linkedin.com/in/simon-rocket")
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(onClick = {navController.navigate(DestinationFilms())}) {
                        Text(text = "Démarrer")
                    }
                }

        }
    }
}}


