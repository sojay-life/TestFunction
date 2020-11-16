package com.sojay.testfunction;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Bundle;

import com.sojay.testfunction.card.CardActivity;
import com.sojay.testfunction.code.CodeActivity;
import com.sojay.testfunction.gxgy.GuangXianActivity;
import com.sojay.testfunction.loading.LoadingActivity;
import com.sojay.testfunction.puzzle.PuzzleActivity;
import com.sojay.testfunction.random.RandomActivity;
import com.sojay.testfunction.wps.WPSActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<ShortcutBean> shortcutBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv1).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CodeActivity.class)));

        findViewById(R.id.tv2).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, PuzzleActivity.class)));

        findViewById(R.id.tv3).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, LoadingActivity.class)));

        findViewById(R.id.tv4).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, CardActivity.class)));

        findViewById(R.id.tv5).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, GuangXianActivity.class)));

        findViewById(R.id.tv6).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, RandomActivity.class)));

        findViewById(R.id.tv7).setOnClickListener(v -> startActivity(new Intent(MainActivity.this, WPSActivity.class)));

        initShortcutData();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1)
            dynamicShortcuts();

    }

    /**
     * 动态设置Shortcuts
     */
    @RequiresApi(api = Build.VERSION_CODES.N_MR1)
    private void dynamicShortcuts() {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);

        List<ShortcutInfo> infoList = new ArrayList<>();

        // 因为我们要添加的Shortcut不能是无限个
        // 可以用getMaxShortcutCountPerActivity来获取到最大个数

        int maxShortcutCount = shortcutManager.getMaxShortcutCountPerActivity();
        List<ShortcutBean> shortcutBeans;
        if (shortcutBeanList.size() > maxShortcutCount)
            shortcutBeans = shortcutBeanList.subList(0, maxShortcutCount);
        else
            shortcutBeans = shortcutBeanList;

        for (ShortcutBean bean : shortcutBeans) {
            Intent intent = new Intent(this, bean.getIntentClass());
            intent.setAction(Intent.ACTION_VIEW);
//            intent.putExtra("msg", "传递的消息内容");

            ShortcutInfo info = new ShortcutInfo.Builder(this, bean.getShortcutId())
                    .setShortLabel(bean.getShortName())
                    .setLongLabel(bean.getLongName())
                    .setIcon(Icon.createWithResource(this, bean.getIconId()))
                    .setIntent(intent)
                    .build();

            infoList.add(info);
        }

        shortcutManager.addDynamicShortcuts(infoList);


        // 对于Shortcut, Android是允许我们直接放到桌面的, 这样就更加方便了用户的操作, google把他称作为Pinning Shortcuts
        // 对于Pinning Shortcuts
        // google的文档说, 我们开发者是没有权利去删除的, 能删除它的只有用户
        // 那我该项功能删除了咋办? 这东西还在桌面上, 是不是APP要崩?
        // 当然Google考虑到了这点, 它允许我们去disable这个shortcut.
        // 首先我们先调用mShortcutManager.getPinnedShortcuts()来获取到所有的Pinning Shortcuts,
        // 然后去遍历它, 通过id找到我们删除的那个,
        // 然后通过APIdisableShortcuts(List<Ids>)来disable掉该项,
        // 最后我们还要用过removeDynamicShortcuts(List<Ids>)来从shortcuts中移除.
//        List<ShortcutInfo> infos = mShortcutManager.getPinnedShortcuts();
//        for (ShortcutInfo info : infos) {
//            if (info.getId().equals("id" + index)) {
//                shortcutManager.disableShortcuts(Arrays.asList(info.getId()), "暂无该联系人");
//            }
//        }
//        shortcutManager.removeDynamicShortcuts(Arrays.asList("id" + index));


        // 对于Shortcut更新
        // 使用ShortcutInfo.Builder来构建了一个新的ShortcutInfo,
        // 最后我们是用过updateShortcuts(List)来实现更新shortcut
//        Intent intent = new Intent(this, MessageActivity.class);
//        intent.setAction(Intent.ACTION_VIEW);
//        intent.putExtra("msg", "我和" + mAdapter.getItem(index) + "的对话");
//
//        ShortcutInfo info = new ShortcutInfo.Builder(this, "id" + index)
//                .setShortLabel(mAdapter.getItem(index))
//                .setLongLabel("联系人:" + mAdapter.getItem(index))
//                .setIcon(Icon.createWithResource(this, R.drawable.icon))
//                .setIntent(intent)
//                .build();
//
//        shortcutManager.updateShortcuts(Arrays.asList(info));

    }

    /**
     * 初始化快捷方式数据
     */
    private void initShortcutData() {
        if (shortcutBeanList == null)
            shortcutBeanList = new ArrayList<>();

        shortcutBeanList.add(new ShortcutBean(PuzzleActivity.class, "puzzle", R.mipmap.ic_launcher_round, getString(R.string.puzzle_short_name), getString(R.string.puzzle_long_name)));
        shortcutBeanList.add(new ShortcutBean(CardActivity.class, "card", R.mipmap.ic_launcher_round, getString(R.string.card_short_name), getString(R.string.card_long_name)));

    }

}