package nobel.dhar.itmdicus.utils

import android.content.SharedPreferences
import nobel.dhar.itmdicus.data.remote.responses.registerresponse.Success
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SharedPrefsHelper @Inject constructor(private val sharedPreferences: SharedPreferences) {


    private val USER_EXPIRE_DATE = "spliff_expire_date"
    private val USER_NAME = "spliff_user_name"
    private val USER_KEY_ACCESS_TOKEN = "spliff_user_access_token"


    private fun delete(key: String?) {
        if (sharedPreferences.contains(key)) {
            getEditor().remove(key).commit()
        }
    }

    private fun save(key: String?, value: Any?) {
        val editor = getEditor()
        if (value is Boolean) {
            editor.putBoolean(key, (value as Boolean?)!!)
        } else if (value is Int) {
            editor.putInt(key, (value as Int?)!!)
        } else if (value is Float) {
            editor.putFloat(key, (value as Float?)!!)
        } else if (value is Long) {
            editor.putLong(key, (value as Long?)!!)
        } else if (value is String) {
            editor.putString(key, value as String?)
        } else if (value is Enum<*>) {
            editor.putString(key, value.toString())
        } else if (value != null) {
            throw RuntimeException("Attempting to save non-supported preference")
        }
        editor.commit()
    }

    private fun getEditor(): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }

    operator fun <T> get(key: String?): T? {
        return sharedPreferences.all[key] as T?
    }

    operator fun <T> get(key: String?, defValue: T): T {
        val returnValue = sharedPreferences.all[key] as T?
        return returnValue ?: defValue
    }

    private fun has(key: String?): Boolean {
        return sharedPreferences.contains(key)
    }



    /**
     * Function to fetch auth token
     */

    fun fetchAuthToken(): String? {
        return sharedPreferences.getString(USER_KEY_ACCESS_TOKEN, null)
    }

    fun getExpiryDate(): String? {
        return sharedPreferences.getString(USER_EXPIRE_DATE, null)
    }



    fun hasUser(): Boolean {
        return has(USER_KEY_ACCESS_TOKEN)
    }

    fun clearAllData() {
        val editor = getEditor()
        editor.clear().commit()
    }



    fun saveUser(success: Success) {
        save(USER_NAME, success.name)
        save(USER_KEY_ACCESS_TOKEN, success.token)
        save(USER_EXPIRE_DATE, success.expires_at?.date)
    }


}