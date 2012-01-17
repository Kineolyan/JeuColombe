package jeucolombe;

import processing.core.PApplet;

public class Ligne extends util.ProcessingObject {
	private Point[] m_points;
	
	private int m_elements;
	
	private int m_directionX;
	
	private int m_directionY;
	
	private Point m_pointAjoute;
	
	public Ligne(int directionX, int directionY, PApplet viewer) {
		super(viewer);
		
		m_directionX = directionX;
		m_directionY = directionY;
		m_points = new Point[5];
		m_elements = 0;
		m_pointAjoute = null;
	}
	
	public void ajouter(Point p) {
		m_points[m_elements++] = p;
		if (Point.Etat.ACTIVE!=p.etat()) {
			m_pointAjoute = p;
		}
	}
	
	public void afficher() {
		m_viewer.strokeWeight(1);
		m_viewer.stroke(0);
		m_viewer.line(m_points[0].x(), m_points[0].y(),
				m_points[4].x(), m_points[4].y());		
	}
	
	public void tracer() {
		if (5!=m_elements) {
			return;
		}
		
		Point p = null;
		for (int i=0; i<5; ++i) {
			p = m_points[i];
			
			if (i<4) {
				p.lier(m_directionX, m_directionY);
			}
			if (i>0) {
				p.lier(-m_directionX, -m_directionY);					
			}
			
			p.activer();
		}
		afficher();
	}
	
	public void effacer() {		
		m_viewer.strokeWeight(1);
		m_viewer.stroke(223);
		m_viewer.line(m_points[0].x(), m_points[0].y(),
				m_points[4].x(), m_points[4].y());
		
		Point p = null;
		for (int i=0; i<5; ++i) {
			p = m_points[i];
			
			if (i<4) {
				p.delier(m_directionX, m_directionY);
			}
			if (i>0) {
				p.delier(-m_directionX, -m_directionY);					
			}
			
			p.activer();
		}
		m_pointAjoute.desactiver();
	}
}
