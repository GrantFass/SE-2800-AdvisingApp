package msoe.se2800_2ndGroup;

import org.junit.jupiter.api.Assertions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

public class ImportTranscriptTest {
    ImportTranscript importer;

    /**
     * TODO
     *
     * @author : First Last
     * @since : Tue, 20 Apr 2021
     */
    @BeforeEach
    void setUp() {
        importer = new ImportTranscript();
        //probably import sample files
    }

    /**
     * TODO
     *
     * @author : First last
     * @since : Tue, 20 Apr 2021
     */
    @Test
    void checkLineForIgnoredWordsAndFailedClassesAndWithdrawnClasses() throws NoSuchMethodException {
        //TODO: changed method name, should be updated once merged
        Method checkString = ImportTranscript.class.getDeclaredMethod("checkLineForIgnoredWordsAndFailedClassesAndWithdrawnClasses", String.class);
        checkString.setAccessible(true);
    };

    /**
     * TODO
     *
     * @author : First last
     * @since : Tue, 20 Apr 2021
     */
    @Test
    void checkStringForCourseCode() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method checkString = ImportTranscript.class.getDeclaredMethod("checkStringForCourseCode", String.class);
        checkString.setAccessible(true);
        //region Contains course code
        Assertions.assertEquals("CS3860", checkString.invoke(importer, "CS3860"));
        Assertions.assertEquals("CS-3860", checkString.invoke(importer, "CS-3860"));
        //endregion

        //region Does not contain course code
        Assertions.assertNull(checkString.invoke(importer, "CS.3860"));
        Assertions.assertNull(checkString.invoke(importer, "CS--3860"));
        Assertions.assertNull(checkString.invoke(importer, "CS"));
        //endregion

    }

    /**
     * TODO
     *
     * @author : First last
     * @since : Tue, 20 Apr 2021
     */
    @Test
    void readInFile(){
        // TODO: figure out how to test overloaded methods :(
    }
}
