/*
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package net.migaud.extendedpixeldungeon.gltextures;

import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader.TileMode;
import android.util.Log;

import net.migaud.extendedpixeldungeon.Assets;
import net.migaud.extendedpixeldungeon.glwrap.Texture;

public class TextureCache {

	public static Context context;
	
	private static HashMap<Object,SmartTexture> all = new HashMap<Object, SmartTexture>();
	
	// No dithering, no scaling, 32 bits per pixel
	private static BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
	static {
		bitmapOptions.inScaled = false;
		bitmapOptions.inDither = false;
		bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
	}

	public static SmartTexture createSolid( int color ) {
		final String key = "1x1:" + color;
		
		if (all.containsKey( key )) {
			
			return all.get( key );
			
		} else {
		
			Bitmap bmp = Bitmap.createBitmap( 1, 1, Bitmap.Config.ARGB_8888 );
			bmp.eraseColor( color );
			
			SmartTexture tx = new SmartTexture( bmp );
			all.put( key, tx );
			
			return tx;
		}
	}
	
	public static SmartTexture createGradient( int width, int height, int... colors ) {
		
		final String key = "" + width + "x" + height + ":" + colors;
		
		if (all.containsKey( key )) {
			
			return all.get( key );
			
		} else {
		
			Bitmap bmp = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );
			Canvas canvas = new Canvas( bmp );
			Paint paint = new Paint();
			paint.setShader( new LinearGradient( 0, 0, 0, height, colors, null, TileMode.CLAMP ) );
			canvas.drawPaint( paint );
			
			SmartTexture tx = new SmartTexture( bmp );
			all.put( key, tx );
			return tx;
		}
		
	}
	
	public static void add( Object key, SmartTexture tx ) {
		all.put( key, tx );
	}

	public static SmartTexture get( Object src ) {
		
		if (all.containsKey( src )) {
			
			return all.get( src );
			
		} else if (src instanceof SmartTexture) {
			
			return (SmartTexture)src;
			
		} else {

			SmartTexture tx = new SmartTexture( getBitmap( src ) );
			if(src == Assets.ROGUE){
				final String TAG = "[Pixel Dungeon X]";
				Log.i(TAG, "this passed the loop -> " + src);

				tx.bitmap = recolor(tx.bitmap);
			}

			all.put( src, tx );
			return tx;
		}
		
	}
	
	public static void clear() {
		
		for (Texture txt:all.values()) {
			txt.delete();
		}
		all.clear();
		
	}
	
	public static void reload() {
		for (SmartTexture tx:all.values()) {
			tx.reload();
		}		
	}

	public static Bitmap recolor(Bitmap image){
		image = image.copy( Bitmap.Config.ARGB_8888 , true);
		final String TAG = "[Pixel Dungeon X]";
		Log.i(TAG, "height" + image.getHeight());
		Log.i(TAG, "width -> " + image.getWidth());
		int colorWhite = (255 & 0xff) << 24 | (251 & 0xff) << 16 | (242 & 0xff) << 8 | (231 & 0xff); //16511719
		int myWhite = Color.rgb(126, 126, 126);
		int myWhit = Color.rgb(255, 255, 255);
		int myWhi = Color.rgb(201, 193, 185);
		Log.i(TAG, "blanc -> " + colorWhite);
		int[] colors = {Color.rgb(163, 126, 126),
				Color.rgb(126, 126, 126),
				Color.rgb(126, 126, 126)};
		int[] defaultHairColors = {Color.rgb(209, 155, 63),
				Color.rgb(98, 55, 9),
				Color.rgb(133, 73, 12),
				Color.rgb(171, 94, 16),
				Color.rgb(184, 136, 55)};
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++){
				Log.i(TAG, "Color en 1, 1 -> " + image.getPixel(x,y));
				int transparent = Color.argb(0,0,0,0);
				if(image.getPixel(x,y) == myWhite){
					image.setPixel(x,y,Color.RED);
				}
				else if(image.getPixel(x,y) == myWhit){
					image.setPixel(x,y,Color.BLUE);
				}
				else if(image.getPixel(x,y) == myWhi){
					image.setPixel(x,y,Color.GREEN);
				}
				/*else {//image.setPixel(x,y,Color.GREEN);
					image.setPixel(x,y,Color.YELLOW);
				}*/
				else if (image.getPixel(x,y) == transparent){
					image.setPixel(x,y,Color.YELLOW);
				}
				for (int color : defaultHairColors) {
					if(image.getPixel(x,y) == color){
						image.setPixel(x,y,Color.RED);
						//int newColor = color.darker();
						//image.setPixel(x,y,newColor);
					}
				}

			}
		}
		return image;
	}

	public static Bitmap getBitmap( Object src ) {
		
		try {
			if (src instanceof Integer){
				
				return BitmapFactory.decodeResource( 
					context.getResources(), (Integer)src, bitmapOptions );
				
			} else if (src instanceof String) {
				
				Bitmap pic = BitmapFactory.decodeStream(
					context.getAssets().open( (String)src ), null, bitmapOptions );

				if(src == Assets.ROGUE){
					final String TAG = "[Pixel Dungeon X]";
					Log.i(TAG, "this passed the loop -> " + src);

					pic = recolor(pic);
				}

				return pic;
				
			} else if (src instanceof Bitmap) {
				
				return (Bitmap)src;
				
			} else {
				
				return null;
				
			}
		} catch (Exception e) {
			
			e.printStackTrace();
			return null;
			
		}
	}
	
	public static boolean contains( Object key ) {
		return all.containsKey( key );
	}
	
}
