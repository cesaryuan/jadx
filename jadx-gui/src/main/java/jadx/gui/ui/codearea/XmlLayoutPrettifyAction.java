package jadx.gui.ui.codearea;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

import jadx.gui.treemodel.JNode;
import jadx.gui.ui.action.ActionModel;

public class XmlLayoutPrettifyAction extends JNodeAction {

	private static final long serialVersionUID = -2682529369671695550L;

	private static final XMLOutputter PRETTIER =
			new AndroidXmlOutputter(4, 4, "android".split(","), "id,layout_width,layout_height".split(","), true, false);

	public XmlLayoutPrettifyAction(CodeArea codeArea) {
		super(ActionModel.XML_LAYOUT_PRETTIFY, codeArea);
	}

	public static String prettyPrintByJDOM(String xmlString) {
		try {
			Writer out = new StringWriter();
			PRETTIER.output(new SAXBuilder().build(new StringReader(xmlString)), out);
			return out.toString();
		} catch (Exception e) {
			throw new RuntimeException("Error occurs when pretty-printing xml:\n" + xmlString, e);
		}
	}

	@Override
	public void runAction(JNode node) {
		String originString = getCodeArea().getCodeInfo().getCodeStr();
		String prettyString = prettyPrintByJDOM(originString);
		getCodeArea().setText(prettyString);
	}

	@Override
	public boolean isActionEnabled(JNode node) {
		return true;
	}
}
