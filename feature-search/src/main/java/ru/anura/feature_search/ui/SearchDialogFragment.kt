package ru.anura.feature_search.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.anura.common.PlugFragment
import ru.anura.feature_search.R
import ru.anura.feature_search.databinding.SearchDialogLayoutBinding
import ru.anura.feature_tickets.ui.TheCountryWasChosenFragment


class SearchDialogFragment : BottomSheetDialogFragment() {
    private var _binding: SearchDialogLayoutBinding? = null
    private val binding: SearchDialogLayoutBinding
        get() = _binding ?: throw RuntimeException("DestinationDialogLayoutBinding == null")
    private lateinit var rootView: View
    private lateinit var from: String
    private var containerId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()

    }

    private fun parseArgs() {
        requireArguments().getString(KEY_FROM)?.let {
            from = it
        }
        requireArguments().getInt(KEY_CONTAINER_ID).let {
            containerId = it
        }
    }

    override fun onStart() {
        super.onStart()
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val displayMetrics = resources.displayMetrics
        bottomSheet?.layoutParams?.height = (displayMetrics.heightPixels * 0.97).toInt()

        val behavior = BottomSheetBehavior.from(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchDialogLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = requireActivity().window.decorView
        binding.etFromDialog.setText(from)
        actionsWithEditTextToCountry()
        buttonListeners()
    }

    private fun buttonListeners() {
        with(binding) {
            layoutDifficultWay.setOnClickListener {
                launchPlugFragment(getString(R.string.difficult_way_plug))
            }
            layoutWherever.setOnClickListener {
                etToDialog.requestFocus()
                etToDialog.setText(tvWherever.text)
                etToDialog.setSelection(tvWherever.text.length)
                etToDialog.clearFocus()
            }
            layoutHolidays.setOnClickListener {
                launchPlugFragment(getString(R.string.holidays_plug))
            }
            layoutHotTickets.setOnClickListener {
                launchPlugFragment(getString(R.string.hot_tickets_plug))
            }
            layoutIstanbul.setOnClickListener {
                etToDialog.requestFocus()
                etToDialog.setText(tvIstanbul.text)
                etToDialog.setSelection(tvIstanbul.text.length)
                etToDialog.clearFocus()
            }
            layoutSochi.setOnClickListener {
                etToDialog.requestFocus()
                etToDialog.setText(tvSochi.text)
                etToDialog.setSelection(tvSochi.text.length)
                etToDialog.clearFocus()
            }
            layoutPhuket.setOnClickListener {
                etToDialog.requestFocus()
                etToDialog.setText(tvPhuket.text)
                etToDialog.setSelection(tvPhuket.text.length)
                etToDialog.clearFocus()

            }
            icClean.setOnClickListener {
                etToDialog.setText("")
            }
        }
    }

    private fun actionsWithEditTextToCountry() {
        with(binding) {
            if (etFromDialog.text.toString().isEmpty()) {
                etFromDialog.requestFocus()
            } else {
                etToDialog.requestFocus()
            }
            //Слушатель клавиатуры
            rootView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)

            //переход к новому экрану, когда editText теряет фокус
            etToDialog.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && etToDialog.text.toString().replace(" ", "").isNotEmpty()) {

                    launchTheCountryWasChosenFragment(
                        etFromDialog.text.toString().trimEnd(),
                        etToDialog.text.toString().trimEnd()
                    )
                }
            }

            //переход к новому экрану по кнопке Поиск на клавиатуре
            etToDialog.setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        if (etToDialog.text.toString().replace(" ", "").isNotEmpty()) {
                            launchTheCountryWasChosenFragment(
                                etFromDialog.text.toString().trimEnd(),
                                etToDialog.text.toString().trimEnd()
                            )
                            return@setOnEditorActionListener true
                        }
                    }
                }
                false
            }
        }
    }

    private fun launchTheCountryWasChosenFragment(from: String, to: String) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(containerId, TheCountryWasChosenFragment.newInstance(from, to))
            .addToBackStack(TheCountryWasChosenFragment.NAME)
            .commit()
        dismiss()
    }

    private fun launchPlugFragment(iconName: String = "какого-то экрана") {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(containerId, PlugFragment.newInstance(iconName))
            .addToBackStack(PlugFragment.NAME)
            .commit()
        dismiss()
    }


    private val globalLayoutListener = ViewTreeObserver.OnGlobalLayoutListener {
        val rect = Rect()
        rootView.getWindowVisibleDisplayFrame(rect)
        val screenHeight = rootView.height
        val keypadHeight = screenHeight - rect.bottom

        if (keypadHeight < screenHeight * 0.15) { // Клавиатура скрыта
            binding.etToDialog.clearFocus()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        rootView.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
        _binding = null
    }

    companion object {
        private const val KEY_FROM = "key_from"
        private const val KEY_CONTAINER_ID = "container_id"

        fun newInstance(from: String, containerId: Int): SearchDialogFragment {
            return SearchDialogFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_FROM, from)
                    putInt(KEY_CONTAINER_ID, containerId)
                }
            }
        }
    }
}
