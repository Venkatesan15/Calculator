package com.example.calculator.viewLayer

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.calculator.R

class MainActivity : AppCompatActivity(){

    private val fragmentA = FragmentA()
    private val fragmentB = FragmentB()
    lateinit var container: LinearLayout




    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

        if(savedInstanceState == null) {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.container, fragmentA, "FrgA")
                    commit()
                }
        }

        else if(savedInstanceState.getBundle("FragmentB") != null ) {

            createFragmentB(savedInstanceState)

        }

    }

    private fun createFragmentB(savedInstanceState: Bundle) {
        fragmentB.arguments = savedInstanceState.getBundle("FragmentB")

        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {

            val landContainerTwo = supportFragmentManager.findFragmentById(R.id.landContainer2)

            if(landContainerTwo != null) {
                supportFragmentManager.beginTransaction().remove(landContainerTwo).commit()
            }
            supportFragmentManager.beginTransaction().apply {
                addToBackStack("FrgA")
                replace(R.id.container, fragmentB).commit()
            }
        } else {

            supportFragmentManager.popBackStack()
            supportFragmentManager.beginTransaction().replace(R.id.landContainer2, fragmentB).commit()
        }
    }



    override fun onSaveInstanceState(outState: Bundle) {

        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)

        if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT && currentFragment is FragmentB) {
            outState.putBundle("FragmentB", currentFragment.arguments)

        } else if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){

            val landContainerTwo = supportFragmentManager.findFragmentById(R.id.landContainer2)

            if(landContainerTwo != null) {
                outState.putBundle("FragmentB", landContainerTwo.arguments)
            }
        }

        super.onSaveInstanceState(outState)
    }


    override fun onBackPressed() {
        val landContainerTwo = supportFragmentManager.findFragmentById(R.id.landContainer2)

        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.container)

        val dynamicResultsContainer = findViewById<LinearLayout>(R.id.dynamicResultContainer)

        if(FragmentA.isShowingDialog) FragmentA.isShowingDialog = false
        if(landContainerTwo != null) {
            supportFragmentManager.beginTransaction().remove(landContainerTwo).commit()
        }
        else if(fragment is FragmentA && dynamicResultsContainer.childCount > 0) {
            dynamicResultsContainer.removeAllViews()
            findViewById<Button>(R.id.addBtn).visibility = View.VISIBLE
            findViewById<Button>(R.id.subBtn).visibility = View.VISIBLE
            findViewById<Button>(R.id.mulBtn).visibility = View.VISIBLE
            findViewById<Button>(R.id.divBtn).visibility = View.VISIBLE
            findViewById<Button>(R.id.historyBtn).visibility = View.VISIBLE

            findViewById<Button>(R.id.backBtn).visibility = View.GONE
            findViewById<Button>(R.id.clear).visibility = View.GONE

        }
        else super.onBackPressed()


    }
    private fun initViews() {
        container = findViewById(R.id.container)

    }
}


