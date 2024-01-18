package com.example.ivcare.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.ivcare.R
import com.example.ivcare.databinding.FragmentLoginBinding
import com.example.ivcare.viewmodel.ViewModelUser

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val viewModelUser: ViewModelUser by viewModels()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment2)
        }
        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsernamelog.text.toString()
            val password = binding.txtPasswordlog.text.toString()
            if (username.isEmpty() || password.isEmpty()) {
                // Username or password fields are empty
                Toast.makeText(
                    requireContext(),
                    "Please enter both username and password",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModelUser.getLoginDetails(
                    requireContext(),
                    username,
                    password
                )!!.observe(viewLifecycleOwner, Observer {
                    if (it == null) {
                        // User not found, check the username or create an account
                        Toast.makeText(
                            requireContext(),
                            "Username not found",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        // User found
                        Toast.makeText(
                            requireContext(),
                            "Welcome, ${it.Username}!",
                            Toast.LENGTH_SHORT
                        ).show()
                        // Check the password
                        if (it.Password == password) {
                            // Password is correct, navigate to the MainActivity2
                            Toast.makeText(
                                requireContext(),
                                "Login successful",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Start MainActivity2 using an Intent
                            val intent = Intent(requireContext(), MainActivity2::class.java)
                            //val intent = Intent(requireContext(), MainActivity2::class.java)
                            startActivity(intent)
                            // Finish the current activity (LoginFragment)
                            activity?.finish()
                        } else {
                            // Password is incorrect
                            Toast.makeText(
                                requireContext(),
                                "Incorrect password, Try again!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

