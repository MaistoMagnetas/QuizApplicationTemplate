package wallpapers.cactustree.pc.quizapplicationtemplate;

import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.List;

/**
 * Container for different constants.
 * Made final and private constructor so no instantiation is possible.
 */
@SuppressWarnings("All")
public final class QuizContract {

    private QuizContract(){
    }

    /**
     * Holds questions table info:
     *  Question, three answers, answer target and its correct answer index[1-3].
     */
    public static class QuestionsTable implements BaseColumns{

        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_ANSWER_TARGET = "answertarget";
        public static final String COLUMN_ANSWER_NUM = "answernum";
    }

    /**
     * Holds all possible answer variants.
     */
    public static class AnswerTargets {
        public static final String AT1_SLYTHERIN = "Slytherin";
        public static final String AT2_GRPYHS = "Gryphon's";
        public static final String AT3_RAVEN = "Raven claw";
        public static final String AT4_WHISTLE = "Whistle blow";

        static List<String> getAnswerTargets()
        {
            List<String> answerTargets = new ArrayList<>();
            answerTargets.add(AT1_SLYTHERIN);
            answerTargets.add(AT2_GRPYHS);
            answerTargets.add(AT3_RAVEN);
            answerTargets.add(AT4_WHISTLE);
            return answerTargets;
        }
    }
}
