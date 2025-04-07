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

fun sendDataToDatabase(first:String,last:String,born:Int) {

    val db = Firebase.firestore

    val cliente = hashMapOf (

        "first" to first,
        "last" to last,
        "born" to born,
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

    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var born by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .background(Color.Gray)
        .fillMaxSize(),
        Arrangement.Center
    ) {

        Row() {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row() {
            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Sobrenome") }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row() {
            TextField(
                value = born,
                onValueChange = { born = it },
                label = { Text("Nascido em: ") }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                //Validaçao básica para verificar se o born é número ou não
                val bornInt = born.toIntOrNull()
                if(bornInt != null) {
                    sendDataToDatabase(name,lastName,bornInt)

                    name = ""
                    lastName= ""
                    born = ""
                }
                else{
                    Log.w(TAG,"Nascimento 'inválido'.Por favor, insira um número.")
                }
            },
            enabled = name.isNotBlank() && lastName.isNotBlank() && born.isNotBlank()
        ) {Text("Enviar") }
    }
}