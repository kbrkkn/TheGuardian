package ui.android.theguardian;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kubra on 20.11.2016.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context,int resource,List<News> newsList){
        super(context,resource,newsList);

    }
    @NonNull
    @Override
/*Gets a View that displays the data at the specified position in the data set

    int position:The position of the item within the adapter's data set of the item whose view we want.
    * View: The old view to reuse, if possible. Note: You should check that this view is non-null and of an appropriate
     * type before using. If it is not possible to convert this view to display the correct data,
     * this method can create a new view.
     *
     * parent:ViewGroup: The parent that this view will eventually be attached to
    */
   public View getView(int position, View convertView, ViewGroup parent) {
View listItemView =convertView;

        if(listItemView ==null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        //Get the data item associated with the specified position in the data set.
        News currentNews=getItem(position);

        //Finds sectionView from listItemView by Id and sets its text to currentNews' section.
        TextView sectionView=(TextView) listItemView.findViewById(R.id.sectionId);
        sectionView.setText(currentNews.getSection());

        TextView titleView=(TextView) listItemView.findViewById(R.id.titleId);
        titleView.setText(currentNews.getTitle());


        return listItemView;
    }

}
