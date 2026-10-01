package ca.georgebrown.keags.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ca.georgebrown.keags.lab2.ui.theme.Lab2Theme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab2Theme {
                Entire()
            }
        }
    }
}


@Composable
fun Plus(modifier: Modifier = Modifier,onClickAction: () -> Unit) {
    Button(
        modifier = modifier,
        colors= ButtonDefaults.buttonColors(
            Color(0xFF235217)
        ),
        shape = RectangleShape,
        onClick = onClickAction,

    ) {
        Text(text = "+")
    }
}
@Composable
fun Minus(modifier: Modifier = Modifier,onClickAction: () -> Unit) {
    Button(
        modifier = modifier,
        shape = RectangleShape,
        colors= ButtonDefaults.buttonColors(Color(0xFF235217)),
        onClick = onClickAction,
    ) {
        Text(text = "-")
    }
}
@Composable
fun Reset(modifier: Modifier = Modifier,onClickAction: () -> Unit) {
    Button(
        modifier = modifier,
        shape = RectangleShape,
        colors= ButtonDefaults.buttonColors(Color.Red),
        onClick = onClickAction,
    ) {
        Text(text = "Reset")
    }
}
@Composable
fun Step(modifier: Modifier = Modifier,onClickAction: () -> Unit) {
    Button(
        modifier = modifier,
        shape = RectangleShape,
        colors= ButtonDefaults.buttonColors(Color(0xFF287d13)),
        onClick = onClickAction,
    ) {
        Text(text = "Step")
    }
}

@Composable
fun Entire(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        var count by remember { mutableStateOf(0) }
        var countStep by remember { mutableStateOf(1) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(id = R.drawable.my_image),
                    contentDescription = "A description of the image",
                    modifier = Modifier.size(200.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text("Current Count: $count")
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Minus(modifier.width(90.dp),onClickAction={count-=countStep})
                Spacer(modifier = Modifier.width(16.dp))
                Plus(modifier.width(90.dp),onClickAction={count+=countStep})
            }

            Spacer(modifier = Modifier.height(1.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Reset(modifier.width(90.dp),onClickAction={count=0;countStep=1})
                Spacer(modifier = Modifier.width(16.dp))
                Step(modifier.width(90.dp),onClickAction={countStep+=1})
            }
        }
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun EntirePreview() {
    Lab2Theme {
       Entire()
    }
}