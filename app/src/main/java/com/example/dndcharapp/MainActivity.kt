package com.example.dndcharapp

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.dndcharapp.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.floor

//text is already parsed at this point
class rollResults(inputDiceString: String, inputDiceResults:String = ""){
    private var diceString:String = inputDiceString
    private var resultsString:String = inputDiceResults
    private var listOfDice:MutableList<Die> = mutableListOf()
    private var listOfResults:MutableList<Int> = mutableListOf()
    private var resultFreqMap: Map<Int,Int> = listOfResults.groupingBy { it }.eachCount()



    init {
        //Roll Dice
        if (resultsString == ""){
            //TODO roll dice
        }
    }

    fun rerollDice(){

    }

    fun getHighest():Int{
        return listOfResults.maxOrNull()!!
    }

    fun getLowest():Int{
        return listOfResults.minOrNull()!!
    }

    fun getHighestMatches(){
        val freqMap: MutableMap<Int,Int> = HashMap()
        for (item in listOfResults){
            var count = freqMap[item]
            if (count == null) count = 0
            freqMap[item] = count +1
        }
    }

    fun getMatchesParticularNumber(numToMatch:Int){

    }
}

//preset Die class
class Die(private var max: Int){
    val name = "d$max"
    fun roll():Int{
        return ((1..max).random())
    }
    fun rollMultiple(times:Int):Int{
        var tmpNum = 0
        for (num in 1..times){
            tmpNum += roll()
        }
        return tmpNum
    }
    fun rollMultipleString(times:Int):String{
        return "$times" + "d$max"
    }
}

/*
    Table of Contents:
    [Main Variables]
    [Dice Code]
    [NFC Tag Code]
    [Main Code]
*/


class MainActivity : AppCompatActivity() {
    // Default Dice
    val d4 = Die(4)

    // ** [Main Variables] **
    private lateinit var binding: ActivityMainBinding
    private var buttonD4:Button? = null
    private var buttonD6:Button? = null
    private var buttonD8:Button? = null
    private var buttonD10:Button? = null
    private var buttonD12:Button? = null
    private var buttonD20:Button? = null
    private var buttonD100:Button? = null

    //Mode 2
    private var buttonMultiDiceRoll:Button? = null
    private var multiListStringHolder = ""

    private var buttonDiceModeAdd:Button? = null
    private var buttonDiceModeSubtract:Button? = null
    private var buttonDiceModeClear:Button? = null

    private var tVRollingTextCurrent:String? = null
    private var textResultCurrent:String? = null
    private var tVResultSumCurrent:String? = null

    private var tVRollingText:TextView? = null
    private var tvResult:TextView? = null
    private var tVResultSum:TextView? = null

    private var scoreHighlighted:Boolean = false
    private var modifierString:String? = null
    private var modifierInt:Int? = null

    private var diceRollMultiAddSub:Boolean = true

    private var modeMultiList:MutableList<Int> = mutableListOf(0,0,0,0,0,0,0) //d4, d6, d8, d10, d12, d20, d100

    private var modeSwitch = 1;
    private var buttonTabSingleRoll:Button? = null
    private var buttonTabMultiRoll:Button? = null
    private var buttonTabCustomRoll:Button? = null
    private var customDiceTil:LinearLayout? = null
    private var lldndDiceRow1:LinearLayout? = null
    private var lldndDiceRow2:LinearLayout? = null
    private var lldndDiceRow3:LinearLayout? = null

    //number of dice to add
    private var buttonNumOfDiceSub:Button? = null
    private var tvNumOfDice:TextView? = null
    private var buttonNumOfDiceAdd:Button? = null
    private var intNumOfDice = 1
    private var strNumOfDice:String = "1d"

    //other modifier
    private var buttonOtherModSub:Button? = null
    private var tvOtherMod:TextView? = null
    private var buttonOtherModAdd:Button? = null
    private var intOtherMod = 0
    private var strOtherMod:String = "+0"

    //show btnShowHideEachRoll
    private var switchShowHideEachRoll:Switch? = null

    //DeleteLastEdition
    private var strLastAction:String = ""

    fun isShowingRollHandler(isChecked:Boolean){
        if(isChecked){
            tvResult?.visibility = View.VISIBLE
        } else{
            tvResult?.visibility = View.INVISIBLE
        }
    }

