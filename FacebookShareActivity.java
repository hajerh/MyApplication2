package com.pidev.esprit.pidevmobile;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

/**
 * Created by esprit on 25/11/2017.
 */

public class FacebookShareActivity {
    public ShareDialog shareDialog;

public void share(Activity a){
    shareDialog  = new ShareDialog(a);
    if (ShareDialog.canShow(ShareLinkContent.class)) {
        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                .build();
        shareDialog.show(linkContent);
    }
}

}
