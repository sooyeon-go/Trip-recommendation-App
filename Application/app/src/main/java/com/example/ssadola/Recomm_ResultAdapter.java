package com.example.ssadola;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Recomm_ResultAdapter extends BaseAdapter {
    /* 아이템을 세트로 담기 위한 어레이 */
    private ArrayList<MyItem> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public MyItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Context context = parent.getContext();

        /* 'listview_recomm' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_recomm, parent, false);
        }

        /* 'listview_recomm'에 정의된 위젯에 대한 참조 획득 */
        //ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img) ;
        final TextView tv_class = (TextView) convertView.findViewById(R.id.tv_classfi) ;
        final TextView tv_course = (TextView) convertView.findViewById(R.id.tv_course) ;

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        MyItem myItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        //iv_img.setImageDrawable(myItem.getIcon());
        tv_class.setText(myItem.getCalssfi());
        tv_course.setText(myItem.getCourse());

        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */
        /*iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(context,ResultDetailActivity.class);
                context.startActivity(detail);
            }
        });*/
        /*tv_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(context,ResultDetailActivity.class);
                context.startActivity(detail);
            }
        });*/

        tv_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detail = new Intent(context,ResultDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("course", tv_course.getText().toString());
                detail.putExtras(bundle);
                context.startActivity(detail);
            }
        });

        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(String classfi, String course) {

        MyItem mItem = new MyItem();


        mItem.setClassfi(classfi);
        mItem.setCourse(course);

        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);

    }
}
