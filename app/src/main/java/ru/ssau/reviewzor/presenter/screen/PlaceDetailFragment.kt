package ru.ssau.reviewzor.presenter.screen

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ssau.reviewzor.*
import ru.ssau.reviewzor.databinding.FragmentPlaceDetailBinding
import ru.ssau.reviewzor.presenter.base.BaseFragment
import ru.ssau.reviewzor.presenter.dialog.PhotoOptionDialogFragment
import ru.ssau.reviewzor.presenter.viewModel.DetailViewModel
import java.io.File

class PlaceDetailFragment : BaseFragment<FragmentPlaceDetailBinding>(),
    PhotoOptionDialogFragment.PhotoOptionDialogListener {

    private val args by navArgs<PlaceDetailFragmentArgs>()
    private val detailViewModel by viewModel<DetailViewModel>()
    private var photoFile: File? = null
    private var photoPath: String? = null

    override fun initBinding(inflater: LayoutInflater): FragmentPlaceDetailBinding =
        FragmentPlaceDetailBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel.sendResponse(args.name)
        initBinding()
        editListener()
    }

    @SuppressLint("ResourceType")
    private fun initBinding() {
        detailViewModel.bookmark.observe(viewLifecycleOwner) {
            binding.apply {
                editTextName.setText(it.name)
                editTextAddress.setText(it.address)
                editTextTextDetail.setText(it.detail)
                editRating.setText(it.rating.toString())
                if (resources.getStringArray(R.array.categories).contains(it.category)) {
                    spinner.setSelection(category(it.category))
                }
                if (it.image.isNotEmpty()) {
                    val bitmap = getImageWithAuthority(it.image.toUri())
                    if (bitmap != null) {
                        updateImage(bitmap)
                    }
                }
            }
        }
        binding.imageView.setOnClickListener {
            replaceImage()
        }
    }

    private fun replaceImage() {
        val newFragment = PhotoOptionDialogFragment.newInstance(requireContext())
        newFragment?.show(childFragmentManager, "photoOptionDialog")
    }

    private fun editListener() {
        binding.run {
            edit.setOnClickListener {
                val bookplace = detailViewModel.bookmark.value
                if (bookplace != null) {
                    val rating = editRating.text.toString().toDouble()
                    val rat = if (rating > 5.0) 5.0 else rating
                    val r = if (rat < -5.0) -5.0 else rat
                    if (photoPath != null) {
                        detailViewModel.update(
                            name = editTextName.text.toString(),
                            address = editTextAddress.text.toString(),
                            detail = editTextTextDetail.text.toString(),
                            category = spinner.selectedItem.toString(),
                            rating = r,
                            image = photoPath!!
                        )
                    } else {
                        detailViewModel.update(
                            name = editTextName.text.toString(),
                            address = editTextAddress.text.toString(),
                            detail = editTextTextDetail.text.toString(),
                            category = spinner.selectedItem.toString(),
                            rating = r,
                        )
                    }
                }
                findNavController().popBackStack()
            }
        }
    }


    private fun category(category: String): Int {
        return when (category) {
            "Shop" -> 0
            "Магазин" -> 0
            "Restaurant" -> 1
            "Ресторан" -> 1
            "Hotel" -> 2
            "Отель" -> 2
            "Other" -> 3
            "Другое" -> 3
            else -> 0
        }
    }

    override fun onCaptureClick() {
        photoFile = null
        try {
            photoFile = createUniqueImageFile(requireContext())
        } catch (ex: java.io.IOException) {
            return
        }
        photoFile?.let { photoFile ->
            val photoUri = FileProvider.getUriForFile(
                requireContext(),
                "ru.ssau.reviewzor.provider",
                photoFile
            )

            val captureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)

            val intentActivities = activity?.packageManager?.queryIntentActivities(
                captureIntent, PackageManager.MATCH_DEFAULT_ONLY
            )
            intentActivities?.map { it.activityInfo.packageName }
                ?.forEach {
                    activity?.grantUriPermission(
                        it, photoUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )
                }
            startActivityForResult(captureIntent, REQUEST_CAPTURE_IMAGE)
        }
    }

    override fun onActivityResult(
        requestCode: Int, resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == android.app.Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CAPTURE_IMAGE -> {
                    val photoFile = photoFile ?: return
                    val uri = FileProvider.getUriForFile(
                        requireContext(),
                        "ru.ssau.reviewzor.provider",
                        photoFile
                    )
                    activity?.revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                    val image = getImageWithPath(photoFile.absolutePath)
                    val bitmap = rotateImageIfRequired(requireContext(), image, uri)
                    photoPath = uri.toString()
                    updateImage(bitmap)
                }
                REQUEST_GALLERY_IMAGE -> if (data != null && data.data != null) {
                    val imageUri = data.data as Uri
                    val image = getImageWithAuthority(imageUri)
                    image?.let {
                        val bitmap = rotateImageIfRequired(requireContext(), image, imageUri)
                        photoPath = imageUri.toString()
                        updateImage(bitmap)
                    }
                }
            }
        }
    }

    private fun updateImage(image: Bitmap) {
        binding.imageView.setImageBitmap(image)
    }

    private fun getImageWithPath(filePath: String) = decodeFileToSize(filePath, 200, 100)

    private fun getImageWithAuthority(uri: Uri) =
        decodeUriStreamToSize(uri, 200, 100, requireContext())

    override fun onPickClick() {
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickIntent, REQUEST_GALLERY_IMAGE)
    }

    companion object {
        private const val REQUEST_CAPTURE_IMAGE = 1
        private const val REQUEST_GALLERY_IMAGE = 2
    }
}