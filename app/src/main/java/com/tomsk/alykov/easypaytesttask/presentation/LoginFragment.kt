package com.tomsk.alykov.easypaytesttask.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.tomsk.alykov.easypaytesttask.R
import com.tomsk.alykov.easypaytesttask.data.network.EasyPayApi
import com.tomsk.alykov.easypaytesttask.data.network.models.AuthRequestModel
import com.tomsk.alykov.easypaytesttask.databinding.FragmentLoginBinding
import com.tomsk.alykov.easypaytesttask.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var easyPayViewModel: EasyPayViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        easyPayViewModel = (activity as SingleActivity).easyPayViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        setUi()
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    private fun setUi() {
        binding.loginButton.setOnClickListener {
            if (binding.loginEditText.text.isNotEmpty() && binding.passwordEditText.text.isNotEmpty()) {
                binding.progressBar.visibility = View.VISIBLE
                auth(
                    binding.loginEditText.text.toString(),
                    binding.passwordEditText.text.toString()
                )
            } else {
                Snackbar.make(
                    requireView(),
                    requireContext().getString(R.string.empty_fields),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun observeViewModel() {
        easyPayViewModel.token.observe(viewLifecycleOwner) { token->
            token?.let {
                Snackbar.make(
                    requireView(),
                    requireContext().getString(R.string.authorization_successful),
                    Snackbar.LENGTH_LONG
                ).show()

                Utils.saveToken(requireContext(), token)
                findNavController().navigate(R.id.action_LoginFragment_to_PaymentsFragment)
            }
        }

        easyPayViewModel.isLoad.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it)
                View.VISIBLE
            else
                View.GONE
        }

        easyPayViewModel.errorResponse.observe(viewLifecycleOwner) {
            it.let { error ->
                Snackbar.make(requireView(), error.toString(), Snackbar.LENGTH_LONG).show()
            }
        }

        easyPayViewModel.isLogin.observe(viewLifecycleOwner) {
            if (!it) {
                binding.errorLoginTextView.visibility = View.VISIBLE
                binding.errorLoginTextView.text =
                    requireContext().getString(R.string.authorization_error)
            } else {
                binding.errorLoginTextView.visibility = View.INVISIBLE
            }
        }

        easyPayViewModel.paymentsList.observe(viewLifecycleOwner) {
            it?.let {
                Log.d("AADebug", "observeViewModel listOfPayments: $it")
            }
        }

    }

    private fun auth(login: String, password: String) {
        easyPayViewModel.login(login, password)
    }

}