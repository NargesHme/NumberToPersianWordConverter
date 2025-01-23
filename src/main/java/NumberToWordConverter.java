import java.util.*;

/**
 * A utility class for converting numeric strings into their Persian word equivalents.
 * <p>
 * This class provides a method to convert numbers represented as strings into their
 * corresponding Persian words, handling values in the range of integers. It supports
 * numbers up to the order of trillions.
 * </p>
 * <p>
 * The conversion is performed by breaking down the number into groups of three digits,
 * converting each group into words, and then combining those words with appropriate
 * suffixes (like thousand, million, etc.).
 * </p>
 * <p>
 * This class cannot be instantiated, as it contains only static methods.
 * </p>
 *
 * @author Narges.HME
 * @version 1.0
 */
public final class NumberToWordConverter {

    private enum PaddingOrder{
        LEFT,RIGHT
    }

    private static final String ZERO = "صفر";
    private static final String[] ONES = new String[] {"",
            "یک", "دو", "سه", "چهار", "پنج", "شش", "هفت", "هشت", "نه"};

    private static final String[] TENS = new String[] {"ده", "یازده", "دوازده", "سیزده", "چهارده", "پانزده", "شانزده", "هفده", "هجده", "نوزده"};

    private static final String[] SUFFIX_TENS = new String[] {"", "ده", "بیست", "سی", "چهل", "پنجاه", "شصت", "هفتاد", "هشتاد", "نود"};

    private static final String[] SUFFIX_HUNDREDS = new String[] {"", "صد", "دویست", "سیصد", "چهارصد", "پانصد", "ششصد", "هفتصد", "هشتصد", "نهصد"};

    private static final String[] SUFFIX = new String[] {"", "هزار", "میلیون", "میلیارد", "تیلیارد"};

    private NumberToWordConverter(){
        // Private constructor to prevent instantiation
    }

    /**
     * Converts a numeric string to its Persian word representation.
     *
     * @param number The numeric string to convert.
     * @return The Persian word representation of the number.
     * @throws IllegalArgumentException if the input string is not a valid number.
     */

    public static String numberToPersianWord (String number){

        List<String> alphabetNumber;
        List<String> threeDigits = new ArrayList<String>();

        // Validate input
        if (number == null || !number.matches("\\d+")) {
            throw new IllegalArgumentException("Input must be a non-negative numeric string.");
        }
        if (Integer.valueOf(number) == 0){
            return ZERO;
        }

        int part = (int)Math.ceil(number.length() / 3) ;

        // Pad the number to ensure it has a length that is a multiple of 3
        number = padString(number, '0', part*3, PaddingOrder.LEFT );
        String alphabetNumber_ = "";
        for(int i = 0; i < part; i++){
            alphabetNumber = new ArrayList<String>();
            int begin = 3 * i;
            int end = 3 * (i+1);
            String threeDigitOfNumber = number.substring(begin, end);
            if(Integer.valueOf(threeDigitOfNumber) != 0) {
                int hundred = Integer.valueOf(threeDigitOfNumber.substring(0,1));
                if (hundred != 0 ){
                    alphabetNumber.add( SUFFIX_HUNDREDS[hundred]);
                }
                int twoLastDigit = Integer.valueOf(threeDigitOfNumber.substring(1,3));
                if(twoLastDigit < 20 && twoLastDigit > 9){
                    alphabetNumber.add(TENS[twoLastDigit % 10]);
                }else{
                    int ten = Integer.valueOf(String.valueOf(threeDigitOfNumber.charAt(1)));
                    int one = Integer.valueOf(String.valueOf(threeDigitOfNumber.charAt(2)));
                    if (ten != 0) {
                        alphabetNumber.add(SUFFIX_TENS[ten]);
                    }
                    if (one != 0 ){
                        alphabetNumber.add(ONES[one]);
                    }
                }

                alphabetNumber_ = String.join(" و ", alphabetNumber);
                threeDigits.add(String.join(" " ,alphabetNumber_,String.valueOf(SUFFIX[part - (i+1)])));
            }
        }

        return String.join(" و ", threeDigits);
    }

    /**
     * Pads a string to a specified length with a given character.
     *
     * @param input  The input string to pad.
     * @param ch     The character to use for padding.
     * @param length The desired length of the output string.
     * @param order  The order in which to pad (LEFT or RIGHT).
     * @return The padded string.
     */
    private static String padString (String input, char ch, int length, PaddingOrder order){

        switch (order){
            case LEFT:
                return String.format("%" + length + "s", input).replace(' ', ch);
            case RIGHT:
                return String.format("%" + -length + "s", input).replace(' ', ch);
            default:
                return "";
        }
    }

    private static String joinResultList (List<String> input){
        String result = "";
        Iterator<String> iterator = input.iterator();
        while (iterator.hasNext()){
            String s = iterator.next();
                if(!s.equalsIgnoreCase("")){
                    if(iterator.hasNext()) {
                        result += result + s + " و ";
                    }
                    else {
                        result += result + s ;}
                }
        }
        return result;

    }

}
