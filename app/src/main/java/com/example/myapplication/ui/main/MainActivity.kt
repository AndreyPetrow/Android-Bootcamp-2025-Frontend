package com.example.myapplication.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.core.RoleConstants
import com.example.myapplication.core.SettingConstants
import com.example.myapplication.ui.login.AuthActivity
import com.example.myapplication.ui.main.account.AccountFragment
import com.example.myapplication.ui.main.event.EventFragment
import com.example.myapplication.ui.main.map.MapFragment
import com.example.myapplication.ui.main.notification.NotificationFragment
import com.example.myapplication.ui.main.searchVolunteer.SearchVolunteerFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var settings: SharedPreferences

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        settings = getSharedPreferences(SettingConstants.PREFS_FILE, Context.MODE_PRIVATE)

        if (settings.getLong(SettingConstants.PREF_ID, SettingConstants.ERROR_ID) == SettingConstants.ERROR_ID) {
            Log.d("Test", "(MainActivity) Пользователь не зарегистрирован, запускаем регистрацию.")
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
            return
        }

        window.navigationBarColor = ContextCompat.getColor(this, R.color.light_green)

        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        if (settings.getString(SettingConstants.PREF_ROLE, SettingConstants.DEF_VALUE) == RoleConstants.ROLE_USER) {
            val menuItem = bottomNavigationView.menu.getItem(1);
            menuItem.setVisible(false)
        }

        bottomNavigationView.setOnItemSelectedListener{ item ->
            //if (item.itemId == bottomNavigationView.selectedItemId) return@setOnItemSelectedListener false

            when (item.itemId) {
                R.id.map -> replaceFragment(MapFragment())
                R.id.volunteers -> replaceFragment(SearchVolunteerFragment())
                R.id.events -> replaceFragment(EventFragment())
                R.id.notification -> replaceFragment(NotificationFragment())
                R.id.profile -> replaceFragment(AccountFragment())
            }
            window.statusBarColor = ContextCompat.getColor(this, R.color.gray)

            return@setOnItemSelectedListener true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView, fragment).commit()
    }
}