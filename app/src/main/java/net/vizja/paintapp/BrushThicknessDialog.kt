package net.vizja.paintapp

import android.R
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class BrushThicknessDialog(private val setBrushThickness:(String) -> Unit) : DialogFragment(), DialogInterface.OnClickListener {

    var data = arrayOf("1", "2", "3")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val adb: AlertDialog.Builder = AlertDialog.Builder(context!!)
            .setTitle("Choose brush thickness").setPositiveButton(R.string.ok, this)
        val adapter: ArrayAdapter<String> = ArrayAdapter(
            context!!, R.layout.select_dialog_singlechoice, data
        )
        adb.setSingleChoiceItems(adapter, -1, this)
        return adb.create()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        val listView = (dialog as AlertDialog).listView
        if (which == Dialog.BUTTON_POSITIVE){
            val choice = listView.checkedItemPosition
            setBrushThickness(data[choice])
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
    }
}