package ru.anura.emtesttask.presentation

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import ru.anura.emtesttask.R
import ru.anura.emtesttask.databinding.FragmentTheCountryWasChosenBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class TheCountryWasChosenFragment : Fragment() {
    private var _binding: FragmentTheCountryWasChosenBinding? = null
    private val binding: FragmentTheCountryWasChosenBinding
        get() = _binding ?: throw RuntimeException("FragmentTheCountryWasChosenBinding == null")

    private lateinit var from: String
    private lateinit var to: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    private fun parseArgs() {
        requireArguments().getString(KEY_FROM)?.let {
            from = it
        }
        requireArguments().getString(KEY_TO)?.let {
            to = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTheCountryWasChosenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            tvTo.text = to
            tvFrom.text = from
            icChange.setOnClickListener {
                val temp = tvFrom.text
                tvFrom.text = tvTo.text
                tvTo.text = temp
            }
            icClean.setOnClickListener {
                tvTo.text = ""
            }
            buttonDate.text = formatDateWithStyle(Date())
            buttonDate.setOnClickListener {
                openCalendar(buttonDate)
            }
            buttonBackWay.setOnClickListener {
                openCalendar(buttonBackWay)
            }
            watchAllButton.setOnClickListener {
                launchWatchAllTicketsFragment(tvFrom.text.toString(),tvTo.text.toString(),buttonDate.text.toString(),COUNT_OF_PASSENGERS)
            }
        }
    }

    private fun openCalendar(button: Button) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDayOfMonth)
                }
                button.text = formatDateWithStyle(selectedDate.time)
            },
            year, month, dayOfMonth
        )
        datePickerDialog.show()
    }


    private fun formatDateWithStyle(date: Date): SpannableString {
        val dateFormat = SimpleDateFormat("d MMM, E", Locale("ru")).format(date).replace(".", "")
        val spannableString = SpannableString(dateFormat)

        val dayOfWeekStartIndex = dateFormat.indexOf(",") + 2
        spannableString.setSpan(
            ForegroundColorSpan(Color.GRAY),
            dayOfWeekStartIndex,
            dateFormat.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        return spannableString
    }

    private fun launchWatchAllTicketsFragment(from:String,to:String,date:String,count:String) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, WatchAllTicketsFragment.newInstance(from,to,date, count))
            .addToBackStack(null)
            .commit()
        Log.d("countPass",count)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    companion object {
        private const val KEY_FROM = "key_from"
        private const val KEY_TO = "key_to"
        private const val COUNT_OF_PASSENGERS = "1"

        const val NAME = "TheCountryWasChosenFragment"

        fun newInstance(from: String, to: String): TheCountryWasChosenFragment {
            return TheCountryWasChosenFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_FROM, from)
                    putString(KEY_TO, to)
                }
            }
        }
    }
}