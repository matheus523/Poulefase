package com.miniclip.poulefase.ui.pool

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.miniclip.poulefase.R
import com.miniclip.poulefase.data.model.Pool
import com.miniclip.poulefase.data.model.PoolLine
import com.miniclip.poulefase.ui.home.HomeViewModel
import com.miniclip.poulefase.utilities.Constants

class PoolViewAdapter (private val pools: List<Pool>, private val context: Context,
                       private val viewModel: HomeViewModel): RecyclerView.Adapter<PoolViewAdapter.PoolViewHolder>() {

    private val textSizeTableRow = 12f
    private val textSizeTableHeader = 12f
    private val bottomMargin = 1
    private val topPadding = 10
    private val bottomPadding = 10
    private val defaultPadding = 0
    private val defaultWeight = 1f

    class PoolViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val poolTitle: TextView = itemView.findViewById(R.id.poolTableTitle)
        val poolTable: TableLayout = itemView.findViewById(R.id.poolTableLayout)
        val gameTextView: TextView = itemView.findViewById(R.id.gameTextView)
        val playGameButton: Button = itemView.findViewById(R.id.playGameButton)
        val infoImageView: ImageView = itemView.findViewById(R.id.infoImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoolViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pool_item,
            parent, false)
        return PoolViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PoolViewHolder, position: Int) {
        val currentItem = pools[position]

        holder.poolTitle.text = currentItem.poolName
        holder.poolTable.setBackgroundColor(ContextCompat.getColor(context,R.color.white))
        makePoolTableHeader(holder.poolTable)
        makePoolTableBody(holder.poolTable, currentItem.poolLines)
        showNextGame(currentItem, holder.gameTextView, holder.playGameButton)

        holder.playGameButton.setOnClickListener {
            viewModel.playGame(currentItem)
        }

        // Show dialog with the explanation of abbreviations in the pools
        holder.infoImageView.setOnClickListener {
            val builder: AlertDialog.Builder = context.let {
                AlertDialog.Builder(it, R.style.AlertDialog)
            }
            val items = arrayOf("${context.getString(R.string.s)}: ${context.getString(R.string.strength)}",
                "${context.getString(R.string.pg)}: ${context.getString(R.string.played_games)}",
                "${context.getString(R.string.w)}: ${context.getString(R.string.wins)}",
                "${context.getString(R.string.d)}: ${context.getString(R.string.draw)}",
                "${context.getString(R.string.l)}: ${context.getString(R.string.losses)}",
                "${context.getString(R.string.gf)}: ${context.getString(R.string.goals_for)}",
                "${context.getString(R.string.ga)}: ${context.getString(R.string.goals_against)}",
                "${context.getString(R.string.p)}: ${context.getString(R.string.points)}" )

            builder.setItems(items) { _, _ -> }
                .setTitle(R.string.explanation_pool)
                .setPositiveButton(R.string.oke){
                        _, _ ->  // User clicked OK button
                }

            val dialog: AlertDialog = builder.create()
            dialog.window?.decorView?.setBackgroundResource(R.drawable.component_bg)
            dialog.show()
        }
    }

    override fun getItemCount(): Int { return pools.size }

    private fun showNextGame(pool: Pool,gameTextView: TextView, playGameButton: Button ){
        var text: String = ""
        if(!pool.hasNextGame()){
            text = context.getString(R.string.no_game)
            gameTextView.text = text
            playGameButton.isClickable = false
            playGameButton.isEnabled = false
        }else{
            text = "${pool.getTeam1().name} ${context.getString(R.string.vs)} ${pool.getTeam2().name}"
            gameTextView.text = text
        }
    }

    private fun makePoolTableHeader(tableLayout: TableLayout){
        val headerRow = TableRow(context)
        headerRow.setBackgroundColor(ContextCompat.getColor(context,R.color.component_bg))
        headerRow.setPadding(defaultPadding,topPadding,defaultPadding,bottomPadding)

        headerRow.addView(createTextView(context.getString(R.string.teams), textSizeTableHeader))
        headerRow.addView(createTextView(context.getString(R.string.s), textSizeTableHeader))
        headerRow.addView(createTextView(context.getString(R.string.pg), textSizeTableHeader))
        headerRow.addView(createTextView(context.getString(R.string.w), textSizeTableHeader))
        headerRow.addView( createTextView(context.getString(R.string.d),textSizeTableHeader))
        headerRow.addView( createTextView(context.getString(R.string.l),textSizeTableHeader))
        headerRow.addView( createTextView(context.getString(R.string.gf),textSizeTableHeader))
        headerRow.addView( createTextView(context.getString(R.string.ga),textSizeTableHeader))
        headerRow.addView( createTextView(context.getString(R.string.p),textSizeTableHeader))

        tableLayout.addView(headerRow)
    }

    private fun makePoolTableBody(tableLayout: TableLayout, poolLines: List<PoolLine>){
        for (poolLine in poolLines){
            val tableRow = TableRow(context)
            val amountGames = poolLine.wins + poolLine.draw + poolLine.losses
            val teamStrength = Constants.ONE_DECIMAL.format(poolLine.team.strength)

            tableRow.layoutParams = getTableLayoutParams()
            tableRow.setBackgroundColor(ContextCompat.getColor(context,R.color.component_bg))
            tableRow.setPadding(defaultPadding,topPadding,defaultPadding,bottomPadding)
            tableRow.addView(createTextView(poolLine.team.name, textSizeTableRow))
            tableRow.addView(createTextView(teamStrength, textSizeTableRow))
            tableRow.addView(createTextView(amountGames.toString(), textSizeTableRow))
            tableRow.addView(createTextView(poolLine.wins.toString(), textSizeTableRow))
            tableRow.addView(createTextView(poolLine.draw.toString(), textSizeTableRow))
            tableRow.addView(createTextView(poolLine.losses.toString(), textSizeTableRow))
            tableRow.addView(createTextView(poolLine.goalsFor.toString(), textSizeTableRow))
            tableRow.addView(createTextView(poolLine.goalsAgainst.toString(), textSizeTableRow))
            tableRow.addView(createTextView(poolLine.points.toString(), textSizeTableRow))
            tableLayout.addView(tableRow)
        }
    }

    private fun createTextView(text: String, textSize: Float, weight: Float = defaultWeight): TextView {
        val textView = TextView(context)

        textView.layoutParams =  getTableRowLayoutParams(weight)
        textView.setTextColor(ContextCompat.getColor(context, R.color.white))
        textView.text = text
        textView.textSize = textSize
        return textView
    }

    private fun getTableLayoutParams(): TableLayout.LayoutParams {
        val params = TableLayout.LayoutParams(
            TableLayout.LayoutParams.WRAP_CONTENT,
            TableLayout.LayoutParams.WRAP_CONTENT
        )
        params.bottomMargin = bottomMargin
        return params
    }

    private fun getTableRowLayoutParams(weight: Float) : TableRow.LayoutParams{
        val params = TableRow.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT)
        params.weight = weight
        return params
    }
}