package com.example.quizapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.quizapp.QuizContract.*;

import java.util.ArrayList;
import java.util.List;

public class QuizDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GeneralKnowledge.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public QuizDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuizContract.QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT," +
                QuestionsTable.COLUMN_OPTION2 + " TEXT," +
                QuestionsTable.COLUMN_OPTION3 + " TEXT," +
                QuestionsTable.COLUMN_OPTION4 + " TEXT," +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable(){
        Question q1 = new Question("In which country can you find the ancient city of Petra, famous for its rock-cut architecture?", "A. Jordan", "B. Greece", "C. Egypt", "D. Turkey", 1);
        addQuestion(q1);
        Question q2 = new Question("What ancient civilization built the city of Machu Picchu in the 15th century?", "A. Aztec", "B. Inca", "C. Maya", "D. Olmec", 2);
        addQuestion(q2);
        Question q3 = new Question("In Greek mythology, who is the Titaness of memory and the mother of the Muses?", "A. Themis", "B. Leto", "C. Mnemosyne", "D. Rhea", 3);
        addQuestion(q3);
        Question q4 = new Question("Which chemical element, named after a famous physicist, has the atomic number 99 and is commonly used in smoke detectors?", "A. Curium", "B. Fermium", "C. Californium", "D. Einsteinium", 4);
        addQuestion(q4);
        Question q5 = new Question("Who is the author of the famous novel \"Crime and Punishment\"?", "A. Leo Tolstoy", "B. Fyodor Dostoevsky", "C. Anton Chekhov", "D. Vladimir Nabokov", 2);
        addQuestion(q5);
    }

    private void addQuestion(Question question){
        ContentValues contentValues = new ContentValues();
        contentValues.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        contentValues.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        contentValues.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        contentValues.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        contentValues.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        contentValues.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswernumber());

        db.insert(QuestionsTable.TABLE_NAME, null ,contentValues);
    }

    @SuppressLint("Range")
    public List<Question> getAllQuestions(){
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+QuestionsTable.TABLE_NAME, null);

        if (cursor.moveToFirst()){
            do {
                Question question = new Question();
                question.setQuestion(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(cursor.getString(cursor.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswernumber(cursor.getInt(cursor.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                questionList.add(question);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return questionList;
    }

}