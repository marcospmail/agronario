package hol.agronario.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import hol.agronario.android.R;
import hol.agronario.android.ob.Idioma;

/**
 * Created by MarcosPaulo on 5/5/15.
 */
public class ActivityMainAdapter extends RecyclerView.Adapter<ActivityMainAdapter.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private List<Idioma> mListIdiomas;
    private int mSelectedPosition = 0;

    Context mContext;

    private String name;
//    private int profile;
    private String email;


    public class ViewHolder extends RecyclerView.ViewHolder {
        int Holderid;

        View mView;
        TextView textView;
        ImageView imageView;
        ImageView profile;
        TextView Name;
        TextView email;


        public ViewHolder(View itemView, int ViewType) {
            super(itemView);
            mView = itemView;
            itemView.setClickable(true);

            if (ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.rowText);
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);
                Holderid = 1;

            } else {
                Name = (TextView) itemView.findViewById(R.id.name);
                email = (TextView) itemView.findViewById(R.id.email);
            //  profile = (ImageView) itemView.findViewById(R.id.circleView);
                Holderid = 0;
            }
        }
    }


    public ActivityMainAdapter(Context context, List<Idioma> listIdioma, String Name, String Email) {
        mContext = context;
        mListIdiomas = listIdioma;
        name = Name;
        email = Email;
//        profile = Profile;
    }


    @Override
    public ActivityMainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_item_row, parent, false); //Inflating the layout
            ViewHolder vhItem = new ViewHolder(v, viewType);
            v.setTag(vhItem);
            v.setClickable(true);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    mSelectedPosition = ((ViewHolder) view.getTag()).getAdapterPosition();
                    view.setSelected(true);
                    notifyDataSetChanged();
                }
            });

            return vhItem;

        } else if (viewType == TYPE_HEADER) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_header, parent, false); //Inflating the layout
            ViewHolder vhHeader = new ViewHolder(v, viewType);

            return vhHeader;


        }
        return null;
    }

    public void setPositionChecked(int position) {
        mSelectedPosition = position;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ActivityMainAdapter.ViewHolder holder, final int position) {
        if (holder.Holderid == 1) {

            Idioma idioma = mListIdiomas.get(position - 1);

            holder.textView.setText(idioma.getIdioma());

            holder.mView.setSelected(mSelectedPosition == position);

            holder.mView.setTag(holder);

        } else {
//            holder.profile.setImageResource(profile);
            holder.Name.setText(name);
            holder.email.setText(email);
        }
    }

    @Override
    public int getItemCount() {
        return mListIdiomas.size() + 1;
    }


    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


}