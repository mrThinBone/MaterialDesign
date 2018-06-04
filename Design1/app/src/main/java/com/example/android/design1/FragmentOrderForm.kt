package com.example.android.design1

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetDialogFragment
import android.support.transition.Transition
import android.support.transition.TransitionInflater
import android.support.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.TextView
import com.example.android.design1.model.Product
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.layout_order_form1.*

class FragmentOrderForm: BottomSheetDialogFragment(), View.OnClickListener {

    enum class STATE{STEP1, STEP2, LAST}
    private var mState: STATE = STATE.STEP1
    private var mView: ViewGroup? = null
    private var viewTransition: Transition? = null

    companion object {
        fun newInstance(p: Product): BottomSheetDialogFragment {
            val args = Bundle()
            args.putParcelable("product", p)
            val fragment = FragmentOrderForm()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.fragment_order, container, false) as ViewGroup
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewTransition = TransitionInflater.from(context).inflateTransition(R.transition.transition_selected_view)
        viewTransition?.duration = 200
        btn_submit.setOnClickListener{
            if(mState == STATE.STEP1) stepForward()
        }
        six_value.setOnClickListener(this)
    }

    private fun stepForward() {
        if(mState == STATE.STEP1) {
            switcher.showNext()
            mState = STATE.STEP2
            bindStep2()
        }
    }

    private fun stepBackward() {
        if(mState == STATE.STEP2) {
            switcher.showPrevious()
            mState = STATE.STEP1
            bindStep1()
        }
    }

    private fun bindStep2() {
        btn_submit.text = getString(R.string.book)
        option1_holder.text = getString(R.string.label_date)
        option2_holder.text = getString(R.string.label_time)
    }

    private fun bindStep1() {
        btn_submit.text = getString(R.string.go)
        option1_holder.text = getString(R.string.label_size)
        option2_holder.text = getString(R.string.label_color)
    }

    override fun onClick(v: View?) {
        if(v != null) onOptionSelected(v)
    }

    private fun onOptionSelected(view: View) {
//        Toast.makeText(activity, "clicked", Toast.LENGTH_SHORT).show()
        val cloneView: View = if(view is TextView) cloneTextView(view) else cloneCircleColor(view)
        mView?.addView(cloneView)
        cloneView.post{

            TransitionManager.beginDelayedTransition(mView!!, viewTransition)
            cloneView.layoutParams = option1LayoutParams()
        }
    }

    private fun cloneTextView(view: TextView): View {
        val textView = TextView(activity)
        textView.text = view.text
        textView.layoutParams = originalLayoutParams(view)
        return textView
    }

    private fun cloneCircleColor(view: View): View {
        return View(activity)
    }

    private fun originalLayoutParams(view: View): ConstraintLayout.LayoutParams {
        // id of view switcher
        val id = (view.parent.parent as ViewGroup).id
        // make clone view's position to be center of copying view
        val x = (view.x + view.width/2).toInt()
        val y = (view.y + view.height/2).toInt()

        val cloneViewLP = ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        cloneViewLP.startToStart = id
        cloneViewLP.topToTop = id
        cloneViewLP.setMargins(x, y, 0, 0)
        return cloneViewLP
    }

    private fun option1LayoutParams(): ConstraintLayout.LayoutParams {
        val layoutParams = ConstraintLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT)
        layoutParams.topToBottom = R.id.option1_holder
        layoutParams.bottomToTop = R.id.option1_holder
        layoutParams.startToEnd = R.id.option1_holder
        layoutParams.marginStart = 30
        return layoutParams
    }
}