package com.example.applicationdatastore.ui.pantailak

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import androidx.compose.material3.Button
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

//DataStore-rekin lan egitea: libs.versions.toml, eta build.gradle.kta aldatu behar dira
val Context.dataStore by preferencesDataStore(name = "datos")

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
fun LPantaila(navController: NavHostController) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val informacionKey = stringPreferencesKey("informacion")

    var texto by remember {
        mutableStateOf("")
    }

    val textoD by context.dataStore.data
        .map { prefs ->
            prefs[informacionKey] ?: ""
        }
        .collectAsState(initial = "")

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){

        Spacer(modifier = Modifier.height(160.dp))

        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it }
        )
        Row() {
            Button(
                onClick = {
                    scope.launch {
                        context.dataStore.edit { prefs ->
                            prefs[informacionKey] = texto
                        }
                    }
                }
            ) {
                Text("Gorde")
            }
            Button(
                onClick = {
                    texto = ""
                }
            ) {
                Text("Garbitu")
            }
        }
        Text("Dato guardado: $textoD")
    }
}


