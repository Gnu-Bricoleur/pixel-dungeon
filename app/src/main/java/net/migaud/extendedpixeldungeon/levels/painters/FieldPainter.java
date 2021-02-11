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

        import net.migaud.extendedpixeldungeon.actors.blobs.Alchemy;
        import net.migaud.extendedpixeldungeon.actors.blobs.Cooking;
        import net.migaud.extendedpixeldungeon.actors.blobs.Foliage;
        import net.migaud.extendedpixeldungeon.items.Generator;
        import net.migaud.extendedpixeldungeon.items.Honeypot;
        import net.migaud.extendedpixeldungeon.items.Item;
        import net.migaud.extendedpixeldungeon.items.potions.Potion;
        import net.migaud.extendedpixeldungeon.levels.Level;
        import net.migaud.extendedpixeldungeon.levels.Room;
        import net.migaud.extendedpixeldungeon.levels.Terrain;
        import net.migaud.extendedpixeldungeon.plants.Sungrass;
        import net.migaud.extendedpixeldungeon.plants.Wheat;
        import net.migaud.extendedpixeldungeon.utils.Point;
        import net.migaud.extendedpixeldungeon.utils.Random;

public class FieldPainter extends Painter {

    public static void paint(Level level, Room room ) {

        fill( level, room, Terrain.WALL );
        fill( level, room, 1, Terrain.HIGH_WHEAT );
        fill( level, room, 2, Terrain.WHEAT );

        room.entrance().set( Room.Door.Type.REGULAR );
        Room.Door entrance = room.entrance();
        Point pot = null;
        if (entrance.x == room.left) {
            pot = new Point( room.right-1, Random.Int( 2 ) == 0 ? room.top + 1 : room.bottom - 1 );
        } else if (entrance.x == room.right) {
            pot = new Point( room.left+1, Random.Int( 2 ) == 0 ? room.top + 1 : room.bottom - 1 );
        } else if (entrance.y == room.top) {
            pot = new Point( Random.Int( 2 ) == 0 ? room.left + 1 : room.right - 1, room.bottom-1 );
        } else if (entrance.y == room.bottom) {
            pot = new Point( Random.Int( 2 ) == 0 ? room.left + 1 : room.right - 1, room.top+1 );
        }
        set( level, pot, Terrain.ALCHEMY );
        Cooking cooking = new Cooking();
        cooking.seed( pot.x + Level.WIDTH * pot.y, 1 );
        level.blobs.put( Cooking.class, cooking );

        int bushes = (Random.Int( 5 ) == 0 ? 2 : 1);
        for (int i=0; i < bushes; i++) {
            int pos = room.random();
            set( level, pos, Terrain.WHEAT );
            level.plant( new Wheat.Seed(), pos );
        }

    }

    private static Item prize( Level level ) {

        Item prize = level.itemToSpanAsPrize();
        if (prize instanceof Potion) {
            return prize;
        } else if (prize != null) {
            level.addItemToSpawn( prize );
        }

        return Generator.random( Generator.Category.POTION );
    }
}
