package com.miniclip.footb.ui.host

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.miniclip.footb.databinding.ActivityAppContainerBinding
import com.miniclip.footb.model.open_ai_api.HolderManager
import com.miniclip.footb.services.params.config.MyConfigImpl
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AppContainerActivity : AppCompatActivity() {
    private var _binding: ActivityAppContainerBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var remoteHolder: HolderManager

    @Inject
    lateinit var firebaseClient: MyConfigImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        _binding = ActivityAppContainerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getOpenAiCurrentKey()
    }

    private fun getOpenAiCurrentKey() {
        firebaseClient.customFetchAndActivate { configTask ->
            if (configTask.isSuccessful) {
                remoteHolder.remoteKeyHolder.openAPIKey = firebaseClient.getOpenAIKey()
            }
        }
    }

    fun killApp() {
        val finishApp = android.os.Process.myPid()
        finishAffinity()
        android.os.Process.killProcess(finishApp)
    }

}