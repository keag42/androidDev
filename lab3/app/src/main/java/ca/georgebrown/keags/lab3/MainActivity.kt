package ca.georgebrown.keags.lab3

import android.R.attr.bottom
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.georgebrown.keags.lab3.ui.theme.Lab3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Entire(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Entire(name: String, modifier: Modifier = Modifier) {
    ScrollableColumnExample()
}

@Composable
fun SimpleBubble(messageContent: String) {
    Surface(
        // 1. Set the shape (all corners rounded equally)
        shape = RoundedCornerShape(16.dp),
        // 2. Set the bubble background color
        color = MaterialTheme.colorScheme.primaryContainer,
        // 3. Automatically colors the text to contrast beautifully
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ) {
        Text(
            text = messageContent,
            fontSize=8.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 0.dp)
        )
    }
}

@Composable
fun SingleText(name: String, messageContent:String){
    Row(modifier = Modifier, verticalAlignment = Alignment.Bottom)
    {

        Image(
            painter = painterResource(id = R.drawable.icon),
            contentDescription = "A description of the image",
            modifier = Modifier.size(40.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            verticalArrangement = Arrangement.spacedBy((-4).dp)
        ) {
            Text(
                text = name,
                fontSize=10.sp
            )
            SimpleBubble(messageContent)

        }
    }
}
@Composable
fun ScrollableColumnExample() {
    // 1. Create and remember the scroll state
    val scrollState = rememberScrollState()

    // 2. Apply the verticalScroll modifier to your Column
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(10.dp),
    ) {
        repeat(3) {
            SingleText("Joe", "Hi!")
            SingleText("jim", "How are you?")

            SingleText("Joe", "test..1..2..3")
            SingleText("jim", "I hate coding!!!")
        }

    }
}
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Lab3Theme {
        Entire("Android")
    }
}