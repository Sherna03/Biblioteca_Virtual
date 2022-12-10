package com.lostdream.bibliotecavirtual;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class DescargaCompleta extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)){
            Toast.makeText(context, "Descarga completa, salga y vuelva a entrar...", Toast.LENGTH_SHORT).show();
        }
    }
}
