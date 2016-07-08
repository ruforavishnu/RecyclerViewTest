package com.efgh.recyclerviewtest;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private int REQ_CODE_PICK_SOUNDFILE = 11;
    private RecyclerView recyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MediaPlayer mp3Player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        loadPlaylist();

        // specify an adapter (see also next example)


    }
    private List<File> getListFiles(File parentDir)
    {
        ArrayList<File> inFiles = new ArrayList<File>();
        File[] files = parentDir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                inFiles.addAll(getListFiles(file));
            } else {
                if(file.getName().endsWith(".mp3"))
                {
                    Log.i("logtest","filepath:"+file.getPath());
                    inFiles.add(file);
                }
            }
        }
        return inFiles;
    }
    public void loadPlaylist()
    {
        try
        {



            List<File> mp3FilesList = new ArrayList<File>();
            String filePath = Environment.getExternalStorageDirectory().getPath();
            File file = new File(filePath);
            mp3FilesList = getListFiles(file);

            ArrayList<String> mp3FileNamesList = new ArrayList<String>();
            for(File f: mp3FilesList)
            {
                mp3FileNamesList.add(f.getPath());
            }

            mAdapter = new RecycleViewAdapter(mp3FileNamesList, getApplicationContext());
            recyclerView.setAdapter(mAdapter);

            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position)
                        {
                            Log.i("logtest", "recyclerview was  touched at position:" + position);

                            Log.i("logtest","view name:"+view.getClass().getName());
                            ViewGroup group = (ViewGroup)view;


                            for(int i = 0; i < group.getChildCount(); i++)
                            {
                                View childView = (View)group.getChildAt(i);
                                Log.i("logtest","view name:"+childView.getClass().getName());

                                if(childView.getClass().getName().endsWith("TextView"))
                                {

                                    if(childView.getTag()!=null)
                                    {
                                        if(mp3Player!=null && mp3Player.isPlaying())
                                        {
                                            mp3Player.stop();
                                            mp3Player = null;

                                        }
                                        Log.i("logtest", "tag:" + childView.getTag());
                                        MyTag tag = (MyTag)childView.getTag();
                                        Log.i("logtest", "tag:" + tag.getMp3FilePath());
                                        Uri songUri = Uri.parse(tag.getMp3FilePath());

                                        mp3Player= MediaPlayer.create(getApplicationContext(),songUri);
                                        mp3Player.start();


                                    }


                                }
                            }
                            // TODO Handle item click
                        }
                    })
            );

           /* recyclerView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event)
                {
                    int action = event.getAction();
                    if(action == MotionEvent.ACTION_DOWN)
                    {
                        Log.i("logtest","recyclerview was  touched");




                        ViewGroup group = (ViewGroup)v;
                        ViewGroup layoutGroup = (ViewGroup)group.getChildAt(0);

                        Log.i("logtest","ViewGroup name:"+layoutGroup.getClass().getName());




                    }
                    return false;
                }
            });*/



        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
