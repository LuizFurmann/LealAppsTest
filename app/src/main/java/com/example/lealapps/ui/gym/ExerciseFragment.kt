package com.example.lealapps.ui.gym

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lealapps.R
import com.example.lealapps.databinding.FragmentExerciseBinding
import com.example.lealapps.model.Training


class ExerciseFragment : Fragment() {

    lateinit var trainingViewModel: TrainingViewModel
    private val adapter = TrainingAdapter()
    var selectedFilter = "Seg"

    private var _binding: FragmentExerciseBinding? = null
    private val binding get() = _binding!!
    var timeWhenStopped: Long = 0
    private lateinit var chronometer: Chronometer
    private var isRunning = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)
        val root: View = binding.root
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()
        setupRecyclerView()
        onBtnFilterClick()
        newExercise(selectedFilter)
    }

    private fun setupViewModel(){
        trainingViewModel = ViewModelProvider(requireActivity())[TrainingViewModel::class.java]
        trainingViewModel.readAllData(selectedFilter).observe(requireActivity()) { exercise ->
            adapter.setData(exercise) }
        trainingViewModel.readAllData(selectedFilter).observe(requireActivity()) { exercises ->
            updateList(exercises) }
    }

    private fun setupRecyclerView(){
        binding.rvExercise.adapter = adapter
        binding.rvExercise.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun onBtnFilterClick() {
        binding.includeBtns.btnSeg.setOnClickListener{
            val selectedFilter = "Seg"
            updateFilterIcons(selectedFilter)

            trainingViewModel.readAllData(selectedFilter).observe(requireActivity()) { exercises ->
                updateList(exercises)
            }

            newExercise(selectedFilter)
        }
        binding.includeBtns.btnTer.setOnClickListener{
            val selectedFilter = "Ter"
            updateFilterIcons(selectedFilter)

            trainingViewModel.readAllData(selectedFilter).observe(requireActivity()) { exercises ->
                updateList(exercises)
            }
            newExercise(selectedFilter)
        }
        binding.includeBtns.btnQua.setOnClickListener{
            val selectedFilter = "Qua"
            updateFilterIcons(selectedFilter)

            trainingViewModel.readAllData(selectedFilter).observe(requireActivity()) { exercises ->
                updateList(exercises)
            }
            newExercise(selectedFilter)
        }
        binding.includeBtns.btnQui.setOnClickListener{
            val selectedFilter = "Qui"
            updateFilterIcons(selectedFilter)

            trainingViewModel.readAllData(selectedFilter).observe(requireActivity()) { exercises ->
                updateList(exercises)
            }
            newExercise(selectedFilter)
        }
        binding.includeBtns.btnSex.setOnClickListener{
            val selectedFilter = "Sex"
            updateFilterIcons(selectedFilter)

            trainingViewModel.readAllData(selectedFilter).observe(requireActivity()) { exercises ->
                updateList(exercises)
            }
            //comentado
            newExercise(selectedFilter)
        }
        binding.includeBtns.btnSab.setOnClickListener{
            val selectedFilter = "Sab"
            updateFilterIcons(selectedFilter)

            trainingViewModel.readAllData(selectedFilter).observe(requireActivity()) { exercises ->
                updateList(exercises)
            }
            newExercise(selectedFilter)
        }
        binding.includeBtns.btnDom.setOnClickListener{
            val selectedFilter = "Dom"
            updateFilterIcons(selectedFilter)

            trainingViewModel.readAllData(selectedFilter).observe(requireActivity()) { exercises ->
                updateList(exercises)
            }
            newExercise(selectedFilter)
        }
    }

    private fun newExercise(dia:String){
        binding.fabNewTrain.setOnClickListener{
            val intent = Intent(requireActivity(), TrainingDetailsActivity::class.java)
            intent.putExtra("day", dia)
            startActivity(intent)
        }
    }

//    private fun createAutExercise(dia:String){
//        binding.btnAutomaticGym.setOnClickListener{
//            var exercise = gymViewModel.createAutoExerciseTriceps(dia)
//            exerciseViewModel.addExercise(exercise)
//        }
//    }

    private fun updateFilterIcons(newFilter: String) {
        updateBtnFilter(
            "Seg",
            newFilter,
            binding.includeBtns.btnSeg
        )
        updateBtnFilter(
            "Ter",
            newFilter,
            binding.includeBtns.btnTer
        )
        updateBtnFilter(
            "Qua",
            newFilter,
            binding.includeBtns.btnQua
        )
        updateBtnFilter(
            "Qui",
            newFilter,
            binding.includeBtns.btnQui
        )
        updateBtnFilter(
            "Sex",
            newFilter,
            binding.includeBtns.btnSex
        )
        updateBtnFilter(
            "Sab",
            newFilter,
            binding.includeBtns.btnSab
        )
        updateBtnFilter(
            "Dom",
            newFilter,
            binding.includeBtns.btnDom
        )
    }

    private fun updateList(trainings: List<Training>){
        if (trainings.isEmpty()) {
            binding.rvExercise.visibility = View.GONE
            binding.tilEmptyList.visibility = View.VISIBLE
        } else {
            binding.rvExercise.visibility = View.VISIBLE
            binding.tilEmptyList.visibility = View.GONE
            adapter.setData(trainings)
        }
    }

    private fun updateBtnFilter(filter: String, filterSelect: String, button: TextView) {
        button.setTextColor(
            if (filter == filterSelect)
                getColor(requireActivity(), R.color.white)
            else
                getColor(requireActivity(), R.color.green_yellow))

        button.background =
            if (filter == filterSelect) {
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.bg_btn_colorprimary_rounded
                )
            } else {
                ContextCompat.getDrawable(
                    requireActivity(),
                    R.drawable.bg_btn_transparent_rounded
                )
            }
    }
}