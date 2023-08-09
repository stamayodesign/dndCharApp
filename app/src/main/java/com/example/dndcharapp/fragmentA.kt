package com.example.dndcharapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val dataStr = "17"
private const val dataDex = "8"
private const val dataCon = "16"
private const val dataInt = "16"
private const val dataWis = "18"
private const val dataCha = "10"

/**
 * A simple [Fragment] subclass.
 * Use the [fragmentA.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragmentA : Fragment() {
    private var ldStr: String? = null
    private var ldDex: String? = null
    private var ldCon: String? = null
    private var ldInt: String? = null
    private var ldWis: String? = null
    private var ldCha: String? = null

    private var modStr:String? = null
    private var modDex:String? = null
    private var modCon:String? = null
    private var modInt:String? = null
    private var modWis:String? = null
    private var modCha:String? = null

    private var modTVStr:TextView? = null
    private var modTVDex:TextView? = null
    private var modTVCon:TextView? = null
    private var modTVInt:TextView? = null
    private var modTVWis:TextView? = null
    private var modTVCha:TextView? = null

    private fun handleAllModifiers(scoreStr:String,scoreDex:String,scoreCon:String,scoreInt:String, scoreWis:String, scoreCha:String){
        var (tmpStrString,_) = (activity as MainActivity).modifierHandler(scoreStr.toInt())
        var (tmpDexString,_) = (activity as MainActivity).modifierHandler(scoreDex.toInt())
        var (tmpConString,_) = (activity as MainActivity).modifierHandler(scoreCon.toInt())
        var (tmpIntString,_) = (activity as MainActivity).modifierHandler(scoreInt.toInt())
        var (tmpWisString,_) = (activity as MainActivity).modifierHandler(scoreWis.toInt())
        var (tmpChaString,_) = (activity as MainActivity).modifierHandler(scoreCha.toInt())

        modStr = tmpStrString
        modDex = tmpDexString
        modCon = tmpConString
        modInt = tmpIntString
        modWis = tmpWisString
        modCha = tmpChaString
    }

    private fun handleOneModifier(scoreStat:String):String{
        if(scoreStat == null || scoreStat == ""){
            return "(?)"
        } else{
            var (tmpScore,_) = (activity as MainActivity).modifierHandler(scoreStat.toInt())
            return tmpScore
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ldStr = it.getString(dataStr)
            ldDex = it.getString(dataDex)
            ldCon = it.getString(dataCon)
            ldInt = it.getString(dataInt)
            ldWis = it.getString(dataWis)
            ldCha = it.getString(dataCha)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_a, container, false)

        (activity as MainActivity).isScoreHighlighted(false,"",0)

        val statEVStr: EditText = view.findViewById(R.id.fragAetStat_STR)
        val statEVDex: EditText = view.findViewById(R.id.fragAetStat_DEX)
        val statEVCon: EditText = view.findViewById(R.id.fragAetStat_CON)
        val statEVInt: EditText = view.findViewById(R.id.fragAetStat_INT)
        val statEVWis: EditText = view.findViewById(R.id.fragAetStat_WIS)
        val statEVCha: EditText = view.findViewById(R.id.fragAetStat_CHA)

        val args = this.arguments
        ldStr = args?.get("dataStr").toString()
        ldDex = args?.get("dataDex").toString()
        ldCon = args?.get("dataCon").toString()
        ldInt = args?.get("dataInt").toString()
        ldWis = args?.get("dataWis").toString()
        ldCha = args?.get("dataCha").toString()

        statEVStr.setText(ldStr)
        statEVDex.setText(ldDex)
        statEVCon.setText(ldCon)
        statEVInt.setText(ldInt)
        statEVWis.setText(ldWis)
        statEVCha.setText(ldCha)

        modTVStr = view.findViewById(R.id.fragAtvStatMod_STR)
        modTVDex = view.findViewById(R.id.fragAtvStatMod_DEX)
        modTVCon = view.findViewById(R.id.fragAtvStatMod_CON)
        modTVInt = view.findViewById(R.id.fragAtvStatMod_INT)
        modTVWis = view.findViewById(R.id.fragAtvStatMod_WIS)
        modTVCha = view.findViewById(R.id.fragAtvStatMod_CHA)

        //Fill in Modifiers
        handleAllModifiers(ldStr.toString(),ldDex.toString(),ldCon.toString(),ldInt.toString(),ldWis.toString(),ldCha.toString())
        modTVStr?.setText(modStr)
        modTVDex?.setText(modDex)
        modTVCon?.setText(modCon)
        modTVInt?.setText(modInt)
        modTVWis?.setText(modWis)
        modTVCha?.setText(modCha)

        return view
    }

    fun closeKeyboard() {
        val activity = activity as MainActivity

        val view = activity.currentFocus
        if (view != null) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
        }
    }

    private fun mySetFocusHandler (view: View, focused: Boolean, modText: TextView?, editText: EditText){
        if(!focused){
            modText?.setText(handleOneModifier(editText.text.toString()))
            view.clearFocus()
        }
    }

    private fun mySetDoneHandler (view: View, actionId: Int, modText: TextView?, editText: EditText):Boolean{
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            modText?.setText(handleOneModifier(editText.text.toString()))
            closeKeyboard()
            view.clearFocus()
            return true
        } else {
            return false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btn_ReturnWOSaving = view.findViewById<Button>(R.id.fragAbtnBackTofragMain)
        val btn_SaveStats = view.findViewById<Button>(R.id.fragAbtnSaveStats)

        val statEVStr: EditText = view.findViewById(R.id.fragAetStat_STR)
        val statEVDex: EditText = view.findViewById(R.id.fragAetStat_DEX)
        val statEVCon: EditText = view.findViewById(R.id.fragAetStat_CON)
        val statEVInt: EditText = view.findViewById(R.id.fragAetStat_INT)
        val statEVWis: EditText = view.findViewById(R.id.fragAetStat_WIS)
        val statEVCha: EditText = view.findViewById(R.id.fragAetStat_CHA)

        statEVStr.setOnFocusChangeListener { view, focused -> mySetFocusHandler(view, focused, modTVStr, statEVStr)}
        statEVDex.setOnFocusChangeListener { view, focused -> mySetFocusHandler(view, focused, modTVDex, statEVDex)}
        statEVCon.setOnFocusChangeListener { view, focused -> mySetFocusHandler(view, focused, modTVCon, statEVCon)}
        statEVInt.setOnFocusChangeListener { view, focused -> mySetFocusHandler(view, focused, modTVInt, statEVInt)}
        statEVWis.setOnFocusChangeListener { view, focused -> mySetFocusHandler(view, focused, modTVWis, statEVWis)}
        statEVCha.setOnFocusChangeListener { view, focused -> mySetFocusHandler(view, focused, modTVCha, statEVCha)}

        statEVStr.setOnEditorActionListener { view, actionId: Int, _ -> mySetDoneHandler(view,actionId,modTVStr,statEVStr)}
        statEVDex.setOnEditorActionListener { view, actionId: Int, _ -> mySetDoneHandler(view,actionId,modTVDex,statEVDex)}
        statEVCon.setOnEditorActionListener { view, actionId: Int, _ -> mySetDoneHandler(view,actionId,modTVCon,statEVCon)}
        statEVInt.setOnEditorActionListener { view, actionId: Int, _ -> mySetDoneHandler(view,actionId,modTVInt,statEVInt)}
        statEVWis.setOnEditorActionListener { view, actionId: Int, _ -> mySetDoneHandler(view,actionId,modTVWis,statEVWis)}
        statEVCha.setOnEditorActionListener { view, actionId: Int, _ -> mySetDoneHandler(view,actionId,modTVCha,statEVCha)}

        btn_SaveStats.setOnClickListener{

            //fragment.arguments = bundle

            val isStrEmpty:Boolean = statEVStr.text.isEmpty()
            val isDexEmpty:Boolean = statEVDex.text.isEmpty()
            val isConEmpty:Boolean = statEVCon.text.isEmpty()
            val isIntEmpty:Boolean = statEVInt.text.isEmpty()
            val isWisEmpty:Boolean = statEVWis.text.isEmpty()
            val isChaEmpty:Boolean = statEVCha.text.isEmpty()

            if(isStrEmpty || isDexEmpty || isConEmpty || isIntEmpty || isWisEmpty || isChaEmpty){
                (activity as MainActivity).invalidStatsString()
            } else{
                val strScore = statEVStr.text.toString()
                val dexScore = statEVDex.text.toString()
                val conScore = statEVCon.text.toString()
                val intScore = statEVInt.text.toString()
                val wisScore = statEVWis.text.toString()
                val chaScore = statEVCha.text.toString()
                val bundle = Bundle()
                bundle.putString("dataStr",strScore)
                bundle.putString("dataDex",dexScore)
                bundle.putString("dataCon",conScore)
                bundle.putString("dataInt",intScore)
                bundle.putString("dataWis",wisScore)
                bundle.putString("dataCha",chaScore)
                val fragment = MainFragment()
                fragment.arguments = bundle
                fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,fragment)?.commit()
            }
        }

        btn_ReturnWOSaving.setOnClickListener{
            val fragment = MainFragment()
            val bundle = Bundle()
            // Previous Stats are still loaded so just return bundle as such
            bundle.putString("dataStr",ldStr)
            bundle.putString("dataDex",ldDex)
            bundle.putString("dataCon",ldCon)
            bundle.putString("dataInt",ldInt)
            bundle.putString("dataWis",ldWis)
            bundle.putString("dataCha",ldCha)
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,fragment)?.commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(ldStr: String, ldDex: String, ldCon: String, ldInt: String, ldWis: String, ldCha: String):fragmentA {
            val newFragment = fragmentA()
            val args = Bundle().apply {
                putString(dataStr, ldStr)
                putString(dataDex, ldDex)
                putString(dataCon, ldCon)
                putString(dataInt, ldInt)
                putString(dataWis, ldWis)
                putString(dataCha, ldCha)
            }
            newFragment.arguments = args
            return newFragment
        }
    }
}