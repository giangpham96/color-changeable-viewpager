package leo.me.la.indicator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import kotlinx.android.synthetic.main.activity_main.indicatorBar
import kotlinx.android.synthetic.main.activity_main.viewPager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager.adapter = ScreenSlidePagerAdapter(fm = supportFragmentManager)
        indicatorBar.viewPager = viewPager
    }
    private inner class ScreenSlidePagerAdapter(
        private val data: List<String> = listOf("Vanilla", "Chocolate", "Strawberry"),
        fm: FragmentManager
    ) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = data.size

        override fun getItem(position: Int): Fragment
            = PlaceHolderFragment.newInstance(data[position])
    }
}
