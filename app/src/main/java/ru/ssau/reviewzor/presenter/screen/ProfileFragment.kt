package ru.ssau.reviewzor.presenter.screen

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.ssau.reviewzor.createUniqueImageFile
import ru.ssau.reviewzor.databinding.FragmentProfileBinding
import ru.ssau.reviewzor.domain.entity.ProfileModel
import ru.ssau.reviewzor.presenter.adapter.PlaceItemAdapter
import ru.ssau.reviewzor.presenter.base.BaseFragment
import ru.ssau.reviewzor.presenter.dialog.PhotoOptionDialogFragment
import ru.ssau.reviewzor.presenter.viewModel.ProfileViewModel
import java.io.File

class ProfileFragment : BaseFragment<FragmentProfileBinding>(),
    PhotoOptionDialogFragment.PhotoOptionDialogListener {

    private val profileViewModel by viewModel<ProfileViewModel>()

    private val adapter: PlaceItemAdapter by lazy { PlaceItemAdapter(requireContext()) }

    private var photoFile: File? = null

    override fun initBinding(inflater: LayoutInflater): FragmentProfileBinding =
        FragmentProfileBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        setObserver()
    }

    private fun setObserver() {
        profileViewModel.favoritesPlaces.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        profileViewModel.profile?.observe(viewLifecycleOwner) {
            binding.editTextName.setText(it?.name)
            binding.editTextSecondName.setText(it?.secondName)
        }
        profileViewModel.exit.observe(viewLifecycleOwner) {
            if (it) activity?.finish()
        }
    }

    private fun setAdapter() {
        binding.rvPlaces.adapter = adapter
        binding.rvPlaces.layoutManager = LinearLayoutManager(this.context)
        adapter.onFollow = {
            profileViewModel.update(it)
        }
        adapter.onClick = {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToPlaceDetailFragment(it)
            )
        }

        binding.edit.setOnClickListener {
            profileViewModel.updateProfile(
                name = binding.editTextName.text.toString(),
                secondName = binding.editTextSecondName.text.toString()
            )
        }
        profileViewModel.imageUrl.observe(viewLifecycleOwner) {
            Log.d("Logger", it)
            if (it.isNotEmpty()) {
                binding.imageView.load(it)
            }
        }
        binding.imageView.setOnClickListener {
            replaceImage()
        }

        binding.exit.setOnClickListener {
            profileViewModel.deleteToken()
        }
    }

    private fun replaceImage() {
        val newFragment = PhotoOptionDialogFragment.newInstance(requireContext())
        newFragment?.show(childFragmentManager, "photoOptionDialog")
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
                    profileViewModel.uploadPhoto(photoFile)
                }
                REQUEST_GALLERY_IMAGE -> if (data != null && data.data != null) {
                    profileViewModel.uploadPhoto(File(data.dataString))
                }
            }
        }
    }

    override fun onPickClick() {
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickIntent, REQUEST_GALLERY_IMAGE)
    }

    companion object {
        private const val REQUEST_CAPTURE_IMAGE = 1
        private const val REQUEST_GALLERY_IMAGE = 2
    }
}