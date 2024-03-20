package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}
	public int getQuantite(){
		return quantite;
	}
	public int getQuantiteDebutMarche() {
		return quantiteDebutMarche;
	}
	public String getProduit() {
		return produit;
	}

	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {
		etalOccupe = false;
		if(this.vendeur==null)
			throw new NullPointerException("this.vendeur ne doit pas être null \n");
		else {
			StringBuilder chaine = new StringBuilder(
					"Le vendeur " + vendeur.getNom() + " quitte son étal, ");
			int produitVendu = quantiteDebutMarche - quantite;
			if (produitVendu > 0) {
				chaine.append(
						"il a vendu " + produitVendu + " parmi " + produit + ".\n");
			} else {
				chaine.append("il n'a malheureusement rien vendu.\n");
			}
			return chaine.toString();
		}
	}

	public String afficherEtal() {
		if (etalOccupe) {
			return "L'étal de " + vendeur.getNom() + " est garni de " + quantite
					+ " " + produit + "\n";
		}
		return "L'étal est libre";
	}

	/* AVANT MODIFS
	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
		if (etalOccupe) {
			StringBuilder chaine = new StringBuilder();
			chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter
					+ " " + produit + " à " + vendeur.getNom());
			if (quantite == 0) {
				chaine.append(", malheureusement il n'y en a plus !");
				quantiteAcheter = 0;
			}
			if (quantiteAcheter > quantite) {
				chaine.append(", comme il n'y en a plus que " + quantite + ", "
						+ acheteur.getNom() + " vide l'étal de "
						+ vendeur.getNom() + ".\n");
				quantiteAcheter = quantite;
				quantite = 0;
			}
			if (quantite != 0) {
				quantite -= quantiteAcheter;
				chaine.append(". " + acheteur.getNom()
						+ ", est ravi de tout trouver sur l'étal de "
						+ vendeur.getNom() + "\n");
			}
			return chaine.toString();
		}
		return null;
	}*/
	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
		try {
			
		}catch(NullPointerException e) {
			e.printStackTrace();
			return null;
		}
		if(quantiteAcheter<1)
			throw new IllegalArgumentException("la quantité doit être positive \n");
		if(this.vendeur==null) {
			throw new IllegalStateException("l’étal doit être occupé \n");
		}
		
		StringBuilder chaine = new StringBuilder();
		chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter
						+ " " + produit + " à " + vendeur.getNom());
		if (quantite == 0) {
			chaine.append(", \n"+"malheureusement il n'y en a plus !");
			quantiteAcheter = 0;
		}
		if (quantiteAcheter > quantite) {
				chaine.append(", \n" +"comme il n'y en a plus que " + quantite + ", "
						+ acheteur.getNom() + " vide l'étal de "
						+ vendeur.getNom() + ".\n");
			quantiteAcheter = quantite;
			quantite = 0;
		}
		if (quantite != 0) {
			quantite -= quantiteAcheter;
			chaine.append(". \n" + acheteur.getNom()
			+ ", est ravi de tout trouver sur l'étal de "
						+ vendeur.getNom() + "\n");
		}
			
		return chaine.toString();
		//return null;
	}

	public boolean contientProduit(String produit) {
		return this.produit.equals(produit);
	}

}