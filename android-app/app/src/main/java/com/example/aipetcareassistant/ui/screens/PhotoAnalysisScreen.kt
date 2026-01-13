package com.example.aipetcareassistant.ui.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.compose.runtime.rememberCoroutineScope
import com.example.aipetcareassistant.data.Analysis
import com.example.aipetcareassistant.network.ApiClient
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

@Composable
fun PhotoAnalysisScreen(petId: String, onDone: (Analysis) -> Unit) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val api = remember { ApiClient.create(context) }
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            selectedBitmap = bitmap
            errorMessage = null
        } else {
            errorMessage = "No se pudo capturar la foto."
        }
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            val bitmap = loadBitmapFromUri(context, uri)
            if (bitmap != null) {
                selectedBitmap = bitmap
                errorMessage = null
            } else {
                errorMessage = "No se pudo leer la imagen seleccionada."
            }
        } else {
            errorMessage = "No se seleccionó ninguna imagen."
        }
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            cameraLauncher.launch(null)
        } else {
            errorMessage = "Necesitamos permiso de cámara para tomar la foto."
        }
    }

    val galleryPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            galleryLauncher.launch("image/*")
        } else {
            errorMessage = "Necesitamos permiso para acceder a tus fotos."
        }
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Sube una foto para el análisis")
        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    cameraLauncher.launch(null)
                } else {
                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tomar foto")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    Manifest.permission.READ_MEDIA_IMAGES
                } else {
                    Manifest.permission.READ_EXTERNAL_STORAGE
                }
                if (ContextCompat.checkSelfPermission(context, permission)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    galleryLauncher.launch("image/*")
                } else {
                    galleryPermissionLauncher.launch(permission)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Seleccionar de la galería")
        }

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = errorMessage ?: "", color = MaterialTheme.colorScheme.error)
        }

        if (isLoading) {
            CircularProgressIndicator()
        }

        selectedBitmap?.let { bitmap ->
            Spacer(modifier = Modifier.height(16.dp))
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "Foto seleccionada",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val bitmap = selectedBitmap
                if (bitmap == null) {
                    errorMessage = "Selecciona una imagen antes de continuar."
                } else {
                    errorMessage = null
                    isLoading = true
                    coroutineScope.launch {
                        try {
                            val part = bitmap.toJpegPart()
                            val analysis = api.uploadPhoto(petId, part)
                            onDone(analysis)
                        } catch (exception: Exception) {
                            errorMessage = "No se pudo analizar la imagen. Intenta nuevamente."
                        } finally {
                            isLoading = false
                        }
                    }
                }
            },
            enabled = selectedBitmap != null && !isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (isLoading) "Analizando..." else "Continuar")
        }
    }
}

private fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
    return runCatching {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        }
    }.getOrNull()
}

private fun Bitmap.toJpegPart(): MultipartBody.Part {
    val output = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.JPEG, 90, output)
    val bytes = output.toByteArray()
    val requestBody = bytes.toRequestBody("image/jpeg".toMediaType())
    return MultipartBody.Part.createFormData("image", "photo.jpg", requestBody)
}
