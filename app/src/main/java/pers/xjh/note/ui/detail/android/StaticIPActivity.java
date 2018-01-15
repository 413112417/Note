package pers.xjh.note.ui.detail.android;

import android.content.Context;
import android.net.LinkAddress;
import android.net.ProxyInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import pers.xjh.note.R;
import pers.xjh.note.ui.base.BaseActivity;

/**
 * Created by xjh on 18-1-14.
 */

public class StaticIPActivity extends BaseActivity {

    private InetAddress inetAddress;

    private Object ipConfigurationInstance;

    @Override
    protected int initContentView() {
        return R.layout.activity_static_ip;
    }

    @Override
    protected void initView() {

        test();
//        ContentResolver cr = this.getContentResolver();
//        try {
//            if (Settings.System.getInt(cr, Settings.System.WIFI_USE_STATIC_IP)==0){
//            }
//            else{
//                Settings.System.putString(cr, Settings.System.WIFI_STATIC_IP, "192.168.1.125");
//                Settings.System.putString(cr, Settings.System.WIFI_STATIC_GATEWAY, "192.168.1.1");
//                Settings.System.putString(cr, Settings.System.WIFI_STATIC_NETMASK, "255.255.255.0");
//                Settings.System.putString(cr, Settings.System.WIFI_STATIC_DNS1, "202.103.24.68");
//                Settings.System.putString(cr, Settings.System.WIFI_STATIC_DNS2, "202.103.0.68");
//            }
//        } catch (Settings.SettingNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }

//        setIpWithTfiStaticIp("192.168.0.123", "192.168.0.1", "8.8.8.8");
    }

    /**
     * 设置静态ip地址的方法
     */
    private boolean setIpWithTfiStaticIp(String ip, String gateway, String dns) {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiConfiguration wifiConfig = null;
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();  //得到连接的wifi网络

        List<WifiConfiguration> configuredNetworks = wifiManager
                .getConfiguredNetworks();
        for (WifiConfiguration conf : configuredNetworks) {
            if (conf.networkId == connectionInfo.getNetworkId()) {
                wifiConfig = conf;
                break;
            }
        }

        try {
            setIpAssignment("STATIC", wifiConfig);
            setIpAddress(InetAddress.getByName(ip), 24, wifiConfig);
            setGateway(InetAddress.getByName(gateway), wifiConfig);
            setDNS(InetAddress.getByName(dns), wifiConfig);
            wifiManager.updateNetwork(wifiConfig); // apply the setting
            System.out.println("静态ip设置成功！");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("静态ip设置失败！");
            return false;
        }
    }

    private static void setIpAssignment(String assign, WifiConfiguration wifiConf)
            throws SecurityException, IllegalArgumentException,
            NoSuchFieldException, IllegalAccessException {
        setEnumField(wifiConf, assign, "ipAssignment");
    }

    private static void setIpAddress(InetAddress addr, int prefixLength, WifiConfiguration wifiConf) throws SecurityException,
        IllegalArgumentException, NoSuchFieldException,
        IllegalAccessException, NoSuchMethodException,
        ClassNotFoundException, InstantiationException,
        InvocationTargetException {
        Object linkProperties = getField(wifiConf, "linkProperties");
        if (linkProperties == null)
            return;
        Class<?> laClass = Class.forName("android.net.LinkAddress");
        Constructor<?> laConstructor = laClass.getConstructor(new Class[] {
                InetAddress.class, int.class });
        Object linkAddress = laConstructor.newInstance(addr, prefixLength);

        ArrayList<Object> mLinkAddresses = (ArrayList<Object>) getDeclaredField(
                linkProperties, "mLinkAddresses");
        mLinkAddresses.clear();
        mLinkAddresses.add(linkAddress);
    }

    private static Object getField(Object obj, String name)
            throws SecurityException, NoSuchFieldException,IllegalArgumentException, IllegalAccessException {
        Field f = obj.getClass().getField(name);
        Object out = f.get(obj);
        return out;
    }
    private static Object getDeclaredField(Object obj, String name)
            throws SecurityException, NoSuchFieldException,IllegalArgumentException, IllegalAccessException {
        Field f = obj.getClass().getDeclaredField(name);
        f.setAccessible(true);
        Object out = f.get(obj);
        return out;
    }

