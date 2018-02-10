package pers.xjh.note.ui.detail.android;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 18-1-26.
 */

public class BluetooehActivity extends BaseActivity {

    private static final String TAG = "BluetooehActivity";

    private BluetoothAdapter mBluetoothAdapter;

    private List<BluetoothDevice> bluetoothDeviceArrayList = new ArrayList<>();

    private TextView mTvContent;

    @Override
    protected int initContentView() {
        return R.layout.activity_bluetooth;
    }

    @Override
    protected void initView() {

        mTvContent = (TextView) findViewById(R.id.tv_content);

        final StringBuilder sb = new StringBuilder();

        // Initializes Bluetooth adapter.
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, 0);
        }

        final BluetoothAdapter.LeScanCallback callback = new BluetoothAdapter.LeScanCallback() {
            @Override
            public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                bluetoothDeviceArrayList.add(device);
                Log.d(TAG, device.getAddress() + "");
                if("20:15:10:19:12:70".equals(device.getAddress())) {
                    device.connectGatt(BluetooehActivity.this, true, new BluetoothGattCallback() {
                        @Override
                        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                            super.onCharacteristicRead(gatt, characteristic, status);
                            if (status == BluetoothGatt.GATT_SUCCESS) {
                                Log.d(TAG, "read value: " + characteristic.getValue());
                                sb.append(characteristic.getValue());
                                mTvContent.setText(sb.toString());
                            }
                        }
                    });

                    mBluetoothAdapter.stopLeScan(null);
                }

            }
        };

        mBluetoothAdapter.startLeScan(callback);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBluetoothAdapter.stopLeScan(null);
    }
}
