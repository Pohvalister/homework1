package pohvalister.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


public class mainActivity extends AppCompatActivity implements View.OnClickListener {
    static final String STATE_OPERATOR = "operator";
    static final String STATE_FIRST_VAL = "frstOper";
    static final String STATE_SECOND_VAL = "scndOper";
    static final String STATE_ANSWER = "answer";
    static final String STATE_AIM = "aim";


    TextView frst, scnd, oper;
    TextView answer;

    String operator;
    String frstOper;
    String scndOper;
    String answerVal;
    int aim;

    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        savedInstanceState.putString(STATE_OPERATOR, operator);
        savedInstanceState.putString(STATE_FIRST_VAL, frstOper);
        savedInstanceState.putString(STATE_SECOND_VAL, scndOper);
        savedInstanceState.putString(STATE_FIRST_VAL, frstOper);
        savedInstanceState.putString(STATE_ANSWER,answerVal);
        savedInstanceState.putInt(STATE_AIM,aim);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            operator = savedInstanceState.getString(STATE_OPERATOR, "...");
            frstOper = savedInstanceState.getString(STATE_FIRST_VAL, "");
            scndOper = savedInstanceState.getString(STATE_SECOND_VAL, "");
            answerVal = savedInstanceState.getString(STATE_ANSWER, "");
            aim = savedInstanceState.getInt(STATE_AIM, 1);
        } else {
            operator = "...";
            frstOper = "";
            scndOper = "";
            answerVal = "";
            aim=1;
        }
        setContentView(R.layout.activity_calculator);
        answer = (TextView) findViewById(R.id.answer);
        answer.setText(answerVal);
        frst = (TextView) findViewById(R.id.firstVal);
        frst.setText(frstOper);
        scnd = (TextView) findViewById(R.id.secondVal);
        scnd.setText(scndOper);
        oper = (TextView) findViewById(R.id.operator);
        oper.setText(operator);

        findViewById(R.id.d0).setOnClickListener(this);
        findViewById(R.id.d1).setOnClickListener(this);
        findViewById(R.id.d2).setOnClickListener(this);
        findViewById(R.id.d3).setOnClickListener(this);
        findViewById(R.id.d4).setOnClickListener(this);
        findViewById(R.id.d5).setOnClickListener(this);
        findViewById(R.id.d6).setOnClickListener(this);
        findViewById(R.id.d7).setOnClickListener(this);
        findViewById(R.id.d8).setOnClickListener(this);
        findViewById(R.id.d9).setOnClickListener(this);
        changeAim(aim);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.d0:
                addDigit('0');
                break;
            case R.id.d1:
                addDigit('1');
                break;
            case R.id.d2:
                addDigit('2');
                break;
            case R.id.d3:
                addDigit('3');
                break;
            case R.id.d4:
                addDigit('4');
                break;
            case R.id.d5:
                addDigit('5');
                break;
            case R.id.d6:
                addDigit('6');
                break;
            case R.id.d7:
                addDigit('7');
                break;
            case R.id.d8:
                addDigit('8');
                break;
            case R.id.d9:
                addDigit('9');
                break;
        }
    }

    public void calcIter(){
        if (!(operator.equals("...")||frstOper.length()==0||scndOper.length()==0)){
            String tmpS1=frstOper;
            String tmpS2=scndOper;
            if (frstOper.charAt(frstOper.length()-1)=='.')
                tmpS1+='0';
            if (scndOper.charAt(scndOper.length()-1)=='.')
                tmpS2+='0';
            if (frstOper.length()==1&&frstOper.charAt(0)=='-')
                tmpS1="0";
            Double tmp1=Double.valueOf(tmpS1);
            Double tmp2=Double.valueOf(tmpS2);
            Double tmpMain=Double.valueOf(0);
            if (operator.equals("+"))
                tmpMain=tmp1+tmp2;
            if (operator.equals("-"))
                tmpMain=tmp1-tmp2;
            if (operator.equals("*"))
                tmpMain=tmp1*tmp2;
            if (operator.equals("/"))
                tmpMain=tmp1/tmp2;
            answerVal=tmpMain.toString();
        }
        answer.setText(answerVal);
    }

    public void changeAim(int i) {
            frst.setBackgroundResource(R.color.colorLightBlue);
            scnd.setBackgroundResource(R.color.colorLightBlue);
            oper.setBackgroundResource(R.color.colorMediumBlue);
        aim = i;
        if (aim == 1)
            frst.setBackgroundResource(R.color.colorConsiderBlue);
        if (aim == 2)
            scnd.setBackgroundResource(R.color.colorConsiderBlue);
        if (aim == 0)
            oper.setBackgroundResource(R.color.colorConsiderBlue);
    }
    public String confirmAddDigit(TextView view, String val, char i) {
        if (val.length() >= 12) {
            Toast toast = Toast.makeText(mainActivity.this, "Слишком большое число (>12 символов)", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            if (val.length()==1&&val.charAt(0)=='0'&&i!='.')
                val=""+i;
            else
            val += i;
        }
        view.setText(val);
        return val;
    }
    public String clearTextView(TextView view) {
        String val = "";
        view.setText(val);
        return val;
    }

    public void addDigit(char i) {
        if (aim == 0) {
            changeAim(2);
            clearTextView(scnd);
            addDigit(i);
            calcIter();
            return;
        }
        if (aim == 1) {
            frstOper = confirmAddDigit(frst, frstOper, i);
            calcIter();
            return;
        }
        if (aim == 2) {
            scndOper = confirmAddDigit(scnd, scndOper, i);
            calcIter();
            return;
        }
    }

    public String confirmRemoveDigit(TextView view, String val) {
        val=val.substring(0,val.length()-1);
        if (val.length()==0) {
            aim-=2;
            if (aim==-1)aim=1;
            changeAim(aim);
        }
        view.setText(val);
        return val;
    }
    public void delClick(View v) {
        if (aim==0||(aim==1&&frstOper.length()<=0)||(aim==2&&scndOper.length()<=0)){
            operator="...";
            oper.setText(operator);
            changeAim(1);
            return;
        }
        if (aim==1){
            frstOper=confirmRemoveDigit(frst,frstOper);
        }
        if (aim==2){
            scndOper=confirmRemoveDigit(scnd,scndOper);
        }
        calcIter();
    }

    public void operCommonPart(){
        oper.setText(operator);
        if (aim==2){
            if (answer.length()>=12){
                Toast toast = Toast.makeText(mainActivity.this, "Слишком большое число (>12 символов) - невозможно записать в операнд", Toast.LENGTH_LONG);
                toast.show();
                calClick(answer);
                return;
            }
            else{
                calcIter();
                if (answerVal.equals("NaN")||answerVal.equals("Infinity")){
                    Toast toast = Toast.makeText(mainActivity.this, "Слишком сложное число - не поддается обработке", Toast.LENGTH_LONG);
                    toast.show();
                    calClick(answer);
                    return;
                }
                else {
                    frstOper = answerVal;
                    frst.setText(frstOper);
                }
            }
        }
        changeAim(2);
        scndOper=clearTextView(scnd);
        calcIter();
    }
    public void divClick(View v) {
        operCommonPart();
        operator="/";
        oper.setText(operator);
    }

    public void mulClick(View v) {
        operCommonPart();
        operator="*";
        oper.setText(operator);
    }

    public void subClick(View v) {
        operCommonPart();
        operator="-";
        oper.setText(operator);
    }

    public void addClick(View v) {
        operCommonPart();
        operator="+";
        oper.setText(operator);
    }

    public void calClick(View v){
        calcIter();
        frstOper=clearTextView(frst);
        scndOper=clearTextView(scnd);
        changeAim(1);
    }


    public boolean checkPointState(String str) {
        for (int i = 0; i < str.length(); i++)
            if (str.charAt(i) == '.')
                return false;
        return true;
    }
    public void pointSet(View v) {
        if (aim == 0||(aim==1&&frstOper.length()==0)||(aim==2&&scndOper.length()==0)) {
            addDigit('0');
            addDigit('.');
            return;
        }
        if ((aim == 1 && checkPointState(frstOper)) || (aim == 2 && checkPointState(scndOper)))
            addDigit('.');
        else {
            Toast toast = Toast.makeText(mainActivity.this, "Вторая по счету \".\" невозможна", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
