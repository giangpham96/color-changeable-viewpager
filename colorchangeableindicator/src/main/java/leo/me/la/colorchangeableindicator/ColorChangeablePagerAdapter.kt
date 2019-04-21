package leo.me.la.colorchangeableindicator

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

abstract class ColorChangeablePagerAdapter(fm: FragmentManager)
    : FragmentStatePagerAdapter(fm) {

    abstract fun getCircleIndicatorSize(position: Int): Int

    abstract fun getTitle(position: Int) : String
}
