package com.jack.utilitatask.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.jack.utilitatask.databinding.StatusFragmentBinding

class StatusFragment: Fragment() {

    private val binding by viewBinding(StatusFragmentBinding::bind)

    private val args: StatusFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return StatusFragmentBinding.inflate(layoutInflater).root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with (binding) {
            textViewStatusName.text = args.statusName
            textViewResponseCode.text = "Response Code: ${args.status.responseCode}"
            if (args.status.responseTime != null) {
                textViewResponseTime.text = "Response Time: ${args.status.responseTime}"
            }
            if (args.status.url != null) {
                textViewUrl.text = args.status.url
            }
            textViewClass.text = "Class: ${args.status.`class`}"
        }
    }
}