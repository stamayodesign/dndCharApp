package com.example.dndcharapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import kotlin.math.floor

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val dataStr = "17"
private const val dataDex = "8"
private const val dataCon = "16"
private const val dataInt = "16"
private const val dataWis = "18"
private const val dataCha = "10"

// Stat Class
class dataStatMain(statString: String, modEnabled:Boolean = false){
    private var ldStat = statString

    private var statTVstatName:TextView? = null
    private var statTVstat:TextView? = null
    private var statIntStat:Int? = null

    private var isHLed = false

    private var btnHighlight:Button? = null
    private var highlightColor:Int? = null
    private var standardColor:Int? = null
    private var hasModifier = modEnabled

    private var statModTV:TextView? = null
    private var statIntMod:Int? = null

    //Whenever Creating a Stat, you need to Call This
    fun setViews(statTextViewName: TextView, statTextView:TextView, statHLButton:Button, HLColor:Int, SDColor:Int){
        statTVstatName  = statTextViewName
        statTVstat      = statTextView
        statIntStat     = statTextView.text.toString().toInt()
        btnHighlight    = statHLButton
        highlightColor  = HLColor
        standardColor   = SDColor
    }

    //Whenever Creating a Stat with a Modifier, you need to Call This
    fun setModView(statModTextView:TextView){
        statModTV       = statModTextView
        statIntMod      = statModTextView.text.toString().toInt()
    }

    fun clearHighLight(){handleStatHL(true)}

    fun getStatName():String{
        return statTVstatName?.text.toString()
    }

    fun getStatValue():Int{
        return if(hasModifier) statIntMod!! else statIntStat!!
    }

    fun getIsHLed():Boolean{return isHLed}

    fun handleStatHL(resetBoolean: Boolean = false){
        isHLed = if(resetBoolean) false else !isHLed
        if(isHLed){
            statTVstatName?.setTextColor(highlightColor!!)
            statTVstat?.setTextColor(highlightColor!!)
            if(hasModifier)statModTV?.setTextColor(highlightColor!!)
        } else{
            statTVstatName?.setTextColor(standardColor!!)
            statTVstat?.setTextColor(standardColor!!)
            if(hasModifier)statModTV?.setTextColor(standardColor!!)
        }
    }

}

class MainFragment : Fragment() {
    private var ldStr: String? = null
    private var ldDex: String? = null
    private var ldCon: String? = null
    private var ldInt: String? = null
    private var ldWis: String? = null
    private var ldCha: String? = null

    private var statTVStrName:TextView? = null
    private var statTVDexName:TextView? = null
    private var statTVConName:TextView? = null
    private var statTVIntName:TextView? = null
    private var statTVWisName:TextView? = null
    private var statTVChaName:TextView? = null

    private var statTVStr:TextView? = null
    private var statTVDex:TextView? = null
    private var statTVCon:TextView? = null
    private var statTVInt:TextView? = null
    private var statTVWis:TextView? = null
    private var statTVCha:TextView? = null

    private var modStrInt:Int? = null
    private var modDexInt:Int? = null
    private var modConInt:Int? = null
    private var modIntInt:Int? = null
    private var modWisInt:Int? = null
    private var modChaInt:Int? = null

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

    var strHighlighted:Boolean = false
    var dexHighlighted:Boolean = false
    var conHighlighted:Boolean = false
    var intHighlighted:Boolean = false
    var wisHighlighted:Boolean = false
    var chaHighlighted:Boolean = false

    private var btnStrHighLight:Button? = null
    private var btnDexHighLight:Button? = null
    private var btnConHighLight:Button? = null
    private var btnIntHighLight:Button? = null
    private var btnWisHighLight:Button? = null
    private var btnChaHighLight:Button? = null

    private fun handleHighlight(statHighlighted:String, nameTV:TextView?, statTV: TextView?, modTV:TextView?){
        var isHighlighted:Boolean = false;
        //switch true/false
        if(statHighlighted == "str"){
            strHighlighted = !strHighlighted
            isHighlighted = strHighlighted
            (activity as MainActivity).isScoreHighlighted(isHighlighted,statHighlighted.uppercase(),modStrInt)
        }
        if(statHighlighted == "dex"){
            dexHighlighted = !dexHighlighted
            isHighlighted = dexHighlighted
            (activity as MainActivity).isScoreHighlighted(isHighlighted,statHighlighted.uppercase(),modDexInt)
        }
        if(statHighlighted == "con"){
            conHighlighted = !conHighlighted
            isHighlighted = conHighlighted
            (activity as MainActivity).isScoreHighlighted(isHighlighted,statHighlighted.uppercase(),modConInt)
        }
        if(statHighlighted == "int"){
            intHighlighted = !intHighlighted
            isHighlighted = intHighlighted
            (activity as MainActivity).isScoreHighlighted(isHighlighted,statHighlighted.uppercase(),modIntInt)
        }
        if(statHighlighted == "wis"){
            wisHighlighted = !wisHighlighted
            isHighlighted = wisHighlighted
            (activity as MainActivity).isScoreHighlighted(isHighlighted,statHighlighted.uppercase(),modWisInt)
        }
        if(statHighlighted == "cha"){
            chaHighlighted = !chaHighlighted
            isHighlighted = chaHighlighted
            (activity as MainActivity).isScoreHighlighted(isHighlighted,statHighlighted.uppercase(),modChaInt)
        }
        clearAllHighlight(statHighlighted)
        if(isHighlighted){
            nameTV?.setTextColor(resources.getColor(R.color.highlightColor))
            statTV?.setTextColor(resources.getColor(R.color.highlightColor))
            modTV?.setTextColor(resources.getColor(R.color.highlightColor))
        }
    }

