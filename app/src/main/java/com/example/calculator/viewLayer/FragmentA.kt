package com.example.calculator.viewLayer

import android.content.DialogInterface
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.calculator.R
import com.example.calculator.domainLayer.ResultsModifier


class FragmentA : Fragment(), View.OnClickListener {

    private val resultsModifier = ResultsModifier()

    private lateinit var addBtn: Button
    private lateinit var subBtn: Button
    private lateinit var mulBtn: Button
    private lateinit var divBtn: Button
    private lateinit var resetBtn: Button
    private lateinit var resultView: TextView

    private lateinit var historyBtn: Button

    private lateinit var backBtn: Button
    private lateinit var clearBtn: Button



    private var alertDialog: AlertDialog? = null


    private lateinit var dynamicResultContainer: LinearLayout

    lateinit var resultChild: View
    companion object {
        var isShowingDialog = false
        const val resultText = "resultText"
        const val action = "Action"
        const val isResultsCurrentlyVisible = "isResultsCurrentlyVisible"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_a, container, false)
        initViews(view)


        if(arguments?.getString(resultText) != null) {

            actionButtonsGone()

            resetBtn.visibility = View.VISIBLE
            resultView.visibility = View.VISIBLE

            resultView.text = arguments?.getString(resultText)
        } else if(savedInstanceState != null && savedInstanceState.getBoolean(
                isResultsCurrentlyVisible
            )) {
            showHistory()
        }
        if(savedInstanceState != null && savedInstanceState.getBoolean("isShowingDialog")) {
            showAlertDialog()
        }
        addBtn.setOnClickListener(this)
        subBtn.setOnClickListener(this)
        mulBtn.setOnClickListener(this)
        divBtn.setOnClickListener(this)
        resetBtn.setOnClickListener(this)
        historyBtn.setOnClickListener(this)
        backBtn.setOnClickListener(this)
        clearBtn.setOnClickListener(this)



        return view
    }

    private fun actionButtonsGone() {
        addBtn.visibility = View.GONE
        subBtn.visibility = View.GONE
        mulBtn.visibility = View.GONE
        divBtn.visibility = View.GONE
        historyBtn.visibility = View.GONE
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dynamicResultContainer = view.findViewById(R.id.dynamicResultContainer)

        super.onViewCreated(view, savedInstanceState)
    }

    private fun showHistory() {

        actionButtonsGone()

        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            val fragment = parentFragmentManager.findFragmentById(R.id.landContainer2)
            if(fragment != null) parentFragmentManager.beginTransaction().remove(fragment).commit()


        }
        backBtn.visibility = View.VISIBLE
        clearBtn.visibility = View.VISIBLE


        var c = 1

        for (result in resultsModifier.getResults()) {


             resultChild = layoutInflater.inflate(
                 R.layout.result_layout,
                dynamicResultContainer,
                false
            )
            val sNo = resultChild.findViewById<TextView>(R.id.sNo)
            val resultTextView = resultChild.findViewById<TextView>(R.id.resultText)


            sNo.text = (c++).toString()
            resultTextView.text = result

            dynamicResultContainer.addView(resultChild)

        }
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
            historyBtn -> {

                if(resultsModifier.getResults().isNotEmpty()) {
                    showHistory()
                } else {
                    Toast.makeText(context, "History is Empty", Toast.LENGTH_SHORT).show()
                }
            }

            resetBtn -> {
                arguments = null
                actionButtonsVisible()

                resetBtn.visibility = View.GONE
                resultView.visibility = View.GONE
            }

            backBtn -> {

                dynamicResultContainer.removeAllViews()

                actionButtonsVisible()

                backBtn.visibility = View.GONE
                clearBtn.visibility = View.GONE

            }
            clearBtn -> {

                showAlertDialog()
            }

        }
    }
    private fun showAlertDialog() {
        isShowingDialog = true

        val alertDialogBuilder: AlertDialog.Builder? = activity?.let { AlertDialog.Builder(it) }
        alertDialogBuilder?.setMessage("Once Cleared You Can't get Back Histories")
        alertDialogBuilder?.setNegativeButton("cancel" , DialogInterface.OnClickListener{ _: DialogInterface, _:Int ->

            isShowingDialog = false
        })
        alertDialogBuilder?.setPositiveButton("Clear" , DialogInterface.OnClickListener{ _: DialogInterface, _:Int ->

            resultsModifier.clearResults()
            dynamicResultContainer.removeAllViews()
            clearBtn.visibility = View.GONE
            backBtn.visibility = View.GONE

            actionButtonsVisible()
            isShowingDialog = false


            Toast.makeText(activity, "Cleared Successfully", Toast.LENGTH_SHORT).show()
        })
        alertDialogBuilder?.setOnCancelListener { isShowingDialog = false }

        alertDialog = alertDialogBuilder?.create()
        alertDialog?.show()
    }

    override fun onSaveInstanceState(outState: Bundle) {

        if(dynamicResultContainer.childCount > 0) {
            outState.putBoolean(isResultsCurrentlyVisible, true)
        }
        if(isShowingDialog) outState.putBoolean("isShowingDialog", true)
        super.onSaveInstanceState(outState)
    }



    private fun createNewFrgB(bundle: Bundle) {

        val frgB = FragmentB()
        frgB.arguments = bundle

        parentFragmentManager.beginTransaction().apply {

            if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {

                replace(R.id.landContainer2, frgB, "FrgB")
                commit()
            } else {

                replace(R.id.container, frgB, "FrgB")
                addToBackStack("FrgA")
                commit()
            }
        }
    }
    fun actionButtonsVisible() {
        addBtn.visibility = View.VISIBLE
        subBtn.visibility = View.VISIBLE
        mulBtn.visibility = View.VISIBLE
        divBtn.visibility = View.VISIBLE
        historyBtn.visibility = View.VISIBLE
    }

    override fun onPause() {
        if(isShowingDialog) alertDialog?.dismiss()
        super.onPause()
    }


    private fun initViews(view: View) {

        dynamicResultContainer = view.findViewById(R.id.dynamicResultContainer)

        addBtn = view.findViewById(R.id.addBtn)
        subBtn = view.findViewById(R.id.subBtn)
        mulBtn = view.findViewById(R.id.mulBtn)
        divBtn = view.findViewById(R.id.divBtn)
        resetBtn = view.findViewById(R.id.resetBtn)
        resultView = view.findViewById(R.id.textView)

        historyBtn = view.findViewById(R.id.historyBtn)
        backBtn = view.findViewById(R.id.backBtn)
        clearBtn = view.findViewById(R.id.clear)
    }

}