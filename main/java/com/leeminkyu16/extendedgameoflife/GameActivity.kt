package com.leeminkyu16.extendedgameoflife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.button.MaterialButton
import java.util.*

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        this.supportActionBar?.hide()

        var timer = Timer()

        val canvasView = findViewById<CanvasView>(R.id.gameCanvasView)
        val startStopButton = findViewById<ToggleButton>(R.id.gameButtonStartStop)
        val cellTypeSpinner = findViewById<Spinner>(R.id.gameSpinnerCellType)
        val clearAllButton = findViewById<Button>(R.id.gameButtonClearAll)

        ArrayAdapter.createFromResource(
            this,
            R.array.cell_types,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cellTypeSpinner.adapter = adapter
        }
        cellTypeSpinner.setSelection(1)

        cellTypeSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                canvasView.tapMode = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        startStopButton.setOnCheckedChangeListener { _: CompoundButton, b: Boolean ->
            if (b) {
                timer.scheduleAtFixedRate(
                    object: TimerTask() {
                        override fun run() {
                            canvasView.doGridIteration()
                        }
                                        },
                    0,
                    500)
            }
            else {
                timer.cancel()
                timer.purge()

                timer = Timer()
            }
        }

        clearAllButton.setOnClickListener { canvasView.clearGrid() }
    }
}
