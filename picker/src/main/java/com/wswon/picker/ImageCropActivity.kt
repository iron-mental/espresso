package com.wswon.picker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.wswon.picker.common.BaseActivity
import com.wswon.picker.databinding.ActivityImageCropBinding
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*


class ImageCropActivity : BaseActivity<ActivityImageCropBinding>(R.layout.activity_image_crop) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageUri = intent?.getParcelableExtra(ARG_IMAGE_URI) as? Uri

        binding.cropImageView.apply {
            setImageUriAsync(imageUri)
            setOnCropImageCompleteListener { _, result: CropImageView.CropResult ->
                val uri = getImageUri(this@ImageCropActivity, result.bitmap)

                setResult(Activity.RESULT_OK, Intent().apply {
                    putExtra(CropImage.CROP_IMAGE_EXTRA_RESULT, uri)
                })
                finish()
            }
        }

        binding.cropButton.setOnClickListener {
            binding.cropImageView.getCroppedImageAsync()
        }

        binding.back.setOnClickListener {
            finish()
        }

    }


    private fun getImageUri(context: Context, inImage: Bitmap): Uri? {
        val tempDir = File(cacheDir.absolutePath)

        val tempFile = File.createTempFile("cache${UUID.randomUUID()}", ".jpg", tempDir)
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val bitmapData: ByteArray = bytes.toByteArray()

        val fos = FileOutputStream(tempFile)
        fos.write(bitmapData)
        fos.flush()
        fos.close()
        return Uri.fromFile(tempFile)
    }

    companion object {
        private const val ARG_IMAGE_URI = "ARG_IMAGE_URI"

        fun getIntent(context: Context, imageUri: Uri) =
            Intent(context, ImageCropActivity::class.java).apply {
                putExtra(ARG_IMAGE_URI, imageUri)
            }
    }
}