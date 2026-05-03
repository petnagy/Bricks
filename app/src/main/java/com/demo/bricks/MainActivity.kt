package com.demo.bricks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.bricks.preferences.Key
import com.bricks.preferences.PreferencesBuilder
import com.bricks.preferences.PreferencesType
import com.bricks.version.BuildType
import com.bricks.version.VersionFormatType
import com.bricks.version.VersionNumber
import com.bricks.version.VersionNumberFormatter
import com.demo.bricks.ui.theme.BricksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val preferences = PreferencesBuilder(this)
            .type(PreferencesType.PREFERENCES_DATASTORE)
            .name("Test")
            .build()

        val key = Key.StringKey("test_key", "")

        setContent {
            BricksTheme {
                val version =
                    VersionNumber.Builder(BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
                        .buildType(
                            if (BuildConfig.DEBUG) {
                                BuildType.DEBUG
                            } else {
                                null
                            }
                        ).build()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val formatter = VersionNumberFormatter.Builder().formatType(
                            VersionFormatType.ClassicWithBuildType
                        ).build()
                        Text("version: ${formatter.format(version)}")

                        var number by remember { mutableStateOf("") }

                        TextField(
                            value = number,
                            onValueChange = { number = it },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            label = { Text("Enter a number here") }
                        )

                        Button(onClick = { preferences.putValue(
                            key = key,
                            value = number
                        ) }) {
                            Text("Save")
                        }

                        val state = preferences.asyncGetValue(key).collectAsState("")
                        Text("Key's value: ${state.value}")

                    }
                }
            }
        }
    }
}
