package ru.itis.filmsandroidapp.core.widget.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp


@Composable
fun AuthTextField(label: String, value: String, onChange: (String) -> Unit) {
    Column {
        Text(
            modifier = Modifier.padding(start = 8.dp, bottom = 4.dp),
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary
        )
        TextField(
            value = value,
            onValueChange = onChange,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done,
            ),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            )
        )
    }
}