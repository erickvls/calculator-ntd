package ntd.calculator.api.utility;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RandomStringConstants {

    /**
     * The minimum value for the random ID generation.
     * This value is defined by the API specification.
     */
    public static final int MIN_NUMBER = 1;

    /**
     * The maximum value for the random ID generation.
     * This value is defined by the API specification.
     */
    public static final int MAX_NUMBER = 1000;

    /**
     * The method name to be used in the API request.
     * This value is defined by the API specification.
     */
    public static final String METHOD_NAME = "generateStrings";

    /**
     * The length of the random string to be generated.
     * This value is defined by the API specification.
     */
    public static final int STRING_LENGTH = 32;

    /**
     * The set of characters to be used in the generated random string.
     * This value is defined by the API specification.
     */
    public static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
}