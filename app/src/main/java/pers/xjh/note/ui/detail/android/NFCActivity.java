package pers.xjh.note.ui.detail.android;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.widget.TextView;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by XJH on 2017/4/12.
 */

public class NFCActivity extends BaseActivity {

    //nfc控制器
    private NfcAdapter mNfcAdapter;
    //显示的内容
    private TextView mTvContent;

    @Override
    protected int initContentView() {
        return R.layout.activity_nfc;
    }

    @Override
    protected void initView() {
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        mTvContent = (TextView) findViewById(R.id.tv_content);

        if(mNfcAdapter == null) {
            mTvContent.setText("该设备不支持NFC");
        } else if(!mNfcAdapter.isEnabled()) {
            mTvContent.setText("请在系统设置中先启用NFC功能！");
        } else {
            mTvContent.setText("请将卡靠近设备");
        }
    }

    @Override
    protected void start() {
        try {
            if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
                //处理该intent
                mTvContent.setText(processIntent(getIntent()));
            }
        } catch (Exception e) {
            mTvContent.setText(e.getMessage());
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        try {
            mTvContent.setText(processIntent(intent));
        } catch (Exception e) {
            mTvContent.setText(e.getMessage());
        }
    }

    /**
     * Parses the NDEF Message from the intent and prints to the TextView
     */
    private String processIntent(Intent intent) {
        //取出封装在intent中的TAG
        Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        /*for (String tech : tagFromIntent.getTechList()) {
            System.out.println(tech);
        }*/
        boolean auth = false;
        //读取TAG
        MifareClassic mfc = MifareClassic.get(tagFromIntent);
        try {
            String metaInfo = "";
            //Enable I/O operations to the tag from this TagTechnology object.
            mfc.connect();
            int type = mfc.getType();//获取TAG的类型
            int sectorCount = mfc.getSectorCount();//获取TAG中包含的扇区数
            String typeS = "";
            switch (type) {
                case MifareClassic.TYPE_CLASSIC:
                    typeS = "TYPE_CLASSIC";
                    break;
                case MifareClassic.TYPE_PLUS:
                    typeS = "TYPE_PLUS";
                    break;
                case MifareClassic.TYPE_PRO:
                    typeS = "TYPE_PRO";
                    break;
                case MifareClassic.TYPE_UNKNOWN:
                    typeS = "TYPE_UNKNOWN";
                    break;
            }
            metaInfo += "卡片类型：" + typeS + "\n共" + sectorCount + "个扇区\n共"
                    + mfc.getBlockCount() + "个块\n存储空间: " + mfc.getSize() + "B\n";
            for (int j = 0; j < sectorCount; j++) {
                //Authenticate a sector with key A.
                auth = mfc.authenticateSectorWithKeyA(j,
                        MifareClassic.KEY_DEFAULT);
                int bCount;
                int bIndex;
                if (auth) {
                    metaInfo += "Sector " + j + ":验证成功\n";
                    // 读取扇区中的块
                    bCount = mfc.getBlockCountInSector(j);
                    bIndex = mfc.sectorToBlock(j);
                    for (int i = 0; i < bCount; i++) {
                        byte[] data = mfc.readBlock(bIndex);
                        metaInfo += "Block " + bIndex + " : "
                                + bytesToHexString(data) + "\n";
                        bIndex++;
                    }
                } else {
                    metaInfo += "Sector " + j + ":验证失败\n";
                }
            }
            return  metaInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    //字符序列转换为16进制字符串
    private String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("0x");
        if (src == null || src.length <= 0) {
            return null;
        }
        char[] buffer = new char[2];
        for (int i = 0; i < src.length; i++) {
            buffer[0] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
            buffer[1] = Character.forDigit(src[i] & 0x0F, 16);
            System.out.println(buffer);
            stringBuilder.append(buffer);
        }
        return stringBuilder.toString();
    }
}
