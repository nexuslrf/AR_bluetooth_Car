package com.google.ar.sceneform.samples.hellosceneform;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

import android.util.Log;

import java.io.InputStream;

import java.io.InputStreamReader;
import java.io.OutputStream;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import java.util.UUID;
import java.util.Set;
import java.io.BufferedReader;


/**
 * Created by nexuslrf on 2018/12/12.
 */

public class MyControler implements Runnable{
    private BluetoothDevice mmDevice;
    private BluetoothAdapter mBTAdapter;
    public static String bluetoothmsg="";
    private UUID uuid;
    public BluetoothSocket mmSocket;
    public OutputStream mmOutputStream;
    public InputStream mmInputStream;
    private BufferedReader pht;

    public MyControler(){
        super();

        mBTAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBTAdapter.getBondedDevices();
        if(pairedDevices.size()!=0)
            mmDevice = pairedDevices.iterator().next();
        uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        try {
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        }
        catch (Exception e){
        }
    }

    public boolean isConnected(){
        return mmSocket != null;
    }

    public boolean Connect(){
        Set<BluetoothDevice> pairedDevices = mBTAdapter.getBondedDevices();
        if(pairedDevices.size()!=0)
            mmDevice = pairedDevices.iterator().next();
        uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
        try {
            mmSocket = mmDevice.createRfcommSocketToServiceRecord(uuid);
        }
        catch (Exception e){
        }
        return mmSocket != null;
    }



    @Override
    public void run() {
        while(true) {
            Log.i("Control",bluetoothmsg);
            try {
                if(sendData(bluetoothmsg)) {

                    System.out.println("Bluetooth send successfully!");
                    Thread.sleep(400);
                    continue;
                }
                Thread.sleep(400);
                mmSocket.connect();
                mmOutputStream = mmSocket.getOutputStream();
                mmInputStream = mmSocket.getInputStream();
                pht=new BufferedReader(new InputStreamReader(mmInputStream));
            } catch (Exception e) {}
        }
    }
    boolean sendData(String m){
        try{
            String msg = m;
            mmOutputStream.write(msg.getBytes());
            System.out.println("Data Sent!");
            return true;
        }catch (Exception e){
            return false;
        }
    }
}
