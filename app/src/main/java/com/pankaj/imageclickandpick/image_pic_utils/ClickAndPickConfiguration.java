package com.pankaj.imageclickandpick.image_pic_utils;

import android.content.Context;
import android.preference.PreferenceManager;


public class ClickAndPickConfiguration implements Constants {

    private Context context;

    ClickAndPickConfiguration(Context context) {
        this.context = context;
    }

    public ClickAndPickConfiguration setImagesFolderName(String folderName) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(BundleKeys.FOLDER_NAME, folderName)
                .apply();
        return this;
    }

    public ClickAndPickConfiguration setAllowMultiplePickInGallery(boolean allowMultiple) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(BundleKeys.ALLOW_MULTIPLE, allowMultiple)
                .apply();
        return this;
    }

    public ClickAndPickConfiguration setCopyTakenPhotosToPublicGalleryAppFolder(boolean copy) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(BundleKeys.COPY_TAKEN_PHOTOS, copy)
                .apply();
        return this;
    }

    public ClickAndPickConfiguration setCopyPickedImagesToPublicGalleryAppFolder(boolean copy) {
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putBoolean(BundleKeys.COPY_PICKED_IMAGES, copy)
                .apply();
        return this;
    }

    public String getFolderName() {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(BundleKeys.FOLDER_NAME, DEFAULT_FOLDER_NAME);
    }

    public boolean allowsMultiplePickingInGallery() {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(BundleKeys.ALLOW_MULTIPLE, false);
    }

    public boolean shouldCopyTakenPhotosToPublicGalleryAppFolder() {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(BundleKeys.COPY_TAKEN_PHOTOS, false);
    }

    public boolean shouldCopyPickedImagesToPublicGalleryAppFolder() {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(BundleKeys.COPY_PICKED_IMAGES, false);
    }

}