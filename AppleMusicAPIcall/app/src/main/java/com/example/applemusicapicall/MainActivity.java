package com.example.applemusicapicall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.SearchView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private APIService apiService;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private List<Result> result;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        //replace the default App bar with  toolbar
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarr);
//        setSupportActionBar(toolbar);

        searchView =(SearchView)findViewById(R.id.searchView);

        recyclerView =(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        Retrofit retrofit= RetrofitCLient.setRetrofit();
        apiService= retrofit.create(APIService.class);
        fetchAllMusic();


        //query music by artistName on user search
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length() >= 1){
                    searchbyArtistName(newText);

                }
                //else fetch all music again if empty
                fetchAllMusic();
                return false;
            }
        });



    }

    private void openMusicPlayer(Music music ){
        myAdapter.setListener(new MyAdapter.Listener() {
            @Override
            public void getItemClickPosition(int position) {

                Intent intent = new Intent(MainActivity.this,MusicPlayer.class);
                Bundle bundle=new Bundle();

                //List of music result
                result = music.results;
                //get the single object position from click on recycler view
                Result artistObject = result.get(position);
                bundle.putString(MusicPlayer.ArtistName,artistObject.artistName);
                bundle.putString(MusicPlayer.songName,artistObject.trackName);
                bundle.putString(MusicPlayer.MUSIC_URL,artistObject.previewUrl);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    private void fetchAllMusic(){

        //make the network Api call
        Call<Music> call= apiService.getAllMusic();
        call.enqueue(new Callback<Music>() {
            @Override
            public void onResponse(Call<Music> call, Response<Music> response) {
                if(!response.isSuccessful()){

                    Log.d("TAG", "CODE: "+response.code());
                    //textResult.setText("CODE: "+response.code());
                    //return will leave this method here if response was not Successful instead of returning
                    //a null exception
                    return;
                }

                Music music = response.body();
                //List of results music
                result = music.results;
                // pass the result list to the Adapter
                myAdapter = new MyAdapter(result,MainActivity.this);
                recyclerView.setAdapter(myAdapter);
                //on item click open Music player
                openMusicPlayer(music);



            }

            @Override
            public void onFailure(Call<Music> call, Throwable t) {

                Log.d("TAG", t.getMessage());

            }
        });
    }

    private void searchbyArtistName(String query){

        Call<Music> call= apiService.searchbyArtistName(query);
        call.enqueue(new Callback<Music>() {
            @Override
            public void onResponse(Call<Music> call, Response<Music> response) {
                if(!response.isSuccessful()){

                    Log.d("TAG", "CODE: "+response.code());
                    //textResult.setText("CODE: "+response.code());
                    //return will leave this method here if response was not Successful instead of returning
                    //a null exception
                    return;
                }

                Music music = response.body();
                result = music.results;
                // pass the result list to the Adapter
                myAdapter = new MyAdapter(result,MainActivity.this);
                recyclerView.setAdapter(myAdapter);

            }

            @Override
            public void onFailure(Call<Music> call, Throwable t) {

                Log.d("TAG", t.getMessage());

            }
        });

    }

















//    //onCreateOptionsMenu will override the App bar
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.app_bar_menu,menu);
//        return true;
//    }
}