package com.pankaj.imageclickandpick.image_pic_utils;

public interface Constants {

    String DEFAULT_FOLDER_NAME = "ClickAndPick";

    interface RequestCodes {
        int ClickAndPick_IDENTIFICATOR = 0b1101101100; //876
        int SOURCE_CHOOSER = 1 << 14;

        int PICK_PICTURE_FROM_DOCUMENTS = ClickAndPick_IDENTIFICATOR + (1 << 11);
        int PICK_PICTURE_FROM_GALLERY = ClickAndPick_IDENTIFICATOR + (1 << 12);
        int TAKE_PICTURE = ClickAndPick_IDENTIFICATOR + (1 << 13);
    }

    interface BundleKeys {
        String FOLDER_NAME = "com.pankaj.folder_name";
        String ALLOW_MULTIPLE = "com.pankaj.clickandpick.allow_multiple";
        String COPY_TAKEN_PHOTOS = "com.pankaj.clickandpick.copy_taken_photos";
        String COPY_PICKED_IMAGES = "com.pankaj.clickandpick.copy_picked_images";
    }
}