/* ###
 * IP: GHIDRA
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ghidra.app.plugin.core.decompile.actions;

import docking.action.MenuData;
import ghidra.app.plugin.core.decompile.DecompilerActionContext;
import ghidra.app.util.HelpTopics;
import ghidra.util.HelpLocation;

public class ToggleRegisterDeadcodeAction extends AbstractDecompilerAction {
	private final static String OPTIONS_TITLE = "Decompiler";

	public ToggleRegisterDeadcodeAction() {
		super("ToggleRegisterDeadcode");
		setPopupMenuData( new MenuData( new String[]{ "ToggleDeadcode" , "Register" }, "Decompile" ) );
	}

	@Override
	protected boolean isEnabledForDecompilerContext(DecompilerActionContext context) {
		return true;
	}
	
	@Override
	protected void decompilerActionPerformed(DecompilerActionContext context) {
		context.getComponentProvider().toggleDeadcodeDelay("register");
	}
	
}
