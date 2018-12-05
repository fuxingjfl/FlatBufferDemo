package zsx.hldkmj.fy.com.flatbufferdemo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/*   数据格式
{
        id:5;
        name:"张三";
        age:20;
        occupation:"IT明宫"
        friend:[
        {
            id:7;
            relationship:"同事";
        }
        ]

        }*/

public class MainActivity extends AppCompatActivity {


    private static String TAG="TAG";
    private static int MY_PERMISSION_REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean isPermission = checkSelfPermissionAll(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE});
        if (isPermission) {
//            Toast.makeText(MainActivity.this, "正在查看!", Toast.LENGTH_SHORT).show();

        }else{
            //        请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST_CODE);

        }

    }

    public void output(View v)  {

        FlatBufferBuilder builder= new FlatBufferBuilder();

        int id1 = builder.createString("同事");
        int friendid1 = Friend.createFriend(builder, 5, id1);

        int id2 = builder.createString("朋友");
        int friendid2 =Friend.createFriend(builder,6,id2);

        int id3 = builder.createString("女朋友");
        int friendid3 =Friend.createFriend(builder,7,id3);

        int nameid = builder.createString("张世超");
        int zyid = builder.createString("IT职业");
        int[] fri= {friendid1,friendid2,friendid3};
        int friendsVector = People.createFriendsVector(builder, fri);
        People.startPeople(builder);
        People.addPid(builder,100);
        People.addAge(builder,26);
        People.addName(builder,nameid);
        People.addOccupation(builder,zyid);
        People.addFriends(builder,friendsVector);
        int endid = People.endPeople(builder);
        People.finishPeopleBuffer(builder,endid);

        //=================序列化======================
        File file = new File(Environment.getExternalStorageDirectory(),"data.txt");
        FileOutputStream fos=null;
        FileChannel channel=null;
        try {

            if (file.exists()){
                file.delete();
            }
            ByteBuffer data = builder.dataBuffer();
            fos = new FileOutputStream(file);

            channel= fos.getChannel();

            while (data.hasRemaining()){
                channel.write(data);
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (channel!=null){
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos!=null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }


//        ==================反序列化=======================
        FileInputStream fis=null;
        try {
            fis=new FileInputStream(file);
            ByteBuffer data = ByteBuffer.allocate(1024);
            channel= fis.getChannel();
            int readBytes = 0;
            while ((readBytes=channel.read(data))!=-1);
            //把指针回到最初的状态，准备从byteBuffer当中读取数据
            data.flip();
            //解析出二进制为Items对象。
            People rootAsPeople = People.getRootAsPeople(data);
            //读取数据测试看看是否跟保存的一致
            Log.i(TAG,"People.name:"+rootAsPeople.name());
            Log.i(TAG,"People.id:"+rootAsPeople.pid());
            for (int i=0;i<rootAsPeople.friendsLength();i++){
                Friend friends = rootAsPeople.friends(i);
                Log.i(TAG,"friends.relationship:"+friends.relationship());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //    检查是否拥有指定的所有权限
    private boolean checkSelfPermissionAll(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_PERMISSION_REQUEST_CODE) {
            boolean isPermission = true;
            for (int grant : grantResults) {
                // 判断是否所有的权限都已经授予了
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isPermission = false;
                    break;
                }
            }
            if (isPermission) {
//                Toast.makeText(BaseActivity.this, "我看看", Toast.LENGTH_SHORT).show();
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("备份通讯录需要访问")
                        .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent=new Intent();
                                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.addCategory(Intent.CATEGORY_DEFAULT);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("退出",null);
                builder.show();
            }
        }
    }

}
