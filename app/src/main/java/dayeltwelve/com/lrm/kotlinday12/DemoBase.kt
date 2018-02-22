package dayeltwelve.com.lrm.kotlinday12

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.FragmentActivity

/**
 * Created by manasi on 22/2/18.
 */
abstract class DemoBase : FragmentActivity() {

    var mMonths = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec")

    var mParties = arrayOf("Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H", "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P", "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X", "Party Y", "Party Z")

    protected lateinit var mTfRegular: Typeface
    protected lateinit var mTfLight: Typeface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mTfRegular = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf")
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf")
    }

    protected fun getRandom(range: Float, startsfrom: Float): Float {
        return (Math.random() * range).toFloat() + startsfrom
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity)
    }
}
