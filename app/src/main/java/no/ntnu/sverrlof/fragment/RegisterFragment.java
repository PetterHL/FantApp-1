package no.ntnu.sverrlof.fragment;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;

import no.ntnu.sverrlof.ApiClient;
import no.ntnu.sverrlof.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    private EditText editEmail;
    private EditText editUsername;
    private EditText editPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_create, container, false);

        editEmail = view.findViewById(R.id.editTextEmail);
        editUsername = view.findViewById(R.id.editTextUsername);
        editPassword = view.findViewById(R.id.editTextPassword);

        Button regButton = (Button) view.findViewById(R.id.regButton);
        regButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.regButton:
                        userRegister();
                        break;
                }
            }
        });
        return view;
    }

    private void userRegister() {
        String email = editEmail.getText().toString();
        String uid = editUsername.getText().toString();
        String pwd = editPassword.getText().toString();


        if (uid.isEmpty()) {
            editUsername.setError("Please enter a username");
            editUsername.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editEmail.setError("Please enter an email!");
            editEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editEmail.setError("Please enter a valid email!");
            editEmail.requestFocus();
        }


        if (pwd.isEmpty()) {
            editPassword.setError("Please enter a password");
            editPassword.requestFocus();
            return;
        }

        if (pwd.length() < 3) {
            editPassword.setError("Password should be at least 3 characters long!");
            editPassword.requestFocus();
        }

        // User registration using api call
        Call<ResponseBody> call = ApiClient
                .getSINGLETON()
                .getApi()
                .createUser(email, uid, pwd);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String s = response.body().string();
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
