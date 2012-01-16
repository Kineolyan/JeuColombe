package util;

import java.util.Iterator;

public class MatriceIterator<E> implements Iterator<E> {
	private Matrice<E> m_matrice;
	private int m_taille;
	private int m_i;
	private int m_j;
	
	public MatriceIterator(Matrice<E> matrice) {
		m_matrice = matrice;
		m_taille = m_matrice.taille();
		m_i = 0;
		m_j = 0;
	}
	
	public boolean hasNext() {
		while (m_i < m_taille) {
			while (m_j < m_taille) {
				return true;
			}
			m_j = 0;
			++m_i;
		}
		return false;
	}

	public E next() {
		return hasNext()? m_matrice.get(m_i, m_j++): null;
	}

	public void remove() {
		m_matrice.remove(m_i, m_j);		
	}

}
