/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package webkul.opencart.mobikul.gcm

import android.util.Log

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import com.google.firebase.messaging.FirebaseMessaging

import webkul.opencart.mobikul.credentials.GCMCredentials

class MyInstanceIDListenerService : FirebaseInstanceIdService() {

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This call is initiated by the
     * InstanceID provider.
     */
    // [START refresh_token]
    override fun onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "onTokenRefresh: " + FirebaseInstanceId.getInstance().id)
        Log.w(GCMCredentials.TAG, "onTokenRefresh: " + refreshedToken!!)
        sendRegistrationToServer(refreshedToken)
        subscribeTopics()
    }

    private fun sendRegistrationToServer(refreshedToken: String?) {}

    // [END refresh_token]
    private fun subscribeTopics() {
        val pubSub = FirebaseMessaging.getInstance()
        for (topic in GCMCredentials.TOPICS) {
            pubSub.subscribeToTopic(topic)
            Log.w(GCMCredentials.TAG, "subscribeTopic: $topic")
        }
    }

    companion object {

        private val TAG = "MyInstanceIDLS"
    }
    // [END refresh_token]
}
