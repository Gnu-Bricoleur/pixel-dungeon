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
package net.migaud.extendedpixeldungeon.items;

import java.util.HashMap;

import net.migaud.extendedpixeldungeon.Dungeon;
import net.migaud.extendedpixeldungeon.actors.hero.Hero;
import net.migaud.extendedpixeldungeon.items.armor.*;
import net.migaud.extendedpixeldungeon.items.bags.Bag;
import net.migaud.extendedpixeldungeon.items.food.Food;
import net.migaud.extendedpixeldungeon.items.food.MysteryMeat;
import net.migaud.extendedpixeldungeon.items.food.Pasty;
import net.migaud.extendedpixeldungeon.items.potions.*;
import net.migaud.extendedpixeldungeon.items.rings.*;
import net.migaud.extendedpixeldungeon.items.scrolls.*;
import net.migaud.extendedpixeldungeon.items.wands.*;
import net.migaud.extendedpixeldungeon.items.weapon.*;
import net.migaud.extendedpixeldungeon.items.weapon.melee.*;
import net.migaud.extendedpixeldungeon.items.weapon.missiles.*;
import net.migaud.extendedpixeldungeon.plants.*;
import net.migaud.extendedpixeldungeon.utils.Random;
import net.migaud.extendedpixeldungeon.items.armor.Armor;
import net.migaud.extendedpixeldungeon.items.armor.ClothArmor;
import net.migaud.extendedpixeldungeon.items.armor.LeatherArmor;
import net.migaud.extendedpixeldungeon.items.armor.MailArmor;
import net.migaud.extendedpixeldungeon.items.armor.PlateArmor;
import net.migaud.extendedpixeldungeon.items.armor.ScaleArmor;
import net.migaud.extendedpixeldungeon.items.potions.Potion;
import net.migaud.extendedpixeldungeon.items.potions.PotionOfExperience;
import net.migaud.extendedpixeldungeon.items.potions.PotionOfFrost;
import net.migaud.extendedpixeldungeon.items.potions.PotionOfHealing;
import net.migaud.extendedpixeldungeon.items.potions.PotionOfInvisibility;
import net.migaud.extendedpixeldungeon.items.potions.PotionOfLevitation;
import net.migaud.extendedpixeldungeon.items.potions.PotionOfLiquidFlame;
import net.migaud.extendedpixeldungeon.items.potions.PotionOfMight;
import net.migaud.extendedpixeldungeon.items.potions.PotionOfMindVision;
import net.migaud.extendedpixeldungeon.items.potions.PotionOfParalyticGas;
import net.migaud.extendedpixeldungeon.items.potions.PotionOfPurity;
import net.migaud.extendedpixeldungeon.items.potions.PotionOfStrength;
import net.migaud.extendedpixeldungeon.items.potions.PotionOfToxicGas;
import net.migaud.extendedpixeldungeon.items.rings.Ring;
import net.migaud.extendedpixeldungeon.items.rings.RingOfAccuracy;
import net.migaud.extendedpixeldungeon.items.rings.RingOfDetection;
import net.migaud.extendedpixeldungeon.items.rings.RingOfElements;
import net.migaud.extendedpixeldungeon.items.rings.RingOfEvasion;
import net.migaud.extendedpixeldungeon.items.rings.RingOfHaggler;
import net.migaud.extendedpixeldungeon.items.rings.RingOfHaste;
import net.migaud.extendedpixeldungeon.items.rings.RingOfHerbalism;
import net.migaud.extendedpixeldungeon.items.rings.RingOfMending;
import net.migaud.extendedpixeldungeon.items.rings.RingOfPower;
import net.migaud.extendedpixeldungeon.items.rings.RingOfSatiety;
import net.migaud.extendedpixeldungeon.items.rings.RingOfShadows;
import net.migaud.extendedpixeldungeon.items.rings.RingOfThorns;
import net.migaud.extendedpixeldungeon.items.scrolls.Scroll;
import net.migaud.extendedpixeldungeon.items.scrolls.ScrollOfChallenge;
import net.migaud.extendedpixeldungeon.items.scrolls.ScrollOfEnchantment;
import net.migaud.extendedpixeldungeon.items.scrolls.ScrollOfIdentify;
import net.migaud.extendedpixeldungeon.items.scrolls.ScrollOfLullaby;
import net.migaud.extendedpixeldungeon.items.scrolls.ScrollOfMagicMapping;
import net.migaud.extendedpixeldungeon.items.scrolls.ScrollOfMirrorImage;
import net.migaud.extendedpixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import net.migaud.extendedpixeldungeon.items.scrolls.ScrollOfRecharging;
import net.migaud.extendedpixeldungeon.items.scrolls.ScrollOfRemoveCurse;
import net.migaud.extendedpixeldungeon.items.scrolls.ScrollOfTeleportation;
import net.migaud.extendedpixeldungeon.items.scrolls.ScrollOfTerror;
import net.migaud.extendedpixeldungeon.items.scrolls.ScrollOfUpgrade;
import net.migaud.extendedpixeldungeon.items.wands.Wand;
import net.migaud.extendedpixeldungeon.items.wands.WandOfAmok;
import net.migaud.extendedpixeldungeon.items.wands.WandOfAvalanche;
import net.migaud.extendedpixeldungeon.items.wands.WandOfBlink;
import net.migaud.extendedpixeldungeon.items.wands.WandOfDisintegration;
import net.migaud.extendedpixeldungeon.items.wands.WandOfFirebolt;
import net.migaud.extendedpixeldungeon.items.wands.WandOfFlock;
import net.migaud.extendedpixeldungeon.items.wands.WandOfLightning;
import net.migaud.extendedpixeldungeon.items.wands.WandOfMagicMissile;
import net.migaud.extendedpixeldungeon.items.wands.WandOfPoison;
import net.migaud.extendedpixeldungeon.items.wands.WandOfReach;
import net.migaud.extendedpixeldungeon.items.wands.WandOfRegrowth;
import net.migaud.extendedpixeldungeon.items.wands.WandOfSlowness;
import net.migaud.extendedpixeldungeon.items.wands.WandOfTeleportation;
import net.migaud.extendedpixeldungeon.items.weapon.Weapon;
import net.migaud.extendedpixeldungeon.items.weapon.melee.BattleAxe;
import net.migaud.extendedpixeldungeon.items.weapon.melee.Dagger;
import net.migaud.extendedpixeldungeon.items.weapon.melee.Glaive;
import net.migaud.extendedpixeldungeon.items.weapon.melee.Knuckles;
import net.migaud.extendedpixeldungeon.items.weapon.melee.Longsword;
import net.migaud.extendedpixeldungeon.items.weapon.melee.Mace;
import net.migaud.extendedpixeldungeon.items.weapon.melee.Quarterstaff;
import net.migaud.extendedpixeldungeon.items.weapon.melee.ShortSword;
import net.migaud.extendedpixeldungeon.items.weapon.melee.Spear;
import net.migaud.extendedpixeldungeon.items.weapon.melee.Sword;
import net.migaud.extendedpixeldungeon.items.weapon.melee.WarHammer;
import net.migaud.extendedpixeldungeon.items.weapon.missiles.Boomerang;
import net.migaud.extendedpixeldungeon.items.weapon.missiles.CurareDart;
import net.migaud.extendedpixeldungeon.items.weapon.missiles.Dart;
import net.migaud.extendedpixeldungeon.items.weapon.missiles.IncendiaryDart;
import net.migaud.extendedpixeldungeon.items.weapon.missiles.Javelin;
import net.migaud.extendedpixeldungeon.items.weapon.missiles.Shuriken;
import net.migaud.extendedpixeldungeon.items.weapon.missiles.Tamahawk;
import net.migaud.extendedpixeldungeon.plants.Dreamweed;
import net.migaud.extendedpixeldungeon.plants.Earthroot;
import net.migaud.extendedpixeldungeon.plants.Fadeleaf;
import net.migaud.extendedpixeldungeon.plants.Firebloom;
import net.migaud.extendedpixeldungeon.plants.Icecap;
import net.migaud.extendedpixeldungeon.plants.Plant;
import net.migaud.extendedpixeldungeon.plants.Rotberry;
import net.migaud.extendedpixeldungeon.plants.Sorrowmoss;
import net.migaud.extendedpixeldungeon.plants.Sungrass;

