package com.example.enemcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.enemcompose.ui.theme.EnemComposeTheme
import com.google.android.gms.ads.MobileAds


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EnemComposeTheme {
                Surface {
                    Navigation()
                }
            }
        }
    }
}
