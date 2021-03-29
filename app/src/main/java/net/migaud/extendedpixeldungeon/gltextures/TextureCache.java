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

import static net.migaud.extendedpixeldungeon.items.ColorGenerator.randomEyeColor;

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

			if(src == Assets.AVATARS){
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
		int eyeColor = randomEyeColor();
		int transparent = Color.argb(0,0,0,0);
		int[] defaultHairColors = {Color.argb(255, 226, 155, 55),//GUT
				Color.argb(255, 110, 55, 4),//GUT
				Color.argb(255, 149, 73, 3),//GUT
				Color.argb(255, 192, 94, 02),//GUT
				Color.argb(255, 199, 136, 48)};//GUT
		for (int x = 0; x < 7; x++) {
			for (int y = 0; y < 7; y++) {
				Log.i(TAG, "color" + image.getPixel(x,y));
			}
		}
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++){
				//Change eye color randomly
				if(image.getPixel(x,y) == Color.BLACK){
					image.setPixel(x,y,eyeColor);
				}
				else if (image.getPixel(x,y) == transparent){
					image.setPixel(x,y,Color.YELLOW);
				}
				else if  (image.getPixel(x,y) == -9554172){
					image.setPixel(x,y,Color.RED);
				}
				//change hair color randomly
				for (int color : defaultHairColors) {
					if(image.getPixel(x,y) == color){
						image.setPixel(x,y,Color.BLUE);
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
