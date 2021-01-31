/*
 * Pixel Dungeon
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
package net.migaud.extendedpixeldungeon.items.wands;

import net.migaud.extendedpixeldungeon.noosa.audio.Sample;
import net.migaud.extendedpixeldungeon.Assets;
import net.migaud.extendedpixeldungeon.Dungeon;
import net.migaud.extendedpixeldungeon.actors.Actor;
import net.migaud.extendedpixeldungeon.actors.Char;
import net.migaud.extendedpixeldungeon.effects.MagicMissile;
import net.migaud.extendedpixeldungeon.effects.Swap;
import net.migaud.extendedpixeldungeon.items.Dewdrop;
import net.migaud.extendedpixeldungeon.items.Heap;
import net.migaud.extendedpixeldungeon.items.Item;
import net.migaud.extendedpixeldungeon.items.potions.Potion;
import net.migaud.extendedpixeldungeon.items.potions.PotionOfMight;
import net.migaud.extendedpixeldungeon.items.potions.PotionOfStrength;
import net.migaud.extendedpixeldungeon.items.scrolls.Scroll;
import net.migaud.extendedpixeldungeon.items.scrolls.ScrollOfUpgrade;
import net.migaud.extendedpixeldungeon.items.scrolls.ScrollOfEnchantment;
import net.migaud.extendedpixeldungeon.levels.Level;
import net.migaud.extendedpixeldungeon.levels.Terrain;
import net.migaud.extendedpixeldungeon.mechanics.Ballistica;
import net.migaud.extendedpixeldungeon.scenes.GameScene;
import net.migaud.extendedpixeldungeon.utils.GLog;
import net.migaud.extendedpixeldungeon.utils.Callback;

public class WandOfReach extends Wand {

	private static final String TXT_YOU_NOW_HAVE	= "You have magically transported %s into your backpack"; 
	
	{
		name = "Wand of Reach";
		hitChars = false;
	}
	
	@Override
	protected void onZap( int cell ) {
		
		int reach = Math.min( Ballistica.distance, power() + 4 );
		
		boolean mapUpdated = false;
		for (int i=1; i < reach; i++) {
			
			int c = Ballistica.trace[i];
			
			int before = Dungeon.level.map[c];
			
			Char ch = Actor.findChar( c );
			if (ch != null) {
				Actor.addDelayed( new Swap( curUser, ch ), -1 );
				break;
			}
			
			Heap heap = Dungeon.level.heaps.get( c );
			if (heap != null) {
				switch (heap.type) {
				case HEAP:
					transport( heap );
					break;
				case CHEST:
				case MIMIC:
				case TOMB:
				case SKELETON:
					heap.open( curUser );
					break;
				default:
				}
				
				break;
			}
			
			Dungeon.level.press( c, null );
			if (before == Terrain.OPEN_DOOR) {
				Level.set( c, Terrain.DOOR );
				GameScene.updateMap( c );
			} else if (Level.water[c]) {
				GameScene.ripple( c );
			}
			
			mapUpdated = mapUpdated || Dungeon.level.map[c] != before;
		}
		
		if (mapUpdated) {
			Dungeon.observe();
		}
	}
	
	private void transport( Heap heap ) {
		Item item = heap.pickUp();
		if (item.doPickUp( curUser )) {
			
			if (item instanceof Dewdrop) {
				// Do nothing
			} else {
				if (((item instanceof ScrollOfUpgrade || item instanceof ScrollOfEnchantment) && ((Scroll)item).isKnown()) ||
					((item instanceof PotionOfStrength || item instanceof PotionOfMight) && ((Potion)item).isKnown())) {
					GLog.p( TXT_YOU_NOW_HAVE, item.name() );
				} else {
					GLog.i( TXT_YOU_NOW_HAVE, item.name() );
				}
			}

		} else {
			Dungeon.level.drop( item, curUser.pos ).sprite.drop();
		}
	}
	
	protected void fx( int cell, Callback callback ) {
		MagicMissile.force( curUser.sprite.parent, curUser.pos, cell, callback );
		Sample.INSTANCE.play( Assets.SND_ZAP );
	}
	
	@Override
	public String desc() {
		return
			"This utility wand can be used to grab objects from a distance and to switch places with enemies. " +
			"Waves of magic force radiated from it will affect all cells on their way triggering traps, " +
			"trampling high vegetation, opening closed doors and closing open ones.";
	}
}
