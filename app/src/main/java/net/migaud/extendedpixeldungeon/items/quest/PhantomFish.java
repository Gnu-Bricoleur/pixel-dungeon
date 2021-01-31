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
package net.migaud.extendedpixeldungeon.items.quest;

import java.util.ArrayList;

import net.migaud.extendedpixeldungeon.noosa.audio.Sample;
import net.migaud.extendedpixeldungeon.Assets;
import net.migaud.extendedpixeldungeon.actors.buffs.Buff;
import net.migaud.extendedpixeldungeon.actors.buffs.Invisibility;
import net.migaud.extendedpixeldungeon.actors.hero.Hero;
import net.migaud.extendedpixeldungeon.items.Item;
import net.migaud.extendedpixeldungeon.sprites.ItemSpriteSheet;
import net.migaud.extendedpixeldungeon.utils.GLog;

public class PhantomFish extends Item {
	
	private static final String AC_EAT	= "EAT";
	
	private static final float TIME_TO_EAT	= 2f;
	
	{
		name = "phantom fish";
		image = ItemSpriteSheet.PHANTOM;

		unique = true;
	}
	
	@Override
	public ArrayList<String> actions( Hero hero ) {
		ArrayList<String> actions = super.actions( hero );
		actions.add( AC_EAT );
		return actions;
	}
	
	@Override
	public void execute( final Hero hero, String action ) {
		if (action.equals( AC_EAT )) {
			
			detach( hero.belongings.backpack );
			
			hero.sprite.operate( hero.pos );
			hero.busy();
			Sample.INSTANCE.play( Assets.SND_EAT );
			Sample.INSTANCE.play( Assets.SND_MELD );
			
			GLog.i( "You see your hands turn invisible!" );
			Buff.affect( hero, Invisibility.class, Invisibility.DURATION );
			
			hero.spend( TIME_TO_EAT );
			
		} else {
			
			super.execute( hero, action );
			
		}
	}
	
	@Override
	public boolean isUpgradable() {
		return false;
	}
	
	@Override
	public boolean isIdentified() {
		return true;
	}
	
	@Override
	public String info() {
		return
			"You can barely see this tiny translucent fish in the air. " +
			"In the water it becomes effectively invisible.";
	}
}
