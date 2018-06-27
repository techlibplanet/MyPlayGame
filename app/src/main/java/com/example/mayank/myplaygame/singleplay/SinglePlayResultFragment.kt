package com.example.mayank.myplaygame.singleplay

import android.content.Context
import android.drm.DrmStore
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.mayank.myplaygame.Constants.logD

import com.example.mayank.myplaygame.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val RIGHT_ANSWERS = "RightAnswers"
private const val WRONG_ANSWERS = "WrongAnswers"
private const val DROP_QUESTIONS = "DropQuestions"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SinglePlayResultFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SinglePlayResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SinglePlayResultFragment : Fragment() {
    private val TAG = SinglePlayResultFragment::class.java.simpleName
    // TODO: Rename and change types of parameters
    private var rightAnswers: Int? = null
    private var wrongAnswers: Int? = null
    private var dropQuestions: Int? = null
    private var listener: OnFragmentInteractionListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            rightAnswers = it.getInt(RIGHT_ANSWERS)
            wrongAnswers = it.getInt(WRONG_ANSWERS)
            dropQuestions = it.getInt(DROP_QUESTIONS)
        }
        logD(TAG, "Right Answers - $rightAnswers")
        logD(TAG, "Wrong Answers - $wrongAnswers")
        logD(TAG, "Drop Questions - $dropQuestions")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_single_play_result, container, false)
        setResult(view)
        return view
    }

    private fun setResult(view: View) {
        if (rightAnswers!!> wrongAnswers!!){
            setResultView(view,R.drawable.master, rightAnswers!!, wrongAnswers!!, dropQuestions)
        }else {
            setResultView(view, R.drawable.pow, rightAnswers!!, wrongAnswers!!, dropQuestions)
        }
    }

    private fun setResultView(view: View,master: Int, rightAnswers: Int, wrongAnswers: Int, dropQuestions: Int?) {
        view.findViewById<ImageView>(R.id.result_image_view)?.setImageResource(master)
        view.findViewById<TextView>(R.id.right_answer_text_view)?.text = "Right Answers - $rightAnswers"
        view.findViewById<TextView>(R.id.wrong_answer_text_view)?.text = "Wrong Answers - $wrongAnswers"
        view.findViewById<TextView>(R.id.drop_question_text_view)?.text = "Drop Questions - $dropQuestions"
    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SinglePlayResultFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: String) =
                SinglePlayResultFragment().apply {
                    arguments = Bundle().apply {
                        putString(RIGHT_ANSWERS, param1)
                        putString(WRONG_ANSWERS, param2)
                        putString(DROP_QUESTIONS, param3)
                    }
                }
    }
}