    @SuppressWarnings({"unchecked", "rawtypes" })
    private static void setEnumField(Object obj, String value, String name)
            throws SecurityException, NoSuchFieldException,IllegalArgumentException, IllegalAccessException {
        Field f = obj.getClass().getField(name);
        f.set(obj, Enum.valueOf((Class<Enum>)f.getType(), value));
    }

    private static void setGateway(InetAddress gateway,WifiConfiguration wifiConf) throws SecurityException,
        IllegalArgumentException, NoSuchFieldException,
        IllegalAccessException, ClassNotFoundException,
        NoSuchMethodException, InstantiationException,
        InvocationTargetException {
        Object linkProperties = getField(wifiConf, "linkProperties");

        if (linkProperties == null)
            return;

        if (android.os.Build.VERSION.SDK_INT >= 14) { // android4.x版本
            Class<?> routeInfoClass = Class.forName("android.net.RouteInfo");
            Constructor<?> routeInfoConstructor = routeInfoClass
            .getConstructor(new Class[] { InetAddress.class });
            Object routeInfo = routeInfoConstructor.newInstance(gateway);

            ArrayList<Object> mRoutes = (ArrayList<Object>)getDeclaredField(
            linkProperties, "mRoutes");
            mRoutes.clear();
            mRoutes.add(routeInfo);
        } else { // android3.x版本
            ArrayList<InetAddress> mGateways = (ArrayList<InetAddress>) getDeclaredField(
            linkProperties, "mGateways");
            mGateways.clear();
            mGateways.add(gateway);
        }
    }

    private static void setDNS(InetAddress dns, WifiConfiguration wifiConf) throws SecurityException, IllegalArgumentException,
        NoSuchFieldException, IllegalAccessException{
        Object linkProperties = getField(wifiConf, "linkProperties");
        if (linkProperties == null)

        return;
        ArrayList<InetAddress> mDnses = (ArrayList<InetAddress>)
        getDeclaredField(linkProperties, "mDnses");
        mDnses.clear(); // 清除原有DNS设置（如果只想增加，不想清除，词句可省略）
        mDnses.add(dns);
        //增加新的DNS
    }

    private void test() {
        try {
            //获取ETHERNET_SERVICE参数
            String ETHERNET_SERVICE = (String) Context.class.getField("ETHERNET_SERVICE").get(null);

            Class<?> ethernetManagerClass = Class.forName("android.net.EthernetManager");

            Class<?> ipConfigurationClass = Class.forName("android.net.IpConfiguration");

            //获取ethernetManager服务对象
            Object ethernetManager = getSystemService(ETHERNET_SERVICE);

            Object getConfiguration = ethernetManagerClass.getDeclaredMethod("getConfiguration").invoke(ethernetManager);

            Log.e("asd", "ETHERNET_SERVICE : "+ ETHERNET_SERVICE);

            //获取在EthernetManager中的抽象类mService成员变量
            Field mService = ethernetManagerClass.getDeclaredField("mService");

            //修改private权限
            mService.setAccessible(true);

            //获取抽象类的实例化对象
            Object mServiceObject = mService.get(ethernetManager);

            Class<?> iEthernetManagerClass = Class.forName("android.net.IEthernetManager");

            Method[] methods = iEthernetManagerClass.getDeclaredMethods();

            for (Method ms : methods) {

                if (ms.getName().equals("setEthernetEnabled")){

                    ms.invoke(mServiceObject,true);

                    Log.e("asd", "mServiceObject : "+mServiceObject);

                }

            }
            Class<?> staticIpConfig = Class.forName("android.net.StaticIpConfiguration");

            Constructor<?> staticIpConfigConstructor = staticIpConfig.getDeclaredConstructor(staticIpConfig);

            Object staticIpConfigInstance = staticIpConfig.newInstance();

            //获取LinkAddress里面只有一个String类型的构造方法
            Constructor<?> linkAddressConstructor = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                linkAddressConstructor = LinkAddress.class.getDeclaredConstructor(String.class);
            }

            //实例化带String类型的构造方法
            LinkAddress linkAddress = (LinkAddress) linkAddressConstructor.newInstance("192.168.1.10/24");//192.168.1.1/24--子网掩码长度,24相当于255.255.255.0

            Class<?> inetAddressClass = Class.forName("java.net.InetAddress");

            //默认网关参数
            byte[] bytes = new byte[]{(byte) 192, (byte) 168, 3, 1};

            Constructor<?>[] inetAddressConstructors = inetAddressClass.getDeclaredConstructors();

            for (Constructor  inetc: inetAddressConstructors) {

                //获取有三种参数类型的构造方法
                if(inetc.getParameterTypes().length==3) {

                    //修改权限
                    inetc.setAccessible(true);

                    WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);

                    int ipAddressInt = wm.getConnectionInfo().getIpAddress();

                    //hostName主机名
                    String hostName = String.format(Locale.getDefault(), "%d.%d.%d.%d", (ipAddressInt & 0xff), (ipAddressInt >> 8 & 0xff), (ipAddressInt >> 16 & 0xff), (ipAddressInt >> 24 & 0xff));

                    inetAddress = (InetAddress) inetc.newInstance(2, bytes, hostName);

                }

            }
            //获取staticIpConfig中所有的成员变量
            Field[] declaredFields = staticIpConfigInstance.getClass().getDeclaredFields();

