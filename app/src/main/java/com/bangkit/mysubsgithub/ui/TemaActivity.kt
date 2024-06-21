package com.bangkit.mysubsgithub.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.bangkit.mysubsgithub.R
import com.bangkit.mysubsgithub.SettingPreferences
import com.bangkit.mysubsgithub.dataStore
import com.google.android.material.switchmaterial.SwitchMaterial

class TemaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tema)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val temaViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(application)).get(
            TemaViewModel::class.java
        )
        temaViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme.isChecked = false
            }
        }

        switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            temaViewModel.saveThemeSetting(isChecked)
        }
    }
}