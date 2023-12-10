package com.example.lealappstest.view.training

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lealappstest.R
import com.example.lealappstest.databinding.ActivityMainBinding
import com.example.lealappstest.model.Training

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    lateinit var trainingViewModel: TrainingViewModel
    private val trainingAdapter = TrainingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupRecyclerView()
        setupViewModel()
        newExercise()
        checkPermissionsAndOpenFilePicker()
    }

    private fun setupRecyclerView(){
        val layoutManager = GridLayoutManager(this, 2)
        binding.rvTraining.layoutManager = layoutManager;
        binding.rvTraining.adapter = trainingAdapter
        binding.rvTraining.setHasFixedSize(true)
    }

    private fun setupViewModel(){
        trainingViewModel = ViewModelProvider(this)[TrainingViewModel::class.java]
        trainingViewModel.readAllData().observe(this) {
                trainings -> updateList(trainings)
        }
    }

    private fun updateList(trainings: List<Training>){
        if (trainings.isEmpty()) {
            binding.rvTraining.visibility = View.GONE
//            binding.tvNoListOrder.visibility = View.VISIBLE
//            binding.imgNoListOrder.visibility = View.VISIBLE
        } else {
            binding.rvTraining.visibility = View.VISIBLE
//            binding.tvNoListOrder.visibility = View.GONE
//            binding.imgNoListOrder.visibility = View.GONE
            trainingAdapter.updateList(trainings)
        }
    }

    private fun newExercise() {
        binding.fabNewTraining.setOnClickListener {

            Intent(this@MainActivity, TrainingDetailsActivity::class.java).also{
                startActivity(it)
            }
        }
    }

    private fun checkPermissionsAndOpenFilePicker() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent()
                intent.action = Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
                val uri: Uri = Uri.fromParts("package", this.packageName, null)
                intent.data = uri
                startActivity(intent)


            }
        }else{
            if (!permissionGranted()) {
                requestPermission()

            }
        }

    }

    private fun permissionGranted(): Boolean {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
                == PackageManager.PERMISSION_GRANTED)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ), 1)
    }
}