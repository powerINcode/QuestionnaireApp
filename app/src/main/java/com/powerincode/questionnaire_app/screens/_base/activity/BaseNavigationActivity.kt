package com.powerincode.questionnaire_app.screens._base.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.powerincode.questionnaire_app.R

/**
 * Created by powerman23rus on 12/02/2019.
 */
abstract class BaseNavigationActivity : BaseActivity() {
    open val navController : NavController by lazy { findNavController(R.id.nav_host_fragment) }
    open val appBarConfiguration : AppBarConfiguration by lazy { buildAppBarConfiguration() }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean {
        return NavigationUI.onNavDestinationSelected(item, navController) || super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.also {
            setupActionBarWithNavController(navController, appBarConfiguration)
        }
    }

    override fun onSupportNavigateUp() : Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    open protected fun buildAppBarConfiguration() : AppBarConfiguration = AppBarConfiguration(navController.graph)
}