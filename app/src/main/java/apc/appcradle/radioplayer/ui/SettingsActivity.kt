package apc.appcradle.radioplayer.ui

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import apc.appcradle.radioplayer.R
import apc.appcradle.radioplayer.constants.BG_COLOR_TOKEN
import apc.appcradle.radioplayer.constants.SELECTOR_COLOR_TOKEN
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

        binding.selectorColorIcon.background =
            createGradient(vm.getSavedColor(SELECTOR_COLOR_TOKEN))
        binding.bgColorIcon.background = createGradient(vm.getSavedColor(BG_COLOR_TOKEN))
        binding.main.setBackgroundColor(vm.getSavedColor(BG_COLOR_TOKEN))

        binding.selectorColorIcon.setOnClickListener { view -> showColorPicker(view) }
        binding.bgColorIcon.setOnClickListener { view -> showColorPicker(view) }
        binding.backArrow.setOnClickListener { finish() }

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
        }
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