public class Generator {

	public static enum Category {
		WEAPON	( 15,	Weapon.class ),
		ARMOR	( 10,	Armor.class ),
		POTION	( 50,	Potion.class ),
		SCROLL	( 40,	Scroll.class ),
		WAND	( 4,	Wand.class ),
		RING	( 2,	Ring.class ),
		SEED	( 5,	Plant.Seed.class ),
		FOOD	( 0,	Food.class ),
		GOLD	( 50,	Gold.class ),
		MISC	( 5,	Item.class );
		
		public Class<?>[] classes;
		public float[] probs;
		
		public float prob;
		public Class<? extends Item> superClass;
		
		private Category( float prob, Class<? extends Item> superClass ) {
			this.prob = prob;
			this.superClass = superClass;
		}
		
		public static int order( Item item ) {
			for (int i=0; i < values().length; i++) {
				if (values()[i].superClass.isInstance( item )) {
					return i;
				}
			}
			
			return item instanceof Bag ? Integer.MAX_VALUE : Integer.MAX_VALUE - 1;
		}
	};
	
	private static HashMap<Category,Float> categoryProbs = new HashMap<Generator.Category, Float>();
	
	static {
		
		Category.GOLD.classes = new Class<?>[]{ 
			Gold.class };
		Category.GOLD.probs = new float[]{ 1 };
		
		Category.SCROLL.classes = new Class<?>[]{ 
			ScrollOfIdentify.class,
			ScrollOfTeleportation.class,
			ScrollOfRemoveCurse.class,
			ScrollOfRecharging.class,
			ScrollOfMagicMapping.class,
			ScrollOfChallenge.class,
			ScrollOfTerror.class,
			ScrollOfLullaby.class,
			ScrollOfPsionicBlast.class,
			ScrollOfMirrorImage.class,
			ScrollOfUpgrade.class,
			ScrollOfEnchantment.class };
		Category.SCROLL.probs = new float[]{ 30, 10, 15, 10, 15, 12, 8, 8, 4, 6, 0, 1 };
		
		Category.POTION.classes = new Class<?>[]{ 
			PotionOfHealing.class,
			PotionOfExperience.class,
			PotionOfToxicGas.class,
			PotionOfParalyticGas.class,
			PotionOfLiquidFlame.class,
			PotionOfLevitation.class,
			PotionOfStrength.class,
			PotionOfMindVision.class,
			PotionOfPurity.class,
			PotionOfInvisibility.class,
			PotionOfMight.class,
			PotionOfFrost.class };
		Category.POTION.probs = new float[]{ 45, 4, 15, 10, 15, 10, 0, 20, 12, 10, 0, 10 };
		
		Category.WAND.classes = new Class<?>[]{ 
			WandOfTeleportation.class,
			WandOfSlowness.class,
			WandOfFirebolt.class,
			WandOfRegrowth.class,
			WandOfPoison.class,
			WandOfBlink.class,
			WandOfLightning.class,
			WandOfAmok.class,
			WandOfReach.class,
			WandOfFlock.class,
			WandOfMagicMissile.class,
			WandOfDisintegration.class,
			WandOfAvalanche.class };
		Category.WAND.probs = new float[]{ 10, 10, 15, 6, 10, 11, 15, 10, 6, 10, 0, 5, 5 };
		
		Category.WEAPON.classes = new Class<?>[]{ 
			Dagger.class,
			Knuckles.class,
			Quarterstaff.class,
			Spear.class,
			Mace.class,
			Sword.class,
			Longsword.class,
			BattleAxe.class,
			WarHammer.class,
			Glaive.class,
			ShortSword.class,
			Dart.class,
			Javelin.class,
			IncendiaryDart.class,
			CurareDart.class,
			Shuriken.class,
			Boomerang.class,
			Tamahawk.class };
		Category.WEAPON.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1 };
		
