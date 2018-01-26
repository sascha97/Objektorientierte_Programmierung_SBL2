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
 * This is the base class to represent any human in the program. A human has only a surname and a last name.
 *
 * @author Sascha Lutzenberger
 * @version 1.0 - 26 Januar 2018
 */
public class Human {
    //Declaration of the attributes; no changes allowed
    private final String surname;
    private final String lastname;

    /**
     * Constructs a new human
     *
     * The name is just assigned, no test if name is ""
     *
     * @param surname - surname of the human
     * @param lastname - lastname of the human
     */
    public Human(String surname, String lastname) {
        this.surname = surname;
        this.lastname = lastname;
    }

    /**
     * This method returns the surname of the human
     * @return surname of the person
     */
    public String getSurname() {
        return surname;
    }

    /**
     * This method returns the lastname of the human
     * @return lastname of the person
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * This method returns the complete name of the human "Surname Lastname"
     * @return name of the person ("Surname Lastname")
     */
    public String getName() {
        return String.format("%s %s", Human.this.getSurname(), Human.this.getLastname());
    }

    /**
     * Returns some basic information about the object.
     *
     * @return A string representation of the human.
     */
    @Override
    public String toString() {
        return String.format("Human{surname=%s, lastname=%s}", getSurname(), getLastname());
    }
}
