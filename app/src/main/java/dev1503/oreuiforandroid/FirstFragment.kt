package dev1503.oreuiforandroid

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import dev1503.oreui.Pixels2D
import dev1503.oreui.StyleSheet
import dev1503.oreui.widgets.OreButton
import dev1503.oreui.widgets.OreTabs
import dev1503.oreuiforandroid.databinding.FragmentFirstBinding
import androidx.core.net.toUri

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    @SuppressLint("UseKtx")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnGreen.apply {
            styleSheet = StyleSheet.STYLE_GREEN
            setOnClickListener {
                Log.v("FirstFragment", "btnGreen clicked")
            }
        }
        binding.btnPurple.apply {
            styleSheet = StyleSheet.STYLE_PURPLE
            setOnClickListener {
                Log.v("FirstFragment", "btnPurple clicked")
            }
//            addOnHoverListener { view, event ->
//                Log.v("FirstFragment", "btnPurple hovered")
//            }
//            addOnUnhoverListener(object : OnUnhoverListener {
//                override fun onUnhover(view: View, event: MotionEvent) {
//                    Log.v("FirstFragment", "btnPurple unhovered")
//                }
//            })
        }
        binding.btnCreateWorlds.styleSheet = StyleSheet.STYLE_GREEN
        binding.btnRed.styleSheet = StyleSheet.STYLE_RED
        binding.btnDarkGray.styleSheet = StyleSheet.STYLE_DARK_GRAY

        binding.alertBlue.styleSheet = StyleSheet.STYLE_ALERT_BLUE
        binding.alertCustom.apply {
            val v = LinearLayout(context)
            val tv = TextView(context)
            tv.text = "这是一条自定义警告"
            val iv = ImageView(context)
            iv.setImageResource(R.drawable.ic_launcher_foreground)
            v.addView(iv)
            v.addView(tv)
            this.view = v
            val style = this.styleSheet.clone()
            style.backgroundColor = Color.GRAY
            styleSheet = style
        }

        binding.switch1.setOnCheckedChangeListener { button, bool ->
            Log.v("FirstFragment", "switch1 checked: $bool")
        }

        binding.switch3.isChecked = true
        binding.switch3.apply {
            val style = this.thumbStyleSheet.clone()
            style.backgroundColor = Color.CYAN
            thumbStyleSheet = style
        }
        binding.switch4.setThumbIcon(Pixels2D.PIXELS_SWITCH_RIGHT)

        binding.slider1.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                Log.v("FirstFragment", "slider1 progress: $progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                Log.v("FirstFragment", "slider1 onStartTrackingTouch")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                Log.v("FirstFragment", "slider1 onStopTrackingTouch")
            }
        })
        binding.slider2.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                Log.v("FirstFragment", "slider2 progress: $progress")
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                Log.v("FirstFragment", "slider2 onStartTrackingTouch")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                Log.v("FirstFragment", "slider2 onStopTrackingTouch")
            }
        })
        binding.slider2.apply {
            val style = this.thumbStyleSheet.clone()
            style.backgroundColor = Color.YELLOW
            thumbStyleSheet = style
            trackLeftStyleSheet = StyleSheet.STYLE_PURPLE
            trackRightStyleSheet = StyleSheet.STYLE_RED
        }

        binding.tabs1.apply {
            addButton(OreButton(context).apply {
                styleSheet = StyleSheet.STYLE_DARK_GRAY
                text = "选项卡1"
            })
            addButton(OreButton(context).apply {
                styleSheet = StyleSheet.STYLE_WHITE
                text = "选项卡2"
            })
            addButton(OreButton(context).apply {
                styleSheet = StyleSheet.STYLE_GREEN
                text = "选项卡2"
            })
            addButton(OreButton(context).apply {
                styleSheet = StyleSheet.STYLE_PURPLE
                text = "选项卡3"
            })
            addButton(OreButton(context).apply {
                styleSheet = StyleSheet.STYLE_RED
                text = "选项卡3"
            })

            addOnTabChangeListener(object : OreTabs.OnTabChangeListener {
                override fun onTabChanged(index: Int, button: OreButton) {
                    Log.v("FirstFragment", "tab $index ($button) clicked")
                }
            })
        }
        binding.tabs2.apply {
            addButton(OreButton(context).apply {
                styleSheet = StyleSheet.STYLE_WHITE
                text = "生存"
            })
            addButton(OreButton(context).apply {
                styleSheet = StyleSheet.STYLE_WHITE
                text = "创造"
            })
        }

        binding.btnJavaDemo.setOnClickListener {
            startActivity(Intent(context, JavaDemoActivity::class.java))
        }
//        startActivity(Intent(context, JavaDemoActivity::class.java))
        binding.btnGithub.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW,
                "https://github.com/1503Dev/ore-ui-for-android".toUri()))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}