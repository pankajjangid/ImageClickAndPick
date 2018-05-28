package com.pankaj.imageclickandpick.image_pic_utils;

public abstract class DefaultCallback implements ClickAndPick.Callbacks {

    @Override
    public void onImagePickerError(Exception e, ClickAndPick.ImageSource source, int type) {
    }

    @Override
    public void onCanceled(ClickAndPick.ImageSource source, int type) {
    }
}