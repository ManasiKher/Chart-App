package dayeltwelve.com.lrm.kotlinday12

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import java.text.DecimalFormat

/**
 * Created by manasi on 22/2/18.
 */
class MyAxisValueFormatter() : IAxisValueFormatter
{

    private lateinit var mFormat: DecimalFormat
    init {
        mFormat = DecimalFormat("###,###,###,##0.0")
    }

    override fun getFormattedValue(value: Float, axis: AxisBase): String {
        return mFormat.format(value.toDouble()) + " $"
    }
}
