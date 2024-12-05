package ru.anura.feature_tickets.ui

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.anura.common.model.OfferTickets
import ru.anura.feature_tickets.R
import ru.anura.feature_tickets.databinding.FragmentTheCountryWasChosenBinding
import ru.anura.feature_tickets.viewmodel.TheCountryWasChosenViewModel
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class TheCountryWasChosenFragment : Fragment() {
    private var _binding: FragmentTheCountryWasChosenBinding? = null
    private val binding: FragmentTheCountryWasChosenBinding
        get() = _binding ?: throw RuntimeException("FragmentTheCountryWasChosenBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: TheCountryWasChosenViewModel


//    private val component by lazy {
//        (requireActivity().application as SearchApp).component
//    }

    private lateinit var from: String
    private lateinit var to: String

    override fun onAttach(context: Context) {
        //component.inject(this)
        super.onAttach(context)
    }

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
        viewModel =
            ViewModelProvider(this, viewModelFactory)[TheCountryWasChosenViewModel::class.java]
        viewModel.getOffersTickets()
        viewModel.offersTickets.observe(viewLifecycleOwner) { it ->
            with(binding) {
                updateOfferTicketInfo(0, it, tvTitle1, tvTime1, tvPrice1)
                updateOfferTicketInfo(1, it, tvTitle2, tvTime2, tvPrice2)
                updateOfferTicketInfo(2, it, tvTitle3, tvTime3, tvPrice3)
            }
        }

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
            buttonDate.text = viewModel.formatDateWithStyle(Date())
            buttonDate.setOnClickListener {
                openCalendar(buttonDate)
            }
            buttonBackWay.setOnClickListener {
                openCalendar(buttonBackWay)
            }
            watchAllButton.setOnClickListener {
                launchWatchAllTicketsFragment(
                    tvFrom.text.toString(),
                    tvTo.text.toString(),
                    buttonDate.text.toString(),
                    ru.anura.feature_tickets.ui.TheCountryWasChosenFragment.COUNT_OF_PASSENGERS
                )
            }
            ivBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun updateOfferTicketInfo(
        index: Int, offers: List<OfferTickets>,
        tvTitle: TextView,
        tvTime: TextView,
        tvPrice: TextView
    ) {
        val ticket = offers.getOrNull(index)

        ticket?.let {
            tvTitle.text = it.title

            tvTime.text = it.timeRange.joinToString("  ")
            tvTime.maxLines = 1
            tvTime.ellipsize = TextUtils.TruncateAt.END

            tvPrice.text =
                String.format(getString(R.string.ticket_price), formatPrice(it.price.value))
        }
    }


    private fun formatPrice(price: Int): String {
        val formatter = DecimalFormat("#,###", DecimalFormatSymbols(Locale("ru", "RU")))
        return formatter.format(price)
    }

    private fun openCalendar(button: Button) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val minDate = viewModel.getMinDateForBackWay()

        val datePickerDialog = DatePickerDialog(
            requireActivity(),
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(selectedYear, selectedMonth, selectedDayOfMonth)
                }
                button.text = viewModel.formatDateWithStyle(selectedDate.time)
                if (button == binding.buttonDate) {
                    viewModel.updateSelectedDateForButtonDate(selectedDate.time)
                }
            },
            year, month, dayOfMonth
        )

        datePickerDialog.datePicker.minDate = minDate
        datePickerDialog.show()
    }


    private fun launchWatchAllTicketsFragment(
        from: String,
        to: String,
        date: String,
        count: String
    ) {
//        requireActivity().supportFragmentManager.beginTransaction()
//            .replace(
//                R.id.main_container,
//                WatchAllTicketsFragment.newInstance(from, to, date, count)
//            )
//            .addToBackStack(WatchAllTicketsFragment.NAME)
//            .commit()

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