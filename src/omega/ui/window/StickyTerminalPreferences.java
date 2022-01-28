/**
 * StickyTerminalPreferences
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
import omega.ui.dialog.FontChooser;

import java.awt.Dimension;
import java.awt.Font;

import omega.io.IconManager;

import java.io.File;

import omegaui.dynamic.database.DataBase;

import java.awt.geom.RoundRectangle2D;

import omegaui.component.FlexPanel;
import omegaui.component.TextComp;
import omegaui.component.SliderComp;

import omega.IDE;
import omega.Screen;

import javax.swing.JWindow;

import static omega.io.UIManager.*;
import static omegaui.component.animation.Animations.*;

public class StickyTerminalPreferences extends JWindow{

	public StickyTerminal currentStickyTerminal;

	public static final File STICKY_TERM_PREF_FILE = new File(".omega-ide", ".sticky-prefs");

	public DataBase dataBase;

	public TextComp iconComp;
	public TextComp titleComp;
	public TextComp closeComp;

	public TextComp defaultWidthLabel;
	public TextComp defaultHeightLabel;

	public SliderComp widthSlider;
	public SliderComp heightSlider;

	public TextComp currentWidthLabel;
	public TextComp currentHeightLabel;

	public SliderComp currentWidthSlider;
	public SliderComp currentHeightSlider;
	
	public TextComp terminalFontComp;
	public FontChooser fontChooser;

	public String fontName = "Ubuntu Mono";
	public int fontState = Font.BOLD;
	public int fontSize = 12;
	
	public Dimension defaultSize = new Dimension(300, 200);
	
	public StickyTerminalPreferences(){
		super(Screen.getScreen());
		FlexPanel contentPane = new FlexPanel(null, c2, null);
		setContentPane(contentPane);
		
		setSize(400, 280);
		setLocationRelativeTo(null);
		init();
	}

	public void init(){

		dataBase = new DataBase(STICKY_TERM_PREF_FILE);

		if(!dataBase.getEntries().isEmpty()){
			fontName = dataBase.getEntryAt("Terminal Font", 0).getValue();
			fontState = dataBase.getEntryAt("Terminal Font", 1).getValueAsInt();
			fontSize = dataBase.getEntryAt("Terminal Font", 2).getValueAsInt();

			defaultSize = new Dimension(dataBase.getEntryAt("Terminal Width").getValueAsInt(), dataBase.getEntryAt("Terminal Height").getValueAsInt());
		}
		
		iconComp = new TextComp(IconManager.fluentsettingsImage, 20, 20, c2, c2, c2, null);
		iconComp.setBounds(0, 0, 25, 25);
		iconComp.setArc(0, 0);
		iconComp.setClickable(false);
		add(iconComp);

		titleComp = new TextComp("Sticky Terminal Preferences", c2, c2, glow, null);
		titleComp.setBounds(25, 0, getWidth() - 50, 25);
		titleComp.setFont(PX14);
		titleComp.setArc(0, 0);
		titleComp.setClickable(false);
		titleComp.attachDragger(this);
		add(titleComp);

		closeComp = new TextComp(IconManager.fluentcloseImage, 20, 20, TOOLMENU_COLOR2_SHADE, c2, glow, this::dispose);
		closeComp.setBounds(getWidth() - 25, 0, 25, 25);
		closeComp.setArc(0, 0);
		add(closeComp);
		
		putAnimationLayer(closeComp, getImageSizeAnimationLayer(20, +5, true), ACTION_MOUSE_ENTERED);

		terminalFontComp = new TextComp("Click to change Sticky Terminal Font", TOOLMENU_COLOR6_SHADE, TOOLMENU_COLOR1_SHADE, TOOLMENU_COLOR1, this::changeFont);
		terminalFontComp.setBounds(10, 30, getWidth() - 20, 25);
		terminalFontComp.setFont(PX14);
		terminalFontComp.setArc(15, 15);
		add(terminalFontComp);

		defaultWidthLabel = new TextComp("Default Width", TOOLMENU_GRADIENT, c2, glow, null);
		defaultWidthLabel.setBounds(10, 70, 120, 40);
		defaultWidthLabel.setFont(PX14);
		defaultWidthLabel.setArc(10, 10);
		defaultWidthLabel.setClickable(false);
		add(defaultWidthLabel);

		defaultHeightLabel = new TextComp("Default Height", TOOLMENU_GRADIENT, c2, glow, null);
		defaultHeightLabel.setBounds(10, 120, 120, 40);
		defaultHeightLabel.setFont(PX14);
		defaultHeightLabel.setArc(10, 10);
		defaultHeightLabel.setClickable(false);
		add(defaultHeightLabel);

		widthSlider = new SliderComp(TOOLMENU_COLOR2_SHADE, TOOLMENU_COLOR4_SHADE, TOOLMENU_COLOR3, glow);
		widthSlider.setSlideListener((value)->{
			defaultSize = new Dimension(value, (int)defaultSize.getHeight());
		});
		widthSlider.setBounds(140, 70, getWidth() - 150, 40);
		widthSlider.setMinMaxValueTextFont(UBUNTU_PX12);
		widthSlider.setValueTextFont(widthSlider.getMinMaxValueTextFont());
		widthSlider.setMinMaxValueTextColor(TOOLMENU_COLOR3);
		widthSlider.setValueTextColor(TOOLMENU_COLOR2);
		widthSlider.setValueUnit(" px");
		widthSlider.setPaintValuesEnabled(true);
		widthSlider.setMinValue(300);
		widthSlider.setMaxValue(1000);
		widthSlider.setValue((int)defaultSize.getWidth());
		add(widthSlider);

		heightSlider = new SliderComp(TOOLMENU_COLOR2_SHADE, TOOLMENU_COLOR4_SHADE, TOOLMENU_COLOR3, glow);
		heightSlider.setSlideListener((value)->{
			defaultSize = new Dimension((int)defaultSize.getWidth(), value);
		});
		heightSlider.setBounds(140, 120, getWidth() - 150, 40);
		heightSlider.setMinMaxValueTextFont(UBUNTU_PX12);
		heightSlider.setValueTextFont(heightSlider.getMinMaxValueTextFont());
		heightSlider.setMinMaxValueTextColor(TOOLMENU_COLOR3);
		heightSlider.setValueTextColor(TOOLMENU_COLOR2);
		heightSlider.setValueUnit(" px");
		heightSlider.setPaintValuesEnabled(true);
		heightSlider.setMinValue(200);
		heightSlider.setMaxValue(700);
		heightSlider.setValue((int)defaultSize.getHeight());
		add(heightSlider);
		
		currentWidthLabel = new TextComp("Current Width", TOOLMENU_GRADIENT, c2, glow, null);
		currentWidthLabel.setBounds(10, 170, 120, 40);
		currentWidthLabel.setFont(PX14);
		currentWidthLabel.setArc(10, 10);
		currentWidthLabel.setClickable(false);
		add(currentWidthLabel);

		currentHeightLabel = new TextComp("Current Height", TOOLMENU_GRADIENT, c2, glow, null);
		currentHeightLabel.setBounds(10, 220, 120, 40);
		currentHeightLabel.setFont(PX14);
		currentHeightLabel.setArc(10, 10);
		currentHeightLabel.setClickable(false);
		add(currentHeightLabel);

		currentWidthSlider = new SliderComp(TOOLMENU_COLOR2_SHADE, TOOLMENU_COLOR4_SHADE, TOOLMENU_COLOR3, glow);
		currentWidthSlider.setSlideListener((value)->{
			currentStickyTerminal.setSize(value, currentStickyTerminal.getHeight());
		});
		currentWidthSlider.setBounds(140, 170, getWidth() - 150, 40);
		currentWidthSlider.setMinMaxValueTextFont(UBUNTU_PX12);
		currentWidthSlider.setValueTextFont(widthSlider.getMinMaxValueTextFont());
		currentWidthSlider.setMinMaxValueTextColor(TOOLMENU_COLOR3);
		currentWidthSlider.setValueTextColor(TOOLMENU_COLOR2);
		currentWidthSlider.setValueUnit(" px");
		currentWidthSlider.setPaintValuesEnabled(true);
		currentWidthSlider.setMinValue(300);
		currentWidthSlider.setMaxValue(1000);
		currentWidthSlider.setValue((int)defaultSize.getWidth());
		add(currentWidthSlider);

		currentHeightSlider = new SliderComp(TOOLMENU_COLOR2_SHADE, TOOLMENU_COLOR4_SHADE, TOOLMENU_COLOR3, glow);
		currentHeightSlider.setSlideListener((value)->{
			currentStickyTerminal.setSize(currentStickyTerminal.getWidth(), value);
		});
		currentHeightSlider.setBounds(140, 220, getWidth() - 150, 40);
		currentHeightSlider.setMinMaxValueTextFont(UBUNTU_PX12);
		currentHeightSlider.setValueTextFont(heightSlider.getMinMaxValueTextFont());
		currentHeightSlider.setMinMaxValueTextColor(TOOLMENU_COLOR3);
		currentHeightSlider.setValueTextColor(TOOLMENU_COLOR2);
		currentHeightSlider.setValueUnit(" px");
		currentHeightSlider.setPaintValuesEnabled(true);
		currentHeightSlider.setMinValue(200);
		currentHeightSlider.setMaxValue(700);
		currentHeightSlider.setValue((int)defaultSize.getHeight());
		add(currentHeightSlider);
	}

	public void changeFont(){
		if(fontChooser == null)
			fontChooser = new FontChooser(IDE.screen);
		Font fx = fontChooser.chooseFont(new Font(fontName, fontState, fontSize));
		if(fx != null){
			fontName = fx.getName();
			fontState = fx.getStyle();
			fontSize = fx.getSize();
		}
	}

	public void showSettings(StickyTerminal terminal){
		this.currentStickyTerminal = terminal;
		setVisible(true);
	}

	@Override
	public void dispose(){
		dataBase.clear();
		dataBase.addEntry("Terminal Font", fontName);
		dataBase.addEntry("Terminal Font", fontState + "");
		dataBase.addEntry("Terminal Font", fontSize + "");
		dataBase.addEntry("Terminal Width", (int)defaultSize.getWidth() + "");
		dataBase.addEntry("Terminal Height", (int)defaultSize.getHeight() + "");
		dataBase.save();
		super.dispose();
	}

	@Override
	public void setSize(int width, int height){
		super.setSize(width, height);
		setShape(new RoundRectangle2D.Double(0, 0, width, height, 20, 20));
	}
}
