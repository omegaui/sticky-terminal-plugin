/**
 * StickyTerminal
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
import omega.Screen;

import omega.ui.component.jediterm.JetTerminal;

import omega.io.IconManager;

import omegaui.component.TextComp;
import omegaui.component.FlexPanel;

import java.awt.Window;

import java.awt.geom.RoundRectangle2D;

import javax.swing.JWindow;
import javax.swing.JPanel;

import static omega.io.UIManager.*;
import static omegaui.component.animation.Animations.*;

public class StickyTerminal extends JWindow{

	public static StickyTerminalPreferences preferences;
	static{
		preferences = new StickyTerminalPreferences();
	}

	public TextComp iconComp;
	public TextComp titleComp;
	public TextComp settingsComp;
	public TextComp closeComp;
	public FlexPanel contentPane;

	public JetTerminal terminal;

	public StickyTerminalSettingsProvider settingsProvider;

	public StickyTerminal(Window window){
		super(window);
		contentPane = new FlexPanel(null, c2, null);
		setContentPane(contentPane);

		setSize(preferences.defaultSize);
		setLocationRelativeTo(window);

		init();
	}

	public void init(){
		iconComp = new TextComp(IconManager.fluentconsoleImage, 20, 20, c2, c2, c2, null);
		iconComp.setArc(0, 0);
		iconComp.setClickable(false);
		add(iconComp);

		titleComp = new TextComp("Sticky Terminal", c2, c2, glow, null);
		titleComp.setFont(PX14);
		titleComp.setArc(0, 0);
		titleComp.setClickable(false);
		titleComp.attachDragger(this);
		add(titleComp);

		settingsComp = new TextComp(IconManager.fluentsettingsImage, 20, 20, TOOLMENU_COLOR1_SHADE, c2, c2, this::showSettings);
		settingsComp.setArc(0, 0);
		add(settingsComp);

		closeComp = new TextComp(IconManager.fluentcloseImage, 20, 20, TOOLMENU_COLOR2_SHADE, c2, glow, this::dispose);
		closeComp.setArc(0, 0);
		add(closeComp);

		putAnimationLayer(settingsComp, getImageSizeAnimationLayer(20, +5, true), ACTION_MOUSE_ENTERED);
		putAnimationLayer(closeComp, getImageSizeAnimationLayer(20, +5, true), ACTION_MOUSE_ENTERED);
	}

	public void showSettings(){
		preferences.showSettings(this);
	}

	@Override
	public void dispose(){
		terminal.exit();
		super.dispose();
	}

	@Override
	public void setSize(int width, int height){
		super.setSize(width, height);
		setShape(new RoundRectangle2D.Double(0, 0, width, height, 20, 20));
	}

	@Override
	public void setVisible(boolean value){
		if(value){
			terminal = new JetTerminal(new String[]{
				Screen.onWindows() ? "cmd.exe" : "/bin/bash"
			}, System.getProperty("user.home"), settingsProvider = new StickyTerminalSettingsProvider());
			terminal.setBackground(c2);
			terminal.setOnProcessExited(this::dispose);
			terminal.start();
			add(terminal);
		}
		super.setVisible(value);
	}

	@Override
	public void layout(){
		iconComp.setBounds(0, 0, 25, 25);
		titleComp.setBounds(25, 0, getWidth() - 75, 25);
		settingsComp.setBounds(getWidth() - 50, 0, 25, 25);
		closeComp.setBounds(getWidth() - 25, 0, 25, 25);
		terminal.setBounds(0, 25, getWidth(), getHeight() - 25);
		super.layout();
	}
}
