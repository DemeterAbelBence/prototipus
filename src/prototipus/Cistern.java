package prototipus;
//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Cistern.java
//  @ Date : 4/22/2023
//  @ Author : 
//
//

import java.io.IOException;

import javax.imageio.ImageIO;

/**A csőrendszer ciszternáinak osztálya, mely befogadja a bele érkező vizet, eltárolja, azonban
 *kimenete nincs, tehát nem folyik ki belőle a víz, amire egyszer belekerült.
 **/
public class Cistern extends Component implements Updateable {
	/**Az osztály konstruktora, az őstől örökölt attribútumok alapértékének
	 *beállítása.
	 **/
	public Cistern() {
		pumpPlaceable = false;
		node = true;
		itemSource = true;
		broken = false;
		leaks = false;
		try {
			sprite = ImageIO.read(this.getClass().getResource("cistern.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**Nem tartozik hozzá implementáció, a függvény törzse üres.**/
	public void repaired() {
	}
	/**Nem tartozik hozzá implementáció, a függvény törzse üres.**/
	public void malfunction() {
	}
	/**Nem tartozik hozzá implementáció, a függvény törzse üres.**/
	public void punctured() {
	}
	/**Nem tartozik hozzá implementáció, a függvény törzse üres.**/
	public void updateStatus() {}

	/**A vízfolyásért felelős függvény*/
	public void waterFlows() {
		addWater();
	}
	/**Ciszterna String-esítése
	 * @return String*/
	public String toString() {
		return "Cistern";
	}
}
