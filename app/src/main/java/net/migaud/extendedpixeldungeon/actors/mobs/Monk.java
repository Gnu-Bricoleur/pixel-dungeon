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
import net.migaud.extendedpixeldungeon.actors.buffs.Amok;
import net.migaud.extendedpixeldungeon.actors.buffs.Terror;
import net.migaud.extendedpixeldungeon.actors.hero.Hero;
import net.migaud.extendedpixeldungeon.actors.mobs.npcs.Imp;
import net.migaud.extendedpixeldungeon.items.KindOfWeapon;
import net.migaud.extendedpixeldungeon.items.food.Food;
import net.migaud.extendedpixeldungeon.items.weapon.melee.Knuckles;
import net.migaud.extendedpixeldungeon.sprites.MonkSprite;
import net.migaud.extendedpixeldungeon.utils.GLog;
import net.migaud.extendedpixeldungeon.utils.Random;

public class Monk extends Mob {

	public static final String TXT_DISARM	= "%s has knocked the %s from your hands!";
	
	{
		name = "dwarf monk";
		spriteClass = MonkSprite.class;
		
		HP = HT = 70;
		defenseSkill = 30;
		
		EXP = 11;
		maxLvl = 21;
		
		loot = new Food();
		lootChance = 0.083f;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 12, 16 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 30;
	}
	
	@Override
	protected float attackDelay() {
		return 0.5f;
	}
	
	@Override
	public int dr() {
		return 2;
	}
	
	@Override
	public String defenseVerb() {
		return "parried";
	}
	
	@Override
	public void die( Object cause ) {
		Imp.Quest.process( this );
		
		super.die( cause );
	}
	
	@Override
	public int attackProc( Char enemy, int damage ) {
		
		if (Random.Int( 6 ) == 0 && enemy == Dungeon.hero) {
			
			Hero hero = Dungeon.hero;
			KindOfWeapon weapon = hero.belongings.weapon;
			
			if (weapon != null && !(weapon instanceof Knuckles) && !weapon.cursed) {
				hero.belongings.weapon = null;
				Dungeon.level.drop( weapon, hero.pos ).sprite.drop();
				GLog.w( TXT_DISARM, name, weapon.name() );
			}
		}
		
		return damage;
	}
	
	@Override
	public String description() {
		return
			"These monks are fanatics, who devoted themselves to protecting their city's secrets from all aliens. " +
			"They don't use any armor or weapons, relying solely on the art of hand-to-hand combat.";
	}
	
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		IMMUNITIES.add( Amok.class );
		IMMUNITIES.add( Terror.class );
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
}
