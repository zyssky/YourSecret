package com.example.administrator.yoursecret.Module.Discover;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.administrator.yoursecret.AppManager.App;
import com.example.administrator.yoursecret.Module.Detail.DetailActivity;
import com.example.administrator.yoursecret.Home.FragmentsHouse;
import com.example.administrator.yoursecret.Network.NetworkManager;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.Entity.Artical;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

public class DiscoverFragment extends Fragment {

    private MapView mapView;
    public ArrayList<Artical> Articals;
    private int pivot = 0;
    private int LoadPageCounter = 0;
    private List<LatLng> PositionList = new ArrayList<>();
    private AMap aMap;
    private Map<Marker, Artical> Marker_Mapper = new ArrayMap<>();
    private boolean switcher = true;
    private boolean switcher2 = true;

    NetworkManager networkManager = App.getInstance().getNetworkManager();
    MapCalculator mapCalculator = new MapCalculator();

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
        setRetainInstance(true);
        FragmentsHouse.getInstance().putFragment(DiscoverFragment.class.getSimpleName(),this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final Observer<ArrayList<Artical>> article_observer = new Observer<ArrayList<Artical>>() {
            //Implement an Observer to get data.
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull ArrayList<Artical> articals) {
                Articals = articals;
                if(articals.size() == 0){
                    //Reach the End Currently.
                    LoadPageCounter = 0;
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.print(e);
            }

            @Override
            public void onComplete() {
                System.out.println("Transmit complete.");
                for(Artical artical : Articals){
                    System.out.println(artical.articalHref);
                }
                showCenter();
                createMarker();
                setInfoWindow();
                setInfoWindowClickListener();
                setCameraChangeListener();
                //self-crafted marker.
                setLoactionProperties();
                pivot = 1;
                mapView.onResume();
                LoadPageCounter += 1;
            }
        };

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_discover, container, false);
        ImageButton Changer = (ImageButton)rootView.findViewById(R.id.traffic);
        Changer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ChangeGroup Clicked!");
                networkManager.getArticalsOnMap(article_observer,LoadPageCounter);
            }
        });
        mapView = (MapView) rootView.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        setInitCameraChangeListener();
        initLocation();
//        Double distanceTest = mapCalculator.GetDistance(113.4062218666,23.0484004090,113.4073430300,23.0466184288);
//        System.out.println("[*]Distance-Test:" );
//        System.out.println(distanceTest);

        networkManager.getArticalsOnMap(article_observer,LoadPageCounter);
        return rootView;
    }

    private void showCenter(){

    }

    private void initLocation(){

        aMap.moveCamera(CameraUpdateFactory.zoomTo(16.5f)); //properly set to 15-17.
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
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

    private void setInitCameraChangeListener() {
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

    private void setCameraChangeListener() {
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {
                if (switcher2) {
                    LatLng currpoi = cameraPosition.target;
                    CameraPosition position = new CameraPosition(currpoi, 17.5f, 40, 0);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(position);
                    aMap.moveCamera(cameraUpdate);
                }
                switcher2 = false;
            }
        });
    }

    private void setInfoWindowClickListener(){

        aMap.setOnInfoWindowClickListener(new AMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                //Set a jump to information detail here:
                Artical artical = Marker_Mapper.get(marker);
//                System.out.println("Am i really here?");debuggable;
//                System.out.println(artical.articalHref);
                Intent intent = new Intent(getContext(), DetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(AppContants.KEY,artical);
                intent.putExtras(bundle);
                getContext().startActivity(intent);
            }
        });
    }

    public void render(Marker marker, View infoContent){

        Artical artical = Marker_Mapper.get(marker);
        String title = artical.title;
        String ImageLink = artical.imageUri;
        TextView TitleUi = ((TextView)infoContent.findViewById(R.id.inforwindow_text));
        ImageView ImageUi = ((ImageView)infoContent.findViewById(R.id.info_image));
        //Set Title
        if (title != null){
            TitleUi.setText(title);
        }else{
            TitleUi.setText("");
        }
        //Set Image
        if(ImageLink != null){
            GlideImageLoader.loadImage(getContext(), ImageLink, ImageUi);
        }else{

        }
    }

    private void createMarker(){

        for(Artical artical : Articals){
            MarkerOptions temp_marker = new MarkerOptions().
                    position(new LatLng(artical.latitude,artical.longtitude)).
                    title(artical.title).
                    snippet("temp");
            Marker optmarker = aMap.addMarker(temp_marker);
            Marker_Mapper.put(optmarker,artical);
            optmarker.showInfoWindow();
        }

    }

    private void setDataChangeListener(){


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
