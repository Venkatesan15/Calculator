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
    private lateinit var eText1: EditText
    private lateinit var eText2: EditText
    private var action: String? = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_b, container, false)
        declaration(view)

        action = arguments?.getString("Action")
        resultBtn.text = action

        resultBtn.setOnClickListener {
            if (eText1.text.toString().trim().isNotEmpty() && eText2.text.toString().trim().isNotEmpty()) {

                val num1 = eText1.text.toString().toFloat()
                val num2 = eText2.text.toString().toFloat()

                val ans = when (action) {
                    "Add" -> (num1 + num2)
                    "Subtract" -> (num1 - num2)
                    "Multiply" -> (num1 * num2)
                    "Division" -> (num1/num2)
                    else -> null!!
                }
                val format = DecimalFormat("0.#")
                val t1 = eText1.text.toString()
                val t2 = eText2.text.toString()

                val bundle = Bundle()

                val resultText = "Your Result is ${format.format(ans)} for inputs $t1 and $t2 with action ${resultBtn.text}"
                bundle.putString("resultText", resultText)

                val frg = parentFragmentManager.findFragmentByTag("FrgA")
                frg?.arguments = bundle

                parentFragmentManager.popBackStack()

            } else {
                Toast.makeText(activity, "Enter Input", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

    private fun declaration(view: View) {

        resultBtn = view.findViewById(R.id.resBtn)
        eText1 = view.findViewById(R.id.eNum1)
        eText2 = view.findViewById(R.id.eNum2)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("Action", action)
        super.onSaveInstanceState(outState)
    }
}