		Category.ARMOR.classes = new Class<?>[]{ 
			ClothArmor.class,
			LeatherArmor.class,
			MailArmor.class,
			ScaleArmor.class,
			PlateArmor.class };
		Category.ARMOR.probs = new float[]{ 1, 1, 1, 1, 1 };
		
		Category.FOOD.classes = new Class<?>[]{ 
			Food.class, 
			Pasty.class,
			MysteryMeat.class };
		Category.FOOD.probs = new float[]{ 4, 1, 0 };
			
		Category.RING.classes = new Class<?>[]{ 
			RingOfMending.class,
			RingOfDetection.class,
			RingOfShadows.class,
			RingOfPower.class,
			RingOfHerbalism.class,
			RingOfAccuracy.class,
			RingOfEvasion.class,
			RingOfSatiety.class,
			RingOfHaste.class,
			RingOfElements.class,
			RingOfHaggler.class,
			RingOfThorns.class };
		Category.RING.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0 };
		
		Category.SEED.classes = new Class<?>[]{ 
			Firebloom.Seed.class,
			Icecap.Seed.class,
			Sorrowmoss.Seed.class,
			Dreamweed.Seed.class,
			Sungrass.Seed.class,
			Earthroot.Seed.class,
			Fadeleaf.Seed.class,
			Rotberry.Seed.class };
		Category.SEED.probs = new float[]{ 1, 1, 1, 1, 1, 1, 1, 0 };
		
		Category.MISC.classes = new Class<?>[]{ 
			Bomb.class,
			Honeypot.class};
		Category.MISC.probs = new float[]{ 2, 1 };
	}
	
	public static void reset() {
		for (Category cat : Category.values()) {
			categoryProbs.put( cat, cat.prob );
		}
	}
	
	public static Item random() {
		return random( Random.chances( categoryProbs ) );
	}
	
	public static Item random( Category cat ) {
		try {
			
			categoryProbs.put( cat, categoryProbs.get( cat ) / 2 );
			
			switch (cat) {
			case ARMOR:
				return randomArmor();
			case WEAPON:
				return randomWeapon();
			default:
				return ((Item)cat.classes[Random.chances( cat.probs )].newInstance()).random();
			}
			
		} catch (Exception e) {

			return null;
			
		}
	}
	
	public static Item random( Class<? extends Item> cl ) {
		try {
			
			return ((Item)cl.newInstance()).random();
			
		} catch (Exception e) {

			return null;
			
		}
	}
	
	public static Armor randomArmor() throws Exception {
		
		int curStr = Hero.STARTING_STR + Dungeon.potionOfStrength;
		
		Category cat = Category.ARMOR;
		
		Armor a1 = (Armor)cat.classes[Random.chances( cat.probs )].newInstance();
		Armor a2 = (Armor)cat.classes[Random.chances( cat.probs )].newInstance();
		
		a1.random();
		a2.random();
		
		return Math.abs( curStr - a1.STR ) < Math.abs( curStr - a2.STR ) ? a1 : a2;
	}
	
	public static Weapon randomWeapon() throws Exception {
		
		int curStr = Hero.STARTING_STR + Dungeon.potionOfStrength;
		
		Category cat = Category.WEAPON;
		
		Weapon w1 = (Weapon)cat.classes[Random.chances( cat.probs )].newInstance();
		Weapon w2 = (Weapon)cat.classes[Random.chances( cat.probs )].newInstance();
		
		w1.random();
		w2.random();
		
		return Math.abs( curStr - w1.STR ) < Math.abs( curStr - w2.STR ) ? w1 : w2;
	}
}
