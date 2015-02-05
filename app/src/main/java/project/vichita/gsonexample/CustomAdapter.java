package project.vichita.gsonexample;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Vichita Fongmala on 2/6/15.
 */
public class CustomAdapter extends BaseAdapter {
    private ViewHolder mViewHolder;
    private LayoutInflater mInflater;
    private Activity activity;
    List<Post> mPosts;
    Post mPost;

    public CustomAdapter(Activity activity,List<Post> posts) {
        mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mPosts = posts;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return mPosts.size();
    }

    @Override
    public Object getItem(int position) {
        return mPosts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.post,parent,false);
            mViewHolder = new ViewHolder();
            mViewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.post_thumbnail);
            mViewHolder.author = (TextView) convertView.findViewById(R.id.post_author);
            mViewHolder.title = (TextView) convertView.findViewById(R.id.post_title);
            mViewHolder.date = (TextView) convertView.findViewById(R.id.post_date);

            convertView.setTag(mViewHolder);
        }else{
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mPost = mPosts.get(position);
        if (mPost.getThumbnail() != null){
            Picasso.with(activity).load(mPost.getThumbnail()).into(mViewHolder.thumbnail);
        }
        mViewHolder.author.setText(mPost.getAuthor());
        mViewHolder.title.setText(mPost.getTitle());
        mViewHolder.date.setText(mPost.getDate());

        return convertView;
    }

    private static class ViewHolder{
        ImageView thumbnail;
        TextView title;
        TextView author;
        TextView date;
    }
}
