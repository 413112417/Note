package pers.xjh.note.ui.detail.android;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RemoteViews;

import pers.xjh.note.R;
import pers.xjh.note.runtime.Runtime;
import pers.xjh.note.ui.base.BaseActivity;
import pers.xjh.note.utils.Constant;

/**
 * Created by XJH on 2017/5/5.
 */

public class NotificationActivity extends BaseActivity implements View.OnClickListener {

    private RadioGroup mRadioGroup;

    @Override
    protected int initContentView() {
        return R.layout.activity_notification;
    }

    @Override
    protected void initView() {
        mRadioGroup = (RadioGroup) findViewById(R.id.radio_group);

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                sendBasicNotification();
                break;
            case R.id.btn_2:
                sendFoldNotification();
                break;
            case R.id.btn_3:
                sendHeadsupNotification();
                break;
        }
    }

    /**
     * 发送基本的通知
     */
    private void sendBasicNotification() {
        Notification.Builder builder = new Notification.Builder(this);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.baidu.com"));

        //PendingIntent会在点击的时候触发
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
        builder.setContentTitle("基本推送");
        builder.setContentText("我是基本推送");
        builder.setSubText("sub text");

        selectNotificationLevel(builder);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(Runtime.makeID(), builder.build());
    }

    /**
     * 折叠式的通知
     */
    private void sendFoldNotification() {
        Notification.Builder builder = new Notification.Builder(this);

        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(Constant.KEY_WEB_URL, "http://www.qq.com");

        //PendingIntent会在点击的时候触发
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));

        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.item_notification);
        contentView.setTextViewText(R.id.text_view, "展开看看");

        Notification notification = builder.build();
        notification.contentView = contentView;
        notification.bigContentView = new RemoteViews(getPackageName(), R.layout.item_notification_expanded);

        selectNotificationLevel(builder);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(Runtime.makeID(), notification);
    }

    /**
     * 悬挂式的通知
     */
    private void sendHeadsupNotification() {
        Notification.Builder builder = new Notification.Builder(this);

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(this, WebViewActivity.class);
        intent.putExtra(Constant.KEY_WEB_URL, "http://www.qq.com");

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setPriority(Notification.PRIORITY_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(Notification.CATEGORY_MESSAGE);
        }

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("悬挂式推送");
        builder.setContentText("我是悬挂式推送");
        builder.setAutoCancel(true);
        builder.setFullScreenIntent(pendingIntent, true);

        selectNotificationLevel(builder);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(Runtime.makeID(), builder.build());
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void selectNotificationLevel(Notification.Builder builder) {
        switch (mRadioGroup.getCheckedRadioButtonId()) {
            case R.id.rb_public:
                builder.setVisibility(Notification.VISIBILITY_PUBLIC);
                builder.setContentText("public");
                break;
            case R.id.rb_private:
                builder.setVisibility(Notification.VISIBILITY_PRIVATE);
                builder.setContentText("private");
                break;
            case R.id.rb_secret:
                builder.setVisibility(Notification.VISIBILITY_SECRET);
                builder.setContentText("secret");
                break;
            default:
                builder.setVisibility(Notification.VISIBILITY_PUBLIC);
                builder.setContentText("public");
                break;
        }
    }
}
