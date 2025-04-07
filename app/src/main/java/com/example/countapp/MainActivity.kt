package com.example.countapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.text.KeyboardOptions

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMIInputScreen()
        }
    }
}

@Composable
fun BMIInputScreen() {
    var weight by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var bmiResult by remember { mutableStateOf("") }
    var bmiCategory by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Height (cm)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val weightValue = weight.toFloatOrNull()
            val heightValue = height.toFloatOrNull()?.div(100) // Convert cm to meters
            if (weightValue != null && heightValue != null && heightValue > 0) {
                val bmi = weightValue / (heightValue * heightValue)
                bmiResult = "Your BMI: %.2f".format(bmi)
                bmiCategory = when {
                    bmi < 18.5 -> "Underweight"
                    bmi < 24.9 -> "Normal weight"
                    bmi < 29.9 -> "Overweight"
                    else -> "Obese"
                }
            } else {
                bmiResult = "Invalid input"
                bmiCategory = ""
            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text("Calculate BMI")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (bmiResult.isNotEmpty()) {
            Text(text = bmiResult, style = MaterialTheme.typography.headlineSmall)
            Text(text = bmiCategory, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BMIInputScreenPreview() {
    BMIInputScreen()
}
