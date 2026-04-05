package dev1503.oreui.dialog

import android.content.Context
import android.content.DialogInterface
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import dev1503.oreui.StyleSheet
import dev1503.oreui.widgets.OreButton
import dev1503.oreui.widgets.OrePanel

class OreDialogBuilder(context: Context) : AlertDialog.Builder(context) {

    private var userView: View? = null
    val rootLayout: OrePanel
    private var headerPanel: OrePanel? = null
    private var headerPanelTextView: TextView? = null
    private var containerScrollView: ScrollView? = null
    private var messageTextView: TextView? = null
    private var footerPanel: OrePanel? = null
    var positiveButton: OreButton? = null
    var negativeButton: OreButton? = null
    var neutralButton: OreButton? = null
    private var dialog: AlertDialog? = null

    companion object {
        const val ANIMATION_DISABLED = true
    }

    init {
        rootLayout = OrePanel(context).apply {
            styleSheet = StyleSheet.STYLE_DIALOG
            orientation = LinearLayout.VERTICAL
            val p = styleSheet.pixelSize.toInt()
            setPadding(p, p, p, p)
        }
        super.setView(rootLayout)
    }

    override fun setView(view: View?): OreDialogBuilder {
        this.userView = view
        if (containerScrollView == null) {
            containerScrollView = ScrollView(context).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0,
                    1f
                )
                rootLayout.addView(this)
            }
        }
        containerScrollView?.removeAllViews()
        containerScrollView?.addView(view)
        return this
    }

    override fun setTitle(title: CharSequence?): OreDialogBuilder {
        if (headerPanel == null) {
            val style = rootLayout.styleSheet
            headerPanel = OrePanel(context).apply {
                outlineEnabled = false
                borderEnabled = true
                layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                gravity = Gravity.CENTER
                rootLayout.addView(this, 0)
            }

            headerPanelTextView = TextView(context).apply {
                style.textSize?.let { setTextSize(TypedValue.COMPLEX_UNIT_PX, style.calcPixelSize(it)) }
                style.textColor?.let { setTextColor(it) }
                headerPanel?.addView(this)
            }
        }
        headerPanelTextView?.text = title
        return this
    }

    override fun setMessage(message: CharSequence?): OreDialogBuilder {
        if (containerScrollView == null) {
            containerScrollView = ScrollView(context).apply {
                layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1f)
                rootLayout.addView(this)
            }
            val style = rootLayout.styleSheet
            val container = LinearLayout(context).apply {
                layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                gravity = Gravity.CENTER
            }
            containerScrollView?.addView(container)
            messageTextView = TextView(context).apply {
                style.textSize?.let { setTextSize(TypedValue.COMPLEX_UNIT_PX, style.calcPixelSize(it * 0.832f)) }
                style.textColor?.let { setTextColor(it) }
                setPadding(style.calcPixelSize(7.5f).toInt(), style.calcPixelSize(7.5f).toInt(), style.calcPixelSize(7.5f).toInt(), style.calcPixelSize(7.5f).toInt())
//                layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//                gravity = Gravity.CENTER
                container.addView(this)
            }
        }
        messageTextView?.text = message
        return this
    }

    private fun initFooter() {
        if (footerPanel == null) {
            val style = rootLayout.styleSheet
            val spacing = style.calcPixelSize(2f).toInt()
            footerPanel = OrePanel(context).apply {
                outlineEnabled = false
                borderEnabled = true
                sideBorderEnabled = false
                layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                dividerDrawable = GradientDrawable().apply {
                    setSize(0, spacing)
                }
                showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
                rootLayout.addView(this)
            }
        }
    }

    private fun refreshFooterButtons() {
        footerPanel?.let { panel ->
            panel.removeAllViews()
            val lp = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            positiveButton?.let { it.layoutParams = lp; panel.addView(it) }
            neutralButton?.let { it.layoutParams = lp; panel.addView(it) }
            negativeButton?.let { it.layoutParams = lp; panel.addView(it) }
        }
    }

    override fun setPositiveButton(_text: CharSequence?, listener: DialogInterface.OnClickListener?): OreDialogBuilder {
        initFooter()
        positiveButton = OreButton(context).apply {
            text = _text
            setOnClickListener { listener?.onClick(dialog, DialogInterface.BUTTON_POSITIVE) }
        }
        refreshFooterButtons()
        return this
    }

    override fun setNegativeButton(_text: CharSequence?, listener: DialogInterface.OnClickListener?): OreDialogBuilder {
        initFooter()
        negativeButton = OreButton(context).apply {
            text = _text
            setOnClickListener { listener?.onClick(dialog, DialogInterface.BUTTON_NEGATIVE) }
        }
        refreshFooterButtons()
        return this
    }

    override fun setNeutralButton(_text: CharSequence?, listener: DialogInterface.OnClickListener?): OreDialogBuilder {
        initFooter()
        neutralButton = OreButton(context).apply {
            text = _text
            setOnClickListener { listener?.onClick(dialog, DialogInterface.BUTTON_NEUTRAL) }
        }
        refreshFooterButtons()
        return this
    }

    override fun create(): AlertDialog {
        dialog = super.create()
        dialog?.window?.let { window ->
            window.setBackgroundDrawableResource(android.R.color.transparent)
            window.decorView.setPadding(0, 0, 0, 0)
            if (ANIMATION_DISABLED) {
                window.setWindowAnimations(0)
                window.setDimAmount(0.5f)
            }
            val lp = window.attributes
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            window.attributes = lp
        }
        return dialog!!
    }

    override fun show(): AlertDialog {
        val dialog = create()
        if (ANIMATION_DISABLED) {
            dialog.window?.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
            dialog.show()
            dialog.window?.let { window ->
                window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                val lp = window.attributes
                lp.windowAnimations = 0
                window.attributes = lp
            }
        } else {
            dialog.show()
        }
        return dialog
    }
}