package com.example.document.apis_json_volly.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.document.apis_json_volly.R;
import com.example.document.apis_json_volly.adapters.Recycler_view_adapter;
import com.example.document.apis_json_volly.models.Anime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String Json_Url = "https://gist.githubusercontent.com/aws1994/f583d54e5af8e56173492d3f60dd5ebf/raw/c7796ba51d5a0d37fc756cf0fd14e54434c547bc/anime.json";
    private  JsonArrayRequest jsonArrayRequest;
    private RequestQueue requestQueue;
    private List <Anime> lst_anime;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        lst_anime = new ArrayList<>();


        jsonrequest();
    }

    private void jsonrequest() {
        jsonArrayRequest = new JsonArrayRequest(Json_Url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;

             for (int i = 0 ; i<response.length() ; i++){

                 try{
                     jsonObject = response.getJSONObject(i);
                     Anime anime = new Anime();
                     anime.setName(jsonObject.getString("name"));
                     anime.setRating(jsonObject.getString("Rating"));
                     anime.setDescription(jsonObject.getString("description"));
                     anime.setImage_url(jsonObject.getString("img"));
                     anime.setStudio(jsonObject.getString("studio"));
                     anime.setCategorie(jsonObject.getString("categorie"));
                     //Toast.makeText(MainActivity.this,anime.toString(),Toast.LENGTH_SHORT).show();
                     lst_anime.add(anime);

                 } catch (JSONException e) {
                     e.printStackTrace();
                 }
             }
             setuprecyclerview(lst_anime);

            }

            private void setuprecyclerview(List<Anime> lst_anime) {
                Recycler_view_adapter myAdapter = new Recycler_view_adapter(MainActivity.this,lst_anime);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(myAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonArrayRequest);
    }
}

//اووووووووو ... كم كان سهلاً هذا الابليكيشن :))))))
