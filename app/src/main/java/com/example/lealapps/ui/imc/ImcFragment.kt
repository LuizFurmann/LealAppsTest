package com.example.lealapps.ui.imc

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.lealapps.databinding.FragmentImcBinding
import com.example.lealapps.ui.infoDialog.InfoDialog

class ImcFragment : Fragment() {

    private var _binding: FragmentImcBinding? = null
    private val binding get() = _binding!!

    var numberPeso = 82

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImcBinding.inflate(inflater, container, false)
        val root: View = binding.root
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        heightSlider()
        peso()
        calcule()
        showInfo()
    }

    private fun heightSlider() {
        binding.tvHeightValue.text = "1.96"

        binding.heightSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                val meters: Double = (progress.toDouble() % 10000 / 100).toDouble()
                binding.tvHeightValue.text = meters.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun peso() {
        binding.tvPesoValue.text = "$numberPeso"

        binding.imgPlus.setOnClickListener {
            numberPeso++
            binding.tvPesoValue.text = "$numberPeso"
        }

        binding.imgLess.setOnClickListener {
            if(numberPeso > 0){
                numberPeso--
                binding.tvPesoValue.text = "$numberPeso"
            }
        }
    }

    private fun calcule(){
        binding.btnCalcule.setOnClickListener {
            var height = binding.tvHeightValue.text.toString().toDouble()
            var peso = binding.tvPesoValue.text.toString().toDouble()
            var imc = peso/(height*2)

            binding.cardInfo.visibility = View.VISIBLE

            //abaixo do peso
            val minAbaixo = 17.0
            val maxAbaixo = 18.4

            //peso normal
            val minNormal = 18.5
            val maxNormal = 24.9

            //acima do peso
            val minAcima = 25.0
            val maxAcima = 29.9

            //obesidade grau 1
            val minObGrau1 = 30.0
            val maxObGrau1 = 34.9

            //obesidade grau 2
            val minObGrau2 = 35.0
            val maxObGrau2 = 40.0

//            val df = DecimalFormat("0.10")
//            df.roundingMode = RoundingMode.UP
//            val result = df.format(imc)
            
            binding.tvResult.text = "%.1f".format(imc)

            if(imc < 16.9){
                binding.tvResultLabel.text = "Muito abaixo do peso"
                binding.tvResultLabel.setTextColor(Color.parseColor("#FF0000"));
            }

            if(imc in minAbaixo..maxAbaixo){
                binding.tvResultLabel.text = "Abaixo do peso"
                binding.tvResultLabel.setTextColor(Color.parseColor("#FF0000"));
            }

            if(imc in minNormal..maxNormal){
                binding.tvResultLabel.text = "Peso normal"
                binding.tvResultLabel.setTextColor(Color.parseColor("#00BF03"));
            }

            if(imc in minAcima..maxAcima){
                binding.tvResultLabel.text = "Acima do peso"
                binding.tvResultLabel.setTextColor(Color.parseColor("#FF0000"));
            }

            if(imc in minObGrau1..maxObGrau1){
                binding.tvResultLabel.text = "\"Obesidade grau 1"
                binding.tvResultLabel.setTextColor(Color.parseColor("#FF0000"));
            }

            if(imc in minObGrau2..maxObGrau2){
                binding.tvResultLabel.text = "\"Obesidade grau 2"
                binding.tvResultLabel.setTextColor(Color.parseColor("#FF0000"));
            }

            if(imc > 40){
                binding.tvResultLabel.text = "Obesidade grau 3"
                binding.tvResultLabel.setTextColor(Color.parseColor("#FF0000"));
            }
        }
    }

    fun showInfo(){
        binding.imgInfoImc.setOnClickListener {
            val dialog = InfoDialog(requireActivity())
            dialog.show()
        }
    }
}