package dev1503.oreui

import android.graphics.Typeface

class StyleSheet {
    companion object {
        init {
            OreUI.init()
        }

        const val FLAG_DEFAULT = 0
        const val FLAG_HOVERED = 1 shl 0
        const val FLAG_PRESSED = 1 shl 1
        const val FLAG_ACTIVE = 1 shl 2
        const val FLAG_DISABLED = 1 shl 3
        const val FLAG_FOCUSED = 1 shl 4

        @JvmField
        val STYLE_DISABLED = StyleSheet().apply {
            outlineColor = 0xFF58585A.toInt()
            backgroundColor = 0xFFB1B2B5.toInt()
            shadowColor = 0xFF8C8D90.toInt()
            borderTopColor = 0xFFB1B2B5.toInt()
            borderBottomColor = 0xFFB1B2B5.toInt()
            textColor = 0xFF58585A.toInt()
        }

        @JvmField
        val STYLE_INACTIVATED = StyleSheet().apply {
            outlineColor = 0xFF1E1E1F.toInt()
            borderTopColor = 0xFFA3A4A6.toInt()
            borderBottomColor = 0xFF97989B.toInt()
            backgroundColor = 0xFF8C8D90.toInt()
            textColor = 0xFF242425.toInt()
        }

        @JvmField
        val STYLE_EDIT_TEXT = StyleSheet().apply {
            outlineColor = 0xFF1E1E1F.toInt()
            backgroundColor = 0xFF313233.toInt()
            shadowColor = 0xFF242425.toInt()
            textColor = 0xFFFFFFFF.toInt()
            caretColor = 0xFF548840.toInt()
        }

        @JvmField
        val STYLE_WHITE = StyleSheet().apply {
            outlineColor = 0xFF1E1E1F.toInt()
            borderTopColor = 0xFFECEDEE.toInt()
            borderBottomColor = 0xFFE3E3E5.toInt()
            backgroundColor = 0xFFD0D1D4.toInt()
            textColor = 0xFF000000.toInt()
            shadowColor = 0xFF58585A.toInt()
            styleHovered = StyleSheet().apply {
                borderTopColor = 0xFFEFF0F0.toInt()
                borderBottomColor = 0xFFE0E0E1.toInt()
                backgroundColor = 0xFFB1B2B5.toInt()
            }
            stylePressed = StyleSheet().apply {
                borderTopColor = 0xFFEFF0F0.toInt()
                borderBottomColor = 0xFFE0E0E1.toInt()
                backgroundColor = 0xFFB1B2B5.toInt()
            }
            styleActive = StyleSheet().apply {
                borderTopColor = 0xFF639D52.toInt()
                borderBottomColor = 0xFF4F913C.toInt()
                backgroundColor = 0xFF3C8527.toInt()
                textColor = 0xFFFFFFFF.toInt()
            }
        }

        @JvmField
        val STYLE_GREEN = StyleSheet().apply {
            outlineColor = 0xFF1E1E1F.toInt()
            borderTopColor = 0xFF639D52.toInt()
            borderBottomColor = 0xFF4F913C.toInt()
            backgroundColor = 0xFF3C8527.toInt()
            textColor = 0xFFFFFFFF.toInt()
            shadowColor = 0xFF1D4D13.toInt()
            styleHovered = StyleSheet().apply {
                borderTopColor = 0xFF7FA277.toInt()
                borderBottomColor = 0xFF699260.toInt()
                backgroundColor = 0xFF2A641C.toInt()
            }
            stylePressed = StyleSheet().apply {
                borderTopColor = 0xFF779471.toInt()
                borderBottomColor = 0xFF608259.toInt()
                backgroundColor = 0xFF1D4D13.toInt()
            }
        }

        @JvmField
        val STYLE_PURPLE = StyleSheet().apply {
            outlineColor = 0xFF1E1E1F.toInt()
            borderTopColor = 0xFFA264F2.toInt()
            borderBottomColor = 0xFF8E49EB.toInt()
            backgroundColor = 0xFF7345E5.toInt()
            textColor = 0xFFFFFFFF.toInt()
            shadowColor = 0xFF4A1CAC.toInt()
            styleHovered = StyleSheet().apply {
                borderTopColor = 0xFFAE69EE.toInt()
                borderBottomColor = 0xFF9243E2.toInt()
                backgroundColor = 0xFF5D2CC6.toInt()
            }
            stylePressed = StyleSheet().apply {
                borderTopColor = 0xFFA864E6.toInt()
                borderBottomColor = 0xFF8B3CD8.toInt()
                backgroundColor = 0xFF4A1CAC.toInt()
            }
        }

        @JvmField
        val STYLE_RED = StyleSheet().apply {
            outlineColor = 0xFF1E1E1F.toInt()
            borderTopColor = 0xFFD55E5E.toInt()
            borderBottomColor = 0xFFCF4A4A.toInt()
            backgroundColor = 0xFFCA3636.toInt()
            textColor = 0xFFFFFFFF.toInt()
            shadowColor = 0xFFAD1D1D.toInt()
            styleHovered = StyleSheet().apply {
                borderTopColor = 0xFFDF9696.toInt()
                borderBottomColor = 0xFFD98181.toInt()
                backgroundColor = 0xFFC02D2D.toInt()
            }
            stylePressed = StyleSheet().apply {
                borderTopColor = 0xFFD68E8E.toInt()
                borderBottomColor = 0xFFCE7777.toInt()
                backgroundColor = 0xFFAD1D1D.toInt()
            }
        }

        @JvmField
        val STYLE_DARK_GRAY = StyleSheet().apply {
            outlineColor = 0xFF1E1E1F.toInt()
            borderTopColor = 0xFF6D6D6E.toInt()
            borderBottomColor = 0xFF5A5B5C.toInt()
            backgroundColor = 0xFF48494A.toInt()
            textColor = 0xFFFFFFFF.toInt()
            shadowColor = 0xFF313233.toInt()
            styleHovered = StyleSheet().apply {
                borderTopColor = 0xFF79797B.toInt()
                borderBottomColor = 0xFF69696B.toInt()
                backgroundColor = 0xFF58585A.toInt()
            }
            stylePressed = StyleSheet().apply {
                borderTopColor = 0xFF5A5B5C.toInt()
                borderBottomColor = 0xFF464747.toInt()
                backgroundColor = 0xFF313233.toInt()
            }
            styleActive = stylePressed
        }

        @JvmField
        val STYLE_ALERT_YELLOW = StyleSheet().apply {
            backgroundColor = 0xFFEFE866.toInt()
            textColor = 0xFF000000.toInt()
            textSize = 6.25f
        }

        @JvmField
        val STYLE_ALERT_BLUE = StyleSheet().apply {
            backgroundColor = 0xFF2E6BE5.toInt()
            textColor = 0xFFFFFFFF.toInt()
            textSize = 6.25f
        }

        @JvmField
        val STYLE_PANEL = StyleSheet().apply {
            outlineColor = 0xFF1E1E1F.toInt()
            backgroundColor = 0xFF48494A.toInt()
            borderTopColor = 0xFF6D6D6E.toInt()
            borderBottomColor = 0xFF5A5B5C.toInt()
            textColor = 0xFFFFFFFF.toInt()

            styleHovered = StyleSheet().apply {
                backgroundColor = 0xFF58585A.toInt()
                borderTopColor = 0xFF68686A.toInt()
                borderBottomColor = 0xFF3E3E3F.toInt()
            }

            stylePressed = StyleSheet().apply {
                backgroundColor = 0xFF313233.toInt()
                borderTopColor = 0xFF454647.toInt()
                borderBottomColor = 0xFF1D1E1F.toInt()
            }
        }

        @JvmField
        val STYLE_DIALOG = STYLE_PANEL.clone().apply {
            backgroundColor = 0xFF313233.toInt()
        }

        @JvmField
        val STYLE_CARD_DARK = StyleSheet().apply {
            outlineColor = 0xFF1E1E1F.toInt()
            backgroundColor = 0xFF313233.toInt()
            borderTopColor = 0xFF454647.toInt()
            borderBottomColor = 0xFF1D1E1F.toInt()
            textColor = 0xFFFFFFFF.toInt()

            styleHovered = StyleSheet().apply {
                backgroundColor = 0xFF48494A.toInt()
                borderTopColor = 0xFF6D6D6E.toInt()
                borderBottomColor = 0xFF5A5B5C.toInt()
            }

            stylePressed = StyleSheet().apply {
                backgroundColor = 0xFF242425.toInt()
                borderTopColor = 0xFF070707.toInt()
                borderBottomColor = 0xFF39393A.toInt()
            }
        }

        var defaultTypeface: Typeface? = null
    }

