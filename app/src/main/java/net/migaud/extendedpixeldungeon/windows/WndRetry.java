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
package net.migaud.extendedpixeldungeon.windows;

        import net.migaud.extendedpixeldungeon.Dungeon;

        import net.migaud.extendedpixeldungeon.items.Item;
        import net.migaud.extendedpixeldungeon.levels.Level;

        import net.migaud.extendedpixeldungeon.noosa.BitmapTextMultiline;
        import net.migaud.extendedpixeldungeon.noosa.Game;
        import net.migaud.extendedpixeldungeon.Rankings;
        import net.migaud.extendedpixeldungeon.Statistics;
        import net.migaud.extendedpixeldungeon.actors.hero.Hero;
        import net.migaud.extendedpixeldungeon.items.Ankh;
        import net.migaud.extendedpixeldungeon.scenes.GameScene;
        import net.migaud.extendedpixeldungeon.scenes.InterlevelScene;
        import net.migaud.extendedpixeldungeon.scenes.PixelScene;
        import net.migaud.extendedpixeldungeon.sprites.ItemSprite;
        import net.migaud.extendedpixeldungeon.ui.RedButton;
        import net.migaud.extendedpixeldungeon.ui.Window;
        import net.migaud.extendedpixeldungeon.utils.Random;

        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.Collections;

        import java.io.IOException;

        import static net.migaud.extendedpixeldungeon.Dungeon.hero;

public class WndRetry extends Window {

    private static final String TXT_MESSAGE	= "You died. Do you want to incarn another Hero exploring this very dungeon ?";
    private static final String TXT_YES		= "Yes, I will avenge myself!";
    private static final String TXT_NO		= "No, I give up";

    private static final int WIDTH		= 120;
    private static final int BTN_HEIGHT	= 20;
    private static final float GAP		= 2;

    public static WndRetry instance;
    public static Object causeOfDeath;

    public WndRetry(Object causeOfDeath ) {

        super();

        instance = this;
        WndResurrect.causeOfDeath = causeOfDeath;

        BitmapTextMultiline message = PixelScene.createMultiline( TXT_MESSAGE, 6 );
        message.maxWidth = WIDTH;
        message.measure();
        message.y = GAP;
        add( message );

        RedButton btnYes = new RedButton( TXT_YES ) {
            @Override
            protected void onClick() {
                hide();


                /*try {
                    Dungeon.saveGame(Dungeon.gameFile(hero.heroClass));
                } catch (IOException e) {
                    e.printStackTrace();
                }*/
                try {
                    Dungeon.saveLevel();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                InterlevelScene.mode = InterlevelScene.Mode.RETRYING;
                Game.switchScene( InterlevelScene.class );
            }
        };
        btnYes.setRect( 0, message.y + message.height() + GAP, WIDTH, BTN_HEIGHT );
        add( btnYes );

        RedButton btnNo = new RedButton( TXT_NO ) {
            @Override
            protected void onClick() {
                hide();

                Rankings.INSTANCE.submit( false );
                Hero.reallyDie( WndResurrect.causeOfDeath, true);
                Dungeon.deleteGame( Dungeon.hero.heroClass, true );
            }
        };
        btnNo.setRect( 0, btnYes.bottom() + GAP, WIDTH, BTN_HEIGHT );
        add( btnNo );

        resize( WIDTH, (int)btnNo.bottom() );
    }

    @Override
    public void destroy() {
        super.destroy();
        instance = null;
    }

    @Override
    public void onBackPressed() {
    }
}
