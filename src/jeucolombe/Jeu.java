package jeucolombe;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import processing.core.PApplet;
import util.Matrice;

public class Jeu {
	private JeuColombe m_viewer;
	
	private int m_largeur;
	private int m_hauteur;
	private int m_nbPoints;
	
	private int m_pas;
	private int m_decalage;
	private int m_centreX;
	private int m_centreY;
	
	private Matrice<Point> m_points;
	private List<Ligne> m_historique;
	private ListIterator<Ligne> m_positionHistorique;
	private int m_indexHistorique;
	
	private int m_score;

	public Jeu(int largeur, int hauteur, int nbPoints, JeuColombe viewer) {
		m_viewer = viewer;
		
		m_largeur = largeur;
		m_hauteur = hauteur;
		m_nbPoints = nbPoints;
		
		m_pas = (m_largeur < m_hauteur)? m_largeur/m_nbPoints: m_hauteur/m_nbPoints;
		m_decalage = m_pas/2;
		
		m_points = new Matrice<Point>(m_nbPoints, null);
		
		m_historique = new LinkedList<Ligne>();
		m_positionHistorique = m_historique.listIterator();
		m_indexHistorique = 0;
		
		m_score = 0;
		m_centreX = 0;
		m_centreY = 0;
	}
	
	public void initialiser() {
		tracerGrille();
		
		int departX = m_nbPoints/2 - 2, departY = departX, i;
		
		for (i = 0; i<4; ++i) {
			ajouterPoint(departX, --departY);
		}
		
		for (i = 0; i<3; ++i) {
			ajouterPoint(++departX, departY);
		}
		
		for (i = 0; i<3; ++i) {
			ajouterPoint(departX, ++departY);
		}
		
		for (i = 0; i<3; ++i) {
			ajouterPoint(++departX, departY);
		}

		for (i = 0; i<3; ++i) {
			ajouterPoint(departX, ++departY);
		}

		for (i = 0; i<3; ++i) {
			ajouterPoint(--departX, departY);
		}
		
		for (i = 0; i<3; ++i) {
			ajouterPoint(departX, ++departY);
		}

		for (i = 0; i<3; ++i) {
			ajouterPoint(--departX, departY);
		}

		for (i = 0; i<3; ++i) {
			ajouterPoint(departX, --departY);
		}

		for (i = 0; i<3; ++i) {
			ajouterPoint(--departX, departY);
		}

		for (i = 0; i<3; ++i) {
			ajouterPoint(departX, --departY);
		}
		
		for (i = 0; i<3; ++i) {
			ajouterPoint(++departX, departY);
		}
	}
	
	public int coordonnee(int index) {
		return m_decalage + index * m_pas;
	}
	
	public int index(int position) {
		return position / m_pas;
	}
	
	public int direction(int depart, int arrivee) {
		if (depart > arrivee) {
			return -1;
		}
		else if (depart < arrivee) {
			return 1;
		}
		else {
			return 0;
		}
	}
	
	public void centre(int x, int y) {
		m_centreX = x;
		m_centreY = y;
	}
	
	private void tracerGrille() {
		//*
		m_viewer.strokeWeight(1);
		m_viewer.stroke(223);
		
		int position = m_decalage - (m_centreX / m_pas) * m_pas,
			largeur = m_largeur - m_centreX,
			hauteur = m_hauteur - m_centreY;
		for (int i=0; i<m_nbPoints; ++i, position+= m_pas) {
			m_viewer.line(position, -m_centreY, position, hauteur);
		}

		position = m_decalage - (m_centreY / m_pas) * m_pas;
		for (int i=0; i<m_nbPoints; ++i, position+= m_pas) {
			m_viewer.line(-m_centreX, position, largeur, position);
		}
		//*/
	}
	
	private void ajouterItem(int indexX, int indexY) {
		if (null==m_points.get(indexX, indexY)) {
			m_points.set(indexX, indexY, new Point(coordonnee(indexX), coordonnee(indexY), m_viewer));
		}
	}
		
	public Point ajouterPoint(int indexX, int indexY) {
		Point point = m_points.get(indexX, indexY);
		if (null==point) {
			point = new Point(coordonnee(indexX), coordonnee(indexY), m_viewer);
			m_points.set(indexX, indexY, point);
		}
		
		ajouterVoisins(indexX, indexY);
		point.activer();
		return point;
	}
	