    fun changeNumOfDice(isAdd:Boolean, isReset:Boolean = false){
        if(!isReset){
            if(isAdd){
                intNumOfDice++
            } else{
                intNumOfDice--
                if (intNumOfDice < 1) intNumOfDice = 1
            }
        } else{
            intNumOfDice = 1
        }
        strNumOfDice = intNumOfDice.toString() + "d"
        tvNumOfDice?.text = strNumOfDice
    }

    fun changeOtherModifier(isAdd: Boolean, isReset: Boolean = false){
        if(!isReset){
            if(isAdd){
                intOtherMod++
            } else{
                intOtherMod--
            }
        } else{
            intOtherMod = 0
        }
        if (intOtherMod >= 0){
            strOtherMod = "+" + intOtherMod.toString()
        } else{
            strOtherMod = intOtherMod.toString()
        }
        tvOtherMod?.text = strOtherMod
        updateDiceText()
    }


    fun modeSwitchHandler(diceMode:Int){
        //Clears All Modes
        modeMultiList = mutableListOf(0,0,0,0,0,0,0)
        multiListStringHolder = ""
        tVRollingTextCurrent = ""
        textResultCurrent = ""
        tVResultSumCurrent = ""
        diceRollModeClear()

        //Single Roll
        if(diceMode == 1){
            buttonTabSingleRoll?.setBackgroundColor(resources.getColor(com.example.dndcharapp.R.color.currentTab))
            buttonTabSingleRoll?.isClickable = false
        } else{
            buttonTabSingleRoll?.setBackgroundColor(resources.getColor(com.example.dndcharapp.R.color.disabledTab))
            buttonTabSingleRoll?.isClickable = true
        }

        //Multi Roll
        if(diceMode == 2){
            buttonTabMultiRoll?.setBackgroundColor(resources.getColor(com.example.dndcharapp.R.color.currentTab))
            buttonTabMultiRoll?.isClickable = false
            lldndDiceRow3?.visibility = View.VISIBLE
            diceRollMultiAddSub = true

            buttonDiceModeAdd?.setBackgroundColor(resources.getColor(com.example.dndcharapp.R.color.currentTab))
            buttonDiceModeSubtract?.setBackgroundColor(resources.getColor(com.example.dndcharapp.R.color.disabledTab))

            buttonMultiDiceRoll?.isClickable = false
            buttonDiceModeAdd?.isClickable = false
            buttonDiceModeSubtract?.isClickable = true
        } else{
            buttonTabMultiRoll?.setBackgroundColor(resources.getColor(com.example.dndcharapp.R.color.disabledTab))
            buttonTabMultiRoll?.isClickable = true
            lldndDiceRow3?.visibility = View.INVISIBLE
            diceRollMultiAddSub = false
        }

        if(diceMode == 1 || diceMode == 2){
            lldndDiceRow1?.visibility = View.VISIBLE
            lldndDiceRow2?.visibility = View.VISIBLE
        } else{
            lldndDiceRow1?.visibility = View.INVISIBLE
            lldndDiceRow2?.visibility = View.INVISIBLE
        }

        //Custom Roll
        if(diceMode == 3){
            buttonTabCustomRoll?.setBackgroundColor(resources.getColor(com.example.dndcharapp.R.color.currentTab))
            buttonTabCustomRoll?.isClickable = false
            customDiceTil?.visibility = View.VISIBLE

        } else{
            buttonTabCustomRoll?.setBackgroundColor(resources.getColor(com.example.dndcharapp.R.color.disabledTab))
            buttonTabCustomRoll?.isClickable = true
            customDiceTil?.visibility = View.INVISIBLE
        }
    }


    fun diceMultiModeDone(){
        if(modeMultiList.sum() > 0){
            var tmpString = ""

            if (modeMultiList[0] > 0)     {tmpString += "+" + modeMultiList[0] + "d4"}
            if (modeMultiList[1] > 0)     {tmpString += "+" + modeMultiList[1] + "d6"}
            if (modeMultiList[2] > 0)     {tmpString += "+" + modeMultiList[2] + "d8"}
            if (modeMultiList[3] > 0)     {tmpString += "+" + modeMultiList[3] + "d10"}
            if (modeMultiList[4] > 0)     {tmpString += "+" + modeMultiList[4] + "d12"}
            if (modeMultiList[5] > 0)     {tmpString += "+" + modeMultiList[5] + "d20"}
            if (modeMultiList[6] > 0)     {tmpString += "+" + modeMultiList[6] + "d100"}

            tmpString = tmpString.drop(1) // remove first "+" sign
            multiListStringHolder = tmpString
            parseCustomText(tmpString)
            //modeMultiList = mutableListOf(0,0,0,0,0,0,0)
        } else if (multiListStringHolder != ""){
            parseCustomText(multiListStringHolder)
        } // allows rerolls
    }

