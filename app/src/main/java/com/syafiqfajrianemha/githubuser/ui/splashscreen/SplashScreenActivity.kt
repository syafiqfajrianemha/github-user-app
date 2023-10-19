package com.syafiqfajrianemha.githubuser.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.syafiqfajrianemha.githubuser.R
import com.syafiqfajrianemha.githubuser.ui.MainActivity
import com.syafiqfajrianemha.githubuser.ui.settings.SettingFactory
import com.syafiqfajrianemha.githubuser.ui.settings.SettingPreferences
import com.syafiqfajrianemha.githubuser.ui.settings.dataStore
import com.syafiqfajrianemha.githubuser.ui.viewmodel.SettingViewModel

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            val moveToMainActivity = Intent(this, MainActivity::class.java)
            startActivity(moveToMainActivity)
            finish()
        }, 2500)

        val pref = SettingPreferences.getInstace(application.dataStore)
        val settingViewModel =
            ViewModelProvider(this, SettingFactory(pref))[SettingViewModel::class.java]

        settingViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}