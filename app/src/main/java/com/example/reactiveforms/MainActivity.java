package com.example.reactiveforms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;


import com.example.reactiveforms.databinding.ActivityMainBinding;

import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.rxjava3.core.Observable;


public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;

MutableLiveData<Boolean> check = new MutableLiveData<>();

    MutableLiveData<Boolean> getEmailMutableLiveData = new MutableLiveData<>();
     LiveData<Boolean> getEmailLiveData() {
        return getEmailMutableLiveData;
    }
    MutableLiveData<Boolean> getPasswordMutableLiveData = new MutableLiveData<>();
     LiveData<Boolean> getPasswordLiveData() {
        return getPasswordMutableLiveData;
    }
    Observable<Boolean> passwordObservable ;
    Observable<Boolean> emailObservable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        setUpUi();
        setUpObserver();
    }

    private void setUpObserver() {
          getEmailMutableLiveData.setValue(false);
          getPasswordMutableLiveData.setValue(false);

        getEmailLiveData().observe(this,aBoolean -> {
            if (aBoolean)
                check.setValue(true);
        });
        getPasswordLiveData().observe(this,aBoolean -> {
            check.setValue(false);
        });

        check.observe(this,aBoolean -> {
            if (getEmailLiveData().getValue() && getPasswordLiveData().getValue()){
                binding.submet.setEnabled(true);
            }else
                binding.submet.setEnabled(false);
        });

//        Observable<Boolean> vvvv = Observable.combineLatest(getEmailLiveData().getValue(),getPasswordLiveData().getValue(),());
//        Observable<Boolean> combinedObservables = Observable.combineLatest(
//                getEmailLiveData().getValue(), getPasswordLiveData().getValue(), (o1, o2)-> o1);
//            combinedObservables.subscribe(binding.submet::setEnabled);

    }

    private void setUpUi() {
//        binding.submet.setEnabled(false);
        SliderAdapter sliderAdapter =new SliderAdapter(this);
        binding.imageSlider.setSliderAdapter(sliderAdapter);

        binding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                binding.emailInputLayout.setError("Invalid email");
                emailObservable = RxTextView.textChanges(binding.email)
                        .map(inputText ->inputText.toString().matches("(?:[a-z0-9!#$%'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))
                        .distinctUntilChanged();
                emailObservable.subscribe(isValid -> binding.emailInputLayout.setErrorEnabled(!isValid));
                emailObservable.subscribe(isValid -> getEmailMutableLiveData.postValue(isValid));

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 binding.passwodInputLayout.setError("Invalid password");
                 passwordObservable = RxTextView.textChanges( binding.password)
                        .map(inputText ->inputText.toString().matches("^(?=.*\\d).{4,8}$"))
                        .distinctUntilChanged();
                 passwordObservable.subscribe(isValid -> getPasswordMutableLiveData.postValue(isValid));
                 passwordObservable.subscribe(isValid -> binding.passwodInputLayout.setErrorEnabled(!isValid));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


//            Observable<Boolean> combinedObservables = Observable.combineLatest(emailObservable, passwordObservable, (o1, o2) -> o1 && o2);
//            combinedObservables.subscribe(binding.submet::setEnabled);


        //isVisible -> binding.submet.setVisibility(isVisible ? View.VISIBLE : View.GONE)

//        getEmailMutableLiveData.setValue(binding.email.getText());
    }

    private boolean isValidLogin(CharSequence value) {
        boolean isValid = false;

        if (value.length()!=0 && value.toString().matches("(?:[a-z0-9!#$%'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"))
        {
            isValid =true;
        }
        return isValid;
    }
    private boolean isValidPassword(CharSequence value) {
        return value.toString().matches("^(?=.*\\d).{4,8}$");
    }
}