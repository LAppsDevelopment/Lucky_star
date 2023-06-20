package com.miniclip.footb.ui.intro_screen

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.miniclip.footb.databinding.ActivityIntroBinding
import com.miniclip.footb.ui.intro_screen.interfaces.ActivityCancellable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroActivity : AppCompatActivity(), ActivityCancellable {
    private var _binding: ActivityIntroBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (cancellableActivity()) {
            finish()
            return
        }

        _binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun cancellableActivity(): Boolean {
        val hasCategory = intent?.hasCategory(Intent.CATEGORY_LAUNCHER)
        val isActionMain = intent?.action?.equals(Intent.ACTION_MAIN)

        return !isTaskRoot && hasCategory == true && isActionMain == true
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}