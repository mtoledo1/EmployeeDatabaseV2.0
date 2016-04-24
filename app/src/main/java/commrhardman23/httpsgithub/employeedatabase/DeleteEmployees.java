package commrhardman23.httpsgithub.employeedatabase;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DeleteEmployees extends AppCompatActivity implements GestureDetector.OnGestureListener {

    private EditText edtxtName;
    private EditText edtxtPosition;
    private EditText edtxtEmployeeNum;
    private EditText edtxtWage;
    private TextView txtvwResult;
    private GestureDetector motionDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_employees);

        edtxtName = (EditText) findViewById(R.id.edtxtName);
        edtxtPosition = (EditText) findViewById(R.id.edtxtPosition);
        edtxtEmployeeNum = (EditText) findViewById(R.id.edtxtEmployeeNum);
        edtxtWage = (EditText) findViewById(R.id.edtxtWage);
        txtvwResult = (TextView) findViewById(R.id.txtvwResult);

        motionDetector = new GestureDetector(this, this);

    }

    /**
     * deleteEntry gets all the deletion criteria from the user and deletes all entries in the
     * database that contain the information provided
     * @param vw is the button the method is associated with
     */
    public void deleteEntry(View vw){

        int numOfArguments = 0;
        int indexOfSearchArray = 0;
        String name = "";
        String position = "";
        String employeeNum = "";
        String wage = "";
        String whereToDelete = "";
        String[] elementsToDelete;
        EmployeeDatabaseHelper employeeDatabaseHelper = new EmployeeDatabaseHelper(this, null, null, 0);
        SQLiteDatabase db;
        int numRowsDeleted;

        if(edtxtName.getText().length() != 0){

            numOfArguments++;
            name = edtxtName.getText().toString();
            whereToDelete += "NAME = ?";

        }

        if(edtxtPosition.getText().length() != 0){

            numOfArguments++;
            position = edtxtPosition.getText().toString();

            if(whereToDelete.equals("")) {
                whereToDelete += "POSITION = ?";
            } else {
                whereToDelete += " AND POSITION = ?";
            }

        }

        if(edtxtEmployeeNum.getText().length() != 0){

            numOfArguments++;
            employeeNum = edtxtEmployeeNum.getText().toString();

            if(whereToDelete.equals("")) {
                whereToDelete += "EMPLOYEE_NUM = ?";
            } else {
                whereToDelete += " AND EMPLOYEE_NUM = ?";
            }

        }

        if(edtxtWage.getText().length() != 0){

            numOfArguments++;
            wage = edtxtWage.getText().toString();

            if(whereToDelete.equals("")) {
                whereToDelete += "WAGE = ?";
            } else {
                whereToDelete += " AND WAGE = ?";
            }

        }

        if(numOfArguments == 0){

            elementsToDelete = null;
            whereToDelete = null;

        } else {

            elementsToDelete = new String[numOfArguments];

            if (edtxtName.getText().length() != 0) {

                elementsToDelete[indexOfSearchArray] = name;
                indexOfSearchArray++;

            }

            if (edtxtPosition.getText().length() != 0) {

                elementsToDelete[indexOfSearchArray] = position;
                indexOfSearchArray++;

            }

            if (edtxtEmployeeNum.getText().length() != 0) {

                elementsToDelete[indexOfSearchArray] = employeeNum;
                indexOfSearchArray++;

            }

            if (edtxtWage.getText().length() != 0) {

                elementsToDelete[indexOfSearchArray] = wage;
                indexOfSearchArray++;

            }

        }

        try {

            db = employeeDatabaseHelper.getWritableDatabase();

            numRowsDeleted = employeeDatabaseHelper.deleteElement(db, whereToDelete, elementsToDelete);

            txtvwResult.setText("Number of rows deleted: " + numRowsDeleted);

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

        if(velocityX < 0){

            setResult(0);
            result = true;
            finish();

        }

        return result;
    }
}
