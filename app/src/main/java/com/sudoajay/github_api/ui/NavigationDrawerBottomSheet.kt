package com.sudoajay.github_api.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sudoajay.github_api.R
import com.sudoajay.github_api.databinding.LayoutNavigationDrawerBottomSheetBinding
import com.sudoajay.github_api.helper.Toaster
import javax.inject.Inject

class NavigationDrawerBottomSheet @Inject constructor() : BottomSheetDialogFragment() {

    private val developerLink = "https://github.com/SudoAjay"
    private val appLink = ""
    val moreAppLink = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val myDrawerView =
            layoutInflater.inflate(R.layout.layout_navigation_drawer_bottom_sheet, null)
        val binding = LayoutNavigationDrawerBottomSheetBinding.inflate(
            layoutInflater,
            myDrawerView as ViewGroup,
            false
        )
        binding.navigation = this

        return binding.root
    }

    private fun callToast() =
        Toaster.showToast(requireContext(), getString(R.string.workOnProgress_text))


    fun rateUs() = callToast()

    fun moreApp() = callToast()

    fun sendFeedback() =  startActivity(Intent(requireContext(), SendFeedback::class.java))


    fun shareApk() =openLink(appLink)


    fun developerPage() = openLink(developerLink)

    fun getVersionName(): String {
        var versionName = "1.0.0"
        try {
            versionName = requireContext().packageManager
                .getPackageInfo(requireContext().packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return "%s %s".format(getString(R.string.app_version_text), versionName)
    }

    private fun openLink(page:String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(page)
        startActivity(i)
    }
}

