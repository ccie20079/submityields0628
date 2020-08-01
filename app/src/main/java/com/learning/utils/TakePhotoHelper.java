package com.learning.utils;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.learning.submityields0628.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Package_name:   com.learning.utils
 * user:           Administrator
 * date:           2020/7/26
 * email:          ccie20079@126.com
 */

/**
 * 拍照类
 */
public class TakePhotoHelper extends BaseActivity {
    private static final int TAKE_PHONE =1 ;
    private Uri imageUri;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        startToTakePhoto();
    }

    public  void startToTakePhoto(){
        //创建File对象，用于存储拍照后的图片
        File outputImage = new File(getExternalCacheDir(),"output_image.jpg");
        try{
            if(outputImage.exists()) outputImage.delete();
            outputImage.createNewFile();
        }catch(Exception ex){
            ex.printStackTrace();
        }
        if(Build.VERSION.SDK_INT>=24){
            imageUri = FileProvider.getUriForFile(this,"com.learning.submityields0628.fileprovider",outputImage);
        }else{
            imageUri = Uri.fromFile(outputImage);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHONE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case TAKE_PHONE:
                if(resultCode==RESULT_OK){
                    ImageView picture = new ImageView(this);
                    MyPicture myPicture = new MyPicture(picture);
                    try{
                        //将拍摄的照片显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    Intent intent = new Intent();
                    intent.putExtra("picture",myPicture);
                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
            default:
                break;
        }
    }
}
