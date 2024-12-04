package ru.anura.emtesttask.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.anura.emtesttask.R
import ru.anura.emtesttask.data.MockServer
import ru.anura.emtesttask.databinding.FragmentWatchAllTicketsBinding
import ru.anura.emtesttask.presentation.adapters.OffersListAdapter
import ru.anura.emtesttask.presentation.adapters.TicketsListAdapter
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

class WatchAllTicketsFragment:Fragment() {
    private var _binding: FragmentWatchAllTicketsBinding? = null
    private val binding: FragmentWatchAllTicketsBinding
        get() = _binding ?: throw RuntimeException("FragmentWatchAllTicketsBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: WatchAllTicketsViewModel


    private val component by lazy {
        (requireActivity().application as SearchApp).component
    }

    private lateinit var ticketsListAdapter: TicketsListAdapter
    private lateinit var from: String
    private lateinit var to: String
    private lateinit var date: String
    private lateinit var count: String

    override fun onAttach(context: Context) {
        component.inject(this)
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
        requireArguments().getString(KEY_DATE)?.let {
            date = it
        }
        requireArguments().getString(KEY_PASSENGERS)?.let {
            count = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWatchAllTicketsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel =
            ViewModelProvider(this, viewModelFactory)[WatchAllTicketsViewModel::class.java]
        viewModel.getTickets()
        setupRecyclerView()
        viewModel.tickets.observe(viewLifecycleOwner) { it ->
            ticketsListAdapter.ticketsList = it
        }

        with(binding) {
            tvDirection.text = String.format(getString(R.string.direction), from, to)
            tvDateAndCount.text = String.format(getString(R.string.date_and_count),formatDate(date), count)
            ivBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
        }
    }

    private fun setupRecyclerView() {
        val rvFlights = binding.rvFlights
        with(rvFlights) {
            ticketsListAdapter = TicketsListAdapter()
            layoutManager = LinearLayoutManager(context, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
            adapter = ticketsListAdapter
        }
    }

    private fun formatDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("d MMM, E", Locale("ru"))
        val outputFormat = SimpleDateFormat("d MMMM", Locale("ru"))

        val date = inputFormat.parse(inputDate)
        return outputFormat.format(date ?: throw IllegalArgumentException("Invalid date"))
    }

    override fun onDestroy() {
        super.onDestroy()
        //mockServer.stop()
        _binding = null
    }


    companion object {
        private const val KEY_FROM = "key_from"
        private const val KEY_TO = "key_to"
        private const val KEY_DATE = "key_date"
        private const val KEY_PASSENGERS = "key_passengers"

        const val NAME = "WatchAllTicketsFragment"

        fun newInstance(from: String, to: String, date: String, count:String): WatchAllTicketsFragment {
            return WatchAllTicketsFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_FROM, from)
                    putString(KEY_TO, to)
                    putString(KEY_DATE, date)
                    putString(KEY_PASSENGERS, count)
                }
            }
        }
    }

}