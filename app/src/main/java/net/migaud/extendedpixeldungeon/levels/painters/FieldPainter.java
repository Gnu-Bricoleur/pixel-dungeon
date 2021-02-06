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

        import net.migaud.extendedpixeldungeon.actors.blobs.Foliage;
        import net.migaud.extendedpixeldungeon.items.Honeypot;
        import net.migaud.extendedpixeldungeon.levels.Level;
        import net.migaud.extendedpixeldungeon.levels.Room;
        import net.migaud.extendedpixeldungeon.levels.Terrain;
        import net.migaud.extendedpixeldungeon.plants.Sungrass;
        import net.migaud.extendedpixeldungeon.plants.Wheat;
        import net.migaud.extendedpixeldungeon.utils.Random;

public class FieldPainter extends Painter {

    public static void paint(Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.HIGH_WHEAT );
        fill( level, room, 2, Terrain.WHEAT );

        room.entrance().set( Room.Door.Type.REGULAR );

        if (Random.Int( 2 ) == 0) {
            level.drop( new Honeypot(), room.random() );
        } else {
            int bushes = (Random.Int( 5 ) == 0 ? 2 : 1);
            for (int i=0; i < bushes; i++) {
                int pos = room.random();
                set( level, pos, Terrain.WHEAT );
                level.plant( new Wheat.Seed(), pos );
            }
        }
    }
}
