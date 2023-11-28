package com.tomsk.alykov.easypaytesttask.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.tomsk.alykov.easypaytesttask.R
import com.tomsk.alykov.easypaytesttask.databinding.ActivitySingleBinding
import com.tomsk.alykov.easypaytesttask.utils.Utils

class SingleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySingleBinding
    private lateinit var navController: NavController

    val easyPayViewModel: EasyPayViewModel by lazy {
        ViewModelProvider(this)[EasyPayViewModel::class.java]
    }
    private var savedToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySingleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_host_fragment_content_single)

        savedToken = Utils.loadSavedToken(this)
        if (savedToken.isNotEmpty()) {
            navController.navigate(R.id.action_LoginFragment_to_PaymentsFragment)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        return findNavController(R.id.nav_host_fragment_content_single).navigateUp(appBarConfiguration)
    }

}