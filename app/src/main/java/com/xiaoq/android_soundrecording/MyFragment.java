package com.xiaoq.android_soundrecording;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Ding.pengqiang
 * on 2017/4/10.
 */

public class MyFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    public ListView mListView;
    public List<String> items = null;//存放名称
    private List<String> paths = null;//存放路径
    private String rootPath = "/record";
    private String filePath = Environment.getExternalStorageDirectory()+"/record/";
    private SecondActivity activity;
    public ArrayAdapter<String> adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_my, container, false);
        mListView = ((ListView) inflate.findViewById(R.id.list));
        getData();
        initView();
        return inflate;
    }

    private void getData() {

//        Bundle arguments = getArguments();
//
//        String filePath = arguments.getString("filePath");
//        if (filePath ==null){
//            return;
//        }
        items = new ArrayList<String>();
        paths = new ArrayList<String>();
        File f = new File(filePath);
        // 列出所有文件
        File[] files = f.listFiles();
        // 如果不是根目录,则列出返回根目录和上一目录选项
//        if (!filePath.equals(rootPath)) {
//            items.add("返回根目录");
//            paths.add(rootPath);
//            items.add("返回上一层目录");
//            paths.add(f.getParent());
//        }
        // 将所有文件存入list中
        if(files != null){
            int count = files.length;// 文件个数
            for (int i = 0; i < count; i++) {
                File file = files[i];

                items.add(file.getName());
                paths.add(file.getPath());
            }
        }
        //设置数据为倒叙
        Collections.reverse(items);

    }

    private void initView() {

        adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, items);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        mListView.setOnItemLongClickListener(this);
        mListView.setSelection(9);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        /**
         * 播放音频是播放的路径
         */
        Toast.makeText(activity, ""+position, Toast.LENGTH_SHORT).show();
        String s = paths.get(position);
        MediaPlayer mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(s);
            mediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (SecondActivity) context;


    }

    @Override
    public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提示").setMessage("确认要删除吗？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String s = paths.get(position);
                        File file = new File(s);
                        file.delete();
                        items.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();

        return false;
    }
}
