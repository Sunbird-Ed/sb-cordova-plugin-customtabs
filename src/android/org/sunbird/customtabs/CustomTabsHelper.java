package org.sunbird.customtabs;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import androidx.browser.customtabs.CustomTabsCallback; 
import androidx.browser.customtabs.CustomTabsClient; 
import androidx.browser.customtabs.CustomTabsIntent; 
import androidx.browser.customtabs.CustomTabsServiceConnection; 
public class CustomTabsHelper {

  private static final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";

  private CustomTabsClient mCustomTabsClient;

  private CustomTabsServiceConnection connection = new CustomTabsServiceConnection() {
    @Override
    public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
      mCustomTabsClient = client;
      mCustomTabsClient.warmup(0);
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
  };

  public boolean initCustomTabs(Activity activity) {
    return CustomTabsClient.bindCustomTabsService(activity, CUSTOM_TAB_PACKAGE_NAME, connection);
  }

  public void launchUrl(String url, Activity activity) {
    CustomTabsIntent.Builder mBuilder = new CustomTabsIntent.Builder(mCustomTabsClient.newSession(new CustomTabsCallback()));
    CustomTabsIntent mIntent = mBuilder.build();
    mIntent.intent.setPackage("com.android.chrome");
    mIntent.intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
    mIntent.intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    mIntent.launchUrl(activity, Uri.parse(url));
  }

}
