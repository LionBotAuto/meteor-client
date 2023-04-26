package dev.hoot.api.input;

import dev.hoot.api.commons.Rand;
import dev.hoot.api.commons.Time;
import dev.hoot.api.game.Game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Keyboard
{
	public static void pressed(int keyCode)
	{
		pressed(keyCode, KeyEvent.CHAR_UNDEFINED);
	}

	public static void pressed(int keyCode, char keyChar)
	{
		Canvas canvas = Game.getClient().getCanvas();
		long time = System.currentTimeMillis();
		KeyEvent event = new KeyEvent(canvas, KeyEvent.KEY_PRESSED, time, 0, keyCode, keyChar, KeyEvent.KEY_LOCATION_STANDARD);
		canvas.dispatchEvent(event);
	}

	public static void typed(int keyCode)
	{
		typed(keyCode, KeyEvent.CHAR_UNDEFINED);
	}

	public static void typed(int keyCode, char keyChar)
	{
		Canvas canvas = Game.getClient().getCanvas();
		long time = System.currentTimeMillis();
		KeyEvent event = new KeyEvent(canvas, KeyEvent.KEY_TYPED, time, 0, keyCode, keyChar, KeyEvent.KEY_LOCATION_UNKNOWN);
		canvas.dispatchEvent(event);
	}

	public static void released(int keyCode)
	{
		released(keyCode, KeyEvent.CHAR_UNDEFINED);
	}

	public static void released(int keyCode, char keyChar)
	{
		Canvas canvas = Game.getClient().getCanvas();
		long time = System.currentTimeMillis();
		KeyEvent event = new KeyEvent(canvas, KeyEvent.KEY_RELEASED, time, 0, keyCode, keyChar, KeyEvent.KEY_LOCATION_STANDARD);
		canvas.dispatchEvent(event);
	}

	public static void type(char c) throws Exception {
		Canvas canvas = Game.getClient().getCanvas();
		long time = System.currentTimeMillis();
		int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
		KeyEvent pressed = new KeyEvent(canvas, KeyEvent.KEY_PRESSED, time, 0, keyCode, c, KeyEvent.KEY_LOCATION_STANDARD);
		KeyEvent typed = new KeyEvent(canvas, KeyEvent.KEY_TYPED, time, 0, 0, c, KeyEvent.KEY_LOCATION_UNKNOWN);
		canvas.dispatchEvent(pressed);
		canvas.dispatchEvent(typed);
		KeyEvent released = new KeyEvent(
				canvas,
				KeyEvent.KEY_RELEASED,
				time + Rand.nextInt(10,15),
				0,
				keyCode,
				c,
				KeyEvent.KEY_LOCATION_STANDARD
		);

		canvas.dispatchEvent(released);
	}

	public static void type(int number) throws Exception {
		type(String.valueOf(number));
	}

	public static void type(String text) throws Exception {
		type(text, false);
	}

	public static void type(String text, boolean sendEnter) throws Exception {
		char[] chars = text.toCharArray();
		for (char c : chars)
		{
			type(c);
		}

		if (sendEnter)
		{
			sendEnter();
		}
	}

	public static void sendEnter() throws Exception {
		type((char) KeyEvent.VK_ENTER);
	}

	public static void sendSpace() throws Exception {
		type((char) KeyEvent.VK_SPACE);
	}
}
