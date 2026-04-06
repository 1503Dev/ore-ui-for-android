package dev1503.oreuiforandroid

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
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
import android.widget.Toast
import dev1503.oreui.Pixels2D
import dev1503.oreui.StyleSheet
import dev1503.oreui.widgets.OreButton
import dev1503.oreui.widgets.OreTabs
import dev1503.oreuiforandroid.databinding.FragmentFirstBinding
import androidx.core.net.toUri
import dev1503.oreui.dialog.OreDialogBuilder
import dev1503.oreui.events.OnHoverListener
import dev1503.oreui.widgets.OreAlert

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

        binding.btnWhiteDisabled.isEnabled = false
        binding.btnRedDisabled.styleSheet = StyleSheet.STYLE_RED
        binding.btnRedDisabled.isEnabled = false
        binding.btnPurpleDisabled.styleSheet = StyleSheet.STYLE_PURPLE
        binding.btnPurpleDisabled.isEnabled = false
        binding.btnGreenDisabled.styleSheet = StyleSheet.STYLE_GREEN
        binding.btnGreenDisabled.isEnabled = false

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
        binding.switch5.apply {
            setThumbIcon(Pixels2D.PIXELS_SWITCH_LEFT)
            isEnabled = false
        }

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
        binding.slider3.isEnabled = false

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
        binding.tabs3.apply {
            addButton(OreButton(context).apply {
                styleSheet = StyleSheet.STYLE_WHITE
                text = "普通"
            })
            addButton(OreButton(context).apply {
                styleSheet = StyleSheet.STYLE_WHITE
                text = "终为白日"
            })
            isEnabled = false
        }


        binding.editText3.isEnabled = false

        binding.btnJavaDemo.setOnClickListener {
            startActivity(Intent(context, JavaDemoActivity::class.java))
        }
//        startActivity(Intent(context, JavaDemoActivity::class.java))
        binding.btnGithub.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW,
                "https://github.com/1503Dev/ore-ui-for-android".toUri()))
        }

        binding.panel2.borderEnabled = true
        binding.panel3.borderEnabled = true
        binding.panel3.outlineEnabled = false

        binding.btnDialog.setOnClickListener {
            context?.let { it1 ->
                OreDialogBuilder(it1)
                    .setTitle("你无法获得成就")
                    .setMessage("""若使用以下功能创建世界，这将意味着你无法在该世界中获得成就。
即使你之后关闭此选项，你也需要访问其他世界才能获得成就。

在以下情况中，将无法在这个世界获得成就：

  ·  世界是以创造模式创建""")
                    .setPositiveButton("关闭", DialogInterface.OnClickListener { dialog, _ ->
                        dialog?.dismiss()
                    })
                    .show()
            }
        }

        binding.btnDialog2.setOnClickListener {
            context?.let { it1 ->
                val builder = OreDialogBuilder(it1)
                    .setTitle("对话框2")
                    .setMessage("""若使用以下功能创建世界，这将意味着你无法在该世界中获得成就。
即使你之后关闭此选项，你也需要访问其他世界才能获得成就。

在以下情况中，将无法在这个世界获得成就：

  ·  已在创造模式创建世界
  ·  这个世界是以平坦世界的设置创建
  ·  已启用作弊
  ·  世界创建者拥有操作员特权
  ·  测试版功能已启用
  ·  世界创建者使用的是《我的世界》试用版
  ·  行为包已激活
  ·  世界由模板建造而来
  
创建世界后，您无法关闭实验性游戏内容。

实验性游戏内容随时可能破坏游戏，所以不要建造任何重要的东西，因为您可能会失去它。

如果你删除这个世界，它将会永远消失。

警告：此操作会将你的服务器地址暴露给当前正在观看你屏幕的任何人（包括通过流媒体观看者）。若你的服务器地址与IP地址相同，泄露该信息则可能使你面临网络攻击的风险。

确定要显示你的服务器地址吗？

如果删除此服务器，稍后仍然可以再次添加它。

是否要退出《我的世界》？

正在购买

稍后即可完成。

管理自己的Minecraft服务器！哪些玩家可以加入、游戏要怎么玩，都由您来定。即使您不在线，成员也可以免费游玩。轻松设置、管理，并且可在任何设备上游玩。

访问优质的精选内容

  ·  Realms Plus 包含 Marketplace Pass
  ·  内容每月更新
  ·  追加内容、世界等等
  ·  角色编辑器物品
  ·  独家优惠""")
                    .setPositiveButton("取消并返回") { dialog, _ ->
                        dialog?.dismiss()
                    }
                    .setNeutralButton("依然打开实验性游戏内容") { dialog, _ ->
                        dialog?.dismiss()
                    }
                    .setNegativeButton("删除世界") { dialog, _ ->
                        dialog?.dismiss()
                    }
                builder.positiveButton?.styleSheet = StyleSheet.STYLE_GREEN
                builder.negativeButton?.styleSheet = StyleSheet.STYLE_RED
                builder.show()
            }
        }

//        binding.card1.styleSheet = StyleSheet.STYLE_CARD_DARK
        binding.card1.setOnClickListener {
            Toast.makeText(context, "点击了卡片1", Toast.LENGTH_SHORT).show()
        }
        binding.card1.addOnHoverListener(object : OnHoverListener {
            override fun onHover(view: android.view.View, event: android.view.MotionEvent) {
                Log.d("FirstFragment", "卡片Hover: $event")
            }
        })

        binding.accordion1.apply {
            title = "Marketplace Pass"
            subtitle = "(100+)"
            contentView = LinearLayout(context).apply {
                orientation = LinearLayout.VERTICAL
                layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT)
                addView(TextView(context).apply {
                    setTextColor(Color.WHITE)
                    text = "Accordion"
                })
                addView(OreAlert(context).apply {
                    text = "12345\n67890"
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}