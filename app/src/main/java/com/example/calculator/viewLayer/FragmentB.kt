package com.example.calculator.viewLayer

import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.calculator.R
import com.example.calculator.domainLayer.ResultsModifier
import java.text.DecimalFormat


class FragmentB : Fragment() {

    private val resultsModifier = ResultsModifier()
    private lateinit var resultBtn: Button
    private lateinit var input1: EditText
    private lateinit var input2: EditText

    private var action: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_b, container, false)
        initViews(view)

        if(arguments?.getString("input1") != null) {
            input1.text = Editable.Factory.getInstance().newEditable(arguments?.getString("input1"))
        }
        if(arguments?.getString("input2") != null) {
            input2.text = Editable.Factory.getInstance().newEditable(arguments?.getString("input2"))
        }

        action = arguments?.getString(FragmentA.action)
        resultBtn.text = action

        resultBtn.setOnClickListener {
            onClick()
        }

        return view
    }
    private fun onClick() {
        if (input1.text.toString().trim().isNotEmpty() && input2.text.toString().trim().isNotEmpty()) {

            val fragmentA = parentFragmentManager.findFragmentByTag("FrgA")
            fragmentA?.arguments = getResultBundle()

            //Edit

            if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {

                parentFragmentManager.popBackStack()
            }

            else {
                if(fragmentA != null) {
                    parentFragmentManager.beginTransaction().detach(fragmentA).commit()
                    parentFragmentManager.beginTransaction().attach(fragmentA).commit()
                    parentFragmentManager.beginTransaction().remove(this).commit()

                }
            }
            arguments = null

        } else {
            Toast.makeText(activity, "Enter Input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getResultBundle(): Bundle{

        val num1 = input1.text.toString().toFloat()
        val num2 = input2.text.toString().toFloat()

        val ans = when (action) {
            "Add" -> (num1 + num2)
            "Subtract" -> (num1 - num2)
            "Multiply" -> (num1 * num2)
            "Division" -> (num1/num2)
            else -> null!!
        }

        val format = DecimalFormat("0.#")
        val textOne = input1.text.toString()
        val textTwo = input2.text.toString()

        val bundle = Bundle()

        val resultText = "Your Result is ${format.format(ans)} for inputs $textOne and $textTwo with action ${resultBtn.text}"

        resultsModifier.addToResults(resultText)

        bundle.putString(FragmentA.resultText, resultText)
        return bundle
    }

    private fun initViews(view: View) {

        resultBtn = view.findViewById(R.id.resBtn)
        input1 = view.findViewById(R.id.eNum1)
        input2 = view.findViewById(R.id.eNum2)
    }

    override fun onSaveInstanceState(outState: Bundle) {

        if (input1.text.toString().trim().isNotEmpty()) {
            arguments?.putString("input1", input1.text.toString().trim())
        }
        if( input2.text.toString().trim().isNotEmpty()) {
            arguments?.putString("input2", input2.text.toString().trim())
        }
        super.onSaveInstanceState(outState)
    }

}