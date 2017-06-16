/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.example.administrator.yoursecret.Editor.Photo;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.administrator.yoursecret.Editor.Manager.AdapterManager;
import com.example.administrator.yoursecret.Editor.Manager.EditorDataManager;
import com.example.administrator.yoursecret.R;
import com.example.administrator.yoursecret.utils.AppContants;
import com.example.administrator.yoursecret.utils.FileUtils;
import com.example.administrator.yoursecret.utils.GlideImageLoader;
import com.github.chrisbanes.photoview.PhotoView;

public class ViewPagerActivity extends AppCompatActivity{

    private boolean showDeleteBtn = false;
    private ImageButton deleteBtn;
    private ViewPager viewPager;
    private SamplePagerAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        deleteBtn = (ImageButton) findViewById(R.id.delete_btn);
        viewPager = (HackyViewPager) findViewById(R.id.view_pager);
        adapter = new SamplePagerAdapter();
        adapter.setSingleClickListener(new OnSingleClickListener() {
            @Override
            public void click() {
                showDeleteBtn = !showDeleteBtn;
                if(showDeleteBtn){
                    deleteBtn.setVisibility(View.VISIBLE);
                }
                else {
                    deleteBtn.setVisibility(View.GONE);
                }
            }
        });
		viewPager.setAdapter(adapter);
		int startPos = getIntent().getIntExtra(AppContants.POSITION,0);
		viewPager.setCurrentItem(startPos);
		viewPager.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
				| View.SYSTEM_UI_FLAG_FULLSCREEN);

	}

	public void onDelete(View view){
        Log.d("Delete item ", "onDelete: "+viewPager.getCurrentItem());
        Uri uri = (Uri) EditorDataManager.getInstance().getPhotoManager().getPhotos().remove(viewPager.getCurrentItem());
        AdapterManager.getInstance().getWriteImagesAdapter().notifyDataSetChanged();
        adapter.notifyDataSetChanged();

        if(EditorDataManager.getInstance().getPhotoManager().getPhotos().isEmpty()){
            finish();
        }
        FileUtils.fileDelete(uri.getPath());
    }

    static class SamplePagerAdapter extends PagerAdapter {
        //		private static final int[] sUris = { R.drawable.sample,R.drawable.sample};
        private OnSingleClickListener listener;

        public void setSingleClickListener(OnSingleClickListener listener){
            this.listener = listener;
        }

		@Override
		public int getCount() {
			return EditorDataManager.getInstance().getPhotoManager().getPhotos().size();
		}

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());
			photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            if(listener!=null)
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.click();
                }
            });
            GlideImageLoader.loadImage(container.getContext(), EditorDataManager.getInstance().getPhotoManager().getPhotos().get(position),photoView);

			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

	interface OnSingleClickListener{
        public void click();
    }
}
