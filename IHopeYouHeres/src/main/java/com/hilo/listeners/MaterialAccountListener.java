package com.hilo.listeners;


import com.hilo.navigation.MaterialAccount;

/**
 * Created by neokree on 11/12/14.
 */
public interface MaterialAccountListener {

    void onAccountOpening(MaterialAccount account);

    void onChangeAccount(MaterialAccount newAccount);

}
