package com.tomsk.alykov.easypaytesttask.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.tomsk.alykov.easypaytesttask.R
import com.tomsk.alykov.easypaytesttask.databinding.FragmentPaymentsBinding
import com.tomsk.alykov.easypaytesttask.presentation.adapters.PaymentsListAdapter
import com.tomsk.alykov.easypaytesttask.presentation.adapters.SpaceDecoration
import com.tomsk.alykov.easypaytesttask.utils.Utils

class PaymentsFragment : Fragment() {

    private lateinit var binding: FragmentPaymentsBinding
    private lateinit var easyPayViewModel: EasyPayViewModel
    private lateinit var paymentsListAdapter: PaymentsListAdapter
    private var savedToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        easyPayViewModel = (activity as SingleActivity).easyPayViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.show()
        (activity as AppCompatActivity).supportActionBar?.title =
            requireContext().getString(R.string.payments_list_caption)
        setHasOptionsMenu(true)

        setUi()
        observeViewModel()

        savedToken = Utils.loadSavedToken(requireContext())
        if (savedToken.isNotEmpty()) {
            easyPayViewModel.getPaymentsList(savedToken)
        }

    }

    private fun setUi() {
        paymentsListAdapter = PaymentsListAdapter(requireContext())
        binding.paymentsListRV.apply {
            layoutManager = GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false)
            adapter = paymentsListAdapter
            addItemDecoration(SpaceDecoration(resources.getDimensionPixelSize(R.dimen.space_decorator_size)))
        }
    }

    private fun observeViewModel() {
        easyPayViewModel.token.observe(viewLifecycleOwner) { token ->
            token?.let {
                Snackbar.make(
                    requireView(), "Token: $token",
                    Snackbar.LENGTH_LONG
                ).show()
                easyPayViewModel.getPaymentsList(savedToken)
            }
        }
        //val t = "7b7c0a690bee2e8d90512ed1b57e19f0"

        easyPayViewModel.paymentsList.observe(viewLifecycleOwner) { paymentsList ->
            paymentsList?.let { list ->
                paymentsListAdapter.paymentsList = list
            }
        }
        easyPayViewModel.errorPaymentsList.observe(viewLifecycleOwner) { error ->
            error?.let {
                Snackbar.make(
                    requireView(), error,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.actionLogout -> {
                easyPayViewModel.logout()
                Utils.saveToken(requireContext(), "")
                val navController = findNavController()
                navController.navigate(R.id.action_PaymentsFragment_to_LoginFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}