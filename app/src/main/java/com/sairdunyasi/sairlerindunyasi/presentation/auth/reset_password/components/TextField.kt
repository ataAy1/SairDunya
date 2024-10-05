import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EmailInput(email: String, onEmailChange: (String) -> Unit, padding: Modifier = Modifier) {
    TextField(
        value = email,
        onValueChange = onEmailChange,
        label = { Text("Mail Adresinizi Giriniz") },
        modifier = Modifier
            .fillMaxWidth()
            .then(padding),
        singleLine = true
    )
}
