package com.example.calculator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import java.text.DecimalFormat


class FragmentB : Fragment() {

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

        action = arguments?.getString(FragmentA.action)
        resultBtn.text = action

        resultBtn.setOnClickListener {
            if (input1.text.toString().trim().isNotEmpty() && input2.text.toString().trim().isNotEmpty()) {

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
                bundle.putString(FragmentA.resultText, resultText)

                val fragmentA = parentFragmentManager.findFragmentByTag("FrgA")
                fragmentA?.arguments = bundle

                parentFragmentManager.popBackStack()

            } else {
                Toast.makeText(activity, "Enter Input", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun initViews(view: View) {

        resultBtn = view.findViewById(R.id.resBtn)
        input1 = view.findViewById(R.id.eNum1)
        input2 = view.findViewById(R.id.eNum2)
    }

}