package com.example.enemcompose

import AdMobScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.enemcompose.ui.theme.EnemComposeTheme
import com.google.android.gms.ads.MobileAds


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MobileAds.initialize(this) {}
        val adMob = AdMobScreen(this)
        adMob.loadAd()

        setContent {
            EnemComposeTheme {
                Surface {
                    Navigation(showAdd = {
                        adMob.showAdd()
                        adMob.loadAd()
                    })
                }
            }
        }
    }
}
