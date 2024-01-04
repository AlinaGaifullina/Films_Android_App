package ru.itis.filmsandroidapp.core.widget.textfield

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AuthBottomText(descriptionText: String, btnText: String, onClick: () -> Unit) {
    Row(
        Modifier.padding(bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            descriptionText,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        TextButton(onClick = onClick) {
            Text(
                btnText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}