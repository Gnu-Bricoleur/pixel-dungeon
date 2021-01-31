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
package net.migaud.extendedpixeldungeon.actors.mobs;

import java.util.HashSet;

import net.migaud.extendedpixeldungeon.Dungeon;
import net.migaud.extendedpixeldungeon.actors.Char;
import net.migaud.extendedpixeldungeon.actors.blobs.Blob;
import net.migaud.extendedpixeldungeon.actors.blobs.ParalyticGas;
import net.migaud.extendedpixeldungeon.actors.buffs.Paralysis;
import net.migaud.extendedpixeldungeon.items.quest.RatSkull;
import net.migaud.extendedpixeldungeon.scenes.GameScene;
import net.migaud.extendedpixeldungeon.sprites.FetidRatSprite;
import net.migaud.extendedpixeldungeon.utils.Random;

public class FetidRat extends Mob {

	{
		name = "fetid rat";
		spriteClass = FetidRatSprite.class;
		
		HP = HT = 15;
		defenseSkill = 5;
		
		EXP = 3;
		maxLvl = 5;	
		
		state = WANDERING;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 2, 6 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 12;
	}
	
	@Override
	public int dr() {
		return 2;
	}
	
	@Override
	public String defenseVerb() {
		return "evaded";
	}
	
	@Override
	public int defenseProc( Char enemy, int damage ) {
		
		GameScene.add( Blob.seed( pos, 20, ParalyticGas.class ) );
		
		return super.defenseProc(enemy, damage);
	}
	
	@Override
	public void die( Object cause ) {
		super.die( cause );
		
		Dungeon.level.drop( new RatSkull(), pos ).sprite.drop();
	}
	
	@Override
	public String description() {
		return
			"This marsupial rat is much larger than a regular one. It is surrounded by a foul cloud.";
	}
	
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add( Paralysis.class );
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
