package com.rsschool.android2021

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment

class FirstFragment : Fragment() {

    private lateinit var generateButton: Button
    private lateinit var previousResult: TextView
    private lateinit var min: EditText
    private lateinit var max: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult.text = "Previous result: ${result.toString()}"

        min = view.findViewById(R.id.min_value)
        max = view.findViewById(R.id.max_value)
        // TODO: val max = ...
        generateButton.isEnabled = max.text.toString().isNotEmpty() && min.text.toString().isNotEmpty()

        min.doOnTextChanged{ _, _, _, _ ->
            var error = ""
            val minValue = 0L
            if (min.text.toString() == "") {
                error = "Заполните значения и попробуйте снова"
            } else {
                if (minValue > Int.MAX_VALUE)
                    error = "Минимальное значение слишком большое"
            }

            if (error.isNotEmpty())
                min.error = error

            if (max.text.toString().isNotEmpty()) {
                val maxValue = max.text.toString().toLong()
                if (minValue > maxValue) {
                    error = "Проверьте значения и попробуйте снова"
                    showToast(error)
                }
            }
            generateButton.isEnabled = error.isEmpty() &&
                    max.text.toString().isNotEmpty() && min.text.toString().isNotEmpty()
          }

        max.doOnTextChanged{ _, _, _, _ ->
            var error = ""
            var maxValue = 0L

            if (max.text.toString() == "") {
                error = "Заполните значения и попробуйте снова"
            } else {
                maxValue = max.text.toString().toLong()
                if (maxValue > Int.MAX_VALUE)
                    error = "Максимальное значение слишком большое"
            }

            if (error.isNotEmpty())
                max.error = error

            if (min.text.toString().isNotEmpty()) {
                 val minValue = min.text.toString().toLong()
                 if (minValue > maxValue) {
                     error = "Проверьте значения и попробуйте снова"
                     showToast(error)
                 }
            }

            generateButton.isEnabled = error.isEmpty() &&
                    max.text.toString().isNotEmpty() && min.text.toString().isNotEmpty()
        }

        generateButton.setOnClickListener {
            var error:String
            val minValue = Integer.parseInt(min.text.toString())
            val maxValue = Integer.parseInt(max.text.toString())

            if (minValue > maxValue ) {
                error = "Проверьте значения и попробуйте снова"
                showToast(error)
            }   else
               (activity as MainActivity).onButtonPressed(minValue, maxValue)

        }
    }

    private fun  showToast(error: String){
        if (error.isEmpty()) return

        val toast: Toast = Toast.makeText(activity, error, Toast.LENGTH_SHORT)
        toast.show()
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }


        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }
}