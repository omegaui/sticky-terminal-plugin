/**
 * StickyTerminalSettingsProvider
 * Copyright (C) 2022 Omega UI

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package omega.ui.window;
import omega.ui.component.jediterm.JetTermSettingsProvider;

import java.io.File;

import com.jediterm.terminal.emulator.ColorPalette;

import java.awt.Font;
import java.awt.Color;

import com.jediterm.terminal.ui.settings.DefaultSettingsProvider;

import static omega.io.UIManager.*;
import static omegaui.component.animation.Animations.*;

public class StickyTerminalSettingsProvider extends DefaultSettingsProvider{

	private Font terminalFont = new Font(StickyTerminal.preferences.fontName, StickyTerminal.preferences.fontState, StickyTerminal.preferences.fontSize);
	
	private static Color[] colors = new Color[16];
	static{
		colors[0] = glow;
		colors[1] = TOOLMENU_COLOR2;
		colors[2] = TOOLMENU_COLOR1;
		colors[3] = TOOLMENU_COLOR5;
		colors[4] = TOOLMENU_COLOR3;
		colors[5] = Color.decode("#FF8C42");
		colors[6] = Color.decode("#FBB13C");
		colors[7] = Color.decode("#D81159");
		colors[8] = Color.decode("#E0777D");
		colors[9] = Color.decode("#8E3B46");
		colors[10] = Color.decode("#A2AD59");
		colors[11] = Color.decode("#92140C");
		colors[12] = Color.decode("#253237");
		colors[13] = Color.decode("#5C6B73");
		colors[14] = Color.decode("#4C2719");
		colors[15] = isDarkMode() ? Color.decode("#242424") : c2;
	}
	
	@Override
	public Font getTerminalFont() {
		return terminalFont;
	}
	@Override
	public boolean useInverseSelectionColor() {
		return true;
	}
	@Override
	public boolean emulateX11CopyPaste() {
		return File.separator.equals(":");
	}
	@Override
	public boolean useAntialiasing() {
		return true;
	}
	@Override
	public boolean audibleBell() {
		return true;
	}
	@Override
	public boolean scrollToBottomOnTyping() {
		return true;
	}
	@Override
	public ColorPalette getTerminalColorPalette() {
		return new ColorPalette(){
			@Override
			public Color[] getIndexColors(){
				return colors;
			}
		};
	}
	@Override
	public float getTerminalFontSize() {
		return (float)terminalFont.getSize();
	}
}

