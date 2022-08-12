package com.ismaeldivita.chipnavigation.sample

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.ismaeldivita.chipnavigation.sample.util.colorAnimation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HorizontalModeActivity : AppCompatActivity() {

    private val container by lazy { findViewById<View>(R.id.container) }
    private val title by lazy { findViewById<TextView>(R.id.title) }
    private val menu by lazy { findViewById<ChipNavigationBar>(R.id.bottom_menu) }
    private val scroller by lazy { findViewById<ScrollView>(R.id.scroller) }
    private var lastColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizontal)

        lastColor = (container.background as ColorDrawable).color

        menu.setOnItemSelectedListener { id ->
            val option = when (id) {
                R.id.home -> R.color.home to "Home"
                R.id.activity -> R.color.activity to "Activity"
                R.id.favorites -> R.color.favorites to "Favorites"
                R.id.settings -> R.color.settings to "Settings"
                else -> R.color.white to ""
            }
            val color = ContextCompat.getColor(this@HorizontalModeActivity, option.first)
            container.colorAnimation(lastColor, color)
            lastColor = color

            title.text = option.second
        }


        if (savedInstanceState == null) {
            menu.showBadge(R.id.home)
            menu.showBadge(R.id.settings, 32)
        }
        scroller.viewTreeObserver.addOnScrollChangedListener {


            GlobalScope.launch(Dispatchers.Main){
                val scrollY: Int = scroller.getScrollY() // For ScrollView
                val scrollX: Int = scroller.getScrollX() // For HorizontalScrollView
                // DO SOMETHING WITH THE SCROLL COORDINATES
                if (scrollY>0){
                    //withContext(Dispatchers.Main){

                    //}
                    menu.reduce()
                    System.out.println("REDUCE "+scrollY)
                }
                if (scrollY==0){
                    menu.restore()

                }
            }

        }
    }
}
