package jeucolombe;

import processing.core.PApplet;
import util.Matrice;

public class Point extends util.ProcessingObject {
	public enum Etat { ACTIVE, DESACTIVE, ACTIVABLE };
	private enum Couleur { NOIR, ROUGE, GRIS, BLANC };
	
	private int m_positionX;
	private int m_positionY;
	
	private Etat m_etat;
	private boolean m_selectionne;
	
	private Matrice<Boolean> m_liaisons;
	
	public Point(int positionX, int positionY, PApplet viewer) {
		super(viewer);
		
		m_positionX = positionX;
		m_positionY = positionY;
		m_etat = Etat.DESACTIVE;
		m_selectionne = false;
		m_liaisons = new Matrice<Boolean>(3, true);
		
		colorer(Couleur.GRIS);
	}
	
	public int x()
	{	return m_positionX;	}
	
	public int y()
	{	return m_positionY;	}
	
	public Etat etat()
	{	return m_etat;	}
	
	public boolean liable(int directionX, int directionY)
	{	return m_liaisons.get(1 + directionY, 1 + directionX);	}
	
	public void lier(int directionX, int directionY)
	{	m_liaisons.set(1 + directionY, 1 + directionX, false);	}
	
	public void delier(int directionX, int directionY)
	{	m_liaisons.set(1 + directionY, 1 + directionX, true);	}
	
	public void afficher() {
		switch(m_etat) {
		case ACTIVE:
			colorer(Couleur.NOIR);
			break;

		case DESACTIVE:
			colorer(Couleur.GRIS);
			break;
			
		default:
			break;
		}
	}
	
	public void activer() {
		m_etat = Etat.ACTIVE;
		colorer(Couleur.NOIR);
	}
	
	public void desactiver() {
		m_etat = Etat.DESACTIVE;
		colorer(Couleur.GRIS);
	}
	
	public void selectionner() {
		m_selectionne = true;
		colorer(Couleur.ROUGE);
	}
	
	public void deselectionner() {
		m_selectionne = false;
		afficher();
	}
	
	private void colorer(Couleur couleur) {
		switch(couleur) {
		case ROUGE:
			m_viewer.fill(255, 0, 0);
			m_viewer.stroke(0);
			break;
			
		case GRIS:
			m_viewer.fill(223);
			m_viewer.stroke(223);
			break;
			
		case BLANC:
			m_viewer.fill(255);
			m_viewer.stroke(255);
			break;
		
		case NOIR:
		default:
			m_viewer.fill(0);
			m_viewer.stroke(0);		
		}

		m_viewer.strokeWeight(1);
		m_viewer.ellipse(m_positionX, m_positionY, 7, 7);
	}
	
}
