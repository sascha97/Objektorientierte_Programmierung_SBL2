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

package filmdistribution;

import filmdistribution.film.DistributionMovie;
import filmdistribution.person.Human;
import filmdistribution.person.Premiumuser;
import filmdistribution.person.Standarduser;
import filmdistribution.person.User;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents the database handling all the user an movie data and providing some functionality so that
 * movies can be rented, added but not yet removed. And that users can also be only added but yet not removed.
 *
 * The remove methods are not implemented.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 26 Januar 2018
 */
public class Database {
    //Lists of users and distribution movies
    private final ArrayList<DistributionMovie> movies;
    private final ArrayList<User> users;

    /**
     * Constructs a new empty database.
     */
    public Database() {
        //Assign instance variables.
        this.movies = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public ArrayList<DistributionMovie> getMovies() {
        return movies;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void insertMovie(DistributionMovie film) {
        movies.add(film);
    }

    public void readInMovie(String movieFile) {
        try {
            Scanner scanner = new Scanner(new File(movieFile));

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] information = line.split(";");

                String title = information[0].trim();
                int year = Integer.parseInt(information[1].trim());
                String genre = information[2].trim();
                int fsk = Integer.parseInt(information[3].trim());

                String d[] = information[4].trim().split(" ");
                Human director = new Human(d[0].trim(), d[1].trim());

                String actorInformation[] = information[5].trim().split(",");
                String[] actor1 = actorInformation[0].trim().split(" ");
                String[] actor2 = actorInformation[1].trim().split(" ");
                String[] actor3 = actorInformation[2].trim().split(" ");

                Human[] actors = new Human[3];
                actors[0] = new Human(actor1[0], actor1[1]);
                actors[1] = new Human(actor2[0], actor2[1]);
                actors[2] = new Human(actor3[0], actor3[1]);

                DistributionMovie distributionMovie = new DistributionMovie(title, year, genre, fsk, director, actors);

                insertMovie(distributionMovie);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void insertUser(String firstname, String lastname, int birthyear, int type) {
        User user = null;

        if(type == User.USER_STANDARD) {
            user = new Standarduser(firstname, lastname, birthyear);
        } else if(type == User.USER_PREMIUM) {
            user = new Premiumuser(firstname, lastname, birthyear);
        }

        if(user != null) {
            users.add(user);
        }
    }

    public void readInUser(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] information = line.split(";");

                String[] name = information[0].trim().split(" ");
                int birthyear = Integer.parseInt(information[1].trim());
                int userGroup = Integer.parseInt(information[2].trim());

                insertUser(name[0], name[1], birthyear, userGroup);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public User getUser(int id) {
        User user = null;

        for(User u : users) {
            if(u.getId() == id) {
                user = u;
                break;
            }
        }

        return user;
    }

    public DistributionMovie getDistributionMovie(int id) {
        DistributionMovie distributionMovie = null;

        for(DistributionMovie m : movies) {
            if(m.getId() == id) {
                distributionMovie = m;
                break;
            }
        }

        return distributionMovie;
    }

    public boolean isAllowedToRentMovie(int userId, int movieId) {
        User user = getUser(userId);
        DistributionMovie movie = getDistributionMovie(movieId);

        boolean userAllowedToRent = user.canRentAnotherMovie();
        boolean userHasFSKAge = user.getAge() >= movie.getFsk();
        boolean movieAvailable = movie.isMovieAvailable();

        return userAllowedToRent && userHasFSKAge && movieAvailable;
    }

    public void rentMovie(int userId, int movieId) {
        boolean rentingAllowed = isAllowedToRentMovie(userId, movieId);

        if(rentingAllowed) {
            User user = getUser(userId);
            DistributionMovie movie = getDistributionMovie(movieId);

            user.rentMovie(movie);
            movie.setMovieAvailable(DistributionMovie.MOVIE_UNAVAILABLE);
        } else {
            String user = getUser(userId).getName();
            String movie = getDistributionMovie(movieId).getTitle();

            String output = String.format("User %s is not allowed to rent the movie '%s' with Movie-ID %d",
                    user, movie, movieId);

            System.out.println(output);
        }
    }

    public DistributionMovie createNewCopyOfDistributionMovie(int id) {
        DistributionMovie movie = getDistributionMovie(id);

        return new DistributionMovie(movie);
    }

    public List<DistributionMovie> searchFSK(int lowerBound, int upperBound) {
        List<DistributionMovie> list = new ArrayList<>();

        for(DistributionMovie m : movies) {
            int fsk = m.getFsk();

            if(fsk >= lowerBound && fsk <= upperBound) {
                list.add(m);
            }
        }

        return list;
    }

    public List<DistributionMovie> searchGenre(String genre) {
        List<DistributionMovie> list = new ArrayList<>();

        for(DistributionMovie m : movies) {
            //to lower case just to prevent spelling mistakes
            if(m.getGenre().toLowerCase().contains(genre.toLowerCase())) {
                list.add(m);
            }
        }

        return list;
    }

    public List<DistributionMovie> searchName(String name) {
        List<DistributionMovie> list = new ArrayList<>();

        for(DistributionMovie m : movies) {
            if(containsName(name, m.getDirector()) || containsName(name, m.getActors())) {
                list.add(m);
            }
        }

        return list;
    }

    public List<DistributionMovie> getAvailableMovies(int userId) {
        User user = getUser(userId);
        int age = user.getAge();

        List<DistributionMovie> list = new ArrayList<>();

        for(DistributionMovie m : movies) {
            if(m.isMovieAvailable()) {
                if(age >= m.getFsk()) {
                    list.add(m);
                }
            }
        }

        return list;
    }

    public void writeMovieListInFile(List<DistributionMovie> movie, String filename) {
        try {
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);

            for(DistributionMovie m : movie) {
                bw.write(m.toString());
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDatabase(String filename) {
        try {
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write("Movie:");
            bw.newLine();

            for(DistributionMovie m : movies) {
                bw.write(m.toString());
                bw.newLine();
            }

            bw.write("User:");
            bw.newLine();

            for(User u : users) {
                bw.write(u.toString());
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "String string"; //TODO: implement
    }

    //helper method to check if an human has the name
    private boolean containsName(String name, Human ... human) {
        boolean result = false;

        for(Human h : human) {
            if(h.getName().toLowerCase().contains(name.toLowerCase())) {
                result = true;
                break;
            }
        }

        return result;
    }

    public static void main(String[] args) {
        Database data = new Database();
        data.readInMovie(".\\Input_movies.txt"); //change this to ./ on unix
        data.readInUser(".\\Input_users.txt"); //change this to ./ on unix

        // Film mit ID 1 (Titel: The Terminal) wird nochmal mit ID 9 eingefuegt
        data.insertMovie(data.createNewCopyOfDistributionMovie(1));

        // User Erika (ID 2) leiht Film mit ID 9 (Titel: The Terminal) aus
        data.rentMovie(2, 9);

        // User Erika (ID 2, Alter 4) versucht Film mit ID 3 (Titel: The Greatest Showman, FSK: 6) aus.
        // Darf nicht wegen Alter
        data.rentMovie(2, 3);

        // User Max (ID 1, Standarduser) versucht gleichen Film auszuleiehen, den User Erika (ID 2) schon ausgeliehen hat
        // 1. Film wuerde 0.5 kosten, darf ihn aber nicht ausleihen
        int numMov = data.getUser(1).getNumberOfRentedMovies()+1;
        System.out.println("The " + numMov +". movie for " + data.getUser(1).getName() + " costs " + data.getUser(1).getPriceForMovie());
        data.rentMovie(1, 9);

        // User Max (ID 1, Standarduser) leiht 5 Filme aus
        // 1. Film kostet 0.5, 2. Film kostet 1, 3. Film kostet 1.5, 4. Film kostet 2, 5. Film kostet 2.5
        numMov = data.getUser(1).getNumberOfRentedMovies()+1;
        System.out.println("The " + numMov +". movie for " + data.getUser(1).getName() + " costs " + data.getUser(1).getPriceForMovie());
        data.rentMovie(1, 1);

        numMov = data.getUser(1).getNumberOfRentedMovies()+1;
        System.out.println("The " + numMov +". movie for " + data.getUser(1).getName() + " costs " + data.getUser(1).getPriceForMovie());
        data.rentMovie(1, 2);

        numMov = data.getUser(1).getNumberOfRentedMovies()+1;
        System.out.println("The " + numMov +". movie for " + data.getUser(1).getName() + " costs " + data.getUser(1).getPriceForMovie());
        data.rentMovie(1, 3);

        numMov = data.getUser(1).getNumberOfRentedMovies()+1;
        System.out.println("The " + numMov +". movie for " + data.getUser(1).getName() + " costs " + data.getUser(1).getPriceForMovie());
        data.rentMovie(1, 4);

        numMov = data.getUser(1).getNumberOfRentedMovies()+1;
        System.out.println("The " + numMov +". movie for " + data.getUser(1).getName() + " costs " + data.getUser(1).getPriceForMovie());
        data.rentMovie(1, 5);

        // User Max (ID 1, Standarduser) versucht 6. Film auszuleihen
        // Kosten tut er unendlich, darf ihn nicht ausleihen, nach Versuch hat er immer noch 5 Filme ausgeliehen (6. Film kostet wieder unendlich)
        numMov = data.getUser(1).getNumberOfRentedMovies()+1;
        System.out.println("The " + numMov +". movie for " + data.getUser(1).getName() + " costs " + data.getUser(1).getPriceForMovie());
        data.rentMovie(1, 6);
        numMov = data.getUser(1).getNumberOfRentedMovies()+1;
        System.out.println("The " + numMov +". movie for " + data.getUser(1).getName() + " costs " + data.getUser(1).getPriceForMovie());

        // User Laura (ID 4, Premiumuser) leiht zwei Filme aus
        // Jeder Film kostet 0.5
        numMov = data.getUser(4).getNumberOfRentedMovies()+1;
        System.out.println("The " + numMov +". movie for " + data.getUser(4).getName() + " costs " + data.getUser(4).getPriceForMovie());
        data.rentMovie(4, 8);
        numMov = data.getUser(4).getNumberOfRentedMovies()+1;
        System.out.println("The " + numMov +". movie for " + data.getUser(4).getName() + " costs " + data.getUser(4).getPriceForMovie());

        // Ausleihbare Filme fuer User werden jeweils in einer Datei ausgegeben
        // Fuer User mit ID 1 ausleihbar: Filme mit ID 6, 7
        // Fuer User mit ID 2 ausleihbar: keine
        // Fuer User mit ID 3 ausleihbar: Filme mit ID 6
        // Fuer User mit ID 4 ausleihbar: keine
        // Fuer User mit ID 5 ausleihbar: keine
        for (int i = 0; i < data.users.size(); ++i) {
            String filename = ".\\Movies_available_for_"; //change this to ./name on unix
            int id = data.users.get(i).getId();
            filename += Integer.toString(id) + ".txt";
            data.writeMovieListInFile(data.getAvailableMovies(id), filename);
        }

        // Alle Filme, die Crime als Genre haben werden in Datei ausgegeben
        // In Datei vorhanden: Filme mit ID 5, 6
        data.writeMovieListInFile(data.searchGenre("Crime"), ".\\Movies_with_Crime.txt"); //change this to ./name on unix

        // Alle Filme, die FSK zwischen 8 und 17 haben werden in Datei ausgegeben
        // In Datei vorhanden: Filme mit ID 4, 5, 6, 8
        data.writeMovieListInFile(data.searchFSK(8, 17), ".\\Movies_with_FSK_8_17.txt"); //change this to ./name on unix

        // Alle Filme, bei denen min. ein Schauspieler oder der Regisseur Tom heisst werden in Datei ausgegeben
        // In Datei vorhanden: Filme mit ID 1, 2, 4, 8, 9
        data.writeMovieListInFile(data.searchName("Tom"), ".\\Movies_with_Tom.txt"); //change this to ./name on unix

        // Alle Filme, bei denen min. ein Schauspieler oder der Regisseur Tom heisst werden in Datei ausgegeben
        // In Datei vorhanden: Filme mit ID 6
        data.writeMovieListInFile(data.searchName("Tarantino"), ".\\Movies_with_Tarantino.txt"); //change this to ./name on unix

        // Alle Filme, bei denen min. ein Schauspieler oder der Regisseur Tom heisst werden in Datei ausgegeben
        // In Datei vorhanden: keine Filme
        data.writeMovieListInFile(data.searchName("Zeta"), ".\\Movies_with_Zeta.txt"); //change this to ./name on unix

        // Alle Filme (9 Stueck) und User (5 Stueck) werden in Datei ausgegeben
        data.writeDatabase(".\\Output_Database.txt"); //change this to ./name on unix
    }
}
