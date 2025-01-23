import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class NumberToWordConverterTest {

    @ParameterizedTest
    @CsvSource(value = {"98700000000:نود و هشت میلیارد و هفتصد میلیون",
            "0:صفر",
            "10:ده",
            "23:بیست و سه",
            "12345:دوازده هزار و سیصد و چهل و پنج",
            "40:چهل"}, delimiter = ':')
    public void testNum2PersianWord(String input, String expected){
        System.out.println(input);
        String actual = NumberToWordConverter.numberToPersianWord(input);
        Assertions.assertEquals(actual.trim(), expected.trim());
    }
}
