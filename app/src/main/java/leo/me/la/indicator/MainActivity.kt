package leo.me.la.indicator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.indicatorBar
import kotlinx.android.synthetic.main.activity_main.viewPager
import leo.me.la.colorchangeableindicator.ColorChangeablePagerAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager.adapter = ScreenSlidePagerAdapter(fm = supportFragmentManager)
        indicatorBar.integrateWithViewPager(viewPager)
    }
    private inner class ScreenSlidePagerAdapter(
        private val data: List<String> = listOf("Vanilla", "Chocolate", "Strawberry"),
        private val size: IntArray = intArrayOf(15, 20, 25),
        fm: FragmentManager
    ) : ColorChangeablePagerAdapter(fm) {
        override fun getCircleIndicatorSize(position: Int): Int {
            return size[position]
        }

        override fun getTitle(position: Int): String {
            return data[position]
        }

        override fun getCount(): Int = data.size

        override fun getItem(position: Int): Fragment
            = PlaceHolderFragment.newInstance(data[position])
    }
}
