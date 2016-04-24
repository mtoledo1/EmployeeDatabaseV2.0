package commrhardman23.httpsgithub.employeedatabase;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EmployeeDatabaseInterface extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private EditText edtxtName;
    private EditText edtxtPosition;
    private EditText edtxtEmployeeNum;
    private EditText edtxtWage;
    private TextView txtvwResult;
    private GestureDetector motionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_database_interface);

        edtxtName = (EditText) findViewById(R.id.edtxtName);
        edtxtPosition = (EditText) findViewById(R.id.edtxtPosition);
        edtxtEmployeeNum = (EditText) findViewById(R.id.edtxtEmployeeNum);
        edtxtWage = (EditText) findViewById(R.id.edtxtWage);
        txtvwResult = (TextView) findViewById(R.id.txtvwResult);

        motionDetector = new GestureDetector(this, this);

    }

    /**
     * insertData adds elements to the Employee database using information given by the user
     * @param vw is the button the method is associated with
     */
    public void insertData(View vw){

        EmployeeDatabaseHelper employeeDatabaseHelper = new EmployeeDatabaseHelper(this, null, null, 0);
        SQLiteDatabase db;

        String name;
        String position;
        String employeeNumber;
        String wage;

        ContentValues employeeValues = new ContentValues();

        try {

            db = employeeDatabaseHelper.getWritableDatabase();

            if (edtxtName.getText().length() == 0 || edtxtPosition.getText().length() == 0 ||
                    edtxtEmployeeNum.getText().length() == 0 || edtxtWage.getText().length() == 0) {

                txtvwResult.setText("You must enter all values to add an element!");

            } else {

                name = edtxtName.getText().toString();
                position = edtxtPosition.getText().toString();
                employeeNumber = edtxtEmployeeNum.getText().toString();
                wage = edtxtWage.getText().toString();

                employeeValues.put("NAME", name);
                employeeValues.put("POSITION", position);
                employeeValues.put("EMPLOYEE_NUM", employeeNumber);
                employeeValues.put("WAGE", wage);

                employeeDatabaseHelper.insertElement(db, employeeValues);

                txtvwResult.setText("Employee successfully added to the database!");
            }

        } catch (SQLiteException e){

            txtvwResult.setText("Employee database was not found...");

        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        edtxtName.setText("");
        edtxtPosition.setText("");
        edtxtEmployeeNum.setText("");
        edtxtWage.setText("");
        txtvwResult.setText("");

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

        Intent goToSearch = new Intent(this, SearchDatabase.class);
        Intent goToDelete = new Intent(this, DeleteEmployees.class);

        if(velocityX < 0){

            startActivityForResult(goToSearch, 0);
            result = true;

        } else {

            startActivityForResult(goToDelete, 0);
            result = true;

        }

        return result;
    }
}
