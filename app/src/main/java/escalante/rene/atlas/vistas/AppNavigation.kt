package escalante.rene.atlas.vistas

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import escalante.rene.atlas.viewmodels.PaisesViewModel

@Composable
fun AppNavigation(
    viewModel: PaisesViewModel,
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "paises",
        modifier = modifier
    ) {
        composable("paises") {
            PaisesScreen(
                paises = viewModel.paises.value,
                cargando = viewModel.cargando.value,
                error = viewModel.error.value,
                onReintentar = { viewModel.cargarPaises() },
                onPaisClick = { codigo -> navController.navigate("detalle/$codigo") },
                onJugar = { navController.navigate("juego") }
            )
        }
        composable("juego") {
            JuegoScreen(paises = viewModel.paises.value)
        }

        composable("detalle/{codigo}") { entrada ->
            val codigo = entrada.arguments?.getString("codigo") ?: ""
            DetalleScreen(
                pais = viewModel.buscarPais(codigo),
                onVolver = { navController.popBackStack() }
            )
        }
    }
}