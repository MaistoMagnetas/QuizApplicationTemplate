package wallpapers.cactustree.pc.quizapplicationtemplate.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import wallpapers.cactustree.pc.quizapplicationtemplate.Question;
import wallpapers.cactustree.pc.quizapplicationtemplate.QuizContract.AnswerTargets;
import wallpapers.cactustree.pc.quizapplicationtemplate.QuizContract.QuestionsTable;

/**
 * Quiz database helper class.
 */
public class QuizDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cactusquizdatabase.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTION_TABLE = "CREATE TABLE " + QuestionsTable.TABLE_NAME + " (" +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_TARGET + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NUM + " INTEGER" + ")";

        db.execSQL(SQL_CREATE_QUESTION_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    /**
     * FIlls db with questions.
     */
    private void fillQuestionsTable() {
        Question question1 = new Question("A is correct", "A", "B", "C", AnswerTargets.AT1_SLYTHERIN, 1);
        addQuestion(question1);

        Question question2 = new Question("B is correct", "A", "B", "C", AnswerTargets.AT2_GRPYHS, 2);
        addQuestion(question2);

        Question question3 = new Question("C is correct", "A", "B", "C", AnswerTargets.AT3_RAVEN, 3);
        addQuestion(question3);

        Question question4 = new Question("B is correct", "A", "B", "C", AnswerTargets.AT2_GRPYHS, 2);
        addQuestion(question4);
    }

    /**
     * Adds question to database
     *
     * @param question question parameter.
     */
    private void addQuestion(Question question) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        contentValues.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        contentValues.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        contentValues.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        contentValues.put(QuestionsTable.COLUMN_ANSWER_TARGET, question.getAnswerTarget());
        contentValues.put(QuestionsTable.COLUMN_ANSWER_NUM, question.getAnswerNum());
        db.insert(QuestionsTable.TABLE_NAME, null, contentValues);
    }

    /**
     * Gets all questions from database.
     *
     * @return all database questions.
     */
    public List<Question> getAllQuestions() {
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerTarget(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_ANSWER_TARGET)));
                question.setAnswerNum(cursor.getInt(cursor.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NUM)));
                questionList.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return questionList;
    }
}
