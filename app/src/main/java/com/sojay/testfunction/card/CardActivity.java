package com.sojay.testfunction.card;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import com.sojay.testfunction.R;
import com.sojay.testfunction.card.card1.CardTransformer;
import com.sojay.testfunction.card.card1.NewPagerAdapter;
import com.sojay.testfunction.card.card2.BookBean;
import com.sojay.testfunction.card.card2.CardLayout;
import com.sojay.testfunction.card.card3.CardAdapter;
import com.sojay.testfunction.card.card3.CardItemTouchHelperCallback;
import com.sojay.testfunction.card.card3.CardLayoutManager;
import com.sojay.testfunction.card.card3.OnSwipeListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CardActivity extends AppCompatActivity {

    private ViewPager mVP;
    private CardLayout mCardLayout;
    private TextView tv2;
    private TextView tv3;
    private RecyclerView mRv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        initView();

        initVP();

        initCView();

        initRV();

    }

    private void initRV() {
        List<String> datas = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            datas.add(String.valueOf(i));
        }
        CardAdapter adapter = new CardAdapter(datas);
        mRv.setAdapter(adapter);

        CardItemTouchHelperCallback cardCallback = new CardItemTouchHelperCallback(mRv.getAdapter(), datas);
        cardCallback.setOnSwipedListener(new OnSwipeListener<String>() {
            @Override
            public void onSwiping(RecyclerView.ViewHolder viewHolder, float ratio) {

            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, String on) {
                datas.add(on);
                //当前显示的  datas.get(0)

            }

            @Override
            public void onSwipedClear() {

            }
        });
        final ItemTouchHelper touchHelper = new ItemTouchHelper(cardCallback);
        final CardLayoutManager cardLayoutManager = new CardLayoutManager(mRv, touchHelper);
        mRv.setLayoutManager(cardLayoutManager);
        touchHelper.attachToRecyclerView(mRv);
    }

    @SuppressLint("CheckResult")
    private void initCView() {
        List<BookBean> mList = new ArrayList<>();

        BookBean b1 = new BookBean();
        b1.setCover("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596704267280&di=1b5014d15b1c39a96de4be02a76c9be6&imgtype=0&src=http%3A%2F%2Fimg.kuai8.com%2Fnewspic%2Fimage%2F202007%2F30%2F75acd3847b.jpg");
        mList.add(b1);

        BookBean b2 = new BookBean();
        b2.setCover("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596704286402&di=c2c0efd47712fd5360ed455c783cb162&imgtype=0&src=http%3A%2F%2Fimg.kuai8.com%2Fnewspic%2Fimage%2F202007%2F30%2F79265d7535.jpg");
        mList.add(b2);

        BookBean b3 = new BookBean();
        b3.setCover("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596704303980&di=cabb088add294fcc53b9f44fbd65b712&imgtype=0&src=http%3A%2F%2Fimg.kuai8.com%2Fnewspic%2Fimage%2F202007%2F30%2F11b8ba4f6c.jpg");
        mList.add(b3);

        BookBean b4 = new BookBean();
        b4.setCover("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596704326929&di=f6bb5e00688259824ecbc455dc88280c&imgtype=0&src=http%3A%2F%2Fimg.kuai8.com%2Fnewspic%2Fimage%2F202007%2F30%2F1f63dbe41a.jpg");
        mList.add(b4);

        Observable.timer(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    mCardLayout.addAllView(mList);
                    mCardLayout.setOnShowTopListener(new CardLayout.OnShowTopListener() {
                        @Override
                        public void onShowTop(BookBean bean) {
                            tv2.setText(bean.getCover().substring(bean.getCover().length() - 10));
                        }
                    });
                });
    }

    private void initVP() {
        List<String> list = new ArrayList<>();
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596704267280&di=1b5014d15b1c39a96de4be02a76c9be6&imgtype=0&src=http%3A%2F%2Fimg.kuai8.com%2Fnewspic%2Fimage%2F202007%2F30%2F75acd3847b.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596704286402&di=c2c0efd47712fd5360ed455c783cb162&imgtype=0&src=http%3A%2F%2Fimg.kuai8.com%2Fnewspic%2Fimage%2F202007%2F30%2F79265d7535.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596704303980&di=cabb088add294fcc53b9f44fbd65b712&imgtype=0&src=http%3A%2F%2Fimg.kuai8.com%2Fnewspic%2Fimage%2F202007%2F30%2F11b8ba4f6c.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1596704326929&di=f6bb5e00688259824ecbc455dc88280c&imgtype=0&src=http%3A%2F%2Fimg.kuai8.com%2Fnewspic%2Fimage%2F202007%2F30%2F1f63dbe41a.jpg");
        NewPagerAdapter adapter = new NewPagerAdapter(list);
        mVP.setAdapter(adapter);

        CardTransformer mCardPageTransformer = new CardTransformer();
        mVP.setPageTransformer(true, mCardPageTransformer);
        mVP.setOffscreenPageLimit(100);

    }

    private void initView() {
        mVP = findViewById(R.id.vp);
        mCardLayout = findViewById(R.id.card_layout);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        mRv = findViewById(R.id.rv);
    }
}