package leo.me.la.indicator


import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.TypedValue
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.set
import androidx.core.text.toSpannable
import androidx.core.view.isGone
import androidx.core.view.isVisible
import kotlinx.android.synthetic.main.fragment_place_holder.benefits
import kotlinx.android.synthetic.main.fragment_place_holder.description
import kotlinx.android.synthetic.main.fragment_place_holder.highlighted
import kotlinx.android.synthetic.main.fragment_place_holder.offer
import kotlinx.android.synthetic.main.fragment_place_holder.price
import kotlinx.android.synthetic.main.fragment_place_holder.textview

private const val ARG_SUBS = "subscription"

class PlaceHolderFragment : Fragment() {
    private lateinit var subscription: Subscription

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscription = arguments!!.getParcelable(ARG_SUBS)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place_holder, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textview.text = subscription.name
        price.text = "€${subscription.price}"
        description.text = subscription.description
        subscription.highlightText?.let {
            highlighted.text = it
        } ?: { highlighted.isGone = true }()
        subscription.benefits.forEach {
            benefits.addView(
                TextView(context).apply {
                    maxLines = 1
                    setTextSize(TypedValue.COMPLEX_UNIT_PX, 45f)
                    gravity = Gravity.CENTER_VERTICAL
                    val displayBenefit = it.extraInfo?.let { ext -> "${it.content} ($ext)" } ?: it.content
                    text = displayBenefit
                        .toSpannable()
                        .apply {
                            set(0, it.content.length, ForegroundColorSpan(Color.BLACK))
                            set(it.content.length, length, RelativeSizeSpan(0.8f))
                        }
                    setCompoundDrawablesWithIntrinsicBounds(it.icon, 0, 0, 0)
                    compoundDrawablePadding = 30
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, 0).apply {
                        weight = 1f
                    }
                }
            )
        }
        if (subscription.isSpecialOffer) {
            price.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark))
            offer.isVisible = true
            offer.post {
                val w = offer.measuredWidth
                val h = offer.measuredHeight
                val sqrt2 = Math.sqrt(2.0).toFloat()
                offer.animate()
                    .apply {
                        duration = 0
                    }
                    .translationY((w - (1 + sqrt2) * h) / (2 * sqrt2))
                    .translationX(((sqrt2 - 1) * w + h) / (2 * sqrt2))
                    .rotation(45f)
            }
        } else {
            price.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
            offer.isVisible = false
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(subscription: Subscription) =
            PlaceHolderFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SUBS, subscription)
                }
            }
    }
}
