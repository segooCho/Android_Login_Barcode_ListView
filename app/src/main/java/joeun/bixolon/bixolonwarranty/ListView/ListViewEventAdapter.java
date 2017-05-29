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

    /**
     * list 의 개수를 리턴. : 필수 구현
     * @return
     */
    @Override
    public int getCount() {
        return listViewEventModelArrayList.size() ;
    }

    /**
     * position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
     * @param position
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.content_main_listview_viewpager, parent, false);
        }

        ImageView lvIconImageView = (ImageView) convertView.findViewById(R.id.lvIconImageView) ;
        TextView lvTextViewSerialNo = (TextView) convertView.findViewById(R.id.lvTextViewSerialNo) ;
        TextView lvTextViewModel = (TextView) convertView.findViewById(R.id.lvTextViewModel) ;
        TextView lvTextViewBuyerCode = (TextView) convertView.findViewById(R.id.lvTextViewBuyerCode) ;
        TextView lvTextViewWarrantyCode = (TextView) convertView.findViewById(R.id.lvTextViewWarrantyCode) ;
        TextView lvTextViewGoingOutDate = (TextView) convertView.findViewById(R.id.lvTextViewGoingOutDate) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewEventModel listViewEventModel = listViewEventModelArrayList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        lvIconImageView.setImageDrawable(listViewEventModel.getIcon());
        lvTextViewSerialNo.setText(listViewEventModel.getSerialNo());
        lvTextViewModel.setText(listViewEventModel.getModel());
        lvTextViewBuyerCode.setText(listViewEventModel.getBuyerCode());
        lvTextViewWarrantyCode.setText(listViewEventModel.getWarrantyCode());
        lvTextViewGoingOutDate.setText(listViewEventModel.getGoingOutDate());

        return convertView;
    }

    /**
     * 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
     * @param position
     * @return
     */
    @Override
    public long getItemId(int position) {
        return position ;
    }

    /**
     * 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
     * @param position
     * @return
     */
    @Override
    public Object getItem(int position) {
        return listViewEventModelArrayList.get(position) ;
    }

    /**
     * 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
     * @param icon
     * @param serialNo
     * @param model
     * @param buyerCode
     * @param warrantyCode
     * @param goingOutDate
     */
    public void addItem(Drawable icon, String serialNo, String model, String buyerCode, String warrantyCode, String goingOutDate) {
        ListViewEventModel listViewEventModel = new ListViewEventModel();

        listViewEventModel.setIcon(icon);
        listViewEventModel.setSerialNo(serialNo);
        listViewEventModel.setModel(model);
        listViewEventModel.setBuyerCode(buyerCode);
        listViewEventModel.setWarrantyCode(warrantyCode);
        listViewEventModel.setGoingOutDate(goingOutDate);

        listViewEventModelArrayList.add(listViewEventModel);
    }

}
