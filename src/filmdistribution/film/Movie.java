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
 * This is the basic representation of a movie. A movie has some information about the title, publishingYear, genre,
 * the rated age information when it is allowed to watch the movie (FSK), a director and the tree main actors.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 26 Januar 2018
 */
public class Movie {
    //constants for the FSK values
    public final int FSK_0 = 0;
    public final int FSK_6 = 6;
    public final int FSK_12 = 12;
    public final int FSK_16 = 16;
    public final int FSK_18 = 18;

    //final attributes of the movie
    private final String title;
    private final int publishingYear;
    private final String genre;
    private final int fsk;
    private final Human director;
    private final Human[] actors;

    /**
     * This constructs a new movie. With the given parameters.
     *
     * The parameters are not checked for valid input, so it can be possible to create movies that are not existent.
     *
     * @param title - The title of the movie
     * @param publishingYear - The year when the movie was published
     * @param genre - The genre of the movie
     * @param fsk - The rated age information of the movie
     * @param director - The director (a human) who directed the movie
     * @param actors - An array of 3 actors that played in the movie
     */
    public Movie(String title, int publishingYear, String genre, int fsk, Human director, Human[] actors) {
        //just assign the values
        this.title = title;
        this.publishingYear = publishingYear;
        this.genre = genre;
        this.fsk = fsk;
        this.director = director;
        this.actors = actors;
    }

    /**
     * This method returns the title of the movie.
     *
     * @return title of the movie
     */
    public String getTitle() {
        return title;
    }

    /**
     * This method returns the publishing year of the movie.
     *
     * @return publishing Year of the movie
     */
    public int getPublishingYear() {
        return publishingYear;
    }

    /**
     * This method returns the genre of the movie.
     *
     * @return genre of the movie
     */
    public String getGenre() {
        return genre;
    }

    /**
     * This method returns the FSK age rating of the movie.
     *
     * @return the fsk age rating
     */
    public int getFsk() {
        return fsk;
    }

    /**
     * This method returns the director of the movie.
     *
     * @return director of the movie.
     */
    public Human getDirector() {
        return director;
    }

    /**
     * This method returns an array that contains the actors of the movie.
     *
     * @return array with the actors
     */
    public Human[] getActors() {
        return actors;
    }

    /**
     * Returns some basic information about the object, like it was specified in the description.
     *
     * @return A string containing all information about the movie.
     */
    @Override
    public String toString() {
        return String.format("%s (%d)%nGenre: %s%nFSK: %d%nDirector: %s%nActor: %s, %s, %s%n",
                getTitle(), getPublishingYear(), getGenre(), getFsk(), getDirector().getName(),
                actors[0].getName(), actors[1].getName(), actors[2].getName());
    }
}