    fun diceRollModeAddSubSwitch(){
        if (modeSwitch == 2){
            diceRollMultiAddSub =! diceRollMultiAddSub
            if (diceRollMultiAddSub){
                buttonDiceModeAdd?.isClickable = false
                buttonDiceModeSubtract?.isClickable = true
                buttonDiceModeAdd?.setBackgroundColor(resources.getColor(com.example.dndcharapp.R.color.currentTab))
                buttonDiceModeSubtract?.setBackgroundColor(resources.getColor(com.example.dndcharapp.R.color.disabledTab))
            } else{
                buttonDiceModeAdd?.isClickable = true
                buttonDiceModeSubtract?.isClickable = false
                buttonDiceModeSubtract?.setBackgroundColor(resources.getColor(com.example.dndcharapp.R.color.currentTab))
                buttonDiceModeAdd?.setBackgroundColor(resources.getColor(com.example.dndcharapp.R.color.disabledTab))
            }
        }
    }

    fun diceRollModeAddSub(myID:Int){
        if(diceRollMultiAddSub){
            modeMultiList[myID] = modeMultiList[myID] + intNumOfDice
        } else{
            modeMultiList[myID] = modeMultiList[myID] - intNumOfDice
            if (modeMultiList[myID] <0) modeMultiList[myID] = 0
        }
        updateMultiDiceString()
    }

    fun updateMultiDiceString(){
        //adding/subtracting
        var tmpString = ""

        buttonMultiDiceRoll?.isClickable = false

        if (modeMultiList[0] > 0)     {tmpString += "+" + modeMultiList[0] + "d4";buttonMultiDiceRoll?.isClickable = true}
        if (modeMultiList[1] > 0)     {tmpString += "+" + modeMultiList[1] + "d6";buttonMultiDiceRoll?.isClickable = true}
        if (modeMultiList[2] > 0)     {tmpString += "+" + modeMultiList[2] + "d8";buttonMultiDiceRoll?.isClickable = true}
        if (modeMultiList[3] > 0)     {tmpString += "+" + modeMultiList[3] + "d10";buttonMultiDiceRoll?.isClickable = true}
        if (modeMultiList[4] > 0)     {tmpString += "+" + modeMultiList[4] + "d12";buttonMultiDiceRoll?.isClickable = true}
        if (modeMultiList[5] > 0)     {tmpString += "+" + modeMultiList[5] + "d20";buttonMultiDiceRoll?.isClickable = true}
        if (modeMultiList[6] > 0)     {tmpString += "+" + modeMultiList[6] + "d100";buttonMultiDiceRoll?.isClickable = true}

        tVRollingTextCurrent = "Rolling" + tmpString
        textResultCurrent = ""
        tVResultSumCurrent = ""

        updateDiceText()
    }

    fun diceRollModeClear(){
        modeMultiList = mutableListOf(0,0,0,0,0,0,0)
        updateMultiDiceString()
        tVRollingTextCurrent = ""
        updateDiceText()
    }

    // Stat Score Highlighted
    fun isScoreHighlighted(myBoolean:Boolean, myString:String, myInt:Int?){
        scoreHighlighted = myBoolean
        modifierString = myString
        modifierInt = myInt
        updateDiceText()
    }

    fun updateDiceText(){
        //Initial Strings
        var tmpRollingString = ""
        var tmpResultString = ""
        var tmpResultSumInt = 0

        //Initial
        if(tVRollingTextCurrent.isNullOrEmpty()) {
            tVRollingText?.text = ""
            tvResult?.text = ""
            tVResultSum?.text = ""
            return
        }

        tmpRollingString = tVRollingTextCurrent as String
        tmpResultString = textResultCurrent as String
        if(!tVResultSumCurrent.isNullOrEmpty()) tmpResultSumInt = tVResultSumCurrent?.toInt()!!

        //If Has Modifier
        if(intOtherMod > 0){
            tmpRollingString += "+" + intOtherMod
            tmpResultString += "+" + intOtherMod
        } else if (intOtherMod < 0){
            tmpRollingString += "" + intOtherMod
            tmpResultString += "" + intOtherMod
        }
        tmpResultSumInt += intOtherMod

        //If Score is Highlighted
        if(scoreHighlighted){
            tmpRollingString += "+" + modifierString
            if(modifierInt!! >= 0){
                tmpResultString += "+" + modifierInt
            } else{
                tmpResultString += modifierInt
            }
            tmpResultSumInt += modifierInt!!
        }

        //Final Commit
        tVRollingText?.text = tmpRollingString
        tvResult?.text = tmpResultString
        tVResultSum?.text = tmpResultSumInt.toString()
    }



    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    // ** [Dice Code] **

