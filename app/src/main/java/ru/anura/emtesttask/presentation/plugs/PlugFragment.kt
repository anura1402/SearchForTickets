package ru.anura.emtesttask.presentation.plugs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import ru.anura.emtesttask.R
import ru.anura.emtesttask.databinding.FragmentPlugBinding
import ru.anura.emtesttask.presentation.WelcomeFragment

class PlugFragment : Fragment() {
    private var _binding: FragmentPlugBinding? = null
    private val binding: FragmentPlugBinding
        get() = _binding ?: throw RuntimeException("FragmentPlugBinding == null")

    private var iconName: String = "null"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    private fun parseArgs() {
        requireArguments().getString(ICON_NAME)?.let {
            iconName = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlugBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvInfo.text = getString(R.string.plug_text, iconName)
        binding.returnButton.setOnClickListener{
            returnBack()
        }

    }
    private fun returnBack() {
        requireActivity().supportFragmentManager.popBackStack(
            PlugFragment.NAME,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        private const val ICON_NAME = "iconName"
        const val NAME = "PlugFragment"

        fun newInstance(iconName: String): PlugFragment {
            return PlugFragment().apply {
                arguments = Bundle().apply {
                    putString(ICON_NAME, iconName)
                }
            }
        }
    }
}