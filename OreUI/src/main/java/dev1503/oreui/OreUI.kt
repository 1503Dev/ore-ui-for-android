package dev1503.oreui

import android.content.Context
import android.util.Log
import android.util.TypedValue

class OreUI {
    companion object {
        init {
            Log.v("OreUI",
                """
          /$$$$$$                      /$$   /$$ /$$$$$$
         /$${'$'}__  $$                    | $$  | $$|_  $${'$'}_/
        | $$  \ $$  /$$$$$$   /$$$$$$ | $$  | $$  | $$  
        | $$  | $$ /$${'$'}__  $$ /$${'$'}__  $$| $$  | $$  | $$  
        | $$  | $$| $$  \__/| $$$$$$$$| $$  | $$  | $$  
        | $$  | $$| $$      | $${'$'}_____/| $$  | $$  | $$  
        |  $$$$$$/| $$      |  $$$$$$$|  $$$$$$/ /$$$$$$
         \______/ |__/       \_______/ \______/ |______/
         
        --------  MCBE OreUI for Android v0.3.0 --------
         https://github.com/1503Dev/ore-ui-for-android/
        -------------  Apache License 2.0  -------------

""")
        }
        const val VERSION_CODE = 3

        fun init(){}

        fun initDisplayBaseline(context: Context) {
            val px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                1f,
                context.resources.displayMetrics
            )
            StyleSheet.defaultPixelSize = px * 1.95f
        }
    }
}