	public void ajouterVoisins(int indexX, int indexY) {
		int x = indexX - 1,
			y = indexY - 1;
		ajouterItem(x, y);
		
		++x;
		ajouterItem(x, y);

		++x;
		ajouterItem(x, y);
		
		++y;
		ajouterItem(x, y);
		
		++y;
		ajouterItem(x, y);
		
		--x;
		ajouterItem(x, y);
		
		--x;
		ajouterItem(x, y);

		--y;
		ajouterItem(x, y);
	}
	
	public void ajouterVoisins(Point p) {
		ajouterVoisins(p.x()/m_pas, p.y()/m_pas);
	}
	
	public void clear() {
		m_points.clear();
		initialiser();
	}
	
	public void reset() {
		Iterator<Point> iterator = m_points.iterator();
		Point point = null;
		
		while (iterator.hasNext()) {
			point = iterator.next();
			if (null!=point) {
				point.effacer();
			}
		}

		m_historique.clear();
		m_positionHistorique = m_historique.listIterator();
		m_indexHistorique=0;
		
		initialiser();
		afficher();
	}
	
	public void afficherPoints() {
		Iterator<Point> iterator = m_points.iterator();
		Point point = null;
		
		while (iterator.hasNext()) {
			point = iterator.next();
			if (null!=point) {
				point.afficher();
			}
		}
	}
	
	public void afficherLignes() {
		Iterator<Ligne> iterator = m_historique.iterator();
		int i = 0;
		
		while (i<m_indexHistorique) {
			iterator.next().afficher();
			++i;
		}
	}
	
	public void afficher() {
		tracerGrille();		
		afficherLignes();
		afficherPoints();
		m_viewer.afficherScore(m_score);
	}

	public Point selectionner(int indexX, int indexY) {
		Point p = m_points.get(indexX, indexY);
		if (null!=p) {
			p.selectionner();
		}
		
		return p;
	}

	public Point deselectionner(int indexX, int indexY) {
		Point p = m_points.get(indexX, indexY);
		if (null!=p) {
			p.deselectionner();
		}
		
		return p;
	}
	
	private boolean verifierLigne(Point depart, Point arrivee) {
		int longueurX = Math.abs(arrivee.x() - depart.x()),
			longueurY = Math.abs(arrivee.y() - depart.y());
		
		if (false==(
				(0==longueurX && 4*m_pas==longueurY)
			 || (4*m_pas==longueurX && 0==longueurY)
			 || (4*m_pas==longueurX && 4*m_pas==longueurY)
				)) {
			System.out.println("ligne mal orientée ou de longeur incorrecte");
			return false;
		}
		
		int compteur = 0, 
			directionX = direction(depart.x(), arrivee.x()),
			directionY = direction(depart.y(), arrivee.y());
		Point p = null;
		for (int i=0, x = index(depart.x()), y = index(depart.y());
				i<5; ++i, x+=directionX, y+=directionY) {
			p = m_points.get(x, y);
			if (null!=p 
					&& (i==4 || p.liable(directionX, directionY))
					&& (i==0 || p.liable(-directionX, -directionY))) {
				if (Point.Etat.ACTIVE!=p.etat()) {
					++compteur;
				}
			}
			else {
				System.out.println("point inutilisable");
				return false;
			}
		}
		
		if (compteur>1) {
			System.out.println("trop de points desactive");
			return false;
		}
		
		return true;
	}
	
	public void tracerLigne(Point depart, Point arrivee) {
		if (null!=depart && null!=arrivee
				&& verifierLigne(depart, arrivee)) {
			int directionX = direction(depart.x(), arrivee.x()),
				directionY = direction(depart.y(), arrivee.y());
			Point p = null;
			Ligne ligne = new Ligne(directionX, directionY, m_viewer);
			for (int i=0, x = index(depart.x()), y = index(depart.y());
					i<5; ++i, x+=directionX, y+=directionY) {
				p = m_points.get(x, y);
				ligne.ajouter(p);
				ajouterVoisins(p);
			}
			ligne.tracer();
			
			m_positionHistorique.add(ligne);
			++m_indexHistorique;
			
			depart.activer();
			arrivee.activer();
			
			m_viewer.afficherScore(++m_score);
		}
	}
	
	public void retourHistorique() {
		if (m_positionHistorique.hasPrevious()) {
			m_positionHistorique.previous().effacer();
			m_viewer.afficherScore(--m_score);
			--m_indexHistorique;
		}
	}
	
	public void avanceHistorique() {
		if (m_positionHistorique.hasNext()) {
			m_positionHistorique.next().tracer();
			m_viewer.afficherScore(++m_score);
			++m_indexHistorique;
		}
	}
	
	public void reprendreJeu() {
		while (m_positionHistorique.hasNext()) {
			m_positionHistorique.next();
			m_positionHistorique.remove();
		}
	}
}