    //rolls one die, if provided a 0 or negative number, returns a 0
    fun rollDie(dieMax: Int): Int {if (dieMax > 0) {return (1..dieMax).random()} else {return 0}}

    //rolls a number of one type of die
    fun rollDiceST(numRolled: Int, dieMax: Int): List<Int> {
        var tmpDiceList: MutableList<Int> = mutableListOf()
        for (a in 1..numRolled) {tmpDiceList.add(rollDie(dieMax))}
        return tmpDiceList
    }

    //takes the parsed text and separates the string by '+' characters
    fun separateDice(diceString: String, sumRoll: Boolean = true): MutableList<String> {
        val tmpDiceStringParts = diceString.split('+')
        var returnDiceResult = mutableListOf<String>()

        for (a in 0..tmpDiceStringParts.size - 1) {
            returnDiceResult.add(a, rollCustomDieString(tmpDiceStringParts[a]))
        }
        return returnDiceResult
    }

    //returns dice roll based on the string part entered after string is parsed
    fun rollCustomDieString(diceString: String): String {
        if ('d' in diceString) {// is a dice string
            var checkDString = diceString
            if (checkDString.first() == 'd') {
                checkDString = '1' + checkDString
            }
            // ending d should have a warning eventually
            if (checkDString.last() == 'd') {
                checkDString = checkDString + '1'
            }
            var tmpString = checkDString.split('d')
            return rollDiceST(tmpString[0].toInt(), tmpString[1].toInt()).toString()
        } else {// is just a modifier
            return diceString
        }
    }

    fun parseCustomText(
        diceString: String,
        sumRoll: Boolean = true
    ) {
        var tmpDiceString = ""

        //checks if string fits format #d#+#
        for (elem in diceString) {
            if (elem.isDigit() || elem == '+' || elem == 'd' || elem == 'D') {
                tmpDiceString = tmpDiceString.plus(elem)
            }
        }
        tmpDiceString = tmpDiceString.lowercase()

        var theRoll = separateDice(tmpDiceString)

        var tmpDiceListString = theRoll.toString()
        tmpDiceListString = tmpDiceListString.drop(1)
        tmpDiceListString = tmpDiceListString.dropLast(1)

        var tmpSum = 0

        theRoll.toString().split(',').forEach() {
            var tmpNumber = it.filter{it.isDigit()}
            tmpSum += tmpNumber.toInt()
        }

        tVRollingTextCurrent = "Rolled " + tmpDiceString
        textResultCurrent = tmpDiceListString
        tVResultSumCurrent = tmpSum.toString()

        updateDiceText()
    }

    fun validateCustomInputText(inputString: String): Boolean {
        var tmpBoolean = true;
        //checks if string fits format #d#+#
        for (elem in inputString) {
            if (!elem.isDigit() && elem != '+' && elem != 'd' && elem != 'D') {
                tmpBoolean = false
            }
        }
        return tmpBoolean
    }

