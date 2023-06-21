package com.miniclip.footb.ui.host

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.miniclip.footb.databinding.ActivityAppContainerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppContainerActivity : AppCompatActivity() {
    private var _binding: ActivityAppContainerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        _binding = ActivityAppContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}