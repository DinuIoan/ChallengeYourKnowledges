package challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges;

import org.junit.Test;

import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.database.InitializeDatabase;
import challengeyourknowledges.appsyouneed.idinu.challengeyourknowledges.model.Question;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private final static String LINE1 = "fast-limbaromana/Cine a scris opera ''Patul lui Procust''? " +
            "/ Camil Petrescu / Mircea Nedelciu / Marin Sorescu / Ion Creanga /";

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testQuestionsScript() {
        String[] parts = LINE1.split("/");
        Question question = InitializeDatabase.makeQuestion(parts);

        assertEquals("limbaromana", question.getDomain());
    }
}