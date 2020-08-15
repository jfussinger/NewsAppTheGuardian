package com.example.android.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * An {@link NewsAdapter} knows how to create a list item layout for each news story
 * in the data source (a list of {@link News} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */


public class NewsAdapter extends ArrayAdapter<News> {

    private static class ViewHolder {
        TextView sectionName;
        TextView webTitle;
        TextView contributor;
        TextView webPublicationDate;

    }

    /**
     * Constructs a new {@link NewsAdapter}.
     *
     * @param context of the app
     * @param newsstories is the list of news stories, which is the data source of the adapter
     */
    public NewsAdapter(Context context, List<News> newsstories) {
        super(context, 0, newsstories);
    }

    /**
     * Returns a list item view that displays information about the news stories
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.

        ViewHolder viewHolder;
        News News = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.news_list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.sectionName = (TextView) convertView.findViewById(R.id.sectionName);
            viewHolder.webTitle = (TextView) convertView.findViewById(R.id.webTitle);
            viewHolder.webPublicationDate = (TextView) convertView.findViewById(R.id.webPublicationDate);
            viewHolder.contributor = (TextView) convertView.findViewById(R.id.contributor);
            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        } else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.sectionName.setText(News.getSectionName());
        viewHolder.webTitle.setText(News.getWebTitle());
        viewHolder.webPublicationDate.setText(News.getWebPublicationDate());
        viewHolder.contributor.setText(News.getContributor());

        return convertView;

    }
}
