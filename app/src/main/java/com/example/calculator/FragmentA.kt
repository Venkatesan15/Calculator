package com.example.calculator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentA : Fragment(), View.OnClickListener {

    private lateinit var addBtn: Button
    private lateinit var subBtn: Button
    private lateinit var mulBtn: Button
    private lateinit var divBtn: Button
    private lateinit var resetBtn: Button
    private lateinit var resultView: TextView

    companion object {
        const val resultText = "resultText"
        const val action = "Action"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_a, container, false)
        initViews(view)

        if(arguments?.getString(resultText) != null) {

            addBtn.visibility = View.GONE
            subBtn.visibility = View.GONE
            mulBtn.visibility = View.GONE
            divBtn.visibility = View.GONE
            resetBtn.visibility = View.VISIBLE
            resultView.visibility = View.VISIBLE

            resultView.text = arguments?.getString(resultText)
        }
        addBtn.setOnClickListener(this)
        subBtn.setOnClickListener(this)
        mulBtn.setOnClickListener(this)
        divBtn.setOnClickListener(this)
        resetBtn.setOnClickListener(this)

        return view
    }

    override fun onClick(view: View?) {
        val actionBundle = Bundle()

        when(view) {
            addBtn -> {
                actionBundle.putString(action, addBtn.text.toString())
                createNewFrgB(actionBundle)
            }

            subBtn ->{
                actionBundle.putString(action, subBtn.text.toString())
                createNewFrgB(actionBundle)
            }

            mulBtn -> {
                actionBundle.putString(action, mulBtn.text.toString())
                createNewFrgB(actionBundle)
            }

            divBtn -> {
                actionBundle.putString(action, divBtn.text.toString())
                createNewFrgB(actionBundle)
            }

            resetBtn -> {
                arguments = null
                addBtn.visibility = View.VISIBLE
                subBtn.visibility = View.VISIBLE
                mulBtn.visibility = View.VISIBLE
                divBtn.visibility = View.VISIBLE
                resetBtn.visibility = View.GONE
                resultView.visibility = View.GONE
            }
        }
    }

    private fun createNewFrgB(bundle: Bundle) {

        val frgB = FragmentB()
        frgB.arguments = bundle

        parentFragmentManager.beginTransaction().apply {
            addToBackStack("FrgA")
            replace(R.id.container, frgB)
            commit()
        }
    }

    private fun initViews(view: View) {

        addBtn = view.findViewById(R.id.addBtn)
        subBtn = view.findViewById(R.id.subBtn)
        mulBtn = view.findViewById(R.id.mulBtn)
        divBtn = view.findViewById(R.id.divBtn)
        resetBtn = view.findViewById(R.id.resetBtn)
        resultView = view.findViewById(R.id.textView)
    }
}