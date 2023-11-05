package hr.illuminative.mixscape

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import hr.illuminative.mixscape.ui.theme.MixscapeTheme
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {

    lateinit var cameraExecutor: ExecutorService
    lateinit var outputDirectory: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MixscapeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MainScreen(outputDirectory, cameraExecutor)
                }
            }
        }
        requestCameraPermission()

        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted ->
        if (isGranted) {
            Log.i("PERMISSION", "Permission granted")
        } else {
            Log.i("PERMISSION", "Permission denied")
        }
    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA,
            ) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("PERMISSION", "Permission previously granted")
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                this,
                android.Manifest.permission.CAMERA,
            ) -> Log.i("PERMISSION", "Show camera permissions dialog")

            else -> requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }
    @JvmName("getOutputDir")
    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else filesDir
    }
    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
