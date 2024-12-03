package ru.anura.emtesttask.presentation

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.inputmethod.EditorInfo
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.anura.emtesttask.R
import ru.anura.emtesttask.databinding.DestinationDialogLayoutBinding
import ru.anura.emtesttask.presentation.plugs.PlugFragment


class SearchDialogFragment : BottomSheetDialogFragment() {
    private var _binding: DestinationDialogLayoutBinding? = null
    private val binding: DestinationDialogLayoutBinding
        get() = _binding ?: throw RuntimeException("DestinationDialogLayoutBinding == null")
    private lateinit var rootView: View



    override fun onStart() {
        super.onStart()
        val bottomSheet =
            dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        bottomSheet?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT

        val behavior = BottomSheetBehavior.from(bottomSheet!!)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.skipCollapsed = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DestinationDialogLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rootView = requireActivity().window.decorView
        actionsWithEditTextToCountry()
        buttonListeners()
    }

    private fun buttonListeners() {
        with(binding) {
            layoutDifficultWay.setOnClickListener {
                launchPlugFragment("кнопки \"Сложный маршрут\"")
            }
            layoutWhereever.setOnClickListener {
                etToDialog.setText(tvWhereever.text)
                etToDialog.setSelection(tvWhereever.text.length)
            }
            layoutHolidays.setOnClickListener {
                launchPlugFragment("кнопки \"Выходные\"")
            }
            layoutHotTickets.setOnClickListener {
                launchPlugFragment("кнопки \"Горячие билеты\"")
            }
            layoutIstanbul.setOnClickListener {
                etToDialog.setText(tvIstanbul.text)
                etToDialog.setSelection(tvIstanbul.text.length)
            }
            layoutSochi.setOnClickListener {
                etToDialog.setText(tvSochi.text)
                etToDialog.setSelection(tvSochi.text.length)
            }
            layoutPhuket.setOnClickListener {
                etToDialog.setText(tvPhuket.text)
                etToDialog.setSelection(tvPhuket.text.length)
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
            } else{
                etToDialog.requestFocus()
            }
            //Слушатель клавиатуры
            rootView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)

            //переход к новому экрану, когда editText теряет фокус
            etToDialog.setOnFocusChangeListener { _, hasFocus ->
                Log.d("etToDialog", "hasFocus: $hasFocus")
                if (!hasFocus && etToDialog.text.toString().replace(" ", "").isNotEmpty()) {
                    launchTheCountryWasChosenFragment("Минск",etToDialog.text.toString().replace(" ", ""))
                }
            }

            //переход к новому экрану по кнопке Поиск на клавиатуре
            etToDialog.setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        if (etToDialog.text.toString().replace(" ", "").isNotEmpty()) {
                            launchTheCountryWasChosenFragment("Минск",etToDialog.text.toString().replace(" ", ""))
                            return@setOnEditorActionListener true
                        }
                    }
                }
                false
            }
        }
    }

    private fun launchTheCountryWasChosenFragment(from:String,to:String) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, TheCountryWasChosenFragment.newInstance(from,to))
            .addToBackStack(null)
            .commit()
        dismiss()
    }

    private fun launchPlugFragment(iconName: String = "какого-то экрана") {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, PlugFragment.newInstance(iconName))
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
}
