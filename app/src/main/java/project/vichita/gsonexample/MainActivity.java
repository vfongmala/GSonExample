package project.vichita.gsonexample;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public static final String URL = "http://blog.teamtreehouse.com/api/get_recent_summary/";

    private ListView listView;
    private CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);

        new SimpleTask().execute(URL);
    }

    private class SimpleTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... params) {
            String result = "";
            try{
                HttpGet httpGet = new HttpGet(params[0]);
                HttpClient client = new DefaultHttpClient();
                HttpResponse response = client.execute(httpGet);
                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode==200){
                    InputStream inputStream = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    while ((line = reader.readLine()) != null){
                        result+=line;
                    }
                }

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            showData(s);
        }
    }

    private void showData(String jsonString){
//        Toast.makeText(this,jsonString,Toast.LENGTH_SHORT).show();

        // map json string with object in Blog class
        Gson gson = new Gson();
        Blog blog = gson.fromJson(jsonString,Blog.class);
        List<Post> posts = blog.getPosts();

        // to put post list in string

        /*StringBuilder builder = new StringBuilder();
        builder.setLength(0);

        for (Post post:posts){
            builder.append(post.getTitle());
            builder.append("\n\n");
        }
        Toast.makeText(this,builder.toString(),Toast.LENGTH_SHORT).show();*/
        mAdapter = new CustomAdapter(this, posts);
        listView.setAdapter(mAdapter);
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
