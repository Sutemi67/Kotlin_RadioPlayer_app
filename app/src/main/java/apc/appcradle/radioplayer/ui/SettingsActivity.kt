package apc.appcradle.radioplayer.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        vm.setViewBackground(binding.selectorColorIcon, SELECTOR_COLOR_TOKEN)
        vm.setViewBackground(binding.bgColorIcon, BG_COLOR_TOKEN)
        vm.setViewBackground(binding.main, BG_COLOR_TOKEN)

        binding.selectorColorIcon.setOnClickListener { view -> showColorPicker(view) }
        binding.bgColorIcon.setOnClickListener { view -> showColorPicker(view) }

        binding.backArrow.setOnClickListener { finish() }

    }

    private fun showColorPicker(view: View) {
        val colorPicker = AmbilWarnaDialog(this, vm.getSavedColor(view),
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
//                R.color.playing_shape = color
//                view.background = AppCompatResources.getDrawable(this, R.drawable.playing_shape)
                view.setBackgroundColor(color)
                vm.saveColor(SELECTOR_COLOR_TOKEN, color)
            }

            binding.bgColorIcon -> {
                view.setBackgroundColor(color)
                binding.main.setBackgroundColor(color)
                vm.saveColor(BG_COLOR_TOKEN, color)
            }
        }
    }

}