    private fun clearAllHighlight(dontEffect:String = ""){
        statTVStrName?.setTextColor(resources.getColor(R.color.normalTextColor))
        statTVDexName?.setTextColor(resources.getColor(R.color.normalTextColor))
        statTVConName?.setTextColor(resources.getColor(R.color.normalTextColor))
        statTVIntName?.setTextColor(resources.getColor(R.color.normalTextColor))
        statTVWisName?.setTextColor(resources.getColor(R.color.normalTextColor))
        statTVChaName?.setTextColor(resources.getColor(R.color.normalTextColor))

        statTVStr?.setTextColor(resources.getColor(R.color.normalTextColor))
        statTVDex?.setTextColor(resources.getColor(R.color.normalTextColor))
        statTVCon?.setTextColor(resources.getColor(R.color.normalTextColor))
        statTVInt?.setTextColor(resources.getColor(R.color.normalTextColor))
        statTVWis?.setTextColor(resources.getColor(R.color.normalTextColor))
        statTVCha?.setTextColor(resources.getColor(R.color.normalTextColor))
        modTVStr?.setTextColor(resources.getColor(R.color.normalTextColor))
        modTVDex?.setTextColor(resources.getColor(R.color.normalTextColor))
        modTVCon?.setTextColor(resources.getColor(R.color.normalTextColor))
        modTVInt?.setTextColor(resources.getColor(R.color.normalTextColor))
        modTVWis?.setTextColor(resources.getColor(R.color.normalTextColor))
        modTVCha?.setTextColor(resources.getColor(R.color.normalTextColor))
        if(dontEffect != "str") {strHighlighted = false}
        if(dontEffect != "dex") {dexHighlighted = false}
        if(dontEffect != "con") {conHighlighted = false}
        if(dontEffect != "int") {intHighlighted = false}
        if(dontEffect != "wis") {wisHighlighted = false}
        if(dontEffect != "cha") {chaHighlighted = false}
    }

