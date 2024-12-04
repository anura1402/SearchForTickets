package ru.anura.emtesttask.presentation

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.anura.emtesttask.R
import ru.anura.emtesttask.data.MockServer
import ru.anura.emtesttask.databinding.ActivityMainBinding
import ru.anura.emtesttask.presentation.plugs.PlugFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private var isItemSelected = false

    @Inject
    lateinit var mockServer: MockServer

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.selectedItemId = R.id.airTicketsFragment
        setupBottomNavigation()
        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.main_container)
            if (!isItemSelected) {
                when (currentFragment) {
                    is WelcomeFragment -> {
                        if (bottomNavigationView.selectedItemId != R.id.airTicketsFragment) {
                            isItemSelected = true
                            setSelectedNavigationItem(R.id.airTicketsFragment)

                        }
                    }

                    is TheCountryWasChosenFragment -> {
                        if (bottomNavigationView.selectedItemId != R.id.airTicketsFragment) {
                            isItemSelected = true
                            setSelectedNavigationItem(R.id.airTicketsFragment)
                        }
                    }

                    is WatchAllTicketsFragment -> {
                        if (bottomNavigationView.selectedItemId != R.id.airTicketsFragment) {
                            isItemSelected = true
                            setSelectedNavigationItem(R.id.airTicketsFragment)
                        }
                    }
                }
            }
        }
    }

    private fun setSelectedNavigationItem(itemId: Int) {
        bottomNavigationView.selectedItemId = itemId
    }


    private fun setupBottomNavigation() {
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.airTicketsFragment -> {
                    if (isItemSelected) {
                        true
                    } else {
                        launchWelcomeFragment()
                        isItemSelected = true
                        true
                    }

                }

                R.id.hotelsFragment -> {
                    isItemSelected = false
                    launchPlugFragment("кнопки меню \"Отели\"")
                    true
                }

                R.id.shorterFragment -> {
                    isItemSelected = false
                    launchPlugFragment("кнопки меню \"Короче\"")
                    true
                }

                R.id.subscriptionFragment -> {
                    isItemSelected = false
                    launchPlugFragment("кнопки меню \"Подписки\"")
                    true
                }

                R.id.profileFragment -> {
                    isItemSelected = false
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
            .addToBackStack(null)
            .commit()
    }

    private fun launchWelcomeFragment() {
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, WelcomeFragment.newInstance())
            .addToBackStack(WelcomeFragment.NAME)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        mockServer.stop()
    }
}


