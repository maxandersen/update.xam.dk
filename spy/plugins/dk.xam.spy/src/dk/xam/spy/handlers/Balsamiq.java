package dk.xam.spy.handlers;

import java.net.URLEncoder;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Control;

public abstract class Balsamiq {

	final String controltype;

	public Balsamiq(String ctrl) {
		this.controltype = ctrl;		
	}

	public int process(int count, StringBuilder sb, int controlCount) {
		StringBuilder spaces = new StringBuilder(count * 2);
		for (int i = 0; i < count * 2; i++) {
			spaces.append(' ');
		}
		
		if (isVisible()) {

			if (isVisual()) {
				sb.append(spaces + "<control ");
				sb.append(attr("controlID", controlCount++));

				sb.append(attr("controlTypeID", getControlType()));

				
				Rectangle bounds = getDisplayLocation();

				sb.append(attr("x", bounds.x));
				sb.append(attr("y", bounds.y));
				sb.append(attr("w", bounds.width));
				sb.append(attr("h", bounds.height));
				sb.append(attr("zOrder", count));
				sb.append(">");
				sb.append("\n");
				
				
				if (getText() != null) {
					sb.append(spaces + "<controlProperties>\n" + spaces
							+ spaces + "<text>"
							+ URLEncoder.encode(getText()).replace("+", "%20") + "</text>\n"
							+ spaces + "</controlProperties>\n");
				}
				sb.append(spaces + "</control>");
				sb.append("\n");
			}

			
			controlCount = processChildren(count, sb, controlCount);
		}
		return controlCount;
	}

	abstract public int processChildren(int count, StringBuilder sb,
			int controlCount);

	abstract boolean isVisible();
	
	abstract Rectangle getDisplayLocation();

	public String getControlType() {
		return controltype;
	}

	abstract public boolean isVisual();
	abstract public String getText();

	static String attr(String attribute, Object contents) {
		return " " + attribute + "=\"" + contents + "\" ";
	}

	static BalsamiqControl getControlTypeID(Control c) {
		
		String simpleName = c.getClass().getSimpleName();

		if (simpleName.equals("ProgressBar")) {
			return new BalsamiqControl(
					"com.balsamiq.mockups::ProgressBar", c);
		}
		if (simpleName.equals("ProgressIndicator")) {
			return new BalsamiqControl(
					"com.balsamiq.mockups::ProgressBar", c);
		}
		if (simpleName.equals("FilteredTree")) {
			return new BalsamiqControl("com.balsamiq.mockups::Tree", c);
		}

		if (simpleName.equals("Tree")) {
			return new BalsamiqControl("com.balsamiq.mockups::Tree", c);
		}
		if (simpleName.equals("CLabel")) {
			return new BalsamiqControl("com.balsamiq.mockups::Label", c);
		}
		if (simpleName.equals("Label")) {
			return new BalsamiqControl("com.balsamiq.mockups::Label", c);
		}
		if (simpleName.equals("Text")) {
			return new BalsamiqControl(
					"com.balsamiq.mockups::TextInput", c);
		}
		if (simpleName.equals("Button")) {
			if((c.getStyle() & SWT.CHECK) == SWT.CHECK) {
				return new BalsamiqControl("com.balsamiq.mockups::CheckBox", c);				
			}else if ((c.getStyle() & SWT.RADIO) == SWT.RADIO) {
				return new BalsamiqControl("com.balsamiq.mockups::RadioButton", c);				
			} else {
				return new BalsamiqControl("com.balsamiq.mockups::Button", c);
			}
			
		}
		if (simpleName.equals("Composite")) {
			return new BalsamiqControl("com.balsamiq.mockups::FieldSet",
					c);
		}
		if (simpleName.equals("StyledText")) {
			return new BalsamiqControl(
					"com.balsamiq.mockups::Paragraph", c);
		}
		if (simpleName.equals("ScrolledComposite")) {
			return new BalsamiqControl("com.balsamiq.mockups::FieldSet",
					c);
		}
		if (simpleName.equals("ToolBar")) {
			return new BalsamiqControl(
					"com.balsamiq.mockups::ButtonBar", c);
		}

		if (simpleName.equals("Shell")) {
			return new BalsamiqControl(
					"com.balsamiq.mockups::TitleWindow", c);
		} // topheight ?

		if(simpleName.equals("Group")) {
			return new BalsamiqControl("com.balsamiq.mockups::FieldSet", c);
		}
		
		if(simpleName.equals("Combo")) {
			return new BalsamiqControl("com.balsamiq.mockups::ComboBox", c);
		}
		if(simpleName.equals("TabFolder")) {
			return new BalsamiqControl("com.balsamiq.mockups::TabBar", c);
		}
		return new BalsamiqControl(c.getClass().getCanonicalName(), c);
	}

}
