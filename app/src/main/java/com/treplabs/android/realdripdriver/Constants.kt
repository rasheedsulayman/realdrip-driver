package com.treplabs.android.realdripdriver

object Constants {

    object APIDataKeys {
        const val HOSPITAL_ADMIN = "hospital_admin"
        const val NURSE = "nurse_user"
        const val ACTIVE = "active"
        const val ENDED = "ended"
        const val TERMINATED = "terminated"
        const val WARD_ID = "wardId"
        const val HOSPITAL_ID = "hospitalId"
        const val DEVICE_ID = "deviceId"
        const val NURSE_ID = "nurseId"
    }

    object FireStorePaths {
        const val DEVICES = "devices"
        const val USERS = "users"
        const val SETTINGS = "settings"
        const val NOTIFICATION_TOKEN = "notificationToken"
    }

    object ImageUpload {
        const val IMAGE_UPLOAD_MEDIA_TYPE = "application/octet-stream"
    }

    object Misc {
        const val AGENT_LINK = "Agent Link"
        const val DEFAULT_FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        const val DEFAULT_PHOTO_EXTENSION = ".jpg"
        const val FILE_PROVIDER_AUTHORITY = "${BuildConfig.APPLICATION_ID}.fileprovider"
    }

}
