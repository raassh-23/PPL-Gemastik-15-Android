package com.raassh.gemastik15.view.activity.dashboard

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.raassh.gemastik15.R
import com.raassh.gemastik15.databinding.ActivityDashboardBinding
import com.raassh.gemastik15.utils.showSnackbar

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView = binding.bottomNavView
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = navHostFragment.navController

//        val appBarConfiguration = AppBarConfiguration.Builder(
//            R.id.navigation_discover
//        ).build()
//        setupActionBarWithNavController(navController, appBarConfiguration)

        navView.setupWithNavController(navController)

        // temporary
        navView.setOnItemSelectedListener {
            if (it.itemId != R.id.navigation_discover) {
                binding.root.showSnackbar(getString(R.string.feature_not_available))
            }

            false
        }

        onBackPressedDispatcher.addCallback {
            if (!navController.navigateUp()) {
                finish()
            }
        }
    }
}