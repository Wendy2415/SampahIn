package com.capstone.sampahin.ui.scan

import android.net.Uri
import androidx.lifecycle.ViewModel

class ScanViewModel : ViewModel() {
    var currentImageUri: Uri? = null
    var resultLabel: String? = null
    var description: String? = null
    var result: String? = null
}