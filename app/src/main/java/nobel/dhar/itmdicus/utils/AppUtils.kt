package nobel.dhar.itmdicus.utils

import android.app.Activity
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.CountDownTimer
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import com.google.android.material.snackbar.Snackbar
import nobel.dhar.itmdicus.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


object AppUtils {

    lateinit var progressDialog: ProgressDialog

    fun showKeyboard(activity: Activity, editText: EditText) {
        try {
            editText.requestFocus()
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.toggleSoftInput(
                InputMethodManager.SHOW_FORCED,
                InputMethodManager.HIDE_IMPLICIT_ONLY
            )
        } catch (e: Exception) {
            Log.d("myException", e.toString())
        }
    }

    fun hideKeyboard(activity: Activity) {
        val view = activity.findViewById<View>(android.R.id.content)
        try {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        } catch (e: Exception) {
            Log.d("myException", e.toString())
        }
    }


     fun showSuccessSnackBar(success: String, activity: Activity) {
        val snackbar: Snackbar = Snackbar.make(activity.findViewById(android.R.id.content), success, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(activity.resources.getColor(R.color.orange))
        snackbar.show()
    }

     fun showErrorSnackbar(error: String, activity: Activity) {
        val snackbar: Snackbar = Snackbar.make(activity.findViewById(android.R.id.content), error, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(Color.RED)
        snackbar.show()
    }



}