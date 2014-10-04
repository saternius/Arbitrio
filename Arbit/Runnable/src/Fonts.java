import java.awt.Font;


public class Fonts {
	public static Font regFont;
	public static Font boldFont;
	public Fonts() {

			regFont = Images.biko_reg;
			regFont = regFont.deriveFont(Font.PLAIN, 13f);

			boldFont = Images.biko_bold;
			boldFont = boldFont.deriveFont(Font.PLAIN, 13f);
		
	}

	public static void setSize(int i) {
		regFont = regFont.deriveFont(Font.PLAIN, i);
		boldFont = boldFont.deriveFont(Font.PLAIN, i);
	}
}
