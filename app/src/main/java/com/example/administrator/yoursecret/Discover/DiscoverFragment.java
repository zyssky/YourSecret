package com.example.administrator.yoursecret.Discover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.administrator.yoursecret.Detail.DetailActivity;
import com.example.administrator.yoursecret.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DiscoverFragment extends Fragment {

    private MapView mapView;
    private List<LatLng> PositionList = new ArrayList<>();
    private AMap aMap;
    //private Map<Marker, Infos>
    private boolean switcher = true;
    public DiscoverFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DiscoverFragment newInstance() {
        DiscoverFragment fragment = new DiscoverFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        MapCalculator mapCalculator = new MapCalculator();
        Double distanceTest = mapCalculator.GetDistance(113.4062218666,23.0484004090,113.4073430300,23.0466184288);
        System.out.println("[*]Distance-Test:" );
        System.out.println(distanceTest);
        showCenter();
        setLoactionProperties();
        setInfoWindow();
        setInfoWindowClickListener();
        setCameraChangeListener();
        //self-crafted marker.
        createMarker();
        return rootView;
    }

    private void showCenter(){

    }

    private void setLoactionProperties(){

        aMap.moveCamera(CameraUpdateFactory.zoomTo(16.5f)); //properly set to 15-17.
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
//        myLocationStyle.interval(10000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        //Set Some test cases:
        //MyLocationStyle myLocationIcon(BitmapDescriptor myLocationIcon);//设置定位蓝点的icon图标方法，需要用到BitmapDescriptor类对象作为参数。
    }

    private void setInfoWindow(){

        aMap.setInfoWindowAdapter(new AMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                View infoContent = LayoutInflater.from(getContext()).inflate(
                        R.layout.discover_infowindow, null);
                render(marker,infoContent);
                return infoContent;
            }

            @Override
            public View getInfoContents(Marker marker) {
                return null;
            }
        });
    }

    private void setCameraChangeListener() {
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                if (switcher) {
                    LatLng currpoi = cameraPosition.target;
                    CameraPosition position = new CameraPosition(currpoi, 17.5f, 40, 0);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(position);
                    aMap.moveCamera(cameraUpdate);
                }
                switcher = false;
            }
        });
    }

    private void setInfoWindowClickListener(){

        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                //Set a jump to information detail here:
                Intent intent = new Intent(getContext(), DetailActivity.class);
                getContext().startActivity(intent);
            }
        });
    }

    public void render(Marker marker, View infoContent){

        String title = marker.getTitle();
        TextView TitleUi = ((TextView)infoContent.findViewById(R.id.inforwindow_text));
        if (title != null){
            System.out.print("Set Info here.");
        }else{
            TitleUi.setText("");
        }
    }

    private void createMarker(){

        PositionList.add(new LatLng(23.0484004090,113.4062218666));
        PositionList.add(new LatLng(23.0466184288,113.4073430300));
        PositionList.add(new LatLng(23.0486184288,113.4073430300));
        PositionList.add(new LatLng(23.0496184288,113.4053430300));
        PositionList.add(new LatLng(23.0486184288,113.4053430300));
        PositionList.add(new LatLng(23.0460853111,113.4060984850));
        for (LatLng poi : PositionList) {
            aMap.addMarker(new MarkerOptions().position(poi).title("广州大学城").snippet("DefaultMarker")).showInfoWindow();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
