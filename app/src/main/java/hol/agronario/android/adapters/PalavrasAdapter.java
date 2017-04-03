package hol.agronario.android.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import hol.agronario.android.R;
import hol.agronario.android.ob.Palavra;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by Marcos on 19/02/2015.
 */
public class PalavrasAdapter extends BaseAdapter implements StickyListHeadersAdapter {

    private List<Palavra> listaTraducoes;
    private LayoutInflater inflater;

    public PalavrasAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        listaTraducoes = new ArrayList<Palavra>();
    }

    public void setData(List<Palavra> listaTraducoesParam) {
        listaTraducoes = listaTraducoesParam;
    }

    @Override
    public int getCount() {
        return listaTraducoes.size();
    }

    @Override
    public Object getItem(int position) {
        return listaTraducoes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.row_palavra, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text.setText(listaTraducoes.get(position).getPalavra());

        return convertView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }
        //set header text as first char in name
        String headerText = "" + listaTraducoes.get(position).getPalavra().subSequence(0, 1).charAt(0);
        holder.text.setText(headerText);

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        //return the first character of the country as ID because this is what headers are based upon
        return listaTraducoes.get(position).getPalavra().subSequence(0, 1).charAt(0);
    }

    class HeaderViewHolder {
        TextView text;
    }

    class ViewHolder {
        TextView text;
    }
}
