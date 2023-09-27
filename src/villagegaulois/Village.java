package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtalsMax) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtalsMax);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	public class Marche{
		private Etal etals[];
		private int nbEtals;
		
		public Marche(int nbEtals) {
			this.nbEtals = nbEtals;
			Etal [] etals = new Etal[nbEtals];
			
		}
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			assert indiceEtal < nbEtals : "Le nombre d'indice est supérieur à la taille du tableau";
			assert indiceEtal >=0 : "L'indice ne peut pas être négatif";
			if(etals[indiceEtal].isEtalOccupe()==false) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}
				
		}
		private int trouverEtalLibre() {
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].isEtalOccupe()==false) {
					return i;
					/*l'etal i est disponible */
				}
			}
			return -1;
		}
		private Etal[] trouverEtals(String produit) {
			int j=0;
			Etal[] tabEtalsOuOnVendLeProduit = new Etal[j];
			
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].isEtalOccupe()) {
					if(etals[i].contientProduit(produit)) {
						tabEtalsOuOnVendLeProduit[j] = etals[i];
						j+=1;
					}
				}
			}
			return tabEtalsOuOnVendLeProduit;
		}
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].isEtalOccupe()) {
					if(etals[i].getVendeur()==gaulois) {
						return etals[i];
					}
				}
			}
			return null;
		}
		private String afficherMarche() {
			int nbEtalsVide = 0;
			String chaine = "";
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].isEtalOccupe()) {
					chaine += etals[i].afficherEtal();
				}
				else {
					nbEtalsVide +=1;
				}
			}
			if(nbEtalsVide>0) {
				chaine += "Il reste "+nbEtalsVide+"non utilisés dans le marché.";
			}
			return chaine;	
		}
		
		

	}
}
