package com.example.firebaseapplication.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.firebaseapplication.data.repository.CustomerRepository;

public class CustomerAuthViewModel extends ViewModel {

    private final CustomerRepository repository;
    private final MutableLiveData<Boolean> registrationSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> registrationError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> loginSuccess = new MutableLiveData<>();
    private final MutableLiveData<String> loginError = new MutableLiveData<>();

    public CustomerAuthViewModel() {
        repository = new CustomerRepository();
    }

    public void register(String name, String email, String phone, String address, String password) {
        repository.registerCustomer(name, email, phone, address, password, new CustomerRepository.RegisterCallback() {
            @Override
            public void onSuccess() {
                registrationSuccess.setValue(true);
            }

            @Override
            public void onFailure(String error) {
                registrationError.setValue(error);
            }
        });
    }

    public void login(String email, String password) {
        repository.loginCustomer(email, password, new CustomerRepository.RegisterCallback() {
            @Override
            public void onSuccess() {
                loginSuccess.setValue(true);
            }

            @Override
            public void onFailure(String error) {
                loginError.setValue(error);
            }
        });
    }

    public LiveData<Boolean> getRegistrationSuccess() {
        return registrationSuccess;
    }

    public LiveData<String> getRegistrationError() {
        return registrationError;
    }

    public LiveData<Boolean> getLoginSuccess() {
        return loginSuccess;
    }

    public LiveData<String> getLoginError() {
        return loginError;
    }

}




