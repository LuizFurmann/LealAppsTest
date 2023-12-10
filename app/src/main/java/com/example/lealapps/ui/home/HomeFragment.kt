package com.example.lealapps.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.lealapps.ui.gym.TrainingAdapter
import com.example.lealapps.databinding.FragmentHomeBinding
import com.example.lealapps.model.Training
import com.example.lealapps.ui.gym.TrainingViewModel
import java.util.*

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val appContext = context
    private lateinit var trainingViewModel: TrainingViewModel
    private val adapter = TrainingAdapter()
    private var selectedFilter = ""
    private var calendar = Calendar.getInstance()
    private var day: Int = calendar.get(Calendar.DAY_OF_WEEK)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDay()
        setupViewModel()
        setupRecyclerView()
    }

    private fun getDay(){
        when(day){
            Calendar.MONDAY -> {selectedFilter = "Seg"}
            Calendar.TUESDAY -> {selectedFilter = "Ter"}
            Calendar.WEDNESDAY -> {selectedFilter = "Qua"}
            Calendar.THURSDAY -> {selectedFilter = "Qui"}
            Calendar.FRIDAY -> {selectedFilter = "Sex"}
            Calendar.SATURDAY -> { selectedFilter = "Sab"}
            Calendar.SUNDAY -> { selectedFilter = "Dom"}
        }
    }
    private fun setupViewModel(){
        trainingViewModel = ViewModelProvider(this)[TrainingViewModel::class.java]
        trainingViewModel.readAllData(selectedFilter).observe(viewLifecycleOwner) { exercise ->
            adapter.setData(exercise)
            updateList(exercise)
        }
    }
    private fun setupRecyclerView(){
        binding.rvHome.adapter = adapter
        binding.rvHome.layoutManager = LinearLayoutManager(appContext)
    }
    private fun updateList(trainings: List<Training>){
        if (trainings.isEmpty()) {
            binding.rvHome.visibility = View.GONE
            binding.tilEmptyList.visibility = View.VISIBLE
        } else {
            binding.rvHome.visibility = View.VISIBLE
            binding.tilEmptyList.visibility = View.GONE
            adapter.setData(trainings)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}