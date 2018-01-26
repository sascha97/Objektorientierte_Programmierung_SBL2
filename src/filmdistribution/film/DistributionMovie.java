/*
 * Copyright (c) 2018. Sascha Lutzenberger. All rights reserved.
 *
 * This file is part of the project "Objektorientierte Programmierung - SBL 2"
 *
 * Redistribution and use in source and binary forms, without modification,
 * are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 * - The author of this source code has given you the permission to use this
 *   source code.
 * - Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 * - The code is not used in commercial projects, except you got the permission
 *   for using the code in any commercial projects from the author.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package filmdistribution.film;

import filmdistribution.person.Human;

/**
 * Is basically a movie that can be rented, so it has more information stored, weather the movie is available or not
 * and the unique ID of the movie in the database.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 26 Januar 2018
 *
 * @see filmdistribution.film.Movie
 */
public class DistributionMovie extends Movie {
    //public constants for declearing a movie available or not available
    public final static boolean MOVIE_AVAILABLE = true;
    public final static boolean MOVIE_UNAVAILABLE = false;

    //final attributes of the distribution movie
    private final int movieId;
    //changeable attributes
    private boolean movieAvailable;

    //static class member counter (also starts with the default id = 1
    private static int nextId = 1;

    /**
     * This constructs a new DistributionMovie. With an already existing movie.
     *
     * @param movie - The movie that should be inserted
     *
     * @see filmdistribution.film.Movie
     */
    public DistributionMovie(Movie movie) {
        //calls the other constructor with the right parameters
        this(movie.getTitle(), movie.getPublishingYear(), movie.getGenre(),
                movie.getFsk(), movie.getDirector(), movie.getActors());
    }

    /**
     * This constructs a new DistributionMovie. With the given parameters.
     *
     * The parameters are not checked for valid input, so it can be possible to create movies that are not existent.
     *
     * @param title - The title of the movie
     * @param publishingYear - The year when the movie was published
     * @param genre - The genre of the movie
     * @param fsk - The rated age information of the movie
     * @param director - The director (a human) who directed the movie
     * @param actors - An array of 3 actors that played in the movie
     *
     * @see filmdistribution.film.Movie
     */
    public DistributionMovie(String title, int publishingYear, String genre, int fsk, Human director, Human[] actors) {
        super(title, publishingYear, genre, fsk, director, actors);

        //every movie is available by default
        movieAvailable = true;

        //unique movie id calculation
        movieId = nextId;
        //increment the id counter
        nextId++;
    }

    /**
     * This method returns the unique ID assigned to the movie. Used to search for the movie.
     *
     * @return the id of the movie.
     */
    public int getId() {
        return movieId;
    }

    /**
     * This method checks weather a movie is available for renting or not.
     *
     * @return true - movie is available for renting - false the movie is already rented
     */
    public boolean isMovieAvailable() {
        return movieAvailable;
    }

    /**
     * This method allows to change the availability of the movie.+
     *
     * @param movieAvailable is the movie available for renting - yes = true; no = false;
     */
    public void setMovieAvailable(boolean movieAvailable) {
        this.movieAvailable = movieAvailable;
    }

    /**
     * Returns some basic information about the object, like it was specified in the description.
     *
     * @return A string containing all information about the distribution movie.
     */
    @Override
    public String toString() {
        //the movie information specified in the description.
        String movieInformation = "ID: " + getId() + (isMovieAvailable() ? "" : " is rented");

        //prints the new information and the movie information
        return String.format("%s%n%s", movieInformation, super.toString());
    }
}
