package com.example.apptestefirebase

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.apptestefirebase.ui.theme.ApptestefirebaseTheme
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApptestefirebaseTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        AppFireBasePreview()

                }
            }
        }
    }
}

fun sendDataToDatabase(nome:String,endereco:String,cep:Int,cidade:String,estado:String,bairro:String) {

    val db = Firebase.firestore

    val cliente = hashMapOf (

        "nome" to nome,
        "endereco" to endereco,
        "cep" to cep,
        "cidade" to cidade,
        "estado" to estado,
        "bairro" to bairro
        )



    db.collection("cliente")
        .add(cliente)
        .addOnSuccessListener { documentReference->

            Log.d(TAG,"DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }


}

@Preview(showBackground = true)
@Composable
fun AppFireBasePreview() {
    AppFireBase()
}
@Composable
fun AppFireBase() {

    var nome by remember { mutableStateOf("") }
    var endereco by remember { mutableStateOf("") }
    var cidade by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var bairro by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .background(Color.Gray)
        .fillMaxSize(),
        Arrangement.Center
    ) {

        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center

        ) {
            TextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = endereco,
                onValueChange = { endereco = it },
                label = { Text("Endereço") }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
            ) {
            TextField(
                value = cep,
                onValueChange = { cep = it },
                label = { Text("CEP ") }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = estado,
                onValueChange = { estado = it },
                label = { Text("Estado ") }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = cidade,
                onValueChange = { cidade = it },
                label = { Text("Cidade ") }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = bairro,
                onValueChange = { bairro = it },
                label = { Text("Bairro ") }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = {
                    //Validaçao básica para verificar se o born é número ou não
                    val cepInt = cep.toIntOrNull()
                    if (cepInt != null) {
                        sendDataToDatabase(nome, endereco, cepInt, estado, cidade, bairro)

                        nome = ""
                        endereco = ""
                        cep = ""
                        estado = ""
                        cidade = ""
                        bairro = ""
                    } else {
                        Log.w(TAG, "Nascimento 'inválido'.Por favor, insira um número.")
                    }
                },
                enabled = nome.isNotBlank() && endereco.isNotBlank()&& cep.isNotBlank()&& estado.isNotBlank()&& cidade.isNotBlank()&& bairro.isNotBlank()
            ) { Text("Enviar") }
        }
    }
}