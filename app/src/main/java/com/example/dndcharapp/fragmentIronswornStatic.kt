package com.example.dndcharapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val dataEdge = "1"
private const val dataHeart = "2"
private const val dataIron = "3"
private const val dataShadow = "1"
private const val dataWits = "2"
private const val dataHealth = "+5"
private const val dataSpirit = "+5"
private const val dataSupply = "+5"


class fragmentIronswornStatic : Fragment() {
    private var dataCLEdge = dataStatMain(dataEdge)
    private var dataCLHeart = dataStatMain(dataHeart)
    private var dataCLIron = dataStatMain(dataIron)
    private var dataCLShadow = dataStatMain(dataShadow)
    private var dataCLWits = dataStatMain(dataWits)

    private var getAllStats:MutableList<dataStatMain> = mutableListOf(dataCLEdge,dataCLHeart,dataCLIron,dataCLShadow,dataCLWits)

    private var ldEdge: String? = null
    private var ldHeart: String? = null
    private var ldIron: String? = null
    private var ldShadow: String? = null
    private var ldWits: String? = null
    private var ldHealth: String? = null
    private var ldSpirit: String? = null
    private var ldSupply:String? = null

    private fun handleHighlight(statEffected:dataStatMain){

        getAllStats.forEach(){ it ->
            if (it.getStatName() == statEffected.getStatName()){
                statEffected.handleStatHL()
            } else{
                it.clearHighLight()
            }
        }
        (activity as MainActivity).isScoreHighlighted(statEffected.getIsHLed(),statEffected.getStatName(),statEffected.getStatValue())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ldEdge = it.getString(dataEdge)
            ldHeart = it.getString(dataHeart)
            ldIron = it.getString(dataIron)
            ldShadow = it.getString(dataShadow)
            ldWits = it.getString(dataWits)
            ldHealth = it.getString(dataHealth)
            ldSpirit = it.getString(dataSpirit)
            ldSupply = it.getString(dataSupply)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ironsworn_static, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        var highlightedColor = resources.getColor(R.color.highlightColor)
        var standardColor = resources.getColor(R.color.normalTextColor)

        dataCLEdge.setViews(view.findViewById(R.id.tv_StatsName_Edge),view.findViewById(R.id.tv_StatsValue_Edge),view.findViewById(R.id.btnUseEdgeMod),highlightedColor,standardColor)
        dataCLHeart.setViews(view.findViewById(R.id.tv_StatsName_Heart),view.findViewById(R.id.tv_StatsValue_Heart),view.findViewById(R.id.btnUseHeartMod),highlightedColor,standardColor)
        dataCLIron.setViews(view.findViewById(R.id.tv_StatsName_Iron),view.findViewById(R.id.tv_StatsValue_Iron),view.findViewById(R.id.btnUseIronMod),highlightedColor,standardColor)
        dataCLShadow.setViews(view.findViewById(R.id.tv_StatsName_Shadow),view.findViewById(R.id.tv_StatsValue_Shadow),view.findViewById(R.id.btnUseShadowMod),highlightedColor,standardColor)
        dataCLWits.setViews(view.findViewById(R.id.tv_StatsName_Wits),view.findViewById(R.id.tv_StatsValue_Wits),view.findViewById(R.id.btnUseWitsMod),highlightedColor,standardColor)

        val tmpEdgeButton:Button = view.findViewById(R.id.btnUseEdgeMod)
        val tmpHeartButton:Button = view.findViewById(R.id.btnUseHeartMod)
        val tmpIronButton:Button = view.findViewById(R.id.btnUseIronMod)
        val tmpShadowButton:Button = view.findViewById(R.id.btnUseShadowMod)
        val tmpWitsButton:Button = view.findViewById(R.id.btnUseWitsMod)

        tmpEdgeButton.setOnClickListener(){handleHighlight(dataCLEdge)}
        tmpHeartButton.setOnClickListener(){handleHighlight(dataCLHeart)}
        tmpIronButton.setOnClickListener(){handleHighlight(dataCLIron)}
        tmpShadowButton.setOnClickListener(){handleHighlight(dataCLShadow)}
        tmpWitsButton.setOnClickListener(){handleHighlight(dataCLWits)}

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment fragmentIronswornStatic.
         */

        @JvmStatic
        fun newInstance(ldEdge: String, ldHeart: String, ldIron: String, ldShadow: String, ldWits: String, ldHealth: String, ldSpirit:String, ldSupply:String):fragmentIronswornStatic {
            val newFragment = fragmentIronswornStatic()
            val args = Bundle().apply {
                putString(dataEdge, ldEdge)
                putString(dataHeart, ldHeart)
                putString(dataIron, ldIron)
                putString(dataShadow, ldShadow)
                putString(dataWits, ldWits)
                putString(dataHealth, ldHealth)
                putString(dataSpirit, ldSpirit)
                putString(dataSupply, ldSupply)
            }
            newFragment.arguments = args
            return newFragment
        }

    }
}