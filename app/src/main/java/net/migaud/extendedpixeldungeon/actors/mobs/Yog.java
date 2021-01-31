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

import java.util.ArrayList;
import java.util.HashSet;

import net.migaud.extendedpixeldungeon.Dungeon;
import net.migaud.extendedpixeldungeon.ResultDescriptions;
import net.migaud.extendedpixeldungeon.Statistics;
import net.migaud.extendedpixeldungeon.actors.Actor;
import net.migaud.extendedpixeldungeon.actors.Char;
import net.migaud.extendedpixeldungeon.actors.blobs.Blob;
import net.migaud.extendedpixeldungeon.actors.blobs.Fire;
import net.migaud.extendedpixeldungeon.actors.blobs.ToxicGas;
import net.migaud.extendedpixeldungeon.actors.buffs.Amok;
import net.migaud.extendedpixeldungeon.actors.buffs.Buff;
import net.migaud.extendedpixeldungeon.actors.buffs.Burning;
import net.migaud.extendedpixeldungeon.actors.buffs.Charm;
import net.migaud.extendedpixeldungeon.actors.buffs.Ooze;
import net.migaud.extendedpixeldungeon.actors.buffs.Poison;
import net.migaud.extendedpixeldungeon.actors.buffs.Sleep;
import net.migaud.extendedpixeldungeon.actors.buffs.Terror;
import net.migaud.extendedpixeldungeon.actors.buffs.Vertigo;
import net.migaud.extendedpixeldungeon.effects.Pushing;
import net.migaud.extendedpixeldungeon.effects.particles.ShadowParticle;
import net.migaud.extendedpixeldungeon.items.keys.SkeletonKey;
import net.migaud.extendedpixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import net.migaud.extendedpixeldungeon.items.weapon.enchantments.Death;
import net.migaud.extendedpixeldungeon.levels.Level;
import net.migaud.extendedpixeldungeon.mechanics.Ballistica;
import net.migaud.extendedpixeldungeon.scenes.GameScene;
import net.migaud.extendedpixeldungeon.sprites.BurningFistSprite;
import net.migaud.extendedpixeldungeon.sprites.CharSprite;
import net.migaud.extendedpixeldungeon.sprites.LarvaSprite;
import net.migaud.extendedpixeldungeon.sprites.RottingFistSprite;
import net.migaud.extendedpixeldungeon.sprites.YogSprite;
import net.migaud.extendedpixeldungeon.utils.GLog;
import net.migaud.extendedpixeldungeon.utils.Utils;
import net.migaud.extendedpixeldungeon.utils.Random;

public class Yog extends Mob {
	
	{
		name = Dungeon.depth == Statistics.deepestFloor ? "Yog-Dzewa" : "echo of Yog-Dzewa";
		spriteClass = YogSprite.class;
		
		HP = HT = 300;
		
		EXP = 50;
		
		state = PASSIVE;
	}
	
	private static final String TXT_DESC =
		"Yog-Dzewa is an Old God, a powerful entity from the realms of chaos. A century ago, the ancient dwarves " +
		"barely won the war against its army of demons, but were unable to kill the god itself. Instead, they then " +
		"imprisoned it in the halls below their city, believing it to be too weak to rise ever again.";	
	
	private static int fistsCount = 0;
	
	public Yog() {
		super();
	}
	
	public void spawnFists() {
		RottingFist fist1 = new RottingFist();
		BurningFist fist2 = new BurningFist();
		
		do {
			fist1.pos = pos + Level.NEIGHBOURS8[Random.Int( 8 )];
			fist2.pos = pos + Level.NEIGHBOURS8[Random.Int( 8 )];
		} while (!Level.passable[fist1.pos] || !Level.passable[fist2.pos] || fist1.pos == fist2.pos);
		
		GameScene.add( fist1 );
		GameScene.add( fist2 );
	}
	
	@Override
	public void damage( int dmg, Object src ) {
		
		if (fistsCount > 0) {
			
			for (Mob mob : Dungeon.level.mobs) {
				if (mob instanceof BurningFist || mob instanceof RottingFist) {
					mob.beckon( pos );
				}
			}
			
			dmg >>= fistsCount;
		}
		
		super.damage( dmg, src );
	}
	
	@Override
	public int defenseProc( Char enemy, int damage ) {

		ArrayList<Integer> spawnPoints = new ArrayList<Integer>();
		
		for (int i=0; i < Level.NEIGHBOURS8.length; i++) {
			int p = pos + Level.NEIGHBOURS8[i];
			if (Actor.findChar( p ) == null && (Level.passable[p] || Level.avoid[p])) {
				spawnPoints.add( p );
			}
		}
		
		if (spawnPoints.size() > 0) {
			Larva larva = new Larva();
			larva.pos = Random.element( spawnPoints );
			
			GameScene.add( larva );
			Actor.addDelayed( new Pushing( larva, pos, larva.pos ), -1 );
		}

		return super.defenseProc(enemy, damage);
	}
	
