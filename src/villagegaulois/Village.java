package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche; //modif

	public Village(String nom, int nbVillageoisMaximum,int nombreEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nombreEtals); //modif
	}
	public class VillageSansChefException extends RuntimeException{
		public VillageSansChefException(String message) {
			super(message);
		}
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
		
		if(this.chef==null) {
			throw new VillageSansChefException("Le village n'a pas de chef ! \n");
		}
		
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
	//debut modifs
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() +" cherche un endroit pour vendre "+ nbProduit+" "+produit+"\n");
		if(marche.leVendeurVendDeja(vendeur)) {
			int indiceEtal = marche.numEtalVendeur(vendeur);
			indiceEtal +=1;	
			chaine.append("Le vendeur "+vendeur.getNom()+" vend des "+produit+" à l'étal n°"+indiceEtal+"\n");
		}
		else {
			int indiceEtalLibre = marche.trouverEtalLibre();
			marche.etals[indiceEtalLibre].occuperEtal(vendeur, produit, nbProduit);
			int indiceEtal = indiceEtalLibre+1;	
			chaine.append("Le vendeur "+vendeur.getNom()+" vend des "+produit+" à l'étal n°"+indiceEtal+"\n");
		}
		
		
		
		return chaine.toString();
	}
	public String rechercherVendeursProduit(String produit) {
		int nbVendeursProduit = 0;
		int longueur = marche.nbEtals;
		StringBuilder chaine = new StringBuilder();
		for(int i=0;i<longueur;i++) {
			if(marche.etals[i].isEtalOccupe()==true) {
				if(marche.etals[i].contientProduit(produit)) {
					nbVendeursProduit+=1;
				}
			}
			
		}
		if(nbVendeursProduit<=0) {
			// <= et pas == pour pouvoir faire else et éviter les erreurs 
			chaine.append("Il n'y a pas de vendeur qui propose des fleurs au marché.\n");
		}
		else if(nbVendeursProduit==1) {
			for(int i=0;i<longueur;i++) {
				if(marche.etals[i].isEtalOccupe()==true) {
					if(marche.etals[i].contientProduit(produit)) {
						//Si this.produit est null ?
						chaine.append("Seul le vendeur "+ marche.etals[i].getVendeur().getNom()+ " propose des fleurs au marché.\n");
						break;
					}
				}
				
			}
		
		}
		else {
			//nbVendeursProduit > 1
			chaine.append("Les vendeurs qui proposent des fleurs sont :\n");
			for(int i=0;i<longueur;i++) {
				if(marche.etals[i].isEtalOccupe()) {
					if(marche.etals[i].contientProduit(produit))
						chaine.append("- "+marche.etals[i].getVendeur().getNom()+"\n");
				}		
			}
		}
		return chaine.toString();
	}
	public String partirVendeur(Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		int nbVentes = etal.getQuantiteDebutMarche() -etal.getQuantite();
		return "Le vendeur "+vendeur.getNom()+" quitte son étal, il a vendu "+nbVentes+" fleurs parmi les "+etal.getQuantiteDebutMarche()+" qu'ilvoulait vendre.\n";
	}
	public String afficherMarche() {
		int nb = marche.nbEtals;
		if(nb==0) {
			return "Il n'y a pas d'étals dans ce marché";
		}
		else {
			StringBuilder chaine = new StringBuilder();
			chaine.append("Le marché du village"+ nom +" possède plusieurs étals : \n");
			for(int i=0;i<nb;i++) {
				if(marche.etals[i].isEtalOccupe())
					chaine.append(marche.etals[i].getVendeur().getNom()+" vend "+marche.etals[i].getQuantite()+" "+marche.etals[i].getProduit()+"\n");
			}			
			
			chaine.append("Il reste "+marche.nbEtalsNonUtilises() +" étals non utilisés dans le marché.\n");
			return chaine.toString();
		}
	}
	public Etal rechercherEtal(Gaulois gaulois) {
		for(int i=0;i<marche.nbEtals;i++) {
			if(marche.etals[i].isEtalOccupe())
				if(marche.etals[i].getVendeur().getNom()==gaulois.getNom())
					return marche.etals[i];
		}
		return null;
	}
	private static class Marche{
		private Etal[] etals;
		private int nbEtals;
		private Marche(int nombreEtals) {
			etals = new Etal[nombreEtals];
			for (int i = 0; i < nombreEtals; i++) {
	            etals[i] = new Etal(); 
	        }
			nbEtals = etals.length; //pb
		}
		void utiliserEtal(int indiceEtal,Gaulois vendeur,String produit,int nbProduit){
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		int trouverEtalLibre() {   //Pas précisé -> protection par défaut
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].isEtalOccupe()==false) 
					return i;
			}
			return -1;
		}
		Etal[] trouverEtals(String produit) {
			int nombreEtalsAvecProduit = 0;
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].contientProduit(produit)) {
					nombreEtalsAvecProduit+=1;
				}
			}
			int indiceTab = 0;
			Etal[] etalsAvecProduit = new Etal[nombreEtalsAvecProduit];
			for(int j=0;j<nbEtals;j++) {
				if(etals[j].contientProduit(produit))
					etalsAvecProduit[indiceTab] = etals[j];
					indiceTab+=1;
			}
			return etalsAvecProduit;
		}
		Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].getVendeur()==gaulois)
					return etals[i];
			}
			return null;
		}
		String afficherMarche(){
			int nbEtalVide = 0;
			String chaine = "";
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].isEtalOccupe())
					chaine+=etals[i].afficherEtal();
				else 
					nbEtalVide +=1;
			}
			if(nbEtalVide>0) {
				chaine += "Il reste "+nbEtalVide+ " étals non utilisés dans le marché.\n";
			}
			return chaine;
		}
		int nbEtalsNonUtilises() {
			int total = 0;
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].isEtalOccupe()==false)
					total+=1;
			}
			return total;
		}
		boolean leVendeurVendDeja(Gaulois gaulois) {
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].isEtalOccupe())
					if(etals[i].getVendeur().getNom()==gaulois.getNom())
						return true;
				
			}
			return false;
		}
		int numEtalVendeur(Gaulois gaulois) {
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].getVendeur().getNom()==gaulois.getNom())
					return i;
			}
			//ça n'arrivera pas
			return -1;
		}
		//fin modifs
	}
}