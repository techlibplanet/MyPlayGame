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
import android.widget.TextSwitcher
import android.widget.TextView
import com.example.mayank.myplaygame.Constants.logD
import com.example.mayank.myplaygame.PlayGameApplication

import com.example.mayank.myplaygame.R
import com.example.mayank.myplaygame.network.ApiClient
import com.example.mayank.myplaygame.network.IQuestion
import com.example.mayank.myplaygame.network.Question
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "Subject"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SinglePlayerQuizFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SinglePlayerQuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class SinglePlayerQuizFragment : Fragment(), View.OnClickListener {


    private val TAG = SinglePlayerQuizFragment::class.java.simpleName
    // TODO: Rename and change types of parameters
    private var subject: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var randomNumbers: ArrayList<Int>
    private var q = 0
    private var answer  = ""
    private var rightAnswers = 0
    private var wrongAnswers = 0
    private var dropQuestions = 0
    private lateinit var textSwitcherCountdown: TextSwitcher
    private var countDownTimer: CountDownTimer? = null
    private lateinit var textViewCount: TextView



    private val CLICKABLES = intArrayOf(R.id.text_view_option_a,R.id.text_view_option_b,R.id.text_view_option_c, R.id.text_view_option_d,R.id.text_view_option_e)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subject = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        logD(TAG, "Subject : $subject")

        getRandomNonRepeatingIntegers(10,1,10)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_single_player_quiz, container, false)
        textSwitcherCountdown = view.findViewById<TextSwitcher>(R.id.textSwitcherCountdown)
        textSwitcherCountdown.setFactory {
            val switcherTextView = TextView(activity)
            switcherTextView.textSize = 20F
            switcherTextView.setTextColor(Color.RED)
            switcherTextView.gravity = Gravity.CENTER
            switcherTextView
        }
        for (id in CLICKABLES){
            view.findViewById<TextView>(id).setOnClickListener(this)
        }

        getQuestionFromServer()
        return view
    }



    override fun onClick(v: View?) {
        when(v?.id){
            R.id.text_view_option_a ->{
                if (view?.findViewById<TextView>(R.id.text_view_option_a)?.text == answer){
                    rightAnswers++
                }else {
                    wrongAnswers++
                }
                getQuestionFromServer()
            }

            R.id.text_view_option_b ->{
                if (view?.findViewById<TextView>(R.id.text_view_option_b)?.text == answer){
                    rightAnswers++
                }else {
                    wrongAnswers++
                }
                getQuestionFromServer()
            }

            R.id.text_view_option_c ->{
                if (view?.findViewById<TextView>(R.id.text_view_option_c)?.text == answer){
                    rightAnswers++
                }else {
                    wrongAnswers++
                }
                getQuestionFromServer()
            }

            R.id.text_view_option_d ->{
                if (view?.findViewById<TextView>(R.id.text_view_option_d)?.text == answer){
                    rightAnswers++
                }else {
                    wrongAnswers++
                }
                getQuestionFromServer()
            }

            R.id.text_view_option_e ->{
                if (view?.findViewById<TextView>(R.id.text_view_option_e)?.text == answer){
                    rightAnswers++
                }else {
                    wrongAnswers++
                }
                getQuestionFromServer()
            }
        }
    }

    private fun getQuestionFromServer() {
        val apiClient = ApiClient()
        var retrofit = apiClient.getService<IQuestion>()
        if (q<10){
            retrofit.getQuestion(randomNumbers[q].toString()).enqueue(object : Callback<Question> {
                override fun onFailure(call: Call<Question>?, t: Throwable?) {
                    logD(TAG, "Error : $t")
                }

                override fun onResponse(call: Call<Question>?, response: Response<Question>?) {
                    if (response?.isSuccessful!!){
                        val result = response.body()
                        q++
                        setQuestionTextViews(result)
                        resetCountdownTimer(10000,1000)
                    }
                }
            })
        }else {
            logD(TAG, "Question Finished !")
            if (countDownTimer != null) {
                Log.d("MyTag", "Cancelled Countdown")
                countDownTimer!!.cancel()
            }
            changeToResultScreen()
        }
    }

    private fun changeToResultScreen(){
        val bundle = Bundle()
        bundle.putInt("RightAnswers", rightAnswers)
        bundle.putInt("WrongAnswers", wrongAnswers)
        bundle.putInt("DropQuestions", dropQuestions)
        val resultFragment = SinglePlayResultFragment()
        resultFragment.arguments = bundle
        switchToFragment(resultFragment)
    }

    private fun setQuestionTextViews(result: Question?) {
        answer = result?.answer!!
        view?.findViewById<TextView>(R.id.text_view_question)?.text = result.question
        view?.findViewById<TextView>(R.id.text_view_option_a)?.text = result.optionA
        view?.findViewById<TextView>(R.id.text_view_option_b)?.text = result.optionB
        view?.findViewById<TextView>(R.id.text_view_option_c)?.text = result.optionC
        view?.findViewById<TextView>(R.id.text_view_option_d)?.text = result.optionD
        view?.findViewById<TextView>(R.id.text_view_option_e)?.text = result.optionE
    }


    private fun getRandomNonRepeatingIntegers(size: Int, min: Int,
                                              max: Int): ArrayList<Int> {
        randomNumbers = ArrayList()

        while (randomNumbers.size < size) {
            val random = getRandomInt(min, max)

            if (!randomNumbers.contains(random)) {
                randomNumbers.add(random)
            }
        }
        return randomNumbers
    }

    private fun getRandomInt(min: Int, max: Int): Int {
        val random = Random()

        return random.nextInt(max - min + 1) + min
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
                if(q<10){
                    textViewCount.text = "00"
                    dropQuestions++
                    getQuestionFromServer()
                    resetCountdownTimer(10000,1000)
                }else {
                    dropQuestions++
                    changeToResultScreen()
                }

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
         * @return A new instance of fragment SinglePlayerQuizFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                SinglePlayerQuizFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
