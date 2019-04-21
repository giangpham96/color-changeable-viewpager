package leo.me.la.indicator


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_place_holder.textview

private const val ARG_TEXT = "text"

class PlaceHolderFragment private constructor() : Fragment() {
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
