package ca.georgebrown.keags.a1

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DisplayMode.Companion.Input
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.georgebrown.keags.a1.ui.theme.A1Theme
import java.time.format.TextStyle
import kotlin.jvm.java

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            A1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Calc(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Calc(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally, // Cross-axis centering
        verticalArrangement = Arrangement.Center
    ) {
        var hoursWorked by rememberSaveable { mutableStateOf("") }
        var hourlyRate by rememberSaveable { mutableStateOf("") }
        var taxRate by rememberSaveable { mutableStateOf("") }


        Text(
            text = "Enter hours worked",
            fontSize=30.sp
        )

        TextField(
            value = hoursWorked,
            onValueChange = { hoursWorked = it },
            singleLine = true,
            modifier = Modifier.width(200.dp).height(50.dp),
        )


        Text(
            text = "Enter hourly rate",
            fontSize=30.sp
        )
        TextField(
            value = hourlyRate,
            onValueChange = { hourlyRate = it },
            singleLine = true,
            modifier = Modifier.width(200.dp).height(50.dp)
        )


        Text(
            text = "Enter tax rate",
            fontSize=30.sp
        )
        TextField(
            value = taxRate,
            onValueChange = { taxRate = it },
            singleLine = true,
            modifier = Modifier.width(200.dp).height(50.dp)
        )
        Spacer(modifier= Modifier.padding(bottom= 100.dp))


        var hours= hoursWorked.toDoubleOrNull() ?: 0.0
        var hourRate= hourlyRate.toDoubleOrNull() ?: 0.0
        var taxRate2= taxRate.toDoubleOrNull() ?: 0.0

        var pay = 0.0
        var overtimePay = 0.0
        var totalPay = 0.0
        var tax = 0.0

        if(hours <= 40){
            pay=hours*hourRate
            overtimePay=0.0
            tax = pay*taxRate2
            totalPay=pay
        }
        else{
            pay = 40*hourRate
            overtimePay=(hours-40)*hourRate*1.5
            totalPay = pay+overtimePay
            tax=pay*taxRate2
        }
        Spacer(modifier= Modifier.padding(bottom= 20.dp))

        Text(
            text= "Pay: $$pay",
            fontSize=30.sp
        )
        Text(
            text= "Overtime Pay: $$overtimePay",
            fontSize=30.sp
        )
        Text(
            text= "Total Pay: $$totalPay",
            fontSize=30.sp
        )
        Text(
            text= "Tax: $$tax",
            fontSize=30.sp
        )

    }
    NavigateButton()

}

@Composable
fun NavigateButton() {
    val context = LocalContext.current

    Button(onClick = {
        val intent = Intent(context, AboutActivity::class.java)
        context.startActivity(intent)
    }) {
        Text("Go to Next Activity")
    }
}
@Preview(showBackground = true)
@Composable
fun calcPreview() {
    A1Theme {
        Calc("Android")
    }
}
