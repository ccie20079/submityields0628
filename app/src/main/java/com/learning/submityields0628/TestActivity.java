package com.learning.submityields0628;
import com.learning.utils.BaseActivity;
import com.learning.utils.DownLoadUtil;
import com.learning.utils.MyApplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class TestActivity extends BaseActivity {
    /*
    Button lookdoc;
    TextView tv;
    String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        lookdoc = (Button) findViewById(R.id.lookdoc);
        lookdoc.setOnClickListener(new lookdoc_onClickListenerImpl());
    }

    private class lookdoc_onClickListenerImpl implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            DownLoadUtil d = new DownLoadUtil();
            //获取文件的类型
            String filetype = filename.substring(filename.lastIndexOf(".") + 1, filename.length());
            if ("jpg".equals(filetype) || "jpeg".equals(filetype) || "bmp".equals(filetype) || "gif".equals(filetype) || "png".equals(filetype)) {
                Toast.makeText(MyApplication.getContext(), "文件类型错误", Toast.LENGTH_LONG).show();
            }
            else
            {
                //检测是否安装了wps软件，没有安装则去下载
                if (isAvilible(MyApplication.getContext(), "cn.wps.moffice_eng")) {
                    //先查看本地是否存在此文件。存在就立即访问。否则再去下载
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory()
                            + "/";
                    String sdcard2 = sdpath + "download/" + DownLoadUtil.urldecodeUTF8(filename).substring(DownLoadUtil.urldecodeUTF8(filename).lastIndexOf("/") + 1);
                    File file = new File(sdcard2);
                    // 判断文件目录是否存在
                    if (file.exists()) {
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();
                        bundle.putString("OpenMode", "ReadOnly");// 只读模式
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setClassName("cn.wps.moffice_eng",
                                "cn.wps.moffice.documentmanager.PreStartActivity2");
                        Uri uri = Uri.fromFile(new File(sdcard2));
                        Log.e("wps访问的uri", uri + "");
                        intent.setData(uri);
                        intent.putExtras(bundle);
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    } else {
                        //返回真实路径
                        String mSavePath = d.downloadFile(filename,"");
                        if (mSavePath != "") {
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("OpenMode", "ReadOnly");// 只读模式
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setClassName("cn.wps.moffice_eng",
                                    "cn.wps.moffice.documentmanager.PreStartActivity2");
                            Uri uri = Uri.fromFile(new File(mSavePath + DownLoadUtil.urldecodeUTF8(filename.substring(filename.lastIndexOf("/") + 1, filename.length()))));
                            Log.e("wps访问的uri", uri + "");
                            intent.setData(uri);
                            intent.putExtras(bundle);
                            try {
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                            }
                        } else {
                            mDownloadDialog.show();
                            Toast.makeText(PreceptInfoActivity.this, "下载中", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    // 从市场上下载
                    Uri uri = Uri.parse("market://details?id=cn.wps.moffice_eng");
                    // 直接从指定网址下载
                    Intent it = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(it);
                }
        }

        private boolean isAvilible( Context context, String packageName )
        {
            final PackageManager packageManager = context.getPackageManager();
            // 获取所有已安装程序的包信息
            List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
            for ( int i = 0; i < pinfo.size(); i++ )
            {
                if(pinfo.get(i).packageName.equalsIgnoreCase(packageName))
                    return true;
            }
            return false;
        }

     */
}