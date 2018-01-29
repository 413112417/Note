package pers.xjh.note.ui.detail.android;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 18-1-26.
 */

public class BluetooehActivity extends BaseActivity {

    private BluetoothAdapter mBluetoothAdapter;

    private List<BluetoothDevice> bluetoothDeviceArrayList = new ArrayList<>();

    @Override
    protected int initContentView() {
        return R.layout.activity_bluetooth;
    }

    @Override
    protected void initView() {

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
                Log.d("ASd", "run: scanning...");
                Toast.makeText(BluetooehActivity.this, rssi + "", Toast.LENGTH_SHORT).show();
            }
        };

        mBluetoothAdapter.startLeScan(callback);

    }
}
