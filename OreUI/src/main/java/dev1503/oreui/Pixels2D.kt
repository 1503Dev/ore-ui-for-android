package dev1503.oreui

class Pixels2D {
    var width: Int = 0
    var height: Int = 0
    var pixels: LongArray = longArrayOf()

    companion object {
        @JvmField
        val PIXELS_SWITCH_LEFT = Pixels2D.fromText("""
            1
            1
            1
            1
            1
            1
        """.trimIndent(), '1')
        @JvmField
        val PIXELS_SWITCH_RIGHT = Pixels2D.fromText("""
            011110
            100001
            100001
            100001
            100001
            011110
        """.trimIndent(), '1')

        @JvmStatic
        fun fromText(text: String, foregroundSymbol: Char): Pixels2D {
            val instance = Pixels2D()
            val lines = text.lines()

            instance.height = lines.size
            instance.width = lines.maxOfOrNull { it.length } ?: 0

            val tempPixels = mutableListOf<Long>()
            lines.forEachIndexed { y, line ->
                line.forEachIndexed { x, char ->
                    if (char == foregroundSymbol) {
                        tempPixels.add((x.toLong() shl 32) or (y.toLong() and 0xFFFFFFFFL))
                    }
                }
            }
            instance.pixels = tempPixels.toLongArray()
            return instance
        }
    }
}