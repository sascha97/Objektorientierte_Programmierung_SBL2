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

package filmdistribution.person;

import filmdistribution.film.DistributionMovie;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * This represents an user which is also an human being.
 *
 * A user has basically a year of birth, an unique ID in the system. An user can also only rent
 * a maximum number of movies, and has stored the number of rented movies.
 *
 * Movies can yet only be added for renting, returning movies is not implemented yet.
 *
 * Different user groups will only be different in the maximal number of movies that they can rent, and the price they
 * have to pay for renting a movie.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 26 Januar 2018
 *
 * @see filmdistribution.person.Human
 */
public abstract class User extends Human {
    //constants for the types of user
    public static final int USER_STANDARD = 1;
    public static final int USER_PREMIUM = 2;

    //final attributes of the user
    private final int birthyear;
    private final int userId;
    private final int maxNumberMovies;
    private final List<DistributionMovie> rentedMovies;

    //static class member counter (also starts with the default id = 1)
    private static int nextId = 1;

    /**
     * Constructs an new user
     *
     * Data is not checked for valid values, so it would be possible to insert illegal values
     *
     * @param surname - Surname of the user
     * @param lastname - Lastname of the user
     * @param birthyear - Year of Birth of the user
     * @param maxNumberMovies - max number that can be rented by a user
     *
     * @see filmdistribution.person.Human
     */
    public User(String surname, String lastname, int birthyear, int maxNumberMovies) {
        //Just store the information from the constructor
        super(surname, lastname);
        this.birthyear = birthyear;
        this.maxNumberMovies = maxNumberMovies;

        //unique user id calculation
        this.userId = nextId;
        //increment the id counter
        nextId++;

        //Create empty array list for rented movies
        this.rentedMovies = new ArrayList<>();
    }

    /**
     * This method returns the id of the user
     *
     * @return id of the user
     */
    public int getId() {
        return userId;
    }

    /**
     * This method returns the maximum number of movies that can be rented by the user
     *
     * @return maximum number of movies that can be rented by the user
     */
    public int getMaxNumberMovies() {
        return maxNumberMovies;
    }

    /**
     * This method returns the number of movies that the user has rented.
     *
     * @return number of rented movies
     */
    public int getNumberOfRentedMovies() {
        //The size of the list should be enough
        return rentedMovies.size();
    }

    /**
     * This method calculates the age of an user.
     *
     * @return Age of an user.
     */
    public int getAge(){
        //get the current year of the calender and subtract the year of birth to get the age
        int year = GregorianCalendar.getInstance().get(Calendar.YEAR);
        return year - birthyear;
    }

    /**
     * This method checks if an user can rent another movie.
     *
     * @return true if the user is allowed to rent another movie - false if not
     */
    public boolean canRentAnotherMovie() {
        //this can just be true if the number of rented movies is smaller than the max number of movies that can be
        //rented
        return getNumberOfRentedMovies() < getMaxNumberMovies();
    }

    /**
     * This method adds an movie in the rented movies list
     *
     * @param movieForRental - the movie that is rented
     */
    public void rentMovie(DistributionMovie movieForRental) {
        rentedMovies.add(movieForRental);
    }

    /**
     * This method checks if a movie is already rented by the user
     *
     * @param movie - the movie that should be checked if it is already rented
     * @return true if the movie is already rented - false if the movie is not already rented
     */
    public boolean hasMovieAlreadyRented(DistributionMovie movie) {
        return rentedMovies.contains(movie);
    }

    /**
     * This method calculates the price of the movie for an user.
     *
     * @return The price for the movie for the user
     */
    public abstract double getPriceForMovie();

    /**
     * Returns some basic information about the object.
     *
     * @return A string representation of the human.
     */
    @Override
    public String toString() {
        return String.format("%d: %s (Age=%d) has rented %d of %d movies.",
                getId(), getName(), getAge(), getNumberOfRentedMovies(), getMaxNumberMovies());
    }
}
