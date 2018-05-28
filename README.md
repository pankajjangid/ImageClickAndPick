# ImageClickAndPick
ImageClickAndPick allows you to easily capture images from the gallery, camera or documents without creating lots of boilerplate.

#Runtime permissions
This library requires specific runtime permissions. Declare it in your AndroidMnifest.xml:

<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />

Please note: for devices running API 23 (marshmallow) you have to request this permission in the runtime, before calling ImageClickAndPick.openCamera().