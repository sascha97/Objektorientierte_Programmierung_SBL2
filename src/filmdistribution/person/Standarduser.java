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

/**
 * This is the standard user that can only rent 5 movies and the price of the movies is dependent on the number of
 * rented movies.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 26 Januar 2018
 *
 * @see filmdistribution.person.User
 */
public class Standarduser extends User {
    //constant for the number of maximal rentable movies for the user
    private final static int MAX_RENTABLE_MOVIES = 5;

    /**
     * This constructs a new Standarduser
     *
     * @param surname - surname of the standarduser
     * @param lastname - lastname of the standarduser
     * @param birthyear - year of birth of the standard user
     */
    public Standarduser(String surname, String lastname, int birthyear) {
        super(surname, lastname, birthyear, MAX_RENTABLE_MOVIES);
    }

    /**
     * This method calculates the price of the movie for an user.
     *
     * @return The price for the movie for the user
     */
    @Override
    public double getPriceForMovie() {
        //the price is by default infinity
        double price = Double.POSITIVE_INFINITY;

        //if the user can rent movies than the price will be changed
        if(super.canRentAnotherMovie()) {
            price = 0.5 * (super.getNumberOfRentedMovies() + 1);
        }

        return price;
    }
}
