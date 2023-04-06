package com.example.calculator

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val fragmentA = FragmentA()
    private val fragmentB = FragmentB()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState == null) {
                supportFragmentManager.beginTransaction().apply {
                    replace(R.id.container, fragmentA, "FrgA")
                    commit()
                }
        }

        else if(savedInstanceState.getBundle("FragmentB") != null ) {

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
//        val fragmentB2 = supportFragmentManager.findFragmentByTag("FrgB")
//        if(fragmentB2 != null) {
//            println("Inside If")
//            outState.putBundle("FragmentB", fragmentB2.arguments)
//        }
        super.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        val landContainerTwo = supportFragmentManager.findFragmentById(R.id.landContainer2)

        if(landContainerTwo != null) supportFragmentManager.beginTransaction().remove(landContainerTwo).commit()
        else super.onBackPressed()
    }

}