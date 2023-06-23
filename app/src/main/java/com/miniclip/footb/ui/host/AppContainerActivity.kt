package com.miniclip.footb.ui.host

import android.os.Bundle
import android.view.WindowManager
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

        // Set full screen
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}