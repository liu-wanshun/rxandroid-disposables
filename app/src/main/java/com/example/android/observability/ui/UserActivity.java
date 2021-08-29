/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.observability.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleDisposable;
import androidx.lifecycle.ViewModelProvider;

import com.example.android.observability.Injection;
import com.example.android.persistence.R;
import com.lws.rxutils.RxCall;
import com.lws.rxutils.RxTransformer;

import io.reactivex.rxjava3.core.Single;


/**
 * Main screen of the app. Displays a user name and gives the option to update the user name.
 */
public class UserActivity extends AppCompatActivity {

    private static final String TAG = UserActivity.class.getSimpleName();
    private TextView mUserName;
    private EditText mUserNameInput;
    private Button mUpdateButton;
    private ViewModelFactory mViewModelFactory;
    private UserViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        mUserName = findViewById(R.id.user_name);
        mUserNameInput = findViewById(R.id.user_name_input);
        mUpdateButton = findViewById(R.id.update_user);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = new ViewModelProvider(this, mViewModelFactory).get(UserViewModel.class);
        mUpdateButton.setOnClickListener(v -> updateUserName());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Subscribe to the emissions of the user name from the view model.
        // Update the user name text view, at every onNext emission.
        // In case of error, log the exception.
        LifecycleDisposable.from(this).add(mViewModel.getUserName()
                .compose(RxTransformer.io2uiFlowable())
                .subscribe(userName -> mUserName.setText(userName),
                        throwable -> Log.e(TAG, "Unable to get username", throwable)));
    }

    private void updateUserName() {
        String userName = mUserNameInput.getText().toString();
        // Disable the update button until the user name update has been done
        mUpdateButton.setEnabled(false);
        // Subscribe to updating the user name.
        // Re-enable the button once the user name has been updated
        LifecycleDisposable.from(this).add(mViewModel.updateUserName(userName)
                .compose(RxTransformer.io2uiCompletable())
                .subscribe(() -> mUpdateButton.setEnabled(true),
                        throwable -> Log.e(TAG, "Unable to update username", throwable)));


        LifecycleDisposable.from(this).add(RxCall.io2ui(() -> work(5000))
                .subscribe(() -> Log.d(TAG, "work:  over")));

        LifecycleDisposable.from(this).add(RxCall.io2ui(() -> {
            work(3000);
            return "work over";
        }).subscribe(result -> Log.d(TAG, "accept: " + result)));


        Single.fromSupplier(() -> {
            work(3000);
            return "work over";
        })
                .compose(RxTransformer.io2uiSingle())
                .subscribe(result -> Log.d(TAG, "accept: " + result));
    }


    private void work(long time) {
        long a = System.currentTimeMillis();
        while (System.currentTimeMillis() <= a + time) {
            Log.d(TAG, "work: ");
        }
    }
}
