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
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.anura.emtesttask.R
import ru.anura.emtesttask.data.ApiFactory
import ru.anura.emtesttask.data.MockServer
import ru.anura.emtesttask.data.OffersRepositoryImpl
import ru.anura.emtesttask.databinding.FragmentWelcomeBinding
import ru.anura.emtesttask.domain.FlyMusicallyItem
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
    //private lateinit var viewModel: WelcomeViewModel

    private lateinit var offersListAdapter: OffersListAdapter
    private lateinit var imm: InputMethodManager
    private val dialog = SearchDialogFragment()

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
        //viewModel = ViewModelProvider(this)[WelcomeViewModel::class.java]
        viewModel = ViewModelProvider(this, viewModelFactory)[WelcomeViewModel::class.java]
        viewModel.getOffers()
        viewModel.offers.observe(viewLifecycleOwner) { offers ->

//            offers.forEach { offer ->
//                Log.d("WelcomeFragment", "Offer: $offer")
//            }
        }

        actionWithEtTo()

    }


    private fun actionWithEtTo() {
        imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        with(binding.etTo) {
            hideKeyboard(this)
            requestFocus()
            imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
            setOnClickListener {
                dialog.show(parentFragmentManager, "BottomSheetDialog")
            }
        }
    }

    private fun setupRecyclerView() {
        val rvFlyMusically = binding.rvFlyMusically
        val testList = DummyData.getDummyData()
        with(rvFlyMusically) {
            offersListAdapter = OffersListAdapter(testList)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = offersListAdapter
        }
    }

    private fun hideKeyboard(view: View) {
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mockServer.stop()
        _binding = null
    }

    object DummyData {
        fun getDummyData(): List<FlyMusicallyItem> {
            return listOf(
                FlyMusicallyItem(R.drawable.die_antwood, "Star 1", "Turkey", "$100"),
                FlyMusicallyItem(R.drawable.socrat_and_lera, "Star 2", "Italy", "$150"),
                FlyMusicallyItem(R.drawable.lampabict, "Star 3", "Spain", "$200")
            )
        }
    }

    companion object {
        const val NAME = "WelcomeFragment"

        fun newInstance(): WelcomeFragment {
            return WelcomeFragment()
        }
    }
}