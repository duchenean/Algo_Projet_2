package be.uclouvain.lsinf1103.exceptions;

import java.util.IllegalFormatCodePointException;

/**
 * Created by Antoine on 07/05/2015.
 */
public class AlreadyExistingIDException extends IllegalArgumentException {
    public AlreadyExistingIDException(int id) {
        super("this ID (" + id + ") is already taken");
    }
}
