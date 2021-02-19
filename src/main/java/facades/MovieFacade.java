package facades;

import dtos.MovieDTO;
import entities.Movie;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class MovieFacade {

    private static MovieFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private MovieFacade() {
    }

    /**
     *
     * @param _emf
     * @return an instance of this facade class.
     */
    public static MovieFacade getMovieFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new MovieFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public MovieDTO create(Movie mv){
        Movie tmpMovie = new Movie(mv.getReleaseYear(), mv.getTitle(), mv.getActors());
        EntityManager em = emf.createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(tmpMovie);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new MovieDTO(mv);
    }
    
    public List<Movie> allMovies(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Movie> q = em.createQuery("SELECT m FROM Movie m", Movie.class);
        List<Movie> movies = q.getResultList();
        return movies;
    }
    
    public MovieDTO getMovieById(long id){
        EntityManager em = emf.createEntityManager();
        Movie movie = em.find(Movie.class, id);
        return new MovieDTO(movie);
    }
    
    public List<Movie> getMoviesByTitle(String title){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Movie> q = em.createQuery("SELECT m FROM Movie m WHERE m.title LIKE :title", Movie.class);
        q.setParameter("title", "%" + title + "%");
        List<Movie> movies = q.getResultList();
        return movies;
    }
}
