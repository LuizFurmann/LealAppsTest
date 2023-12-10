package com.example.lealappstest.view.training

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lealappstest.R
import com.example.lealappstest.databinding.ActivityMainBinding
import com.example.lealappstest.model.Training
import com.example.lealappstest.view.authentication.LoginActivity
import com.example.lealappstest.view.exercise.ExerciseViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding : ActivityMainBinding
    lateinit var trainingViewModel: TrainingViewModel
    lateinit var exerciseViewModel: ExerciseViewModel
    private val trainingAdapter = TrainingAdapter()
    lateinit var user : FirebaseUser
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setTitle("")
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!

        val headerView = binding.navView.getHeaderView(0)
        val navUsername = headerView.findViewById<View>(R.id.userEmail) as TextView
        navUsername.text = user.email.toString()

        setupDrawer()
        setupRecyclerView()
        setupViewModel()
        newExercise()
//        checkPermissionsAndOpenFilePicker()
    }

    private fun setupRecyclerView(){
        val layoutManager = GridLayoutManager(this, 2)
        binding.rvTraining.layoutManager = layoutManager;
        binding.rvTraining.adapter = trainingAdapter
        binding.rvTraining.setHasFixedSize(true)
    }

    private fun setupViewModel(){
        trainingViewModel = ViewModelProvider(this)[TrainingViewModel::class.java]
        exerciseViewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]
        trainingViewModel.readAllData().observe(this) {
                trainings -> updateList(trainings)
        }
    }

    private fun updateList(trainings: List<Training>){
        if (trainings.isEmpty()) {
            binding.rvTraining.visibility = View.GONE
            binding.myTrainingTittle.visibility = View.GONE
            binding.emptyTrainingList.visibility = View.VISIBLE
        } else {
            binding.rvTraining.visibility = View.VISIBLE
            binding.myTrainingTittle.visibility = View.VISIBLE
            binding.emptyTrainingList.visibility = View.GONE
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

    private fun setupDrawer(){
        val draweLayout : DrawerLayout = binding.drawerLayout
        val navView : NavigationView = binding.navView

        toggle = ActionBarDrawerToggle(this,draweLayout, R.string.open, R.string.close)
        draweLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_delete_training_db -> dialogDeleteTrainingDb()
                R.id.nav_delete_exercise_db -> dialogDeleteExerciseDb()
                R.id.nav_logout -> logout()
            }
            true
        }
    }

    fun dialogDeleteTrainingDb(){
        val builder = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_rounded)
        builder.setMessage("Deseja deletar todos os treinos?")
        builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->
            trainingViewModel.deleteExerciseDb()
            Snackbar.make(binding.root, "Treinos deletados", Snackbar.LENGTH_LONG).show()
        }
        builder.setNegativeButton(getString(R.string.no)) { dialog, which ->
        }
        builder.show()
    }

    fun dialogDeleteExerciseDb(){
        val builder = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_rounded)
        builder.setMessage("Deseja deletar todos os exercícios?")
        builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->
            exerciseViewModel.deleteExerciseDb()
            Snackbar.make(binding.root, "Exercícios deletados", Snackbar.LENGTH_LONG)
                .show()
        }
        builder.setNegativeButton(getString(R.string.no)) { dialog, which ->
        }
        builder.show()
    }

    fun logout(){
        FirebaseAuth.getInstance().signOut()
        Intent(this@MainActivity, LoginActivity::class.java).also{
            startActivity(it)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }
}