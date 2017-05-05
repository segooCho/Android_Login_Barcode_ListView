package joeun.bixolon.bixolonwarranty.ListView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import joeun.bixolon.bixolonwarranty.Model.ListViewEventModel;
import joeun.bixolon.bixolonwarranty.R;

/**
 * Created by admin on 2017. 5. 4..
 */

public class ListViewEventAdapter extends BaseAdapter {

    private ArrayList<ListViewEventModel> listViewEventModelArrayList = new ArrayList<ListViewEventModel>() ;

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewEventModelArrayList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_main_listview_viewpager, parent, false);
        }

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.iconImageView) ;
        TextView textViewBarcode = (TextView) convertView.findViewById(R.id.textViewBarcode) ;
        TextView textViewProducntName = (TextView) convertView.findViewById(R.id.textViewProducntName) ;
        TextView textViewDesc = (TextView) convertView.findViewById(R.id.textViewDesc) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewEventModel listViewEventModel = listViewEventModelArrayList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        iconImageView.setImageDrawable(listViewEventModel.getIcon());
        textViewBarcode.setText(listViewEventModel.getBarcode());
        textViewProducntName.setText(listViewEventModel.getProductName());
        textViewDesc.setText(listViewEventModel.getDesc());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewEventModelArrayList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, String barcode, String productName, String Desc) {
        ListViewEventModel listViewEventModel = new ListViewEventModel();

        listViewEventModel.setIcon(icon);
        listViewEventModel.setBarcode(barcode);
        listViewEventModel.setProductName(productName);
        listViewEventModel.setDesc(Desc);

        listViewEventModelArrayList.add(listViewEventModel);
    }

}
