package com.zckj.compose.ui.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.zckj.compose.R
import com.zckj.compose.ui.Screen
import com.zckj.compose.viewmodels.MainViewModel

@Composable
fun Alert(vm: MainViewModel) {
    Dialog(onDismissRequest = { vm.openDialog = false }) {
        Card(shape = MaterialTheme.shapes.medium, modifier = Modifier.fillMaxWidth()) {
            Column {
                Head(vm)
                Spacer(
                    modifier = Modifier
                        .height(3.dp)
                        .fillMaxWidth()
                        .background(Color.Gray)
                )
            }
            Column {
                Row(
                    Modifier
                        .padding(16.dp)
                        .offset(x = 24.dp)
                        .clickable(onClick = {
                            vm.sendScreen(Screen.LazyList.route)
                            vm.openDialog = false
                        }),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Favorite, contentDescription = null, Modifier.size(32.dp))
                    Text(
                        text = "LazyColumn",
                        color = Color.Black,
                        modifier = Modifier.padding(start = 24.dp),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.W400
                    )
                }
            }
        }
    }
}

@Composable
private fun Head(vm: MainViewModel) {
    Column(Modifier.padding(start = 12.dp)) {
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            Image(
                painter = painterResource(id = R.drawable.close),
                contentDescription = null,
                Modifier
                    .padding(end = 12.dp, top = 6.dp)
                    .clickable { vm.openDialog = false })
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Filled.AccountCircle,
                    contentDescription = null,
                    Modifier.size(42.dp)
                )
            }
            Column(Modifier.padding(start = 4.dp)) {
                Text(text = "Name", fontStyle = FontStyle.Italic, color = Color.Black)
                Text(text = "www.connor.com", fontSize = 12.sp)
            }
        }
        Spacer(modifier = Modifier.height(18.dp))
        Card(
            modifier = Modifier
                .padding(start = 42.dp)
                .offset(x = 8.dp),
            border = BorderStroke(1.dp, Color.Gray)
        ) {
            Text(
                text = "Manage your account",
                Modifier.padding(top = 6.dp, bottom = 6.dp, start = 10.dp, end = 10.dp),
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}