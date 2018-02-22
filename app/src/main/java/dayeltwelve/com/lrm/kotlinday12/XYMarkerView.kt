package dayeltwelve.com.lrm.kotlinday12

import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.DecimalFormat

/**
 * Created by manasi on 22/2/18.
 */
class XYMarkerView(context: Context, xAxisValueFormatter: IAxisValueFormatter) : MarkerView(context, R.layout.custom_marker_view) {

    private lateinit var tvContent: TextView
    private lateinit var  xAxisValueFormatter: IAxisValueFormatter

    private lateinit var format: DecimalFormat

    init {
        this.xAxisValueFormatter = xAxisValueFormatter
        tvContent = findViewById(R.id.tvContent) as TextView
        format = DecimalFormat("###.0")
    }
    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    override fun refreshContent(e: Entry, highlight: Highlight) {

       // tvContent.text = "x: " + xAxisValueFormatter.getFormattedValue(e.x, null) + ", y: " + format.format(e.y.toDouble())

        super.refreshContent(e, highlight)
    }

    override fun getOffset(): MPPointF {
        return MPPointF((-(getWidth() / 2)).toFloat(), (-getHeight()).toFloat())
    }
}