            for (Field f :declaredFields ) {

                //设置成员变量的值
                if (f.getName().equals("ipAddress")){

                    //设置IP地址和子网掩码
                    f.set(staticIpConfigInstance,linkAddress);

                }else if (f.getName().equals("gateway")){

                    //设置默认网关
//                    f.set(staticIpConfigInstance, inetAddress);

                }else if (f.getName().equals("domains")){

                    f.set(staticIpConfigInstance,"");

                }/*else if (f.getName().equals("dnsServers")){

                //设置DNS
                    f.set(staticIpConfigInstance,new ArrayList<InetAddress>());

                }*/

            }
            Object staticInstance =staticIpConfigConstructor.newInstance(staticIpConfigInstance);

            //存放ipASSignment枚举类参数的集合
            HashMap ipAssignmentMap=new HashMap();

            //存放proxySettings枚举类参数的集合
            HashMap proxySettingsMap=new HashMap();

            Class<?>[] enumClass = ipConfigurationClass.getDeclaredClasses();

            for (Class enumC : enumClass) {

                //获取枚举数组
                Object[] enumConstants = enumC.getEnumConstants();

                if (enumC.getSimpleName().equals("ProxySettings")){

                    for (Object enu : enumConstants) {

                        //设置代理设置集合 STATIC DHCP UNASSIGNED PAC
                        proxySettingsMap.put(enu.toString(),enu);

                    }

                }else if (enumC.getSimpleName().equals("IpAssignment")){

                    for (Object enu : enumConstants) {

                        //设置以太网连接模式设置集合 STATIC DHCP UNASSIGNED
                        ipAssignmentMap.put(enu.toString(),enu);

                    }

                }

            }
            //获取ipConfiguration类的构造方法
            Constructor<?>[] ipConfigConstructors = ipConfigurationClass.getDeclaredConstructors();

            for (Constructor constru : ipConfigConstructors) {

                //获取ipConfiguration类的4个参数的构造方法
                if (constru.getParameterTypes().length==4){//设置以上四种类型

                    //初始化ipConfiguration对象,设置参数
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ipConfigurationInstance = constru.newInstance(ipAssignmentMap.get("STATIC"), proxySettingsMap.get("NONE"), staticInstance, ProxyInfo.buildDirectProxy(null,0));
                    }

                }

            }

            Log.e("asd", "ipCon : "+ipConfigurationInstance);

            //获取ipConfiguration类中带有StaticIpConfiguration参数类型的名叫setStaticIpConfiguration的方法
            Method setStaticIpConfiguration = ipConfigurationClass.getDeclaredMethod("setStaticIpConfiguration", staticIpConfig);

            //修改private方法权限
            setStaticIpConfiguration.setAccessible(true);

            //在ipConfiguration对象中使用setStaticIpConfiguration方法,并传入参数
            setStaticIpConfiguration.invoke(ipConfigurationInstance,staticInstance);

            Object ethernetManagerInstance = ethernetManagerClass.getDeclaredConstructor(Context.class, iEthernetManagerClass).newInstance(this, mServiceObject);

            Method method = ethernetManagerClass.getDeclaredMethod("setConfiguration",ipConfigurationClass);
            method.invoke(ethernetManagerInstance,ipConfigurationInstance);

            Log.e("asd", "getConfiguration : "+ getConfiguration.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
