package leo.me.la.indicator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_main.indicatorBar
import kotlinx.android.synthetic.main.activity_main.viewPager
import leo.me.la.colorchangeableindicator.ColorChangeablePagerAdapter

private val dataSource = listOf(
    Pair(
        Subscription(
            "Whim to Go",
            0,
            "per month",
            "Pay for each trip as you go",
            false,
            listOf(
                Benefit(R.drawable.ic_hsl, "Public transport"),
                Benefit(R.drawable.ic_taxi, "Taxi rides"),
                Benefit(R.drawable.ic_car, "Cars")
            )
        ),
        15
    ),
    Pair(
        Subscription(
            "Whim Urban 30",
            49,
            "For first 30 days (reg. €62)\nPlan renews automatically at the regular price" +
                ".\nAny discounts will be applied when you pay.",
            null,
            true,
            listOf(
                Benefit(R.drawable.ic_hsl, "HSL 30-day ticket"),
                Benefit(R.drawable.ic_taxi, "Taxi rides for €10", "5 km radius"),
                Benefit(R.drawable.ic_car, "Cars from €49 /day"),
                Benefit(R.drawable.ic_bike, "City bikes", "Helsinki and Espoo, 30 mins")
            )
        ),
        20
    ),
    Pair(
        Subscription(
            "Whim Unlimited",
            499,
            "renews automatically every month",
            "Go unlimited",
            false,
            listOf(
                Benefit(R.drawable.ic_hsl, "Unlimited public transport"),
                Benefit(R.drawable.ic_taxi, "Unlimited Taxi rides", "5 km radius"),
                Benefit(R.drawable.ic_car, "Unlimited car use"),
                Benefit(R.drawable.ic_bike, "Unlimited City bikes", "first 30 mins")
            )
        ),
        20
    )
)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewPager.adapter = ScreenSlidePagerAdapter(fm = supportFragmentManager)
        indicatorBar.integrateWithViewPager(viewPager)
    }
    private inner class ScreenSlidePagerAdapter(
        private val data: List<Pair<Subscription, Int>> = dataSource,
        fm: FragmentManager
    ) : ColorChangeablePagerAdapter(fm) {
        override fun getCircleIndicatorSize(position: Int): Int {
            return data[position].second
        }

        override fun getTitle(position: Int): String {
            return data[position].first.name
        }

        override fun getCount(): Int = data.size

        override fun getItem(position: Int): Fragment
            = PlaceHolderFragment.newInstance(data[position].first)
    }
}

@Parcelize
data class Subscription(
    val name: String,
    val price: Int,
    val description: String,
    val highlightText: String?,
    val isSpecialOffer: Boolean,
    val benefits: List<Benefit>
) : Parcelable

@Parcelize
data class Benefit(
    @DrawableRes val icon: Int,
    val content: String,
    val extraInfo: String? = null
) : Parcelable
