/**
 * StickyTerminalPlugin
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
import omega.ui.popup.OPopupItem;

import java.net.URL;

import omega.IDE;
import omega.Screen;

import omega.io.IconManager;

import omega.plugin.Plugin;
import omega.plugin.PluginCategory;
public class StickyTerminalPlugin implements Plugin{
	private StickyTerminal terminal;

	private OPopupItem item;
	
	@Override
	public boolean init() {
		item = new OPopupItem(Screen.getScreen().getToolMenu().toolsPopup, "New Sticky Terminal", IconManager.fluentconsoleImage, ()->{
			terminal = new StickyTerminal(Screen.getScreen());
			terminal.setVisible(true);
		});
		return true;
	}
	
	@Override
	public boolean enable() {
		Screen.getScreen().getToolMenu().toolsPopup.addItem(item);
		return true;
	}
	
	@Override
	public boolean disable() {
		Screen.getScreen().getToolMenu().toolsPopup.removeItem("New Sticky Terminal");
		return true;
	}
	
	@Override
	public String getName() {
		return "Sticky Terminal";
	}
	
	@Override
	public String getVersion() {
		return "v2.2";
	}
	
	@Override
	public boolean needsRestart() {
		return false;
	}
	
	@Override
	public String getPluginCategory() {
		return PluginCategory.UTILITY;
	}
	
	@Override
	public String getDescription() {
		return "Sticky Terminals";
	}
	
	@Override
	public String getAuthor() {
		return "Omega UI";
	}
	
	@Override
	public String getLicense() {
		return "GNU GPL v3";
	}
	
	@Override
	public String getSizeInMegaBytes() {
		return "0.010 MB";
	}
	
	@Override
	public URL getImage() {
		try{
			return IconManager.class.getResource("/fluent-icons/icons8-console-50.png");
		}
		catch(Exception e){
		}
		return null;
	}
	
}
