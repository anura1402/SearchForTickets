package ru.anura.feature_search.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import ru.anura.common.ContainerProvider
import ru.anura.feature_search.databinding.FragmentWelcomeBinding
import ru.anura.feature_search.di.FeatureSearchComponentProvider
import ru.anura.feature_search.viewmodel.WelcomeViewModel
import javax.inject.Inject

class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding: FragmentWelcomeBinding
        get() = _binding ?: throw RuntimeException("FragmentWelcomeBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: WelcomeViewModel

    private lateinit var offersListAdapter: ru.anura.feature_search.adapters.OffersListAdapter
    private var dialog = SearchDialogFragment()
    private lateinit var containerProvider: ContainerProvider


    override fun onAttach(context: Context) {
        val app = (requireActivity().applicationContext as FeatureSearchComponentProvider)
            .provideFeatureSearchComponent()
        app.inject(this)
        super.onAttach(context)
        if (context is ContainerProvider) {
            containerProvider = context
        } else {
            throw IllegalStateException("Activity must implement ContainerProvider")
        }
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
            binding.etFrom.clearFocus()
            dialog = SearchDialogFragment.newInstance(
                binding.etFrom.text.toString().trimEnd(),
                containerProvider.getContainerId()
            )
            dialog.show(parentFragmentManager, "BottomSheetDialog")
        }

    }


    private fun observers() {
        viewModel.loadCachedText()
        viewModel.cachedText.observe(viewLifecycleOwner) { cachedText ->
            binding.etFrom.setText(cachedText)
            binding.etFrom.clearFocus()
        }
        viewModel.getOffers()
        viewModel.offers.observe(viewLifecycleOwner) { offers ->
            offersListAdapter.offerList = offers
        }
    }


    private fun setupRecyclerView() {
        val rvFlyMusically = binding.rvFlyMusically
        with(rvFlyMusically) {
            offersListAdapter = ru.anura.feature_search.adapters.OffersListAdapter()
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