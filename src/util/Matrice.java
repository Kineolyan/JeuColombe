package util;

public class Matrice<T> {
	private int m_taille;
	private T m_valeurInitiale;
	
	Object[][] m_matrice;
	
	public Matrice(int taille, T valeurInitiale) {
		m_taille = taille;
		m_valeurInitiale = valeurInitiale;
		m_matrice = new Object[m_taille][m_taille];
		
		for (int i = 0; i < m_taille; ++i) {
			for (int j = 0; j < m_taille; ++j) {
				m_matrice[i][j] = m_valeurInitiale;
			}
		}
	}
	
	public Matrice(int taille) {
		this(taille, null);
	}
	
	public int taille()
	{	return m_taille;	}
	
	public T get(int i, int j)
	{	return (T) m_matrice[i][j];	}
	
	public void set(int i, int j, T element)
	{	m_matrice[i][j] = (Object) element;	}
	
	public void remplir(T valeur) {
		for (int i = 0; i < m_taille; ++i) {
			for (int j = 0; j < m_taille; ++j) {
				m_matrice[i][j] = valeur;
			}
		}
	}
	
	public void reset() {
		for (int i = 0; i < m_taille; ++i) {
			for (int j = 0; j < m_taille; ++j) {
				m_matrice[i][j] = m_valeurInitiale;
			}
		}
	}
	
	public void remove(int i, int j) {
		m_matrice[i][j] = null;
	}
	
	public void clear() {
		for (int i = 0; i < m_taille; ++i) {
			for (int j = 0; j < m_taille; ++j) {
				remove(i, j);
			}
		}
	}
	
	public MatriceIterator<T> iterator()
	{	return new MatriceIterator<T>(this);	}
}
