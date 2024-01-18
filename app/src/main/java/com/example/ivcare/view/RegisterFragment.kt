package com.example.ivcare.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ivcare.R
import com.example.ivcare.databinding.FragmentRegisterBinding
import com.example.ivcare.viewmodel.ViewModelUser

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val viewModelUser: ViewModelUser by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegisterreg.setOnClickListener {
            val username = binding.txtUsernamereg.text.toString()
            val password = binding.txtPasswordreg.text.toString()
            val confirmPassword = binding.txtPasswordconfirmreg.text.toString()

            // Check if any of the fields are empty
            if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                // Display a Toast if any field is empty
                Toast.makeText(
                    context,
                    "Please fill in all fields",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                // Insert data if the fields are not empty
                viewModelUser.insertData(requireContext(), username, password)
                Toast.makeText(context, "Account created successfully", Toast.LENGTH_SHORT).show()

                // Navigate to the login fragment
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }

        // Add click listener to navigate to login fragment if the user already has an account
        binding.btnLoginreg.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