    var pixelSize = 5f

    var outlineColor: Int? = null
    var borderTopColor: Int? = null
    var borderBottomColor: Int? = null
    var backgroundColor: Int? = null
    var textColor: Int? = null
    var shadowColor: Int? = null
    var caretColor: Int? = null

    var textSize: Float? = 7.5f

    var typeface: Typeface? = null
        get() {
            return defaultTypeface
        }
        set(value) {
            field = value
        }

    var styleHovered: StyleSheet? = null
    var stylePressed: StyleSheet? = null
    var styleActive: StyleSheet? = null
    var styleDisabled: StyleSheet? = null

    init {
        styleDisabled = STYLE_DISABLED
    }

    fun apply(other: StyleSheet): StyleSheet {
        val result = StyleSheet()
        result.outlineColor = other.outlineColor ?: this.outlineColor
        result.borderTopColor = other.borderTopColor ?: this.borderTopColor
        result.borderBottomColor = other.borderBottomColor ?: this.borderBottomColor
        result.backgroundColor = other.backgroundColor ?: this.backgroundColor
        result.textColor = other.textColor ?: this.textColor
        result.shadowColor = other.shadowColor ?: this.shadowColor
        result.caretColor = other.caretColor ?: this.caretColor
        result.textSize = other.textSize ?: this.textSize
        result.typeface = other.typeface ?: this.typeface
        result.pixelSize = other.pixelSize ?: this.pixelSize
        return result
    }

    fun getStyleSheet(flags: Int): StyleSheet {
        if (flags == lastFlags && cachedStyle != null) {
            return cachedStyle!!
        }

        var result = this
        if ((flags and FLAG_DISABLED) != 0) {
            styleDisabled?.let { result = result.apply(it) }
        } else {
            if ((flags and FLAG_HOVERED) != 0) styleHovered?.let { result = result.apply(it) }

            if ((flags and FLAG_ACTIVE) != 0) {
                val active = styleActive ?: stylePressed
                active?.let { result = result.apply(it) }
            }
            if ((flags and FLAG_PRESSED) != 0) stylePressed?.let { result = result.apply(it) }
        }

        lastFlags = flags
        cachedStyle = result
        return result
    }

    fun calcPixelSize(float: Float = 1f): Float {
        return float * pixelSize
    }

    fun clearCache() {
        lastFlags = -1
        cachedStyle = null
    }

    fun clone(): StyleSheet {
        var result = StyleSheet()
        result = result.apply(this)
        result.styleDisabled = styleDisabled?.clone()
        result.styleHovered = styleHovered?.clone()
        result.stylePressed = stylePressed?.clone()
        result.styleActive = styleActive?.clone()
        return result
    }

    private var lastFlags: Int = -1
    private var cachedStyle: StyleSheet? = null
}