	@Override
	public void beckon( int cell ) {
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void die( Object cause ) {

		for (Mob mob : (Iterable<Mob>)Dungeon.level.mobs.clone()) {
			if (mob instanceof BurningFist || mob instanceof RottingFist) {
				mob.die( cause );
			}
		}
		
		GameScene.bossSlain();
		Dungeon.level.drop( new SkeletonKey(), pos ).sprite.drop();
		super.die( cause );
		
		yell( "..." );
	}
	
	@Override
	public void notice() {
		super.notice();
		yell( "Hope is an illusion..." );
	}
	
	@Override
	public String description() {
		return TXT_DESC;
			
	}
	
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		
		IMMUNITIES.add( Death.class );
		IMMUNITIES.add( Terror.class );
		IMMUNITIES.add( Amok.class );
		IMMUNITIES.add( Charm.class );
		IMMUNITIES.add( Sleep.class );
		IMMUNITIES.add( Burning.class );
		IMMUNITIES.add( ToxicGas.class );
		IMMUNITIES.add( ScrollOfPsionicBlast.class );
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
	public static class RottingFist extends Mob {
	
		private static final int REGENERATION	= 4;
		
		{
			name = "rotting fist";
			spriteClass = RottingFistSprite.class;
			
			HP = HT = 300;
			defenseSkill = 25;
			
			EXP = 0;
			
			state = WANDERING;
		}
		
		public RottingFist() {
			super();
			fistsCount++;
		}
		
		@Override
		public void die( Object cause ) {
			super.die( cause );
			fistsCount--;
		}
		
		@Override
		public int attackSkill( Char target ) {
			return 36;
		}
		
		@Override
		public int damageRoll() {
			return Random.NormalIntRange( 24, 36 );
		}
		
		@Override
		public int dr() {
			return 15;
		}
		
		@Override
		public int attackProc( Char enemy, int damage ) {
			if (Random.Int( 3 ) == 0) {
				Buff.affect( enemy, Ooze.class );
				enemy.sprite.burst( 0xFF000000, 5 );
			}
			
			return damage;
		}
		
		@Override
		public boolean act() {
			
			if (Level.water[pos] && HP < HT) {
				sprite.emitter().burst( ShadowParticle.UP, 2 );
				HP += REGENERATION;
			}
			
			return super.act();
		}
		
		@Override
		public String description() {
			return TXT_DESC;
				
		}
		
		private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
		static {
			RESISTANCES.add( ToxicGas.class );
			RESISTANCES.add( Death.class );
			RESISTANCES.add( ScrollOfPsionicBlast.class );
		}
		
		@Override
		public HashSet<Class<?>> resistances() {
			return RESISTANCES;
		}
		
		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
		static {
			IMMUNITIES.add( Amok.class );
			IMMUNITIES.add( Sleep.class );
			IMMUNITIES.add( Terror.class );
			IMMUNITIES.add( Poison.class );
			IMMUNITIES.add( Vertigo.class );
		}
		
		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}
	
	public static class BurningFist extends Mob {
		
		{
			name = "burning fist";
			spriteClass = BurningFistSprite.class;
			
			HP = HT = 200;
			defenseSkill = 25;
			
			EXP = 0;
			
			state = WANDERING;
		}
		
		public BurningFist() {
			super();
			fistsCount++;
		}
		
		@Override
		public void die( Object cause ) {
			super.die( cause );
			fistsCount--;
		}
		
		@Override
		public int attackSkill( Char target ) {
			return 36;
		}
		
		@Override
		public int damageRoll() {
			return Random.NormalIntRange( 20, 32 );
		}
		
		@Override
		public int dr() {
			return 15;
		}
		
		@Override
		protected boolean canAttack( Char enemy ) {
			return Ballistica.cast( pos, enemy.pos, false, true ) == enemy.pos;
		}
		
		@Override
		public boolean attack( Char enemy ) {
			
			if (!Level.adjacent( pos, enemy.pos )) {
				spend( attackDelay() );
				
				if (hit( this, enemy, true )) {
					
					int dmg =  damageRoll();
					enemy.damage( dmg, this );
					
					enemy.sprite.bloodBurstA( sprite.center(), dmg );
					enemy.sprite.flash();
					
					if (!enemy.isAlive() && enemy == Dungeon.hero) {
						Dungeon.fail( Utils.format( ResultDescriptions.BOSS, name, Dungeon.depth ) );
						GLog.n( TXT_KILL, name );
					}
					return true;
					
				} else {
					
					enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
					return false;
				}
			} else {
				return super.attack( enemy );
			}
		}
		
		@Override
		public boolean act() {
			for (int i=0; i < Level.NEIGHBOURS9.length; i++) {
				GameScene.add( Blob.seed( pos + Level.NEIGHBOURS9[i], 2, Fire.class ) );
			}
			
			return super.act();
		}
		
		@Override
		public String description() {
			return TXT_DESC;
				
		}
		
		private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
		static {
			RESISTANCES.add( ToxicGas.class );
			RESISTANCES.add( Death.class );
			RESISTANCES.add( ScrollOfPsionicBlast.class );
		}
		
		@Override
		public HashSet<Class<?>> resistances() {
			return RESISTANCES;
		}
		
		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
		static {
			IMMUNITIES.add( Amok.class );
			IMMUNITIES.add( Sleep.class );
			IMMUNITIES.add( Terror.class );
			IMMUNITIES.add( Burning.class );
		}
		
		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}
	
	public static class Larva extends Mob {
		
		{
			name = "god's larva";
			spriteClass = LarvaSprite.class;
			
			HP = HT = 25;
			defenseSkill = 20;
			
			EXP = 0;
			
			state = HUNTING;
		}
		
		@Override
		public int attackSkill( Char target ) {
			return 30;
		}
		
		@Override
		public int damageRoll() {
			return Random.NormalIntRange( 15, 20 );
		}
		
		@Override
		public int dr() {
			return 8;
		}
		
		@Override
		public String description() {
			return TXT_DESC;
				
		}
	}
}
