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
package net.migaud.extendedpixeldungeon.levels.features;

        import net.migaud.extendedpixeldungeon.actors.hero.Hero;
        import net.migaud.extendedpixeldungeon.items.Item;
        import net.migaud.extendedpixeldungeon.scenes.GameScene;
        import net.migaud.extendedpixeldungeon.windows.WndBag;

public class CookingPot {

    private static final String TXT_SELECT_SEED	= "Select a seed to throw";

    private static Hero hero;
    private static int pos;

    public static void operate( Hero hero, int pos ) {

        CookingPot.hero = hero;
        CookingPot.pos = pos;

        GameScene.selectItem( itemSelector, WndBag.Mode.SEED, TXT_SELECT_SEED );
    }

    private static final WndBag.Listener itemSelector = new WndBag.Listener() {
        @Override
        public void onSelect( Item item ) {
            if (item != null) {
                item.cast( hero, pos );
            }
        }
    };
}