    private fun handleAllModifiers(scoreStr:String,scoreDex:String,scoreCon:String,scoreInt:String, scoreWis:String, scoreCha:String){
        var (tmpStrString,tmpStrInt) = (activity as MainActivity).modifierHandler(scoreStr.toInt())
        var (tmpDexString,tmpDexInt) = (activity as MainActivity).modifierHandler(scoreDex.toInt())
        var (tmpConString,tmpConInt) = (activity as MainActivity).modifierHandler(scoreCon.toInt())
        var (tmpIntString,tmpIntInt) = (activity as MainActivity).modifierHandler(scoreInt.toInt())
        var (tmpWisString,tmpWisInt) = (activity as MainActivity).modifierHandler(scoreWis.toInt())
        var (tmpChaString,tmpChaInt) = (activity as MainActivity).modifierHandler(scoreCha.toInt())

        modStr = tmpStrString
        modDex = tmpDexString
        modCon = tmpConString
        modInt = tmpIntString
        modWis = tmpWisString
        modCha = tmpChaString

        modStrInt = tmpStrInt
        modDexInt = tmpDexInt
        modConInt = tmpConInt
        modIntInt = tmpIntInt
        modWisInt = tmpWisInt
        modChaInt = tmpChaInt
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Default
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
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        (activity as MainActivity).isScoreHighlighted(false,"",0)

        statTVStrName = view.findViewById(R.id.fragMainllStatsName_STR)
        statTVDexName = view.findViewById(R.id.fragMainllStatsName_DEX)
        statTVConName = view.findViewById(R.id.fragMainllStatsName_CON)
        statTVIntName = view.findViewById(R.id.fragMainllStatsName_INT)
        statTVWisName = view.findViewById(R.id.fragMainllStatsName_WIS)
        statTVChaName = view.findViewById(R.id.fragMainllStatsName_CHA)

        statTVStr = view.findViewById(R.id.fragMainetStat_STR)
        statTVDex = view.findViewById(R.id.fragMainetStat_DEX)
        statTVCon = view.findViewById(R.id.fragMainetStat_CON)
        statTVInt = view.findViewById(R.id.fragMainetStat_INT)
        statTVWis = view.findViewById(R.id.fragMainetStat_WIS)
        statTVCha = view.findViewById(R.id.fragMainetStat_CHA)

        val args = this.arguments

        if (args != null) {
            ldStr = args?.get("dataStr").toString()
            ldDex = args?.get("dataDex").toString()
            ldCon = args?.get("dataCon").toString()
            ldInt = args?.get("dataInt").toString()
            ldWis = args?.get("dataWis").toString()
            ldCha = args?.get("dataCha").toString()
        } else{
            ldStr = dataStr
            //ldStr = MainActivity().handlesPref("loadStat","1")
            ldDex = dataDex
            ldCon = dataCon
            ldInt = dataInt
            ldWis = dataWis
            ldCha = dataCha
        }
        statTVStr?.setText(ldStr)
        statTVDex?.setText(ldDex)
        statTVCon?.setText(ldCon)
        statTVInt?.setText(ldInt)
        statTVWis?.setText(ldWis)
        statTVCha?.setText(ldCha)

        modTVStr = view.findViewById(R.id.fragMaintvStatMod_STR)
        modTVDex = view.findViewById(R.id.fragMaintvStatMod_DEX)
        modTVCon = view.findViewById(R.id.fragMaintvStatMod_CON)
        modTVInt = view.findViewById(R.id.fragMaintvStatMod_INT)
        modTVWis = view.findViewById(R.id.fragMaintvStatMod_WIS)
        modTVCha = view.findViewById(R.id.fragMaintvStatMod_CHA)

        //Fill in Modifiers
        handleAllModifiers(ldStr.toString(),ldDex.toString(),ldCon.toString(),ldInt.toString(),ldWis.toString(),ldCha.toString())
        modTVStr?.setText(modStr)
        modTVDex?.setText(modDex)
        modTVCon?.setText(modCon)
        modTVInt?.setText(modInt)
        modTVWis?.setText(modWis)
        modTVCha?.setText(modCha)

        // Highlight Code
        btnStrHighLight = view.findViewById(R.id.btnUseStrMod)
        btnDexHighLight = view.findViewById(R.id.btnUseDexMod)
        btnConHighLight = view.findViewById(R.id.btnUseConMod)
        btnIntHighLight = view.findViewById(R.id.btnUseIntMod)
        btnWisHighLight = view.findViewById(R.id.btnUseWisMod)
        btnChaHighLight = view.findViewById(R.id.btnUseChaMod)

        btnStrHighLight?.setOnClickListener{handleHighlight("str",statTVStrName,statTVStr,modTVStr)}
        btnDexHighLight?.setOnClickListener{handleHighlight("dex",statTVDexName,statTVDex,modTVDex)}
        btnConHighLight?.setOnClickListener{handleHighlight("con",statTVConName,statTVCon,modTVCon)}
        btnIntHighLight?.setOnClickListener{handleHighlight("int",statTVIntName,statTVInt,modTVInt)}
        btnWisHighLight?.setOnClickListener{handleHighlight("wis",statTVWisName,statTVWis,modTVWis)}
        btnChaHighLight?.setOnClickListener{handleHighlight("cha",statTVChaName,statTVCha,modTVCha)}

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentABtn = view.findViewById<Button>(R.id.fragMainbtnEditStats)
        val fragmentISBtn = view.findViewById<Button>(R.id.fragMainbtnBackToFragMain)

        fragmentISBtn.setOnClickListener(){
            fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,fragmentIronswornStatic())?.commit()
            clearAllHighlight()
        }


        fragmentABtn.setOnClickListener{
            clearAllHighlight()
            val statTVStr:TextView = view.findViewById(R.id.fragMainetStat_STR)
            val statTVDex:TextView = view.findViewById(R.id.fragMainetStat_DEX)
            val statTVCon:TextView = view.findViewById(R.id.fragMainetStat_CON)
            val statTVInt:TextView = view.findViewById(R.id.fragMainetStat_INT)
            val statTVWis:TextView = view.findViewById(R.id.fragMainetStat_WIS)
            val statTVCha:TextView = view.findViewById(R.id.fragMainetStat_CHA)
            val strScore = statTVStr.text.toString()
            val dexScore = statTVDex.text.toString()
            val conScore = statTVCon.text.toString()
            val intScore = statTVInt.text.toString()
            val wisScore = statTVWis.text.toString()
            val chaScore = statTVCha.text.toString()
            val bundle = Bundle()
            bundle.putString("dataStr",strScore)
            bundle.putString("dataDex",dexScore)
            bundle.putString("dataCon",conScore)
            bundle.putString("dataInt",intScore)
            bundle.putString("dataWis",wisScore)
            bundle.putString("dataCha",chaScore)
            val fragment = fragmentA()
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.fragmentContainerView,fragment)?.commit()
            //Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_fragmentA)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainFragment.
//         */
//        @JvmStatic
//        fun newInstance(ldStr: String, ldDex: String, ldCon: String, ldInt: String, ldWis: String, ldCha: String) =
//            MainFragment().apply {
//                arguments = Bundle().apply {
//                    putString(dataStr, ldStr)
//                    putString(dataDex, ldDex)
//                    putString(dataCon, ldCon)
//                    putString(dataInt, ldInt)
//                    putString(dataWis, ldWis)
//                    putString(dataCha, ldCha)
//                }
//            }

        @JvmStatic
        fun newInstance(ldStr: String, ldDex: String, ldCon: String, ldInt: String, ldWis: String, ldCha: String):MainFragment {
            val newFragment = MainFragment()
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