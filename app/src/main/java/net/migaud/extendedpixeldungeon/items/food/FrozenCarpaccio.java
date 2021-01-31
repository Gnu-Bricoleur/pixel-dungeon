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
package net.migaud.extendedpixeldungeon.items.food;

import net.migaud.extendedpixeldungeon.actors.buffs.Barkskin;
import net.migaud.extendedpixeldungeon.actors.buffs.Bleeding;
import net.migaud.extendedpixeldungeon.actors.buffs.Buff;
import net.migaud.extendedpixeldungeon.actors.buffs.Cripple;
import net.migaud.extendedpixeldungeon.actors.buffs.Hunger;
import net.migaud.extendedpixeldungeon.actors.buffs.Invisibility;
import net.migaud.extendedpixeldungeon.actors.buffs.Poison;
import net.migaud.extendedpixeldungeon.actors.buffs.Weakness;
import net.migaud.extendedpixeldungeon.actors.hero.Hero;
import net.migaud.extendedpixeldungeon.effects.Speck;
import net.migaud.extendedpixeldungeon.sprites.ItemSpriteSheet;
import net.migaud.extendedpixeldungeon.utils.GLog;
import net.migaud.extendedpixeldungeon.utils.Random;

public class FrozenCarpaccio extends Food {

	{
		name = "frozen carpaccio";
		image = ItemSpriteSheet.CARPACCIO;
		energy = Hunger.STARVING - Hunger.HUNGRY;
	}
	
	@Override
	public void execute(Hero hero, String action ) {
		
		super.execute( hero, action );
		
		if (action.equals( AC_EAT )) {
			
			switch (Random.Int( 5 )) {
			case 0:
				GLog.i( "You see your hands turn invisible!" );
				Buff.affect( hero, Invisibility.class, Invisibility.DURATION );
				break;
			case 1:
				GLog.i( "You feel your skin hardens!" );
				Buff.affect( hero, Barkskin.class ).level( hero.HT / 4 );
				break;
			case 2:
				GLog.i( "Refreshing!" );
				Buff.detach( hero, Poison.class );
				Buff.detach( hero, Cripple.class );
				Buff.detach( hero, Weakness.class );
				Buff.detach( hero, Bleeding.class );
				break;
			case 3:
				GLog.i( "You feel better!" );
				if (hero.HP < hero.HT) {
					hero.HP = Math.min( hero.HP + hero.HT / 4, hero.HT );
					hero.sprite.emitter().burst( Speck.factory( Speck.HEALING ), 1 );
				}
				break;
			}
		}
	}
	
	@Override
	public String info() {
		return 
			"It's a piece of frozen raw meat. The only way to eat it is " +
			"by cutting thin slices of it. And this way it's suprisingly good.";
	}
	
	public int price() {
		return 10 * quantity;
	};
	
	public static Food cook( MysteryMeat ingredient ) {
		FrozenCarpaccio result = new FrozenCarpaccio();
		result.quantity = ingredient.quantity();
		return result;
	}
}
