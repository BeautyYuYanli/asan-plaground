package com.example.asanplayground

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.tooling.preview.Preview
import com.example.asanplayground.ui.theme.ASanPlaygroundTheme


private const val TAG = "My Log"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ASanPlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ButtonList(arrayOf(
                        Pair("helloFromJNI", {Log.d(TAG, helloFromJNI().toString())}),
                        Pair("useAfterFree", {Log.d(TAG, useAfterFree().toString())}),
                        Pair("heapBufferOverflow", {Log.d(TAG, heapBufferOverflow().toString())}),
                        Pair("stackBufferOverflow", {Log.d(TAG, stackBufferOverflow().toString())}),
                        Pair("globalBufferOverflow", {Log.d(TAG, globalBufferOverflow().toString())}),
//                        Pair("stackUseAfterReturn?", {Log.d(TAG, stackUseAfterReturn().toString())}),
                        Pair("stackUseAfterScope", {Log.d(TAG, stackUseAfterScope().toString())}),
                    ))
                }
            }
        }
    }
    external fun helloFromJNI(): Int
    external fun useAfterFree(): Int
    external fun heapBufferOverflow(): Int
    external fun stackBufferOverflow(): Int
    external fun globalBufferOverflow(): Int
    external fun stackUseAfterReturn(): Int
    external fun stackUseAfterScope(): Int
    companion object {
        init {
            System.loadLibrary("asanplayground")
        }
    }
}




@Composable
fun MyButton(text: String, onClick: ()->Unit) {
    Button(
        onClick = onClick
    ) {
        Icon(
            Icons.Filled.Favorite,
            contentDescription = null
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
        Text(text = text)
    }
}


@Composable
fun ButtonList(pairs: Array<Pair<String, ()->Unit>>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        for (i in pairs){
            MyButton(text = i.first, onClick = i.second)
        }
    }
}
