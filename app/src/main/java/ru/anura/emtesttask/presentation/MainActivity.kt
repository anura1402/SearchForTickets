package ru.anura.emtesttask.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.anura.emtesttask.R
import ru.anura.data.MockServer
import ru.anura.emtesttask.databinding.ActivityMainBinding
import ru.anura.emtesttask.di.AppComponent
import ru.anura.emtesttask.di.DaggerAppComponent
import ru.anura.emtesttask.presentation.PlugFragmentDirections.Companion.actionGlobalPlugFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private var isItemSelected = false

    private lateinit var navController: NavController

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(application)
    }

    @Inject
    lateinit var mockServer: MockServer

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottomNavigationView = binding.bottomNavigation
        bottomNavigationView.selectedItemId = R.id.airTicketsFragment

        supportFragmentManager.addOnBackStackChangedListener {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.main_container)
            if (!isItemSelected) {
                when (currentFragment) {
                    is ru.anura.feature_search.ui.WelcomeFragment -> {
                        if (bottomNavigationView.selectedItemId != R.id.airTicketsFragment) {
                            isItemSelected = true
                            setSelectedNavigationItem(R.id.airTicketsFragment)

                        }
                    }

                    is ru.anura.feature_tickets.ui.TheCountryWasChosenFragment -> {
                        if (bottomNavigationView.selectedItemId != R.id.airTicketsFragment) {
                            isItemSelected = true
                            setSelectedNavigationItem(R.id.airTicketsFragment)
                        }
                    }

                    is ru.anura.feature_tickets.ui.WatchAllTicketsFragment -> {
                        if (bottomNavigationView.selectedItemId != R.id.airTicketsFragment) {
                            isItemSelected = true
                            setSelectedNavigationItem(R.id.airTicketsFragment)
                        }
                    }
                }
            }
        }
        setupBottomNavigation()
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
//        this.supportFragmentManager.beginTransaction()
//            .replace(R.id.main_container, PlugFragment.newInstance(iconName))
//            .addToBackStack(null)
//            .commit()
//        val action = NavGraphDirections.actionGlobalPlugFragment(iconName)
//        navController.navigate(action)
        val navController =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        navController.navController.navigate(R.id.action_global_plugFragment)
        //NavHostFragment.findNavController(requireParentFragment()).navigate(R.id.action_global_plugFragment)

    }

    private fun launchWelcomeFragment() {
//        this.supportFragmentManager.beginTransaction()
//            .replace(R.id.main_container, ru.anura.feature_search.ui.WelcomeFragment.newInstance())
//            .addToBackStack(ru.anura.feature_search.ui.WelcomeFragment.NAME)
//            .commit()
        val navController =
            supportFragmentManager.findFragmentById(R.id.main_container) as NavHostFragment
        navController.navController.navigate(R.id.action_global_welcomeFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        mockServer.stop()
    }
}


