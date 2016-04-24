package commrhardman23.httpsgithub.employeedatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SearchDatabase extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private EditText edtxtName;
    private EditText edtxtPosition;
    private EditText edtxtEmployeeNum;
    private EditText edtxtWage;
    private TextView txtvwResult;
    private GestureDetector motionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_database);

        edtxtName = (EditText) findViewById(R.id.edtxtName);
        edtxtPosition = (EditText) findViewById(R.id.edtxtPosition);
        edtxtEmployeeNum = (EditText) findViewById(R.id.edtxtEmployeeNum);
        edtxtWage = (EditText) findViewById(R.id.edtxtWage);
        txtvwResult = (TextView) findViewById(R.id.txtvwResult);

        motionDetector = new GestureDetector(this, this);

    }

    /**
     * searchDatabase gets all the information the user wants to search for, searches the database
     * for it, and displays the result in a TextView.
     * @param vw is the button this method is associated with
     */
    public void searchDatabase(View vw){

        int numOfArguments = 0;
        int indexOfSearchArray = 0;
        String name = "";
        String position = "";
        String employeeNum = "";
        String wage = "";
        String whereToSearch = "";
        String[] elementsToSearch;
        EmployeeDatabaseHelper employeeDatabaseHelper = new EmployeeDatabaseHelper(this, null, null, 0);
        SQLiteDatabase db;
        Cursor searchCursor;


        if(edtxtName.getText().length() != 0){

            numOfArguments++;
            name = edtxtName.getText().toString();

            whereToSearch += "NAME = ?";
        }

        if(edtxtPosition.getText().length() != 0){

            numOfArguments++;
            position = edtxtPosition.getText().toString();

            if(whereToSearch.equals("")) {
                whereToSearch += "POSITION = ?";
            } else {
                whereToSearch += " OR POSITION = ?";
            }


        }

        if(edtxtEmployeeNum.getText().length() != 0){

            numOfArguments++;
            employeeNum = edtxtEmployeeNum.getText().toString();

            if(whereToSearch.equals("")) {
                whereToSearch += "EMPLOYEE_NUM = ?";
            } else {
                whereToSearch += " OR EMPLOYEE_NUM = ?";
            }


        }

        if(edtxtWage.getText().length() != 0){

            numOfArguments++;
            wage = edtxtWage.getText().toString();

            if(whereToSearch.equals("")) {
                whereToSearch += "WAGE = ?";
            } else {
                whereToSearch += " OR WAGE = ?";
            }


        }

        if(numOfArguments == 0){

            elementsToSearch = null;
            whereToSearch = null;

        } else {

            elementsToSearch = new String[numOfArguments];

            if(edtxtName.getText().length() != 0){

                elementsToSearch[indexOfSearchArray] = name;
                indexOfSearchArray++;

            }

            if(edtxtPosition.getText().length() != 0){

                elementsToSearch[indexOfSearchArray] = position;
                indexOfSearchArray++;

            }

            if(edtxtEmployeeNum.getText().length() != 0){

                elementsToSearch[indexOfSearchArray] = employeeNum;
                indexOfSearchArray++;

            }

            if(edtxtWage.getText().length() != 0){

                elementsToSearch[indexOfSearchArray] = wage;
                indexOfSearchArray++;

            }

        }

        try {

            db = employeeDatabaseHelper.getReadableDatabase();

            searchCursor = db.query("EMPLOYEE", new String[] {"NAME", "POSITION", "EMPLOYEE_NUM", "WAGE"},
                    whereToSearch, elementsToSearch, null, null, null);

            if(searchCursor.getCount() == 0){

                txtvwResult.setText("There are no entries with this info...");

            } else {

                txtvwResult.setText("");

                if(searchCursor.moveToFirst()) {
                    for (int i = 0; i < searchCursor.getCount(); i++) {

                        txtvwResult.setText(txtvwResult.getText().toString() +
                                String.format("%28s%s\n%28s%s\n%s%d\n%28.6s%.2f\n\n", "Name: ",
                                                searchCursor.getString(0), "Position: ",
                                                searchCursor.getString(1), "Employee Number: ",
                                                searchCursor.getInt(2), "Wage: $",
                                                searchCursor.getDouble(3)));

                                        searchCursor.moveToNext();

                    }

                }
            }

        } catch(SQLiteException e){

            txtvwResult.setText("Employee database was not found...");

        }

    }

    public boolean onTouchEvent(MotionEvent event){

        this.motionDetector.onTouchEvent(event);

        return super.onTouchEvent(event);

    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        boolean result = false;

        if(velocityX > 0){

            setResult(0);
            finish();

        }

        return result;

    }
}