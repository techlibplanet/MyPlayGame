package com.example.mayank.myplaygame.singleplay

import android.content.Context
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextSwitcher
import android.widget.TextView
import android.widget.ViewSwitcher

import com.example.mayank.myplaygame.R
import kotlinx.android.synthetic.main.single_play_detail_screen.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SinglePlayDetailFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SinglePlayDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SinglePlayDetailFragment : Fragment(), View.OnClickListener {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private val TAG = SinglePlayDetailFragment::class.java.simpleName
    private val CLICKABLES = intArrayOf(R.id.imageButtonPreviousSubject, R.id.imageButtonNextSubject)
    private lateinit var subjectList:Array<String>
    private lateinit var subjectCode:Array<String>
    private lateinit var textSwitcherCountdown: TextSwitcher
    internal var i = -1
    internal var j:Int = 0
    private var subject = ""
    private var subCode = ""
    private var countDownTimer: CountDownTimer? = null
    private lateinit var textViewCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        // Subjects and Amounts from String.xml
        subjectList = resources.getStringArray(R.array.subjectList)
        subjectCode = resources.getStringArray(R.array.subject_code)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_single_play_detail, container, false)
        textSwitcherCountdown = view.findViewById<TextSwitcher>(R.id.textSwitcherCountdown)
        val inAnimation = AnimationUtils.loadAnimation(activity,android.R.anim.fade_in)
        val outAnimation = AnimationUtils.loadAnimation(activity, android.R.anim.fade_out)

        textSwitcherCountdown.setFactory {
            val switcherTextView = TextView(activity)
            switcherTextView.textSize = 16F
            switcherTextView.setTextColor(Color.RED)
            switcherTextView.gravity = Gravity.CENTER
            switcherTextView
        }
        textSwitcherCountdown.inAnimation = inAnimation
        textSwitcherCountdown.outAnimation = outAnimation

        for (id in CLICKABLES){
            view.findViewById<ImageButton>(id).setOnClickListener(this)
        }
        return view
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imageButtonPreviousSubject ->{
                if (j > 0) {
                    j--
                    i = j
                    subCode = subjectCode[j]
                    subject = subjectList[j]
                    textViewSubject.text = subject
                } else {
                    j = 6
                    subCode = subjectCode[j]
                    subject = subjectList[j]
                    textViewSubject.text = subject
                }
                resetCountdownTimer(10000,1000)
            }

            R.id.imageButtonNextSubject ->{
                if (i < 6) {
                    i++
                    j = i
                    subject = subjectList[i]
                    subCode = subjectCode[i]
                    textViewSubject.text = subject
                } else {
                    i = 0
                    subject = subjectList[i]
                    subCode = subjectCode[i]
                    textViewSubject.text = subject
                }
                resetCountdownTimer(10000,1000)
            }
        }
    }

    private fun startCountdownTimer(max: Long, min: Long) {
        countDownTimer = object : CountDownTimer(max, min) {
            override fun onTick(millisUntilFinished: Long) {
                var seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                seconds = seconds % 60
                //                countdownTextView.setText("TIME : " + String.format("%02d", minutes)
                //                        + ":" + String.format("%02d", seconds));
                textViewCount = textSwitcherCountdown.getChildAt(0) as TextView
                //                if(millisUntilFinished < 10001)
                //                    textView.setTextColor(Color.RED);

                textViewCount.text = "Time left : 0" + millisUntilFinished / 1000
            }

            override fun onFinish() {
                //textViewCount.setText("Starting Quiz...");
                //                showAlertDialog(QuickPlayActivity.this,TAG,"Quiz Starting...");
                if (countDownTimer != null) {
                    Log.d("MyTag", "Cancelled Countdown")
                    countDownTimer!!.cancel()
                }
                textViewCount.text = "Countdown Finished !"
                Log.d("Alert", "Countdown Finished !")
                val bundle = Bundle()
                bundle.putString("Subject", subject)
                val quizFragment = SinglePlayerQuizFragment()
                quizFragment.arguments = bundle
                switchToFragment(quizFragment)

            }
        }
        (countDownTimer as CountDownTimer).start()
    }

    // Switch UI to the given fragment
    private fun switchToFragment(newFrag: Fragment) {
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container, newFrag)?.commit()
    }


    private fun resetCountdownTimer(max: Long, min: Long) {
        if (countDownTimer != null) {
            countDownTimer?.cancel()
        }
        startCountdownTimer(max, min)
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
         * @return A new instance of fragment SinglePlayDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SinglePlayDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
