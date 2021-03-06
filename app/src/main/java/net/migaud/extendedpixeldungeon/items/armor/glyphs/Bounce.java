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
package net.migaud.extendedpixeldungeon.items.armor.glyphs;

import net.migaud.extendedpixeldungeon.Dungeon;
import net.migaud.extendedpixeldungeon.actors.Actor;
import net.migaud.extendedpixeldungeon.actors.Char;
import net.migaud.extendedpixeldungeon.actors.mobs.Mob;
import net.migaud.extendedpixeldungeon.effects.Pushing;
import net.migaud.extendedpixeldungeon.items.armor.Armor;
import net.migaud.extendedpixeldungeon.items.armor.Armor.Glyph;
import net.migaud.extendedpixeldungeon.levels.Level;
import net.migaud.extendedpixeldungeon.utils.Random;

public class Bounce extends Glyph {

	private static final String TXT_BOUNCE	= "%s of bounce";
	
	@Override
	public int proc(Armor armor, Char attacker, Char defender, int damage) {

		int level = Math.max( 0, armor.effectiveLevel() );
		
		if (Level.adjacent( attacker.pos, defender.pos ) && Random.Int( level + 5) >= 4) {
			
			for (int i=0; i < Level.NEIGHBOURS8.length; i++) {
				int ofs = Level.NEIGHBOURS8[i];
				if (attacker.pos - defender.pos == ofs) {
					int newPos = attacker.pos + ofs;
					if ((Level.passable[newPos] || Level.avoid[newPos]) && Actor.findChar( newPos ) == null) {
						
						Actor.addDelayed( new Pushing( attacker, attacker.pos, newPos ), -1 );
						
						attacker.pos = newPos;
						// FIXME
						if (attacker instanceof Mob) {
							Dungeon.level.mobPress( (Mob)attacker );
						} else {
							Dungeon.level.press( newPos, attacker );
						}
						
					}
					break;
				}
			}

		}
		
		return damage;
	}
	
	@Override
	public String name( String weaponName) {
		return String.format( TXT_BOUNCE, weaponName );
	}

}
