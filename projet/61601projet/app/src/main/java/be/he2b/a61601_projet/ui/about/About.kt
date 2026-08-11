package be.he2b.a61601_projet.ui.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import be.he2b.a61601_projet.R

@Preview(showBackground = true)
@Composable
fun AboutScreenPreview(){
    AboutScreen()
}


@Composable
fun AboutScreen(){
    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Sarah Okitombahe-tsha",
                fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(12.dp))
            Text("Matricule : 61601")
            Spacer(modifier = Modifier.height(12.dp))
            Text("Groupe : E122")
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "LogoESI"
            )
        }
    }

}

