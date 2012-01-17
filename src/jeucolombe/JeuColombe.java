package jeucolombe;

import processing.core.PApplet;
import processing.core.PFont;

public class JeuColombe extends PApplet {
	private int m_largeur;
	private int m_hauteur;
	private int m_nbPoints;
	private Jeu m_jeu;
	private int m_pas;
	private Point m_selection;
	private boolean m_selectionEnCours;
	private boolean m_nouvellePartie;
	
	private boolean m_navigationHistorique;
	
	private PFont m_police;

	private int m_centreX;
	private int m_centreY;
	private int m_offsetX;
	private int m_offsetY;
	private boolean m_enDeplacement;
	
	private int m_largeurScore;
	
	public JeuColombe() {
		m_largeur = 800;
		m_hauteur = 800;
		m_nbPoints = 25;
		m_jeu = new Jeu(m_largeur, m_hauteur, m_nbPoints, this);
		m_pas = (m_largeur < m_hauteur)? m_largeur/m_nbPoints: m_hauteur / m_nbPoints;
		m_selection = null;
		m_selectionEnCours = false;
		m_nouvellePartie = false;
		m_navigationHistorique = false;
		m_police = null;
		
		m_centreX = 0;
		m_centreY = 0;
		m_offsetX = 0;
		m_offsetY = 0;
		m_enDeplacement = false;
		m_largeurScore = 50;
	}

	public void setup() {
	  size(800, 800);
	  background(255);
	  m_jeu.initialiser();
	  
	  m_police = createFont("Arial",40,true);
	  afficherScore(0);
	}

	public void draw() {
		if (true==mousePressed && RIGHT==mouseButton) {
			m_centreX = mouseX - m_offsetX;
			m_centreY = mouseY - m_offsetY;
			m_jeu.centre(m_centreX, m_centreY);
		}
		
		translate(m_centreX, m_centreY);
			
		if (true==mousePressed && RIGHT==mouseButton) {	
			background(255);
			m_jeu.afficher();
		}
	}
	
	public void mouseClicked() {
		if (LEFT==mouseButton) {
			int x = (mouseX - m_centreX) / m_pas,
				y = (mouseY - m_centreY) / m_pas;
		
			if (m_selectionEnCours=!m_selectionEnCours) {
				m_selection = m_jeu.selectionner(x, y);
				if (null==m_selection) {
					m_selectionEnCours = false;
				}
			}
			else {
				if (m_navigationHistorique) {
					m_navigationHistorique = false;
					m_jeu.reprendreJeu();
				}
				
				m_jeu.tracerLigne(m_selection, m_jeu.selectionner(x, y));
				
				m_selection.deselectionner();
				m_jeu.deselectionner(x, y);
				m_selection = null;
			}
		}
	}
	
	public void mousePressed() {
		if (RIGHT==mouseButton) {
			if (false==m_enDeplacement) {
				m_enDeplacement = true;
				pushMatrix();
			}
			
			cursor(HAND);
			m_offsetX = mouseX - m_centreX;
			m_offsetY = mouseY - m_centreY;
		}
	}
	
	public void mouseReleased() {
		if (RIGHT==mouseButton) {
			cursor(ARROW);
		}
	}
	
	public void keyPressed() {
		if ('z'==key) {
			m_navigationHistorique = true;
			m_jeu.retourHistorique();
		}
		else if ('y'==key && m_navigationHistorique) {
			m_jeu.avanceHistorique();
		}
		else if('c'==key) {
			if (true==m_enDeplacement) {
				m_enDeplacement = false;
				popMatrix();
				m_centreX = 0;
				m_centreY = 0;
				m_offsetX = 0;
				m_offsetY = 0;
				m_jeu.centre(m_centreX, m_centreY);
				
				background(255);
				m_jeu.afficher();
			}
		}
		else if (BACKSPACE==key) {
			m_nouvellePartie = true;
		}
		else if (ENTER==keyCode && m_nouvellePartie) {
			background(255);
			m_jeu.reset();
		}
		else {
			m_nouvellePartie = false;
		}
	}
	
	public void afficherScore(int score) {
		pushMatrix();
		translate(-m_centreX, -m_centreY);
		
		// Mise à jour du score
		textFont(m_police, 40);
		if (textWidth(str(score)) > m_largeurScore - 10) {
			m_largeurScore = (int) textWidth(str(score)) + 10;
		}
		
		// Afficher un cadre
		noStroke();
		fill(55, 148, 254);
		roundedRect(5, 5, m_largeurScore, 50, 20);
		
		// Afficher le texte
		fill(0);
		textAlign(LEFT);
		text(score, 10, 45);
		
		popMatrix();
	}
	
	private void roundedRect(int x, int y, int w, int h, int r) {
		beginShape();
		// haut
		vertex(x+r, y);
		bezierVertex(x+2*r, y, x+w-2*r, y, x+w-r, y);
		// droite
		bezierVertex( x+w, y, x+w, y, x+w, y+r);
		bezierVertex(x+w, y+2*r, x+w, y+h-2*r, x+w, y+h-r);
		// bas
		bezierVertex(x+w, y+h, x+w, y+h, x+w-r, y+h);
		bezierVertex(x+w-2*r, y+h, x+2*r, y+h, x+r, y+h);
		// droite
		bezierVertex(x, y+h, x, y+h, x, y+h-r);
		bezierVertex(x, y+2*r, x, y+2*r, x, y+r);

		bezierVertex(x, y, x, y, x+r, y);
		endShape();
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { jeucolombe.JeuColombe.class.getName() });
	}
}


