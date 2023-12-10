package com.example.lealapps

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.example.lealapps.ui.exercise.ExerciseViewModel
import com.example.lealapps.ui.gym.ExerciseFragment
import com.example.lealapps.ui.gym.TrainingViewModel
import com.example.lealapps.ui.home.HomeFragment
import com.example.lealapps.ui.imc.ImcFragment
import com.example.lealapps.databinding.ActivityMainBinding
import com.example.lealapps.ui.exercise.ExerciseActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout


class MainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    lateinit var trainingViewModel: TrainingViewModel
    lateinit var exerciseViewModel: ExerciseViewModel

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        supportActionBar!!.setDisplayShowTitleEnabled(false)

        setupDrawer()
        openFragment(HomeFragment())

        trainingViewModel = ViewModelProvider(this)[TrainingViewModel::class.java]
        exerciseViewModel = ViewModelProvider(this)[ExerciseViewModel::class.java]

        binding.appBarMain.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.hoje -> {
                    openFragment(HomeFragment())
                    true
                }
                R.id.treino -> {
                    openFragment(ExerciseFragment())
                    true
                }
                R.id.imc -> {
                    openFragment(ImcFragment())
                    true
                }

                else -> false
            }
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(binding.appBarMain.container.id, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun setupDrawer(){
        val draweLayout : DrawerLayout = binding.drawerLayout
        val navView : NavigationView = binding.navView

        toggle = ActionBarDrawerToggle(this,draweLayout, R.string.open, R.string.close)
        draweLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {

            when(it.itemId){
                R.id.nav_member -> memberAcitity()
                R.id.nav_delete_training_db -> dialogDeleteTrainingDb()
                R.id.nav_delete_exercise_db -> dialogDeleteExerciseDb()
            }
            true
        }
    }

    private fun memberAcitity(){
        Intent(this@MainActivity, ExerciseActivity::class.java).also{
            startActivity(it)
        }
    }

    fun dialogDeleteTrainingDb(){
        val builder = MaterialAlertDialogBuilder(this, R.style.MaterialAlertDialog_rounded)
        builder.setMessage("Deseja deletar todos os treinos?")
        builder.setPositiveButton(getString(R.string.yes)) { dialog, which ->
            trainingViewModel.deleteTrainingDb()

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
//                .setAction("Teste") {
//                        alarmData.add(position, recoverItem);
//                        notifyItemInserted(position);
//                }
                .show()
        }
        builder.setNegativeButton(getString(R.string.no)) { dialog, which ->
        }
        builder.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item))
            return true
        return super.onOptionsItemSelected(item)
    }
}