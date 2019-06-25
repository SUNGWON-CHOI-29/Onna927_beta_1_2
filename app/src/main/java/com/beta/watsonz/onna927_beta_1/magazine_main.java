package com.beta.watsonz.onna927_beta_1;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by watsonz on 2015-07-29.
 */

public class magazine_main extends Fragment {

        /*private int[] imageIDs = new int[] {
                R.drawable.gallery_image_01,
                R.drawable.gallery_image_02,
                R.drawable.gallery_image_03,
                R.drawable.gallery_image_04,
                R.drawable.gallery_image_05,
                R.drawable.gallery_image_06,
        };*/
    Bitmap bmp,bmp2,bmp3;
    Bitmap bmp4,bmp5,bmp6,bmp7,bmp8,bmp9;
    public magazine_main() {

    }

    static magazine_main newInstance(int SectionNumber){
            magazine_main fragment = new magazine_main();
            Bundle args = new Bundle();
            args.putInt("section_number", SectionNumber);
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_magazine, container, false);
            ImageView mImage1 = (ImageView)rootView.findViewById(R.id.imageView1);
            ImageView mImage2 = (ImageView)rootView.findViewById(R.id.imageView2);
            ImageView mImage3 = (ImageView)rootView.findViewById(R.id.imageView3);

            ImageView mImage4 = (ImageView)rootView.findViewById(R.id.imageView4);
            ImageView mImage5 = (ImageView)rootView.findViewById(R.id.imageView5);
            ImageView mImage6 = (ImageView)rootView.findViewById(R.id.imageView6);
            ImageView mImage7 = (ImageView)rootView.findViewById(R.id.imageView7);
            ImageView mImage8 = (ImageView)rootView.findViewById(R.id.imageView8);
            ImageView mImage9 = (ImageView)rootView.findViewById(R.id.imageView9);

            Resources res = getResources();
            bmp = BitmapFactory.decodeResource(res, R.drawable.gallery_image_01);
            bmp = Bitmap.createScaledBitmap(bmp, 480, 120, false);

            bmp2 = BitmapFactory.decodeResource(res, R.drawable.gallery_image_02);
            bmp2 = Bitmap.createScaledBitmap(bmp2, 480, 120, false);

            bmp3 = BitmapFactory.decodeResource(res, R.drawable.gallery_image_03);
            bmp3 = Bitmap.createScaledBitmap(bmp3, 480, 120, false);


            bmp4 = BitmapFactory.decodeResource(res, R.drawable.gallery_image_04);
            bmp4 = Bitmap.createScaledBitmap(bmp4, 320, 240, false);

            bmp5 = BitmapFactory.decodeResource(res, R.drawable.gallery_image_05);
            bmp5 = Bitmap.createScaledBitmap(bmp5, 320, 240, false);

            bmp6 = BitmapFactory.decodeResource(res, R.drawable.gallery_image_06);
            bmp6 = Bitmap.createScaledBitmap(bmp6, 320, 240, false);

            bmp7 = BitmapFactory.decodeResource(res, R.drawable.gallery_image_07);
            bmp7 = Bitmap.createScaledBitmap(bmp7, 320, 240, false);

            bmp8 = BitmapFactory.decodeResource(res, R.drawable.gallery_image_08);
            bmp8 = Bitmap.createScaledBitmap(bmp8, 320, 240, false);

            bmp9 = BitmapFactory.decodeResource(res, R.drawable.gallery_image_09);
            bmp9 = Bitmap.createScaledBitmap(bmp9, 320, 240, false);

            mImage1.setImageBitmap(bmp);
            mImage2.setImageBitmap(bmp2);
            mImage3.setImageBitmap(bmp3);

            mImage4.setImageBitmap(bmp4);
            mImage5.setImageBitmap(bmp5);
            mImage6.setImageBitmap(bmp6);
            mImage7.setImageBitmap(bmp7);
            mImage8.setImageBitmap(bmp8);
            mImage9.setImageBitmap(bmp9);
            return rootView;
        }
}