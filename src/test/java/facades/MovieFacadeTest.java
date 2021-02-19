/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.MovieDTO;
import entities.Movie;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utils.EMF_Creator;

/**
 *
 * @author Jack
 */
public class MovieFacadeTest {

    private static EntityManagerFactory emf;
    private static MovieFacade facade;

    public MovieFacadeTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactory();
        facade = MovieFacade.getMovieFacade(emf);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        Movie mv1 = new Movie(2020, "AA", new String[] {"A", "B"});
        Movie mv2 = new Movie(2019, "BB", new String[] {"C", "D"});
        Movie mv3 = new Movie(2018, "AA and BB", new String[] {"E", "F"});
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Movie.deleteAllRows").executeUpdate();
            em.persist(mv1);
            em.persist(mv2);
            em.persist(mv3);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }

    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class MovieFacade.
     */
    @Test
    public void testCreate() {
        System.out.println("create");
        Movie mv = new Movie(2012, "AAA", new String[]{"E", "F"});
        MovieFacade instance = null;
        MovieDTO expResult = new MovieDTO(mv);
        MovieDTO result = instance.create(mv);
        assertEquals(expResult, result);
    }

    /**
     * Test of allMovies method, of class MovieFacade.
     */
    @Test
    public void testAllMovies() {
        System.out.println("allMovies");
        MovieFacade instance = null;
        List<Movie> expResult = new ArrayList<>();
        expResult.add(new Movie(2020, "AA", new String[]{"A", "B"}));
        expResult.add(new Movie(2019, "BB", new String[]{"C", "D"}));
        List<Movie> result = instance.allMovies();
        assertEquals(expResult, result);
    }

    /**
     * Test of getMovieById method, of class MovieFacade.
     */
    @Test
    public void testGetMovieById() {
        System.out.println("getMovieById");
        long id = 1;
        MovieFacade instance = null;
        MovieDTO expResult = new MovieDTO(new Movie(2020, "AA", new String[]{"A", "B"}));
        MovieDTO result = instance.getMovieById(id);
        assertEquals(expResult, result);
    }

    /**
     * Test of getMoviesByTitle method, of class MovieFacade.
     */
    @Test
    public void testGetMoviesByTitle() {
        System.out.println("getMoviesByTitle");
        String title = "";
        MovieFacade instance = null;
        List<Movie> expResult = new ArrayList<>();
        expResult.add(new Movie(2020, "AA", new String[] {"A", "B"}));
        expResult.add(new Movie(2018, "AA and BB", new String[]{"E", "F"}));
        List<Movie> result = instance.getMoviesByTitle("AA");
        assertEquals(expResult, result);
    }

}
