package com.example.bottomdrawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val drawerState = rememberBottomDrawerState(initialValue = BottomDrawerValue.Closed)
            val text = remember { mutableStateOf("") }
            val scope = rememberCoroutineScope()

            BottomDrawer(
                drawerShape = AbsoluteRoundedCornerShape(15.dp),
                drawerState = drawerState,
                drawerContent = {
                    LazyColumn(content = {
                        items(10){
                            ListItem(
                                modifier = Modifier.clickable {
                                    text.value = "Coin: ${it+1}"
                                    scope.launch {
                                        drawerState.close()
                                    }
                                },
                                text = {
                                    Text(text = "Coin: ${it+1}")
                                }
                            )
                            Divider()
                        }
                    })
                }, content = {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        OutlinedTextField(
                            value = text.value,
                            onValueChange = {},
                            enabled = false,
                            modifier = Modifier.clickable {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        )
                    }
                }
            )
        }
    }
}

