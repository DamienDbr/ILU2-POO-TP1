package histoire;
import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {
	public static void main(String[] args) {
		Etal etal = new Etal();
		//etal.libererEtal();
		Gaulois obelix = new Gaulois("Obélix", 25);
		Gaulois asterix = new Gaulois("Astérix",13);
		etal.occuperEtal(asterix, "nems", 20);
		etal.acheterProduit(5, obelix);
		System.out.println("Fin du test");
		}
}
