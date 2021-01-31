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
package net.migaud.extendedpixeldungeon.levels.painters;

import net.migaud.extendedpixeldungeon.items.Gold;
import net.migaud.extendedpixeldungeon.items.Heap;
import net.migaud.extendedpixeldungeon.items.keys.IronKey;
import net.migaud.extendedpixeldungeon.levels.Level;
import net.migaud.extendedpixeldungeon.levels.Room;
import net.migaud.extendedpixeldungeon.levels.Terrain;
import net.migaud.extendedpixeldungeon.utils.Random;

public class TreasuryPainter extends Painter {

	public static void paint( Level level, Room room ) {

		fill( level, room, Terrain.WALL );
		fill( level, room, 1, Terrain.EMPTY );
		
		set( level, room.center(), Terrain.STATUE );
		
		Heap.Type heapType = Random.Int( 2 ) == 0 ? Heap.Type.CHEST : Heap.Type.HEAP;
		
		int n = Random.IntRange( 2, 3 );
		for (int i=0; i < n; i++) {
			int pos;
			do {
				pos = room.random();
			} while (level.map[pos] != Terrain.EMPTY || level.heaps.get( pos ) != null);
			level.drop( new Gold().random(), pos ).type = (i == 0 && heapType == Heap.Type.CHEST ? Heap.Type.MIMIC : heapType);
		}
		
		if (heapType == Heap.Type.HEAP) {
			for (int i=0; i < 6; i++) {
				int pos;
				do {
					pos = room.random();
				} while (level.map[pos] != Terrain.EMPTY);
				level.drop( new Gold( Random.IntRange( 1, 3 ) ), pos );
			}
		}
		
		room.entrance().set( Room.Door.Type.LOCKED );
		level.addItemToSpawn( new IronKey() );
	}
}
