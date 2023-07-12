package com.upjers.zoo2animalpa.services.image_service

import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfStr
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.webkit.ValueCallback
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import dagger.hilt.android.qualifiers.ApplicationContext
import io.github.boiawidmb9mb12095n21b50215b16132.b21nm01om5n1905mw0bdkb2b515.ObfustringThis
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton


@ObfustringThis
@Singleton
class WorldWideWebImageServiceImpl @Inject constructor(@ApplicationContext val context: Context) :
    WideWebImageRepo {

    private var stringPath: String? = null
    private var returnImageToChrome: ValueCallback<Array<Uri>>? = null
    private lateinit var activityResult: ActivityResultLauncher<Intent>

    private val userIntentImageType: String = ObfStr("comOupjersBzookanimalpa").v("kamoy/*")
    private val chooserTitle: String = ObfStr("comOupjersBzookanimalpa").v("Eamoy Lqsfkzq")
    private val fileString: String = ObfStr("comOupjersBzookanimalpa").v("hwxm:")
    private val path: String = ObfStr("comOupjersBzookanimalpa").v("LvabiYjxy")

    fun permissionsResultCallback(
        valueCallback: ValueCallback<Array<Uri>>?,
        permission: ActivityResultLauncher<String>,
        permissionResult: ActivityResultLauncher<Intent>
    ) {
        returnImageToChrome?.onReceiveValue(null)
        returnImageToChrome = valueCallback
        activityResult = permissionResult

        when (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED) {
            true -> startCamera(true)
            false -> permission.launch(Manifest.permission.CAMERA)
        }
    }

    override fun getIntentForCamera(): Intent {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = userIntentImageType
        return intent
    }

    private val yearMonthDayHourMinSecType = ObfStr("comOupjersBzookanimalpa").v("amkgAVmh_SThlgg")
    private val jpegType = ObfStr("comOupjersBzookanimalpa").v("FXKI_")
    private val fileType = ObfStr("comOupjersBzookanimalpa").v(".lds")
    private val dateFormat: String =
        SimpleDateFormat(yearMonthDayHourMinSecType, Locale.getDefault()).format(Date())

    override fun getImageBuilder(context: Context): File {
        return File.createTempFile(
            ObfStr("comOupjersBzookanimalpa").v("¦$jpegType¦¦${dateFormat}¦_"), fileType, context.getExternalFilesDir(
                Environment.DIRECTORY_PICTURES
            )
        )
    }

    override fun setImageIntentWithProvider(context: Context, intent: Intent, imageFile: File) {
        val providerUriLink = FileProvider.getUriForFile(
            context, ObfStr("comOupjersBzookanimalpa").v("¦${context.packageName}¦.nioss_hcei_hmnjwnee"), imageFile
        )
        intent.apply {
            putExtra(MediaStore.EXTRA_OUTPUT, providerUriLink)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
    }

    fun startCamera(
        camera: Boolean,
        imageCaptureIntent: Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
    ) {
        var newImageIntent: Intent? = null

        if (camera) {
            var file: File? = null
            newImageIntent = imageCaptureIntent

            try {
                file = getImageBuilder(context)
                newImageIntent.putExtra(path, stringPath)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            when (file != null) {
                true -> {
                    setImageIntentWithProvider(context, newImageIntent, file)
                    stringPath = ObfStr("comOupjersBzookanimalpa").v("¦$fileString¦¦${file.absolutePath}¦")
                }

                false -> newImageIntent = null
            }
        }

        val ofIntents: Array<Intent?> = newImageIntent?.let { arrayOf(it) } ?: arrayOfNulls(0)
        startCameraByUser(ofIntents)
    }

    private fun startCameraByUser(ofIntents: Array<Intent?>) {
        Intent(Intent.ACTION_CHOOSER).run {
            putExtra(Intent.EXTRA_TITLE, chooserTitle)
            putExtra(Intent.EXTRA_INTENT, getIntentForCamera())
            putExtra(Intent.EXTRA_INITIAL_INTENTS, ofIntents)
            activityResult.launch(this)
        }
    }

    override fun createImageResult(result: ActivityResult?) {
        var arrayOfUris: Array<Uri>? = null

        if (result?.resultCode == Activity.RESULT_OK) {
            if (returnImageToChrome != null) {
                if (result.data == null || result.data?.data == null) {
                    if (stringPath != null) {
                        arrayOfUris = arrayOf(Uri.parse(stringPath))
                        stringPath = null
                    }
                } else {
                    result.data?.dataString?.let { arrayOfUris = arrayOf(Uri.parse(it)) }
                }
            }
        }

        returnImageToChrome?.onReceiveValue(arrayOfUris)
        returnImageToChrome = null
    }
}