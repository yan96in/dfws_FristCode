package zjdf.zhaogongzuo.activity.mycenter;

import zjdf.zhaogongzuo.R;
import zjdf.zhaogongzuo.activity.ApplicationConfig;
import zjdf.zhaogongzuo.activity.BaseActivity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.umeng.analytics.MobclickAgent;

/**
 *   百度 地图  地图位置  展示
 * @author Administrator
 *
 */
public class LocsMapActivity extends BaseActivity {

	private Context context;// 上下文
	private Double longitude;// 经度
	private Double latitude;// 纬度
	private String name;// 名字
	// 定位相关
	LocationClient mLocClient;
	LocationData locData = null;
	public MyLocationListenner myListener = new MyLocationListenner();

	// 定位图层
	locationOverlay myLocationOverlay = null;
	// 弹出泡泡图层
	private PopupOverlay mPop = null;// 弹出泡泡图层，浏览节点时使用
	private TextView popupText = null;// 泡泡view
	private View viewCache = null;

	// 地图相关，使用继承MapView的MyLocationMapView目的是重写touch事件实现泡泡处理
	// 如果不处理touch事件，则无需继承，直接使用MapView即可
	MapView mMapView = null; // 地图View
	private MapController mMapController = null;

	boolean isRequest = false;// 是否手动触发请求定位
	boolean isFirstLoc = true;// 是否首次定位
	private GeoPoint point;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		mApplication.initEngineManager(context);
		setContentView(R.layout.layout_baidu_map);
		longitude = getIntent().getDoubleExtra("longitude", 0.0);
		latitude = getIntent().getDoubleExtra("latitude", 0.0);
		name = getIntent().getStringExtra("name");
		initView();

	}

	// 初始化
	private void initView() {
		// 地图初始化
		mMapView = (MapView) findViewById(R.id.bmapView);
		mMapController = mMapView.getController();
//		point=new GeoPoint((int) (longitude * 1e6),(int) (latitude * 1e6));
		mMapController.setZoom(17);
		mMapController.enableClick(true);
		mMapView.setBuiltInZoomControls(true);
		popupText=new TextView(context);
		popupText.setBackgroundResource(R.drawable.popup);
		popupText.setText(name);
		
		// 创建 弹出泡泡图层
		createPaopao();
		locData = new LocationData();
		point=gcj02ToBaiduGeoPoint(latitude, longitude);
//		locData.longitude = longitude;
//		locData.latitude = latitude;
		// 定位图层初始化
		myLocationOverlay = new locationOverlay(mMapView);
		myLocationOverlay.setMarker(getResources().getDrawable(R.drawable.icon_marka));
		// 设置定位数据
		myLocationOverlay.setData(locData);
		myLocationOverlay.enableCompass();
		myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);
		// 添加定位图层
		mMapView.getOverlays().add(myLocationOverlay);
		// 修改定位数据后刷新图层生效
		mMapController.setCenter(point);
		mMapView.refresh();
//		mPop.showPopup(popupText, point, 32);
	}

	/**
	 * 创建弹出泡泡图层
	 */
	public void createPaopao() {
//		viewCache = getLayoutInflater()
//				.inflate(R.layout.custom_text_view, null);
//		popupText = (TextView) viewCache.findViewById(R.id.textcache);
		// 泡泡点击响应回调
		PopupClickListener popListener = new PopupClickListener() {
			@Override
			public void onClickedPopup(int index) {
				Log.v("click", "clickapoapo");
//				mPop.hidePop();
			}
		};
		mPop = new PopupOverlay(mMapView, popListener);
	}

	/**
	 * 定位SDK监听函数
	 */

	public class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			locData.latitude = latitude;
			locData.longitude = longitude;
			// 如果不显示定位精度圈，将accuracy赋值为0即可
			locData.accuracy = location.getRadius();
			// 此处可以设置 locData的方向信息, 如果定位 SDK 未返回方向信息，用户可以自己实现罗盘功能添加方向信息。
			locData.direction = location.getDerect();
			// 更新定位数据
			myLocationOverlay.setData(locData);
			// 更新图层数据执行刷新后生效
			mMapView.refresh();
			// 是手动触发请求或首次定位时，移动到定位点
			if (isRequest || isFirstLoc) {
				// 移动地图到定位点
				mMapController.animateTo(point);
				isRequest = false;
				myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);

			}
			// 首次定位完成
			isFirstLoc = false;
		}

		public void onReceivePoi(BDLocation poiLocation) {
			if (poiLocation == null) {
				return;
			}
		}
	}

	// 继承MyLocationOverlay重写dispatchTap实现点击处理
	public class locationOverlay extends MyLocationOverlay {

		public locationOverlay(MapView mapView) {
			super(mapView);
			// TODO Auto-generated constructor stub
		}
		

		@Override
		protected boolean dispatchTap() {
			// TODO Auto-generated method stub
			// 处理点击事件,弹出泡泡
			
//			mPop.showPopup(BitmapTools.getBitmapFromView(popupText),
//					point, 8);
			mPop.showPopup(popupText, point, 32);
			return true;
		}

	}
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onResume(this);
		mMapView.onResume();
	}
	
	@Override
	public void onPause() {
		super.onPause();
		/**
		 * 此处调用基本统计代码
		 */
		MobclickAgent.onPause(this);
		mMapView.onPause();
	}

	@Override
	protected void onDestroy() {
		// 退出时销毁定位

		if (mLocClient != null)
			mLocClient.stop();

		mMapView.destroy();
		super.onDestroy();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);

	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mMapView.onRestoreInstanceState(savedInstanceState);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	
	private GeoPoint gcj02ToBaiduGeoPoint(double la,double lo){
		double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
		double x = lo, y = la;  
	    double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);  
	    double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);  
	    double bd_lon = z * Math.cos(theta) + 0.0065;  
	    double bd_lat = z * Math.sin(theta) + 0.006;  
	    locData.latitude=bd_lat;
	    locData.longitude=bd_lon;
	    return new GeoPoint((int)(bd_lon*1e6), (int)(bd_lat*1e6));
	}
}
