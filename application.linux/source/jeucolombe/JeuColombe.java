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
	
	private boolean m_navigationHistorique;
	
	private PFont m_police;

	private int m_centreX;
	private int m_centreY;
	private int m_offsetX;
	private int m_offsetY;
	private boolean m_enDeplacement;
	
	public JeuColombe() {
		m_largeur = 800;
		m_hauteur = 800;
		m_nbPoints = 25;
		m_jeu = new Jeu(m_largeur, m_hauteur, m_nbPoints, this);
		m_pas = (m_largeur < m_hauteur)? m_largeur/m_nbPoints: m_hauteur / m_nbPoints;
		m_selection = null;
		m_selectionEnCours = false;
		m_navigationHistorique = false;
		m_police = null;
		
		m_centreX = 0;
		m_centreY = 0;
		m_offsetX = 0;
		m_offsetY = 0;
		m_enDeplacement = false;
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
	}
	
	public void afficherScore(int score) {
		pushMatrix();
		translate(-m_centreX, -m_centreY);
		
		// Effacer le score précédent
		rectMode(CORNER);
		noStroke();
		fill(255);
		rect(0, 0, 60, 60);
		
		// Afficher un cadre
		fill(3, 41, 90, 128);
		roundedRect(5, 5, 50, 50, 10);
		
		// Mise à jour du score
		textFont(m_police, 40);
		fill(0);
		textAlign(LEFT);
		text(score, 10, 45);
		
		popMatrix();
	}
	
	private void roundedRect(int x, int y, int w, int h, int r) {
//		noStroke();
//		rectMode(CORNER);
//
//		int  ax, ay, hr;
//
//		ax=x+w-1;
//		ay=y+h-1;
//		hr = r/2;
//
//		rect(x, y, w, h);
//		arc(x, y, r, r, radians(180), radians(270));
//		arc(ax, y, r,r, radians(270), radians(360));
//		arc(x, ay, r,r, radians(90), radians(180));
//		arc(ax, ay, r,r, radians(0), radians(90));
//		rect(x, y-hr, w, hr);
//		rect(x-hr, y, hr, h);
//		rect(x, y+h, w, hr);
//		rect(x+w,y,hr, h);
		beginShape();
		curveVertex(x+r, y);
		
		curveVertex(x+r, y);
		curveVertex(x+w-r, y);
		
		curveVertex(x+w, y+r);
		curveVertex(x+w, y+h-r);
		
		curveVertex(x+w-r, y+h);
		curveVertex(x+r, y+h);
		
		curveVertex(x, y+h-r);
		curveVertex(x, y+r);

		curveVertex(x+r, y);
		curveVertex(x+r, y);
		endShape();
	}

	public static void main(String _args[]) {
		PApplet.main(new String[] { jeucolombe.JeuColombe.class.getName() });
	}
}


