package com.example.weatherapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

import com.example.weatherapp.R


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view=inflater.inflate(R.layout.fragment_main, container, false)

        val textCity=view.findViewById<EditText>(R.id.city)
        val showButton=view.findViewById<Button>(R.id.showButton)

        showButton.setOnClickListener {
            val cityName = textCity.text.toString()

            // Check if city name is entered
            if (cityName.isNotEmpty()) {
                val bundle = Bundle().apply {
                    putString("cityName", cityName)
                }
                // Pass the bundle to DetailFragment
                findNavController().navigate(R.id.action_mainFragment_to_detailFragment, bundle)
            } else {
                Toast.makeText(requireContext(), "Please enter a city", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }


}