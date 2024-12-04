package ru.anura.emtesttask.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.anura.emtesttask.R
import ru.anura.emtesttask.databinding.ActivityMainBinding
import ru.anura.emtesttask.presentation.plugs.PlugFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationView = binding.bottomNavigation
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.airTicketsFragment -> {
                    launchWelcomeFragment()
                    true
                }

                R.id.hotelsFragment -> {
                    launchPlugFragment("кнопки меню \"Отели\"")
                    true
                }

                R.id.shorterFragment -> {
                    launchPlugFragment("кнопки меню \"Короче\"")
                    true
                }

                R.id.subscriptionFragment -> {
                    launchPlugFragment("кнопки меню \"Подписки\"")
                    true
                }

                R.id.profileFragment -> {
                    launchPlugFragment("кнопки меню \"Профиль\"")
                    true
                }

                else -> false
            }
        }
    }

    private fun launchPlugFragment(iconName: String = "какого-то экрана") {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, PlugFragment.newInstance(iconName))
            .addToBackStack(PlugFragment.NAME)
            .commit()
    }
    private fun launchWelcomeFragment() {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, WelcomeFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    fun selectBottomNavigationItem(itemId: Int) {
        bottomNavigationView.selectedItemId = itemId
    }
}


