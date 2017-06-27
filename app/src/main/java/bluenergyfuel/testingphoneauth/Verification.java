package bluenergyfuel.testingphoneauth;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;


/**
 * A simple {@link Fragment} subclass.
 */
public class Verification extends Fragment {
    public static final String ARGS_PHONE ="phone";
    public static final String ARGS_VER_CODE = "verificationID";
    private EditText myEditText;
    private static final String LETTER_SPACING = " ";
    private String myPreviousText;
    private TextView textView;
    private Button button;


    public Verification() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_verification, container, false);
        final String st = getArguments().getString(ARGS_PHONE);
        final String cd = getArguments().getString(ARGS_VER_CODE);
        textView = (TextView) view.findViewById(R.id.phone_numbah);
        textView.setText(st);

        button = (Button) view.findViewById(R.id.submit_btn);

        myEditText = (EditText) view.findViewById(R.id.ver_edit);
        myEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                addSpaces(text);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temp = myEditText.getText().toString();
                temp = temp.replaceAll(" ", "");
                MainActivity  m = (MainActivity) getActivity();
                PhoneAuthCredential phoneCre = PhoneAuthProvider.getCredential(cd, temp);
                m.signInWithPhoneAuthCredential(phoneCre);
            }
        });



        return view;
    }

    private void addSpaces(String text) {
        // Only update the EditText when the user modify it -> Otherwise it will be triggered when adding spaces
        if (!text.equals(myPreviousText)) {
            // Remove spaces
            text = text.replace(" ", "");

            // Add space between each character
            StringBuilder newText = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                if (i == text.length() - 1) {
                    // Do not add a space after the last character -> Allow user to delete last character
                    newText.append(Character.toUpperCase(text.charAt(text.length() - 1)));
                }
                else {
                    newText.append(Character.toUpperCase(text.charAt(i)) + LETTER_SPACING);
                }
            }

            myPreviousText = newText.toString();
            // Update the text with spaces and place the cursor at the end
            myEditText.setText(newText);
            myEditText.setSelection(newText.length());
        }
    }


}
