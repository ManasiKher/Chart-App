package dayeltwelve.com.lrm.kotlinday12

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.WindowManager
import android.widget.SeekBar
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.MPPointF
import java.util.ArrayList

class MainActivity :  AppCompatActivity(), SeekBar.OnSeekBarChangeListener,
        OnChartValueSelectedListener {

    private var mLineChart: LineChart? = null
    private lateinit var mBarChart: BarChart
    private lateinit var mPieChart: PieChart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setBarChartData()
        setLineChartData()
        setPieChartData()

    }

    fun setPieChartData()
    {
        mPieChart = findViewById(R.id.chart_pie) as PieChart
        mPieChart.setUsePercentValues(true)
        mPieChart.getDescription().setEnabled(false)
        mPieChart.setExtraOffsets(5f, 10f, 5f, 5f)

        mPieChart.setDragDecelerationFrictionCoef(0.95f)

        //mPieChart.setCenterTextTypeface(mTfLight)
        //mPieChart.setCenterText(generateCenterSpannableText())

        mPieChart.setDrawHoleEnabled(true)
        mPieChart.setHoleColor(Color.WHITE)

        mPieChart.setTransparentCircleColor(Color.WHITE)
        mPieChart.setTransparentCircleAlpha(110)

        mPieChart.setHoleRadius(58f)
        mPieChart.setTransparentCircleRadius(61f)

        mPieChart.setDrawCenterText(true)

        mPieChart.setRotationAngle(0f)
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(true)
        mPieChart.setHighlightPerTapEnabled(true)

        // mPieChart.setUnit(" €");
        // mPieChart.setDrawUnitsInChart(true);

        // add a selection listener
        mPieChart.setOnChartValueSelectedListener(this)

        setPieData(4, 100f)

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad)
        // mPieChart.spin(2000, 0, 360);


        val l = mPieChart.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT)
        l.setOrientation(Legend.LegendOrientation.VERTICAL)
        l.setDrawInside(false)
        l.setXEntrySpace(7f)
        l.setYEntrySpace(0f)
        l.setYOffset(0f)

        // entry label styling
        mPieChart.setEntryLabelColor(Color.WHITE)
        mPieChart.setEntryLabelTextSize(12f)
    }

    fun setBarChartData()
    {
        mBarChart = findViewById(R.id.chart_bar) as BarChart
        mBarChart?.setOnChartValueSelectedListener(this)

        mBarChart?.setDrawBarShadow(false)
        mBarChart?.setDrawValueAboveBar(true)

        mBarChart?.getDescription()?.setEnabled(false)

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        mBarChart?.setMaxVisibleValueCount(60)

        // scaling can now only be done on x- and y-axis separately
        mBarChart?.setPinchZoom(false)

        mBarChart?.setDrawGridBackground(false)
        // mBarChart.setDrawYLabels(false);

        val xAxisFormatter = DayAxisValueFormatter(mBarChart)

        val xAxis = mBarChart.getXAxis()
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM)
        //xAxis.setTypeface(mTfLight)
        xAxis.setDrawGridLines(false)
        xAxis.setGranularity(1f) // only intervals of 1 day
        xAxis.setLabelCount(7)
        xAxis.setValueFormatter(xAxisFormatter)

        val custom = MyAxisValueFormatter()

        val leftAxis = mBarChart.getAxisLeft()
        //leftAxis.setTypeface(mTfLight)
        leftAxis.setLabelCount(8, false)
        leftAxis.setValueFormatter(custom)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.setSpaceTop(15f)
        leftAxis.setAxisMinimum(0f) // this replaces setStartAtZero(true)

        val rightAxis = mBarChart.getAxisRight()
        rightAxis.setDrawGridLines(false)
        //rightAxis.setTypeface(mTfLight)
        rightAxis.setLabelCount(8, false)
        rightAxis.setValueFormatter(custom)
        rightAxis.setSpaceTop(15f)
        rightAxis.setAxisMinimum(0f) // this replaces setStartAtZero(true)

        val l = mBarChart.getLegend()
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM)
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT)
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL)
        l.setDrawInside(false)
        l.setForm(Legend.LegendForm.SQUARE)
        l.setFormSize(9f)
        l.setTextSize(11f)
        l.setXEntrySpace(4f)
        // l.setExtra(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });
        // l.setCustom(ColorTemplate.VORDIPLOM_COLORS, new String[] { "abc",
        // "def", "ghj", "ikl", "mno" });

        val mv = XYMarkerView(this, xAxisFormatter)
        mv.setChartView(mBarChart) // For bounds control
        mBarChart?.setMarker(mv) // Set the marker to the chart

        setBarData(12, 50f)

    }


    private fun setPieData(count:Int, range:Float)
    {
        var mParties = arrayOf("Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H", "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P", "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X", "Party Y", "Party Z")

        val entries = ArrayList<PieEntry>()

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (i in 0 until count) {
            entries.add(PieEntry((Math.random() * range + range / 5).toFloat(),
                    mParties[i % mParties.size],
                    resources.getDrawable(R.drawable.star)))
        }



        val dataSet = PieDataSet(entries, "Election Results")

        dataSet.setDrawIcons(false)

        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // add a lot of colors

        val colors = ArrayList<Int>()

        for (c in ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c)

        for (c in ColorTemplate.JOYFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.COLORFUL_COLORS)
            colors.add(c)

        for (c in ColorTemplate.LIBERTY_COLORS)
            colors.add(c)

        for (c in ColorTemplate.PASTEL_COLORS)
            colors.add(c)

        colors.add(ColorTemplate.getHoloBlue())

        dataSet.colors = colors
        //dataSet.setSelectionShift(0f);

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(11f)
        data.setValueTextColor(Color.WHITE)
        //data.setValueTypeface(mTfLight)
        mPieChart.setData(data)

        // undo all highlights
        mPieChart.highlightValues(null)

        mPieChart.invalidate()

    }

    private fun generateCenterSpannableText(): SpannableString {

        val s = SpannableString("Kotlin Day 12")
        s.setSpan(RelativeSizeSpan(1.7f), 0, 14, 0)
        //s.setSpan(StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0)
        //s.setSpan(ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0)
        //s.setSpan(RelativeSizeSpan(.8f), 14, s.length() - 15, 0)
       // s.setSpan(StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0)
       // s.setSpan(ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0)
        return s
    }
    private fun setBarData(count: Int, range: Float) {

        val start = 1f

        val yVals1 = ArrayList<BarEntry>()

        var i = start.toInt()
        while (i < start + count.toFloat() + 1f) {
            val mult = range + 1
            val `val` = (Math.random() * mult).toFloat()

            if (Math.random() * 100 < 25) {
                yVals1.add(BarEntry(i.toFloat(), `val`, resources.getDrawable(R.drawable.star)))
            } else {
                yVals1.add(BarEntry(i.toFloat(), `val`))
            }
            i++
        }

        val set1: BarDataSet

        if (mBarChart?.getData() != null && mBarChart?.getData()?.getDataSetCount()!! > 0) {
            set1 = mBarChart?.getData()?.getDataSetByIndex(0) as BarDataSet
            set1.values = yVals1
            mBarChart?.getData()?.notifyDataChanged()
            mBarChart?.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(yVals1, "The year 2017")

            set1.setDrawIcons(false)

            set1.setColors(*ColorTemplate.MATERIAL_COLORS)

            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set1)

            val data = BarData(dataSets)
            data.setValueTextSize(10f)
            //data.setValueTypeface(mTfLight)
            data.barWidth = 0.9f

            mBarChart?.setData(data)
        }
    }

    fun setLineChartData()
    {
        mLineChart = findViewById(R.id.chart_line) as LineChart
        mLineChart?.setOnChartValueSelectedListener(this)

        // no description text
        mLineChart?.getDescription()?.isEnabled = false

        // enable touch gestures
        mLineChart?.setTouchEnabled(true)

        mLineChart?.setDragDecelerationFrictionCoef(0.9f)

        // enable scaling and dragging
        mLineChart?.setDragEnabled(true)
        mLineChart?.setScaleEnabled(true)
        mLineChart?.setDrawGridBackground(false)
        mLineChart?.setHighlightPerDragEnabled(true)

        // if disabled, scaling can be done on x- and y-axis separately
        mLineChart?.setPinchZoom(true)

        // set an alternative background color
        mLineChart?.setBackgroundColor(Color.WHITE)

        // add data
        setLineData(20, 30f)

        mLineChart?.animateX(2500)

        // get the legend (only possible after setting data)
        val l = mLineChart?.getLegend()

        // modify the legend ...
        l?.form = Legend.LegendForm.LINE
        //l?.typeface = mTfLight
        l?.textSize = 11f
        l?.textColor = Color.WHITE
        l?.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        l?.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l?.orientation = Legend.LegendOrientation.HORIZONTAL
        l?.setDrawInside(false)
//        l.setYOffset(11f);

        val xAxis = mLineChart?.getXAxis()
        //xAxis?.typeface = mTfLight
        xAxis?.textSize = 11f
        xAxis?.textColor = Color.BLUE
        xAxis?.setDrawGridLines(false)
        xAxis?.setDrawAxisLine(false)

        val leftAxis = mLineChart?.getAxisLeft()
        //leftAxis?.typeface = mTfLight
        leftAxis?.textColor = ColorTemplate.getHoloBlue()
        leftAxis?.axisMaximum = 200f
        leftAxis?.axisMinimum = 0f
        leftAxis?.setDrawGridLines(true)
        leftAxis?.isGranularityEnabled = true

        val rightAxis = mLineChart?.getAxisRight()
        //rightAxis?.typeface = mTfLight
        rightAxis?.textColor = Color.RED
        rightAxis?.axisMaximum = 900f
        rightAxis?.axisMinimum = -200f
        rightAxis?.setDrawGridLines(false)
        rightAxis?.setDrawZeroLine(false)
        rightAxis?.isGranularityEnabled = false
    }


    override fun onValueSelected(e: Entry?, h: Highlight?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onNothingSelected() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onStopTrackingTouch(p0: SeekBar?) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun setLineData(count: Int, range: Float) {

        val yVals1 = ArrayList<Entry>()

        for (i in 0 until count) {
            val mult = range / 2f
            val `val` = (Math.random() * mult).toFloat() + 50
            yVals1.add(Entry(i.toFloat(), `val`))
        }

        val set1: LineDataSet


        if (mLineChart?.getData() != null && mLineChart?.getData()?.getDataSetCount()!! > 0) {
            set1 = mLineChart?.getData()?.getDataSetByIndex(0) as LineDataSet

            set1.values = yVals1
            mLineChart?.getData()?.notifyDataChanged()
            mLineChart?.notifyDataSetChanged()
        } else {
            // create a dataset and give it a type
            set1 = LineDataSet(yVals1, "DataSet 1")

            set1.axisDependency = YAxis.AxisDependency.LEFT
            set1.color = ColorTemplate.getHoloBlue()
            set1.setCircleColor(Color.BLUE)
            set1.lineWidth = 2f
            set1.circleRadius = 3f
            set1.fillAlpha = 65
            set1.fillColor = ColorTemplate.getHoloBlue()
            set1.highLightColor = Color.rgb(244, 117, 117)
            set1.setDrawCircleHole(false)


            // create a data object with the datasets
            val data = LineData(set1)
            data.setValueTextColor(Color.BLUE)
            data.setValueTextSize(9f)

            // set data
            mLineChart?.setData(data)
        }
    }

}
