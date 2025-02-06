package apc.appcradle.radioplayer.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import apc.appcradle.radioplayer.constants.BG_COLOR_TOKEN
import apc.appcradle.radioplayer.constants.SELECTOR_COLOR_TOKEN
import apc.appcradle.radioplayer.constants.TEXT_COLOR_TOKEN
import apc.appcradle.radioplayer.R
import apc.appcradle.radioplayer.databinding.ActivitySettingsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import yuku.ambilwarna.AmbilWarnaDialog

class SettingsActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySettingsBinding.inflate(layoutInflater) }
    private val vm by viewModel<SettingsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setColors()

        binding.selectorColorIcon.setOnClickListener { view -> showColorPicker(view) }
        binding.bgColorIcon.setOnClickListener { view -> showColorPicker(view) }
        binding.textColorIcon.setOnClickListener { view -> showColorPicker(view) }

        binding.backArrow.setOnClickListener { finish() }
        binding.clearButton.setOnClickListener {
            vm.saveColor(TEXT_COLOR_TOKEN, 0)
            vm.saveColor(BG_COLOR_TOKEN, 0)
            vm.saveColor(SELECTOR_COLOR_TOKEN, 0)
            setColors()
        }
    }

    private fun setColors() {
        binding.selectorColorIcon.background = createGradient(selectorColor())
        binding.bgColorIcon.background = createGradient(bgColor())
        binding.textColorIcon.background = createGradient(textColor())
        binding.textColorText.setTextColor(textColor())
        binding.bgColorText.setTextColor(textColor())
        binding.selectorColorText.setTextColor(textColor())
        binding.main.setBackgroundColor(bgColor())
        binding.backArrow.background = createGradient(textColor())
    }

    private fun showColorPicker(view: View) {
        val colorPicker = AmbilWarnaDialog(this, Color.WHITE,
            object : AmbilWarnaDialog.OnAmbilWarnaListener {
                override fun onCancel(dialog: AmbilWarnaDialog) {}
                override fun onOk(dialog: AmbilWarnaDialog, color: Int) {
                    setNewColor(view, color)
                }
            })
        colorPicker.show()
    }

    private fun setNewColor(view: View, color: Int) {
        when (view) {
            binding.selectorColorIcon -> {
                view.background = createGradient(color)
                vm.saveColor(SELECTOR_COLOR_TOKEN, color)
            }

            binding.bgColorIcon -> {
                view.background = createGradient(color)
                binding.main.setBackgroundColor(color)
                vm.saveColor(BG_COLOR_TOKEN, color)
            }

            binding.textColorIcon -> {
                view.background = createGradient(color)
                binding.backArrow.background = createGradient(color)
                binding.textColorText.setTextColor(color)
                binding.selectorColorText.setTextColor(color)
                binding.bgColorText.setTextColor(color)
                vm.saveColor(TEXT_COLOR_TOKEN, color)
            }
        }
    }

    private fun bgColor(): Int {
        val color = vm.getSavedColor(BG_COLOR_TOKEN)
        return if (color == 0) Color.parseColor("#000000E4") else color
    }

    private fun textColor(): Int {
        val color = vm.getSavedColor((TEXT_COLOR_TOKEN))
        return if (color == 0) Color.parseColor("#C8FFFFFF") else color
    }

    private fun selectorColor(): Int {
        val color = vm.getSavedColor((SELECTOR_COLOR_TOKEN))
        return if (color == 0) Color.parseColor("#7E448CCB") else color
    }

    private fun createGradient(startColor: Int): GradientDrawable {
        if (startColor == 0) {
            return GradientDrawable().apply {
                gradientType = GradientDrawable.LINEAR_GRADIENT
                orientation = GradientDrawable.Orientation.LEFT_RIGHT
                colors = intArrayOf(
                    Color.TRANSPARENT,
                    Color.TRANSPARENT
                )
                cornerRadius = 30f
                setStroke(1, Color.WHITE)
            }
        } else {
            return GradientDrawable().apply {
                gradientType = GradientDrawable.LINEAR_GRADIENT
                orientation = GradientDrawable.Orientation.LEFT_RIGHT
                colors = intArrayOf(
                    startColor,
                    Color.TRANSPARENT
                )
                cornerRadius = 30f
                setStroke(1, Color.WHITE)
            }
        }
    }
}