package leo.me.la.indicator


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_place_holder.offer
import kotlinx.android.synthetic.main.fragment_place_holder.textview

private const val ARG_TEXT = "text"

class PlaceHolderFragment : Fragment() {
    private lateinit var text: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        text = arguments!!.getString(ARG_TEXT)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place_holder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textview.text = text
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
    }

    companion object {
        @JvmStatic
        fun newInstance(text: String) =
            PlaceHolderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TEXT, text)
                }
            }
    }
}
