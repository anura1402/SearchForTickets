package ru.anura.emtesttask.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.anura.emtesttask.R
import ru.anura.emtesttask.data.MockServer
import ru.anura.emtesttask.databinding.FragmentWelcomeBinding
import ru.anura.emtesttask.presentation.adapters.OffersListAdapter
import javax.inject.Inject

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: WelcomeViewModel

    @Inject
    lateinit var mockServer: MockServer

    private val component by lazy {
        (requireActivity().application as SearchApp).component
    }

    private lateinit var offersListAdapter: OffersListAdapter
    private var dialog = SearchDialogFragment()

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        viewModel = ViewModelProvider(this, viewModelFactory)[WelcomeViewModel::class.java]
        observers()

        // сохраняем введенное значение
        binding.etFrom.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus && binding.etFrom.text.toString().replace(" ", "").isNotEmpty()) {
                viewModel.saveCachedText(binding.etFrom.text.toString().trim())
            }
        }

        //открытие модального окна
        binding.etTo.setOnClickListener {
            dialog = SearchDialogFragment.newInstance(binding.etFrom.text.toString().trimEnd())
            dialog.show(parentFragmentManager, "BottomSheetDialog")
        }

    }

    private fun observers() {
        viewModel.loadCachedText()
        viewModel.cachedText.observe(viewLifecycleOwner) { cachedText ->
            binding.etFrom.setText(cachedText)
        }
        viewModel.getOffers()
        viewModel.offers.observe(viewLifecycleOwner) { offers ->
            offersListAdapter.offerList = offers
        }
    }


    private fun setupRecyclerView() {
        val rvFlyMusically = binding.rvFlyMusically
        with(rvFlyMusically) {
            offersListAdapter = OffersListAdapter()
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = offersListAdapter
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        const val NAME = "WelcomeFragment"

        fun newInstance(): WelcomeFragment {
            return WelcomeFragment()
        }
    }
}