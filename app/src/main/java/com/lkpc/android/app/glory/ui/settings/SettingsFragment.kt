package com.lkpc.android.app.glory.ui.settings

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.lkpc.android.app.glory.R
import com.lkpc.android.app.glory.constants.Notification
import com.lkpc.android.app.glory.constants.SharedPreference

class SettingsFragment : PreferenceFragmentCompat(), Preference.OnPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val p1 = preferenceScreen.findPreference<SwitchPreferenceCompat>(
            SharedPreference.NOTIFICATION_TOPIC_GENERAL)
        p1!!.onPreferenceChangeListener = this

        val p2 = preferenceScreen.findPreference<SwitchPreferenceCompat>(
            SharedPreference.NOTIFICATION_TOPIC_URGENT)
        p2!!.onPreferenceChangeListener = this
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean {
        if (preference!!.key == SharedPreference.NOTIFICATION_TOPIC_GENERAL) {
            if (newValue as Boolean) {
                FirebaseMessaging.getInstance().subscribeToTopic(Notification.TOPIC_GENERAL)
            } else {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Notification.TOPIC_GENERAL)
            }
        }

        if (preference.key == SharedPreference.NOTIFICATION_TOPIC_URGENT) {
            if (newValue as Boolean) {
                FirebaseMessaging.getInstance().subscribeToTopic(Notification.TOPIC_URGENT)
            } else {
                FirebaseMessaging.getInstance().unsubscribeFromTopic(Notification.TOPIC_URGENT)
            }
        }

        return true
    }
}