    private fun customDiceFocusListener() {
        binding.tietCustomDiceRoll.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                validDiceString().also { binding.tilCustomDiceRollContainer.helperText = it }
                hideKeyboard(binding.tietCustomDiceRoll)
            }
        }
        binding.tietCustomDiceRoll.setOnEditorActionListener { _, actionId: Int, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                binding.tilCustomDiceRollContainer.helperText = validDiceString()
                if (binding.tilCustomDiceRollContainer.helperText == null) {
                    hideKeyboard(binding.tietCustomDiceRoll)
                    submitCustomRoll()
                }
                true
            } else {
                false
            }
        }
    }

    private fun validDiceString(): String? {
        // removes whitespace during validation
        val tmpCustomText =
            (binding.tietCustomDiceRoll.text.toString()).replace("\\s".toRegex(), "")

        if (!validateCustomInputText(tmpCustomText)) {
            return "Contains a non number, non 'd', or non '+' character"
        }
        if (!tmpCustomText.contains('d', true)) {
            return "Must contain at least one 'd'"
        }
        if (tmpCustomText.matches(".*[d]+[d]+.*".toRegex(RegexOption.IGNORE_CASE))) {
            return "Text contains repeating 'd''s "
        } // just one 'dd' situation will trigger this
        if (tmpCustomText.matches(".*[d]+.*".toRegex(RegexOption.IGNORE_CASE))) {
            // anything 'd' anything
            if (tmpCustomText.matches(".*[d][0-9]+[d]+.*".toRegex(RegexOption.IGNORE_CASE))) {
                return "Die Roll does not include a '+' between the 'd's"
            } // as long as there is something else between 1
        }
        if (!tmpCustomText.matches(".*[0-9]$".toRegex(RegexOption.IGNORE_CASE))) {
            return "Must end with a Number"
        }
        if (!tmpCustomText.matches(".*[0-9]$".toRegex(RegexOption.IGNORE_CASE))) {
            return "Must end with a Number"
        }
        return null
    }

    private fun submitCustomRoll() {
        binding.tietCustomDiceRoll.clearFocus()
        val validCustomDice = binding.tilCustomDiceRollContainer.helperText == null

        if (validCustomDice) {
            val tmpCustomText = (binding.tietCustomDiceRoll.text.toString()).replace(
                "\\s".toRegex(),
                ""
            ) // Removes white spaces from validated text
            parseCustomText(tmpCustomText)
        } else {
            invalidCustomRollString()
        }
    }

    private fun invalidCustomRollString() {
        AlertDialog.Builder(this)
            .setTitle("Invalid Dice Roll")
            .setMessage(binding.tilCustomDiceRollContainer.helperText)
            .setPositiveButton("Okay") { _, _ ->
                //do nothing
            }
            .show()
    }

    fun invalidStatsString(){
        AlertDialog.Builder(this)
            .setTitle("Invalid Stats Roll")
            .setMessage("All Stats must be filled in")
            .setPositiveButton("Okay") { _, _ ->
                //do nothing
            }
            .show()
    }

    fun modifierHandler(score:Int):Pair<String, Int>{
        var modNumber = floor((score.toFloat()-10) /2).toInt()
        if(modNumber >= 0){return Pair("(+${modNumber})",modNumber)
        } else{return Pair("(${modNumber})",modNumber)}
    }

    var defaultDNDStrScore:String? = null

    fun handlesPref(thingToDo:String,statScore:String):String{
        edit?.putString(getString(R.string.saved_DNDStrScore), statScore)

        var currentDNDStrScore = sharedPref?.getString(getString(R.string.saved_DNDStrScore), defaultDNDStrScore!!)
        print(defaultDNDStrScore)
        if(thingToDo == "storeStat"){
            with (edit) {
                this?.putString(getString(R.string.saved_DNDStrScore), statScore)
                this?.apply()
            }
            return ""
        } else if (thingToDo == "loadStat"){
            return defaultDNDStrScore.toString()
        } else {
            return ""
        }
        //val highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), dndStrScore)
    }

    var sharedPref: SharedPreferences? = null
    var edit: SharedPreferences.Editor? = null

    // ** [Main Code] **
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPref = getSharedPreferences("myDNDApp1",Context.MODE_PRIVATE)
        edit = sharedPref?.edit()
        defaultDNDStrScore = resources.getString(R.string.dDNDStrScore)

        lldndDiceRow1 = findViewById(R.id.ll_d20btnrow1)
        lldndDiceRow2 = findViewById(R.id.ll_d20btnrow2)
        lldndDiceRow3 = findViewById(R.id.ll_d20btnrow3)
        customDiceTil = findViewById(R.id.ll_CustomDice)

        //num of dice to roll
        buttonNumOfDiceSub = findViewById(R.id.btnNumOfDiceSub)
        tvNumOfDice = findViewById(R.id.tv_NumOfDiceToAdd)
        buttonNumOfDiceAdd = findViewById(R.id.btnNumOfDiceAdd)

        buttonNumOfDiceSub?.setOnClickListener{changeNumOfDice(false)}
        buttonNumOfDiceAdd?.setOnClickListener{changeNumOfDice(true)}

        //other modifier
        buttonOtherModSub = findViewById(R.id.btnOtherModiferSub)
        tvOtherMod = findViewById(R.id.tv_OtherModifer)
        buttonOtherModAdd = findViewById(R.id.btnOtherModiferAdd)

        buttonOtherModSub?.setOnClickListener{changeOtherModifier(false)}
        buttonOtherModAdd?.setOnClickListener{changeOtherModifier(true)}

        //mode
        buttonTabSingleRoll = findViewById(R.id.btnTabSR)
        buttonTabMultiRoll = findViewById(R.id.btnTabMR)
        buttonTabCustomRoll = findViewById(R.id.btnTabCR)

        modeSwitchHandler(modeSwitch)//default 1

        buttonTabSingleRoll?.setOnClickListener(){
            modeSwitch = 1
            modeSwitchHandler(modeSwitch)
        }

        buttonTabMultiRoll?.setOnClickListener(){
            modeSwitch = 2
            modeSwitchHandler(modeSwitch)
        }

        buttonTabCustomRoll?.setOnClickListener(){
            modeSwitch = 3
            modeSwitchHandler(modeSwitch)
        }

        //buttonShowHideEachRoll
        switchShowHideEachRoll = findViewById(R.id.swhShowHideEachRoll)
        switchShowHideEachRoll?.setOnCheckedChangeListener({_,isChecked ->
            isShowingRollHandler(isChecked)
        })

        //basicDice
        buttonD4 = findViewById(R.id.btnD4)
        buttonD6 = findViewById(R.id.btnD6)
        buttonD8 = findViewById(R.id.btnD8)
        buttonD10 = findViewById(R.id.btnD10)
        buttonD12 = findViewById(R.id.btnD12)
        buttonD20 = findViewById(R.id.btnD20)
        buttonD100 = findViewById(R.id.btnD100)

        buttonMultiDiceRoll = findViewById(R.id.btnMultDiceRoll)
        buttonDiceModeAdd = findViewById(R.id.multiModeSAdd)
        buttonDiceModeSubtract = findViewById(R.id.multiModeSubtract)
        buttonDiceModeClear = findViewById(R.id.diceRollModeClear)

        tvResult = findViewById<TextView>(R.id.tv_ResultView)
        tVRollingText = findViewById<TextView>(R.id.tv_RollingText)
        tVResultSum = findViewById<TextView>(R.id.tv_ResultViewSum)

        customDiceFocusListener()
        binding.btnRollValidCustom.setOnClickListener {submitCustomRoll()}

        buttonMultiDiceRoll?.setOnClickListener {diceMultiModeDone()}

        buttonDiceModeAdd?.setOnClickListener{diceRollModeAddSubSwitch()}
        buttonDiceModeSubtract?.setOnClickListener{diceRollModeAddSubSwitch()}
        buttonDiceModeClear?.setOnClickListener{diceRollModeClear()}

        buttonD4?.setOnClickListener {
            if(modeSwitch == 2){diceRollModeAddSub(0)
            }else{parseCustomText(
                d4.rollMultipleString(intNumOfDice))
            }
        }
        buttonD6?.setOnClickListener {
            if(modeSwitch == 2){diceRollModeAddSub(1)
            }else{parseCustomText(strNumOfDice+"6")}
        }
        buttonD8?.setOnClickListener {
            if(modeSwitch == 2){diceRollModeAddSub(2)
            }else{parseCustomText(strNumOfDice+"8")}
        }
        buttonD10?.setOnClickListener {
            if(modeSwitch == 2){diceRollModeAddSub(3)
            }else{parseCustomText(strNumOfDice+"10")}
        }
        buttonD12?.setOnClickListener {
            if(modeSwitch == 2){diceRollModeAddSub(4)
            }else{parseCustomText(strNumOfDice+"12")}
        }
        buttonD20?.setOnClickListener {
            if(modeSwitch == 2){diceRollModeAddSub(5)
            }else{parseCustomText(strNumOfDice+"20")}
        }
        buttonD100?.setOnClickListener {
            if(modeSwitch == 2){diceRollModeAddSub(6)
            }else{parseCustomText(strNumOfDice+"100")}
        }

        buttonD4?.setOnLongClickListener{   longClickHandler(0);true}
        buttonD6?.setOnLongClickListener{   longClickHandler(1);true}
        buttonD8?.setOnLongClickListener{   longClickHandler(2);true}
        buttonD10?.setOnLongClickListener{  longClickHandler(3);true}
        buttonD12?.setOnLongClickListener{  longClickHandler(4);true}
        buttonD20?.setOnLongClickListener{  longClickHandler(5);true}
        buttonD100?.setOnLongClickListener{ longClickHandler(6);true}
    }
    fun longClickHandler(myID: Int){
        if(modeSwitch == 2){
            modeMultiList[myID] = 0
            updateMultiDiceString()
        }
    }

}