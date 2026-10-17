package ca.georgebrown.keags.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.georgebrown.keags.lab4.ui.theme.Lab4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val database = AppDatabase.getDatabase(applicationContext)
        setContent {
            Lab4Theme {
                val customerViewModel: CustomerViewModel = viewModel(
                    factory = CustomerViewModelFactory(database.customerDao())
                )
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CustomerApp(customerViewModel, Modifier.padding(innerPadding))
                }
            }
        }
    }
}
