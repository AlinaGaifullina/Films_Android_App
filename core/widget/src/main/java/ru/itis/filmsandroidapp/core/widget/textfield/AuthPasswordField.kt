package ru.itis.filmsandroidapp.core.widget.textfield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import ru.itis.filmsandroidapp.core.widget.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthPasswordField(
    label: String,
    value: String,
    onChange: (String) -> Unit,
    passwordVisible: Boolean,
    onVisibleChange: () -> Unit
) {
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
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            textStyle = MaterialTheme.typography.bodyMedium,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                containerColor = MaterialTheme.colorScheme.surface
            ),
            trailingIcon = {
                val image = if (passwordVisible) painterResource(id = R.drawable.ic_show) else painterResource(id = R.drawable.ic_hide)
                val description = if (passwordVisible) "Hide password" else "Show password"

                IconButton(onClick = onVisibleChange, modifier = Modifier.padding(end = 8.dp)) {
                    Icon(
                        painter = image,
                        description,
                        tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        )
    }
}